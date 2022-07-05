package org.penguinframework.test.bean;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.penguinframework.test.bean.annotation.BeanCsvMeta;
import org.penguinframework.test.bean.annotation.BeanExcelMeta;
import org.penguinframework.test.bean.annotation.BeanValueSource;
import org.penguinframework.test.type.CsvFormatType;
import org.penguinframework.test.type.FileType;

@TestMethodOrder(OrderAnnotation.class)
@DisplayName("BeanLoader")
class BeanLoaderTest {
    @Nested
    @DisplayName("Excelファイルによる検証")
    class excel {
        @Test
        @DisplayName("クラス変数にExcel形式のBean初期化ファイルを指定したクラスのフィールドが初期化されること")
        @Tag("normal")
        void initExcelFile() throws Exception {
            class StubClass {
                @BeanValueSource("prepare_for_bean.xlsx")
                public Profile profile;
            }
            StubClass instance = new StubClass();

            BeanLoader.initFields(instance);

            Assertions.assertNotNull(instance.profile);
            Assertions.assertEquals(1, instance.profile.getId());
            Assertions.assertEquals("penguin", instance.profile.getName());
            Assertions.assertEquals(LocalDate.of(2002, 1, 1), instance.profile.getBirthday());
        }

        @Test
        @DisplayName("privateスコープのクラス変数にExcel形式のBean初期化ファイルを指定したクラスのフィールドが初期化されること")
        @Tag("normal")
        void initPrivateFieldForExcelFile() throws Exception {
            class StubClass {
                @BeanValueSource("prepare_for_bean.xlsx")
                private Profile profile;
            }
            StubClass instance = new StubClass();

            BeanLoader.initFields(instance);

            Assertions.assertNotNull(instance.profile);
            Assertions.assertEquals(1, instance.profile.getId());
            Assertions.assertEquals("penguin", instance.profile.getName());
            Assertions.assertEquals(LocalDate.of(2002, 1, 1), instance.profile.getBirthday());
        }

        @Test
        @DisplayName("finalで修飾されたクラス変数にExcel形式のBean初期化ファイルを指定したクラスのフィールドが初期化されること")
        @Tag("normal")
        void initFinalFieldForExcelFile() throws Exception {
            class StubClass {
                @BeanValueSource("prepare_for_bean.xlsx")
                private final Profile profile = null;
            }
            StubClass instance = new StubClass();

            BeanLoader.initFields(instance);

            Assertions.assertNotNull(instance.profile);
            Assertions.assertEquals(1, instance.profile.getId());
            Assertions.assertEquals("penguin", instance.profile.getName());
            Assertions.assertEquals(LocalDate.of(2002, 1, 1), instance.profile.getBirthday());
        }

        @Test
        @DisplayName("配列のクラス変数にExcel形式のBean初期化ファイルを指定したクラスのフィールドが初期化されること")
        @Tag("normal")
        void initArrayFieldForExcelFile() throws Exception {
            class StubClass {
                @BeanValueSource("prepare_for_bean.xlsx")
                public Profile[] profiles;
            }
            StubClass instance = new StubClass();

            BeanLoader.initFields(instance);

            Assertions.assertNotNull(instance.profiles);
            Assertions.assertEquals(2, instance.profiles.length);
            Assertions.assertEquals(1, instance.profiles[0].getId());
            Assertions.assertEquals("penguin", instance.profiles[0].getName());
            Assertions.assertEquals(LocalDate.of(2002, 1, 1), instance.profiles[0].getBirthday());
            Assertions.assertEquals(2, instance.profiles[1].getId());
            Assertions.assertEquals("matsuki", instance.profiles[1].getName());
            Assertions.assertEquals(LocalDate.of(2010, 12, 3), instance.profiles[1].getBirthday());
        }

