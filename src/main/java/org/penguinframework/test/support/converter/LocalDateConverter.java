package org.penguinframework.test.support.converter;

import java.time.LocalDate;

import org.penguinframework.test.support.DateTimeUtils;

public class LocalDateConverter extends AbstractDateTimeConverter {

    @Override
    protected <T> T convertToType(Class<T> type, Object value) throws Throwable {
        java.sql.Timestamp timestamp = this.toTimestamp(value);

        if (timestamp != null) {
            return this.fromTimestamp(type, timestamp);
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
        return LocalDate.class;
    }

    @Override
    protected <T> T fromTimestamp(Class<T> type, java.sql.Timestamp value) {
        // java.time.LocalDate
        if (type.equals(LocalDate.class)) {
            return type.cast(value.toLocalDateTime().toLocalDate());
        }

        return super.fromTimestamp(type, value);
    }

    @Override
    protected <T> T fromString(Class<T> type, String value) {
        // java.time.LocalDate
        if (type.equals(LocalDate.class)) {
            return type.cast(DateTimeUtils.toLocalDate(value));
        }

        return super.fromString(type, value);
    }
}
