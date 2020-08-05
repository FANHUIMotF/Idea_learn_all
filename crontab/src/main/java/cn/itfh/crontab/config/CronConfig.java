package cn.itfh.crontab.config;

import cn.itfh.crontab.entity.CronTaskEntity;
import cn.itfh.crontab.runnable.CronTaskRunnable;
import cn.itfh.crontab.service.CronTaskService;
import cn.itfh.crontab.util.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/***
 *  初始化 定时任务调度器 ；
 *  @className: CronConfig
 *  @author: fh
 *  @date: 2020/7/29
 *  @version : V1.0
 */
@Configuration("cronConfig")
@Slf4j
public class CronConfig implements SchedulingConfigurer {
    @Autowired
    CronTaskService cronTaskService;

    /**
     * springboot默认创造的定时任务注册器
     */
    protected ScheduledTaskRegistrar scheduledTaskRegistrar;

    /**
     * 储存定时任务类名  -  对应的cron表达式
     */
    protected ConcurrentMap<String, String> nameCron = new ConcurrentHashMap<>(8);
    /**
     * 储存定时任务类名  -  对应的執行綫程
     */
    protected Map<String, ScheduledTask> nameTask= new ConcurrentHashMap<>(8);





    /**
     * 加载定时任务
     *
     * @param scheduledTaskRegistrar
     */
    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        this.initRegistrar(scheduledTaskRegistrar);
        this.scheduledTaskRegistrar = scheduledTaskRegistrar;
        List<CronTaskEntity> all = cronTaskService.findAll();
        if (all.size() == 0) {
            log.info("无定时任务需要加载");
            return;
        }
        for (CronTaskEntity cronTaskEntity : all) {
            //加载 启动状态的定时任务
            if (!Constant.Cron.IsUser_True.getKey().equals(cronTaskEntity.getIsUser())) {
                continue;
            }
            //注册定时任务
            addTaskInStart(cronTaskEntity);
        }
    }

    /**
     * 初始化注册器   对注册器进行一些设置
     */
    private void initRegistrar(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        //创建一个线程池调度器
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        //初始化线程池
        scheduler.initialize();
        //设置线程池容量
        scheduler.setPoolSize(5);
        //线程名前缀
        scheduler.setThreadNamePrefix("cronTask-");
        //调度器销毁时等待已执行任务先完成
        scheduler.setWaitForTasksToCompleteOnShutdown(true);
        //设置容器关闭时等待剩余任务执行的最大时长
        scheduler.setAwaitTerminationSeconds(60);
        //设置当任务被取消的同时从当前调度器移除的策略(取消即移除容器)
        scheduler.setRemoveOnCancelPolicy(true);
        //注册器加入调度器
        scheduledTaskRegistrar.setTaskScheduler(scheduler);
    }

    /**
     * 项目启动时 添加定时任务
     *
     * @param cronTaskEntity
     */
    private void addTaskInStart(CronTaskEntity cronTaskEntity) {
        if (StringUtils.isEmpty(cronTaskEntity.getClassName())) {
            log.error("定时任务执行类名为空");
            return;
        }
        nameCron.put(cronTaskEntity.getClassName(), cronTaskEntity.getCorn());
        Runnable runnable = new CronTaskRunnable(cronTaskEntity.getClassName(),nameCron);

        try {
            //  addCronTask方法 根据cron表达式在第一次加载时生成触发器  后期不可变
//            scheduledTaskRegistrar.addCronTask(
//                    //加入定时任务
//                    this.createTask(cronTaskEntity),
//                    //设置任务对应的触发器（定时器）
//                    nameCron.get(cronTaskEntity.getClassName())
//            );
            scheduledTaskRegistrar.addTriggerTask(
                    //加入定时任务
                    runnable,
                    //每次执行此任务都会根据传入cron生成一个新的触发器
                    triggerContext -> new CronTrigger(nameCron.get(cronTaskEntity.getClassName()))
                            .nextExecutionTime(triggerContext)
            );
        } catch (Exception e) {
            log.error("项目启动时，注册器添加定时任务炸了，错误原因:", e);
            //去除注册失败的定时任务，防止以后添加任务时误判重复
            nameCron.remove(cronTaskEntity.getClassName());
        }

    }





}