        @Test
        @DisplayName("java.util.List型のクラス変数にExcel形式のBean初期化ファイルを指定したクラスのフィールドが初期化されること")
        @Tag("normal")
        void initListFieldForExcelFile() throws Exception {
            class StubClass {
                @BeanValueSource("prepare_for_bean.xlsx")
                public List<Profile> profileList;
            }
            StubClass instance = new StubClass();

            BeanLoader.initFields(instance);

            Assertions.assertNotNull(instance.profileList);
            Assertions.assertEquals(2, instance.profileList.size());
            Assertions.assertEquals(1, instance.profileList.get(0).getId());
            Assertions.assertEquals("penguin", instance.profileList.get(0).getName());
            Assertions.assertEquals(LocalDate.of(2002, 1, 1), instance.profileList.get(0).getBirthday());
            Assertions.assertEquals(2, instance.profileList.get(1).getId());
            Assertions.assertEquals("matsuki", instance.profileList.get(1).getName());
            Assertions.assertEquals(LocalDate.of(2010, 12, 3), instance.profileList.get(1).getBirthday());
        }

        @Test
        @DisplayName("static変数にExcel形式のBean初期化ファイルを指定したクラスのフィールドが初期化されること")
        @Tag("normal")
        void initStaticFieldForExcelFile() throws Exception {
            StaticStubClass instance = new StaticStubClass();

            BeanLoader.initFields(instance);

            Assertions.assertNotNull(StaticStubClass.profile);
            Assertions.assertEquals(1, StaticStubClass.profile.getId());
            Assertions.assertEquals("penguin", StaticStubClass.profile.getName());
            Assertions.assertEquals(LocalDate.of(2002, 1, 1), StaticStubClass.profile.getBirthday());
        }

        @Test
        @DisplayName("継承関係を持つクラスのクラス変数にExcel形式のBean初期化ファイルを指定したクラスのフィールドが初期化されること")
        @Tag("normal")
        void initExtendsClassForExcelFile() throws Exception {
            class StubClass {
                @BeanValueSource("prepare_for_bean.xlsx")
                public Profile profile;
            }
            class StubSubClass extends StubClass {
                @BeanValueSource("prepare_for_bean.xlsx")
                public Profile profileSub;
            }
            StubSubClass instance = new StubSubClass();

            BeanLoader.initFields(instance);

            Assertions.assertNotNull(instance.profile);
            Assertions.assertEquals(1, instance.profile.getId());
            Assertions.assertEquals("penguin", instance.profile.getName());
            Assertions.assertEquals(LocalDate.of(2002, 1, 1), instance.profile.getBirthday());

            Assertions.assertNotNull(instance.profileSub);
            Assertions.assertEquals(1, instance.profileSub.getId());
            Assertions.assertEquals("penguin", instance.profileSub.getName());
            Assertions.assertEquals(LocalDate.of(2002, 1, 1), instance.profileSub.getBirthday());
        }

        @Test
        @DisplayName("クラス変数にExcel形式のBean初期化ファイルを指定したカスタムセッターを持つクラスのフィールドが初期化されること")
        @Tag("normal")
        void initCustomSetterClassForExcelFile() throws Exception {
            CustomSetterStubClass instance = new CustomSetterStubClass();

            BeanLoader.initFields(instance);

            Assertions.assertNotNull(instance.profile);
            Assertions.assertEquals(1001, instance.profile.getId());
        }

        @Test
        @DisplayName("初期化ファイルに項目が存在しないフィールドを持つクラスの初期化対象フィールドが初期化されること")
        @Tag("normal")
        void initNoInitFieldForExcelFile() throws Exception {
            class StubClass {
                @BeanValueSource(path = "prepare_for_bean.xlsx", excelMeta = @BeanExcelMeta(sheet = "Profile"))
                private NoInitFieldProfile profile;
            }
            StubClass instance = new StubClass();

            BeanLoader.initFields(instance);

            Assertions.assertNotNull(instance.profile);
            Assertions.assertEquals(1, instance.profile.getId());
            Assertions.assertEquals("penguin", instance.profile.getName());
            Assertions.assertEquals(LocalDate.of(2002, 1, 1), instance.profile.getBirthday());
            Assertions.assertNull(instance.profile.noInitField);
            Assertions.assertEquals("Initialized", instance.profile.constantInitField);
        }

