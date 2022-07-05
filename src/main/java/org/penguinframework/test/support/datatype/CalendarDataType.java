package org.penguinframework.test.support.datatype;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Calendar;

import org.dbunit.dataset.ITable;
import org.dbunit.dataset.datatype.AbstractDataType;
import org.dbunit.dataset.datatype.DataType;
import org.dbunit.dataset.datatype.TypeCastException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CalendarDataType extends AbstractDataType {

    /**
     * Logger for this class
     */
    private static final Logger logger = LoggerFactory.getLogger(CalendarDataType.class);

    CalendarDataType() {
        super("TIMESTAMP", Types.TIMESTAMP, Calendar.class, false);
    }

    ////////////////////////////////////////////////////////////////////////////
    // DataType class

    @Override
    public Object typeCast(Object value) throws TypeCastException {
        if (value == null || value == ITable.NO_VALUE) {
            return null;
        }

        if (value instanceof Calendar) {
            return value;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(java.sql.Timestamp.class.cast(DataType.TIMESTAMP.typeCast(value)).getTime());
        return cal;
    }

    @Override
    public boolean isDateTime() {
        return DataType.TIMESTAMP.isDateTime();
    }

    @Override
    public Object getSqlValue(int column, ResultSet resultSet) throws SQLException, TypeCastException {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(java.sql.Timestamp.class.cast(DataType.TIMESTAMP.getSqlValue(column, resultSet)).getTime());
        return cal;
    }

    @Override
    public void setSqlValue(Object value, int column, PreparedStatement statement)
            throws SQLException, TypeCastException {
        DataType.TIMESTAMP.setSqlValue(
                new java.sql.Timestamp(Calendar.class.cast(this.typeCast(value)).getTimeInMillis()), column, statement);
    }
}
