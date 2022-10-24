package org.penguinframework.example.test.database.assertion.basic;

import org.junit.jupiter.api.Nested;
import org.penguinframework.test.database.annotation.DatabaseMeta;
import org.penguinframework.test.type.Platform;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("hsqldb")
@DatabaseMeta(platform = Platform.HSQLDB)
class BasicTypeAssertionHsqldbTest extends BasicTypeAssertionAbstractTest {
    @Nested
    class ExcelFile extends ExcelFileAbstract {
    }

    @Nested
    class CsvFile extends CsvFileAbstract {
    }
}