        @Test
        @DisplayName("クラス変数にExcel形式のBean初期化ファイルを指定したクラスのフィールドが再初期化されること")
        @Tag("normal")
        void reinitExcelFile() throws Exception {
            class StubClass {
                @BeanValueSource("prepare_for_bean.xlsx")
                public Profile profile;
            }
            StubClass instance = new StubClass();

            BeanLoader.initFields(instance);
            Profile initProfile = instance.profile;

            BeanLoader.initFields(instance);
            Assertions.assertNotNull(instance.profile);
            Assertions.assertNotSame(initProfile, instance.profile);
        }

        @Test
        @DisplayName("サポートしている型のフィールドがExcel形式のBean初期化ファイルにより初期化されること")
        @Tag("normal")
        void initAllTypesForExcelFile() throws Exception {
            class StubClass {
                @BeanValueSource("prepare_for_all_types.xlsx")
                public AllTypes allTypes;
            }
            StubClass instance = new StubClass();

            BeanLoader.initFields(instance);

            Assertions.assertTrue(instance.allTypes.booleanPrimitive);
            Assertions.assertEquals(120, instance.allTypes.bytePrimitive);
            Assertions.assertEquals('G', instance.allTypes.charPrimitive);
            Assertions.assertEquals(3000, instance.allTypes.shortPrimitive);
            Assertions.assertEquals(1234567, instance.allTypes.intPrimitive);
            Assertions.assertEquals(123456789012345L, instance.allTypes.longPrimitive);
            Assertions.assertEquals(3.14F, instance.allTypes.floatPrimitive);
            Assertions.assertEquals(3.14159265358979D, instance.allTypes.doublePrimitive);

            Assertions.assertEquals(Boolean.TRUE, instance.allTypes.booleanWrapper);
            Assertions.assertEquals((byte) -120, instance.allTypes.byteWrapper);
            Assertions.assertEquals('H', instance.allTypes.characterWrapper);
            Assertions.assertEquals((short) -3000, instance.allTypes.shortWrapper);
            Assertions.assertEquals(-1234567, instance.allTypes.integerWrapper);
            Assertions.assertEquals(-123456789012345L, instance.allTypes.longWrapper);
            Assertions.assertEquals(-3.14F, instance.allTypes.floatWrapper);
            Assertions.assertEquals(-3.14159265358979D, instance.allTypes.doubleWrapper);

            Assertions.assertEquals("Hello", instance.allTypes.string);

            Assertions.assertEquals(new BigInteger("123456789012345"), instance.allTypes.bigInteger);
            Assertions.assertEquals(new BigDecimal("3.14159265358979"), instance.allTypes.bigDecimal);

            Assertions.assertEquals(java.lang.Object.class, instance.allTypes.clazz);

            Assertions.assertEquals(new java.util.Date(java.sql.Timestamp.valueOf("2001-01-23 08:30:59.987").getTime()),
                    instance.allTypes.date);
            Assertions.assertEquals(java.sql.Date.valueOf("2002-02-03"), instance.allTypes.sqlDate);
            Assertions.assertEquals(java.sql.Time.valueOf("13:34:45"), instance.allTypes.sqlTime);
            Assertions.assertEquals(java.sql.Timestamp.valueOf("2003-4-5 13:14:15.167"),
                    instance.allTypes.sqlTimestamp);

            Assertions.assertEquals(DateUtils.toCalendar(java.sql.Timestamp.valueOf("2005-06-07 08:09:10.123")),
                    instance.allTypes.calendar);

            Assertions.assertEquals(LocalDate.of(2002, 2, 3), instance.allTypes.localDate);
            Assertions.assertEquals(LocalTime.of(13, 34, 45, 567000000), instance.allTypes.localTime);
            Assertions.assertEquals(LocalDateTime.of(2003, 4, 5, 13, 14, 15, 167000000),
                    instance.allTypes.localDateTime);
            Assertions.assertEquals(
                    ZonedDateTime.of(2001, 1, 23, 8, 30, 59, 987000000, ZoneId.systemDefault()).toInstant(),
                    instance.allTypes.instant);

            Assertions.assertEquals(new File("/etc/hosts"), instance.allTypes.file);
            Assertions.assertEquals(new URL("https://www.google.com/"), instance.allTypes.url);
        }

