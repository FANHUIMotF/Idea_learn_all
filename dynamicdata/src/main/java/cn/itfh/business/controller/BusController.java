package cn.itfh.business.controller;

import cn.itfh.business.entity.CronTaskEntity;
import cn.itfh.business.entity.Test4Entity;
import cn.itfh.business.service.CronTaskService;
import cn.itfh.business.service.Test4Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 *
 *  @className: BusController
 *  @author: fh
 *  @date: 2020/8/15
 *  @version : V1.0
 */
@RestController
public class BusController {
    @Autowired
    CronTaskService cronTaskService;
    @Autowired
    Test4Service test4Service;
    @RequestMapping("/test1")
    public void test1(){
        List<CronTaskEntity> all = cronTaskService.findAll();
        all.forEach(e-> System.out.println(e));
    }

    @RequestMapping("/test2")
    public Map test2(){
        List<CronTaskEntity> list = new ArrayList<>();
        CronTaskEntity newEntity = new CronTaskEntity();
        newEntity.setClassName("task3");
        newEntity.setStatus("1");
        newEntity.setIsUser("1");
        newEntity.setCorn("0/5 * * * * ?");
        newEntity.setVersion(1);
        newEntity.setResult("1");

        CronTaskEntity newEntity2 = new CronTaskEntity();
        newEntity2.setClassName("task3");
        newEntity2.setStatus("1");
        newEntity2.setIsUser("1");
        newEntity2.setCorn("0/5 * * * * ?");
        newEntity2.setVersion(1);
        newEntity2.setResult("1");

        list.add(newEntity);
        list.add(newEntity2);
        cronTaskService.addBatsh(list);
        return new HashMap();
    }

    @RequestMapping("/test3")
    public Map test3(){
        List<Test4Entity> list = new ArrayList<>();
        Test4Entity entity = new Test4Entity();
        entity.setId(6);
        entity.setPd("6");
        Test4Entity entity2 = new Test4Entity();
        entity2.setId(6);
        entity2.setPd("6");
        Test4Entity entity3 = new Test4Entity();
        entity3.setId(3);
        entity3.setPd("3");
        list.add(entity);
        list.add(entity2);
        list.add(entity3);
        test4Service.batchInset(list);
        return new HashMap();
    }

}
