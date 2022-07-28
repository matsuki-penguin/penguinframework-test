package org.penguinframework.test.support.datatype;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.dbunit.dataset.datatype.DataType;
import org.dbunit.dataset.datatype.TypeCastException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ExtDataType extends DataType {
    /**
     * Logger for this class
     */
    private static final Logger logger = LoggerFactory.getLogger(ExtDataType.class);

    public static final DataType EXT_BLOB = new ExtBlobDataType();
    public static final DataType EXT_CLOB = new ExtClobDataType();
    public static final DataType EXT_DATE = new ExtDateDataType();
    public static final DataType EXT_TIME = new ExtTimeDataType();
    public static final DataType EXT_TIMESTAMP = new ExtTimestampDataType();

    private static final DataType[] TYPES = { // Supported types
            DataType.VARCHAR, // Java type is java.lang.String, SQL type is Types.VARCHAR
            DataType.CHAR, // Java type is java.lang.String, SQL type is Types.CHAR
            DataType.LONGVARCHAR, // Java type is java.lang.String, SQL type is Types.LONGVARCHAR
            DataType.NCHAR, // Java type is java.lang.String, SQL type is Types.NCHAR
            DataType.NVARCHAR, // Java type is java.lang.String, SQL type is Types.NVARCHAR
            DataType.LONGNVARCHAR, // Java type is java.lang.String, SQL type is Types.LONGNVARCHAR
            ExtDataType.EXT_CLOB, // Java type is java.lang.String, SQL type is Types.CLOB
            DataType.NUMERIC, // Java type is java.math.BigDecimal, SQL type is Types.NUMERIC
            DataType.DECIMAL, // Java type is java.math.BigDecimal, SQL type is Types.DECIMAL
            DataType.BOOLEAN, // Java type is java.lang.Boolean, SQL type is Types.BOOLEAN
            DataType.BIT, // Java type is java.lang.Boolean, SQL type is Types.BIT
            DataType.INTEGER, // Java type is java.lang.Integer, SQL type is Types.INTEGER
            DataType.TINYINT, // Java type is java.lang.Integer, SQL type is Types.TINYINT
            DataType.SMALLINT, // Java type is java.lang.Integer, SQL type is Types.SMALLINT
            DataType.BIGINT, // Java type is java.math.BigInteger, SQL type is Types.BIGINT
            DataType.REAL, // Java type is java.lang.Float, SQL type is Types.REAL
            DataType.DOUBLE, // Java type is java.lang.Double, SQL type is Types.DOUBLE
            DataType.FLOAT, // Java type is java.lang.Double, SQL type is Types.FLOAT
            ExtDataType.EXT_DATE, // Java type is java.time.Date, SQL type is Types.DATE
            ExtDataType.EXT_TIME, // Java type is java.time.Time, SQL type is Types.TIME
            ExtDataType.EXT_TIMESTAMP, // Java type is java.time.DateTime, SQL type is Types.TIMESTAMP
            DataType.VARBINARY, // Java type is primitive byte[], SQL type is Types.VARBINARY
            DataType.BINARY, // Java type is primitive byte[], SQL type is Types.BINARY
            DataType.LONGVARBINARY, // Java type is primitive byte[], SQL type is Types.LONGVARBINARY
            ExtDataType.EXT_BLOB, // Java type is primitive byte[], SQL type is Types.BLOB
            DataType.BIGINT_AUX_LONG // Java type is java.lang.Long, SQL type is Types.BIGINT
    };

    private static final Map<DataType, DataType> REMAP_DATA_TYPE_MAP = new HashMap<>();

    static {
        ExtDataType.REMAP_DATA_TYPE_MAP.put(DataType.BLOB, ExtDataType.EXT_BLOB);
        ExtDataType.REMAP_DATA_TYPE_MAP.put(DataType.CLOB, ExtDataType.EXT_CLOB);
        ExtDataType.REMAP_DATA_TYPE_MAP.put(DataType.DATE, ExtDataType.EXT_DATE);
        ExtDataType.REMAP_DATA_TYPE_MAP.put(DataType.TIME, ExtDataType.EXT_TIME);
        ExtDataType.REMAP_DATA_TYPE_MAP.put(DataType.TIMESTAMP, ExtDataType.EXT_TIMESTAMP);
    }

    private static final Map<Class<?>, DataType> JAVA_TYPE_MAP = new HashMap<>();

    static {
        ExtDataType.JAVA_TYPE_MAP.put(boolean.class, DataType.BOOLEAN);
        ExtDataType.JAVA_TYPE_MAP.put(Boolean.class, DataType.BOOLEAN);
        ExtDataType.JAVA_TYPE_MAP.put(byte.class, DataType.TINYINT);
        ExtDataType.JAVA_TYPE_MAP.put(Byte.class, DataType.TINYINT);
        ExtDataType.JAVA_TYPE_MAP.put(char.class, DataType.CHAR);
        ExtDataType.JAVA_TYPE_MAP.put(Character.class, DataType.CHAR);
        ExtDataType.JAVA_TYPE_MAP.put(short.class, DataType.SMALLINT);
        ExtDataType.JAVA_TYPE_MAP.put(Short.class, DataType.SMALLINT);
        ExtDataType.JAVA_TYPE_MAP.put(int.class, DataType.INTEGER);
        ExtDataType.JAVA_TYPE_MAP.put(Integer.class, DataType.INTEGER);
        ExtDataType.JAVA_TYPE_MAP.put(long.class, DataType.BIGINT_AUX_LONG);
        ExtDataType.JAVA_TYPE_MAP.put(Long.class, DataType.BIGINT_AUX_LONG);

        ExtDataType.JAVA_TYPE_MAP.put(float.class, DataType.REAL);
        ExtDataType.JAVA_TYPE_MAP.put(Float.class, DataType.REAL);
        ExtDataType.JAVA_TYPE_MAP.put(double.class, DataType.DOUBLE);
        ExtDataType.JAVA_TYPE_MAP.put(Double.class, DataType.DOUBLE);

        ExtDataType.JAVA_TYPE_MAP.put(BigInteger.class, DataType.BIGINT);
        ExtDataType.JAVA_TYPE_MAP.put(BigDecimal.class, DataType.DECIMAL);

        ExtDataType.JAVA_TYPE_MAP.put(byte[].class, ExtDataType.EXT_BLOB);
        ExtDataType.JAVA_TYPE_MAP.put(Byte[].class, ExtDataType.EXT_BLOB);
        ExtDataType.JAVA_TYPE_MAP.put(char[].class, ExtDataType.EXT_CLOB);
        ExtDataType.JAVA_TYPE_MAP.put(Character[].class, ExtDataType.EXT_CLOB);

        ExtDataType.JAVA_TYPE_MAP.put(String.class, DataType.VARCHAR);

        ExtDataType.JAVA_TYPE_MAP.put(java.sql.Date.class, ExtDataType.EXT_DATE);
        ExtDataType.JAVA_TYPE_MAP.put(LocalDate.class, ExtDataType.EXT_DATE);
        ExtDataType.JAVA_TYPE_MAP.put(java.sql.Time.class, ExtDataType.EXT_TIME);
        ExtDataType.JAVA_TYPE_MAP.put(LocalTime.class, ExtDataType.EXT_TIME);
        ExtDataType.JAVA_TYPE_MAP.put(java.sql.Timestamp.class, ExtDataType.EXT_TIMESTAMP);
        ExtDataType.JAVA_TYPE_MAP.put(LocalDateTime.class, ExtDataType.EXT_TIMESTAMP);
        ExtDataType.JAVA_TYPE_MAP.put(java.util.Date.class, ExtDataType.EXT_TIMESTAMP);
        ExtDataType.JAVA_TYPE_MAP.put(Calendar.class, ExtDataType.EXT_TIMESTAMP);
        ExtDataType.JAVA_TYPE_MAP.put(Instant.class, ExtDataType.EXT_TIMESTAMP);

        ExtDataType.JAVA_TYPE_MAP.put(UUID.class, DataType.BINARY);
    }

    public static String asString(Object value) throws TypeCastException {
        ExtDataType.logger.debug("asString(value={}) - start", value);

        return String.class.cast(DataType.VARCHAR.typeCast(value));
    }

    public static DataType remapForDataType(DataType sourceDataType) {
        return ExtDataType.REMAP_DATA_TYPE_MAP.getOrDefault(sourceDataType, sourceDataType);
    }

    public static DataType forSqlType(int sqlType) {
        if (ExtDataType.logger.isDebugEnabled())
            ExtDataType.logger.debug("forSqlType(sqlType={}) - start", Integer.valueOf(sqlType));

        return Arrays.stream(ExtDataType.TYPES).filter(dataType -> dataType.getSqlType() == sqlType).findFirst()
                .orElse(DataType.UNKNOWN);
    }

    public static DataType forObject(Object value) {
        ExtDataType.logger.debug("forObject(value={}) - start", value);

        if (value == null) {
            return DataType.UNKNOWN;
        }

        return Arrays.stream(ExtDataType.TYPES).filter(dataType -> dataType.getTypeClass().isInstance(value))
                .findFirst().orElse(DataType.UNKNOWN);
    }

    public static DataType forType(Class<?> value) {
        ExtDataType.logger.debug("forType(value={}) - start", value);

        if (value == null) {
            return DataType.UNKNOWN;
        }

        return ExtDataType.JAVA_TYPE_MAP.getOrDefault(value, DataType.UNKNOWN);
    }

    protected static boolean isExtendedSyntax(String input) {
        return !input.isEmpty() && input.charAt(0) == '[';
    }
}