        @Test
        @DisplayName("サポートしている型のフィールドが16桁を超える数値、ミリ秒以上の精度を含む時間はセルのフォーマットを文字列として入力したExcel形式のBean初期化ファイルにより初期化されること")
        @Tag("normal")
        void initAllTypesForTextExcelFile() throws Exception {
            class StubClass {
                @BeanValueSource("prepare_for_all_types_text.xlsx")
                public AllTypes allTypes;
            }
            StubClass instance = new StubClass();

            BeanLoader.initFields(instance);

            Assertions.assertTrue(instance.allTypes.booleanPrimitive);
            Assertions.assertEquals(120, instance.allTypes.bytePrimitive);
            Assertions.assertEquals('G', instance.allTypes.charPrimitive);
            Assertions.assertEquals(3000, instance.allTypes.shortPrimitive);
            Assertions.assertEquals(1234567, instance.allTypes.intPrimitive);
            Assertions.assertEquals(123456789012345L, instance.allTypes.longPrimitive);
            Assertions.assertEquals(3.14F, instance.allTypes.floatPrimitive);
            Assertions.assertEquals(3.14159265358979D, instance.allTypes.doublePrimitive);

            Assertions.assertEquals(Boolean.TRUE, instance.allTypes.booleanWrapper);
            Assertions.assertEquals((byte) -120, instance.allTypes.byteWrapper);
            Assertions.assertEquals('H', instance.allTypes.characterWrapper);
            Assertions.assertEquals((short) -3000, instance.allTypes.shortWrapper);
            Assertions.assertEquals(-1234567, instance.allTypes.integerWrapper);
            Assertions.assertEquals(-123456789012345L, instance.allTypes.longWrapper);
            Assertions.assertEquals(-3.14F, instance.allTypes.floatWrapper);
            Assertions.assertEquals(-3.14159265358979D, instance.allTypes.doubleWrapper);

            Assertions.assertEquals("Hello", instance.allTypes.string);

            Assertions.assertEquals(new BigInteger("123456789012345678901234567890"), instance.allTypes.bigInteger);
            Assertions.assertEquals(new BigDecimal("3.141592653589793238462643383279502884"),
                    instance.allTypes.bigDecimal);

            Assertions.assertEquals(new java.util.Date(java.sql.Timestamp.valueOf("2001-01-23 08:30:59.987").getTime()),
                    instance.allTypes.date);
            Assertions.assertEquals(java.sql.Date.valueOf("2002-02-03"), instance.allTypes.sqlDate);
            Assertions.assertEquals(java.sql.Time.valueOf("13:34:45"), instance.allTypes.sqlTime);
            Assertions.assertEquals(java.sql.Timestamp.valueOf("2003-4-5 13:14:15.123456789"),
                    instance.allTypes.sqlTimestamp);

            Assertions.assertEquals(DateUtils.toCalendar(java.sql.Timestamp.valueOf("2005-06-07 08:09:10.123")),
                    instance.allTypes.calendar);

            Assertions.assertEquals(LocalDate.of(2002, 2, 3), instance.allTypes.localDate);
            Assertions.assertEquals(LocalTime.of(13, 34, 45, 123456789), instance.allTypes.localTime);
            Assertions.assertEquals(LocalDateTime.of(2003, 4, 5, 13, 14, 15, 123456789),
                    instance.allTypes.localDateTime);
            Assertions.assertEquals(
                    ZonedDateTime.of(2001, 1, 23, 8, 30, 59, 123456789, ZoneId.systemDefault()).toInstant(),
                    instance.allTypes.instant);

            Assertions.assertEquals(new File("/etc/hosts"), instance.allTypes.file);
            Assertions.assertEquals(new URL("https://www.google.com/"), instance.allTypes.url);
        }

        @Test
        @DisplayName("クラス変数にCSV形式のBean初期化ファイルを指定したクラスのフィールドが初期化されること")
        @Tag("normal")
        void initCsvFile() throws Exception {
            class StubClass {
                @BeanValueSource("prepare_for_bean.csv")
                public Profile profile;
            }
            StubClass instance = new StubClass();

            BeanLoader.initFields(instance);

            Assertions.assertNotNull(instance.profile);
            Assertions.assertEquals(1, instance.profile.getId());
            Assertions.assertEquals("penguin", instance.profile.getName());
            Assertions.assertEquals(LocalDate.of(2002, 1, 1), instance.profile.getBirthday());
        }

