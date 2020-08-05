package cn.itfh.crontab.task;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/***
 *
 *  @className: Task2
 *  @author: fh
 *  @date: 2020/7/31
 *  @version : V1.0
 */
@Service("task2")
public class Task2 implements Job{
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void execute() {
        System.out.println("任务2执行");
    }
}
