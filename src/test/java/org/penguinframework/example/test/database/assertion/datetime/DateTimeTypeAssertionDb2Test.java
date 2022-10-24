package org.penguinframework.example.test.database.assertion.datetime;

import org.junit.jupiter.api.Nested;
import org.penguinframework.test.database.annotation.DatabaseMeta;
import org.penguinframework.test.type.Platform;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("db2")
@DatabaseMeta(platform = Platform.DB2)
class DateTimeTypeAssertionDb2Test extends DateTimeTypeAssertionDb2AbstractTest {
    @Nested
    class ExcelFile extends ExcelFileAbstract {
    }

    @Nested
    class CsvFile extends CsvFileAbstract {
    }
}
