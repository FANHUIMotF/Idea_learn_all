package cn.itfh.crontab.task;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/***
 *
 *  @className: Task1
 *  @author: fh
 *  @date: 2020/7/29
 *  @version : V1.0
 */
@Service("task1")
public class Task1 implements Job {
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void execute() throws Exception {
        System.out.println(Thread.currentThread().getName()+":任务1执行开始");
//        Thread.sleep(10*1000);
        System.out.println(Thread.currentThread().getName()+":任务1执行结束");
    }
}
