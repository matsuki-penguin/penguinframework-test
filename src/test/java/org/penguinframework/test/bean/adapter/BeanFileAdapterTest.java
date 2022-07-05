package org.penguinframework.test.bean.adapter;

import java.lang.reflect.InvocationTargetException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.dbunit.dataset.Column;
import org.dbunit.dataset.DefaultTable;
import org.dbunit.dataset.datatype.DataType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.penguinframework.test.meta.Meta;
import org.penguinframework.test.support.BeanType;

@DisplayName("BeanFileAdapter")
public class BeanFileAdapterTest {

    @Nested
    @DisplayName("Object toBean(ITable table)")
    class toBean_itable {

        private String methodName = "toBean";

        @Test
        @DisplayName("単一のJava Beanが初期化されること")
        @Tag("normal")
        void createBeanFromExcelFile() throws Exception {
            DefaultTable table = new DefaultTable("Profile", new Column[] { new Column("id", DataType.UNKNOWN),
                    new Column("name", DataType.UNKNOWN), new Column("birthday", DataType.UNKNOWN) });
            table.addRow(new Object[] { 1, "penguin", "2002-1-1" });
            table.addRow(new Object[] { 2, "matsuki", "2010-12-3" });

            BeanFileAdapter fileAdapter = new ExcelBeanFileAdapter(StringUtils.EMPTY, Meta.excel());
            BeanType.Info info = new BeanType.Info();
            info.setActualClass(Profile.class);
            info.setArray(false);
            info.setList(false);

            Object result = MethodUtils.invokeMethod(fileAdapter, true, this.methodName, table, info);

            Assertions.assertNotNull(result);
            Assertions.assertInstanceOf(Profile.class, result);
            Profile profile = Profile.class.cast(result);
            Assertions.assertEquals(1, profile.getId());
            Assertions.assertEquals("penguin", profile.getName());
            Assertions.assertEquals(LocalDate.of(2002, 1, 1), profile.getBirthday());
        }

        @Test
        @DisplayName("配列のJava Beanが初期化されること")
        @Tag("normal")
        void createBeanArrayFromExcelFile() throws Exception {
            DefaultTable table = new DefaultTable("Profile", new Column[] { new Column("id", DataType.UNKNOWN),
                    new Column("name", DataType.UNKNOWN), new Column("birthday", DataType.UNKNOWN) });
            table.addRow(new Object[] { 1, "penguin", "2002-1-1" });
            table.addRow(new Object[] { 2, "matsuki", "2010-12-3" });

            BeanFileAdapter fileAdapter = new ExcelBeanFileAdapter(StringUtils.EMPTY, Meta.excel());
            BeanType.Info info = new BeanType.Info();
            info.setActualClass(Profile.class);
            info.setArray(true);
            info.setList(false);

            Object result = MethodUtils.invokeMethod(fileAdapter, true, this.methodName, table, info);

            Assertions.assertNotNull(result);
            Assertions.assertInstanceOf(Profile[].class, result);
            Profile[] profile = Profile[].class.cast(result);
            Assertions.assertEquals(1, profile[0].getId());
            Assertions.assertEquals("penguin", profile[0].getName());
            Assertions.assertEquals(LocalDate.of(2002, 1, 1), profile[0].getBirthday());
            Assertions.assertEquals(2, profile[1].getId());
            Assertions.assertEquals("matsuki", profile[1].getName());
            Assertions.assertEquals(LocalDate.of(2010, 12, 3), profile[1].getBirthday());
        }

