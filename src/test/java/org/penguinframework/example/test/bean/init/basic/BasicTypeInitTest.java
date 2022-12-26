package org.penguinframework.example.test.bean.init.basic;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.penguinframework.example.application.bean.BasicTypeBean;
import org.penguinframework.test.bean.annotation.BeanCsvMeta;
import org.penguinframework.test.bean.annotation.BeanExcelMeta;
import org.penguinframework.test.bean.annotation.BeanValueSource;
import org.penguinframework.test.extension.PenguinExtension;

/**
 * BeanValueSourceアノテーションによるBeanの初期化テスト。
 */
@ExtendWith(PenguinExtension.class)
class BasicTypeInitTest {
    @Nested
    @DisplayName("Excel形式のパラメータファイル")
    class ExcelFile {
        @Nested
        @DisplayName("フィールドの初期化")
        class Field {
            @Nested
            @DisplayName("パラメータファイルのすべての項目を使った初期化")
            class Default {
                @BeanValueSource("prepare_basic_type.xlsx")
                private BasicTypeBean bean;

                @BeanValueSource("prepare_basic_type.xlsx")
                private BasicTypeBean[] beans;

                @BeanValueSource("prepare_basic_type.xlsx")
                private List<BasicTypeBean> beanList;

                @Test
                @DisplayName("基本的な型のメンバーをもつBeanのフィールドが変数宣言に指定されたExcelファイルの内容で初期化されること。")
                void basicTypeBeanFieldInit() {
                    Assertions.assertNotNull(this.bean);

                    Assertions.assertEquals(1, this.bean.getIntegerType());
                    Assertions.assertEquals(123456789012345L, this.bean.getLongType());
                    Assertions.assertEquals(Boolean.TRUE, this.bean.getBooleanType());
                    Assertions.assertEquals(3.141592F, this.bean.getFloatType().floatValue());
                    Assertions.assertEquals(3.1415926535898D, this.bean.getDoubleType());
                    Assertions.assertEquals(new BigInteger("123456789012345678901234567890"),
                            this.bean.getBigintegerType());
                    Assertions.assertEquals(new BigDecimal("3.14159265358979"),
                            this.bean.getBigdecimalType().stripTrailingZeros());
                    Assertions.assertEquals("string!", this.bean.getStringType());
                    Assertions.assertArrayEquals(new byte[] { 0x61, 0x62, 0x63 }, this.bean.getByteArrayType());
                }

                @Test
                @DisplayName("基本的な型のメンバーをもつBeanの配列のフィールドが変数宣言に指定されたExcelファイルの内容で初期化されること。")
                void basicTypeBeansFieldInit() {
                    Assertions.assertNotNull(this.beans);
                    Assertions.assertEquals(2, this.beans.length);

                    BasicTypeBean bean = this.beans[0];
                    Assertions.assertEquals(1, bean.getIntegerType());
                    Assertions.assertEquals(123456789012345L, bean.getLongType());
                    Assertions.assertEquals(Boolean.TRUE, bean.getBooleanType());
                    Assertions.assertEquals(3.141592F, bean.getFloatType().floatValue());
                    Assertions.assertEquals(3.1415926535898D, bean.getDoubleType());
                    Assertions.assertEquals(new BigInteger("123456789012345678901234567890"), bean.getBigintegerType());
                    Assertions.assertEquals(new BigDecimal("3.14159265358979"),
                            bean.getBigdecimalType().stripTrailingZeros());
                    Assertions.assertEquals("string!", bean.getStringType());
                    Assertions.assertArrayEquals(new byte[] { 0x61, 0x62, 0x63 }, bean.getByteArrayType());

                    bean = this.beans[1];
                    Assertions.assertEquals(2, bean.getIntegerType());
                    Assertions.assertEquals(-123456789012345678L, bean.getLongType());
                    Assertions.assertEquals(Boolean.TRUE, bean.getBooleanType());
                    Assertions.assertEquals(-3.141592F, bean.getFloatType().floatValue());
                    Assertions.assertEquals(-3.1415926535898D, bean.getDoubleType());
                    Assertions.assertEquals(new BigInteger("-123456789012345678901234567890"),
                            bean.getBigintegerType());
                    Assertions.assertEquals(new BigDecimal("-3.14159265358979"),
                            bean.getBigdecimalType().stripTrailingZeros());
                    Assertions.assertEquals("STRING!", bean.getStringType());
                    Assertions.assertArrayEquals(new byte[] { 0x41, 0x42, 0x43 }, bean.getByteArrayType());
                }

                @Test
                @DisplayName("基本的な型のメンバーをもつBeanのListのフィールドが変数宣言に指定されたExcelファイルの内容で初期化されること。")
                void basicTypeBeanListFieldInit() {
                    Assertions.assertNotNull(this.beanList);
                    Assertions.assertEquals(2, this.beanList.size());

                    BasicTypeBean bean = this.beanList.get(0);
                    Assertions.assertEquals(1, bean.getIntegerType());
                    Assertions.assertEquals(123456789012345L, bean.getLongType());
                    Assertions.assertEquals(Boolean.TRUE, bean.getBooleanType());
                    Assertions.assertEquals(3.141592F, bean.getFloatType().floatValue());
                    Assertions.assertEquals(3.1415926535898D, bean.getDoubleType());
                    Assertions.assertEquals(new BigInteger("123456789012345678901234567890"), bean.getBigintegerType());
                    Assertions.assertEquals(new BigDecimal("3.14159265358979"),
                            bean.getBigdecimalType().stripTrailingZeros());
                    Assertions.assertEquals("string!", bean.getStringType());
                    Assertions.assertArrayEquals(new byte[] { 0x61, 0x62, 0x63 }, bean.getByteArrayType());

                    bean = this.beanList.get(1);
                    Assertions.assertEquals(2, bean.getIntegerType());
                    Assertions.assertEquals(-123456789012345678L, bean.getLongType());
                    Assertions.assertEquals(Boolean.TRUE, bean.getBooleanType());
                    Assertions.assertEquals(-3.141592F, bean.getFloatType().floatValue());
                    Assertions.assertEquals(-3.1415926535898D, bean.getDoubleType());
                    Assertions.assertEquals(new BigInteger("-123456789012345678901234567890"),
                            bean.getBigintegerType());
                    Assertions.assertEquals(new BigDecimal("-3.14159265358979"),
                            bean.getBigdecimalType().stripTrailingZeros());
                    Assertions.assertEquals("STRING!", bean.getStringType());
                    Assertions.assertArrayEquals(new byte[] { 0x41, 0x42, 0x43 }, bean.getByteArrayType());
                }
            }

            @Nested
            @DisplayName("指定したシート名を持つシートを使った初期化")
            class Sheet {
                @BeanValueSource(path = "prepare_basic_type.xlsx", excelMeta = @BeanExcelMeta(sheet = "BASIC_TYPE"))
                private BasicTypeBean beanSheet;

                @BeanValueSource(path = "prepare_basic_type.xlsx", excelMeta = @BeanExcelMeta(sheet = "BASIC_TYPE"))
                private BasicTypeBean[] beansSheet;

                @BeanValueSource(path = "prepare_basic_type.xlsx", excelMeta = @BeanExcelMeta(sheet = "BASIC_TYPE"))
                private List<BasicTypeBean> beanListSheet;

                @Test
                @DisplayName("基本的な型のメンバーをもつBeanのフィールドが変数宣言に指定されたExcelファイルの指定されたシートの内容で初期化されること。")
                void basicTypeBeanSheetFieldInit() {
                    Assertions.assertNotNull(this.beanSheet);

                    Assertions.assertEquals(999, this.beanSheet.getIntegerType());
                    Assertions.assertEquals(999999999999999L, this.beanSheet.getLongType());
                    Assertions.assertEquals(Boolean.FALSE, this.beanSheet.getBooleanType());
                    Assertions.assertEquals(9.999999F, this.beanSheet.getFloatType().floatValue());
                    Assertions.assertEquals(9.9999999999999D, this.beanSheet.getDoubleType());
                    Assertions.assertEquals(new BigInteger("999999999999999999999999999999"),
                            this.beanSheet.getBigintegerType());
                    Assertions.assertEquals(new BigDecimal("9.99999999999999"),
                            this.beanSheet.getBigdecimalType().stripTrailingZeros());
                    Assertions.assertEquals("penguin", this.beanSheet.getStringType());
                    Assertions.assertArrayEquals(new byte[] { 0x7a, 0x7a, 0x7a }, this.beanSheet.getByteArrayType());
                }

