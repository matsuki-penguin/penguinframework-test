package org.penguinframework.test.bean;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.penguinframework.example.application.Application;
import org.penguinframework.example.application.bean.AllTypeBean;
import org.penguinframework.test.annotation.Load;
import org.penguinframework.test.bean.adapter.BeanFileAdapterTest.Profile;
import org.penguinframework.test.bean.assertion.BeanAssertion;
import org.penguinframework.test.extension.PenguinExtension;
import org.penguinframework.test.meta.Meta;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith({ SpringExtension.class, PenguinExtension.class })
@SpringBootTest(classes = Application.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
class BeanAssertionTest {

    @Nested
    @DisplayName("Excelファイルによる検証")
    class excel {
        @Load
        private BeanAssertion beanAssertion;

        private Meta getExcelMeta(String beanName) {
            // jacocoで実行したときに内部クラスに追加されるフィールドを比較対象から除外
            return Meta.excel().ignoreCols(Collections.singletonMap(beanName, new String[] { "$jacocoData" }));
        }

        @Test
        @DisplayName("単一のBeanを検証できること")
        void singleBean() {
            Profile profile = new Profile();
            profile.setId(1L);
            profile.setName("penguin");
            profile.setBirthday(LocalDate.of(2002, 1, 1));

            this.beanAssertion.assertEquals("expected_for_bean.xlsx", getExcelMeta("Profile"), profile);
        }

        @Test
        @DisplayName("配列のBeanを検証できること")
        @Tag("normal")
        void beanArray() {
            Profile profile = new Profile();
            profile.setId(1L);
            profile.setName("penguin");
            profile.setBirthday(LocalDate.of(2002, 1, 1));

            this.beanAssertion.assertEquals("expected_for_bean.xlsx", getExcelMeta("Profile"), profile);
        }

        @Test
        @DisplayName("java.util.ListのBeanを検証できること")
        @Tag("normal")
        void beanList() {
            List<Profile> profileList = new ArrayList<>();
            Profile profile = new Profile();
            profile.setId(1L);
            profile.setName("penguin");
            profile.setBirthday(LocalDate.of(2002, 1, 1));
            profileList.add(profile);

            this.beanAssertion.assertEquals("expected_for_bean.xlsx", getExcelMeta("Profile"), profileList,
                    Profile.class);
        }

        @Test
        @DisplayName("Beanがnullの場合、例外が発生すること")
        @Tag("error")

        void beanNull() {
            IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class,
                    () -> this.beanAssertion.assertEquals("expected_for_bean.xlsx", null));

            Assertions.assertTrue(e.getMessage().contains("Bean is null"));
        }

        @Test
        @DisplayName("java.util.ListのBeanがnullの場合、例外が発生すること")
        @Tag("error")
        void beanListNull() {
            IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class,
                    () -> this.beanAssertion.assertEquals("expected_for_bean.xlsx", (List<Object>) null, Object.class));

            Assertions.assertTrue(e.getMessage().contains("Bean is null"));
        }

        @Test
        @DisplayName("java.util.ListのBeanを指定し、Beanのクラス指定がない場合、例外が発生すること")
        @Tag("error")
        void beanListWithoutParameterType() {
            List<Profile> profileList = new ArrayList<>();
            Profile profile = new Profile();
            profile.setId(1L);
            profile.setName("penguin");
            profile.setBirthday(LocalDate.of(2002, 1, 1));

            IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class,
                    () -> this.beanAssertion.assertEquals("expected_for_bean.xlsx", profileList));

            Assertions.assertTrue(e.getMessage().contains("java.util.List"));
        }

        @Nested
        @DisplayName("様々な型の列を含むBeanのテスト")
        class all_type_bean {
            @Load
            private BeanAssertion beanAssertion;

            @Test
            @DisplayName("Beanのフィールドを検証できること")
            void singleBean() {
                AllTypeBean bean = new AllTypeBean();
                bean.setPrimitiveBoolean(true);
                bean.setWrapperBoolean(Boolean.TRUE);
                bean.setPrimitiveByte((byte) 127);
                bean.setWrapperByte((byte) 127);
                bean.setPrimitiveChar('c');
                bean.setWrapperCharacter('c');
                bean.setPrimitiveShort((short) 32767);
                bean.setWrapperShort((short) 32767);
                bean.setPrimitiveInt(123456789);
                bean.setWrapperInteger(123456789);
                bean.setPrimitiveLong(999999999L);
                bean.setWrapperLong(999999999L);
                bean.setPrimitiveFloat(3.14F);
                bean.setWrapperFloat(3.14F);
                bean.setPrimitiveDouble(3.141592D);
                bean.setWrapperDouble(3.141592D);
                bean.setBigInteger(new BigInteger("999999999"));
                bean.setBigDecimal(new BigDecimal("3.1415926535898"));
                bean.setPrimitiveByteArray(new byte[] { 0x61, 0x62, 0x63 });
                bean.setWrapperByteArray(new Byte[] { 0x61, 0x62, 0x63 });
                bean.setPrimitiveCharArray(
                        new char[] { 'C', 'h', 'a', 'r', 'a', 'c', 't', 'e', 'r', ' ', 'L', 'O', 'B' });
                bean.setWrapperCharacterArray(
                        new Character[] { 'C', 'h', 'a', 'r', 'a', 'c', 't', 'e', 'r', ' ', 'L', 'O', 'B' });
                bean.setString("String!");
                bean.setSqlDate(java.sql.Date.valueOf("2021-11-30"));
                bean.setLocalDate(java.sql.Date.valueOf("2021-11-30").toLocalDate());
                bean.setSqlTime(java.sql.Time.valueOf("13:14:15"));
                bean.setLocalTime(java.sql.Time.valueOf("13:14:15").toLocalTime());
                bean.setSqlTimestamp(java.sql.Timestamp.valueOf("2021-11-30 13:14:15"));
                bean.setLocalDateTime(java.sql.Timestamp.valueOf("2021-11-30 13:14:15").toLocalDateTime());
                bean.setUtilDate(new java.util.Date(java.sql.Timestamp.valueOf("2021-11-30 13:14:15").getTime()));
                bean.setCalendar(DateUtils
                        .toCalendar(new java.util.Date(java.sql.Timestamp.valueOf("2021-11-30 13:14:15").getTime())));
                bean.setInstant(java.sql.Timestamp.valueOf("2021-11-30 13:14:15").toInstant());
                bean.setUuid(java.util.UUID.fromString("6779defb-6d49-4e2e-b3dd-95cd071cea5c"));

                this.beanAssertion.assertEquals("expected_all_type.xlsx", getExcelMeta("AllTypeBean"), bean);
            }

            @Test
            @DisplayName("セルが文字列フォーマットのExcelファイルとBeanのフィールドを検証できること")
            void singleBeanFormatText() {
                AllTypeBean bean = new AllTypeBean();
                bean.setPrimitiveBoolean(true);
                bean.setWrapperBoolean(Boolean.TRUE);
                bean.setPrimitiveByte((byte) 127);
                bean.setWrapperByte((byte) 127);
                bean.setPrimitiveChar('c');
                bean.setWrapperCharacter('c');
                bean.setPrimitiveShort((short) 32767);
                bean.setWrapperShort((short) 32767);
                bean.setPrimitiveInt(123456789);
                bean.setWrapperInteger(123456789);
                bean.setPrimitiveLong(999999999L);
                bean.setWrapperLong(999999999L);
                bean.setPrimitiveFloat(3.14F);
                bean.setWrapperFloat(3.14F);
                bean.setPrimitiveDouble(3.141592D);
                bean.setWrapperDouble(3.141592D);
                bean.setBigInteger(new BigInteger("999999999"));
                bean.setBigDecimal(new BigDecimal("3.1415926535898"));
                bean.setPrimitiveByteArray(new byte[] { 0x61, 0x62, 0x63 });
                bean.setWrapperByteArray(new Byte[] { 0x61, 0x62, 0x63 });
                bean.setPrimitiveCharArray(
                        new char[] { 'C', 'h', 'a', 'r', 'a', 'c', 't', 'e', 'r', ' ', 'L', 'O', 'B' });
                bean.setWrapperCharacterArray(
                        new Character[] { 'C', 'h', 'a', 'r', 'a', 'c', 't', 'e', 'r', ' ', 'L', 'O', 'B' });
                bean.setString("String!");
                bean.setSqlDate(java.sql.Date.valueOf("2021-11-30"));
                bean.setLocalDate(java.sql.Date.valueOf("2021-11-30").toLocalDate());
                bean.setSqlTime(java.sql.Time.valueOf("13:14:15"));
                bean.setLocalTime(
                        java.sql.Timestamp.valueOf("2021-11-30 13:14:15.123456789").toLocalDateTime().toLocalTime());
                bean.setSqlTimestamp(java.sql.Timestamp.valueOf("2021-11-30 13:14:15.123456789"));
                bean.setLocalDateTime(java.sql.Timestamp.valueOf("2021-11-30 13:14:15.123456789").toLocalDateTime());
                bean.setUtilDate(
                        new java.util.Date(java.sql.Timestamp.valueOf("2021-11-30 13:14:15.123456789").getTime()));
                bean.setCalendar(DateUtils.toCalendar(
                        new java.util.Date(java.sql.Timestamp.valueOf("2021-11-30 13:14:15.123456789").getTime())));
                bean.setInstant(java.sql.Timestamp.valueOf("2021-11-30 13:14:15.123456789").toInstant());
                bean.setUuid(java.util.UUID.fromString("6779defb-6d49-4e2e-b3dd-95cd071cea5c"));

                this.beanAssertion.assertEquals("expected_all_type_format_text.xlsx", getExcelMeta("AllTypeBean"),
                        bean);
            }

            @Test
            @DisplayName("Beanの初期値(0, false, null)のフィールドを検証できること")
            void insertNull() {
                AllTypeBean bean = new AllTypeBean();
                bean.setPrimitiveChar(' '); // プリミティブのchar型は文字として検証するので、文字として表現できない初期値0は検証できない

                this.beanAssertion.assertEquals("expected_all_type_null.xlsx", getExcelMeta("AllTypeBean"), bean);
            }
        }
    }
}
