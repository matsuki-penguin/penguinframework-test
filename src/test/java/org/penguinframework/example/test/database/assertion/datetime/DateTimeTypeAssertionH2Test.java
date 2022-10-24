package org.penguinframework.example.test.database.assertion.datetime;

import org.junit.jupiter.api.Nested;
import org.penguinframework.test.database.annotation.DatabaseMeta;
import org.penguinframework.test.type.Platform;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("h2")
@DatabaseMeta(platform = Platform.H2)
class DateTimeTypeAssertionH2Test extends DateTimeTypeAssertionAbstractTest {
    @Nested
    class ExcelFile extends ExcelFileAbstract {
    }

    @Nested
    class CsvFile extends CsvFileAbstract {
    }
}