                @Test
                @DisplayName("基本的な型のメンバーをもつBeanの配列のフィールドが変数宣言に指定されたExcelファイルの指定されたシートの内容で初期化されること。")
                void basicTypeBeansSheetFieldInit() {
                    Assertions.assertNotNull(this.beansSheet);
                    Assertions.assertEquals(2, this.beansSheet.length);

                    BasicTypeBean bean = this.beansSheet[0];
                    Assertions.assertEquals(999, bean.getIntegerType());
                    Assertions.assertEquals(999999999999999L, bean.getLongType());
                    Assertions.assertEquals(Boolean.FALSE, bean.getBooleanType());
                    Assertions.assertEquals(9.999999F, bean.getFloatType().floatValue());
                    Assertions.assertEquals(9.9999999999999D, bean.getDoubleType());
                    Assertions.assertEquals(new BigInteger("999999999999999999999999999999"), bean.getBigintegerType());
                    Assertions.assertEquals(new BigDecimal("9.99999999999999"),
                            bean.getBigdecimalType().stripTrailingZeros());
                    Assertions.assertEquals("penguin", bean.getStringType());
                    Assertions.assertArrayEquals(new byte[] { 0x7a, 0x7a, 0x7a }, bean.getByteArrayType());

                    bean = this.beansSheet[1];
                    Assertions.assertEquals(-111, bean.getIntegerType());
                    Assertions.assertEquals(-111111111111111111L, bean.getLongType());
                    Assertions.assertEquals(Boolean.FALSE, bean.getBooleanType());
                    Assertions.assertEquals(-1.111111F, bean.getFloatType().floatValue());
                    Assertions.assertEquals(-1.1111111111111D, bean.getDoubleType());
                    Assertions.assertEquals(new BigInteger("-111111111111111111111111111111"),
                            bean.getBigintegerType());
                    Assertions.assertEquals(new BigDecimal("-1.11111111111111"),
                            bean.getBigdecimalType().stripTrailingZeros());
                    Assertions.assertEquals("PENGUIN", bean.getStringType());
                    Assertions.assertArrayEquals(new byte[] { 0x5a, 0x5a, 0x5a }, bean.getByteArrayType());
                }

                @Test
                @DisplayName("基本的な型のメンバーをもつBeanのListのフィールドが変数宣言に指定されたExcelファイルの指定されたシートの内容で初期化されること。")
                void basicTypeBeanListSheetFieldInit() {
                    Assertions.assertNotNull(this.beanListSheet);
                    Assertions.assertEquals(2, this.beanListSheet.size());

                    BasicTypeBean bean = this.beanListSheet.get(0);
                    Assertions.assertEquals(999, bean.getIntegerType());
                    Assertions.assertEquals(999999999999999L, bean.getLongType());
                    Assertions.assertEquals(Boolean.FALSE, bean.getBooleanType());
                    Assertions.assertEquals(9.999999F, bean.getFloatType().floatValue());
                    Assertions.assertEquals(9.9999999999999D, bean.getDoubleType());
                    Assertions.assertEquals(new BigInteger("999999999999999999999999999999"), bean.getBigintegerType());
                    Assertions.assertEquals(new BigDecimal("9.99999999999999"),
                            bean.getBigdecimalType().stripTrailingZeros());
                    Assertions.assertEquals("penguin", bean.getStringType());
                    Assertions.assertArrayEquals(new byte[] { 0x7a, 0x7a, 0x7a }, bean.getByteArrayType());

                    bean = this.beanListSheet.get(1);
                    Assertions.assertEquals(-111, bean.getIntegerType());
                    Assertions.assertEquals(-111111111111111111L, bean.getLongType());
                    Assertions.assertEquals(Boolean.FALSE, bean.getBooleanType());
                    Assertions.assertEquals(-1.111111F, bean.getFloatType().floatValue());
                    Assertions.assertEquals(-1.1111111111111D, bean.getDoubleType());
                    Assertions.assertEquals(new BigInteger("-111111111111111111111111111111"),
                            bean.getBigintegerType());
                    Assertions.assertEquals(new BigDecimal("-1.11111111111111"),
                            bean.getBigdecimalType().stripTrailingZeros());
                    Assertions.assertEquals("PENGUIN", bean.getStringType());
                    Assertions.assertArrayEquals(new byte[] { 0x5a, 0x5a, 0x5a }, bean.getByteArrayType());
                }
            }

            @Nested
            @DisplayName("パラメータファイルの一部の項目を使った初期化")
            class IgnoreCols {
                @BeanValueSource(path = "prepare_basic_type.xlsx", excelMeta = @BeanExcelMeta(ignoreCols = {
                        "booleanType", "byteArrayType" }))
                private BasicTypeBean beanIgnoreCols;

                @BeanValueSource(path = "prepare_basic_type.xlsx", excelMeta = @BeanExcelMeta(ignoreCols = {
                        "booleanType", "byteArrayType" }))
                private BasicTypeBean[] beansIgnoreColsArg;

                @BeanValueSource(path = "prepare_basic_type.xlsx", excelMeta = @BeanExcelMeta(ignoreCols = {
                        "booleanType", "byteArrayType" }))
                private List<BasicTypeBean> beanListIgnoreColsArg;

                @Test
                @DisplayName("基本的な型のメンバーをもつBeanのフィールドが変数宣言に指定されたExcelファイルの内容で一部の項目を除き初期化されること。")
                void basicTypeBeanIgnoreColsFieldInit() {
                    Assertions.assertNotNull(this.beanIgnoreCols);

                    Assertions.assertEquals(1, this.beanIgnoreCols.getIntegerType());
                    Assertions.assertEquals(123456789012345L, this.beanIgnoreCols.getLongType());
                    Assertions.assertNull(this.beanIgnoreCols.getBooleanType()); // ignoreCols
                    Assertions.assertEquals(3.141592F, this.beanIgnoreCols.getFloatType().floatValue());
                    Assertions.assertEquals(3.1415926535898D, this.beanIgnoreCols.getDoubleType());
                    Assertions.assertEquals(new BigInteger("123456789012345678901234567890"),
                            this.beanIgnoreCols.getBigintegerType());
                    Assertions.assertEquals(new BigDecimal("3.14159265358979"),
                            this.beanIgnoreCols.getBigdecimalType().stripTrailingZeros());
                    Assertions.assertEquals("string!", this.beanIgnoreCols.getStringType());
                    Assertions.assertNull(this.beanIgnoreCols.getByteArrayType()); // ignoreCols
                }

                @Test
                @DisplayName("基本的な型のメンバーをもつBeanの配列のフィールドが変数宣言に指定されたExcelファイルの内容で一部の項目を除き初期化されること。")
                void basicTypeBeansIgnoreColsFieldInit() {
                    Assertions.assertNotNull(this.beansIgnoreColsArg);
                    Assertions.assertEquals(2, this.beansIgnoreColsArg.length);

                    BasicTypeBean bean = this.beansIgnoreColsArg[0];
                    Assertions.assertEquals(1, bean.getIntegerType());
                    Assertions.assertEquals(123456789012345L, bean.getLongType());
                    Assertions.assertNull(bean.getBooleanType()); // ignoreCols
                    Assertions.assertEquals(3.141592F, bean.getFloatType().floatValue());
                    Assertions.assertEquals(3.1415926535898D, bean.getDoubleType());
                    Assertions.assertEquals(new BigInteger("123456789012345678901234567890"), bean.getBigintegerType());
                    Assertions.assertEquals(new BigDecimal("3.14159265358979"),
                            bean.getBigdecimalType().stripTrailingZeros());
                    Assertions.assertEquals("string!", bean.getStringType());
                    Assertions.assertNull(bean.getByteArrayType()); // ignoreCols

                    bean = this.beansIgnoreColsArg[1];
                    Assertions.assertEquals(2, bean.getIntegerType());
                    Assertions.assertEquals(-123456789012345678L, bean.getLongType());
                    Assertions.assertNull(bean.getBooleanType()); // ignoreCols
                    Assertions.assertEquals(-3.141592F, bean.getFloatType().floatValue());
                    Assertions.assertEquals(-3.1415926535898D, bean.getDoubleType());
                    Assertions.assertEquals(new BigInteger("-123456789012345678901234567890"),
                            bean.getBigintegerType());
                    Assertions.assertEquals(new BigDecimal("-3.14159265358979"),
                            bean.getBigdecimalType().stripTrailingZeros());
                    Assertions.assertEquals("STRING!", bean.getStringType());
                    Assertions.assertNull(bean.getByteArrayType()); // ignoreCols
                }

                @Test
                @DisplayName("基本的な型のメンバーをもつBeanのListのフィールドが変数宣言に指定されたExcelファイルの内容で一部の項目を除き初期化されること。")
                void basicTypeBeanListIgnoreColsFieldInit() {
                    Assertions.assertNotNull(this.beanListIgnoreColsArg);
                    Assertions.assertEquals(2, this.beanListIgnoreColsArg.size());

                    BasicTypeBean bean = this.beanListIgnoreColsArg.get(0);
                    Assertions.assertEquals(1, bean.getIntegerType());
                    Assertions.assertEquals(123456789012345L, bean.getLongType());
                    Assertions.assertNull(bean.getBooleanType()); // ignoreCols
                    Assertions.assertEquals(3.141592F, bean.getFloatType().floatValue());
                    Assertions.assertEquals(3.1415926535898D, bean.getDoubleType());
                    Assertions.assertEquals(new BigInteger("123456789012345678901234567890"), bean.getBigintegerType());
                    Assertions.assertEquals(new BigDecimal("3.14159265358979"),
                            bean.getBigdecimalType().stripTrailingZeros());
                    Assertions.assertEquals("string!", bean.getStringType());
                    Assertions.assertNull(bean.getByteArrayType()); // ignoreCols

                    bean = this.beanListIgnoreColsArg.get(1);
                    Assertions.assertEquals(2, bean.getIntegerType());
                    Assertions.assertEquals(-123456789012345678L, bean.getLongType());
                    Assertions.assertNull(bean.getBooleanType()); // ignoreCols
                    Assertions.assertEquals(-3.141592F, bean.getFloatType().floatValue());
                    Assertions.assertEquals(-3.1415926535898D, bean.getDoubleType());
                    Assertions.assertEquals(new BigInteger("-123456789012345678901234567890"),
                            bean.getBigintegerType());
                    Assertions.assertEquals(new BigDecimal("-3.14159265358979"),
                            bean.getBigdecimalType().stripTrailingZeros());
                    Assertions.assertEquals("STRING!", bean.getStringType());
                    Assertions.assertNull(bean.getByteArrayType()); // ignoreCols
                }
            }

