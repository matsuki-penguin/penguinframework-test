package org.penguinframework.example.test.database.init;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.penguinframework.example.application.Application;
import org.penguinframework.example.application.dao.BasicTypeDao;
import org.penguinframework.example.application.dao.entity.BasicTypeEntity;
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
@TestMethodOrder(OrderAnnotation.class)
class InitTransactionTest {
    @Autowired
    private BasicTypeDao basicTypeDao;

    @Test
    @Order(1)
    @DisplayName("メソッド単位でExcelファイルから初期化したテーブルのすべてのレコードが取得されること")
    @TableValueSource(path = "prepare_method.xlsx")
    void testFindAllMethodInit() {
        List<BasicTypeEntity> entities = this.basicTypeDao.findAll();

        Assertions.assertNotNull(entities);
        Assertions.assertEquals(2, entities.size());

        BasicTypeEntity entity = entities.get(0);
        Assertions.assertEquals(1, entity.getIntegerType());
        Assertions.assertEquals(123456789012345L, entity.getLongType());
        Assertions.assertEquals(Boolean.TRUE, entity.getBooleanType());
        Assertions.assertEquals(3.141592F, entity.getFloatType());
        Assertions.assertEquals(3.1415926535898D, entity.getDoubleType());
        Assertions.assertEquals(new BigInteger("123456789012345678901234567890"), entity.getBigintegerType());
        Assertions.assertEquals(new BigDecimal("3.14159265358979"), entity.getBigdecimalType().stripTrailingZeros());
        Assertions.assertEquals("string!", entity.getStringType());
        Assertions.assertArrayEquals(new byte[] { 0x61, 0x62, 0x63 }, entity.getByteArrayType());

        entity = entities.get(1);
        Assertions.assertEquals(2, entity.getIntegerType());
        Assertions.assertEquals(-123456789012345678L, entity.getLongType());
        Assertions.assertEquals(Boolean.TRUE, entity.getBooleanType());
        Assertions.assertEquals(-3.141592F, entity.getFloatType());
        Assertions.assertEquals(-3.1415926535898D, entity.getDoubleType());
        Assertions.assertEquals(new BigInteger("-123456789012345678901234567890"), entity.getBigintegerType());
        Assertions.assertEquals(new BigDecimal("-3.14159265358979"), entity.getBigdecimalType().stripTrailingZeros());
        Assertions.assertEquals("STRING!", entity.getStringType());
        Assertions.assertArrayEquals(new byte[] { 0x41, 0x42, 0x43 }, entity.getByteArrayType());
    }

    @Test
    @Order(2)
    @DisplayName("テーブルの初期化データがロールバックされ初期データすべてのレコードが取得されること")
    void testFindAllAfterInit() {
        List<BasicTypeEntity> entities = this.basicTypeDao.findAll();

        Assertions.assertNotNull(entities);
        Assertions.assertEquals(1, entities.size());

        BasicTypeEntity entity = entities.get(0);
        Assertions.assertEquals(100, entity.getIntegerType());
        Assertions.assertEquals(1234567890123L, entity.getLongType());
        Assertions.assertEquals(Boolean.TRUE, entity.getBooleanType());
        Assertions.assertEquals(1.7320508F, entity.getFloatType());
        Assertions.assertEquals(1.4142110356D, entity.getDoubleType());
        Assertions.assertEquals(new BigInteger("987654321987654321"), entity.getBigintegerType());
        Assertions.assertEquals(new BigDecimal("3.14159265358979"), entity.getBigdecimalType().stripTrailingZeros());
        Assertions.assertEquals("penguin!", entity.getStringType());
        Assertions.assertArrayEquals(new byte[] { 0x61, 0x62, 0x63 }, entity.getByteArrayType());
    }

    @Test
    @Order(3)
    @DisplayName("テーブルの初期データをSQL文で更新したすべてのレコードが取得されること")
    void testFindByIdMethodUpdate() {
        BasicTypeEntity entity = new BasicTypeEntity();
        entity.setLongType(123456789012345L);
        entity.setBooleanType(Boolean.TRUE);
        entity.setFloatType(3.141592D);
        entity.setDoubleType(3.1415926535898D);
        entity.setBigintegerType(new BigInteger("123456789012345678901234567890"));
        entity.setBigdecimalType(new BigDecimal("3.14159265358979"));
        entity.setStringType("string!");
        entity.setByteArrayType(new byte[] { 0x61, 0x62, 0x63 });
        this.basicTypeDao.updateById(100, entity);

        List<BasicTypeEntity> entities = this.basicTypeDao.findAll();

        Assertions.assertNotNull(entities);
        Assertions.assertEquals(1, entities.size());

        entity = entities.get(0);
        Assertions.assertEquals(100, entity.getIntegerType());
        Assertions.assertEquals(123456789012345L, entity.getLongType());
        Assertions.assertEquals(Boolean.TRUE, entity.getBooleanType());
        Assertions.assertEquals(3.141592F, entity.getFloatType());
        Assertions.assertEquals(3.1415926535898D, entity.getDoubleType());
        Assertions.assertEquals(new BigInteger("123456789012345678901234567890"), entity.getBigintegerType());
        Assertions.assertEquals(new BigDecimal("3.14159265358979"), entity.getBigdecimalType().stripTrailingZeros());
        Assertions.assertEquals("string!", entity.getStringType());
        Assertions.assertArrayEquals(new byte[] { 0x61, 0x62, 0x63 }, entity.getByteArrayType());
    }

    @Test
    @Order(4)
    @DisplayName("SQL文による更新がロールバックされ初期データすべてのレコードが取得されること")
    void testFindByIdAfterUpdate() {
        List<BasicTypeEntity> entities = this.basicTypeDao.findAll();

        Assertions.assertNotNull(entities);
        Assertions.assertEquals(1, entities.size());

        BasicTypeEntity entity = entities.get(0);
        Assertions.assertEquals(100, entity.getIntegerType());
        Assertions.assertEquals(1234567890123L, entity.getLongType());
        Assertions.assertEquals(Boolean.TRUE, entity.getBooleanType());
        Assertions.assertEquals(1.7320508F, entity.getFloatType());
        Assertions.assertEquals(1.4142110356D, entity.getDoubleType());
        Assertions.assertEquals(new BigInteger("987654321987654321"), entity.getBigintegerType());
        Assertions.assertEquals(new BigDecimal("3.14159265358979"), entity.getBigdecimalType().stripTrailingZeros());
        Assertions.assertEquals("penguin!", entity.getStringType());
        Assertions.assertArrayEquals(new byte[] { 0x61, 0x62, 0x63 }, entity.getByteArrayType());
    }
}
