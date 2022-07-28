package org.penguinframework.example.dao;

import java.util.List;

import org.penguinframework.example.dao.entity.AllTypeDateTimeApiEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AllTypeDateTimeApiDaoImpl implements AllTypeDateTimeApiDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<AllTypeDateTimeApiEntity> findAll() {
        return this.jdbcTemplate.query(
                "select int_type, boolean_type, tinyint_type, smallint_type, bigint_type, identity_type, decimal_type, double_type, real_type, time_type, date_type, timestamp_type, binary_type, other_type, varchar_type, varchar_ignorecase_type, char_type, blob_type, clob_type, uuid_type, array_type from all_type",
                new BeanPropertyRowMapper<>(AllTypeDateTimeApiEntity.class));
    }

    @Override
    public AllTypeDateTimeApiEntity findById(long identityType) {
        return this.jdbcTemplate.queryForObject(
                "select int_type, boolean_type, tinyint_type, smallint_type, bigint_type, identity_type, decimal_type, double_type, real_type, time_type, date_type, timestamp_type, binary_type, other_type, varchar_type, varchar_ignorecase_type, char_type, blob_type, clob_type, uuid_type, array_type from all_type where identity_type = ?",
                new BeanPropertyRowMapper<>(AllTypeDateTimeApiEntity.class), identityType);
    }

    @Override
    public int updateById(long identityType, AllTypeDateTimeApiEntity allType) {
        return this.jdbcTemplate.update(
                "update all_type set int_type = ?, boolean_type = ?, tinyint_type = ?, smallint_type = ?, bigint_type = ?, decimal_type = ?, double_type = ?, real_type = ?, time_type = ?, date_type = ?, timestamp_type = ?, binary_type = ?, other_type = ?, varchar_type = ?, varchar_ignorecase_type = ?, char_type = ?, blob_type = ?, clob_type = ?, uuid_type = ?, array_type = ? where identity_type = ?",
                allType.getIntType(), allType.getBooleanType(), allType.getTinyintType(), allType.getSmallintType(),
                allType.getBigintType(), allType.getDecimalType(), allType.getDoubleType(), allType.getRealType(),
                allType.getTimeType(), allType.getDateType(), allType.getTimestampType(), allType.getBinaryType(),
                allType.getOtherType(), allType.getVarcharType(), allType.getVarchar_ignorecaseType(),
                allType.getCharType(), allType.getBlobType(), allType.getClobType(), allType.getUuidType(),
                allType.getArrayType(), identityType);
    }

    @Override
    public int insert(AllTypeDateTimeApiEntity allType) {
        return this.jdbcTemplate.update(
                "insert into all_type (int_type, boolean_type, tinyint_type, smallint_type, bigint_type, identity_type, decimal_type, double_type, real_type, time_type, date_type, timestamp_type, binary_type, other_type, varchar_type, varchar_ignorecase_type, char_type, blob_type, clob_type, uuid_type, array_type) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                allType.getIntType(), allType.getBooleanType(), allType.getTinyintType(), allType.getSmallintType(),
                allType.getBigintType(), allType.getIdentityType(), allType.getDecimalType(), allType.getDoubleType(),
                allType.getRealType(), allType.getTimeType(), allType.getDateType(), allType.getTimestampType(),
                allType.getBinaryType(), allType.getOtherType(), allType.getVarcharType(),
                allType.getVarchar_ignorecaseType(), allType.getCharType(), allType.getBlobType(),
                allType.getClobType(), allType.getUuidType(), allType.getArrayType());
    }
}
