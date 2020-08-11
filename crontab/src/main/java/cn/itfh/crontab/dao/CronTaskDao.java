package cn.itfh.crontab.dao;

import cn.itfh.crontab.entity.CronTaskEntity;
import org.apache.ibatis.annotations.*;

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
    @Select({"<script>" +
            "select * from crontask" +
            " </script>"})
    List<CronTaskEntity> findAll();


    @Update({
            "<script>" +
                    "update crontask\n" +
                    " set Version = #{cronTaskEntity.version}, \n" +
                    "  Status = #{cronTaskEntity.status} , Result = #{cronTaskEntity.result} , ResultDesc = #{cronTaskEntity.resultDesc},\n" +
                    "  LastTime = #{cronTaskEntity.lastTime} , NextTime = #{cronTaskEntity.nextTime}" +
                    " where className = #{cronTaskEntity.className}\n" +
                    " </script>"
    })
    int updateEntity(@Param("cronTaskEntity") CronTaskEntity cronTaskEntity);

    @Update({"<script>" +
            "update crontask\n" +
            " set  \n" +
            "  Status = #{status} \n" +
            " where className = #{className}\n" +
            " </script>"})
    void updateStatus(@Param("className") String className,@Param("status") String status);
    @Select({
            "<script>" +
                    "select * from crontask" +
                    " where ClassName = #{className}" +
                    " </script>"
    })
    CronTaskEntity findByPriKey(@Param("className")String className);

    @Insert({
            "INSERT INTO crontab.crontask (ClassName, IsUser, Version, Corn, LastTime, NextTime, Status, Result, ResultDesc, Remark)\n" +
            "VALUES (#{cronTaskEntity.className}, #{cronTaskEntity.isUser}, #{cronTaskEntity.version}, #{cronTaskEntity.corn}, " +
                    "#{cronTaskEntity.lastTime}, #{cronTaskEntity.nextTime}, #{cronTaskEntity.status}, #{cronTaskEntity.result}, #{cronTaskEntity.resultDesc}, #{cronTaskEntity.remark})"
    })
    void add(@Param("cronTaskEntity") CronTaskEntity cronTaskEntity);

    @Delete({
            "delete from crontask where ClassName = #{className}"
    })
    void delByPriKey(@Param("className") String className);

    @Update({
            "<script>" +
                    "update crontask\n" +
                    " set IsUser = #{isUser} \n" +
                    " where className = #{className}\n" +
                    " </script>"
    })
    void updateIsUserByPriKey(@Param("className")String className,@Param("isUser")String isUser);
}
