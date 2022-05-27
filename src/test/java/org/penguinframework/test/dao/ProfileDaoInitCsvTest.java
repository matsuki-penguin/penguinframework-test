package org.penguinframework.test.dao;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.penguinframework.example.Application;
import org.penguinframework.example.dao.ProfileDao;
import org.penguinframework.example.dao.entity.ProfileEntity;
import org.penguinframework.test.database.annotation.CsvMeta;
import org.penguinframework.test.database.annotation.TableValueSource;
import org.penguinframework.test.database.annotation.type.OperationType;
import org.penguinframework.test.extension.PenguinExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith({ SpringExtension.class, PenguinExtension.class })
@SpringBootTest(classes = Application.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TableValueSource("profile.csv")
class ProfileDaoInitCsvTest {
    @Autowired
    private ProfileDao profileDao;

    @Test
    @DisplayName("クラス単位でCSVファイルから初期化したprofileテーブルのすべてのレコードが取得されること")
    void testFindAllClassInit() {
        List<ProfileEntity> result = this.profileDao.findAll();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(1, result.get(0).getId());
        Assertions.assertEquals("penguin", result.get(0).getName());
        Assertions.assertEquals(LocalDate.of(2002, 1, 1), result.get(0).getBirthday());
    }

    @Test
    @DisplayName("メソッド単位でCSVファイルから初期化したprofileテーブルのすべてのレコードが取得されること")
    @TableValueSource(value = "prepare_for_method.csv", csvMeta = @CsvMeta(table = "profile"))
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
    @DisplayName("クラス単位とメソッド単位でCSVファイルから初期化したprofileテーブルのすべてのレコードが取得されること")
    @TableValueSource(value = "prepare_for_method.csv", csvMeta = @CsvMeta(table = "profile"), operation = OperationType.INSERT)
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
