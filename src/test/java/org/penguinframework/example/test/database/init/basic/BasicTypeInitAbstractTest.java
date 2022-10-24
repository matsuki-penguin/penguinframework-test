package org.penguinframework.example.test.database.init.basic;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.penguinframework.example.application.Application;
import org.penguinframework.example.application.dao.BasicTypeDao;
import org.penguinframework.example.application.dao.entity.BasicTypeEntity;
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
abstract class BasicTypeInitAbstractTest {
    @Autowired
    private BasicTypeDao basicTypeDao;

    abstract class ExcelFileAbstract {
        @TableValueSource("prepare_basic_type.xlsx")
        abstract class ClassInitAbstract {
            @Test
            @DisplayName("基本的なDB型の列をもつテーブルがクラスに指定されたExcelファイルの内容で初期化されること。")
            void basicTypeTableClassInit() {
                List<BasicTypeEntity> entities = BasicTypeInitAbstractTest.this.basicTypeDao.findAll();

                Assertions.assertNotNull(entities);
                Assertions.assertEquals(2, entities.size());

                BasicTypeEntity entity = entities.get(0);
                Assertions.assertEquals(1, entity.getIntegerType());
                Assertions.assertEquals(123456789012345L, entity.getLongType());
                Assertions.assertEquals(Boolean.TRUE, entity.getBooleanType());
                Assertions.assertEquals(3.141592F, entity.getFloatType().floatValue());
                Assertions.assertEquals(3.1415926535898D, entity.getDoubleType());
                Assertions.assertEquals(new BigInteger("123456789012345678901234567890"), entity.getBigintegerType());
                Assertions.assertEquals(new BigDecimal("3.14159265358979"),
                        entity.getBigdecimalType().stripTrailingZeros());
                Assertions.assertEquals("string!", entity.getStringType());
                Assertions.assertArrayEquals(new byte[] { 0x61, 0x62, 0x63 }, entity.getByteArrayType());

                entity = entities.get(1);
                Assertions.assertEquals(2, entity.getIntegerType());
                Assertions.assertEquals(-123456789012345678L, entity.getLongType());
                Assertions.assertEquals(Boolean.TRUE, entity.getBooleanType());
                Assertions.assertEquals(-3.141592F, entity.getFloatType().floatValue());
                Assertions.assertEquals(-3.1415926535898D, entity.getDoubleType());
                Assertions.assertEquals(new BigInteger("-123456789012345678901234567890"), entity.getBigintegerType());
                Assertions.assertEquals(new BigDecimal("-3.14159265358979"),
                        entity.getBigdecimalType().stripTrailingZeros());
                Assertions.assertEquals("STRING!", entity.getStringType());
                Assertions.assertArrayEquals(new byte[] { 0x41, 0x42, 0x43 }, entity.getByteArrayType());
            }

            @Test
            @DisplayName("基本的なDB型の列をもつテーブルがメソッドに指定されたExcelファイルの内容で初期化されること。")
            @TableValueSource("prepare_addition_basic_type.xlsx")
            void basicTypeTableMethodInit() {
                List<BasicTypeEntity> entities = BasicTypeInitAbstractTest.this.basicTypeDao.findAll();

                Assertions.assertNotNull(entities);
                Assertions.assertEquals(1, entities.size());

                BasicTypeEntity entity = entities.get(0);
                Assertions.assertEquals(99, entity.getIntegerType());
                Assertions.assertEquals(123456789012345678L, entity.getLongType());
                Assertions.assertEquals(Boolean.TRUE, entity.getBooleanType());
                Assertions.assertEquals(1.41421356F, entity.getFloatType().floatValue());
                Assertions.assertEquals(1.41421356237D, entity.getDoubleType());
                Assertions.assertEquals(new BigInteger("123456789012345678901234567890"), entity.getBigintegerType());
                Assertions.assertEquals(new BigDecimal("-1.41421356237"),
                        entity.getBigdecimalType().stripTrailingZeros());
                Assertions.assertEquals("add!", entity.getStringType());
                Assertions.assertArrayEquals(new byte[] { 0x61, 0x62, 0x63 }, entity.getByteArrayType());
            }

