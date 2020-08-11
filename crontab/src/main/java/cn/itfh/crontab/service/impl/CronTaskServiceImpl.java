package cn.itfh.crontab.service.impl;

import cn.itfh.crontab.config.ConfigModifier;
import cn.itfh.crontab.dao.CronTaskDao;
import cn.itfh.crontab.entity.CronTaskEntity;
import cn.itfh.crontab.runnable.CronTaskRunnable;
import cn.itfh.crontab.service.CronTaskService;
import cn.itfh.crontab.task.Job;
import cn.itfh.crontab.util.BeanUtil;
import cn.itfh.crontab.util.Constant;
import cn.itfh.crontab.util.CronUtil;
import cn.itfh.crontab.util.JedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.CronExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;

import java.util.Date;
import java.util.List;

/***
 *
 *  @className: CronTaskServiceImpl
 *  @author: fh
 *  @date: 2020/7/29
 *  @version : V1.0
 */
@Service("cronTaskService")
@Slf4j
public class CronTaskServiceImpl implements CronTaskService {
    @Autowired
    CronTaskDao cronTaskDao;

    @Autowired
    @Qualifier("configModifier")
    ConfigModifier configModifier;
    /**
     * 判断是否为第一次加载
     */
    private boolean first = true;

    @Override
    public List<CronTaskEntity> findAll() {
        return cronTaskDao.findAll();
    }

    @Override
    public CronTaskEntity findByPriKey(String className) {
        return cronTaskDao.findByPriKey(className);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.NOT_SUPPORTED)
    public String execute(String calssName, Job job) {
        //项目启动时仅加载定时任务进入调度器，不执行
//        if(first){
//            log.info("定时任务:"+calssName+"加载成功.");
//            first = false;
//            return;
//        }
        String lockName = "CronLock:" + calssName;
        String cron = "";
        Jedis jedis = null;
        CronTaskEntity cronTaskEntity ;
        try {
            //获取此定时任务的最新配置
            cronTaskEntity = cronTaskDao.findByPriKey(calssName);
            if (cronTaskEntity == null) {
                log.error("未找到定时任务[className:" + calssName + "]");
                return cron;
            }
            jedis = JedisUtil.getJedis();
            //设置锁  锁的存活期要大于此任务的执行时间  否则锁无效
            String set = jedis.set(lockName, lockName, "nx", "px", 2000L);
            if (!JedisUtil.OK.equals(set)) {
                log.info("当前线程未抢到执行权");
                return cron;
            }

            //不启用的任务
            if (!Constant.Cron.IsUser_True.getKey().equals(cronTaskEntity.getIsUser())) {
                log.info("此任务不启用，className:" + cronTaskEntity.getClassName());
                //从定时注册器中删除此任务
                configModifier.delTask(cronTaskEntity.getClassName());
                return cron;
            }
            //获取最新的cron
            cron = cronTaskEntity.getCorn();
            //运行中的任务 不再执行
            if (Constant.Cron.Status_Running.equals(cronTaskEntity.getStatus())) {
                log.info("此任务正在运行中，className:" + cronTaskEntity.getClassName());
                return cron;
            }

            this.updateAndExcuteTask(cronTaskEntity, job);

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (jedis != null) {
                //释放锁
                jedis.del(lockName);
                jedis.close();
            }
        }
        return cron;
    }