            @Nested
            @DisplayName("指定したシート名を持つシートの一部の項目を使った初期化")
            class SheetIgnoreCols {
                @BeanValueSource(path = "prepare_basic_type.xlsx", excelMeta = @BeanExcelMeta(sheet = "BASIC_TYPE", ignoreCols = {
                        "booleanType", "byteArrayType" }))
                private BasicTypeBean beanSheetIgnoreCols;

                @BeanValueSource(path = "prepare_basic_type.xlsx", excelMeta = @BeanExcelMeta(sheet = "BASIC_TYPE", ignoreCols = {
                        "booleanType", "byteArrayType" }))
                private BasicTypeBean[] beansSheetIgnoreCols;

                @BeanValueSource(path = "prepare_basic_type.xlsx", excelMeta = @BeanExcelMeta(sheet = "BASIC_TYPE", ignoreCols = {
                        "booleanType", "byteArrayType" }))
                private List<BasicTypeBean> beanListSheetIgnoreCols;

                @Test
                @DisplayName("基本的な型のメンバーをもつBeanのフィールドが変数宣言に指定されたExcelファイルの指定されたシートの内容で一部の項目を除き初期化されること。")
                void basicTypeBeanSheetIgnoreColsFieldInit() {
                    Assertions.assertNotNull(this.beanSheetIgnoreCols);

                    Assertions.assertEquals(999, this.beanSheetIgnoreCols.getIntegerType());
                    Assertions.assertEquals(999999999999999L, this.beanSheetIgnoreCols.getLongType());
                    Assertions.assertNull(this.beanSheetIgnoreCols.getBooleanType()); // ignoreCols
                    Assertions.assertEquals(9.999999F, this.beanSheetIgnoreCols.getFloatType().floatValue());
                    Assertions.assertEquals(9.9999999999999D, this.beanSheetIgnoreCols.getDoubleType());
                    Assertions.assertEquals(new BigInteger("999999999999999999999999999999"),
                            this.beanSheetIgnoreCols.getBigintegerType());
                    Assertions.assertEquals(new BigDecimal("9.99999999999999"),
                            this.beanSheetIgnoreCols.getBigdecimalType().stripTrailingZeros());
                    Assertions.assertEquals("penguin", this.beanSheetIgnoreCols.getStringType());
                    Assertions.assertNull(this.beanSheetIgnoreCols.getByteArrayType()); // ignoreCols
                }

                @Test
                @DisplayName("基本的な型のメンバーをもつBeanの配列のフィールドが変数宣言に指定されたExcelファイルの指定されたシートの内容で一部の項目を除き初期化されること。")
                void basicTypeBeansSheetFieldIgnoreColsInit() {
                    Assertions.assertNotNull(this.beansSheetIgnoreCols);
                    Assertions.assertEquals(2, this.beansSheetIgnoreCols.length);

                    BasicTypeBean bean = this.beansSheetIgnoreCols[0];
                    Assertions.assertEquals(999, bean.getIntegerType());
                    Assertions.assertEquals(999999999999999L, bean.getLongType());
                    Assertions.assertNull(bean.getBooleanType()); // ignoreCols
                    Assertions.assertEquals(9.999999F, bean.getFloatType().floatValue());
                    Assertions.assertEquals(9.9999999999999D, bean.getDoubleType());
                    Assertions.assertEquals(new BigInteger("999999999999999999999999999999"), bean.getBigintegerType());
                    Assertions.assertEquals(new BigDecimal("9.99999999999999"),
                            bean.getBigdecimalType().stripTrailingZeros());
                    Assertions.assertEquals("penguin", bean.getStringType());
                    Assertions.assertNull(bean.getByteArrayType()); // ignoreCols

                    bean = this.beansSheetIgnoreCols[1];
                    Assertions.assertEquals(-111, bean.getIntegerType());
                    Assertions.assertEquals(-111111111111111111L, bean.getLongType());
                    Assertions.assertNull(bean.getBooleanType()); // ignoreCols
                    Assertions.assertEquals(-1.111111F, bean.getFloatType().floatValue());
                    Assertions.assertEquals(-1.1111111111111D, bean.getDoubleType());
                    Assertions.assertEquals(new BigInteger("-111111111111111111111111111111"),
                            bean.getBigintegerType());
                    Assertions.assertEquals(new BigDecimal("-1.11111111111111"),
                            bean.getBigdecimalType().stripTrailingZeros());
                    Assertions.assertEquals("PENGUIN", bean.getStringType());
                    Assertions.assertNull(bean.getByteArrayType()); // ignoreCols
                }

                @Test
                @DisplayName("基本的な型のメンバーをもつBeanのListのフィールドが変数宣言に指定されたExcelファイルの指定されたシートの内容で一部の項目を除き初期化されること。")
                void basicTypeBeanListSheetIgnoreColsFieldInit() {
                    Assertions.assertNotNull(this.beanListSheetIgnoreCols);
                    Assertions.assertEquals(2, this.beanListSheetIgnoreCols.size());

                    BasicTypeBean bean = this.beanListSheetIgnoreCols.get(0);
                    Assertions.assertEquals(999, bean.getIntegerType());
                    Assertions.assertEquals(999999999999999L, bean.getLongType());
                    Assertions.assertNull(bean.getBooleanType()); // ignoreCols
                    Assertions.assertEquals(9.999999F, bean.getFloatType().floatValue());
                    Assertions.assertEquals(9.9999999999999D, bean.getDoubleType());
                    Assertions.assertEquals(new BigInteger("999999999999999999999999999999"), bean.getBigintegerType());
                    Assertions.assertEquals(new BigDecimal("9.99999999999999"),
                            bean.getBigdecimalType().stripTrailingZeros());
                    Assertions.assertEquals("penguin", bean.getStringType());
                    Assertions.assertNull(bean.getByteArrayType()); // ignoreCols

                    bean = this.beanListSheetIgnoreCols.get(1);
                    Assertions.assertEquals(-111, bean.getIntegerType());
                    Assertions.assertEquals(-111111111111111111L, bean.getLongType());
                    Assertions.assertNull(bean.getBooleanType()); // ignoreCols
                    Assertions.assertEquals(-1.111111F, bean.getFloatType().floatValue());
                    Assertions.assertEquals(-1.1111111111111D, bean.getDoubleType());
                    Assertions.assertEquals(new BigInteger("-111111111111111111111111111111"),
                            bean.getBigintegerType());
                    Assertions.assertEquals(new BigDecimal("-1.11111111111111"),
                            bean.getBigdecimalType().stripTrailingZeros());
                    Assertions.assertEquals("PENGUIN", bean.getStringType());
                    Assertions.assertNull(bean.getByteArrayType()); // ignoreCols
                }
            }
        }

        @Nested
        @DisplayName("引数の初期化")
        class Argument {
            @Nested
            @DisplayName("パラメータファイルのすべての項目を使った初期化")
            class Default {
                @Test
                @DisplayName("基本的な型のメンバーをもつBeanの引数が変数宣言に指定されたExcelファイルの内容で初期化されること。")
                void basicTypeBeanArgumentInit(@BeanValueSource("prepare_basic_type.xlsx") BasicTypeBean beansArg) {
                    Assertions.assertNotNull(beansArg);

                    Assertions.assertEquals(1, beansArg.getIntegerType());
                    Assertions.assertEquals(123456789012345L, beansArg.getLongType());
                    Assertions.assertEquals(Boolean.TRUE, beansArg.getBooleanType());
                    Assertions.assertEquals(3.141592F, beansArg.getFloatType().floatValue());
                    Assertions.assertEquals(3.1415926535898D, beansArg.getDoubleType());
                    Assertions.assertEquals(new BigInteger("123456789012345678901234567890"),
                            beansArg.getBigintegerType());
                    Assertions.assertEquals(new BigDecimal("3.14159265358979"),
                            beansArg.getBigdecimalType().stripTrailingZeros());
                    Assertions.assertEquals("string!", beansArg.getStringType());
                    Assertions.assertArrayEquals(new byte[] { 0x61, 0x62, 0x63 }, beansArg.getByteArrayType());
                }