            @Test
            @DisplayName("基本的なDB型の列をもつテーブルがクラスとメソッドに指定されたExcelファイルの内容で初期化されること。")
            @TableValueSource(path = "prepare_addition_basic_type.xlsx", operation = OperationType.INSERT)
            void basicTypeTableClassAndMethodInit() {
                List<BasicTypeEntity> entities = BasicTypeInitAbstractTest.this.basicTypeDao.findAll();

                Assertions.assertNotNull(entities);
                Assertions.assertEquals(3, entities.size());

                BasicTypeEntity entity = entities.get(0);
                Assertions.assertEquals(1, entity.getIntegerType());
                Assertions.assertEquals(123456789012345L, entity.getLongType());
                Assertions.assertEquals(Boolean.TRUE, entity.getBooleanType());
                Assertions.assertEquals(3.141592F, entity.getFloatType().floatValue());
                Assertions.assertEquals(3.1415926535898D, entity.getDoubleType());
                Assertions.assertEquals(new BigInteger("123456789012345678901234567890"), entity.getBigintegerType());
                Assertions.assertEquals(new BigDecimal("3.14159265358979"),
                        entity.getBigdecimalType().stripTrailingZeros());
                Assertions.assertEquals("string!", entity.getStringType());
                Assertions.assertArrayEquals(new byte[] { 0x61, 0x62, 0x63 }, entity.getByteArrayType());

                entity = entities.get(1);
                Assertions.assertEquals(2, entity.getIntegerType());
                Assertions.assertEquals(-123456789012345678L, entity.getLongType());
                Assertions.assertEquals(Boolean.TRUE, entity.getBooleanType());
                Assertions.assertEquals(-3.141592F, entity.getFloatType().floatValue());
                Assertions.assertEquals(-3.1415926535898D, entity.getDoubleType());
                Assertions.assertEquals(new BigInteger("-123456789012345678901234567890"), entity.getBigintegerType());
                Assertions.assertEquals(new BigDecimal("-3.14159265358979"),
                        entity.getBigdecimalType().stripTrailingZeros());
                Assertions.assertEquals("STRING!", entity.getStringType());
                Assertions.assertArrayEquals(new byte[] { 0x41, 0x42, 0x43 }, entity.getByteArrayType());

                entity = entities.get(2);
                Assertions.assertEquals(99, entity.getIntegerType());
                Assertions.assertEquals(123456789012345678L, entity.getLongType());
                Assertions.assertEquals(Boolean.TRUE, entity.getBooleanType());
                Assertions.assertEquals(1.41421356F, entity.getFloatType().floatValue());
                Assertions.assertEquals(1.41421356237D, entity.getDoubleType());
                Assertions.assertEquals(new BigInteger("123456789012345678901234567890"), entity.getBigintegerType());
                Assertions.assertEquals(new BigDecimal("-1.41421356237"),
                        entity.getBigdecimalType().stripTrailingZeros());
                Assertions.assertEquals("add!", entity.getStringType());
                Assertions.assertArrayEquals(new byte[] { 0x61, 0x62, 0x63 }, entity.getByteArrayType());
            }
        }

        abstract class ClassNoInitAbstract {
            @Test
            @DisplayName("基本的なDB型の列をもつテーブルの既存データが削除されないこと。")
            void basicTypeTableClassInit() {
                List<BasicTypeEntity> entities = BasicTypeInitAbstractTest.this.basicTypeDao.findAll();

                Assertions.assertNotNull(entities);
                Assertions.assertEquals(1, entities.size());

                BasicTypeEntity entity = entities.get(0);
                Assertions.assertEquals(100, entity.getIntegerType());
                Assertions.assertEquals(1234567890123L, entity.getLongType());
                Assertions.assertEquals(Boolean.TRUE, entity.getBooleanType());
                Assertions.assertEquals(1.7320508F, entity.getFloatType().floatValue());
                Assertions.assertEquals(1.4142110356D, entity.getDoubleType());
                Assertions.assertEquals(new BigInteger("987654321987654321"), entity.getBigintegerType());
                Assertions.assertEquals(new BigDecimal("3.14159265358979"),
                        entity.getBigdecimalType().stripTrailingZeros());
                Assertions.assertEquals("penguin!", entity.getStringType());
                Assertions.assertArrayEquals(new byte[] { 0x61, 0x62, 0x63 }, entity.getByteArrayType());
            }

