package org.penguinframework.test.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.extension.ExtendWith;
import org.penguinframework.example.Application;
import org.penguinframework.test.database.annotation.DatabaseMeta;
import org.penguinframework.test.extension.PenguinExtension;
import org.penguinframework.test.type.Platform;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith({ SpringExtension.class, PenguinExtension.class })
@SpringBootTest(classes = Application.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("db2")
@DatabaseMeta(platform = Platform.DB2)
class ProfileDaoInitExcelForDb2Test {
    @Nested
    @DisplayName("クラス単位で初期化されたデータに対する検証")
    class classUnitInit extends ProfileDaoInitExcelTest.classUnitInit {
    }

    @Nested
    @DisplayName("クラス単位で初期化されていないデータに対する検証")
    class classUnitNoInit extends ProfileDaoInitExcelTest.classUnitNoInit {
    }
}