                @Test
                @DisplayName("基本的な型のメンバーをもつBeanの配列の引数が変数宣言に指定されたExcelファイルの内容で初期化されること。")
                void basicTypeBeansArgumentInit(@BeanValueSource("prepare_basic_type.xlsx") BasicTypeBean[] beansArg) {
                    Assertions.assertNotNull(beansArg);
                    Assertions.assertEquals(2, beansArg.length);

                    BasicTypeBean bean = beansArg[0];
                    Assertions.assertEquals(1, bean.getIntegerType());
                    Assertions.assertEquals(123456789012345L, bean.getLongType());
                    Assertions.assertEquals(Boolean.TRUE, bean.getBooleanType());
                    Assertions.assertEquals(3.141592F, bean.getFloatType().floatValue());
                    Assertions.assertEquals(3.1415926535898D, bean.getDoubleType());
                    Assertions.assertEquals(new BigInteger("123456789012345678901234567890"), bean.getBigintegerType());
                    Assertions.assertEquals(new BigDecimal("3.14159265358979"),
                            bean.getBigdecimalType().stripTrailingZeros());
                    Assertions.assertEquals("string!", bean.getStringType());
                    Assertions.assertArrayEquals(new byte[] { 0x61, 0x62, 0x63 }, bean.getByteArrayType());

                    bean = beansArg[1];
                    Assertions.assertEquals(2, bean.getIntegerType());
                    Assertions.assertEquals(-123456789012345678L, bean.getLongType());
                    Assertions.assertEquals(Boolean.TRUE, bean.getBooleanType());
                    Assertions.assertEquals(-3.141592F, bean.getFloatType().floatValue());
                    Assertions.assertEquals(-3.1415926535898D, bean.getDoubleType());
                    Assertions.assertEquals(new BigInteger("-123456789012345678901234567890"),
                            bean.getBigintegerType());
                    Assertions.assertEquals(new BigDecimal("-3.14159265358979"),
                            bean.getBigdecimalType().stripTrailingZeros());
                    Assertions.assertEquals("STRING!", bean.getStringType());
                    Assertions.assertArrayEquals(new byte[] { 0x41, 0x42, 0x43 }, bean.getByteArrayType());
                }

                @Test
                @DisplayName("基本的な型のメンバーをもつBeanのListの引数が変数宣言に指定されたExcelファイルの内容で初期化されること。")
                void basicTypeBeanListArgumentInit(
                        @BeanValueSource("prepare_basic_type.xlsx") List<BasicTypeBean> beanListArg) {
                    Assertions.assertNotNull(beanListArg);
                    Assertions.assertEquals(2, beanListArg.size());

                    BasicTypeBean bean = beanListArg.get(0);
                    Assertions.assertEquals(1, bean.getIntegerType());
                    Assertions.assertEquals(123456789012345L, bean.getLongType());
                    Assertions.assertEquals(Boolean.TRUE, bean.getBooleanType());
                    Assertions.assertEquals(3.141592F, bean.getFloatType().floatValue());
                    Assertions.assertEquals(3.1415926535898D, bean.getDoubleType());
                    Assertions.assertEquals(new BigInteger("123456789012345678901234567890"), bean.getBigintegerType());
                    Assertions.assertEquals(new BigDecimal("3.14159265358979"),
                            bean.getBigdecimalType().stripTrailingZeros());
                    Assertions.assertEquals("string!", bean.getStringType());
                    Assertions.assertArrayEquals(new byte[] { 0x61, 0x62, 0x63 }, bean.getByteArrayType());

                    bean = beanListArg.get(1);
                    Assertions.assertEquals(2, bean.getIntegerType());
                    Assertions.assertEquals(-123456789012345678L, bean.getLongType());
                    Assertions.assertEquals(Boolean.TRUE, bean.getBooleanType());
                    Assertions.assertEquals(-3.141592F, bean.getFloatType().floatValue());
                    Assertions.assertEquals(-3.1415926535898D, bean.getDoubleType());
                    Assertions.assertEquals(new BigInteger("-123456789012345678901234567890"),
                            bean.getBigintegerType());
                    Assertions.assertEquals(new BigDecimal("-3.14159265358979"),
                            bean.getBigdecimalType().stripTrailingZeros());
                    Assertions.assertEquals("STRING!", bean.getStringType());
                    Assertions.assertArrayEquals(new byte[] { 0x41, 0x42, 0x43 }, bean.getByteArrayType());
                }
            }

            @Nested
            @DisplayName("指定したシート名を持つシートを使った初期化")
            class Sheet {
                @Test
                @DisplayName("基本的な型のメンバーをもつBeanの引数が変数宣言に指定されたExcelファイルの指定されたシートの内容で初期化されること。")
                void basicTypeBeanSheetArgumentInit(
                        @BeanValueSource(path = "prepare_basic_type.xlsx", excelMeta = @BeanExcelMeta(sheet = "BASIC_TYPE")) BasicTypeBean beanSheetArg) {
                    Assertions.assertNotNull(beanSheetArg);

                    Assertions.assertEquals(999, beanSheetArg.getIntegerType());
                    Assertions.assertEquals(999999999999999L, beanSheetArg.getLongType());
                    Assertions.assertEquals(Boolean.FALSE, beanSheetArg.getBooleanType());
                    Assertions.assertEquals(9.999999F, beanSheetArg.getFloatType().floatValue());
                    Assertions.assertEquals(9.9999999999999D, beanSheetArg.getDoubleType());
                    Assertions.assertEquals(new BigInteger("999999999999999999999999999999"),
                            beanSheetArg.getBigintegerType());
                    Assertions.assertEquals(new BigDecimal("9.99999999999999"),
                            beanSheetArg.getBigdecimalType().stripTrailingZeros());
                    Assertions.assertEquals("penguin", beanSheetArg.getStringType());
                    Assertions.assertArrayEquals(new byte[] { 0x7a, 0x7a, 0x7a }, beanSheetArg.getByteArrayType());
                }

                @Test
                @DisplayName("基本的な型のメンバーをもつBeanの配列の引数が変数宣言に指定されたExcelファイルの指定されたシートの内容で初期化されること。")
                void basicTypeBeansSheetArgumentInit(
                        @BeanValueSource(path = "prepare_basic_type.xlsx", excelMeta = @BeanExcelMeta(sheet = "BASIC_TYPE")) BasicTypeBean[] beansSheetArg) {
                    Assertions.assertNotNull(beansSheetArg);
                    Assertions.assertEquals(2, beansSheetArg.length);

                    BasicTypeBean bean = beansSheetArg[0];
                    Assertions.assertEquals(999, bean.getIntegerType());
                    Assertions.assertEquals(999999999999999L, bean.getLongType());
                    Assertions.assertEquals(Boolean.FALSE, bean.getBooleanType());
                    Assertions.assertEquals(9.999999F, bean.getFloatType().floatValue());
                    Assertions.assertEquals(9.9999999999999D, bean.getDoubleType());
                    Assertions.assertEquals(new BigInteger("999999999999999999999999999999"), bean.getBigintegerType());
                    Assertions.assertEquals(new BigDecimal("9.99999999999999"),
                            bean.getBigdecimalType().stripTrailingZeros());
                    Assertions.assertEquals("penguin", bean.getStringType());
                    Assertions.assertArrayEquals(new byte[] { 0x7a, 0x7a, 0x7a }, bean.getByteArrayType());

                    bean = beansSheetArg[1];
                    Assertions.assertEquals(-111, bean.getIntegerType());
                    Assertions.assertEquals(-111111111111111111L, bean.getLongType());
                    Assertions.assertEquals(Boolean.FALSE, bean.getBooleanType());
                    Assertions.assertEquals(-1.111111F, bean.getFloatType().floatValue());
                    Assertions.assertEquals(-1.1111111111111D, bean.getDoubleType());
                    Assertions.assertEquals(new BigInteger("-111111111111111111111111111111"),
                            bean.getBigintegerType());
                    Assertions.assertEquals(new BigDecimal("-1.11111111111111"),
                            bean.getBigdecimalType().stripTrailingZeros());
                    Assertions.assertEquals("PENGUIN", bean.getStringType());
                    Assertions.assertArrayEquals(new byte[] { 0x5a, 0x5a, 0x5a }, bean.getByteArrayType());
                }

                @Test
                @DisplayName("基本的な型のメンバーをもつBeanのListの引数が変数宣言に指定されたExcelファイルの指定されたシートの内容で初期化されること。")
                void basicTypeBeanListSheetArgumentInit(
                        @BeanValueSource(path = "prepare_basic_type.xlsx", excelMeta = @BeanExcelMeta(sheet = "BASIC_TYPE")) List<BasicTypeBean> beanListSheetArg) {
                    Assertions.assertNotNull(beanListSheetArg);
                    Assertions.assertEquals(2, beanListSheetArg.size());

                    BasicTypeBean bean = beanListSheetArg.get(0);
                    Assertions.assertEquals(999, bean.getIntegerType());
                    Assertions.assertEquals(999999999999999L, bean.getLongType());
                    Assertions.assertEquals(Boolean.FALSE, bean.getBooleanType());
                    Assertions.assertEquals(9.999999F, bean.getFloatType().floatValue());
                    Assertions.assertEquals(9.9999999999999D, bean.getDoubleType());
                    Assertions.assertEquals(new BigInteger("999999999999999999999999999999"), bean.getBigintegerType());
                    Assertions.assertEquals(new BigDecimal("9.99999999999999"),
                            bean.getBigdecimalType().stripTrailingZeros());
                    Assertions.assertEquals("penguin", bean.getStringType());
                    Assertions.assertArrayEquals(new byte[] { 0x7a, 0x7a, 0x7a }, bean.getByteArrayType());

                    bean = beanListSheetArg.get(1);
                    Assertions.assertEquals(-111, bean.getIntegerType());
                    Assertions.assertEquals(-111111111111111111L, bean.getLongType());
                    Assertions.assertEquals(Boolean.FALSE, bean.getBooleanType());
                    Assertions.assertEquals(-1.111111F, bean.getFloatType().floatValue());
                    Assertions.assertEquals(-1.1111111111111D, bean.getDoubleType());
                    Assertions.assertEquals(new BigInteger("-111111111111111111111111111111"),
                            bean.getBigintegerType());
                    Assertions.assertEquals(new BigDecimal("-1.11111111111111"),
                            bean.getBigdecimalType().stripTrailingZeros());
                    Assertions.assertEquals("PENGUIN", bean.getStringType());
                    Assertions.assertArrayEquals(new byte[] { 0x5a, 0x5a, 0x5a }, bean.getByteArrayType());
                }
            }

