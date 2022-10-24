package org.penguinframework.example.test.database.assertion.basic;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.penguinframework.example.application.Application;
import org.penguinframework.example.application.dao.BasicTypeDao;
import org.penguinframework.example.application.dao.entity.BasicTypeEntity;
import org.penguinframework.test.annotation.Load;
import org.penguinframework.test.database.annotation.TableCsvMeta;
import org.penguinframework.test.database.annotation.TableValueSource;
import org.penguinframework.test.database.assertion.TableAssertion;
import org.penguinframework.test.extension.PenguinExtension;
import org.penguinframework.test.type.OperationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;

@ExtendWith(PenguinExtension.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
abstract class BasicTypeAssertionAbstractTest {
    @Load
    private TableAssertion tableAssertion;

    @Autowired
    private BasicTypeDao basicTypeDao;

    abstract class ExcelFileAbstract {
        @Test
        @DisplayName("基本的なDB型の列をもつテーブルの初期状態の内容を指定されたExcelファイルの内容で検証されること。")
        void basicTypeTableAssertion() {
            BasicTypeAssertionAbstractTest.this.tableAssertion.assertEquals("expected_basic_type.xlsx", "basic_type");
        }

        @Test
        @DisplayName("基本的なDB型の列をもつテーブルにExcelファイルからレコードを追加した内容を指定されたExcelファイルの内容で検証されること。")
        @TableValueSource(path = "prepare_addition_basic_type.xlsx", operation = OperationType.INSERT)
        void basicTypeAdditionTableAssertion() {
            BasicTypeAssertionAbstractTest.this.tableAssertion.assertEquals("expected_basic_type_addition.xlsx", "basic_type");
        }

        @Test
        @DisplayName("基本的なDB型の列をもつテーブルにSQLのinsert文によりレコードを追加した内容を指定されたExcelファイルの内容で検証されること。")
        void basicTypeInsertTableAssertion() {
            BasicTypeEntity entity = new BasicTypeEntity();
            entity.setIntegerType(99);
            entity.setLongType(123456789012345678L);
            entity.setBooleanType(true);
            entity.setFloatType(1.41421356D);
            entity.setDoubleType(1.41421356237D);
            entity.setBigintegerType(new BigInteger("123456789012345678901234567890"));
            entity.setBigdecimalType(new BigDecimal("-1.41421356237"));
            entity.setStringType("add!");
            entity.setByteArrayType(new byte[] { 0x61, 0x62, 0x63 });
            BasicTypeAssertionAbstractTest.this.basicTypeDao.insert(entity);

            BasicTypeAssertionAbstractTest.this.tableAssertion.assertEquals("expected_basic_type_addition.xlsx", "basic_type");
        }

        @Test
        @DisplayName("基本的なDB型の列をもつテーブルにSQLのupdate文によりレコードを更新した内容を指定されたExcelファイルの内容で検証されること。")
        void basicTypeUpdateTableAssertion() {
            BasicTypeEntity entity = new BasicTypeEntity();
            entity.setLongType(123456789012345678L);
            entity.setBooleanType(true);
            entity.setFloatType(1.41421356D);
            entity.setDoubleType(1.41421356237D);
            entity.setBigintegerType(new BigInteger("123456789012345678901234567890"));
            entity.setBigdecimalType(new BigDecimal("-1.41421356237"));
            entity.setStringType("add!");
            entity.setByteArrayType(new byte[] { 0x61, 0x62, 0x63 });
            BasicTypeAssertionAbstractTest.this.basicTypeDao.updateById(100, entity);

            BasicTypeAssertionAbstractTest.this.tableAssertion.assertEquals("expected_basic_type_update.xlsx", "basic_type");
        }

        @Test
        @DisplayName("基本的なDB型の列をもつテーブルにSQLのdelete文によりレコードを削除した内容を指定されたExcelファイルの内容で検証されること。")
        void basicTypeDeleteTableAssertion() {
            BasicTypeAssertionAbstractTest.this.basicTypeDao.deleteById(100);

            BasicTypeAssertionAbstractTest.this.tableAssertion.assertEquals("expected_basic_type_delete.xlsx", "basic_type");
        }
    }

    abstract class CsvFileAbstract {
        @Test
        @DisplayName("基本的なDB型の列をもつテーブルの初期状態の内容を指定されたCSVファイルの内容で検証されること。")
        void basicTypeTableAssertion() {
            BasicTypeAssertionAbstractTest.this.tableAssertion.assertEquals("expected_basic_type.csv", "basic_type");
        }

        @Test
        @DisplayName("基本的なDB型の列をもつテーブルにExcelファイルからレコードを追加した内容を指定されたExcelファイルの内容で検証されること。")
        @TableValueSource(path = "prepare_addition_basic_type.csv", operation = OperationType.INSERT, csvMeta = @TableCsvMeta(table = "basic_type"))
        void basicTypeAdditionTableAssertion() {
            BasicTypeAssertionAbstractTest.this.tableAssertion.assertEquals("expected_basic_type_addition.csv", "basic_type");
        }

        @Test
        @DisplayName("基本的なDB型の列をもつテーブルにSQLのinsert文によりレコードを追加した内容を指定されたCSVファイルの内容で検証されること。")
        void basicTypeInsertTableAssertion() {
            BasicTypeEntity entity = new BasicTypeEntity();
            entity.setIntegerType(99);
            entity.setLongType(123456789012345678L);
            entity.setBooleanType(true);
            entity.setFloatType(1.41421356D);
            entity.setDoubleType(1.41421356237D);
            entity.setBigintegerType(new BigInteger("123456789012345678901234567890"));
            entity.setBigdecimalType(new BigDecimal("-1.41421356237"));
            entity.setStringType("add!");
            entity.setByteArrayType(new byte[] { 0x61, 0x62, 0x63 });
            BasicTypeAssertionAbstractTest.this.basicTypeDao.insert(entity);

            BasicTypeAssertionAbstractTest.this.tableAssertion.assertEquals("expected_basic_type_addition.csv", "basic_type");
        }

        @Test
        @DisplayName("基本的なDB型の列をもつテーブルにSQLのupdate文によりレコードを更新した内容を指定されたCSVファイルの内容で検証されること。")
        void basicTypeUpdateTableAssertion() {
            BasicTypeEntity entity = new BasicTypeEntity();
            entity.setLongType(123456789012345678L);
            entity.setBooleanType(true);
            entity.setFloatType(1.41421356D);
            entity.setDoubleType(1.41421356237D);
            entity.setBigintegerType(new BigInteger("123456789012345678901234567890"));
            entity.setBigdecimalType(new BigDecimal("-1.41421356237"));
            entity.setStringType("add!");
            entity.setByteArrayType(new byte[] { 0x61, 0x62, 0x63 });
            BasicTypeAssertionAbstractTest.this.basicTypeDao.updateById(100, entity);

            entity = BasicTypeAssertionAbstractTest.this.basicTypeDao.findById(100);

            BasicTypeAssertionAbstractTest.this.tableAssertion.assertEquals("expected_basic_type_update.csv", "basic_type");
        }

        @Test
        @DisplayName("基本的なDB型の列をもつテーブルにSQLのdelete文によりレコードを削除した内容を指定されたCSVファイルの内容で検証されること。")
        void basicTypeDeleteTableAssertion() {
            BasicTypeAssertionAbstractTest.this.basicTypeDao.deleteById(100);

            BasicTypeAssertionAbstractTest.this.tableAssertion.assertEquals("expected_basic_type_delete.csv", "basic_type");
        }
    }
}
