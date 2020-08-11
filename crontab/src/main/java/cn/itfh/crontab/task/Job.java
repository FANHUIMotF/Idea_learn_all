package cn.itfh.crontab.task;

/**
 * 定时任务实际执行类  实现此接口
 */
public interface Job {
    void execute() throws Exception;
}
