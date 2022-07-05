package org.penguinframework.test.dao;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.penguinframework.example.Application;
import org.penguinframework.example.dao.AllTypeDao;
import org.penguinframework.example.dao.ProfileDao;
import org.penguinframework.example.dao.entity.AllTypeEntity;
import org.penguinframework.example.dao.entity.ProfileEntity;
import org.penguinframework.test.annotation.Load;
import org.penguinframework.test.database.annotation.TableValueSource;
import org.penguinframework.test.database.assertion.TableAssertion;
import org.penguinframework.test.extension.PenguinExtension;
import org.penguinframework.test.meta.Meta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith({ SpringExtension.class, PenguinExtension.class })
@SpringBootTest(classes = Application.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
class TableAssertionTest {

    @Nested
    @DisplayName("Excelファイルによる検証")
    class excel {
        @Autowired
        private ProfileDao profileDao;

        @Load
        private TableAssertion tableAssertion;

        @Test
        @DisplayName("初期化済みのテーブルを検証できること")
        void init() {
            this.tableAssertion.assertEquals("expected_profile_init.xlsx", "PROFILE");
        }

        @Test
        @DisplayName("Excelファイルで初期化済みのテーブルを検証できること")
        @TableValueSource("prepare_profile_init_annotation.xlsx")
        void initExcelFile() {
            this.tableAssertion.assertEquals("expected_profile_init_annotation.xlsx", "PROFILE");
        }

        @Test
        @DisplayName("insertしたテーブルを検証できること")
        void insert() {
            ProfileEntity profile = new ProfileEntity();
            profile.setId(2L);
            profile.setName("insert");
            profile.setBirthday(LocalDate.of(2010, 7, 7));
            this.profileDao.insert(profile);

            this.tableAssertion.assertEquals("expected_profile_insert.xlsx", "PROFILE");
        }

        @Test
        @DisplayName("updateしたテーブルを検証できること")
        void update() {
            ProfileEntity profile = new ProfileEntity();
            profile.setName("update");
            profile.setBirthday(LocalDate.of(2012, 12, 31));
            this.profileDao.updateById(1L, profile);

            this.tableAssertion.assertEquals("expected_profile_update.xlsx", "PROFILE");
        }

        @Nested
        @DisplayName("様々な型の列を含むテーブルのテスト")
        class all_type_table {

            @Autowired
            private AllTypeDao allTypeDao;

            @Load
            private TableAssertion tableAssertion;

            @Test
            @DisplayName("挿入したレコードを検証できること")
            void insert() {
                AllTypeEntity entity = new AllTypeEntity();
                entity.setIntType(123456789);
                entity.setBooleanType(Boolean.TRUE);
                entity.setTinyintType((byte) 127);
                entity.setSmallintType((short) 32767);
                entity.setBigintType(999999999L);
                entity.setIdentityType(1L);
                entity.setDecimalType(new BigDecimal("3.1415926535898"));
                entity.setDoubleType(3.141592D);
                entity.setRealType(3.14F);
                entity.setTimeType(java.sql.Time.valueOf("13:14:15")); // 期待値のファイルには「HH:mm:ss」の文字列、もしくはExcelの場合は日付なしの時間フォーマットで記述
                entity.setDateType(java.sql.Date.valueOf("2021-11-30"));
                entity.setTimestampType(java.sql.Timestamp.valueOf("2021-11-30 13:14:15"));
                entity.setBinaryType(new byte[] { 0x41, 0x42, 0x43 }); // 期待値のファイルには期待値「ABC」をbase64でエンコードしたものを記述
                entity.setOtherType(null);
                entity.setVarcharType("String!");
                entity.setVarchar_ignorecaseType("STRING!");
                entity.setCharType('c');
                entity.setBlobType(new byte[] { 0x61, 0x62, 0x63 }); // 期待値のファイルには期待値「abc」をbase64でエンコードしたものを記述
                entity.setClobType("Character LOB");
                entity.setUuidType(java.util.UUID.fromString("6779defb-6d49-4e2e-b3dd-95cd071cea5c")); // 期待値のファイルには期待値のUUIDのハイフンを除いた16進表記のバイナリをbase64でエンコードしたものを記述
                entity.setArrayType(null);

                this.allTypeDao.insert(entity);

                // H2 の OTHER, ARRAY 型の列はDBUnitのITable型に読み込まれないので、これらの型の列は期待値に含めない(検証できない)
                this.tableAssertion.assertEquals("expected_all_type.xlsx", "ALL_TYPE");
            }

            @Test
            @DisplayName("主キー以外をNULLで挿入したレコードを検証できること")
            void insertNull() {
                AllTypeEntity entity = new AllTypeEntity();
                entity.setIdentityType(1L);

                this.allTypeDao.insert(entity);

                // H2 の OTHER, ARRAY 型の列はDBUnitのITable型に読み込まれないので、これらの型の列は期待値に含めない(検証できない)
                this.tableAssertion.assertEquals("expected_all_type_null.xlsx", "ALL_TYPE");
            }
        }
    }

    @Nested
    @DisplayName("CSVファイルによる検証")
    class csv {
        @Autowired
        private ProfileDao profileDao;

        @Load
        private TableAssertion tableAssertion;

        @Test
        @DisplayName("初期化済みのテーブルを検証できること")
        void init() {
            this.tableAssertion.assertEquals("expected_profile_init.csv", "PROFILE");
        }

        @Test
        @DisplayName("Excelファイルで初期化済みのテーブルを検証できること")
        @TableValueSource("prepare_profile_init_annotation.xlsx")
        void initExcelFile() {
            this.tableAssertion.assertEquals("expected_profile_init_annotation.csv", "PROFILE");
        }

        @Test
        @DisplayName("insertしたテーブルを検証できること")
        void insert() {
            ProfileEntity profile = new ProfileEntity();
            profile.setId(2L);
            profile.setName("insert");
            profile.setBirthday(LocalDate.of(2010, 7, 7));
            this.profileDao.insert(profile);

            this.tableAssertion.assertEquals("expected_profile_insert.csv", "PROFILE");
        }

        @Test
        @DisplayName("updateしたテーブルを検証できること")
        void update() {
            ProfileEntity profile = new ProfileEntity();
            profile.setName("update");
            profile.setBirthday(LocalDate.of(2012, 12, 31));
            this.profileDao.updateById(1L, profile);

            this.tableAssertion.assertEquals("expected_profile_update.csv", "PROFILE");
        }

        @Test
        @DisplayName("updateしたテーブルをファイルエンコードを指定して検証できること")
        void updateEncoding() {
            ProfileEntity profile = new ProfileEntity();
            profile.setName("ぺんぎん");
            profile.setBirthday(LocalDate.of(2012, 12, 31));
            this.profileDao.updateById(1L, profile);

            this.tableAssertion.assertEquals("expected_profile_update_sjis.csv", Meta.csv().encoding("sjis"),
                    "PROFILE");
        }

        @Nested
        @DisplayName("様々な型の列を含むテーブルのテスト")
        class all_type_table {

            @Autowired
            private AllTypeDao allTypeDao;

            @Load
            private TableAssertion tableAssertion;

            @Test
            @DisplayName("挿入したレコードを検証できること")
            void insert() {
                AllTypeEntity entity = new AllTypeEntity();
                entity.setIntType(123456789);
                entity.setBooleanType(Boolean.TRUE);
                entity.setTinyintType((byte) 127);
                entity.setSmallintType((short) 32767);
                entity.setBigintType(999999999L);
                entity.setIdentityType(1L);
                entity.setDecimalType(new BigDecimal("3.1415926535898"));
                entity.setDoubleType(3.141592D);
                entity.setRealType(3.14F);
                entity.setTimeType(java.sql.Time.valueOf("13:14:15")); // 期待値のファイルには「HH:mm:ss」の文字列、もしくはExcelの場合は日付なしの時間フォーマットで記述
                entity.setDateType(java.sql.Date.valueOf("2021-11-30"));
                entity.setTimestampType(java.sql.Timestamp.valueOf("2021-11-30 13:14:15"));
                entity.setBinaryType(new byte[] { 0x41, 0x42, 0x43 }); // 期待値のファイルには期待値「ABC」をbase64でエンコードしたものを記述
                entity.setOtherType(null);
                entity.setVarcharType("String!");
                entity.setVarchar_ignorecaseType("STRING!");
                entity.setCharType('c');
                entity.setBlobType(new byte[] { 0x61, 0x62, 0x63 }); // 期待値のファイルには期待値「abc」をbase64でエンコードしたものを記述
                entity.setClobType("Character LOB");
                entity.setUuidType(java.util.UUID.fromString("6779defb-6d49-4e2e-b3dd-95cd071cea5c")); // 期待値のファイルには期待値のUUIDのハイフンを除いた16進表記のバイナリをbase64でエンコードしたものを記述
                entity.setArrayType(null);

                this.allTypeDao.insert(entity);

                // H2 の OTHER, ARRAY 型の列はDBUnitのITable型に読み込まれないので、これらの型の列は期待値に含めない(検証できない)
                this.tableAssertion.assertEquals("expected_all_type.csv", "ALL_TYPE");
            }

            @Test
            @DisplayName("主キー以外をNULLで挿入したレコードを検証できること")
            void insertNull() {
                AllTypeEntity entity = new AllTypeEntity();
                entity.setIdentityType(1L);

                this.allTypeDao.insert(entity);

                // H2 の OTHER, ARRAY 型の列はDBUnitのITable型に読み込まれないので、これらの型の列は期待値に含めない(検証できない)
                this.tableAssertion.assertEquals("expected_all_type_null.csv", "ALL_TYPE");
            }
        }
    }

    @Nested
    @DisplayName("@Loadアノテーションによるテストコンポーネントのロードの検証")
    class load {
        @Load
        private TableAssertion tableAssertionWithAnnotation;

        private TableAssertion tableAssertionWithoutAnnotation;

        @Load
        private Object objectWithAnnotation;

        @Test
        @DisplayName("@Loadアノテーションが指定されたTableAssertion型のフィールドが初期化されていること")
        void withAnnotationField() {
            Assertions.assertNotNull(this.tableAssertionWithAnnotation);
        }

        @Test
        @DisplayName("@Loadアノテーションが指定されていないTableAssertion型のフィールドが初期化されていないこと")
        void withoutAnnotationField() {
            Assertions.assertNull(this.tableAssertionWithoutAnnotation);
        }

        @Test
        @DisplayName("@Loadアノテーションが指定されたTableAssertion型以外のフィールドが初期化されていないこと")
        void withAnnotationFieldNotSupportClass() {
            Assertions.assertNull(this.objectWithAnnotation);
        }

        @Test
        @DisplayName("@Loadアノテーションが指定されたTableAssertion型の引数が初期化されていること")
        void withAnnotationParameter(@Load TableAssertion tableAssertionParameterWithAnnotation) {
            Assertions.assertNotNull(tableAssertionParameterWithAnnotation);
        }
    }
}