        @Test
        @DisplayName("java.util.List型のJava Beanが初期化されること")
        @Tag("normal")
        void createBeanListFromExcelFile() throws Exception {
            DefaultTable table = new DefaultTable("Profile", new Column[] { new Column("id", DataType.UNKNOWN),
                    new Column("name", DataType.UNKNOWN), new Column("birthday", DataType.UNKNOWN) });
            table.addRow(new Object[] { 1, "penguin", "2002-1-1" });
            table.addRow(new Object[] { 2, "matsuki", "2010-12-3" });

            BeanFileAdapter fileAdapter = new ExcelBeanFileAdapter(StringUtils.EMPTY, Meta.excel());
            BeanType.Info info = new BeanType.Info();
            info.setActualClass(Profile.class);
            info.setArray(false);
            info.setList(true);

            Object result = MethodUtils.invokeMethod(fileAdapter, true, this.methodName, table, info);

            Assertions.assertNotNull(result);
            Assertions.assertInstanceOf(List.class, result);
            List<?> profile = List.class.cast(result);
            Assertions.assertInstanceOf(Profile.class, profile.get(0));
            Assertions.assertEquals(1, Profile.class.cast(profile.get(0)).getId());
            Assertions.assertEquals("penguin", Profile.class.cast(profile.get(0)).getName());
            Assertions.assertEquals(LocalDate.of(2002, 1, 1), Profile.class.cast(profile.get(0)).getBirthday());
            Assertions.assertInstanceOf(Profile.class, profile.get(1));
            Assertions.assertEquals(2, Profile.class.cast(profile.get(1)).getId());
            Assertions.assertEquals("matsuki", Profile.class.cast(profile.get(1)).getName());
            Assertions.assertEquals(LocalDate.of(2010, 12, 3), Profile.class.cast(profile.get(1)).getBirthday());
        }
    }

    @Nested
    @DisplayName("Object toSingleBean(ITable table, int row, Class<?> clazz)")
    class toSingleBean_ITable_int_Class {

        private String methodName = "toSingleBean";

        @Test
        @DisplayName("初期化ファイルの1行目でJava Beanが初期化されること")
        @Tag("normal")
        void createBeanFrom1stRow() throws Exception {
            DefaultTable table = new DefaultTable("Profile", new Column[] { new Column("id", DataType.UNKNOWN),
                    new Column("name", DataType.UNKNOWN), new Column("birthday", DataType.UNKNOWN) });
            table.addRow(new Object[] { 1, "penguin", "2002-1-1" });
            table.addRow(new Object[] { 2, "matsuki", "2010-12-3" });

            BeanFileAdapter fileAdapter = new ExcelBeanFileAdapter(StringUtils.EMPTY, Meta.excel());

            Object result = MethodUtils.invokeMethod(fileAdapter, true, this.methodName, table, 0, Profile.class);

            Assertions.assertNotNull(result);
            Assertions.assertInstanceOf(Profile.class, result);
            Profile profile = Profile.class.cast(result);
            Assertions.assertEquals(1, profile.getId());
            Assertions.assertEquals("penguin", profile.getName());
            Assertions.assertEquals(LocalDate.of(2002, 1, 1), profile.getBirthday());
        }

        @Test
        @Tag("normal")
        @DisplayName("初期化ファイルの2行目でJava Beanが初期化されること")
        void createBeanFrom2ndRow() throws Exception {
            DefaultTable table = new DefaultTable("Profile", new Column[] { new Column("id", DataType.UNKNOWN),
                    new Column("name", DataType.UNKNOWN), new Column("birthday", DataType.UNKNOWN) });
            table.addRow(new Object[] { 1, "penguin", "2002-1-1" });
            table.addRow(new Object[] { 2, "matsuki", "2010-12-3" });

            BeanFileAdapter fileAdapter = new ExcelBeanFileAdapter(StringUtils.EMPTY, Meta.excel());

            Object result = MethodUtils.invokeMethod(fileAdapter, true, this.methodName, table, 1, Profile.class);

            Assertions.assertNotNull(result);
            Assertions.assertInstanceOf(Profile.class, result);
            Profile profile = Profile.class.cast(result);
            Assertions.assertEquals(2, profile.getId());
            Assertions.assertEquals("matsuki", profile.getName());
            Assertions.assertEquals(LocalDate.of(2010, 12, 3), profile.getBirthday());
        }

