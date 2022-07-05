package org.penguinframework.test.support.datatype;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDateTime;

import org.dbunit.dataset.ITable;
import org.dbunit.dataset.datatype.AbstractDataType;
import org.dbunit.dataset.datatype.DataType;
import org.dbunit.dataset.datatype.TypeCastException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LocalDateTimeDataType extends AbstractDataType {

    /**
     * Logger for this class
     */
    private static final Logger logger = LoggerFactory.getLogger(LocalDateTimeDataType.class);

    LocalDateTimeDataType() {
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
        }

        return java.sql.Timestamp.class.cast(DataType.TIMESTAMP.typeCast(value)).toLocalDateTime();
    }

    @Override
    public boolean isDateTime() {
        return DataType.TIMESTAMP.isDateTime();
    }

    @Override
    public Object getSqlValue(int column, ResultSet resultSet) throws SQLException, TypeCastException {
        return java.sql.Timestamp.class.cast(DataType.TIMESTAMP.getSqlValue(column, resultSet)).toLocalDateTime();
    }

    @Override
    public void setSqlValue(Object value, int column, PreparedStatement statement)
            throws SQLException, TypeCastException {
        DataType.TIMESTAMP.setSqlValue(java.sql.Timestamp.valueOf(LocalDateTime.class.cast(this.typeCast(value))),
                column, statement);
    }
}
