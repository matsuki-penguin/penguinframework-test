package org.penguinframework.test.support.converter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.time.format.SignStyle;
import java.time.temporal.ChronoField;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.converters.AbstractConverter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LocalTimeConverter extends AbstractConverter {
    private static final String PACKAGE = "org.penguinframework.test.support.converter.";

    private transient Log log;

    @Override
    protected <T> T convertToType(Class<T> type, Object value) throws Throwable {

        final Class<?> sourceType = value.getClass();

        // Handle java.sql.Timestamp
        if (value instanceof java.sql.Timestamp) {
            return this.toDate(type, java.sql.Timestamp.class.cast(value));
        }

        // Handle Date (includes java.sql.Date & java.sql.Time)
        if (value instanceof Date) {
            final Date date = (Date) value;
            return this.toTime(type, date.getTime());
        }

        // Handle Calendar
        if (value instanceof Calendar) {
            final Calendar calendar = (Calendar) value;
            return this.toTime(type, calendar.getTime().getTime());
        }

        // Handle Long
        if (value instanceof Long) {
            final Long longObj = (Long) value;
            return this.toTime(type, longObj.longValue());
        }

        // Convert all other types to String & handle
        final String stringValue = value.toString().trim();
        if (stringValue.length() == 0) {
            return this.handleMissing(type);
        }

        // Default String conversion
        return this.toDate(type, stringValue);
    }

    @Override
    protected Class<?> getDefaultType() {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    private <T> T toTime(final Class<T> type, final long value) {

        // java.time.LocalTime
        if (type.equals(LocalTime.class)) {
            return type.cast(new java.sql.Timestamp(value).toLocalDateTime().toLocalTime());
        }

        final String msg = this.toString(this.getClass()) + " cannot handle conversion to '" + this.toString(type)
                + "'";
        if (this.log().isWarnEnabled()) {
            this.log().warn("    " + msg);
        }
        throw new ConversionException(msg);
    }

    private <T> T toDate(final Class<T> type, final String value) {
        // java.time.LocalTime
        if (type.equals(LocalTime.class)) {
            DateTimeFormatter isoLocalTime = new DateTimeFormatterBuilder()
                    .appendValue(ChronoField.HOUR_OF_DAY, 1, 2, SignStyle.NOT_NEGATIVE).appendLiteral(':')
                    .appendValue(ChronoField.MINUTE_OF_HOUR, 1, 2, SignStyle.NOT_NEGATIVE).optionalStart()
                    .appendLiteral(':').appendValue(ChronoField.SECOND_OF_MINUTE, 1, 2, SignStyle.NOT_NEGATIVE)
                    .optionalStart().appendFraction(ChronoField.NANO_OF_SECOND, 0, 9, true).toFormatter()
                    .withResolverStyle(ResolverStyle.STRICT);
            try {
                return type.cast(LocalTime.parse(value, isoLocalTime));
            } catch (final DateTimeParseException e) {
                throw new ConversionException(
                        "String must be in ISO_LOCAL_TIME format [HH:mm:ss.[f...]] to create a java.time.LocalTime : ");
            }
        }

        final String msg = this.toString(this.getClass()) + " does not support default String to '"
                + this.toString(type) + "' conversion.";
        if (this.log().isWarnEnabled()) {
            this.log().warn("    " + msg);
            this.log().warn("    (N.B. Re-configure Converter or use alternative implementation)");
        }
        throw new ConversionException(msg);
    }

    private <T> T toDate(final Class<T> type, final java.sql.Timestamp value) {
        return type.cast(value.toLocalDateTime().toLocalTime());
    }

    String toString(final Class<?> type) {
        String typeName = null;
        if (type == null) {
            typeName = "null";
        } else if (type.isArray()) {
            Class<?> elementType = type.getComponentType();
            int count = 1;
            while (elementType.isArray()) {
                elementType = elementType.getComponentType();
                count++;
            }
            typeName = elementType.getName();
            for (int i = 0; i < count; i++) {
                typeName += "[]";
            }
        } else {
            typeName = type.getName();
        }
        if (typeName.startsWith("java.lang.") || typeName.startsWith("java.util.")
                || typeName.startsWith("java.math.")) {
            typeName = typeName.substring("java.lang.".length());
        } else if (typeName.startsWith(LocalTimeConverter.PACKAGE)) {
            typeName = typeName.substring(LocalTimeConverter.PACKAGE.length());
        }
        return typeName;
    }

    Log log() {
        if (this.log == null) {
            this.log = LogFactory.getLog(this.getClass());
        }
        return this.log;
    }
}
