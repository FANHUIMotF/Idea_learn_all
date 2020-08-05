package cn.itfh.crontab.config;

import cn.itfh.crontab.runnable.CronTaskRunnable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.scheduling.config.Task;

import java.util.Set;

/***
 *  定时任务注册器加载完所有定时任务之后，赋值cronConfig.nameTask
 *  @className: AfterConfig
 *  @author: fh
 *  @date: 2020/8/5
 *  @version : V1.0
 */
@Configuration
@Order
public class AfterConfig implements CommandLineRunner {
    @Autowired
    @Qualifier("cronConfig")
    CronConfig cronConfig;

    @Override
    public void run(String... args) {
        //存入 classname - ScheduledTask
        Set<ScheduledTask> scheduledTasks = cronConfig.scheduledTaskRegistrar.getScheduledTasks();
        for (ScheduledTask next : scheduledTasks) {
            Task task = next.getTask();
            CronTaskRunnable runnable = (CronTaskRunnable) task.getRunnable();
            String className = runnable.getClassName();
            cronConfig.nameTask.put(className, next);
        }
    }
}