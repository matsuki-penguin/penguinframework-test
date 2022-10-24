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

public class ExtNumberDataType extends AbstractDataType {

    /**
     * Logger for this class
     */
    private static final Logger logger = LoggerFactory.getLogger(ExtNumberDataType.class);

    private static final Number TRUE = BigDecimal.valueOf(1L);
    private static final Number FALSE = BigDecimal.valueOf(0L);

    ExtNumberDataType(String name, int sqlType) {
        super(name, sqlType, BigDecimal.class, true);
    }

    ////////////////////////////////////////////////////////////////////////////
    // DataType class

    @Override
    public Object typeCast(Object value) throws TypeCastException {
        ExtNumberDataType.logger.debug("typeCast(value={}) - start", value);

        if (value == null || value == ITable.NO_VALUE) {
            return null;
        }

        if (value instanceof BigDecimal) {
            return value;
        }

        if (value instanceof Boolean) {
            return ((Boolean) value).booleanValue() ? ExtNumberDataType.TRUE : ExtNumberDataType.FALSE;
        }

        // Treat "false" as 0, "true" as 1
        if (value instanceof String) {
            String string = String.class.cast(value);

            if ("false".equalsIgnoreCase(string)) {
                return ExtNumberDataType.FALSE;
            }

            if ("true".equalsIgnoreCase(string)) {
                return ExtNumberDataType.TRUE;
            }
        }

        try {
            return new BigDecimal(value.toString());
        } catch (java.lang.NumberFormatException e) {
            throw new TypeCastException(value, this, e);
        }
    }

    @Override
    public Object getSqlValue(int column, ResultSet resultSet) throws SQLException, TypeCastException {
        if (ExtNumberDataType.logger.isDebugEnabled()) {
            ExtNumberDataType.logger.debug("getSqlValue(column={}, resultSet={}) - start", new Integer(column),
                    resultSet);
        }

        BigDecimal value = resultSet.getBigDecimal(column);
        if (value == null || resultSet.wasNull()) {
            return null;
        }
        return value;
    }

    @Override
    public void setSqlValue(Object value, int column, PreparedStatement statement)
            throws SQLException, TypeCastException {
        if (ExtNumberDataType.logger.isDebugEnabled()) {
            ExtNumberDataType.logger.debug("setSqlValue(value={}, column={}, statement={}) - start",
                    new Object[] { value, new Integer(column), statement });
        }

        statement.setBigDecimal(column, (BigDecimal) typeCast(value));
    }
}
