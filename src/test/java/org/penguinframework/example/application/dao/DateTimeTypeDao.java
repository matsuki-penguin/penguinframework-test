package org.penguinframework.example.application.dao;

import java.util.List;

import org.penguinframework.example.application.dao.entity.DateTimeTypeEntity;

public interface DateTimeTypeDao {

    List<DateTimeTypeEntity> findAll();

    DateTimeTypeEntity findById(Integer id);

    int updateById(Integer integerType, DateTimeTypeEntity entity);

    int insert(DateTimeTypeEntity entity);

    int deleteById(Integer id);

    String[] getIgnoreCols();
}