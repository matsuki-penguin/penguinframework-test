package org.penguinframework.example.dao;

import java.util.List;

import org.penguinframework.example.dao.entity.ProfileEntity;

public interface ProfileDao {

    List<ProfileEntity> findAll();

    ProfileEntity findById(long id);

    int insert(ProfileEntity profile);

    int updateById(long id, ProfileEntity profile);
}