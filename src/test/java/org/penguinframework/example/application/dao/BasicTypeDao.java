package org.penguinframework.example.application.dao;

import java.util.List;

import org.penguinframework.example.application.dao.entity.BasicTypeEntity;

public interface BasicTypeDao {

    List<BasicTypeEntity> findAll();

    BasicTypeEntity findById(Integer id);

    int insert(BasicTypeEntity entity);

    int updateById(Integer id, BasicTypeEntity entity);

    int deleteById(Integer id);
}