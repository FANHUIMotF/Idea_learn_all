package cn.itfh.business.service.impl;

import cn.itfh.business.dao.Test4Dao;
import cn.itfh.business.entity.Test4Entity;
import cn.itfh.business.service.Test4Service;
import cn.itfh.dynamicdata.annotation.DB;
import cn.itfh.dynamicdata.datasource.DataSourceNames;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/***
 *
 *  @className: Test4ServiceImpl
 *  @author: fh
 *  @date: 2020/8/11
 *  @version : V1.0
 */
@Service("Test4Service")
public class Test4ServiceImpl implements Test4Service {
    @Autowired
    private Test4Dao test4Dao;
    @Override
    @DB(value = DataSourceNames.DB2)
    public List<Test4Entity> findAll() {
        return test4Dao.findAll();
    }

    @Override
    @DB(value = DataSourceNames.DB2)
    @Transactional
    public void insert(Test4Entity entity) {
        test4Dao.insert(entity);
    }


    @Override
    @Transactional
    @DB(value = DataSourceNames.DB2)
    public void batchInset(List<Test4Entity> list) {
        for (Test4Entity entity : list) {
            test4Dao.insert(entity);
            System.out.println("插入成功");
        }
    }
}
