package org.penguinframework.example.application.dao;

import java.util.List;

import org.penguinframework.example.application.dao.entity.DateTimeTypeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DateTimeTypeDaoImpl implements DateTimeTypeDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<DateTimeTypeEntity> findAll() {
        return this.jdbcTemplate.query(
                "select integer_type, date_type, time_type, timestamp_type from datetime_type order by integer_type",
                new BeanPropertyRowMapper<>(DateTimeTypeEntity.class));
    }

    @Override
    public DateTimeTypeEntity findById(Integer id) {
        return this.jdbcTemplate.queryForObject(
                "select integer_type, date_type, time_type, timestamp_type from datetime_type where integer_type = ?",
                new BeanPropertyRowMapper<>(DateTimeTypeEntity.class), id);
    }

    @Override
    public int updateById(Integer id, DateTimeTypeEntity entity) {
        return this.jdbcTemplate.update(
                "update datetime_type set date_type = ?, time_type = ?, timestamp_type = ? where integer_type = ?",
                entity.getDateType(), entity.getTimeType(), entity.getTimestampType(), id);
    }

    @Override
    public int insert(DateTimeTypeEntity entity) {
        return this.jdbcTemplate.update(
                "insert into datetime_type (integer_type, date_type, time_type, timestamp_type) values (?, ?, ?, ?)",
                entity.getIntegerType(), entity.getDateType(), entity.getTimeType(), entity.getTimestampType());
    }

    @Override
    public int deleteById(Integer id) {
        return this.jdbcTemplate.update("delete from datetime_type where integer_type = ?", id);
    }

    @Override
    public String[] getIgnoreCols() {
        return new String[] {};
    }
}
