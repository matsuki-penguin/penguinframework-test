package org.penguinframework.test.support.converter;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.converters.AbstractConverter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DateConverter extends AbstractConverter {
    private static final String PACKAGE = "org.penguinframework.test.support.converter.";

    private transient Log log;

    @Override
    protected <T> T convertToType(Class<T> type, Object value) throws Throwable {

        final Class<?> sourceType = value.getClass();

        // Handle java.sql.Timestamp
        if (value instanceof java.sql.Timestamp) {

            // ---------------------- JDK 1.3 Fix ----------------------
            // N.B. Prior to JDK 1.4 the Timestamp's getTime() method
            // didn't include the milliseconds. The following code
            // ensures it works consistently accross JDK versions
            final java.sql.Timestamp timestamp = (java.sql.Timestamp) value;
            long timeInMillis = ((timestamp.getTime() / 1000) * 1000);
            timeInMillis += timestamp.getNanos() / 1000000;
            // ---------------------- JDK 1.3 Fix ----------------------
            return this.toDate(type, timeInMillis);
        }

        // Handle Date (includes java.sql.Date & java.sql.Time)
        if (value instanceof Date) {
            final Date date = (Date) value;
            return this.toDate(type, date.getTime());
        }

        // Handle Calendar
        if (value instanceof Calendar) {
            final Calendar calendar = (Calendar) value;
            return this.toDate(type, calendar.getTime().getTime());
        }

        // Handle Long
        if (value instanceof Long) {
            final Long longObj = (Long) value;
            return this.toDate(type, longObj.longValue());
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

    private <T> T toDate(final Class<T> type, final long value) {

        // java.util.Date
        if (type.equals(Date.class)) {
            return type.cast(new Date(value));
        }

        final String msg = this.toString(this.getClass()) + " cannot handle conversion to '" + this.toString(type)
                + "'";
        if (this.log().isWarnEnabled()) {
            this.log().warn("    " + msg);
        }
        throw new ConversionException(msg);
    }

    private <T> T toDate(final Class<T> type, final String value) {
        // java.util.Date
        if (type.equals(Date.class)) {
            return type.cast(DateConverter.valueOf(value));
        }

        final String msg = this.toString(this.getClass()) + " does not support default String to '"
                + this.toString(type) + "' conversion.";
        if (this.log().isWarnEnabled()) {
            this.log().warn("    " + msg);
            this.log().warn("    (N.B. Re-configure Converter or use alternative implementation)");
        }
        throw new ConversionException(msg);
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
        } else if (typeName.startsWith(DateConverter.PACKAGE)) {
            typeName = typeName.substring(DateConverter.PACKAGE.length());
        }
        return typeName;
    }

    Log log() {
        if (this.log == null) {
            this.log = LogFactory.getLog(this.getClass());
        }
        return this.log;
    }

    public static Date valueOf(String s) {
        final int YEAR_LENGTH = 4;
        final int MONTH_LENGTH = 2;
        final int DAY_LENGTH = 2;
        final int MAX_MONTH = 12;
        final int MAX_DAY = 31;
        String date_s;
        String time_s;
        String nanos_s;
        int year = 0;
        int month = 0;
        int day = 0;
        int hour;
        int minute;
        int second;
        int a_nanos = 0;
        int firstDash;
        int secondDash;
        int dividingSpace;
        int firstColon = 0;
        int secondColon = 0;
        int period = 0;
        String formatError = "Timestamp format must be yyyy-mm-dd hh:mm:ss[.fffffffff]";
        String zeros = "000000000";
        String delimiterDate = "-";
        String delimiterTime = ":";

        if (s == null)
            throw new java.lang.IllegalArgumentException("null string");

        // Split the string into date and time components
        s = s.trim();
        dividingSpace = s.indexOf(' ');
        if (dividingSpace > 0) {
            date_s = s.substring(0, dividingSpace);
            time_s = s.substring(dividingSpace + 1);
        } else {
            throw new java.lang.IllegalArgumentException(formatError);
        }

        // Parse the date
        firstDash = date_s.indexOf('-');
        secondDash = date_s.indexOf('-', firstDash + 1);

        // Parse the time
        if (time_s == null)
            throw new java.lang.IllegalArgumentException(formatError);
        firstColon = time_s.indexOf(':');
        secondColon = time_s.indexOf(':', firstColon + 1);
        period = time_s.indexOf('.', secondColon + 1);

        // Convert the date
        boolean parsedDate = false;
        if ((firstDash > 0) && (secondDash > 0) && (secondDash < date_s.length() - 1)) {
            String yyyy = date_s.substring(0, firstDash);
            String mm = date_s.substring(firstDash + 1, secondDash);
            String dd = date_s.substring(secondDash + 1);
            if (yyyy.length() == YEAR_LENGTH && (mm.length() >= 1 && mm.length() <= MONTH_LENGTH)
                    && (dd.length() >= 1 && dd.length() <= DAY_LENGTH)) {
                year = Integer.parseInt(yyyy);
                month = Integer.parseInt(mm);
                day = Integer.parseInt(dd);

                if ((month >= 1 && month <= MAX_MONTH) && (day >= 1 && day <= MAX_DAY)) {
                    parsedDate = true;
                }
            }
        }
        if (!parsedDate) {
            throw new java.lang.IllegalArgumentException(formatError);
        }

        // Convert the time; default missing nanos
        if ((firstColon > 0) & (secondColon > 0) & (secondColon < time_s.length() - 1)) {
            hour = Integer.parseInt(time_s.substring(0, firstColon));
            minute = Integer.parseInt(time_s.substring(firstColon + 1, secondColon));
            if ((period > 0) & (period < time_s.length() - 1)) {
                second = Integer.parseInt(time_s.substring(secondColon + 1, period));
                nanos_s = time_s.substring(period + 1);
                if (nanos_s.length() > 9)
                    throw new java.lang.IllegalArgumentException(formatError);
                if (!Character.isDigit(nanos_s.charAt(0)))
                    throw new java.lang.IllegalArgumentException(formatError);
                nanos_s = nanos_s + zeros.substring(0, 9 - nanos_s.length());
                a_nanos = Integer.parseInt(nanos_s);
            } else if (period > 0) {
                throw new java.lang.IllegalArgumentException(formatError);
            } else {
                second = Integer.parseInt(time_s.substring(secondColon + 1));
            }
        } else {
            throw new java.lang.IllegalArgumentException(formatError);
        }

        return new Date(new Timestamp(year - 1900, month - 1, day, hour, minute, second, a_nanos).getTime());
    }
}