    private void updateAndExcuteTask(CronTaskEntity cronTaskEntity){
        Job job = (Job) BeanUtil.getBean(cronTaskEntity.getClassName());
        this.updateAndExcuteTask(cronTaskEntity,job);

    }
    /**
     * 修改定时任务，执行对应的定时任务
     *
     * @param cronTaskEntity
     * @param job
     */
    private void updateAndExcuteTask(CronTaskEntity cronTaskEntity, Job job) {
        if(job == null){
            throw new RuntimeException("传入具体任务执行类为null");
        }
        int oldVersion = cronTaskEntity.getVersion();
        int newVersion = (oldVersion >= Constant.MAX_VERSION) ? 1 : oldVersion + 1;
        Date currentDate = new Date();
        String result = Constant.Cron.Result_Fail.getKey();
        String resultDesc;
        CronTaskEntity updateEntity = new CronTaskEntity();
        updateEntity.setClassName(cronTaskEntity.getClassName());
        updateEntity.setVersion(newVersion);
        updateEntity.setStatus(cronTaskEntity.getStatus());
        updateEntity.setCorn(cronTaskEntity.getCorn());
        updateEntity.setLastTime(currentDate);
        //下次执行时间
        updateEntity.setNextTime(CronUtil.getNextExecuteTime(updateEntity.getCorn(), currentDate));
        //执行主要任务
        try {
            //先将任务改为执行执行中
            this.updateStatus(updateEntity.getClassName(),Constant.Cron.Status_Running.getKey());
            job.execute();
            result = Constant.Cron.Result_Success.getKey();
            resultDesc = Constant.Cron.Result_Success.getDisplayName();
        } catch (Exception e) {
            log.error("定时任务执行出错,className:" + cronTaskEntity.getClassName(), e);
            resultDesc = e.getMessage().substring(0, 256);
        }finally {
            //最后改为等待执行
            this.updateStatus(updateEntity.getClassName(),Constant.Cron.Status_Wait.getKey());
        }
        updateEntity.setResult(result);
        updateEntity.setResultDesc(resultDesc);
        cronTaskDao.updateEntity(updateEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void addTask(CronTaskEntity cronTaskEntity) {
        if(StringUtils.isEmpty(cronTaskEntity.getClassName())){
            throw new RuntimeException("主键为空！");
        }

        CronTaskEntity oldEntity = cronTaskDao.findByPriKey(cronTaskEntity.getClassName());
        if (oldEntity != null) {
            throw new RuntimeException("重复的className:" + cronTaskEntity.getClassName());
        }
        //判断cron表达式是否正确
        boolean flage = CronExpression.isValidExpression(cronTaskEntity.getCorn());
        if (!flage) {
            throw new RuntimeException("Cron表达式错了！");
        }
        cronTaskDao.add(cronTaskEntity);
        configModifier.addTaskInRunning(cronTaskEntity,new CronTaskRunnable(cronTaskEntity.getClassName(),cronTaskEntity.getCorn()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void delTask(String className) {
        CronTaskEntity byPriKey = cronTaskDao.findByPriKey(className);
        if(byPriKey == null){
            throw new RuntimeException("不存在此定时任务[className:"+className+"]");
        }
        if(Constant.Cron.Status_Running.getKey().equals(byPriKey.getStatus())){
            throw new RuntimeException("此定时任务处于运行中状态");
        }
        cronTaskDao.delByPriKey(className);
        configModifier.delTask(className);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void suspendTask(String className) {
        CronTaskEntity byPriKey = cronTaskDao.findByPriKey(className);
        if(byPriKey == null){
            throw new RuntimeException("不存在此定时任务[className:"+className+"]");
        }
        if(Constant.Cron.Status_Running.getKey().equals(byPriKey.getStatus())){
            throw new RuntimeException("此定时任务处于运行中状态");
        }
        if(Constant.Cron.IsUser_False.getKey().equals(byPriKey.getIsUser())){
            throw new RuntimeException("此定时任务未启用，无法暂停");
        }
        cronTaskDao.updateIsUserByPriKey(className,Constant.Cron.IsUser_False.getKey());
        configModifier.delTask(className);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void runTask(String className) {
        CronTaskEntity byPriKey = cronTaskDao.findByPriKey(className);
        if (byPriKey == null) {
            throw new RuntimeException("不存在此定时任务[className:" + className + "]");
        }
        if(Constant.Cron.Status_Running.getKey().equals(byPriKey.getStatus())){
            throw new RuntimeException("此定时任务处于运行中状态");
        }
        if (Constant.Cron.IsUser_True.getKey().equals(byPriKey.getStatus())) {
            throw new RuntimeException("此任务已处已启用");
        }
        cronTaskDao.updateIsUserByPriKey(className,Constant.Cron.IsUser_True.getKey());
        configModifier.addTaskInRunning(byPriKey,new CronTaskRunnable(byPriKey.getClassName(),byPriKey.getCorn()));
    }


    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void doNow(String className) {
        CronTaskEntity byPriKey = cronTaskDao.findByPriKey(className);
        if(byPriKey == null){
            throw new RuntimeException("不存在此定时任务[className:"+className+"]");
        }
        if(Constant.Cron.Status_Running.getKey().equals(byPriKey.getStatus())){
            throw new RuntimeException("此定时任务处于运行中状态");
        }
        this.updateAndExcuteTask(byPriKey);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.NOT_SUPPORTED)
    public void updateStatus(String className, String status) {
        cronTaskDao.updateStatus(className,status);
    }
}
