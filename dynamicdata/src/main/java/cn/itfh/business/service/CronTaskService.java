package cn.itfh.business.service;

import cn.itfh.business.entity.CronTaskEntity;

import java.util.List;

/***
 *
 *  @className: CronTaskService
 *  @author: fh
 *  @date: 2020/7/29
 *  @version : V1.0
 */
public interface CronTaskService {
    /**
     * 查询所有定时任务
     * @return
     */
    List<CronTaskEntity> findAll();

    void  addBatsh(List<CronTaskEntity> list);
}
