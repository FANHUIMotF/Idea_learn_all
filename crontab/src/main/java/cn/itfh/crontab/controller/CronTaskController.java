package cn.itfh.crontab.controller;

import cn.itfh.crontab.entity.CronTaskEntity;
import cn.itfh.crontab.service.CronTaskService;
import cn.itfh.crontab.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/***
 *
 *  @className: CronTaskController
 *  @author: fh
 *  @date: 2020/8/3
 *  @version : V1.0
 */
@Configuration
@RequestMapping("/cronTask")
public class CronTaskController {
    @Autowired
    private CronTaskService cronTaskService;


    @RequestMapping("/add")
    @ResponseBody
    public void addTask(CronTaskEntity cronTaskEntity){
        CronTaskEntity newEntity = new CronTaskEntity();
        newEntity.setClassName("task2");
        newEntity.setStatus(Constant.Cron.Status_Wait.getKey());
        newEntity.setIsUser(Constant.Cron.IsUser_True.getKey());
        newEntity.setCorn("0/5 * * * * ?");
        newEntity.setVersion(1);
        newEntity.setResult(Constant.Cron.Result_Success.getKey());
        cronTaskService.addTask(newEntity);
    }

    @RequestMapping("/del")
    @ResponseBody
    public void delTask(String className){
        cronTaskService.delTask("task2");
    }


    @RequestMapping("/suspend")
    @ResponseBody
    public void suspendTask(String className){
        cronTaskService.suspendTask("task2");
    }

    @RequestMapping("/runTask")
    @ResponseBody
    public void runTask(String className){
        cronTaskService.runTask("task1");
    }

    @RequestMapping("/doNow")
    @ResponseBody
    public void adas(String className){
        cronTaskService.doNow("task1");
    }
}