        @Test
        @DisplayName("クラス変数に拡張子がtxtのCSV形式のBean初期化ファイルを指定したクラスのフィールドが初期化されること")
        @Tag("normal")
        void initTextCsvFile() throws Exception {
            class StubClass {
                @BeanValueSource(path = "prepare_for_bean.txt", type = FileType.CSV)
                public Profile profile;
            }
            StubClass instance = new StubClass();

            BeanLoader.initFields(instance);

            Assertions.assertNotNull(instance.profile);
            Assertions.assertEquals(1, instance.profile.getId());
            Assertions.assertEquals("penguin", instance.profile.getName());
            Assertions.assertEquals(LocalDate.of(2002, 1, 1), instance.profile.getBirthday());
        }

        @Test
        @DisplayName("インスタンス変数がBOMありUTF-8文字セットのCSVファイルにより初期化されること")
        @Tag("normal")
        void testInitBomUtf8File() throws Exception {
            class StubClass {
                @BeanValueSource("prepare_for_bean_bom_utf8.csv")
                private Profile profile;
            }
            StubClass instance = new StubClass();

            BeanLoader.initFields(instance);

            Assertions.assertNotNull(instance.profile);
            Assertions.assertEquals(1, instance.profile.getId());
            Assertions.assertEquals("ぺんぎん🐧", instance.profile.getName());
            Assertions.assertEquals(LocalDate.of(2002, 1, 1), instance.profile.getBirthday());
        }

        @Test
        @DisplayName("インスタンス変数がBOMありUTF-16LE文字セットのCSVファイルにより初期化されること")
        @Tag("normal")
        void testInitBomUtf16leFile() throws Exception {
            class StubClass {
                @BeanValueSource(path = "prepare_for_bean_bom_utf16le.csv", csvMeta = @BeanCsvMeta(encoding = "UTF-16"))
                private Profile profile;
            }
            StubClass instance = new StubClass();

            BeanLoader.initFields(instance);

            Assertions.assertNotNull(instance.profile);
            Assertions.assertEquals(1, instance.profile.getId());
            Assertions.assertEquals("ぺんぎん🐧", instance.profile.getName());
            Assertions.assertEquals(LocalDate.of(2002, 1, 1), instance.profile.getBirthday());
        }

        @Test
        @DisplayName("インスタンス変数がBOMありUTF-16BE文字セットのCSVファイルにより初期化されること")
        @Tag("normal")
        void testInitBomUtf16beFile() throws Exception {
            class StubClass {
                @BeanValueSource(path = "prepare_for_bean_bom_utf16be.csv", csvMeta = @BeanCsvMeta(encoding = "UTF-16"))
                private Profile profile;
            }
            StubClass instance = new StubClass();

            BeanLoader.initFields(instance);

            Assertions.assertNotNull(instance.profile);
            Assertions.assertEquals(1, instance.profile.getId());
            Assertions.assertEquals("ぺんぎん🐧", instance.profile.getName());
            Assertions.assertEquals(LocalDate.of(2002, 1, 1), instance.profile.getBirthday());
        }

        @Test
        @DisplayName("インスタンス変数がShift-JIS文字セットのCSVファイルにより初期化されること")
        @Tag("normal")
        void testInitSjisFile() throws Exception {
            class StubClass {
                @BeanValueSource(path = "prepare_for_bean_sjis.csv", csvMeta = @BeanCsvMeta(encoding = "sjis"))
                private Profile profile;
            }
            StubClass instance = new StubClass();

            BeanLoader.initFields(instance);

            Assertions.assertNotNull(instance.profile);
            Assertions.assertEquals(1, instance.profile.getId());
            Assertions.assertEquals("ぺんぎん", instance.profile.getName());
            Assertions.assertEquals(LocalDate.of(2002, 1, 1), instance.profile.getBirthday());
        }

