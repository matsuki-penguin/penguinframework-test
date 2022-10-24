package org.penguinframework.example.test.database.assertion.basic;

import org.junit.jupiter.api.Nested;
import org.penguinframework.test.database.annotation.DatabaseMeta;
import org.penguinframework.test.type.Platform;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("mysql")
@DatabaseMeta(platform = Platform.MYSQL)
class BasicTypeAssertionMysqlTest extends BasicTypeAssertionAbstractTest {
    @Nested
    class ExcelFile extends ExcelFileAbstract {
    }

    @Nested
    class CsvFile extends CsvFileAbstract {
    }
}
