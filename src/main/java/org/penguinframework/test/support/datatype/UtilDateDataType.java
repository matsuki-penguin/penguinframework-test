package org.penguinframework.test.support.datatype;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.dbunit.dataset.ITable;
import org.dbunit.dataset.datatype.AbstractDataType;
import org.dbunit.dataset.datatype.DataType;
import org.dbunit.dataset.datatype.TypeCastException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UtilDateDataType extends AbstractDataType {

    /**
     * Logger for this class
     */
    private static final Logger logger = LoggerFactory.getLogger(UtilDateDataType.class);

    UtilDateDataType() {
        super("TIMESTAMP", Types.TIMESTAMP, java.util.Date.class, false);
    }

    ////////////////////////////////////////////////////////////////////////////
    // DataType class

    @Override
    public Object typeCast(Object value) throws TypeCastException {
        if (value == null || value == ITable.NO_VALUE) {
            return null;
        }

        if (value instanceof java.util.Date) {
            return value;
        }

        return new java.util.Date(java.sql.Timestamp.class.cast(DataType.TIMESTAMP.typeCast(value)).getTime());
    }

    @Override
    public boolean isDateTime() {
        return DataType.TIMESTAMP.isDateTime();
    }

    @Override
    public Object getSqlValue(int column, ResultSet resultSet) throws SQLException, TypeCastException {
        return new java.util.Date(
                java.sql.Timestamp.class.cast(DataType.TIMESTAMP.getSqlValue(column, resultSet)).getTime());
    }

    @Override
    public void setSqlValue(Object value, int column, PreparedStatement statement)
            throws SQLException, TypeCastException {
        DataType.TIMESTAMP.setSqlValue(
                new java.sql.Timestamp(java.util.Date.class.cast(this.typeCast(value)).getTime()), column, statement);
    }
}
