package org.penguinframework.test.support.datatype;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Calendar;

import org.dbunit.dataset.ITable;
import org.dbunit.dataset.datatype.AbstractDataType;
import org.dbunit.dataset.datatype.DataType;
import org.dbunit.dataset.datatype.TypeCastException;
import org.penguinframework.test.support.DateTimeUtils;

public class ExtTimestampDataType extends AbstractDataType {

    ExtTimestampDataType() {
        super("TIMESTAMP", Types.TIMESTAMP, LocalDateTime.class, false);
    }

    ////////////////////////////////////////////////////////////////////////////
    // DataType class

    @Override
    public Object typeCast(Object value) throws TypeCastException {
        if (value == null || value == ITable.NO_VALUE) {
            return null;
        }

        if (value instanceof LocalDateTime) {
            return value;
        } else if (value instanceof java.sql.Timestamp) {
            return java.sql.Timestamp.class.cast(value).toLocalDateTime();
        } else if (value instanceof java.util.Date) {
            return new java.sql.Timestamp(java.util.Date.class.cast(value).getTime()).toLocalDateTime();
        } else if (value instanceof Calendar) {
            return new java.sql.Timestamp(Calendar.class.cast(value).getTime().getTime()).toLocalDateTime();
        } else if (value instanceof Instant) {
            return java.sql.Timestamp.from(Instant.class.cast(value)).toLocalDateTime();
        } else if (value instanceof Long) {
            return new java.sql.Timestamp(Long.class.cast(value).longValue()).toLocalDateTime();
        }

        if (value instanceof String) {
            String stringValue = String.class.cast(value);

            if (DataType.isExtendedSyntax(stringValue)) {
                // Relative date.
                try {
                    return DataType.RELATIVE_DATE_TIME_PARSER.parse(stringValue);
                } catch (IllegalArgumentException | DateTimeParseException e) {
                    throw new TypeCastException(value, this, e);
                }
            }

            try {
                return DateTimeUtils.toLocalDateTime(stringValue);
            } catch (IllegalArgumentException e) {
                throw new TypeCastException(value, this, e);
            }
        }

        throw new TypeCastException(value, this);
    }

    @Override
    public boolean isDateTime() {
        return DataType.TIMESTAMP.isDateTime();
    }

    @Override
    public Object getSqlValue(int column, ResultSet resultSet) throws SQLException, TypeCastException {
        Object sqlValue = DataType.TIMESTAMP.getSqlValue(column, resultSet);
        if (sqlValue == null) {
            return sqlValue;
        }
        return java.sql.Timestamp.class.cast(sqlValue).toLocalDateTime();
    }

    @Override
    public void setSqlValue(Object value, int column, PreparedStatement statement)
            throws SQLException, TypeCastException {
        DataType.TIMESTAMP.setSqlValue(java.sql.Timestamp.valueOf(LocalDateTime.class.cast(this.typeCast(value))),
                column, statement);
    }
}
