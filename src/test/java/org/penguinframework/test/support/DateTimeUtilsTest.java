package org.penguinframework.test.support;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@DisplayName("DateTimeUtils")
public class DateTimeUtilsTest {
    @Nested
    @DisplayName("Calendar toCalendar(String valueFrom)")
    class toCalendar_String {

        @Test
        @DisplayName("日付と時間の日時文字列からCalendarクラスに変換できること")
        @Tag("normal")
        void dateTimeFormat() throws Exception {
            Calendar cal = DateTimeUtils.toCalendar("2020-12-31 23:59:59.999");

            Calendar expected = Calendar.getInstance();
            expected.set(2020, 12 - 1, 31, 23, 59, 59);
            expected.set(Calendar.MILLISECOND, 999);
            Assertions.assertEquals(expected, cal);
        }

        @Test
        @DisplayName("0がトリムされたの日時文字列からCalendarクラスに変換できること")
        @Tag("normal")
        void zeroTrimFormat() throws Exception {
            Calendar cal = DateTimeUtils.toCalendar("2020-1-2 3:4:5.6");

            Calendar expected = Calendar.getInstance();
            expected.set(2020, 1 - 1, 2, 3, 4, 5);
            expected.set(Calendar.MILLISECOND, 600); // ミリ秒は後続の0がトリムされる
            Assertions.assertEquals(expected, cal);
        }

        @Test
        @DisplayName("ナノ秒まで指定された日時文字列からCalendarクラスに変換できること")
        @Tag("normal")
        void nanoSecondFormat() throws Exception {
            Calendar cal = DateTimeUtils.toCalendar("2020-12-31 23:59:59.999999999");

            Calendar expected = Calendar.getInstance();
            expected.set(2020, 12 - 1, 31, 23, 59, 59);
            expected.set(Calendar.MILLISECOND, 999);
            Assertions.assertEquals(expected, cal);
        }

        @Test
        @DisplayName("秒以降が省略されたフォーマットの日時文字列からCalendarクラスに変換できること")
        @Tag("normal")
        void minuteFormat() throws Exception {
            Calendar cal = DateTimeUtils.toCalendar("2020-12-31 23:59");

            Calendar expected = Calendar.getInstance();
            expected.set(2020, 12 - 1, 31, 23, 59, 0);
            expected.set(Calendar.MILLISECOND, 0);
            Assertions.assertEquals(expected, cal);
        }

        @Test
        @DisplayName("分以降が省略されたフォーマットの日時文字列からCalendarクラスに変換するとエラーが発生すること")
        @Tag("error")
        void hourFormat() throws Exception {
            IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class,
                    () -> DateTimeUtils.toCalendar("2020-12-31 23"));
            Assertions.assertEquals(DateTimeParseException.class, e.getCause().getClass());
        }