            @Test
            @DisplayName("基本的なDB型の列をもつテーブルの既存データにメソッドに指定されたExcelファイルの内容が追加されること。")
            @TableValueSource(path = "prepare_addition_basic_type.xlsx", operation = OperationType.INSERT)
            void basicTypeTableClassAndMethodInit() {
                List<BasicTypeEntity> entities = BasicTypeInitAbstractTest.this.basicTypeDao.findAll();

                Assertions.assertNotNull(entities);
                Assertions.assertEquals(2, entities.size());

                BasicTypeEntity entity = entities.get(0);
                Assertions.assertEquals(99, entity.getIntegerType());
                Assertions.assertEquals(123456789012345678L, entity.getLongType());
                Assertions.assertEquals(Boolean.TRUE, entity.getBooleanType());
                Assertions.assertEquals(1.41421356F, entity.getFloatType().floatValue());
                Assertions.assertEquals(1.41421356237D, entity.getDoubleType());
                Assertions.assertEquals(new BigInteger("123456789012345678901234567890"), entity.getBigintegerType());
                Assertions.assertEquals(new BigDecimal("-1.41421356237"),
                        entity.getBigdecimalType().stripTrailingZeros());
                Assertions.assertEquals("add!", entity.getStringType());
                Assertions.assertArrayEquals(new byte[] { 0x61, 0x62, 0x63 }, entity.getByteArrayType());

                entity = entities.get(1);
                Assertions.assertEquals(100, entity.getIntegerType());
                Assertions.assertEquals(1234567890123L, entity.getLongType());
                Assertions.assertEquals(Boolean.TRUE, entity.getBooleanType());
                Assertions.assertEquals(1.7320508F, entity.getFloatType().floatValue());
                Assertions.assertEquals(1.4142110356D, entity.getDoubleType());
                Assertions.assertEquals(new BigInteger("987654321987654321"), entity.getBigintegerType());
                Assertions.assertEquals(new BigDecimal("3.14159265358979"),
                        entity.getBigdecimalType().stripTrailingZeros());
                Assertions.assertEquals("penguin!", entity.getStringType());
                Assertions.assertArrayEquals(new byte[] { 0x61, 0x62, 0x63 }, entity.getByteArrayType());
            }
        }
    }

    abstract class CsvFileAbstract {
        @TableValueSource(path = "prepare_basic_type.csv", csvMeta = @TableCsvMeta(table = "basic_type"))
        abstract class ClassInitAbstract {
            @Test
            @DisplayName("基本的なDB型の列をもつテーブルがクラスに指定されたExcelファイルの内容で初期化されること。")
            void basicTypeTableClassInit() {
                List<BasicTypeEntity> entities = BasicTypeInitAbstractTest.this.basicTypeDao.findAll();

                Assertions.assertNotNull(entities);
                Assertions.assertEquals(2, entities.size());

                BasicTypeEntity entity = entities.get(0);
                Assertions.assertEquals(1, entity.getIntegerType());
                Assertions.assertEquals(123456789012345L, entity.getLongType());
                Assertions.assertEquals(Boolean.TRUE, entity.getBooleanType());
                Assertions.assertEquals(3.141592F, entity.getFloatType().floatValue());
                Assertions.assertEquals(3.1415926535898D, entity.getDoubleType());
                Assertions.assertEquals(new BigInteger("123456789012345678901234567890"), entity.getBigintegerType());
                Assertions.assertEquals(new BigDecimal("3.14159265358979"),
                        entity.getBigdecimalType().stripTrailingZeros());
                Assertions.assertEquals("string!", entity.getStringType());
                Assertions.assertArrayEquals(new byte[] { 0x61, 0x62, 0x63 }, entity.getByteArrayType());

                entity = entities.get(1);
                Assertions.assertEquals(2, entity.getIntegerType());
                Assertions.assertEquals(-123456789012345678L, entity.getLongType());
                Assertions.assertEquals(Boolean.TRUE, entity.getBooleanType());
                Assertions.assertEquals(-3.141592F, entity.getFloatType().floatValue());
                Assertions.assertEquals(-3.1415926535898D, entity.getDoubleType());
                Assertions.assertEquals(new BigInteger("-123456789012345678901234567890"), entity.getBigintegerType());
                Assertions.assertEquals(new BigDecimal("-3.14159265358979"),
                        entity.getBigdecimalType().stripTrailingZeros());
                Assertions.assertEquals("STRING!", entity.getStringType());
                Assertions.assertArrayEquals(new byte[] { 0x41, 0x42, 0x43 }, entity.getByteArrayType());
            }

