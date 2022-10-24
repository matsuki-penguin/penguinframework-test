package org.penguinframework.example.test.database.init.datetime;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.penguinframework.example.application.Application;
import org.penguinframework.example.application.dao.DateTimeTypeDao;
import org.penguinframework.example.application.dao.entity.DateTimeTypeEntity;
import org.penguinframework.test.database.annotation.DatabaseMeta;
import org.penguinframework.test.database.annotation.TableValueSource;
import org.penguinframework.test.extension.PenguinExtension;
import org.penguinframework.test.type.Platform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ExtendWith(PenguinExtension.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("h2")
@DatabaseMeta(platform = Platform.H2)
class DateTimeInitPatternTest {
    @Autowired
    protected DateTimeTypeDao dateTimeTypeDao;

    @Test
    @DisplayName("Excelファイルの様々な記述方式によりall_typeテーブルの日時列が初期化されること")
    @TableValueSource("prepare_all_type_datetime_pattern.xlsx")
    void dateTimeFormatPattern() {
        List<DateTimeTypeEntity> entities = this.dateTimeTypeDao.findAll();

        Assertions.assertNotNull(entities);
        Assertions.assertEquals(5, entities.size());

        Assertions.assertEquals(LocalDateTime.of(2021, 11, 30, 13, 14, 15, 167000000),
                entities.get(0).getTimestampType());
        Assertions.assertEquals(LocalDateTime.of(2021, 11, 30, 13, 14, 15, 987654321),
                entities.get(1).getTimestampType());
        Assertions.assertEquals(LocalDateTime.of(2021, 11, 30, 13, 14, 15, 167000000),
                entities.get(2).getTimestampType());
        Assertions.assertEquals(LocalDateTime.of(2021, 11, 30, 13, 14, 15), entities.get(3).getTimestampType());
        Assertions.assertEquals(LocalDateTime.of(2021, 11, 30, 13, 14), entities.get(4).getTimestampType());
    }

    @Test
    @DisplayName("Excelファイルの様々な記述方式によりall_typeテーブルの時間列がナノ秒精度されて初期化され、LocalTimeで受け取るとナノ秒精度で取得できること")
    @TableValueSource("prepare_all_type_time_pattern.xlsx")
    void localtimeFormatPattern() {
        List<DateTimeTypeEntity> entities = this.dateTimeTypeDao.findAll();

        Assertions.assertNotNull(entities);
        Assertions.assertEquals(5, entities.size());

        Assertions.assertEquals(LocalTime.of(13, 14, 15, 167000000), entities.get(0).getTimeType());
        Assertions.assertEquals(LocalTime.of(13, 14, 15, 987654321), entities.get(1).getTimeType());
        Assertions.assertEquals(LocalTime.of(13, 14, 15, 167000000), entities.get(2).getTimeType());
        Assertions.assertEquals(LocalTime.of(13, 14, 15), entities.get(3).getTimeType());
        Assertions.assertEquals(LocalTime.of(13, 14), entities.get(4).getTimeType());
    }

    @Test
    @DisplayName("Excelファイルの様々な記述方式によりall_typeテーブルの日付列が初期化されること")
    @TableValueSource("prepare_all_type_date_pattern.xlsx")
    void dateFormatPattern() {
        List<DateTimeTypeEntity> entities = this.dateTimeTypeDao.findAll();

        Assertions.assertNotNull(entities);
        Assertions.assertEquals(3, entities.size());

        Assertions.assertEquals(LocalDate.of(2021, 11, 30), entities.get(0).getDateType());
        Assertions.assertEquals(LocalDate.of(2021, 01, 02), entities.get(1).getDateType());
        Assertions.assertEquals(LocalDate.of(2021, 01, 02), entities.get(2).getDateType());
    }
}
