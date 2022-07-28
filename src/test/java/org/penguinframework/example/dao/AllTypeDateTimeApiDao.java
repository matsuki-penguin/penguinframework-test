package org.penguinframework.example.dao;

import java.util.List;

import org.penguinframework.example.dao.entity.AllTypeDateTimeApiEntity;

public interface AllTypeDateTimeApiDao {

    List<AllTypeDateTimeApiEntity> findAll();

    AllTypeDateTimeApiEntity findById(long identityType);

    int updateById(long identityType, AllTypeDateTimeApiEntity allType);

    int insert(AllTypeDateTimeApiEntity allType);
}