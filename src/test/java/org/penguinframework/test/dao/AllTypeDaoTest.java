package org.penguinframework.test.dao;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.penguinframework.example.Application;
import org.penguinframework.example.dao.AllTypeDao;
import org.penguinframework.example.dao.AllTypeDateTimeApiDao;
import org.penguinframework.example.dao.entity.AllTypeDateTimeApiEntity;
import org.penguinframework.example.dao.entity.AllTypeEntity;
import org.penguinframework.test.database.annotation.DatabaseMeta;
import org.penguinframework.test.database.annotation.TableValueSource;
import org.penguinframework.test.extension.PenguinExtension;
import org.penguinframework.test.support.DateTimeUtils;
import org.penguinframework.test.type.Platform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith({ SpringExtension.class, PenguinExtension.class })
@SpringBootTest(classes = Application.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("h2")
@DatabaseMeta(platform = Platform.H2)
class AllTypeDaoTest {
    @Autowired
    private AllTypeDao allTypeDao;

    @Autowired
    private AllTypeDateTimeApiDao allTypeDateTimeApiDao;

    @Test
    @DisplayName("Excelファイルによりall_typeテーブルのすべての列が初期化されること")
    @TableValueSource("prepare_all_type.xlsx")
    void testFindAllClassInit() {
        List<AllTypeEntity> result = this.allTypeDao.findAll();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());

        Assertions.assertEquals(123456789, result.get(0).getIntType());
        Assertions.assertEquals(Boolean.TRUE, result.get(0).getBooleanType());
        Assertions.assertEquals((byte) 127, result.get(0).getTinyintType());
        Assertions.assertEquals((short) 32767, result.get(0).getSmallintType());
        Assertions.assertEquals(999999999L, result.get(0).getBigintType());
        Assertions.assertEquals(1L, result.get(0).getIdentityType());
        Assertions.assertTrue(new BigDecimal("3.1415926535898").compareTo(result.get(0).getDecimalType()) == 0);
        Assertions.assertEquals(3.141592D, result.get(0).getDoubleType());
        Assertions.assertEquals(3.14F, result.get(0).getRealType());
        Assertions.assertEquals(java.sql.Time.valueOf("13:14:15"), result.get(0).getTimeType()); // 期待値のファイルには「HH:mm:ss」の文字列、もしくはExcelの場合は日付なしの時間フォーマットで記述
        Assertions.assertEquals(java.sql.Date.valueOf("2021-11-30"), result.get(0).getDateType());
        Assertions.assertEquals(java.sql.Timestamp.valueOf("2021-11-30 13:14:15"), result.get(0).getTimestampType());
        Assertions.assertArrayEquals(new byte[] { 0x41, 0x42, 0x43 }, result.get(0).getBinaryType()); // 期待値のファイルには期待値「ABC」をbase64でエンコードしたものを記述
        Assertions.assertNull(result.get(0).getOtherType());
        Assertions.assertEquals("String!", result.get(0).getVarcharType());
        Assertions.assertEquals("STRING!", result.get(0).getVarchar_ignorecaseType());
        Assertions.assertEquals('c', result.get(0).getCharType());
        Assertions.assertArrayEquals(new byte[] { 0x61, 0x62, 0x63 }, result.get(0).getBlobType()); // 期待値のファイルには期待値「abc」をbase64でエンコードしたものを記述
        Assertions.assertEquals("Character LOB", result.get(0).getClobType());
        Assertions.assertEquals(java.util.UUID.fromString("6779defb-6d49-4e2e-b3dd-95cd071cea5c"),
                result.get(0).getUuidType());
        Assertions.assertNull(result.get(0).getArrayType());
    }

    @Test
    @DisplayName("Excelファイルの様々な記述方式によりall_typeテーブルの日時列が初期化されること")
    @TableValueSource("prepare_all_type_datetime_pattern.xlsx")
    void dateTimeFormatPattern() {
        List<AllTypeEntity> result = this.allTypeDao.findAll();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(5, result.size());

        Assertions.assertEquals(Timestamp.valueOf("2021-11-30 13:14:15.167000000"), result.get(0).getTimestampType());
        Assertions.assertEquals(Timestamp.valueOf("2021-11-30 13:14:15.987654321"), result.get(1).getTimestampType());
        Assertions.assertEquals(Timestamp.valueOf("2021-11-30 13:14:15.167000000"), result.get(2).getTimestampType());
        Assertions.assertEquals(Timestamp.valueOf("2021-11-30 13:14:15.000000000"), result.get(3).getTimestampType());
        Assertions.assertEquals(Timestamp.valueOf("2021-11-30 13:14:00.000000000"), result.get(4).getTimestampType());
    }

    @Test
    @DisplayName("Excelファイルの様々な記述方式によりall_typeテーブルの時間列がナノ秒精度されて初期化され、java.sql.Timeで受け取るとミリ秒精度で取得できること")
    @TableValueSource("prepare_all_type_time_pattern.xlsx")
    void timeFormatPattern() {
        List<AllTypeEntity> result = this.allTypeDao.findAll();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(5, result.size());

        Assertions.assertEquals(DateTimeUtils.toSqlTime("13:14:15.167"), result.get(0).getTimeType());
        Assertions.assertEquals(DateTimeUtils.toSqlTime("13:14:15.987"), result.get(1).getTimeType());
        Assertions.assertEquals(DateTimeUtils.toSqlTime("13:14:15.167"), result.get(2).getTimeType());
        Assertions.assertEquals(DateTimeUtils.toSqlTime("13:14:15"), result.get(3).getTimeType());
        Assertions.assertEquals(DateTimeUtils.toSqlTime("13:14:00"), result.get(4).getTimeType());
    }

    @Test
    @DisplayName("Excelファイルの様々な記述方式によりall_typeテーブルの時間列がナノ秒精度されて初期化され、LocalTimeで受け取るとナノ秒精度で取得できること")
    @TableValueSource("prepare_all_type_time_pattern.xlsx")
    void localtimeFormatPattern() {
        List<AllTypeDateTimeApiEntity> result = this.allTypeDateTimeApiDao.findAll();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(5, result.size());

        Assertions.assertEquals(DateTimeUtils.toLocalTime("13:14:15.167"), result.get(0).getTimeType());
        Assertions.assertEquals(DateTimeUtils.toLocalTime("13:14:15.987654321"), result.get(1).getTimeType());
        Assertions.assertEquals(DateTimeUtils.toLocalTime("13:14:15.167"), result.get(2).getTimeType());
        Assertions.assertEquals(DateTimeUtils.toLocalTime("13:14:15"), result.get(3).getTimeType());
        Assertions.assertEquals(DateTimeUtils.toLocalTime("13:14:00"), result.get(4).getTimeType());
    }

    @Test
    @DisplayName("Excelファイルの様々な記述方式によりall_typeテーブルの日付列が初期化されること")
    @TableValueSource("prepare_all_type_date_pattern.xlsx")
    void dateFormatPattern() {
        List<AllTypeEntity> result = this.allTypeDao.findAll();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(3, result.size());

        Assertions.assertEquals(java.sql.Date.valueOf("2021-11-30"), result.get(0).getDateType());
        Assertions.assertEquals(java.sql.Date.valueOf("2021-01-02"), result.get(1).getDateType());
        Assertions.assertEquals(java.sql.Date.valueOf("2021-01-02"), result.get(2).getDateType());
    }
}
