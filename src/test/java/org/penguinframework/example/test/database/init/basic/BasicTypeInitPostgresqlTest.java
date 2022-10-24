package org.penguinframework.example.test.database.init.basic;

import org.junit.jupiter.api.Nested;
import org.penguinframework.test.database.annotation.DatabaseMeta;
import org.penguinframework.test.type.Platform;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("postgresql")
@DatabaseMeta(platform = Platform.POSTGRESQL)
class BasicTypeInitPostgresqlTest extends BasicTypeInitAbstractTest {
    @Nested
    class Excel extends ExcelFileAbstract {
        @Nested
        class ClassInit extends ClassInitAbstract {
        }

        @Nested
        class ClassNoInit extends ClassNoInitAbstract {
        }
    }

    @Nested
    class CsvFile extends CsvFileAbstract {
        @Nested
        class ClassInit extends ClassInitAbstract {
        }

        @Nested
        class ClassNoInit extends ClassNoInitAbstract {
        }
    }
}
