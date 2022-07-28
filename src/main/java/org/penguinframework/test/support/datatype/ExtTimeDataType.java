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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExtTimeDataType extends AbstractDataType {

    private static final Logger logger = LoggerFactory.getLogger(ExtTimeDataType.class);

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
            return new java.sql.Timestamp(Long.class.cast(value).longValue()).toLocalDateTime().toLocalTime();
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
        if (ExtTimeDataType.logger.isDebugEnabled()) {
            ExtTimeDataType.logger.debug("getSqlValue(column={}, resultSet={}) - start", Integer.valueOf(column),
                    resultSet);
        }

        String value = resultSet.getString(column);
        if (value == null || resultSet.wasNull()) {
            return null;
        }

        return DateTimeUtils.toLocalTime(value);
    }

    @Override
    public void setSqlValue(Object value, int column, PreparedStatement statement)
            throws SQLException, TypeCastException {
        if (ExtTimeDataType.logger.isDebugEnabled()) {
            ExtTimeDataType.logger.debug("setSqlValue(value={}, column={}, statement={}) - start", value,
                    Integer.valueOf(column), statement);
        }

        Object sqlValue = typeCast(value);
        if (sqlValue != null) {
            sqlValue = DateTimeUtils.toString(LocalTime.class.cast(typeCast(value)));
        }
        statement.setString(column, String.class.cast(sqlValue));
    }
}
