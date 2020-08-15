package cn.itfh.business.controller;

import cn.itfh.business.entity.CronTaskEntity;
import cn.itfh.business.service.CronTaskService;
import cn.itfh.business.service.Test4Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/***
 *
 *  @className: BusController
 *  @author: fh
 *  @date: 2020/8/15
 *  @version : V1.0
 */
@Controller
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

}
