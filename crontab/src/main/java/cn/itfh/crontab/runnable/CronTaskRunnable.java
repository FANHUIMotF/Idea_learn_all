package cn.itfh.crontab.runnable;

import cn.itfh.crontab.service.CronTaskService;
import cn.itfh.crontab.task.Job;
import cn.itfh.crontab.util.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.concurrent.ConcurrentMap;

/***
 *  自定义定时任务线程
 *  @className: CronTaskRunnable
 *  @author: fh
 *  @date: 2020/8/5
 *  @version : V1.0
 */
@Slf4j
public class CronTaskRunnable implements Runnable {
    private String className;
    private ConcurrentMap<String, String> nameCron;
    public CronTaskRunnable(String className, ConcurrentMap<String, String> nameCron) {
       this.className = className;
       this.nameCron = nameCron;
    }

    @Override
    public void run() {
        Job job;
        try {
            job = (Job) BeanUtil.getBean(className);
        } catch (Exception e) {
            log.error("转换任务执行bean出现错误:", e);
            return;
        }
        //执行任务
        String newCron = ((CronTaskService) BeanUtil.getBean("cronTaskService")).execute(className, job);
        //如果返回的不为空   说明执行成功  刷新此任务对应的cron
        if (!StringUtils.isEmpty(newCron)) {
            nameCron.put(className, newCron);
        }
    }

    public String getClassName() {
        return className;
    }
}
