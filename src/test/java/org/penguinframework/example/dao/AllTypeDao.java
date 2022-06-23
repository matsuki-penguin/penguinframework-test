package org.penguinframework.example.dao;

import java.util.List;

import org.penguinframework.example.dao.entity.AllTypeEntity;

public interface AllTypeDao {

    List<AllTypeEntity> findAll();

    AllTypeEntity findById(long identityType);

    int updateById(long identityType, AllTypeEntity allType);

    int insert(AllTypeEntity allType);
}