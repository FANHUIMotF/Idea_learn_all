package cn.itfh.business.dao;

import cn.itfh.business.entity.Test4Entity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface Test4Dao {

    List<Test4Entity> findAll();
    void insert(@Param("entity") Test4Entity entity);
}
