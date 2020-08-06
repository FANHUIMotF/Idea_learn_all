package cn.itfh.crontab.config;

import cn.itfh.crontab.entity.CronTaskEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.config.CronTask;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.stereotype.Component;

/***
 *  注册器修改类
 *  @className: ConfigModifier
 *  @author: fh
 *  @date: 2020/8/5
 *  @version : V1.0
 */
@Component("configModifier")
@Slf4j
public class ConfigModifier {

    @Autowired
    @Qualifier("cronConfig")
    CronConfig cronConfig;


    /**
     * 项目运行中，添加新的定时任务
     *
     * @param cronTaskEntity
     */
    public void addTaskInRunning(CronTaskEntity cronTaskEntity, Runnable task) {
        //创建新的定时任务执行类,并将此任务加入调度器
        ScheduledTask scheduledTask = cronConfig.scheduledTaskRegistrar.scheduleCronTask(new CronTask(task, cronTaskEntity.getCorn()));
        ScheduledTask oldScheduledTask = cronConfig.nameTask.get(cronTaskEntity.getClassName());
        //极端并发的情况下，有重复添加的风险
        if(oldScheduledTask != null){
            this.delTask(cronTaskEntity.getClassName());
        }
        cronConfig.nameTask.put(cronTaskEntity.getClassName(),scheduledTask);
        log.info("定时任务[className:" + cronTaskEntity.getClassName() + "],添加成功");

    }

    /**
     * 刪除定時任務
     */
    public void delTask(String className) {
        ScheduledTask scheduledTask = cronConfig.nameTask.get(className);
        if(scheduledTask != null){
            //取消此定时任务
            scheduledTask.cancel();
        }
    }
}
