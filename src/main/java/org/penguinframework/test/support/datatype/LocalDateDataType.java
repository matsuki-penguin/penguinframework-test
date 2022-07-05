package org.penguinframework.test.support.datatype;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;

import org.dbunit.dataset.ITable;
import org.dbunit.dataset.datatype.AbstractDataType;
import org.dbunit.dataset.datatype.DataType;
import org.dbunit.dataset.datatype.TypeCastException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LocalDateDataType extends AbstractDataType {

    /**
     * Logger for this class
     */
    private static final Logger logger = LoggerFactory.getLogger(LocalDateDataType.class);

    LocalDateDataType() {
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
        }

        return java.sql.Date.class.cast(DataType.DATE.typeCast(value)).toLocalDate();
    }

    @Override
    public boolean isDateTime() {
        return DataType.DATE.isDateTime();
    }

    @Override
    public Object getSqlValue(int column, ResultSet resultSet) throws SQLException, TypeCastException {
        return java.sql.Date.class.cast(DataType.DATE.getSqlValue(column, resultSet)).toLocalDate();
    }

    @Override
    public void setSqlValue(Object value, int column, PreparedStatement statement)
            throws SQLException, TypeCastException {
        DataType.DATE.setSqlValue(java.sql.Date.valueOf(LocalDate.class.cast(this.typeCast(value))), column, statement);
    }
}
