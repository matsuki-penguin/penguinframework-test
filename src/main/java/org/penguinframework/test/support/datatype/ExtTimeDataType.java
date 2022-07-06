package org.penguinframework.test.support.datatype;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.Instant;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Calendar;

import org.dbunit.dataset.ITable;
import org.dbunit.dataset.datatype.AbstractDataType;
import org.dbunit.dataset.datatype.DataType;
import org.dbunit.dataset.datatype.TypeCastException;
import org.penguinframework.test.support.DateTimeUtils;

public class ExtTimeDataType extends AbstractDataType {

    ExtTimeDataType() {
        super("TIME", Types.TIME, LocalTime.class, false);
    }

    ////////////////////////////////////////////////////////////////////////////
    // DataType class

    @Override
    public Object typeCast(Object value) throws TypeCastException {
        if (value == null || value == ITable.NO_VALUE) {
            return null;
        }

        if (value instanceof LocalTime) {
            return value;
        } else if (value instanceof java.sql.Time) {
            return java.sql.Time.class.cast(value).toLocalTime();
        } else if (value instanceof java.util.Date) {
            return new java.sql.Time(java.util.Date.class.cast(value).getTime()).toLocalTime();
        } else if (value instanceof Calendar) {
            return new java.sql.Time(Calendar.class.cast(value).getTime().getTime()).toLocalTime();
        } else if (value instanceof Instant) {
            return java.sql.Timestamp.from(Instant.class.cast(value)).toLocalDateTime().toLocalTime();
        } else if (value instanceof Long) {
            return new java.sql.Time(Long.class.cast(value).longValue()).toLocalTime();
        }

        if (value instanceof String) {
            String stringValue = String.class.cast(value);

            if (DataType.isExtendedSyntax(stringValue)) {
                // Relative date.
                try {
                    return DataType.RELATIVE_DATE_TIME_PARSER.parse(stringValue).toLocalTime();
                } catch (IllegalArgumentException | DateTimeParseException e) {
                    throw new TypeCastException(value, this, e);
                }
            }

            try {
                return DateTimeUtils.toLocalTime(stringValue);
            } catch (IllegalArgumentException e) {
                throw new TypeCastException(value, this, e);
            }
        }

        throw new TypeCastException(value, this);
    }

    @Override
    public boolean isDateTime() {
        return DataType.TIME.isDateTime();
    }

    @Override
    public Object getSqlValue(int column, ResultSet resultSet) throws SQLException, TypeCastException {
        return java.sql.Time.class.cast(DataType.TIME.getSqlValue(column, resultSet)).toLocalTime();
    }

    @Override
    public void setSqlValue(Object value, int column, PreparedStatement statement)
            throws SQLException, TypeCastException {
        DataType.TIME.setSqlValue(java.sql.Time.valueOf(LocalTime.class.cast(this.typeCast(value))), column, statement);
    }
}