            @Nested
            @DisplayName("パラメータファイルの一部の項目を使った初期化")
            class IgnoreCols {
                @Test
                @DisplayName("基本的な型のメンバーをもつBeanの引数が変数宣言に指定されたExcelファイルの内容で一部の項目を除き初期化されること。")
                void basicTypeBeanIgnoreColsArgumentInit(
                        @BeanValueSource(path = "prepare_basic_type.xlsx", excelMeta = @BeanExcelMeta(ignoreCols = {
                                "booleanType", "byteArrayType" })) BasicTypeBean beanIgnoreColsArg) {
                    Assertions.assertNotNull(beanIgnoreColsArg);

                    Assertions.assertEquals(1, beanIgnoreColsArg.getIntegerType());
                    Assertions.assertEquals(123456789012345L, beanIgnoreColsArg.getLongType());
                    Assertions.assertNull(beanIgnoreColsArg.getBooleanType()); // ignoreCols
                    Assertions.assertEquals(3.141592F, beanIgnoreColsArg.getFloatType().floatValue());
                    Assertions.assertEquals(3.1415926535898D, beanIgnoreColsArg.getDoubleType());
                    Assertions.assertEquals(new BigInteger("123456789012345678901234567890"),
                            beanIgnoreColsArg.getBigintegerType());
                    Assertions.assertEquals(new BigDecimal("3.14159265358979"),
                            beanIgnoreColsArg.getBigdecimalType().stripTrailingZeros());
                    Assertions.assertEquals("string!", beanIgnoreColsArg.getStringType());
                    Assertions.assertNull(beanIgnoreColsArg.getByteArrayType()); // ignoreCols
                }

                @Test
                @DisplayName("基本的な型のメンバーをもつBeanの配列の引数が変数宣言に指定されたExcelファイルの内容で一部の項目を除き初期化されること。")
                void basicTypeBeansIgnoreColsArgumentInit(
                        @BeanValueSource(path = "prepare_basic_type.xlsx", excelMeta = @BeanExcelMeta(ignoreCols = {
                                "booleanType", "byteArrayType" })) BasicTypeBean[] beansIgnoreColsArg) {
                    Assertions.assertNotNull(beansIgnoreColsArg);
                    Assertions.assertEquals(2, beansIgnoreColsArg.length);

                    BasicTypeBean bean = beansIgnoreColsArg[0];
                    Assertions.assertEquals(1, bean.getIntegerType());
                    Assertions.assertEquals(123456789012345L, bean.getLongType());
                    Assertions.assertNull(bean.getBooleanType()); // ignoreCols
                    Assertions.assertEquals(3.141592F, bean.getFloatType().floatValue());
                    Assertions.assertEquals(3.1415926535898D, bean.getDoubleType());
                    Assertions.assertEquals(new BigInteger("123456789012345678901234567890"), bean.getBigintegerType());
                    Assertions.assertEquals(new BigDecimal("3.14159265358979"),
                            bean.getBigdecimalType().stripTrailingZeros());
                    Assertions.assertEquals("string!", bean.getStringType());
                    Assertions.assertNull(bean.getByteArrayType()); // ignoreCols

                    bean = beansIgnoreColsArg[1];
                    Assertions.assertEquals(2, bean.getIntegerType());
                    Assertions.assertEquals(-123456789012345678L, bean.getLongType());
                    Assertions.assertNull(bean.getBooleanType()); // ignoreCols
                    Assertions.assertEquals(-3.141592F, bean.getFloatType().floatValue());
                    Assertions.assertEquals(-3.1415926535898D, bean.getDoubleType());
                    Assertions.assertEquals(new BigInteger("-123456789012345678901234567890"),
                            bean.getBigintegerType());
                    Assertions.assertEquals(new BigDecimal("-3.14159265358979"),
                            bean.getBigdecimalType().stripTrailingZeros());
                    Assertions.assertEquals("STRING!", bean.getStringType());
                    Assertions.assertNull(bean.getByteArrayType()); // ignoreCols
                }

                @Test
                @DisplayName("基本的な型のメンバーをもつBeanのListの引数が変数宣言に指定されたExcelファイルの内容で一部の項目を除き初期化されること。")
                void basicTypeBeanListIgnoreColsArgumentInit(
                        @BeanValueSource(path = "prepare_basic_type.xlsx", excelMeta = @BeanExcelMeta(ignoreCols = {
                                "booleanType", "byteArrayType" })) List<BasicTypeBean> beanListIgnoreColsArg) {
                    Assertions.assertNotNull(beanListIgnoreColsArg);
                    Assertions.assertEquals(2, beanListIgnoreColsArg.size());

                    BasicTypeBean bean = beanListIgnoreColsArg.get(0);
                    Assertions.assertEquals(1, bean.getIntegerType());
                    Assertions.assertEquals(123456789012345L, bean.getLongType());
                    Assertions.assertNull(bean.getBooleanType()); // ignoreCols
                    Assertions.assertEquals(3.141592F, bean.getFloatType().floatValue());
                    Assertions.assertEquals(3.1415926535898D, bean.getDoubleType());
                    Assertions.assertEquals(new BigInteger("123456789012345678901234567890"), bean.getBigintegerType());
                    Assertions.assertEquals(new BigDecimal("3.14159265358979"),
                            bean.getBigdecimalType().stripTrailingZeros());
                    Assertions.assertEquals("string!", bean.getStringType());
                    Assertions.assertNull(bean.getByteArrayType()); // ignoreCols

                    bean = beanListIgnoreColsArg.get(1);
                    Assertions.assertEquals(2, bean.getIntegerType());
                    Assertions.assertEquals(-123456789012345678L, bean.getLongType());
                    Assertions.assertNull(bean.getBooleanType()); // ignoreCols
                    Assertions.assertEquals(-3.141592F, bean.getFloatType().floatValue());
                    Assertions.assertEquals(-3.1415926535898D, bean.getDoubleType());
                    Assertions.assertEquals(new BigInteger("-123456789012345678901234567890"),
                            bean.getBigintegerType());
                    Assertions.assertEquals(new BigDecimal("-3.14159265358979"),
                            bean.getBigdecimalType().stripTrailingZeros());
                    Assertions.assertEquals("STRING!", bean.getStringType());
                    Assertions.assertNull(bean.getByteArrayType()); // ignoreCols
                }
            }

            @Nested
            @DisplayName("指定したシート名を持つシートの一部の項目を使った初期化")
            class SheetIgnoreCols {
                @Test
                @DisplayName("基本的な型のメンバーをもつBeanの引数が変数宣言に指定されたExcelファイルの指定されたシートの内容で初期化されること。")
                void basicTypeBeanSheetArgumentInit(
                        @BeanValueSource(path = "prepare_basic_type.xlsx", excelMeta = @BeanExcelMeta(sheet = "BASIC_TYPE", ignoreCols = {
                                "booleanType", "byteArrayType" })) BasicTypeBean beanSheetIgnoreColsArg) {
                    Assertions.assertNotNull(beanSheetIgnoreColsArg);

                    Assertions.assertEquals(999, beanSheetIgnoreColsArg.getIntegerType());
                    Assertions.assertEquals(999999999999999L, beanSheetIgnoreColsArg.getLongType());
                    Assertions.assertNull(beanSheetIgnoreColsArg.getBooleanType()); // ignoreCols
                    Assertions.assertEquals(9.999999F, beanSheetIgnoreColsArg.getFloatType().floatValue());
                    Assertions.assertEquals(9.9999999999999D, beanSheetIgnoreColsArg.getDoubleType());
                    Assertions.assertEquals(new BigInteger("999999999999999999999999999999"),
                            beanSheetIgnoreColsArg.getBigintegerType());
                    Assertions.assertEquals(new BigDecimal("9.99999999999999"),
                            beanSheetIgnoreColsArg.getBigdecimalType().stripTrailingZeros());
                    Assertions.assertEquals("penguin", beanSheetIgnoreColsArg.getStringType());
                    Assertions.assertNull(beanSheetIgnoreColsArg.getByteArrayType()); // ignoreCols
                }

