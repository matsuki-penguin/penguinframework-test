package org.penguinframework.example.test.database.init.datetime;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.jupiter.api.Nested;
import org.penguinframework.test.database.annotation.DatabaseMeta;
import org.penguinframework.test.type.Platform;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("hsqldb")
@DatabaseMeta(platform = Platform.HSQLDB)
class DateTimeTypeInitHsqldbTest extends DateTimeTypeInitAbstractTest {
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
        return from;
    }

    @Override
    LocalTime toDatabaseScaleTime(LocalTime from) {
        return from;
    }

    @Override
    LocalDateTime toDatabaseScaleDateTime(LocalDateTime from) {
        return from;
    }
}
