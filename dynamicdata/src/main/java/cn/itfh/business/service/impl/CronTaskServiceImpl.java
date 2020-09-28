package cn.itfh.business.service.impl;

import cn.itfh.business.dao.CronTaskDao;
import cn.itfh.business.entity.CronTaskEntity;
import cn.itfh.business.service.CronTaskService;
import cn.itfh.dynamicdata.annotation.DB;
import cn.itfh.dynamicdata.datasource.DataSourceNames;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/***
 *
 *  @className: CronTaskServiceImpl
 *  @author: fh
 *  @date: 2020/7/29
 *  @version : V1.0
 */
@Service("cronTaskService")
@Slf4j
public class CronTaskServiceImpl implements CronTaskService {
    @Autowired
    CronTaskDao cronTaskDao;


    @Override
    @DB(value = DataSourceNames.DB1)
    public List<CronTaskEntity> findAll() {
        return cronTaskDao.findAll();
    }

    @Override
    @DB(value = DataSourceNames.DB1)
    @Transactional
    public void addBatsh(List<CronTaskEntity> list) {
        list.forEach(entity ->
                cronTaskDao.add(entity)
        );
    }
}