        @Test
        @Tag("normal")
        @DisplayName("初期化ファイルに定義されていないフィールドが存在するJava Beanが初期化されること")
        void createBeanNoInitField() throws Exception {
            DefaultTable table = new DefaultTable("Profile", new Column[] { new Column("id", DataType.UNKNOWN),
                    new Column("name", DataType.UNKNOWN), new Column("birthday", DataType.UNKNOWN) });
            table.addRow(new Object[] { 1, "penguin", "2002-1-1" });
            table.addRow(new Object[] { 2, "matsuki", "2010-12-3" });

            BeanFileAdapter fileAdapter = new ExcelBeanFileAdapter(StringUtils.EMPTY, Meta.excel());

            Object result = MethodUtils.invokeMethod(fileAdapter, true, this.methodName, table, 1,
                    ProfileNoInitField.class);

            Assertions.assertNotNull(result);
            Assertions.assertInstanceOf(ProfileNoInitField.class, result);
            ProfileNoInitField profile = ProfileNoInitField.class.cast(result);
            Assertions.assertEquals(2, profile.id);
            Assertions.assertNull(profile.hobby);
        }

        @Test
        @DisplayName("継承関係を持つJava Beanが親クラスのフィールドも含め初期化されること")
        @Tag("normal")
        void createExtendsBean() throws Exception {
            DefaultTable table = new DefaultTable("Profile", new Column[] { new Column("id", DataType.UNKNOWN),
                    new Column("name", DataType.UNKNOWN), new Column("birthday", DataType.UNKNOWN) });
            table.addRow(new Object[] { 1, "penguin", "2002-1-1" });
            table.addRow(new Object[] { 2, "matsuki", "2010-12-3" });

            BeanFileAdapter fileAdapter = new ExcelBeanFileAdapter(StringUtils.EMPTY, Meta.excel());

            Object result = MethodUtils.invokeMethod(fileAdapter, true, this.methodName, table, 0,
                    ProfileBase.ProfileExtends.class);

            Assertions.assertNotNull(result);
            Assertions.assertInstanceOf(ProfileBase.ProfileExtends.class, result);
            ProfileBase.ProfileExtends profile = ProfileBase.ProfileExtends.class.cast(result);
            Assertions.assertEquals(1, profile.id);
            Assertions.assertEquals("penguin", profile.name);
            Assertions.assertEquals(LocalDate.of(2002, 1, 1), profile.birthday);
        }

        @Test
        @DisplayName("型パラメータを持つフィールドが存在するJava Beanが初期化されること")
        @Tag("normal")
        void createTypeParameterBean() throws Exception {
            DefaultTable table = new DefaultTable("Profile", new Column[] { new Column("clazz", DataType.UNKNOWN) });
            table.addRow(new Object[] { "java.util.List" });

            BeanFileAdapter fileAdapter = new ExcelBeanFileAdapter(StringUtils.EMPTY, Meta.excel());

            Object result = MethodUtils.invokeMethod(fileAdapter, true, this.methodName, table, 0,
                    TypeParameterField.class);

            Assertions.assertNotNull(result);
            Assertions.assertInstanceOf(TypeParameterField.class, result);
            TypeParameterField clazz = TypeParameterField.class.cast(result);
            Assertions.assertEquals(java.util.List.class, clazz.clazz);
        }

        @Test
        @DisplayName("旧来の日時型を持つフィールドが存在するテスト用Java Beanが日時を表す文字列により初期化されること")
        @Tag("normal")
        void createLegacyDateTypeBeanFromString() throws Exception {
            DefaultTable table = new DefaultTable("Profile",
                    new Column[] { new Column("date", DataType.UNKNOWN), new Column("sqlDate", DataType.UNKNOWN),
                            new Column("sqlTime", DataType.UNKNOWN), new Column("sqlTimestamp", DataType.UNKNOWN),
                            new Column("calendar", DataType.UNKNOWN) });
            table.addRow(new Object[] { "2020-1-2 1:23:45.678", "2021-2-3", "2:33:44", "2022-3-4 3:44:55.987654321",
                    "2022-12-31 23:59:59.999" });

            BeanFileAdapter fileAdapter = new ExcelBeanFileAdapter(StringUtils.EMPTY, Meta.excel());

            Object result = MethodUtils.invokeMethod(fileAdapter, true, this.methodName, table, 0,
                    LegacyDateType.class);

            Assertions.assertNotNull(result);
            Assertions.assertInstanceOf(LegacyDateType.class, result);
            LegacyDateType legacyDateType = LegacyDateType.class.cast(result);
            Assertions.assertEquals(new java.util.Date(java.sql.Timestamp.valueOf("2020-1-2 1:23:45.678").getTime()),
                    legacyDateType.date);
            Assertions.assertEquals(java.sql.Date.valueOf("2021-2-3"), legacyDateType.sqlDate);
            Assertions.assertEquals(java.sql.Time.valueOf("2:33:44"), legacyDateType.sqlTime);
            Assertions.assertEquals(java.sql.Timestamp.valueOf("2022-3-4 3:44:55.987654321"),
                    legacyDateType.sqlTimestamp);
            Assertions.assertEquals(DateUtils.toCalendar(java.sql.Timestamp.valueOf("2022-12-31 23:59:59.999")),
                    legacyDateType.calendar);
        }

