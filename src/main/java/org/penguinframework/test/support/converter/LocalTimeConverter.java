package org.penguinframework.test.support.converter;

import java.time.LocalTime;

import org.penguinframework.test.support.DateTimeUtils;

public class LocalTimeConverter extends AbstractDateTimeConverter {

    @Override
    protected <T> T convertToType(Class<T> type, Object value) throws Throwable {
        java.sql.Timestamp timestamp = this.toTimestamp(value);

        if (timestamp != null) {
            this.fromTimestamp(type, timestamp);
        }

        // Convert all other types to String & handle
        final String stringValue = value.toString().trim();
        if (stringValue.isEmpty()) {
            return this.handleMissing(type);
        }

        // Default String conversion
        return this.fromString(type, stringValue);
    }

    @Override
    protected Class<?> getDefaultType() {
        return LocalTime.class;
    }

    @Override
    protected <T> T fromTimestamp(Class<T> type, java.sql.Timestamp value) {
        // java.time.LocalTime
        if (type.equals(LocalTime.class)) {
            return type.cast(value.toLocalDateTime().toLocalTime());
        }

        return super.fromTimestamp(type, value);
    }

    @Override
    protected <T> T fromString(Class<T> type, String value) {
        // java.time.LocalTime
        if (type.equals(LocalTime.class)) {
            return type.cast(DateTimeUtils.toLocalTime(value));
        }

        return super.fromString(type, value);
    }
}