                @Test
                @DisplayName("基本的な型のメンバーをもつBeanの配列の引数が変数宣言に指定されたExcelファイルの指定されたシートの内容で初期化されること。")
                void basicTypeBeansSheetArgumentInit(
                        @BeanValueSource(path = "prepare_basic_type.xlsx", excelMeta = @BeanExcelMeta(sheet = "BASIC_TYPE", ignoreCols = {
                                "booleanType", "byteArrayType" })) BasicTypeBean[] beansSheetIgnoreColsArg) {
                    Assertions.assertNotNull(beansSheetIgnoreColsArg);
                    Assertions.assertEquals(2, beansSheetIgnoreColsArg.length);

                    BasicTypeBean bean = beansSheetIgnoreColsArg[0];
                    Assertions.assertEquals(999, bean.getIntegerType());
                    Assertions.assertEquals(999999999999999L, bean.getLongType());
                    Assertions.assertNull(bean.getBooleanType()); // ignoreCols
                    Assertions.assertEquals(9.999999F, bean.getFloatType().floatValue());
                    Assertions.assertEquals(9.9999999999999D, bean.getDoubleType());
                    Assertions.assertEquals(new BigInteger("999999999999999999999999999999"), bean.getBigintegerType());
                    Assertions.assertEquals(new BigDecimal("9.99999999999999"),
                            bean.getBigdecimalType().stripTrailingZeros());
                    Assertions.assertEquals("penguin", bean.getStringType());
                    Assertions.assertNull(bean.getByteArrayType()); // ignoreCols

                    bean = beansSheetIgnoreColsArg[1];
                    Assertions.assertEquals(-111, bean.getIntegerType());
                    Assertions.assertEquals(-111111111111111111L, bean.getLongType());
                    Assertions.assertNull(bean.getBooleanType()); // ignoreCols
                    Assertions.assertEquals(-1.111111F, bean.getFloatType().floatValue());
                    Assertions.assertEquals(-1.1111111111111D, bean.getDoubleType());
                    Assertions.assertEquals(new BigInteger("-111111111111111111111111111111"),
                            bean.getBigintegerType());
                    Assertions.assertEquals(new BigDecimal("-1.11111111111111"),
                            bean.getBigdecimalType().stripTrailingZeros());
                    Assertions.assertEquals("PENGUIN", bean.getStringType());
                    Assertions.assertNull(bean.getByteArrayType()); // ignoreCols
                }

                @Test
                @DisplayName("基本的な型のメンバーをもつBeanのListの引数が変数宣言に指定されたExcelファイルの指定されたシートの内容で初期化されること。")
                void basicTypeBeanListSheetArgumentInit(
                        @BeanValueSource(path = "prepare_basic_type.xlsx", excelMeta = @BeanExcelMeta(sheet = "BASIC_TYPE", ignoreCols = {
                                "booleanType", "byteArrayType" })) List<BasicTypeBean> beanListSheetIgnoreColsArg) {
                    Assertions.assertNotNull(beanListSheetIgnoreColsArg);
                    Assertions.assertEquals(2, beanListSheetIgnoreColsArg.size());

                    BasicTypeBean bean = beanListSheetIgnoreColsArg.get(0);
                    Assertions.assertEquals(999, bean.getIntegerType());
                    Assertions.assertEquals(999999999999999L, bean.getLongType());
                    Assertions.assertNull(bean.getBooleanType()); // ignoreCols
                    Assertions.assertEquals(9.999999F, bean.getFloatType().floatValue());
                    Assertions.assertEquals(9.9999999999999D, bean.getDoubleType());
                    Assertions.assertEquals(new BigInteger("999999999999999999999999999999"), bean.getBigintegerType());
                    Assertions.assertEquals(new BigDecimal("9.99999999999999"),
                            bean.getBigdecimalType().stripTrailingZeros());
                    Assertions.assertEquals("penguin", bean.getStringType());
                    Assertions.assertNull(bean.getByteArrayType()); // ignoreCols

                    bean = beanListSheetIgnoreColsArg.get(1);
                    Assertions.assertEquals(-111, bean.getIntegerType());
                    Assertions.assertEquals(-111111111111111111L, bean.getLongType());
                    Assertions.assertNull(bean.getBooleanType()); // ignoreCols
                    Assertions.assertEquals(-1.111111F, bean.getFloatType().floatValue());
                    Assertions.assertEquals(-1.1111111111111D, bean.getDoubleType());
                    Assertions.assertEquals(new BigInteger("-111111111111111111111111111111"),
                            bean.getBigintegerType());
                    Assertions.assertEquals(new BigDecimal("-1.11111111111111"),
                            bean.getBigdecimalType().stripTrailingZeros());
                    Assertions.assertEquals("PENGUIN", bean.getStringType());
                    Assertions.assertNull(bean.getByteArrayType()); // ignoreCols
                }
            }
        }
    }

    @Nested
    @DisplayName("CSV形式のパラメータファイル")
    class CsvFile {
        @Nested
        @DisplayName("フィールドの初期化")
        class Field {
            @Nested
            @DisplayName("パラメータファイルのすべての項目を使った初期化")
            class Default {
                @BeanValueSource("prepare_basic_type.csv")
                private BasicTypeBean bean;

                @BeanValueSource("prepare_basic_type.csv")
                private BasicTypeBean[] beans;

                @BeanValueSource("prepare_basic_type.csv")
                private List<BasicTypeBean> beanList;

                @Test
                @DisplayName("基本的な型のメンバーをもつBeanのフィールドが変数宣言に指定されたExcelファイルの内容で初期化されること。")
                void basicTypeBeanFieldInit() {
                    Assertions.assertNotNull(this.bean);

                    Assertions.assertEquals(1, this.bean.getIntegerType());
                    Assertions.assertEquals(123456789012345L, this.bean.getLongType());
                    Assertions.assertEquals(Boolean.TRUE, this.bean.getBooleanType());
                    Assertions.assertEquals(3.141592F, this.bean.getFloatType().floatValue());
                    Assertions.assertEquals(3.1415926535898D, this.bean.getDoubleType());
                    Assertions.assertEquals(new BigInteger("123456789012345678901234567890"),
                            this.bean.getBigintegerType());
                    Assertions.assertEquals(new BigDecimal("3.14159265358979"),
                            this.bean.getBigdecimalType().stripTrailingZeros());
                    Assertions.assertEquals("string!", this.bean.getStringType());
                    Assertions.assertArrayEquals(new byte[] { 0x61, 0x62, 0x63 }, this.bean.getByteArrayType());
                }

                @Test
                @DisplayName("基本的な型のメンバーをもつBeanの配列のフィールドが変数宣言に指定されたExcelファイルの内容で初期化されること。")
                void basicTypeBeansFieldInit() {
                    Assertions.assertNotNull(this.beans);
                    Assertions.assertEquals(2, this.beans.length);

                    BasicTypeBean bean = this.beans[0];
                    Assertions.assertEquals(1, bean.getIntegerType());
                    Assertions.assertEquals(123456789012345L, bean.getLongType());
                    Assertions.assertEquals(Boolean.TRUE, bean.getBooleanType());
                    Assertions.assertEquals(3.141592F, bean.getFloatType().floatValue());
                    Assertions.assertEquals(3.1415926535898D, bean.getDoubleType());
                    Assertions.assertEquals(new BigInteger("123456789012345678901234567890"), bean.getBigintegerType());
                    Assertions.assertEquals(new BigDecimal("3.14159265358979"),
                            bean.getBigdecimalType().stripTrailingZeros());
                    Assertions.assertEquals("string!", bean.getStringType());
                    Assertions.assertArrayEquals(new byte[] { 0x61, 0x62, 0x63 }, bean.getByteArrayType());

                    bean = this.beans[1];
                    Assertions.assertEquals(2, bean.getIntegerType());
                    Assertions.assertEquals(-123456789012345678L, bean.getLongType());
                    Assertions.assertEquals(Boolean.TRUE, bean.getBooleanType());
                    Assertions.assertEquals(-3.141592F, bean.getFloatType().floatValue());
                    Assertions.assertEquals(-3.1415926535898D, bean.getDoubleType());
                    Assertions.assertEquals(new BigInteger("-123456789012345678901234567890"),
                            bean.getBigintegerType());
                    Assertions.assertEquals(new BigDecimal("-3.14159265358979"),
                            bean.getBigdecimalType().stripTrailingZeros());
                    Assertions.assertEquals("STRING!", bean.getStringType());
                    Assertions.assertArrayEquals(new byte[] { 0x41, 0x42, 0x43 }, bean.getByteArrayType());
                }

                @Test
                @DisplayName("基本的な型のメンバーをもつBeanのListのフィールドが変数宣言に指定されたExcelファイルの内容で初期化されること。")
                void basicTypeBeanListFieldInit() {
                    Assertions.assertNotNull(this.beanList);
                    Assertions.assertEquals(2, this.beanList.size());

                    BasicTypeBean bean = this.beanList.get(0);
                    Assertions.assertEquals(1, bean.getIntegerType());
                    Assertions.assertEquals(123456789012345L, bean.getLongType());
                    Assertions.assertEquals(Boolean.TRUE, bean.getBooleanType());
                    Assertions.assertEquals(3.141592F, bean.getFloatType().floatValue());
                    Assertions.assertEquals(3.1415926535898D, bean.getDoubleType());
                    Assertions.assertEquals(new BigInteger("123456789012345678901234567890"), bean.getBigintegerType());
                    Assertions.assertEquals(new BigDecimal("3.14159265358979"),
                            bean.getBigdecimalType().stripTrailingZeros());
                    Assertions.assertEquals("string!", bean.getStringType());
                    Assertions.assertArrayEquals(new byte[] { 0x61, 0x62, 0x63 }, bean.getByteArrayType());

                    bean = this.beanList.get(1);
                    Assertions.assertEquals(2, bean.getIntegerType());
                    Assertions.assertEquals(-123456789012345678L, bean.getLongType());
                    Assertions.assertEquals(Boolean.TRUE, bean.getBooleanType());
                    Assertions.assertEquals(-3.141592F, bean.getFloatType().floatValue());
                    Assertions.assertEquals(-3.1415926535898D, bean.getDoubleType());
                    Assertions.assertEquals(new BigInteger("-123456789012345678901234567890"),
                            bean.getBigintegerType());
                    Assertions.assertEquals(new BigDecimal("-3.14159265358979"),
                            bean.getBigdecimalType().stripTrailingZeros());
                    Assertions.assertEquals("STRING!", bean.getStringType());
                    Assertions.assertArrayEquals(new byte[] { 0x41, 0x42, 0x43 }, bean.getByteArrayType());
                }
            }

