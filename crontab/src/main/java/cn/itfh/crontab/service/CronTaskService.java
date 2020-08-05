package cn.itfh.crontab.service;

import cn.itfh.crontab.entity.CronTaskEntity;
import cn.itfh.crontab.task.Job;

import java.util.List;

/***
 *
 *  @className: CronTaskService
 *  @author: fh
 *  @date: 2020/7/29
 *  @version : V1.0
 */
public interface CronTaskService {
    /**
     * 查询所有定时任务
     * @return
     */
    List<CronTaskEntity> findAll();

    /**
     * 执行任务，执行成功则返回最新的cron
     * @param className
     * @param job
     */
    String execute(String className,Job job);

    /**
     * 根据主键查找定时任务
     * @param className
     * @return
     */
    CronTaskEntity findByPriKey(String className);

    /**
     * 添加定时任务
     * @param cronTaskEntity
     */
    void addTask(CronTaskEntity cronTaskEntity);

    /**
     * 删除定时任务
     * @param className
     */
    void delTask(String className);

    /**
     * 暂停任务
     * @param className
     */
    void suspendTask(String className);

    /**
     * 开启任务
     * @param className
     */
    void runTask(String className);

    /**
     * 立刻执行定时任务
     * @param className
     */
    void doNow(String className);
}
