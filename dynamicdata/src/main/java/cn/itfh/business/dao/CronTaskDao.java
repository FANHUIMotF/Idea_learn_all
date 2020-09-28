package cn.itfh.business.dao;

import cn.itfh.business.entity.CronTaskEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

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

    @Insert({
            "INSERT INTO crontab.crontask (ClassName, IsUser, Version, Corn, LastTime, NextTime, Status, Result, ResultDesc, Remark)\n" +
                    "VALUES (#{cronTaskEntity.className}, #{cronTaskEntity.isUser}, #{cronTaskEntity.version}, #{cronTaskEntity.corn}, " +
                    "#{cronTaskEntity.lastTime}, #{cronTaskEntity.nextTime}, #{cronTaskEntity.status}, #{cronTaskEntity.result}, #{cronTaskEntity.resultDesc}, #{cronTaskEntity.remark})"
    })
    void add(@Param("cronTaskEntity") CronTaskEntity cronTaskEntity);

}