            @Test
            @DisplayName("基本的なDB型の列をもつテーブルがメソッドに指定されたExcelファイルの内容で初期化されること。")
            @TableValueSource(path = "prepare_addition_basic_type.csv", csvMeta = @TableCsvMeta(table = "basic_type"))
            void basicTypeTableMethodInit() {
                List<BasicTypeEntity> entities = BasicTypeInitAbstractTest.this.basicTypeDao.findAll();

                Assertions.assertNotNull(entities);
                Assertions.assertEquals(1, entities.size());

                BasicTypeEntity entity = entities.get(0);
                Assertions.assertEquals(99, entity.getIntegerType());
                Assertions.assertEquals(123456789012345678L, entity.getLongType());
                Assertions.assertEquals(Boolean.TRUE, entity.getBooleanType());
                Assertions.assertEquals(1.41421356F, entity.getFloatType().floatValue());
                Assertions.assertEquals(1.41421356237D, entity.getDoubleType());
                Assertions.assertEquals(new BigInteger("123456789012345678901234567890"), entity.getBigintegerType());
                Assertions.assertEquals(new BigDecimal("-1.41421356237"),
                        entity.getBigdecimalType().stripTrailingZeros());
                Assertions.assertEquals("add!", entity.getStringType());
                Assertions.assertArrayEquals(new byte[] { 0x61, 0x62, 0x63 }, entity.getByteArrayType());
            }

            @Test
            @DisplayName("基本的なDB型の列をもつテーブルがクラスとメソッドに指定されたExcelファイルの内容で初期化されること。")
            @TableValueSource(path = "prepare_addition_basic_type.csv", csvMeta = @TableCsvMeta(table = "basic_type"), operation = OperationType.INSERT)
            void basicTypeTableClassAndMethodInit() {
                List<BasicTypeEntity> entities = BasicTypeInitAbstractTest.this.basicTypeDao.findAll();

                Assertions.assertNotNull(entities);
                Assertions.assertEquals(3, entities.size());

                BasicTypeEntity entity = entities.get(0);
                Assertions.assertEquals(1, entity.getIntegerType());
                Assertions.assertEquals(123456789012345L, entity.getLongType());
                Assertions.assertEquals(Boolean.TRUE, entity.getBooleanType());
                Assertions.assertEquals(3.141592F, entity.getFloatType().floatValue());
                Assertions.assertEquals(3.1415926535898D, entity.getDoubleType());
                Assertions.assertEquals(new BigInteger("123456789012345678901234567890"), entity.getBigintegerType());
                Assertions.assertEquals(new BigDecimal("3.14159265358979"),
                        entity.getBigdecimalType().stripTrailingZeros());
                Assertions.assertEquals("string!", entity.getStringType());
                Assertions.assertArrayEquals(new byte[] { 0x61, 0x62, 0x63 }, entity.getByteArrayType());

                entity = entities.get(1);
                Assertions.assertEquals(2, entity.getIntegerType());
                Assertions.assertEquals(-123456789012345678L, entity.getLongType());
                Assertions.assertEquals(Boolean.TRUE, entity.getBooleanType());
                Assertions.assertEquals(-3.141592F, entity.getFloatType().floatValue());
                Assertions.assertEquals(-3.1415926535898D, entity.getDoubleType());
                Assertions.assertEquals(new BigInteger("-123456789012345678901234567890"), entity.getBigintegerType());
                Assertions.assertEquals(new BigDecimal("-3.14159265358979"),
                        entity.getBigdecimalType().stripTrailingZeros());
                Assertions.assertEquals("STRING!", entity.getStringType());
                Assertions.assertArrayEquals(new byte[] { 0x41, 0x42, 0x43 }, entity.getByteArrayType());

                entity = entities.get(2);
                Assertions.assertEquals(99, entity.getIntegerType());
                Assertions.assertEquals(123456789012345678L, entity.getLongType());
                Assertions.assertEquals(Boolean.TRUE, entity.getBooleanType());
                Assertions.assertEquals(1.41421356F, entity.getFloatType().floatValue());
                Assertions.assertEquals(1.41421356237D, entity.getDoubleType());
                Assertions.assertEquals(new BigInteger("123456789012345678901234567890"), entity.getBigintegerType());
                Assertions.assertEquals(new BigDecimal("-1.41421356237"),
                        entity.getBigdecimalType().stripTrailingZeros());
                Assertions.assertEquals("add!", entity.getStringType());
                Assertions.assertArrayEquals(new byte[] { 0x61, 0x62, 0x63 }, entity.getByteArrayType());
            }
        }

