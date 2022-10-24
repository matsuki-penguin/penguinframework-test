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
import org.penguinframework.test.database.annotation.TableCsvMeta;
import org.penguinframework.test.database.annotation.TableValueSource;
import org.penguinframework.test.extension.PenguinExtension;
import org.penguinframework.test.type.OperationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;

@ExtendWith(PenguinExtension.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
abstract class DateTimeTypeInitAbstractTest {
    @Autowired
    protected DateTimeTypeDao dateTimeTypeDao;

    abstract class ExcelFileAbstract {
        @TableValueSource("prepare_datetime_type.xlsx")
        abstract class ClassInitAbstractTest {
            @Test
            @DisplayName("日時型の列をもつテーブルがクラスに指定されたExcelファイルの内容で初期化されること。")
            void dateTimeTypeTableClassInit() {
                List<DateTimeTypeEntity> entities = DateTimeTypeInitAbstractTest.this.dateTimeTypeDao.findAll();

                Assertions.assertNotNull(entities);
                Assertions.assertEquals(2, entities.size());

                DateTimeTypeEntity entity = entities.get(0);
                Assertions.assertEquals(1, entity.getIntegerType());
                Assertions.assertEquals(toDatabaseScaleDate(LocalDate.of(2022, 1, 20)), entity.getDateType());
                Assertions.assertEquals(toDatabaseScaleTime(LocalTime.of(15, 16, 17, 987000000)), entity.getTimeType());
                Assertions.assertEquals(toDatabaseScaleDateTime(LocalDateTime.of(2022, 1, 20, 15, 16, 17, 987000000)),
                        entity.getTimestampType());

                entity = entities.get(1);
                Assertions.assertEquals(2, entity.getIntegerType());
                Assertions.assertEquals(toDatabaseScaleDate(LocalDate.of(2022, 1, 20)), entity.getDateType());
                Assertions.assertEquals(toDatabaseScaleTime(LocalTime.of(15, 16, 17, 123456789)), entity.getTimeType());
                Assertions.assertEquals(toDatabaseScaleDateTime(LocalDateTime.of(2022, 1, 20, 15, 16, 17, 123456789)),
                        entity.getTimestampType());
            }

            @Test
            @DisplayName("日時型の列をもつテーブルがメソッドに指定されたExcelファイルの内容で初期化されること。")
            @TableValueSource("prepare_addition_datetime_type.xlsx")
            void dateTimeTypeTableMethodInit() {
                List<DateTimeTypeEntity> entities = DateTimeTypeInitAbstractTest.this.dateTimeTypeDao.findAll();

                Assertions.assertNotNull(entities);
                Assertions.assertEquals(1, entities.size());

                DateTimeTypeEntity entity = entities.get(0);
                Assertions.assertEquals(99, entity.getIntegerType());
                Assertions.assertEquals(toDatabaseScaleDate(LocalDate.of(2022, 2, 3)), entity.getDateType());
                Assertions.assertEquals(toDatabaseScaleTime(LocalTime.of(9, 0)), entity.getTimeType());
                Assertions.assertEquals(toDatabaseScaleDateTime(LocalDateTime.of(2022, 2, 3, 9, 0)),
                        entity.getTimestampType());
            }

            @Test
            @DisplayName("日時型の列をもつテーブルがクラスとメソッドに指定されたExcelファイルの内容で初期化されること。")
            @TableValueSource(path = "prepare_addition_datetime_type.xlsx", operation = OperationType.INSERT)
            void dateTimeTypeTableClassAndMethodInit() {
                List<DateTimeTypeEntity> entities = DateTimeTypeInitAbstractTest.this.dateTimeTypeDao.findAll();

                Assertions.assertNotNull(entities);
                Assertions.assertEquals(3, entities.size());

                DateTimeTypeEntity entity = entities.get(0);
                Assertions.assertEquals(1, entity.getIntegerType());
                Assertions.assertEquals(toDatabaseScaleDate(LocalDate.of(2022, 1, 20)), entity.getDateType());
                Assertions.assertEquals(toDatabaseScaleTime(LocalTime.of(15, 16, 17, 987000000)), entity.getTimeType());
                Assertions.assertEquals(toDatabaseScaleDateTime(LocalDateTime.of(2022, 1, 20, 15, 16, 17, 987000000)),
                        entity.getTimestampType());

                entity = entities.get(1);
                Assertions.assertEquals(2, entity.getIntegerType());
                Assertions.assertEquals(toDatabaseScaleDate(LocalDate.of(2022, 1, 20)), entity.getDateType());
                Assertions.assertEquals(toDatabaseScaleTime(LocalTime.of(15, 16, 17, 123456789)), entity.getTimeType());
                Assertions.assertEquals(toDatabaseScaleDateTime(LocalDateTime.of(2022, 1, 20, 15, 16, 17, 123456789)),
                        entity.getTimestampType());

                entity = entities.get(2);
                Assertions.assertEquals(99, entity.getIntegerType());
                Assertions.assertEquals(toDatabaseScaleDate(LocalDate.of(2022, 2, 3)), entity.getDateType());
                Assertions.assertEquals(toDatabaseScaleTime(LocalTime.of(9, 0)), entity.getTimeType());
                Assertions.assertEquals(toDatabaseScaleDateTime(LocalDateTime.of(2022, 2, 3, 9, 0)),
                        entity.getTimestampType());
            }
        }

        abstract class ClassNoInitAbstractTest {

            @Test
            @DisplayName("日時型の列をもつテーブルの既存データが削除されないこと。")
            void basicTypeTableClassInit() {
                List<DateTimeTypeEntity> entities = DateTimeTypeInitAbstractTest.this.dateTimeTypeDao.findAll();

                Assertions.assertNotNull(entities);
                Assertions.assertEquals(1, entities.size());

                DateTimeTypeEntity entity = entities.get(0);
                Assertions.assertEquals(100, entity.getIntegerType());
                Assertions.assertEquals(toDatabaseScaleDate(LocalDate.of(2020, 12, 24)), entity.getDateType());
                Assertions.assertEquals(toDatabaseScaleTime(LocalTime.of(1, 23, 4, 567890123)), entity.getTimeType());
                Assertions.assertEquals(toDatabaseScaleDateTime(LocalDateTime.of(2020, 12, 24, 1, 23, 4, 567890123)),
                        entity.getTimestampType());
            }

            @Test
            @DisplayName("日時型の列をもつテーブルの既存データにメソッドに指定されたExcelファイルの内容が追加されること。")
            @TableValueSource(path = "prepare_addition_datetime_type.xlsx", operation = OperationType.INSERT)
            void basicTypeTableClassAndMethodInit() {
                List<DateTimeTypeEntity> entities = DateTimeTypeInitAbstractTest.this.dateTimeTypeDao.findAll();

                Assertions.assertNotNull(entities);
                Assertions.assertEquals(2, entities.size());

                DateTimeTypeEntity entity = entities.get(0);
                Assertions.assertEquals(99, entity.getIntegerType());
                Assertions.assertEquals(toDatabaseScaleDate(LocalDate.of(2022, 2, 3)), entity.getDateType());
                Assertions.assertEquals(toDatabaseScaleTime(LocalTime.of(9, 0)), entity.getTimeType());
                Assertions.assertEquals(toDatabaseScaleDateTime(LocalDateTime.of(2022, 2, 3, 9, 0)),
                        entity.getTimestampType());

                entity = entities.get(1);
                Assertions.assertEquals(100, entity.getIntegerType());
                Assertions.assertEquals(toDatabaseScaleDate(LocalDate.of(2020, 12, 24)), entity.getDateType());
                Assertions.assertEquals(toDatabaseScaleTime(LocalTime.of(1, 23, 4, 567890123)), entity.getTimeType());
                Assertions.assertEquals(toDatabaseScaleDateTime(LocalDateTime.of(2020, 12, 24, 1, 23, 4, 567890123)),
                        entity.getTimestampType());
            }
        }
    }

    abstract class CsvFileAbstract {
        @TableValueSource(path = "prepare_datetime_type.csv", csvMeta = @TableCsvMeta(table = "datetime_type"))
        abstract class ClassInitAbstractTest {
            @Test
            @DisplayName("日時型の列をもつテーブルがクラスに指定されたExcelファイルの内容で初期化されること。")
            void dateTimeTypeTableClassInit() {
                List<DateTimeTypeEntity> entities = DateTimeTypeInitAbstractTest.this.dateTimeTypeDao.findAll();

                Assertions.assertNotNull(entities);
                Assertions.assertEquals(2, entities.size());

                DateTimeTypeEntity entity = entities.get(0);
                Assertions.assertEquals(1, entity.getIntegerType());
                Assertions.assertEquals(toDatabaseScaleDate(LocalDate.of(2022, 1, 20)), entity.getDateType());
                Assertions.assertEquals(toDatabaseScaleTime(LocalTime.of(15, 16, 17, 987000000)), entity.getTimeType());
                Assertions.assertEquals(toDatabaseScaleDateTime(LocalDateTime.of(2022, 1, 20, 15, 16, 17, 987000000)),
                        entity.getTimestampType());

                entity = entities.get(1);
                Assertions.assertEquals(2, entity.getIntegerType());
                Assertions.assertEquals(toDatabaseScaleDate(LocalDate.of(2022, 1, 20)), entity.getDateType());
                Assertions.assertEquals(toDatabaseScaleTime(LocalTime.of(15, 16, 17, 123456789)), entity.getTimeType());
                Assertions.assertEquals(toDatabaseScaleDateTime(LocalDateTime.of(2022, 1, 20, 15, 16, 17, 123456789)),
                        entity.getTimestampType());
            }

            @Test
            @DisplayName("日時型の列をもつテーブルがメソッドに指定されたExcelファイルの内容で初期化されること。")
            @TableValueSource(path = "prepare_addition_datetime_type.csv", csvMeta = @TableCsvMeta(table = "datetime_type"))
            void dateTimeTypeTableMethodInit() {
                List<DateTimeTypeEntity> entities = DateTimeTypeInitAbstractTest.this.dateTimeTypeDao.findAll();

                Assertions.assertNotNull(entities);
                Assertions.assertEquals(1, entities.size());

                DateTimeTypeEntity entity = entities.get(0);
                Assertions.assertEquals(99, entity.getIntegerType());
                Assertions.assertEquals(toDatabaseScaleDate(LocalDate.of(2022, 2, 3)), entity.getDateType());
                Assertions.assertEquals(toDatabaseScaleTime(LocalTime.of(9, 0)), entity.getTimeType());
                Assertions.assertEquals(toDatabaseScaleDateTime(LocalDateTime.of(2022, 2, 3, 9, 0)),
                        entity.getTimestampType());
            }

            @Test
            @DisplayName("日時型の列をもつテーブルがクラスとメソッドに指定されたExcelファイルの内容で初期化されること。")
            @TableValueSource(path = "prepare_addition_datetime_type.csv", csvMeta = @TableCsvMeta(table = "datetime_type"), operation = OperationType.INSERT)
            void dateTimeTypeTableClassAndMethodInit() {
                List<DateTimeTypeEntity> entities = DateTimeTypeInitAbstractTest.this.dateTimeTypeDao.findAll();

                Assertions.assertNotNull(entities);
                Assertions.assertEquals(3, entities.size());

                DateTimeTypeEntity entity = entities.get(0);
                Assertions.assertEquals(1, entity.getIntegerType());
                Assertions.assertEquals(toDatabaseScaleDate(LocalDate.of(2022, 1, 20)), entity.getDateType());
                Assertions.assertEquals(toDatabaseScaleTime(LocalTime.of(15, 16, 17, 987000000)), entity.getTimeType());
                Assertions.assertEquals(toDatabaseScaleDateTime(LocalDateTime.of(2022, 1, 20, 15, 16, 17, 987000000)),
                        entity.getTimestampType());

                entity = entities.get(1);
                Assertions.assertEquals(2, entity.getIntegerType());
                Assertions.assertEquals(toDatabaseScaleDate(LocalDate.of(2022, 1, 20)), entity.getDateType());
                Assertions.assertEquals(toDatabaseScaleTime(LocalTime.of(15, 16, 17, 123456789)), entity.getTimeType());
                Assertions.assertEquals(toDatabaseScaleDateTime(LocalDateTime.of(2022, 1, 20, 15, 16, 17, 123456789)),
                        entity.getTimestampType());

                entity = entities.get(2);
                Assertions.assertEquals(99, entity.getIntegerType());
                Assertions.assertEquals(toDatabaseScaleDate(LocalDate.of(2022, 2, 3)), entity.getDateType());
                Assertions.assertEquals(toDatabaseScaleTime(LocalTime.of(9, 0)), entity.getTimeType());
                Assertions.assertEquals(toDatabaseScaleDateTime(LocalDateTime.of(2022, 2, 3, 9, 0)),
                        entity.getTimestampType());
            }
        }

        abstract class ClassNoInitAbstractTest {

            @Test
            @DisplayName("日時型の列をもつテーブルの既存データが削除されないこと。")
            void basicTypeTableClassInit() {
                List<DateTimeTypeEntity> entities = DateTimeTypeInitAbstractTest.this.dateTimeTypeDao.findAll();

                Assertions.assertNotNull(entities);
                Assertions.assertEquals(1, entities.size());

                DateTimeTypeEntity entity = entities.get(0);
                Assertions.assertEquals(100, entity.getIntegerType());
                Assertions.assertEquals(toDatabaseScaleDate(LocalDate.of(2020, 12, 24)), entity.getDateType());
                Assertions.assertEquals(toDatabaseScaleTime(LocalTime.of(1, 23, 4, 567890123)), entity.getTimeType());
                Assertions.assertEquals(toDatabaseScaleDateTime(LocalDateTime.of(2020, 12, 24, 1, 23, 4, 567890123)),
                        entity.getTimestampType());
            }

            @Test
            @DisplayName("日時型の列をもつテーブルの既存データにメソッドに指定されたExcelファイルの内容が追加されること。")
            @TableValueSource(path = "prepare_addition_datetime_type.csv", csvMeta = @TableCsvMeta(table = "datetime_type"), operation = OperationType.INSERT)
            void basicTypeTableClassAndMethodInit() {
                List<DateTimeTypeEntity> entities = DateTimeTypeInitAbstractTest.this.dateTimeTypeDao.findAll();

                Assertions.assertNotNull(entities);
                Assertions.assertEquals(2, entities.size());

                DateTimeTypeEntity entity = entities.get(0);
                Assertions.assertEquals(99, entity.getIntegerType());
                Assertions.assertEquals(toDatabaseScaleDate(LocalDate.of(2022, 2, 3)), entity.getDateType());
                Assertions.assertEquals(toDatabaseScaleTime(LocalTime.of(9, 0)), entity.getTimeType());
                Assertions.assertEquals(toDatabaseScaleDateTime(LocalDateTime.of(2022, 2, 3, 9, 0)),
                        entity.getTimestampType());

                entity = entities.get(1);
                Assertions.assertEquals(100, entity.getIntegerType());
                Assertions.assertEquals(toDatabaseScaleDate(LocalDate.of(2020, 12, 24)), entity.getDateType());
                Assertions.assertEquals(toDatabaseScaleTime(LocalTime.of(1, 23, 4, 567890123)), entity.getTimeType());
                Assertions.assertEquals(toDatabaseScaleDateTime(LocalDateTime.of(2020, 12, 24, 1, 23, 4, 567890123)),
                        entity.getTimestampType());
            }
        }
    }

    abstract LocalDate toDatabaseScaleDate(LocalDate from);

    abstract LocalTime toDatabaseScaleTime(LocalTime from);

    abstract LocalDateTime toDatabaseScaleDateTime(LocalDateTime from);
}