            @Nested
            @DisplayName("パラメータファイルの一部の項目を使った初期化")
            class IgnoreCols {
                @BeanValueSource(path = "prepare_basic_type.csv", csvMeta = @BeanCsvMeta(ignoreCols = { "booleanType",
                        "byteArrayType" }))
                private BasicTypeBean beanIgnoreCols;

                @BeanValueSource(path = "prepare_basic_type.csv", csvMeta = @BeanCsvMeta(ignoreCols = { "booleanType",
                        "byteArrayType" }))
                private BasicTypeBean[] beansIgnoreColsArg;

                @BeanValueSource(path = "prepare_basic_type.csv", csvMeta = @BeanCsvMeta(ignoreCols = { "booleanType",
                        "byteArrayType" }))
                private List<BasicTypeBean> beanListIgnoreColsArg;

                @Test
                @DisplayName("基本的な型のメンバーをもつBeanのフィールドが変数宣言に指定されたExcelファイルの内容で一部の項目を除き初期化されること。")
                void basicTypeBeanIgnoreColsFieldInit() {
                    Assertions.assertNotNull(this.beanIgnoreCols);

                    Assertions.assertEquals(1, this.beanIgnoreCols.getIntegerType());
                    Assertions.assertEquals(123456789012345L, this.beanIgnoreCols.getLongType());
                    Assertions.assertNull(this.beanIgnoreCols.getBooleanType()); // ignoreCols
                    Assertions.assertEquals(3.141592F, this.beanIgnoreCols.getFloatType().floatValue());
                    Assertions.assertEquals(3.1415926535898D, this.beanIgnoreCols.getDoubleType());
                    Assertions.assertEquals(new BigInteger("123456789012345678901234567890"),
                            this.beanIgnoreCols.getBigintegerType());
                    Assertions.assertEquals(new BigDecimal("3.14159265358979"),
                            this.beanIgnoreCols.getBigdecimalType().stripTrailingZeros());
                    Assertions.assertEquals("string!", this.beanIgnoreCols.getStringType());
                    Assertions.assertNull(this.beanIgnoreCols.getByteArrayType()); // ignoreCols
                }

                @Test
                @DisplayName("基本的な型のメンバーをもつBeanの配列のフィールドが変数宣言に指定されたExcelファイルの内容で一部の項目を除き初期化されること。")
                void basicTypeBeansIgnoreColsFieldInit() {
                    Assertions.assertNotNull(this.beansIgnoreColsArg);
                    Assertions.assertEquals(2, this.beansIgnoreColsArg.length);

                    BasicTypeBean bean = this.beansIgnoreColsArg[0];
                    Assertions.assertEquals(1, bean.getIntegerType());
                    Assertions.assertEquals(123456789012345L, bean.getLongType());
                    Assertions.assertNull(bean.getBooleanType()); // ignoreCols
                    Assertions.assertEquals(3.141592F, bean.getFloatType().floatValue());
                    Assertions.assertEquals(3.1415926535898D, bean.getDoubleType());
                    Assertions.assertEquals(new BigInteger("123456789012345678901234567890"), bean.getBigintegerType());
                    Assertions.assertEquals(new BigDecimal("3.14159265358979"),
                            bean.getBigdecimalType().stripTrailingZeros());
                    Assertions.assertEquals("string!", bean.getStringType());
                    Assertions.assertNull(bean.getByteArrayType()); // ignoreCols

                    bean = this.beansIgnoreColsArg[1];
                    Assertions.assertEquals(2, bean.getIntegerType());
                    Assertions.assertEquals(-123456789012345678L, bean.getLongType());
                    Assertions.assertNull(bean.getBooleanType()); // ignoreCols
                    Assertions.assertEquals(-3.141592F, bean.getFloatType().floatValue());
                    Assertions.assertEquals(-3.1415926535898D, bean.getDoubleType());
                    Assertions.assertEquals(new BigInteger("-123456789012345678901234567890"),
                            bean.getBigintegerType());
                    Assertions.assertEquals(new BigDecimal("-3.14159265358979"),
                            bean.getBigdecimalType().stripTrailingZeros());
                    Assertions.assertEquals("STRING!", bean.getStringType());
                    Assertions.assertNull(bean.getByteArrayType()); // ignoreCols
                }

                @Test
                @DisplayName("基本的な型のメンバーをもつBeanのListのフィールドが変数宣言に指定されたExcelファイルの内容で一部の項目を除き初期化されること。")
                void basicTypeBeanListIgnoreColsFieldInit() {
                    Assertions.assertNotNull(this.beanListIgnoreColsArg);
                    Assertions.assertEquals(2, this.beanListIgnoreColsArg.size());

                    BasicTypeBean bean = this.beanListIgnoreColsArg.get(0);
                    Assertions.assertEquals(1, bean.getIntegerType());
                    Assertions.assertEquals(123456789012345L, bean.getLongType());
                    Assertions.assertNull(bean.getBooleanType()); // ignoreCols
                    Assertions.assertEquals(3.141592F, bean.getFloatType().floatValue());
                    Assertions.assertEquals(3.1415926535898D, bean.getDoubleType());
                    Assertions.assertEquals(new BigInteger("123456789012345678901234567890"), bean.getBigintegerType());
                    Assertions.assertEquals(new BigDecimal("3.14159265358979"),
                            bean.getBigdecimalType().stripTrailingZeros());
                    Assertions.assertEquals("string!", bean.getStringType());
                    Assertions.assertNull(bean.getByteArrayType()); // ignoreCols

                    bean = this.beanListIgnoreColsArg.get(1);
                    Assertions.assertEquals(2, bean.getIntegerType());
                    Assertions.assertEquals(-123456789012345678L, bean.getLongType());
                    Assertions.assertNull(bean.getBooleanType()); // ignoreCols
                    Assertions.assertEquals(-3.141592F, bean.getFloatType().floatValue());
                    Assertions.assertEquals(-3.1415926535898D, bean.getDoubleType());
                    Assertions.assertEquals(new BigInteger("-123456789012345678901234567890"),
                            bean.getBigintegerType());
                    Assertions.assertEquals(new BigDecimal("-3.14159265358979"),
                            bean.getBigdecimalType().stripTrailingZeros());
                    Assertions.assertEquals("STRING!", bean.getStringType());
                    Assertions.assertNull(bean.getByteArrayType()); // ignoreCols
                }
            }
        }

        @Nested
        @DisplayName("引数の初期化")
        class Argument {
            @Nested
            @DisplayName("パラメータファイルのすべての項目を使った初期化")
            class Default {
                @Test
                @DisplayName("基本的な型のメンバーをもつBeanの引数が変数宣言に指定されたExcelファイルの内容で初期化されること。")
                void basicTypeBeanArgumentInit(@BeanValueSource("prepare_basic_type.csv") BasicTypeBean beansArg) {
                    Assertions.assertNotNull(beansArg);

                    Assertions.assertEquals(1, beansArg.getIntegerType());
                    Assertions.assertEquals(123456789012345L, beansArg.getLongType());
                    Assertions.assertEquals(Boolean.TRUE, beansArg.getBooleanType());
                    Assertions.assertEquals(3.141592F, beansArg.getFloatType().floatValue());
                    Assertions.assertEquals(3.1415926535898D, beansArg.getDoubleType());
                    Assertions.assertEquals(new BigInteger("123456789012345678901234567890"),
                            beansArg.getBigintegerType());
                    Assertions.assertEquals(new BigDecimal("3.14159265358979"),
                            beansArg.getBigdecimalType().stripTrailingZeros());
                    Assertions.assertEquals("string!", beansArg.getStringType());
                    Assertions.assertArrayEquals(new byte[] { 0x61, 0x62, 0x63 }, beansArg.getByteArrayType());
                }

                @Test
                @DisplayName("基本的な型のメンバーをもつBeanの配列の引数が変数宣言に指定されたExcelファイルの内容で初期化されること。")
                void basicTypeBeansArgumentInit(@BeanValueSource("prepare_basic_type.csv") BasicTypeBean[] beansArg) {
                    Assertions.assertNotNull(beansArg);
                    Assertions.assertEquals(2, beansArg.length);

                    BasicTypeBean bean = beansArg[0];
                    Assertions.assertEquals(1, bean.getIntegerType());
                    Assertions.assertEquals(123456789012345L, bean.getLongType());
                    Assertions.assertEquals(Boolean.TRUE, bean.getBooleanType());
                    Assertions.assertEquals(3.141592F, bean.getFloatType().floatValue());
                    Assertions.assertEquals(3.1415926535898D, bean.getDoubleType());
                    Assertions.assertEquals(new BigInteger("123456789012345678901234567890"), bean.getBigintegerType());
                    Assertions.assertEquals(new BigDecimal("3.14159265358979"),
                            bean.getBigdecimalType().stripTrailingZeros());
                    Assertions.assertEquals("string!", bean.getStringType());
                    Assertions.assertArrayEquals(new byte[] { 0x61, 0x62, 0x63 }, bean.getByteArrayType());

                    bean = beansArg[1];
                    Assertions.assertEquals(2, bean.getIntegerType());
                    Assertions.assertEquals(-123456789012345678L, bean.getLongType());
                    Assertions.assertEquals(Boolean.TRUE, bean.getBooleanType());
                    Assertions.assertEquals(-3.141592F, bean.getFloatType().floatValue());
                    Assertions.assertEquals(-3.1415926535898D, bean.getDoubleType());
                    Assertions.assertEquals(new BigInteger("-123456789012345678901234567890"),
                            bean.getBigintegerType());
                    Assertions.assertEquals(new BigDecimal("-3.14159265358979"),
                            bean.getBigdecimalType().stripTrailingZeros());
                    Assertions.assertEquals("STRING!", bean.getStringType());
                    Assertions.assertArrayEquals(new byte[] { 0x41, 0x42, 0x43 }, bean.getByteArrayType());
                }

