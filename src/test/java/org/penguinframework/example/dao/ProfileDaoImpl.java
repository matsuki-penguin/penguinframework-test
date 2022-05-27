package org.penguinframework.example.dao;

import java.util.List;

import org.penguinframework.example.dao.entity.ProfileEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ProfileDaoImpl implements ProfileDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<ProfileEntity> findAll() {
        return this.jdbcTemplate.query("select id, name, birthday from profile",
                new BeanPropertyRowMapper<>(ProfileEntity.class));
    }

    @Override
    public ProfileEntity findById(long id) {
        return this.jdbcTemplate.queryForObject("select id, name, birthday from profile where id = ?",
                new BeanPropertyRowMapper<>(ProfileEntity.class), id);
    }

    @Override
    public int updateById(long id, ProfileEntity profile) {
        return this.jdbcTemplate.update("update profile set name = ?, birthday = ? where id = ?", profile.getName(),
                profile.getBirthday(), id);
    }
}