        @Test
        @DisplayName("旧来の日時型を持つフィールドが存在するテスト用Java Beanが日時を表すオブジェクトにより初期化されること")
        @Tag("normal")
        void createLegacyDateTypeBeanFromObject() throws Exception {
            DefaultTable table = new DefaultTable("Profile",
                    new Column[] { new Column("date", DataType.UNKNOWN), new Column("sqlDate", DataType.UNKNOWN),
                            new Column("sqlTime", DataType.UNKNOWN), new Column("sqlTimestamp", DataType.UNKNOWN),
                            new Column("calendar", DataType.UNKNOWN) });
            table.addRow(new Object[] { java.sql.Timestamp.valueOf("2020-1-2 1:23:45.678"),
                    java.sql.Timestamp.valueOf("2021-2-3 1:23:45.678"),
                    java.sql.Timestamp.valueOf("2020-1-2 2:33:44.567"),
                    java.sql.Timestamp.valueOf("2022-3-4 3:44:55.987654321"),
                    java.sql.Timestamp.valueOf("2022-12-31 23:59:59.999") });

            BeanFileAdapter fileAdapter = new ExcelBeanFileAdapter(StringUtils.EMPTY, Meta.excel());

            Object result = MethodUtils.invokeMethod(fileAdapter, true, this.methodName, table, 0,
                    LegacyDateType.class);

            Assertions.assertNotNull(result);
            Assertions.assertInstanceOf(LegacyDateType.class, result);
            LegacyDateType legacyDateType = LegacyDateType.class.cast(result);
            Assertions.assertEquals(new java.util.Date(java.sql.Timestamp.valueOf("2020-1-2 1:23:45.678").getTime()),
                    legacyDateType.date);
            Assertions.assertEquals(java.sql.Date.valueOf("2021-2-3"), legacyDateType.sqlDate);
            Assertions.assertEquals(java.sql.Time.valueOf("2:33:44"), legacyDateType.sqlTime);
            Assertions.assertEquals(java.sql.Timestamp.valueOf("2022-3-4 3:44:55.987654321"),
                    legacyDateType.sqlTimestamp);
            Assertions.assertEquals(DateUtils.toCalendar(java.sql.Timestamp.valueOf("2022-12-31 23:59:59.999")),
                    legacyDateType.calendar);

            // java.sql.Dateの場合、時間部分は0クリアされていることを確認
            Calendar cal = Calendar.getInstance();
            cal.setTime(legacyDateType.sqlDate);
            Assertions.assertEquals(0, cal.get(Calendar.HOUR_OF_DAY));
            Assertions.assertEquals(0, cal.get(Calendar.MINUTE));
            Assertions.assertEquals(0, cal.get(Calendar.SECOND));
            Assertions.assertEquals(0, cal.get(Calendar.MILLISECOND));

            // java.sql.Timeの場合、日付部分は1970-1-1、ミリ秒部分は0でクリアされていることを確認
            cal.setTime(legacyDateType.sqlTime);
            Assertions.assertEquals(1970, cal.get(Calendar.YEAR));
            Assertions.assertEquals(0, cal.get(Calendar.MONTH));
            Assertions.assertEquals(1, cal.get(Calendar.DAY_OF_MONTH));
            Assertions.assertEquals(0, cal.get(Calendar.MILLISECOND));
        }

