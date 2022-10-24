package org.penguinframework.example.application.dao;

import java.math.BigDecimal;
import java.util.List;

import org.penguinframework.example.application.dao.entity.BasicTypeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class BasicTypeDaoImpl implements BasicTypeDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<BasicTypeEntity> findAll() {
        return this.jdbcTemplate.query(
                "select integer_type, long_type, boolean_type, float_type, double_type, biginteger_type, bigdecimal_type, string_type, byte_array_type from basic_type order by integer_type",
                new BeanPropertyRowMapper<>(BasicTypeEntity.class));
    }

    @Override
    public BasicTypeEntity findById(Integer id) {
        return this.jdbcTemplate.queryForObject(
                "select integer_type, long_type, boolean_type, float_type, double_type, biginteger_type, bigdecimal_type, string_type, byte_array_type from basic_type where integer_type = ?",
                new BeanPropertyRowMapper<>(BasicTypeEntity.class), id);
    }

    @Override
    public int insert(BasicTypeEntity entity) {
        return this.jdbcTemplate.update(
                "insert into basic_type (integer_type, long_type, boolean_type, float_type, double_type, biginteger_type, bigdecimal_type, string_type, byte_array_type) values (?, ?, ?, ?, ?, ?, ?, ?, ?)",
                entity.getIntegerType(), entity.getLongType(), entity.getBooleanType(), entity.getFloatType(),
                entity.getDoubleType(), new BigDecimal(entity.getBigintegerType()), entity.getBigdecimalType(),
                entity.getStringType(), entity.getByteArrayType());
    }

    @Override
    public int updateById(Integer id, BasicTypeEntity entity) {
        return this.jdbcTemplate.update(
                "update basic_type set long_type = ?, boolean_type = ?, float_type = ?, double_type = ?, biginteger_type = ?, bigdecimal_type = ?, string_type = ?, byte_array_type = ? where integer_type = ?",
                entity.getLongType(), entity.getBooleanType(), entity.getFloatType(), entity.getDoubleType(),
                new BigDecimal(entity.getBigintegerType()), entity.getBigdecimalType(), entity.getStringType(),
                entity.getByteArrayType(), id);
    }

    @Override
    public int deleteById(Integer id) {
        return this.jdbcTemplate.update("delete from basic_type where integer_type = ?", id);
    }
}