        @Test
        @DisplayName("インスタンス変数がタブ区切りのCSVファイルにより初期化されること")
        @Tag("normal")
        void testInitTsvFormatFile() throws Exception {
            class StubClass {
                @BeanValueSource(path = "prepare_for_bean.tsv", csvMeta = @BeanCsvMeta(format = CsvFormatType.TDF))
                private Profile profile;
            }
            StubClass instance = new StubClass();

            BeanLoader.initFields(instance);

            Assertions.assertNotNull(instance.profile);
            Assertions.assertEquals(1, instance.profile.getId());
            Assertions.assertEquals("penguin", instance.profile.getName());
            Assertions.assertEquals(LocalDate.of(2002, 1, 1), instance.profile.getBirthday());
        }

        @Test
        @DisplayName("クラス変数にファイルタイプが不明のBean初期化ファイルを指定したクラスのフィールドの初期化で例外が発生すること")
        @Tag("normal")
        void unknownFileType() throws Exception {
            class StubClass {
                @BeanValueSource("filetype.unknown")
                private Profile profile;
            }
            StubClass instance = new StubClass();

            Assertions.assertThrows(IllegalArgumentException.class, () -> BeanLoader.initFields(instance));
        }

        @Test
        @DisplayName("クラス変数に存在しないBean初期化ファイルを指定したクラスのフィールドの初期化で例外が発生すること")
        @Tag("error")
        void notExistsFile() throws Exception {
            class StubClass {
                @BeanValueSource("filenotexists")
                private Profile profile;
            }
            StubClass instance = new StubClass();

            Assertions.assertThrows(FileNotFoundException.class, () -> BeanLoader.initFields(instance));
        }
    }

    @Nested
    @DisplayName("Object load(Field targetField)")
    class load_field {
        @Test
        @DisplayName("クラス変数にExcel形式のBean初期化ファイルを指定したクラスの初期化済みインスタンスが生成されること")
        @Tag("normal")
        void createInstanceForExcelFile() throws Exception {
            class StubClass {
                @BeanValueSource("prepare_for_bean.xlsx")
                public Profile profile;
            }

            Field field = FieldUtils.getDeclaredField(StubClass.class, "profile");

            Object result = BeanLoader.load(field);

            Assertions.assertNotNull(result);
            Assertions.assertInstanceOf(Profile.class, result);
            Assertions.assertEquals(1, Profile.class.cast(result).getId());
            Assertions.assertEquals("penguin", Profile.class.cast(result).getName());
            Assertions.assertEquals(LocalDate.of(2002, 1, 1), Profile.class.cast(result).getBirthday());
        }

        @Test
        @DisplayName("クラス変数にCSV形式のBean初期化ファイルを指定したクラスの初期化済みインスタンスが生成されること")
        @Tag("normal")
        void createInstanceForCsvFile() throws Exception {
            class StubClass {
                @BeanValueSource("prepare_for_bean.csv")
                public Profile profile;
            }

            Field field = FieldUtils.getDeclaredField(StubClass.class, "profile");

            Object result = BeanLoader.load(field);

            Assertions.assertNotNull(result);
            Assertions.assertInstanceOf(Profile.class, result);
            Assertions.assertEquals(1, Profile.class.cast(result).getId());
            Assertions.assertEquals("penguin", Profile.class.cast(result).getName());
            Assertions.assertEquals(LocalDate.of(2002, 1, 1), Profile.class.cast(result).getBirthday());
        }

        @Test
        @DisplayName("クラス変数にファイルタイプが不明のBean初期化ファイルを指定すると例外が発生すること")
        @Tag("error")
        void unknownFileType() throws Exception {
            class StubClass {
                @BeanValueSource("filetype.unknown")
                public Profile profile;
            }

            Field field = FieldUtils.getDeclaredField(StubClass.class, "profile");

            Assertions.assertThrows(IllegalArgumentException.class, () -> BeanLoader.load(field));
        }

        @Test
        @DisplayName("クラス変数に存在しないBean初期化ファイルを指定すると例外が発生すること")
        @Tag("error")
        void notExistsFile() throws Exception {
            class StubClass {
                @BeanValueSource("filenotexists")
                public Profile profile;
            }

            Field field = FieldUtils.getDeclaredField(StubClass.class, "profile");

            Assertions.assertThrows(FileNotFoundException.class, () -> BeanLoader.load(field));
        }
    }