        abstract class ClassNoInitAbstract {
            @Test
            @DisplayName("基本的なDB型の列をもつテーブルの既存データが削除されないこと。")
            void basicTypeTableClassInit() {
                List<BasicTypeEntity> entities = BasicTypeInitAbstractTest.this.basicTypeDao.findAll();

                Assertions.assertNotNull(entities);
                Assertions.assertEquals(1, entities.size());

                BasicTypeEntity entity = entities.get(0);
                Assertions.assertEquals(100, entity.getIntegerType());
                Assertions.assertEquals(1234567890123L, entity.getLongType());
                Assertions.assertEquals(Boolean.TRUE, entity.getBooleanType());
                Assertions.assertEquals(1.7320508F, entity.getFloatType().floatValue());
                Assertions.assertEquals(1.4142110356D, entity.getDoubleType());
                Assertions.assertEquals(new BigInteger("987654321987654321"), entity.getBigintegerType());
                Assertions.assertEquals(new BigDecimal("3.14159265358979"),
                        entity.getBigdecimalType().stripTrailingZeros());
                Assertions.assertEquals("penguin!", entity.getStringType());
                Assertions.assertArrayEquals(new byte[] { 0x61, 0x62, 0x63 }, entity.getByteArrayType());
            }

            @Test
            @DisplayName("基本的なDB型の列をもつテーブルの既存データにメソッドに指定されたExcelファイルの内容が追加されること。")
            @TableValueSource(path = "prepare_addition_basic_type.csv", csvMeta = @TableCsvMeta(table = "basic_type"), operation = OperationType.INSERT)
            void basicTypeTableClassAndMethodInit() {
                List<BasicTypeEntity> entities = BasicTypeInitAbstractTest.this.basicTypeDao.findAll();

                Assertions.assertNotNull(entities);
                Assertions.assertEquals(2, entities.size());

                BasicTypeEntity entity = entities.get(0);
                Assertions.assertEquals(99, entity.getIntegerType());
                Assertions.assertEquals(123456789012345678L, entity.getLongType());
                Assertions.assertEquals(Boolean.TRUE, entity.getBooleanType());
                Assertions.assertEquals(1.41421356F, entity.getFloatType().floatValue());
                Assertions.assertEquals(1.41421356237D, entity.getDoubleType());
                Assertions.assertEquals(new BigInteger("123456789012345678901234567890"), entity.getBigintegerType());
                Assertions.assertEquals(new BigDecimal("-1.41421356237"),
                        entity.getBigdecimalType().stripTrailingZeros());
                Assertions.assertEquals("add!", entity.getStringType());
                Assertions.assertArrayEquals(new byte[] { 0x61, 0x62, 0x63 }, entity.getByteArrayType());

                entity = entities.get(1);
                Assertions.assertEquals(100, entity.getIntegerType());
                Assertions.assertEquals(1234567890123L, entity.getLongType());
                Assertions.assertEquals(Boolean.TRUE, entity.getBooleanType());
                Assertions.assertEquals(1.7320508F, entity.getFloatType().floatValue());
                Assertions.assertEquals(1.4142110356D, entity.getDoubleType());
                Assertions.assertEquals(new BigInteger("987654321987654321"), entity.getBigintegerType());
                Assertions.assertEquals(new BigDecimal("3.14159265358979"),
                        entity.getBigdecimalType().stripTrailingZeros());
                Assertions.assertEquals("penguin!", entity.getStringType());
                Assertions.assertArrayEquals(new byte[] { 0x61, 0x62, 0x63 }, entity.getByteArrayType());
            }
        }
    }
}
