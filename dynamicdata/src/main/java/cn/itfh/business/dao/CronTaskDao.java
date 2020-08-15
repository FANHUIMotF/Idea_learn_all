package cn.itfh.business.dao;

import cn.itfh.business.entity.CronTaskEntity;

import java.util.List;

/***
 *
 *  @className: CronTaskDao
 *  @author: fh
 *  @date: 2020/7/29
 *  @version : V1.0
 */
public interface CronTaskDao {
    /**
     * 查询所有定时任务
     *
     * @return
     */
    List<CronTaskEntity> findAll();


}
