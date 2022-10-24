package org.penguinframework.test.support.datatype;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.dbunit.dataset.ITable;
import org.dbunit.dataset.datatype.AbstractDataType;
import org.dbunit.dataset.datatype.TypeCastException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExtIntegerDataType extends AbstractDataType {
    private static final Logger logger = LoggerFactory.getLogger(ExtIntegerDataType.class);

    ExtIntegerDataType(String name, int sqlType) {
        super(name, sqlType, Integer.class, true);
    }

    ////////////////////////////////////////////////////////////////////////////
    // DataType class

    @Override
    public Object typeCast(Object value) throws TypeCastException {
        ExtIntegerDataType.logger.debug("typeCast(value={}) - start", value);

        if (value == null || value == ITable.NO_VALUE) {
            return null;
        }

        if (value instanceof Number) {
            return new Integer(((Number) value).intValue());
        }

        if (value instanceof Boolean) {
            return Integer.valueOf(Boolean.class.cast(value).booleanValue() ? 1 : 0);
        }

        // Treat "false" as 0, "true" as 1
        if (value instanceof String) {
            String string = (String) value;

            if ("false".equalsIgnoreCase(string)) {
                return new Integer(0);
            }

            if ("true".equalsIgnoreCase(string)) {
                return new Integer(1);
            }
        }

        // Bugfix in release 2.4.6
        String stringValue = value.toString().trim();
        if (stringValue.length() <= 0) {
            return null;
        }

        try {
            return typeCast(new BigDecimal(stringValue));
        } catch (java.lang.NumberFormatException e) {
            throw new TypeCastException(value, this, e);
        }
    }

    @Override
    public Object getSqlValue(int column, ResultSet resultSet) throws SQLException, TypeCastException {
        if (ExtIntegerDataType.logger.isDebugEnabled()) {
            ExtIntegerDataType.logger.debug("getSqlValue(column={}, resultSet={}) - start", new Integer(column),
                    resultSet);
        }

        int value = resultSet.getInt(column);
        if (resultSet.wasNull()) {
            return null;
        }
        return new Integer(value);
    }

    @Override
    public void setSqlValue(Object value, int column, PreparedStatement statement)
            throws SQLException, TypeCastException {
        if (ExtIntegerDataType.logger.isDebugEnabled()) {
            ExtIntegerDataType.logger.debug("setSqlValue(value={}, column={}, statement={}) - start",
                    new Object[] { value, new Integer(column), statement });
        }

        statement.setInt(column, ((Integer) typeCast(value)).intValue());
    }
}
