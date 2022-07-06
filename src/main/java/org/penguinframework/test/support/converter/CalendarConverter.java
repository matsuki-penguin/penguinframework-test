package org.penguinframework.test.support.converter;

import java.util.Calendar;

import org.penguinframework.test.support.DateTimeUtils;

public class CalendarConverter extends AbstractDateTimeConverter {

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
        return Calendar.class;
    }

    @Override
    protected <T> T fromTimestamp(Class<T> type, java.sql.Timestamp value) {
        // java.util.Calendar
        if (type.equals(Calendar.class)) {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(value.getTime());
            return type.cast(cal);
        }

        return super.fromTimestamp(type, value);
    }

    @Override
    protected <T> T fromString(Class<T> type, String value) {
        // java.util.Calendar
        if (type.equals(Calendar.class)) {
            return type.cast(DateTimeUtils.toCalendar(value));
        }

        return super.fromString(type, value);
    }
}