    @Nested
    @DisplayName("Object load(Parameter targetParameter)")
    class load_parameter {
        @Test
        @DisplayName("パラメータにExcel形式のBean初期化ファイルを指定したクラスの初期化済みインスタンスが生成されること")
        @Tag("normal")
        void createInstanceForExcelFile() throws Exception {
            class StubClass {
                @SuppressWarnings("unused")
                public void stubMethod(@BeanValueSource("prepare_for_bean.xlsx") Profile profile) {
                }
            }

            Parameter parameter = MethodUtils
                    .getMatchingAccessibleMethod(StubClass.class, "stubMethod", new Class<?>[] { Profile.class })
                    .getParameters()[0];

            Object result = BeanLoader.load(parameter);

            Assertions.assertNotNull(result);
            Assertions.assertInstanceOf(Profile.class, result);
            Assertions.assertEquals(1, Profile.class.cast(result).getId());
            Assertions.assertEquals("penguin", Profile.class.cast(result).getName());
            Assertions.assertEquals(LocalDate.of(2002, 1, 1), Profile.class.cast(result).getBirthday());
        }

        @Test
        @DisplayName("パラメータにExcel形式のBean初期化ファイルを指定したクラスの配列の初期化済みインスタンスが生成されること")
        @Tag("normal")
        void createArrayInstanceForExcelFile() throws Exception {
            class StubClass {
                @SuppressWarnings("unused")
                public void stubMethod(@BeanValueSource("prepare_for_bean.xlsx") Profile[] profile) {
                }
            }

            Parameter parameter = MethodUtils
                    .getMatchingAccessibleMethod(StubClass.class, "stubMethod", new Class<?>[] { Profile[].class })
                    .getParameters()[0];

            Object result = BeanLoader.load(parameter);

            Assertions.assertNotNull(result);
            Assertions.assertInstanceOf(Profile[].class, result);
            Profile[] profiles = Profile[].class.cast(result);
            Assertions.assertEquals(2, profiles.length);
            Assertions.assertEquals(1, profiles[0].getId());
            Assertions.assertEquals("penguin", profiles[0].getName());
            Assertions.assertEquals(LocalDate.of(2002, 1, 1), profiles[0].getBirthday());
            Assertions.assertEquals(2, profiles[1].getId());
            Assertions.assertEquals("matsuki", profiles[1].getName());
            Assertions.assertEquals(LocalDate.of(2010, 12, 3), profiles[1].getBirthday());
        }

        @Test
        @DisplayName("パラメータにExcel形式のBean初期化ファイルを指定したjava.utul.Listのクラスの初期化済みインスタンスが生成されること")
        @Tag("normal")
        void createListInstanceForExcelFile() throws Exception {
            class StubClass {
                @SuppressWarnings("unused")
                public void stubMethod(@BeanValueSource("prepare_for_bean.xlsx") List<Profile> profile) {
                }
            }

            Parameter parameter = MethodUtils
                    .getMatchingAccessibleMethod(StubClass.class, "stubMethod", new Class<?>[] { List.class })
                    .getParameters()[0];

            Object result = BeanLoader.load(parameter);

            Assertions.assertNotNull(result);
            Assertions.assertInstanceOf(List.class, result);
            @SuppressWarnings("unchecked")
            List<Profile> profiles = List.class.cast(result);
            Assertions.assertEquals(2, profiles.size());
            Assertions.assertEquals(1, profiles.get(0).getId());
            Assertions.assertEquals("penguin", profiles.get(0).getName());
            Assertions.assertEquals(LocalDate.of(2002, 1, 1), profiles.get(0).getBirthday());
            Assertions.assertEquals(2, profiles.get(1).getId());
            Assertions.assertEquals("matsuki", profiles.get(1).getName());
            Assertions.assertEquals(LocalDate.of(2010, 12, 3), profiles.get(1).getBirthday());
        }

