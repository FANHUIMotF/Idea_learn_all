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
    public void execute() throws InterruptedException {
        System.out.println(Thread.currentThread().getName()+":任务2执行开始");
//        Thread.sleep(10*1000);
        System.out.println(Thread.currentThread().getName()+":任务2执行结束");
    }
}