        @Test
        @DisplayName("Date and Time APIの日時型を持つフィールドが存在するテスト用Java Beanが日時を表す文字列により初期化されること")
        @Tag("normal")
        void createNewDateTypeBeanFromString() throws Exception {
            DefaultTable table = new DefaultTable("Profile",
                    new Column[] { new Column("localDate", DataType.UNKNOWN), new Column("localTime", DataType.UNKNOWN),
                            new Column("localDateTime", DataType.UNKNOWN), new Column("instant", DataType.UNKNOWN) });
            table.addRow(new Object[] { "2021-2-3", "2:33:44", "2022-3-4 3:44:55.987654321",
                    "2022-12-31 23:59:59.987654321", });

            BeanFileAdapter fileAdapter = new ExcelBeanFileAdapter(StringUtils.EMPTY, Meta.excel());

            Object result = MethodUtils.invokeMethod(fileAdapter, true, this.methodName, table, 0, NewDateType.class);

            Assertions.assertNotNull(result);
            Assertions.assertInstanceOf(NewDateType.class, result);
            NewDateType dateType = NewDateType.class.cast(result);
            Assertions.assertEquals(LocalDate.of(2021, 2, 3), dateType.localDate);
            Assertions.assertEquals(LocalTime.of(2, 33, 44), dateType.localTime);
            Assertions.assertEquals(LocalDateTime.of(2022, 3, 4, 3, 44, 55, 987654321), dateType.localDateTime);
            Assertions.assertEquals(java.sql.Timestamp.valueOf("2022-12-31 23:59:59.987654321").toInstant(),
                    dateType.instant);
        }

        @Test
        @DisplayName("Date and Time APIの日時型を持つフィールドが存在するテスト用Java Beanが日時を表すオブジェクトにより初期化されること")
        @Tag("normal")
        void createNewDateTypeBeanFromObject() throws Exception {
            DefaultTable table = new DefaultTable("Profile",
                    new Column[] { new Column("localDate", DataType.UNKNOWN), new Column("localTime", DataType.UNKNOWN),
                            new Column("localDateTime", DataType.UNKNOWN), new Column("instant", DataType.UNKNOWN) });
            table.addRow(new Object[] { java.sql.Timestamp.valueOf("2021-2-3 1:23:45.678"),
                    java.sql.Timestamp.valueOf("2020-1-2 2:33:44.567000123"),
                    java.sql.Timestamp.valueOf("2022-3-4 3:44:55.987654321"),
                    java.sql.Timestamp.valueOf("2022-12-31 23:59:59.987654321") });

            BeanFileAdapter fileAdapter = new ExcelBeanFileAdapter(StringUtils.EMPTY, Meta.excel());

            Object result = MethodUtils.invokeMethod(fileAdapter, true, this.methodName, table, 0, NewDateType.class);

            Assertions.assertNotNull(result);
            Assertions.assertInstanceOf(NewDateType.class, result);
            NewDateType dateType = NewDateType.class.cast(result);
            Assertions.assertEquals(LocalDate.of(2021, 2, 3), dateType.localDate);
            Assertions.assertEquals(LocalTime.of(2, 33, 44, 567000123), dateType.localTime);
            Assertions.assertEquals(LocalDateTime.of(2022, 3, 4, 3, 44, 55, 987654321), dateType.localDateTime);
            Assertions.assertEquals(java.sql.Timestamp.valueOf("2022-12-31 23:59:59.987654321").toInstant(),
                    dateType.instant);
        }

        @Test
        @DisplayName("初期化するJava Beanのフィールド型が総称型の場合エラーとなること")
        @Tag("error")
        void createBeanGenericType() throws Exception {
            DefaultTable table = new DefaultTable("Profile", new Column[] { new Column("id", DataType.UNKNOWN),
                    new Column("name", DataType.UNKNOWN), new Column("birthday", DataType.UNKNOWN) });
            table.addRow(new Object[] { 1, "penguin", "2002-1-1" });
            table.addRow(new Object[] { 2, "matsuki", "2010-12-3" });

            BeanFileAdapter fileAdapter = new ExcelBeanFileAdapter(StringUtils.EMPTY, Meta.excel());

            InvocationTargetException e = Assertions.assertThrows(InvocationTargetException.class, () -> MethodUtils
                    .invokeMethod(fileAdapter, true, this.methodName, table, 1, GenericTypeClass.class));

            Assertions.assertInstanceOf(IllegalArgumentException.class, e.getCause());
            Assertions.assertTrue(StringUtils.containsIgnoreCase(e.getCause().getMessage(), "E id"));
        }

