package org.penguinframework.test.support.datatype;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.time.format.SignStyle;
import java.time.temporal.ChronoField;

import org.dbunit.dataset.ITable;
import org.dbunit.dataset.datatype.AbstractDataType;
import org.dbunit.dataset.datatype.DataType;
import org.dbunit.dataset.datatype.TypeCastException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LocalTimeDataType extends AbstractDataType {

    /**
     * Logger for this class
     */
    private static final Logger logger = LoggerFactory.getLogger(LocalTimeDataType.class);

    LocalTimeDataType() {
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
        }

        if (value instanceof String) {
            final String stringValue = (String) value;

            if (DataType.isExtendedSyntax(stringValue)) {
                // Relative date.
                try {
                    LocalDateTime datetime = DataType.RELATIVE_DATE_TIME_PARSER.parse(stringValue);
                    return datetime.toLocalTime();
                } catch (IllegalArgumentException | DateTimeParseException e) {
                    throw new TypeCastException(value, this, e);
                }
            }

            DateTimeFormatter isoLocalTime = new DateTimeFormatterBuilder()
                    .appendValue(ChronoField.HOUR_OF_DAY, 1, 2, SignStyle.NOT_NEGATIVE).appendLiteral(':')
                    .appendValue(ChronoField.MINUTE_OF_HOUR, 1, 2, SignStyle.NOT_NEGATIVE).optionalStart()
                    .appendLiteral(':').appendValue(ChronoField.SECOND_OF_MINUTE, 1, 2, SignStyle.NOT_NEGATIVE)
                    .optionalStart().appendFraction(ChronoField.NANO_OF_SECOND, 0, 9, true).toFormatter()
                    .withResolverStyle(ResolverStyle.STRICT);
            try {
                return LocalTime.parse(stringValue, isoLocalTime);
            } catch (DateTimeParseException e) {
                throw new TypeCastException(value, this, e);
            }
        }

        return java.sql.Time.class.cast(DataType.TIME.typeCast(value)).toLocalTime();
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
