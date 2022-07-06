package org.penguinframework.test.support.converter;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.converters.AbstractConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractDateTimeConverter extends AbstractConverter {
    private static final String PACKAGE = CalendarConverter.class.getPackage().getName();

    /** Logger object. */
    private static final Logger logger = LoggerFactory.getLogger(AbstractDateTimeConverter.class);

    protected java.sql.Timestamp toTimestamp(Object value) {
        if (value == null) {
            return null;
        }

        // Handle java.sql.Timestamp
        if (value instanceof java.sql.Timestamp) {
            return java.sql.Timestamp.class.cast(value);
        }

        // Handle Date (includes java.sql.Date & java.sql.Time)
        if (value instanceof Date) {
            return new java.sql.Timestamp(Date.class.cast(value).getTime());
        }

        // Handle Calendar
        if (value instanceof Calendar) {
            return new java.sql.Timestamp(Calendar.class.cast(value).getTime().getTime());
        }

        // Handle Long
        if (value instanceof Long) {
            return new java.sql.Timestamp(Long.class.cast(value).longValue());
        }

        // Handle LocalDateTime
        if (value instanceof LocalDateTime) {
            return java.sql.Timestamp.valueOf(LocalDateTime.class.cast(value));
        }

        return null;
    }

    protected <T> T fromTimestamp(Class<T> type, java.sql.Timestamp value) {
        final String msg = this.toStringType(this.getClass()) + " cannot handle conversion to '"
                + this.toStringType(type) + "'";
        if (AbstractDateTimeConverter.logger.isWarnEnabled()) {
            AbstractDateTimeConverter.logger.warn("    {}", msg);
        }
        throw new ConversionException(msg);
    }

    protected <T> T fromString(Class<T> type, String value) {
        final String msg = this.toStringType(this.getClass()) + " does not support default String to '"
                + this.toStringType(type) + "' conversion.";
        if (AbstractDateTimeConverter.logger.isWarnEnabled()) {
            AbstractDateTimeConverter.logger.warn("    {}", msg);
            AbstractDateTimeConverter.logger
                    .warn("    (N.B. Re-configure Converter or use alternative implementation)");
        }
        throw new ConversionException(msg);
    }

    private String toStringType(final Class<?> type) {
        if (type == null) {
            return "null";
        }

        Class<?> typeClass = type;
        StringBuilder className = new StringBuilder();
        while (typeClass.isArray()) {
            typeClass = typeClass.getComponentType();
            className.append("[]");
        }

        String packageName = typeClass.getPackage().getName();
        if (packageName.startsWith("java.lang") || packageName.startsWith("java.util")
                || packageName.startsWith("java.math") || packageName.startsWith(AbstractDateTimeConverter.PACKAGE)) {
            className.insert(0, typeClass.getSimpleName());
        } else {
            className.insert(0, typeClass.getName());
        }

        return className.toString();
    }
}
