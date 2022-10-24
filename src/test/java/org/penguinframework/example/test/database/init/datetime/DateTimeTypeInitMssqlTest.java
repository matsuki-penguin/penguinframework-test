package org.penguinframework.example.test.database.init.datetime;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.jupiter.api.Nested;
import org.penguinframework.test.database.annotation.DatabaseMeta;
import org.penguinframework.test.type.Platform;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("mssql")
@DatabaseMeta(platform = Platform.MSSQL)
class DateTimeTypeInitMssqlTest extends DateTimeTypeInitAbstractTest {
    private static final int NANO_SECOND_PRECISION_SCALE = 7;
    private static final BigDecimal ROUND_DOWN_CALC_VALUE = BigDecimal
            .valueOf(Math.pow(10, 9 - DateTimeTypeInitMssqlTest.NANO_SECOND_PRECISION_SCALE));

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
        // MSSQLの時刻型で扱える精度は100ナノ秒で四捨五入
        int nano = BigDecimal.valueOf(from.getNano())
                .divide(DateTimeTypeInitMssqlTest.ROUND_DOWN_CALC_VALUE, RoundingMode.HALF_UP)
                .multiply(DateTimeTypeInitMssqlTest.ROUND_DOWN_CALC_VALUE).intValue();
        return LocalTime.of(from.getHour(), from.getMinute(), from.getSecond(), nano);
    }

    @Override
    LocalDateTime toDatabaseScaleDateTime(LocalDateTime from) {
        // MSSQLの日時型で扱える精度は100ナノ秒で四捨五入
        int nano = BigDecimal.valueOf(from.getNano())
                .divide(DateTimeTypeInitMssqlTest.ROUND_DOWN_CALC_VALUE, RoundingMode.HALF_UP)
                .multiply(DateTimeTypeInitMssqlTest.ROUND_DOWN_CALC_VALUE).intValue();
        return LocalDateTime.of(from.getYear(), from.getMonth(), from.getDayOfMonth(), from.getHour(), from.getMinute(),
                from.getSecond(), nano);
    }
}
