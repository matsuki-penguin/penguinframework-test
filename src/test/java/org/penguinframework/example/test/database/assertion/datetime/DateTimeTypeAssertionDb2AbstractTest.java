package org.penguinframework.example.test.database.assertion.datetime;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.penguinframework.example.application.Application;
import org.penguinframework.example.application.dao.DateTimeTypeDao;
import org.penguinframework.example.application.dao.entity.DateTimeTypeEntity;
import org.penguinframework.test.annotation.Load;
import org.penguinframework.test.database.annotation.TableCsvMeta;
import org.penguinframework.test.database.annotation.TableValueSource;
import org.penguinframework.test.database.assertion.TableAssertion;
import org.penguinframework.test.extension.PenguinExtension;
import org.penguinframework.test.meta.Meta;
import org.penguinframework.test.type.OperationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;

@ExtendWith(PenguinExtension.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
abstract class DateTimeTypeAssertionDb2AbstractTest {
    @Load
    private TableAssertion tableAssertion;

    @Autowired
    private DateTimeTypeDao dateTimeTypeDao;

    abstract class ExcelFileAbstract {
        private Meta getMeta() {
            return Meta.excel().ignoreCols(Collections.singletonMap("datetime_type",
                    DateTimeTypeAssertionDb2AbstractTest.this.dateTimeTypeDao.getIgnoreCols()));
        }

        @Test
        @DisplayName("日時型の列をもつテーブルの初期状態の内容を指定されたExcelファイルの内容で検証されること。")
        void basicTypeTableAssertion() {
            DateTimeTypeAssertionDb2AbstractTest.this.tableAssertion.assertEquals("expected_datetime_type_db2.xlsx", getMeta(),
                    "datetime_type");
        }

        @Test
        @DisplayName("日時型の列をもつテーブルにExcelファイルからレコードを追加した内容を指定されたExcelファイルの内容で検証されること。")
        @TableValueSource(path = "prepare_addition_datetime_type.xlsx", operation = OperationType.INSERT)
        void basicTypeAdditionTableAssertion() {
            DateTimeTypeAssertionDb2AbstractTest.this.tableAssertion.assertEquals("expected_datetime_type_addition_db2.xlsx",
                    getMeta(), "datetime_type");
        }

        @Test
        @DisplayName("日時型の列をもつテーブルにSQLのinsert文によりレコードを追加した内容を指定されたExcelファイルの内容で検証されること。")
        void basicTypeInsertTableAssertion() {
            DateTimeTypeEntity entity = new DateTimeTypeEntity();
            entity.setIntegerType(99);
            entity.setDateType(LocalDate.of(2022, 2, 3));
            entity.setTimeType(LocalTime.of(9, 0));
            entity.setTimestampType(LocalDateTime.of(2022, 2, 3, 9, 0));
            DateTimeTypeAssertionDb2AbstractTest.this.dateTimeTypeDao.insert(entity);

            DateTimeTypeAssertionDb2AbstractTest.this.tableAssertion.assertEquals("expected_datetime_type_addition_db2.xlsx",
                    getMeta(), "datetime_type");
        }

        @Test
        @DisplayName("日時型の列をもつテーブルにSQLのupdate文によりレコードを更新した内容を指定されたExcelファイルの内容で検証されること。")
        void basicTypeUpdateTableAssertion() {
            DateTimeTypeEntity entity = new DateTimeTypeEntity();
            entity.setDateType(LocalDate.of(2022, 2, 3));
            entity.setTimeType(LocalTime.of(9, 0));
            entity.setTimestampType(LocalDateTime.of(2022, 2, 3, 9, 0));
            DateTimeTypeAssertionDb2AbstractTest.this.dateTimeTypeDao.updateById(100, entity);

            DateTimeTypeAssertionDb2AbstractTest.this.tableAssertion.assertEquals("expected_datetime_type_update_db2.xlsx",
                    getMeta(), "datetime_type");
        }

        @Test
        @DisplayName("日時型の列をもつテーブルにSQLのdelete文によりレコードを削除した内容を指定されたExcelファイルの内容で検証されること。")
        void basicTypeDeleteTableAssertion() {
            DateTimeTypeAssertionDb2AbstractTest.this.dateTimeTypeDao.deleteById(100);

            DateTimeTypeAssertionDb2AbstractTest.this.tableAssertion.assertEquals("expected_datetime_type_delete_db2.xlsx",
                    getMeta(), "datetime_type");
        }
    }

    abstract class CsvFileAbstract {
        private Meta getMeta() {
            return Meta.csv().ignoreCols(DateTimeTypeAssertionDb2AbstractTest.this.dateTimeTypeDao.getIgnoreCols());
        }

        @Test
        @DisplayName("日時型の列をもつテーブルの初期状態の内容を指定されたCSVファイルの内容で検証されること。")
        void basicTypeTableAssertion() {
            DateTimeTypeAssertionDb2AbstractTest.this.tableAssertion.assertEquals("expected_datetime_type_db2.csv", getMeta(),
                    "datetime_type");
        }

        @Test
        @DisplayName("日時型の列をもつテーブルにExcelファイルからレコードを追加した内容を指定されたExcelファイルの内容で検証されること。")
        @TableValueSource(path = "prepare_addition_datetime_type.csv", operation = OperationType.INSERT, csvMeta = @TableCsvMeta(table = "datetime_type"))
        void basicTypeAdditionTableAssertion() {
            DateTimeTypeAssertionDb2AbstractTest.this.tableAssertion.assertEquals("expected_datetime_type_addition_db2.csv",
                    getMeta(), "datetime_type");
        }

        @Test
        @DisplayName("日時型の列をもつテーブルにSQLのinsert文によりレコードを追加した内容を指定されたCSVファイルの内容で検証されること。")
        void basicTypeInsertTableAssertion() {
            DateTimeTypeEntity entity = new DateTimeTypeEntity();
            entity.setIntegerType(99);
            entity.setDateType(LocalDate.of(2022, 2, 3));
            entity.setTimeType(LocalTime.of(9, 0));
            entity.setTimestampType(LocalDateTime.of(2022, 2, 3, 9, 0));
            DateTimeTypeAssertionDb2AbstractTest.this.dateTimeTypeDao.insert(entity);

            DateTimeTypeAssertionDb2AbstractTest.this.tableAssertion.assertEquals("expected_datetime_type_addition_db2.csv",
                    getMeta(), "datetime_type");
        }

        @Test
        @DisplayName("日時型の列をもつテーブルにSQLのupdate文によりレコードを更新した内容を指定されたCSVファイルの内容で検証されること。")
        void basicTypeUpdateTableAssertion() {
            DateTimeTypeEntity entity = new DateTimeTypeEntity();
            entity.setDateType(LocalDate.of(2022, 2, 3));
            entity.setTimeType(LocalTime.of(9, 0));
            entity.setTimestampType(LocalDateTime.of(2022, 2, 3, 9, 0));
            DateTimeTypeAssertionDb2AbstractTest.this.dateTimeTypeDao.updateById(100, entity);

            DateTimeTypeAssertionDb2AbstractTest.this.tableAssertion.assertEquals("expected_datetime_type_update_db2.csv",
                    getMeta(), "datetime_type");
        }

        @Test
        @DisplayName("日時型の列をもつテーブルにSQLのdelete文によりレコードを削除した内容を指定されたCSVファイルの内容で検証されること。")
        void basicTypeDeleteTableAssertion() {
            DateTimeTypeAssertionDb2AbstractTest.this.dateTimeTypeDao.deleteById(100);

            DateTimeTypeAssertionDb2AbstractTest.this.tableAssertion.assertEquals("expected_datetime_type_delete_db2.csv",
                    getMeta(), "datetime_type");
        }
    }
}
