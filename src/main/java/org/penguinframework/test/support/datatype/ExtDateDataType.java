package org.penguinframework.test.support.datatype;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Calendar;

import org.dbunit.dataset.ITable;
import org.dbunit.dataset.datatype.AbstractDataType;
import org.dbunit.dataset.datatype.DataType;
import org.dbunit.dataset.datatype.TypeCastException;
import org.penguinframework.test.support.DateTimeUtils;

public class ExtDateDataType extends AbstractDataType {

    ExtDateDataType() {
        super("DATE", Types.DATE, LocalDate.class, false);
    }

    ////////////////////////////////////////////////////////////////////////////
    // DataType class

    @Override
    public Object typeCast(Object value) throws TypeCastException {
        if (value == null || value == ITable.NO_VALUE) {
            return null;
        }

        if (value instanceof LocalDate) {
            return value;
        } else if (value instanceof java.sql.Date) {
            return java.sql.Date.class.cast(value).toLocalDate();
        } else if (value instanceof java.util.Date) {
            return new java.sql.Date(java.util.Date.class.cast(value).getTime()).toLocalDate();
        } else if (value instanceof Calendar) {
            return new java.sql.Date(Calendar.class.cast(value).getTime().getTime()).toLocalDate();
        } else if (value instanceof Instant) {
            return java.sql.Timestamp.from(Instant.class.cast(value)).toLocalDateTime().toLocalDate();
        } else if (value instanceof Long) {
            return new java.sql.Date(Long.class.cast(value).longValue()).toLocalDate();
        }

        if (value instanceof String) {
            String stringValue = String.class.cast(value);

            if (DataType.isExtendedSyntax(stringValue)) {
                // Relative date.
                try {
                    return DataType.RELATIVE_DATE_TIME_PARSER.parse(stringValue).toLocalDate();
                } catch (IllegalArgumentException | DateTimeParseException e) {
                    throw new TypeCastException(value, this, e);
                }
            }

            try {
                return DateTimeUtils.toLocalDate(stringValue);
            } catch (IllegalArgumentException e) {
                throw new TypeCastException(value, this, e);
            }
        }

        throw new TypeCastException(value, this);
    }

    @Override
    public boolean isDateTime() {
        return DataType.DATE.isDateTime();
    }

    @Override
    public Object getSqlValue(int column, ResultSet resultSet) throws SQLException, TypeCastException {
        Object sqlValue = DataType.DATE.getSqlValue(column, resultSet);
        if (sqlValue == null) {
            return sqlValue;
        }
        return java.sql.Date.class.cast(sqlValue).toLocalDate();
    }

    @Override
    public void setSqlValue(Object value, int column, PreparedStatement statement)
            throws SQLException, TypeCastException {
        DataType.DATE.setSqlValue(java.sql.Date.valueOf(LocalDate.class.cast(this.typeCast(value))), column, statement);
    }
}