        @Test
        @DisplayName("初期化するJava Beanのフィールド型が総称型の配列の場合エラーとなること")
        @Tag("error")
        void createBeanGenericArrayType() throws Exception {
            DefaultTable table = new DefaultTable("Profile", new Column[] { new Column("id", DataType.UNKNOWN),
                    new Column("name", DataType.UNKNOWN), new Column("birthday", DataType.UNKNOWN) });
            table.addRow(new Object[] { 1, "penguin", "2002-1-1" });
            table.addRow(new Object[] { 2, "matsuki", "2010-12-3" });

            BeanFileAdapter fileAdapter = new ExcelBeanFileAdapter(StringUtils.EMPTY, Meta.excel());

            InvocationTargetException e = Assertions.assertThrows(InvocationTargetException.class, () -> MethodUtils
                    .invokeMethod(fileAdapter, true, this.methodName, table, 1, GenericArrayTypeClass.class));

            Assertions.assertInstanceOf(IllegalArgumentException.class, e.getCause());
            Assertions.assertTrue(StringUtils.containsIgnoreCase(e.getCause().getMessage(), "E[] id"));
        }

        @Test
        @DisplayName("初期化するJava Beanのフィールド型が総称型の配列の場合エラーとなること")
        @Tag("error")
        void createBeanNotSupportType() throws Exception {
            DefaultTable table = new DefaultTable("Profile", new Column[] { new Column("id", DataType.UNKNOWN),
                    new Column("name", DataType.UNKNOWN), new Column("birthday", DataType.UNKNOWN) });
            table.addRow(new Object[] { 1, "penguin", "2002-1-1" });
            table.addRow(new Object[] { 2, "matsuki", "2010-12-3" });

            BeanFileAdapter fileAdapter = new ExcelBeanFileAdapter(StringUtils.EMPTY, Meta.excel());

            InvocationTargetException e = Assertions.assertThrows(InvocationTargetException.class, () -> MethodUtils
                    .invokeMethod(fileAdapter, true, this.methodName, table, 1, NotSupportTypeClass.class));

            Assertions.assertInstanceOf(IllegalArgumentException.class, e.getCause());
            Assertions.assertTrue(StringUtils.containsIgnoreCase(e.getCause().getMessage(),
                    "java.util.concurrent.atomic.AtomicLong id"));
        }
    }

    /**
     * 初期化ファイルに定義されていない列が存在するテスト用Java Beanクラス
     */
    public static class ProfileNoInitField {
        public long id;
        public String hobby;
    }

    /**
     * 継承関係をもつテスト用Java Beanクラス
     */
    public static class ProfileBase {
        public long id;
        public String name;

        public static class ProfileExtends extends ProfileBase {
            public LocalDate birthday;
        }
    }

    /**
     * 型パラメータを持つフィールドが存在するテスト用Java Beanクラス
     */
    public static class TypeParameterField {
        public Class<?> clazz;
    }

    /**
     * 旧来の日時型を持つフィールドが存在するテスト用Java Beanクラス
     */
    public static class LegacyDateType {
        public java.util.Date date;
        public java.sql.Date sqlDate;
        public java.sql.Time sqlTime;
        public java.sql.Timestamp sqlTimestamp;

        public Calendar calendar;
    }

    /**
     * Date and Time APIの日時型を持つフィールドが存在するテスト用Java Beanクラス
     */
    public static class NewDateType {
        public LocalDate localDate;
        public LocalTime localTime;
        public LocalDateTime localDateTime;
        public Instant instant;
    }

    /**
     * 総称型のフィールドが存在するテスト用Java Beanクラス
     */
    public static class GenericTypeClass<E> {
        public E id;
    }

    /**
     * 総称型配列のフィールドが存在するテスト用Java Beanクラス
     */
    public static class GenericArrayTypeClass<E> {
        public E[] id;
    }

    /**
     * サポートしていない型のフィールドが存在するテスト用Java Beanクラス
     */
    public static class NotSupportTypeClass {
        public AtomicLong id;
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
}
