package org.penguinframework.test.dao;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.penguinframework.example.Application;
import org.penguinframework.example.dao.ProfileDao;
import org.penguinframework.example.dao.entity.ProfileEntity;
import org.penguinframework.test.database.annotation.TableValueSource;
import org.penguinframework.test.extension.PenguinExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith({ SpringExtension.class, PenguinExtension.class })
@SpringBootTest(classes = Application.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
class ProfileDaoTransactionTest {
    @Autowired
    private ProfileDao profileDao;

    @Test
    @Order(1)
    @DisplayName("Excelファイルから初期化したprofileテーブルのすべてのレコードが取得されること")
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
    @Order(2)
    @DisplayName("初期化していないprofileテーブルのすべてのレコードが取得されること")
    void testFindAllAfterInit() {
        List<ProfileEntity> result = this.profileDao.findAll();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(1, result.get(0).getId());
        Assertions.assertEquals("init", result.get(0).getName());
        Assertions.assertEquals(LocalDate.of(1900, 1, 1), result.get(0).getBirthday());
    }

    @Test
    @Order(3)
    @DisplayName("Updateしたprofileテーブルのすべてのレコードが取得されること")
    void testFindByIdMethodUpdate() {
        ProfileEntity profile = new ProfileEntity();
        profile.setName("update");
        profile.setBirthday(LocalDate.of(2021, 10, 10));
        this.profileDao.updateById(1L, profile);

        ProfileEntity result = this.profileDao.findById(1L);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.getId());
        Assertions.assertEquals("update", result.getName());
        Assertions.assertEquals(LocalDate.of(2021, 10, 10), result.getBirthday());
    }

    @Test
    @Order(4)
    @DisplayName("初期化していないprofileテーブルのすべてのレコードが取得されること")
    void testFindByIdAfterUpdate() {
        List<ProfileEntity> result = this.profileDao.findAll();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(1, result.get(0).getId());
        Assertions.assertEquals("init", result.get(0).getName());
        Assertions.assertEquals(LocalDate.of(1900, 1, 1), result.get(0).getBirthday());
    }
}
