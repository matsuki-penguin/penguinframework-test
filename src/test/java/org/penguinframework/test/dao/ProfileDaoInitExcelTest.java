package org.penguinframework.test.dao;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.penguinframework.example.dao.ProfileDao;
import org.penguinframework.example.dao.entity.ProfileEntity;
import org.penguinframework.test.database.annotation.TableValueSource;
import org.penguinframework.test.type.OperationType;
import org.springframework.beans.factory.annotation.Autowired;

abstract class ProfileDaoInitExcelTest {
    @Nested
    @DisplayName("クラス単位で初期化されたデータに対する検証")
    @TableValueSource("prepare_for_class.xlsx")
    @Disabled
    static class classUnitInit {
        @Autowired
        private ProfileDao profileDao;

        @Test
        @DisplayName("クラス単位でExcelファイルから初期化したprofileテーブルのすべてのレコードが取得されること")
        void testFindAllClassInit() {
            List<ProfileEntity> result = this.profileDao.findAll();

            Assertions.assertNotNull(result);
            Assertions.assertEquals(1, result.size());
            Assertions.assertEquals(1, result.get(0).getId());
            Assertions.assertEquals("penguin", result.get(0).getName());
            Assertions.assertEquals(LocalDate.of(2002, 1, 1), result.get(0).getBirthday());
        }

        @Test
        @DisplayName("メソッド単位でExcelファイルから初期化したprofileテーブルのすべてのレコードが取得されること")
        @TableValueSource(path = "prepare_for_method.xlsx")
        void testFindAllMethodInit() {
            List<ProfileEntity> result = this.profileDao.findAll();

            Assertions.assertNotNull(result);
            Assertions.assertEquals(2, result.size());
            Assertions.assertEquals(2, result.get(0).getId());
            Assertions.assertEquals("matsuki", result.get(0).getName());
            Assertions.assertEquals(LocalDate.of(2012, 12, 31), result.get(0).getBirthday());
            Assertions.assertEquals(3, result.get(1).getId());
            Assertions.assertEquals("penpen", result.get(1).getName());
            Assertions.assertEquals(LocalDate.of(2020, 2, 29), result.get(1).getBirthday());
        }

        @Test
        @DisplayName("クラス単位とメソッド単位でExcelファイルから初期化したprofileテーブルのすべてのレコードが取得されること")
        @TableValueSource(path = "prepare_for_method.xlsx", operation = OperationType.INSERT)
        void testFindAllClassMethodInit() {
            List<ProfileEntity> result = this.profileDao.findAll();

            Assertions.assertNotNull(result);
            Assertions.assertEquals(3, result.size());
            Assertions.assertEquals(1, result.get(0).getId());
            Assertions.assertEquals("penguin", result.get(0).getName());
            Assertions.assertEquals(LocalDate.of(2002, 1, 1), result.get(0).getBirthday());
            Assertions.assertEquals(2, result.get(1).getId());
            Assertions.assertEquals("matsuki", result.get(1).getName());
            Assertions.assertEquals(LocalDate.of(2012, 12, 31), result.get(1).getBirthday());
            Assertions.assertEquals(3, result.get(2).getId());
            Assertions.assertEquals("penpen", result.get(2).getName());
            Assertions.assertEquals(LocalDate.of(2020, 2, 29), result.get(2).getBirthday());
        }
    }

    @Nested
    @DisplayName("クラス単位で初期化されていないデータに対する検証")
    @Disabled
    static class classUnitNoInit {
        @Autowired
        private ProfileDao profileDao;

        @Test
        @DisplayName("データベースに格納済みのprofileテーブルのすべてのレコードが取得されること")
        void noInit() {
            List<ProfileEntity> result = this.profileDao.findAll();

            Assertions.assertNotNull(result);
            Assertions.assertEquals(1, result.size());
            Assertions.assertEquals(1, result.get(0).getId());
            Assertions.assertEquals("init", result.get(0).getName());
            Assertions.assertEquals(LocalDate.of(1900, 1, 1), result.get(0).getBirthday());
        }
    }
}
