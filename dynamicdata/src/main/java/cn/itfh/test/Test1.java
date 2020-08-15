package cn.itfh.test;

import cn.itfh.DynamicdataApplication;
import cn.itfh.business.entity.CronTaskEntity;
import cn.itfh.business.entity.Test4Entity;
import cn.itfh.business.service.CronTaskService;
import cn.itfh.business.service.Test4Service;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/***
 *
 *  @className: Test1
 *  @author: fh
 *  @date: 2020/8/11
 *  @version : V1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {DynamicdataApplication.class})
public class Test1 {
    @Autowired
    CronTaskService cronTaskService;
    @Autowired
    Test4Service test4Service;
    @Autowired
    SqlSession sqlSession;
    @Test
    public void test1(){

        List<Test4Entity> all1 = test4Service.findAll();
        all1.forEach(e-> System.out.println(e));
//        String sql = sqlSession.getConfiguration().getMappedStatement("cn.itfh.business.dao.Test4Dao.findAll").getBoundSql(null).getSql();
//        System.out.println(sql);
    }

    @Test
    public void test2(){
        List<CronTaskEntity> all = cronTaskService.findAll();
        all.forEach(e-> System.out.println(e));
    }

    @Test
    public void test3(){
        Test4Entity entity = new Test4Entity();
        entity.setId(3);
        entity.setPd("3");
        test4Service.insert(entity);
    }
}
