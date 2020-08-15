package cn.itfh.business.service;

import cn.itfh.business.entity.Test4Entity;

import java.util.List;

/***
 *
 *  @className: Test4Service
 *  @author: fh
 *  @date: 2020/8/11
 *  @version : V1.0
 */
public interface Test4Service {
    List<Test4Entity> findAll();

    void insert(Test4Entity entity);
}