                @Test
                @DisplayName("基本的な型のメンバーをもつBeanのListの引数が変数宣言に指定されたExcelファイルの内容で初期化されること。")
                void basicTypeBeanListArgumentInit(
                        @BeanValueSource("prepare_basic_type.csv") List<BasicTypeBean> beanListArg) {
                    Assertions.assertNotNull(beanListArg);
                    Assertions.assertEquals(2, beanListArg.size());

                    BasicTypeBean bean = beanListArg.get(0);
                    Assertions.assertEquals(1, bean.getIntegerType());
                    Assertions.assertEquals(123456789012345L, bean.getLongType());
                    Assertions.assertEquals(Boolean.TRUE, bean.getBooleanType());
                    Assertions.assertEquals(3.141592F, bean.getFloatType().floatValue());
                    Assertions.assertEquals(3.1415926535898D, bean.getDoubleType());
                    Assertions.assertEquals(new BigInteger("123456789012345678901234567890"), bean.getBigintegerType());
                    Assertions.assertEquals(new BigDecimal("3.14159265358979"),
                            bean.getBigdecimalType().stripTrailingZeros());
                    Assertions.assertEquals("string!", bean.getStringType());
                    Assertions.assertArrayEquals(new byte[] { 0x61, 0x62, 0x63 }, bean.getByteArrayType());

                    bean = beanListArg.get(1);
                    Assertions.assertEquals(2, bean.getIntegerType());
                    Assertions.assertEquals(-123456789012345678L, bean.getLongType());
                    Assertions.assertEquals(Boolean.TRUE, bean.getBooleanType());
                    Assertions.assertEquals(-3.141592F, bean.getFloatType().floatValue());
                    Assertions.assertEquals(-3.1415926535898D, bean.getDoubleType());
                    Assertions.assertEquals(new BigInteger("-123456789012345678901234567890"),
                            bean.getBigintegerType());
                    Assertions.assertEquals(new BigDecimal("-3.14159265358979"),
                            bean.getBigdecimalType().stripTrailingZeros());
                    Assertions.assertEquals("STRING!", bean.getStringType());
                    Assertions.assertArrayEquals(new byte[] { 0x41, 0x42, 0x43 }, bean.getByteArrayType());
                }
            }

            @Nested
            @DisplayName("パラメータファイルの一部の項目を使った初期化")
            class IgnoreCols {
                @Test
                @DisplayName("基本的な型のメンバーをもつBeanの引数が変数宣言に指定されたExcelファイルの内容で一部の項目を除き初期化されること。")
                void basicTypeBeanIgnoreColsArgumentInit(
                        @BeanValueSource(path = "prepare_basic_type.csv", csvMeta = @BeanCsvMeta(ignoreCols = {
                                "booleanType", "byteArrayType" })) BasicTypeBean beanIgnoreColsArg) {
                    Assertions.assertNotNull(beanIgnoreColsArg);

                    Assertions.assertEquals(1, beanIgnoreColsArg.getIntegerType());
                    Assertions.assertEquals(123456789012345L, beanIgnoreColsArg.getLongType());
                    Assertions.assertNull(beanIgnoreColsArg.getBooleanType()); // ignoreCols
                    Assertions.assertEquals(3.141592F, beanIgnoreColsArg.getFloatType().floatValue());
                    Assertions.assertEquals(3.1415926535898D, beanIgnoreColsArg.getDoubleType());
                    Assertions.assertEquals(new BigInteger("123456789012345678901234567890"),
                            beanIgnoreColsArg.getBigintegerType());
                    Assertions.assertEquals(new BigDecimal("3.14159265358979"),
                            beanIgnoreColsArg.getBigdecimalType().stripTrailingZeros());
                    Assertions.assertEquals("string!", beanIgnoreColsArg.getStringType());
                    Assertions.assertNull(beanIgnoreColsArg.getByteArrayType()); // ignoreCols
                }

                @Test
                @DisplayName("基本的な型のメンバーをもつBeanの配列の引数が変数宣言に指定されたExcelファイルの内容で一部の項目を除き初期化されること。")
                void basicTypeBeansIgnoreColsArgumentInit(
                        @BeanValueSource(path = "prepare_basic_type.csv", csvMeta = @BeanCsvMeta(ignoreCols = {
                                "booleanType", "byteArrayType" })) BasicTypeBean[] beansIgnoreColsArg) {
                    Assertions.assertNotNull(beansIgnoreColsArg);
                    Assertions.assertEquals(2, beansIgnoreColsArg.length);

                    BasicTypeBean bean = beansIgnoreColsArg[0];
                    Assertions.assertEquals(1, bean.getIntegerType());
                    Assertions.assertEquals(123456789012345L, bean.getLongType());
                    Assertions.assertNull(bean.getBooleanType()); // ignoreCols
                    Assertions.assertEquals(3.141592F, bean.getFloatType().floatValue());
                    Assertions.assertEquals(3.1415926535898D, bean.getDoubleType());
                    Assertions.assertEquals(new BigInteger("123456789012345678901234567890"), bean.getBigintegerType());
                    Assertions.assertEquals(new BigDecimal("3.14159265358979"),
                            bean.getBigdecimalType().stripTrailingZeros());
                    Assertions.assertEquals("string!", bean.getStringType());
                    Assertions.assertNull(bean.getByteArrayType()); // ignoreCols

                    bean = beansIgnoreColsArg[1];
                    Assertions.assertEquals(2, bean.getIntegerType());
                    Assertions.assertEquals(-123456789012345678L, bean.getLongType());
                    Assertions.assertNull(bean.getBooleanType()); // ignoreCols
                    Assertions.assertEquals(-3.141592F, bean.getFloatType().floatValue());
                    Assertions.assertEquals(-3.1415926535898D, bean.getDoubleType());
                    Assertions.assertEquals(new BigInteger("-123456789012345678901234567890"),
                            bean.getBigintegerType());
                    Assertions.assertEquals(new BigDecimal("-3.14159265358979"),
                            bean.getBigdecimalType().stripTrailingZeros());
                    Assertions.assertEquals("STRING!", bean.getStringType());
                    Assertions.assertNull(bean.getByteArrayType()); // ignoreCols
                }

                @Test
                @DisplayName("基本的な型のメンバーをもつBeanのListの引数が変数宣言に指定されたExcelファイルの内容で一部の項目を除き初期化されること。")
                void basicTypeBeanListIgnoreColsArgumentInit(
                        @BeanValueSource(path = "prepare_basic_type.csv", csvMeta = @BeanCsvMeta(ignoreCols = {
                                "booleanType", "byteArrayType" })) List<BasicTypeBean> beanListIgnoreColsArg) {
                    Assertions.assertNotNull(beanListIgnoreColsArg);
                    Assertions.assertEquals(2, beanListIgnoreColsArg.size());

                    BasicTypeBean bean = beanListIgnoreColsArg.get(0);
                    Assertions.assertEquals(1, bean.getIntegerType());
                    Assertions.assertEquals(123456789012345L, bean.getLongType());
                    Assertions.assertNull(bean.getBooleanType()); // ignoreCols
                    Assertions.assertEquals(3.141592F, bean.getFloatType().floatValue());
                    Assertions.assertEquals(3.1415926535898D, bean.getDoubleType());
                    Assertions.assertEquals(new BigInteger("123456789012345678901234567890"), bean.getBigintegerType());
                    Assertions.assertEquals(new BigDecimal("3.14159265358979"),
                            bean.getBigdecimalType().stripTrailingZeros());
                    Assertions.assertEquals("string!", bean.getStringType());
                    Assertions.assertNull(bean.getByteArrayType()); // ignoreCols

                    bean = beanListIgnoreColsArg.get(1);
                    Assertions.assertEquals(2, bean.getIntegerType());
                    Assertions.assertEquals(-123456789012345678L, bean.getLongType());
                    Assertions.assertNull(bean.getBooleanType()); // ignoreCols
                    Assertions.assertEquals(-3.141592F, bean.getFloatType().floatValue());
                    Assertions.assertEquals(-3.1415926535898D, bean.getDoubleType());
                    Assertions.assertEquals(new BigInteger("-123456789012345678901234567890"),
                            bean.getBigintegerType());
                    Assertions.assertEquals(new BigDecimal("-3.14159265358979"),
                            bean.getBigdecimalType().stripTrailingZeros());
                    Assertions.assertEquals("STRING!", bean.getStringType());
                    Assertions.assertNull(bean.getByteArrayType()); // ignoreCols
                }
            }
        }
    }
}