        @Test
        @DisplayName("パラメータにCSV形式のBean初期化ファイルを指定したクラスの初期化済みインスタンスが生成されること")
        @Tag("normal")
        void createInstanceForCsvFile() throws Exception {
            class StubClass {
                @SuppressWarnings("unused")
                public void stubMethod(@BeanValueSource("prepare_for_bean.csv") Profile profile) {
                }
            }

            Parameter parameter = MethodUtils
                    .getMatchingAccessibleMethod(StubClass.class, "stubMethod", new Class<?>[] { Profile.class })
                    .getParameters()[0];

            Object result = BeanLoader.load(parameter);

            Assertions.assertNotNull(result);
            Assertions.assertInstanceOf(Profile.class, result);
            Assertions.assertEquals(1, Profile.class.cast(result).getId());
            Assertions.assertEquals("penguin", Profile.class.cast(result).getName());
            Assertions.assertEquals(LocalDate.of(2002, 1, 1), Profile.class.cast(result).getBirthday());
        }

        @Test
        @DisplayName("パラメータにファイルタイプが不明のBean初期化ファイルを指定すると例外が発生すること")
        @Tag("error")
        void unknownFileType() throws Exception {
            class StubClass {
                @SuppressWarnings("unused")
                public void stubMethod(@BeanValueSource("filetype.unknown") Profile profile) {
                }
            }

            Parameter parameter = MethodUtils
                    .getMatchingAccessibleMethod(StubClass.class, "stubMethod", new Class<?>[] { Profile.class })
                    .getParameters()[0];

            Assertions.assertThrows(IllegalArgumentException.class, () -> BeanLoader.load(parameter));
        }

        @Test
        @DisplayName("パラメータに存在しないBean初期化ファイルを指定すると例外が発生すること")
        @Tag("error")
        void notExistsFile() throws Exception {
            class StubClass {
                @SuppressWarnings("unused")
                public void stubMethod(@BeanValueSource("filenotexists") Profile profile) {
                }
            }

            Parameter parameter = MethodUtils
                    .getMatchingAccessibleMethod(StubClass.class, "stubMethod", new Class<?>[] { Profile.class })
                    .getParameters()[0];

            Assertions.assertThrows(FileNotFoundException.class, () -> BeanLoader.load(parameter));
        }
    }

    /**
     * テスト用Java Beanクラス。
     */
    public static class Profile {
        private long id;

        private String name;

        private LocalDate birthday;

        public long getId() {
            return this.id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public LocalDate getBirthday() {
            return this.birthday;
        }

        public void setBirthday(LocalDate birthday) {
            this.birthday = birthday;
        }
    }

    public static class NoInitFieldProfile extends Profile {
        public String noInitField;
        public String constantInitField = "Initialized";
    }

    public static class AllTypes {
        public boolean booleanPrimitive;
        public byte bytePrimitive;
        public char charPrimitive;
        public short shortPrimitive;
        public int intPrimitive;
        public long longPrimitive;
        public float floatPrimitive;
        public double doublePrimitive;

        public Boolean booleanWrapper;
        public Byte byteWrapper;
        public Character characterWrapper;
        public Short shortWrapper;
        public Integer integerWrapper;
        public Long longWrapper;
        public Float floatWrapper;
        public Double doubleWrapper;

        public String string;

        public BigInteger bigInteger;
        public BigDecimal bigDecimal;

        public Class<?> clazz;

        public java.util.Date date;
        public java.sql.Date sqlDate;
        public java.sql.Time sqlTime;
        public java.sql.Timestamp sqlTimestamp;

        public Calendar calendar;

        public LocalDate localDate;
        public LocalTime localTime;
        public LocalDateTime localDateTime;
        public Instant instant;

        public File file;
        public URL url;
    }

    /**
     * staticなフィールドを持つテスト用Java Beanクラス
     */
    static class StaticStubClass {
        @BeanValueSource("prepare_for_bean.xlsx")
        public static Profile profile;
    }

    /**
     * カスタムセッターメソッドを持つテスト用Java Beanクラス
     */
    public static class CustomSetterStubClass {
        @BeanValueSource("prepare_for_bean.xlsx")
        public Profile profile;

        public Profile getProfile() {
            return this.profile;
        }

        public void setProfile(Profile profile) {
            this.profile = profile;
            this.profile.setId(this.profile.getId() + 1000);
        }
    }
}