        @Test
        @DisplayName("日付部分のみのフォーマットの日時文字列からCalendarクラスに変換するとエラーが発生すること")
        @Tag("error")
        void dateFormat() throws Exception {
            IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class,
                    () -> DateTimeUtils.toCalendar("2020-12-31"));
            Assertions.assertEquals(DateTimeParseException.class, e.getCause().getClass());
        }

        @Test
        @DisplayName("時間部分のみのフォーマットの日時文字列からCalendarクラスに変換するとエラーが発生すること")
        @Tag("error")
        void timeFormat() throws Exception {
            IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class,
                    () -> DateTimeUtils.toCalendar("23:59:59.999"));
            Assertions.assertEquals(DateTimeParseException.class, e.getCause().getClass());
        }

        @Test
        @DisplayName("nullからCalendarクラスに変換するとエラーが発生すること")
        @Tag("error")
        void nullFormat() throws Exception {
            IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class,
                    () -> DateTimeUtils.toCalendar(null));
            Assertions.assertEquals(NullPointerException.class, e.getCause().getClass());
        }
    }

    @Nested
    @DisplayName("Date toDate(String valueFrom)")
    class toDate_String {

        @Test
        @DisplayName("日付と時間の日時文字列からDateクラスに変換できること")
        @Tag("normal")
        void dateTimeFormat() throws Exception {
            Date date = DateTimeUtils.toDate("2020-12-31 23:59:59.999");

            Calendar expected = Calendar.getInstance();
            expected.set(2020, 12 - 1, 31, 23, 59, 59);
            expected.set(Calendar.MILLISECOND, 999);
            Assertions.assertEquals(expected.getTime(), date);
        }

        @Test
        @DisplayName("0がトリムされたの日時文字列からDateクラスに変換できること")
        @Tag("normal")
        void zeroTrimFormat() throws Exception {
            Date date = DateTimeUtils.toDate("2020-1-2 3:4:5.6");

            Calendar expected = Calendar.getInstance();
            expected.set(2020, 1 - 1, 2, 3, 4, 5);
            expected.set(Calendar.MILLISECOND, 600); // ミリ秒は後続の0がトリムされる
            Assertions.assertEquals(expected.getTime(), date);
        }

        @Test
        @DisplayName("ナノ秒まで指定された日時文字列からDateクラスに変換できること")
        @Tag("normal")
        void nanoSecondFormat() throws Exception {
            Date date = DateTimeUtils.toDate("2020-12-31 23:59:59.999999999");

            Calendar expected = Calendar.getInstance();
            expected.set(2020, 12 - 1, 31, 23, 59, 59);
            expected.set(Calendar.MILLISECOND, 999);
            Assertions.assertEquals(expected.getTime(), date);
        }

        @Test
        @DisplayName("秒以降が省略されたフォーマットの日時文字列からDateクラスに変換できること")
        @Tag("normal")
        void minuteFormat() throws Exception {
            Date date = DateTimeUtils.toDate("2020-12-31 23:59");

            Calendar expected = Calendar.getInstance();
            expected.set(2020, 12 - 1, 31, 23, 59, 0);
            expected.set(Calendar.MILLISECOND, 0);
            Assertions.assertEquals(expected.getTime(), date);
        }

        @Test
        @DisplayName("分以降が省略されたフォーマットの日時文字列からDateクラスに変換するとエラーが発生すること")
        @Tag("error")
        void hourFormat() throws Exception {
            IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class,
                    () -> DateTimeUtils.toDate("2020-12-31 23"));
            Assertions.assertEquals(DateTimeParseException.class, e.getCause().getClass());
        }

        @Test
        @DisplayName("日付部分のみのフォーマットの日時文字列からDateクラスに変換するとエラーが発生すること")
        @Tag("error")
        void dateFormat() throws Exception {
            IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class,
                    () -> DateTimeUtils.toDate("2020-12-31"));
            Assertions.assertEquals(DateTimeParseException.class, e.getCause().getClass());
        }

        @Test
        @DisplayName("時間部分のみのフォーマットの日時文字列からDateクラスに変換するとエラーが発生すること")
        @Tag("error")
        void timeFormat() throws Exception {
            IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class,
                    () -> DateTimeUtils.toDate("23:59:59.999"));
            Assertions.assertEquals(DateTimeParseException.class, e.getCause().getClass());
        }

        @Test
        @DisplayName("nullからDateクラスに変換するとエラーが発生すること")
        @Tag("error")
        void nullFormat() throws Exception {
            IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class,
                    () -> DateTimeUtils.toDate(null));
            Assertions.assertEquals(NullPointerException.class, e.getCause().getClass());
        }
    }

    @Nested
    @DisplayName("LocalDate toLocalDate(String valueFrom)")
    class toLocalDate_String {

        @Test
        @DisplayName("日付と時間の日時文字列からLocalDateクラスに変換するとエラーが発生すること")
        @Tag("error")
        void dateTimeFormat() throws Exception {
            IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class,
                    () -> DateTimeUtils.toLocalDate("2020-12-31 23:59:59.999"));
            Assertions.assertEquals(DateTimeParseException.class, e.getCause().getClass());
        }

        @Test
        @DisplayName("0がトリムされたの日付文字列からLocalDateクラスに変換できること")
        @Tag("normal")
        void zeroTrimFormat() throws Exception {
            LocalDate localDate = DateTimeUtils.toLocalDate("2020-1-2");

            LocalDate expected = LocalDate.of(2020, 1, 2);
            Assertions.assertEquals(expected, localDate);
        }

        @Test
        @DisplayName("ナノ秒まで指定された日時文字列からLocalDateクラスに変換するとエラーが発生すること")
        @Tag("error")
        void nanoSecondFormat() throws Exception {
            IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class,
                    () -> DateTimeUtils.toLocalDate("2020-12-31 23:59:59.999999999"));
            Assertions.assertEquals(DateTimeParseException.class, e.getCause().getClass());
        }

        @Test
        @DisplayName("秒以降が省略されたフォーマットの日時文字列からLocalDateクラスに変換するとエラーが発生すること")
        @Tag("error")
        void minuteFormat() throws Exception {
            IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class,
                    () -> DateTimeUtils.toLocalDate("2020-12-31 23:59"));
            Assertions.assertEquals(DateTimeParseException.class, e.getCause().getClass());
        }

        @Test
        @DisplayName("分以降が省略されたフォーマットの日時文字列からLocalDateクラスに変換するとエラーが発生すること")
        @Tag("error")
        void hourFormat() throws Exception {
            IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class,
                    () -> DateTimeUtils.toLocalDate("2020-12-31 23"));
            Assertions.assertEquals(DateTimeParseException.class, e.getCause().getClass());
        }

        @Test
        @DisplayName("日付部分のみのフォーマットの日時文字列からLocalDateクラスに変換できること")
        @Tag("normal")
        void dateFormat() throws Exception {
            LocalDate localDate = DateTimeUtils.toLocalDate("2020-12-31");

            LocalDate expected = LocalDate.of(2020, 12, 31);
            Assertions.assertEquals(expected, localDate);
        }

        @Test
        @DisplayName("時間部分のみのフォーマットの日時文字列からLocalDateクラスに変換するとエラーが発生すること")
        @Tag("error")
        void timeFormat() throws Exception {
            IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class,
                    () -> DateTimeUtils.toLocalDate("23:59:59.999"));
            Assertions.assertEquals(DateTimeParseException.class, e.getCause().getClass());
        }

        @Test
        @DisplayName("nullからLocalDateクラスに変換するとエラーが発生すること")
        @Tag("error")
        void nullFormat() throws Exception {
            IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class,
                    () -> DateTimeUtils.toLocalDate(null));
            Assertions.assertEquals(NullPointerException.class, e.getCause().getClass());
        }
    }

    @Nested
    @DisplayName("Date toLocalTime(String valueFrom)")
    class toLocalTime_String {

        @Test
        @DisplayName("日付と時間の日時文字列からLocalTimeクラスに変換するとエラーが発生すること")
        @Tag("error")
        void dateTimeFormat() throws Exception {
            IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class,
                    () -> DateTimeUtils.toLocalTime("2020-12-31 23:59:59.999"));
            Assertions.assertEquals(DateTimeParseException.class, e.getCause().getClass());
        }

        @Test
        @DisplayName("0がトリムされたの日時文字列からLocalTimeクラスに変換できること")
        @Tag("normal")
        void zeroTrimFormat() throws Exception {
            LocalTime localTime = DateTimeUtils.toLocalTime("3:4:5.6");

            LocalTime expected = LocalTime.of(3, 4, 5, 600000000);
            Assertions.assertEquals(expected, localTime);
        }

        @Test
        @DisplayName("ナノ秒まで指定された日時文字列からLocalTimeクラスに変換できること")
        @Tag("normal")
        void nanoSecondFormat() throws Exception {
            LocalTime localTime = DateTimeUtils.toLocalTime("23:59:59.999999999");

            LocalTime expected = LocalTime.of(23, 59, 59, 999999999);
            Assertions.assertEquals(expected, localTime);
        }

        @Test
        @DisplayName("秒以降が省略されたフォーマットの日時文字列からLocalTimeクラスに変換できること")
        @Tag("normal")
        void minuteFormat() throws Exception {
            LocalTime localTime = DateTimeUtils.toLocalTime("23:59");

            LocalTime expected = LocalTime.of(23, 59, 0, 0);
            Assertions.assertEquals(expected, localTime);
        }

        @Test
        @DisplayName("分以降が省略されたフォーマットの日時文字列からLocalTimeクラスに変換するとエラーが発生すること")
        @Tag("error")
        void hourFormat() throws Exception {
            IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class,
                    () -> DateTimeUtils.toLocalTime("2020-12-31 23"));
            Assertions.assertEquals(DateTimeParseException.class, e.getCause().getClass());
        }

        @Test
        @DisplayName("日付部分のみのフォーマットの日時文字列からLocalTimeクラスに変換するとエラーが発生すること")
        @Tag("error")
        void dateFormat() throws Exception {
            IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class,
                    () -> DateTimeUtils.toLocalTime("2020-12-31"));
            Assertions.assertEquals(DateTimeParseException.class, e.getCause().getClass());
        }

        @Test
        @DisplayName("時間部分のみのフォーマットの日時文字列からLocalTimeクラスに変換できること")
        @Tag("normal")
        void timeFormat() throws Exception {
            LocalTime localTime = DateTimeUtils.toLocalTime("23:59:59.999");

            LocalTime expected = LocalTime.of(23, 59, 59, 999000000);
            Assertions.assertEquals(expected, localTime);
        }

        @Test
        @DisplayName("nullからLocalTimeクラスに変換するとエラーが発生すること")
        @Tag("error")
        void nullFormat() throws Exception {
            IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class,
                    () -> DateTimeUtils.toLocalTime(null));
            Assertions.assertEquals(NullPointerException.class, e.getCause().getClass());
        }
    }

    @Nested
    @DisplayName("Date toLocalDateTime(String valueFrom)")
    class toLocalDateTime_String {

        @Test
        @DisplayName("日付と時間の日時文字列からLocalDateTimeクラスに変換できること")
        @Tag("normal")
        void dateTimeFormat() throws Exception {
            LocalDateTime localDateTime = DateTimeUtils.toLocalDateTime("2020-12-31 23:59:59.999");

            LocalDateTime expected = LocalDateTime.of(2020, 12, 31, 23, 59, 59, 999000000);
            Assertions.assertEquals(expected, localDateTime);
        }

        @Test
        @DisplayName("0がトリムされたの日時文字列からLocalDateTimeクラスに変換できること")
        @Tag("normal")
        void zeroTrimFormat() throws Exception {
            LocalDateTime localDateTime = DateTimeUtils.toLocalDateTime("2020-1-2 3:4:5.6");

            LocalDateTime expected = LocalDateTime.of(2020, 1, 2, 3, 4, 5, 600000000);
            Assertions.assertEquals(expected, localDateTime);
        }

        @Test
        @DisplayName("ナノ秒まで指定された日時文字列からLocalDateTimeクラスに変換できること")
        @Tag("normal")
        void nanoSecondFormat() throws Exception {
            LocalDateTime localDateTime = DateTimeUtils.toLocalDateTime("2020-12-31 23:59:59.999999999");

            LocalDateTime expected = LocalDateTime.of(2020, 12, 31, 23, 59, 59, 999999999);
            Assertions.assertEquals(expected, localDateTime);
        }

        @Test
        @DisplayName("秒以降が省略されたフォーマットの日時文字列からLocalDateTimeクラスに変換できること")
        @Tag("normal")
        void minuteFormat() throws Exception {
            LocalDateTime localDateTime = DateTimeUtils.toLocalDateTime("2020-12-31 23:59");

            LocalDateTime expected = LocalDateTime.of(2020, 12, 31, 23, 59, 0, 0);
            Assertions.assertEquals(expected, localDateTime);
        }

        @Test
        @DisplayName("分以降が省略されたフォーマットの日時文字列からLocalDateTimeクラスに変換するとエラーが発生すること")
        @Tag("error")
        void hourFormat() throws Exception {
            IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class,
                    () -> DateTimeUtils.toLocalDateTime("2020-12-31 23"));
            Assertions.assertEquals(DateTimeParseException.class, e.getCause().getClass());
        }

        @Test
        @DisplayName("日付部分のみのフォーマットの日時文字列からLocalDateTimeクラスに変換するとエラーが発生すること")
        @Tag("error")
        void dateFormat() throws Exception {
            IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class,
                    () -> DateTimeUtils.toLocalDateTime("2020-12-31"));
            Assertions.assertEquals(DateTimeParseException.class, e.getCause().getClass());
        }

        @Test
        @DisplayName("時間部分のみのフォーマットの日時文字列からLocalDateTimeクラスに変換するとエラーが発生すること")
        @Tag("error")
        void timeFormat() throws Exception {
            IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class,
                    () -> DateTimeUtils.toLocalDateTime("23:59:59.999"));
            Assertions.assertEquals(DateTimeParseException.class, e.getCause().getClass());
        }

        @Test
        @DisplayName("nullからLocalDateTimeクラスに変換するとエラーが発生すること")
        @Tag("error")
        void nullFormat() throws Exception {
            IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class,
                    () -> DateTimeUtils.toLocalDateTime(null));
            Assertions.assertEquals(NullPointerException.class, e.getCause().getClass());
        }
    }

    @Nested
    @DisplayName("Date toInstant(String valueFrom)")
    class toInstant_String {

        @Test
        @DisplayName("日付と時間の日時文字列からInstantクラスに変換できること")
        @Tag("normal")
        void dateTimeFormat() throws Exception {
            Instant instant = DateTimeUtils.toInstant("2020-12-31 23:59:59.999");

            Instant expected = java.sql.Timestamp.valueOf("2020-12-31 23:59:59.999000000").toInstant();
            Assertions.assertEquals(expected, instant);
        }

        @Test
        @DisplayName("0がトリムされたの日時文字列からInstantクラスに変換できること")
        @Tag("normal")
        void zeroTrimFormat() throws Exception {
            Instant instant = DateTimeUtils.toInstant("2020-1-2 3:4:5.6");

            Instant expected = java.sql.Timestamp.valueOf("2020-1-2 3:4:5.600000000").toInstant();
            Assertions.assertEquals(expected, instant);
        }

        @Test
        @DisplayName("ナノ秒まで指定された日時文字列からInstantクラスに変換できること")
        @Tag("normal")
        void nanoSecondFormat() throws Exception {
            Instant instant = DateTimeUtils.toInstant("2020-12-31 23:59:59.999999999");

            Instant expected = java.sql.Timestamp.valueOf("2020-12-31 23:59:59.999999999").toInstant();
            Assertions.assertEquals(expected, instant);
        }

        @Test
        @DisplayName("秒以降が省略されたフォーマットの日時文字列からInstantクラスに変換できること")
        @Tag("normal")
        void minuteFormat() throws Exception {
            Instant instant = DateTimeUtils.toInstant("2020-12-31 23:59");

            Instant expected = java.sql.Timestamp.valueOf("2020-12-31 23:59:0.0").toInstant();
            Assertions.assertEquals(expected, instant);
        }

        @Test
        @DisplayName("分以降が省略されたフォーマットの日時文字列からInstantクラスに変換するとエラーが発生すること")
        @Tag("error")
        void hourFormat() throws Exception {
            IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class,
                    () -> DateTimeUtils.toInstant("2020-12-31 23"));
            Assertions.assertEquals(DateTimeParseException.class, e.getCause().getClass());
        }

        @Test
        @DisplayName("日付部分のみのフォーマットの日時文字列からInstantクラスに変換するとエラーが発生すること")
        @Tag("error")
        void dateFormat() throws Exception {
            IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class,
                    () -> DateTimeUtils.toInstant("2020-12-31"));
            Assertions.assertEquals(DateTimeParseException.class, e.getCause().getClass());
        }

        @Test
        @DisplayName("時間部分のみのフォーマットの日時文字列からInstantクラスに変換するとエラーが発生すること")
        @Tag("error")
        void timeFormat() throws Exception {
            IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class,
                    () -> DateTimeUtils.toInstant("23:59:59.999"));
            Assertions.assertEquals(DateTimeParseException.class, e.getCause().getClass());
        }

        @Test
        @DisplayName("nullからInstantクラスに変換するとエラーが発生すること")
        @Tag("error")
        void nullFormat() throws Exception {
            IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class,
                    () -> DateTimeUtils.toInstant(null));
            Assertions.assertEquals(NullPointerException.class, e.getCause().getClass());
        }
    }

    @Nested
    @DisplayName("java.sql.Date toSqlDate(String valueFrom)")
    class toSqlDate_String {

        @Test
        @DisplayName("日付と時間の日時文字列からjava.sql.Dateクラスに変換するとエラーが発生すること")
        @Tag("error")
        void dateTimeFormat() throws Exception {
            IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class,
                    () -> DateTimeUtils.toSqlDate("2020-12-31 23:59:59.999"));
            Assertions.assertEquals(DateTimeParseException.class, e.getCause().getClass());
        }

        @Test
        @DisplayName("0がトリムされたの日付文字列からjava.sql.Dateクラスに変換できること")
        @Tag("normal")
        void zeroTrimFormat() throws Exception {
            java.sql.Date date = DateTimeUtils.toSqlDate("2020-1-2");

            LocalDate expected = LocalDate.of(2020, 1, 2);
            Assertions.assertEquals(java.sql.Date.valueOf(expected), date);
        }

        @Test
        @DisplayName("ナノ秒まで指定された日時文字列からjava.sql.Dateクラスに変換するとエラーが発生すること")
        @Tag("error")
        void nanoSecondFormat() throws Exception {
            IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class,
                    () -> DateTimeUtils.toSqlDate("2020-12-31 23:59:59.999999999"));
            Assertions.assertEquals(DateTimeParseException.class, e.getCause().getClass());
        }
    }

    @Nested
    @DisplayName("java.sql.Time toSqlTime(String valueFrom)")
    class toSqlTime_String {

        @Test
        @DisplayName("日付と時間の日時文字列からjava.sql.Timeクラスに変換するとエラーが発生すること")
        @Tag("error")
        void dateTimeFormat() throws Exception {
            IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class,
                    () -> DateTimeUtils.toSqlTime("2020-12-31 23:59:59.999"));
            Assertions.assertEquals(DateTimeParseException.class, e.getCause().getClass());
        }

        @Test
        @DisplayName("0がトリムされたの日時文字列からjava.sql.Timeクラスに変換できること")
        @Tag("normal")
        void zeroTrimFormat() throws Exception {
            java.sql.Time time = DateTimeUtils.toSqlTime("3:4:5.6");

            java.sql.Time expected = new java.sql.Time(
                    java.sql.Timestamp.valueOf(LocalDateTime.of(1970, 1, 1, 3, 4, 5, 600000000)).getTime());
            Assertions.assertEquals(expected, time);
        }

        @Test
        @DisplayName("ナノ秒まで指定された日時文字列からjava.sql.Timeクラスに変換できること")
        @Tag("normal")
        void nanoSecondFormat() throws Exception {
            java.sql.Time time = DateTimeUtils.toSqlTime("23:59:59.999999999");

            java.sql.Time expected = new java.sql.Time(
                    java.sql.Timestamp.valueOf(LocalDateTime.of(1970, 1, 1, 23, 59, 59, 999999999)).getTime());
            Assertions.assertEquals(expected, time);
        }

        @Test
        @DisplayName("秒以降が省略されたフォーマットの日時文字列からjava.sql.Timeクラスに変換できること")
        @Tag("normal")
        void minuteFormat() throws Exception {
            java.sql.Time time = DateTimeUtils.toSqlTime("23:59");

            java.sql.Time expected = new java.sql.Time(
                    java.sql.Timestamp.valueOf(LocalDateTime.of(1970, 1, 1, 23, 59, 0, 0)).getTime());
            Assertions.assertEquals(expected, time);
        }

        @Test
        @DisplayName("分以降が省略されたフォーマットの日時文字列からjava.sql.Timeクラスに変換するとエラーが発生すること")
        @Tag("error")
        void hourFormat() throws Exception {
            IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class,
                    () -> DateTimeUtils.toSqlTime("2020-12-31 23"));
            Assertions.assertEquals(DateTimeParseException.class, e.getCause().getClass());
        }

        @Test
        @DisplayName("日付部分のみのフォーマットの日時文字列からjava.sql.Timeクラスに変換するとエラーが発生すること")
        @Tag("error")
        void dateFormat() throws Exception {
            IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class,
                    () -> DateTimeUtils.toSqlTime("2020-12-31"));
            Assertions.assertEquals(DateTimeParseException.class, e.getCause().getClass());
        }

        @Test
        @DisplayName("時間部分のみのフォーマットの日時文字列からjava.sql.Timeクラスに変換できること")
        @Tag("normal")
        void timeFormat() throws Exception {
            java.sql.Time time = DateTimeUtils.toSqlTime("23:59:59.999");

            java.sql.Time expected = new java.sql.Time(
                    java.sql.Timestamp.valueOf(LocalDateTime.of(1970, 1, 1, 23, 59, 59, 999000000)).getTime());
            Assertions.assertEquals(expected, time);
        }

        @Test
        @DisplayName("nullからjava.sql.Timeクラスに変換するとエラーが発生すること")
        @Tag("error")
        void nullFormat() throws Exception {
            IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class,
                    () -> DateTimeUtils.toSqlTime(null));
            Assertions.assertEquals(NullPointerException.class, e.getCause().getClass());
        }
    }

    @Nested
    @DisplayName("java.sql.Timestamp toSqlTimestamp(String valueFrom)")
    class toSqlTimestamp_String {

        @Test
        @DisplayName("日付と時間の日時文字列からjava.sql.Timestampクラスに変換できること")
        @Tag("normal")
        void dateTimeFormat() throws Exception {
            java.sql.Timestamp timestamp = DateTimeUtils.toSqlTimestamp("2020-12-31 23:59:59.999");

            java.sql.Timestamp expected = java.sql.Timestamp
                    .valueOf(LocalDateTime.of(2020, 12, 31, 23, 59, 59, 999000000));
            Assertions.assertEquals(expected, timestamp);
        }

        @Test
        @DisplayName("0がトリムされたの日時文字列からjava.sql.Timestampクラスに変換できること")
        @Tag("normal")
        void zeroTrimFormat() throws Exception {
            java.sql.Timestamp timestamp = DateTimeUtils.toSqlTimestamp("2020-1-2 3:4:5.6");

            java.sql.Timestamp expected = java.sql.Timestamp.valueOf(LocalDateTime.of(2020, 1, 2, 3, 4, 5, 600000000));
            Assertions.assertEquals(expected, timestamp);
        }

        @Test
        @DisplayName("ナノ秒まで指定された日時文字列からjava.sql.Timestampクラスに変換できること")
        @Tag("normal")
        void nanoSecondFormat() throws Exception {
            java.sql.Timestamp timestamp = DateTimeUtils.toSqlTimestamp("2020-12-31 23:59:59.999999999");

            java.sql.Timestamp expected = java.sql.Timestamp
                    .valueOf(LocalDateTime.of(2020, 12, 31, 23, 59, 59, 999999999));
            Assertions.assertEquals(expected, timestamp);
        }

        @Test
        @DisplayName("秒以降が省略されたフォーマットの日時文字列からjava.sql.Timestampクラスに変換できること")
        @Tag("normal")
        void minuteFormat() throws Exception {
            java.sql.Timestamp timestamp = DateTimeUtils.toSqlTimestamp("2020-12-31 23:59");

            java.sql.Timestamp expected = java.sql.Timestamp.valueOf(LocalDateTime.of(2020, 12, 31, 23, 59, 0, 0));
            Assertions.assertEquals(expected, timestamp);
        }

        @Test
        @DisplayName("分以降が省略されたフォーマットの日時文字列からjava.sql.Timestampクラスに変換するとエラーが発生すること")
        @Tag("error")
        void hourFormat() throws Exception {
            IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class,
                    () -> DateTimeUtils.toSqlTimestamp("2020-12-31 23"));
            Assertions.assertEquals(DateTimeParseException.class, e.getCause().getClass());
        }

        @Test
        @DisplayName("日付部分のみのフォーマットの日時文字列からjava.sql.Timestampクラスに変換するとエラーが発生すること")
        @Tag("error")
        void dateFormat() throws Exception {
            IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class,
                    () -> DateTimeUtils.toSqlTimestamp("2020-12-31"));
            Assertions.assertEquals(DateTimeParseException.class, e.getCause().getClass());
        }

        @Test
        @DisplayName("時間部分のみのフォーマットの日時文字列からjava.sql.Timestampクラスに変換するとエラーが発生すること")
        @Tag("error")
        void timeFormat() throws Exception {
            IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class,
                    () -> DateTimeUtils.toSqlTimestamp("23:59:59.999"));
            Assertions.assertEquals(DateTimeParseException.class, e.getCause().getClass());
        }

        @Test
        @DisplayName("nullからjava.sql.Timestampクラスに変換するとエラーが発生すること")
        @Tag("error")
        void nullFormat() throws Exception {
            IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class,
                    () -> DateTimeUtils.toSqlTimestamp(null));
            Assertions.assertEquals(NullPointerException.class, e.getCause().getClass());
        }
    }

    @Nested
    @DisplayName("String toString(LocalTime valueFrom)")
    class toString_LocalTime {

        @Test
        @DisplayName("LocalTimeクラスから0がトリムされたの日時文字列に変換できること")
        @Tag("normal")
        void zeroTrimFormat() throws Exception {
            String actual = DateTimeUtils.toString(LocalTime.of(3, 4, 5, 600000000));

            Assertions.assertEquals("3:4:5.6", actual);
        }

        @Test
        @DisplayName("LocalTimeクラスからナノ秒まで指定された日時文字列に変換できること")
        @Tag("normal")
        void nanoSecondFormat() throws Exception {
            String actual = DateTimeUtils.toString(LocalTime.of(23, 59, 59, 999999999));

            Assertions.assertEquals("23:59:59.999999999", actual);
        }

        @Test
        @DisplayName("LocalTimeクラスから秒以降が省略されたフォーマットの日時文字列に変換できること")
        @Tag("normal")
        void minuteFormat() throws Exception {
            String actual = DateTimeUtils.toString(LocalTime.of(23, 59, 0, 0));

            Assertions.assertEquals("23:59:0", actual);
        }

        @Test
        @DisplayName("LocalTimeクラスから時間部分のみのフォーマットの日時文字列に変換できること")
        @Tag("normal")
        void timeFormat() throws Exception {
            String actual = DateTimeUtils.toString(LocalTime.of(23, 59, 59, 999000000));

            Assertions.assertEquals("23:59:59.999", actual);
        }

        @Test
        @DisplayName("nullから日時文字列に変換するとエラーが発生すること")
        @Tag("error")
        void nullFormat() throws Exception {
            IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class,
                    () -> DateTimeUtils.toString(null));
            Assertions.assertEquals(NullPointerException.class, e.getCause().getClass());
        }
    }
}
