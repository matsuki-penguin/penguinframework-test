package org.penguinframework.test.support;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.chrono.IsoChronology;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.time.format.SignStyle;
import java.time.temporal.ChronoField;
import java.util.Calendar;

public class DateTimeUtils {
    private static final String STANDARD_DATE_TIME_FORMAT_ERROR = "String format must be 'yyyy-MM-dd HH:mm[:ss[.fffffffff]]'.";
    private static final String STANDARD_DATE_FORMAT_ERROR = "String format must be 'yyyy-MM-dd'.";
    private static final String STANDARD_TIME_FORMAT_ERROR = "String format must be 'HH:mm[:ss[.fffffffff]]'.";

    private static final DateTimeFormatter standardLocalDateFormatter = new DateTimeFormatterBuilder()
            .appendValue(ChronoField.YEAR, 4, 10, SignStyle.EXCEEDS_PAD).appendLiteral('-')
            .appendValue(ChronoField.MONTH_OF_YEAR, 1, 2, SignStyle.NOT_NEGATIVE).appendLiteral('-')
            .appendValue(ChronoField.DAY_OF_MONTH, 1, 2, SignStyle.NOT_NEGATIVE).toFormatter()
            .withResolverStyle(ResolverStyle.STRICT).withChronology(IsoChronology.INSTANCE);

    private static final DateTimeFormatter standardLocalTimeFormatter = new DateTimeFormatterBuilder()
            .appendValue(ChronoField.HOUR_OF_DAY, 1, 2, SignStyle.NOT_NEGATIVE).appendLiteral(':')
            .appendValue(ChronoField.MINUTE_OF_HOUR, 1, 2, SignStyle.NOT_NEGATIVE).optionalStart().appendLiteral(':')
            .appendValue(ChronoField.SECOND_OF_MINUTE, 1, 2, SignStyle.NOT_NEGATIVE).optionalStart()
            .appendFraction(ChronoField.NANO_OF_SECOND, 0, 9, true).toFormatter()
            .withResolverStyle(ResolverStyle.STRICT);

    private static final DateTimeFormatter standardLocalDateTimeFormatter = new DateTimeFormatterBuilder()
            .append(DateTimeUtils.standardLocalDateFormatter).appendLiteral(' ')
            .append(DateTimeUtils.standardLocalTimeFormatter).toFormatter().withResolverStyle(ResolverStyle.STRICT)
            .withChronology(IsoChronology.INSTANCE);

    private DateTimeUtils() {
    }

    public static Calendar toCalendar(String valueFrom) {
        if (valueFrom == null) {
            throw new IllegalArgumentException(DateTimeUtils.STANDARD_DATE_TIME_FORMAT_ERROR,
                    new NullPointerException());
        }

        LocalDateTime valueTo;
        try {
            valueTo = LocalDateTime.parse(valueFrom, DateTimeUtils.standardLocalDateTimeFormatter);
        } catch (final DateTimeParseException e) {
            throw new IllegalArgumentException(DateTimeUtils.STANDARD_DATE_TIME_FORMAT_ERROR, e);
        }

        long millis = Timestamp.valueOf(valueTo).getTime();
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(millis);
        return cal;
    }

    public static java.util.Date toDate(String valueFrom) {
        if (valueFrom == null) {
            throw new IllegalArgumentException(DateTimeUtils.STANDARD_DATE_TIME_FORMAT_ERROR,
                    new NullPointerException());
        }

        LocalDateTime valueTo;
        try {
            valueTo = LocalDateTime.parse(valueFrom, DateTimeUtils.standardLocalDateTimeFormatter);
        } catch (final DateTimeParseException e) {
            throw new IllegalArgumentException(DateTimeUtils.STANDARD_DATE_TIME_FORMAT_ERROR, e);
        }

        return new java.util.Date(Timestamp.valueOf(valueTo).getTime());
    }

    public static LocalDate toLocalDate(String valueFrom) {
        if (valueFrom == null) {
            throw new IllegalArgumentException(DateTimeUtils.STANDARD_DATE_FORMAT_ERROR, new NullPointerException());
        }

        LocalDate valueTo;
        try {
            valueTo = LocalDate.parse(valueFrom, DateTimeUtils.standardLocalDateFormatter);
        } catch (final DateTimeParseException e) {
            throw new IllegalArgumentException(DateTimeUtils.STANDARD_DATE_FORMAT_ERROR, e);
        }

        return valueTo;
    }

    public static LocalTime toLocalTime(String valueFrom) {
        if (valueFrom == null) {
            throw new IllegalArgumentException(DateTimeUtils.STANDARD_TIME_FORMAT_ERROR, new NullPointerException());
        }

        LocalTime valueTo;
        try {
            valueTo = LocalTime.parse(valueFrom, DateTimeUtils.standardLocalTimeFormatter);
        } catch (final DateTimeParseException e) {
            throw new IllegalArgumentException(DateTimeUtils.STANDARD_TIME_FORMAT_ERROR, e);
        }

        return valueTo;
    }

    public static LocalDateTime toLocalDateTime(String valueFrom) {
        if (valueFrom == null) {
            throw new IllegalArgumentException(DateTimeUtils.STANDARD_DATE_TIME_FORMAT_ERROR,
                    new NullPointerException());
        }

        LocalDateTime valueTo;
        try {
            valueTo = LocalDateTime.parse(valueFrom, DateTimeUtils.standardLocalDateTimeFormatter);
        } catch (final DateTimeParseException e) {
            throw new IllegalArgumentException(DateTimeUtils.STANDARD_DATE_TIME_FORMAT_ERROR, e);
        }

        return valueTo;
    }

    public static Instant toInstant(String valueFrom) {
        if (valueFrom == null) {
            throw new IllegalArgumentException(DateTimeUtils.STANDARD_DATE_TIME_FORMAT_ERROR,
                    new NullPointerException());
        }

        LocalDateTime valueTo;
        try {
            valueTo = LocalDateTime.parse(valueFrom, DateTimeUtils.standardLocalDateTimeFormatter);
        } catch (final DateTimeParseException e) {
            throw new IllegalArgumentException(DateTimeUtils.STANDARD_DATE_TIME_FORMAT_ERROR, e);
        }

        return Timestamp.valueOf(valueTo).toInstant();
    }
}
