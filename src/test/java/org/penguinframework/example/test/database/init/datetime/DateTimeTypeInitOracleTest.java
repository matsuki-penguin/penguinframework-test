package org.penguinframework.example.test.database.init.datetime;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.jupiter.api.Nested;
import org.penguinframework.test.database.annotation.DatabaseMeta;
import org.penguinframework.test.type.Platform;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("oracle")
@DatabaseMeta(platform = Platform.ORACLE10)
class DateTimeTypeInitOracleTest extends DateTimeTypeInitAbstractTest {
    @Nested
    class ExcelFile extends ExcelFileAbstract {
        @Nested
        class ClassInitTest extends ClassInitAbstractTest {
        }

        @Nested
        class ClassNoInitTest extends ClassNoInitAbstractTest {
        }
    }

    @Nested
    class CsvFile extends CsvFileAbstract {
        @Nested
        class ClassInitTest extends ClassInitAbstractTest {
        }

        @Nested
        class ClassNoInitTest extends ClassNoInitAbstractTest {
        }
    }

    @Override
    LocalDate toDatabaseScaleDate(LocalDate from) {
        // ORACLEは日付型がないので検証対象外
        return null;
    }

    @Override
    LocalTime toDatabaseScaleTime(LocalTime from) {
        // ORACLEは時刻型がないので検証対象外
        return null;
    }

    @Override
    LocalDateTime toDatabaseScaleDateTime(LocalDateTime from) {
        return from;
    }
}
