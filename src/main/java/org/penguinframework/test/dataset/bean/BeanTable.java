package org.penguinframework.test.dataset.bean;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.dbunit.dataset.AbstractTable;
import org.dbunit.dataset.Column;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.DefaultTableMetaData;
import org.dbunit.dataset.ITableMetaData;
import org.dbunit.dataset.datatype.DataType;
import org.penguinframework.test.exception.ReflectionRuntimeException;
import org.penguinframework.test.support.BeanType;
import org.penguinframework.test.support.datatype.ExtendDataType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BeanTable extends AbstractTable {
    /** Logger object. */
    private static final Logger logger = LoggerFactory.getLogger(BeanTable.class);

    private static final Map<Class<?>, DataType> DATA_TYPE_MAP = new HashMap<>();

    static {
        BeanTable.DATA_TYPE_MAP.put(boolean.class, DataType.BOOLEAN);
        BeanTable.DATA_TYPE_MAP.put(Boolean.class, DataType.BOOLEAN);
        BeanTable.DATA_TYPE_MAP.put(byte.class, DataType.TINYINT);
        BeanTable.DATA_TYPE_MAP.put(Byte.class, DataType.TINYINT);
        BeanTable.DATA_TYPE_MAP.put(char.class, DataType.CHAR);
        BeanTable.DATA_TYPE_MAP.put(Character.class, DataType.CHAR);
        BeanTable.DATA_TYPE_MAP.put(short.class, DataType.SMALLINT);
        BeanTable.DATA_TYPE_MAP.put(Short.class, DataType.SMALLINT);
        BeanTable.DATA_TYPE_MAP.put(int.class, DataType.INTEGER);
        BeanTable.DATA_TYPE_MAP.put(Integer.class, DataType.INTEGER);
        BeanTable.DATA_TYPE_MAP.put(long.class, DataType.BIGINT);
        BeanTable.DATA_TYPE_MAP.put(Long.class, DataType.BIGINT);

        BeanTable.DATA_TYPE_MAP.put(float.class, DataType.REAL);
        BeanTable.DATA_TYPE_MAP.put(Float.class, DataType.REAL);
        BeanTable.DATA_TYPE_MAP.put(double.class, DataType.DOUBLE);
        BeanTable.DATA_TYPE_MAP.put(Double.class, DataType.DOUBLE);

        BeanTable.DATA_TYPE_MAP.put(BigInteger.class, DataType.DECIMAL);
        BeanTable.DATA_TYPE_MAP.put(BigDecimal.class, DataType.DECIMAL);

        BeanTable.DATA_TYPE_MAP.put(byte[].class, DataType.BLOB);
        BeanTable.DATA_TYPE_MAP.put(Byte[].class, DataType.BLOB);
        BeanTable.DATA_TYPE_MAP.put(char[].class, DataType.CLOB);
        BeanTable.DATA_TYPE_MAP.put(Character[].class, DataType.CLOB);

        BeanTable.DATA_TYPE_MAP.put(String.class, DataType.NVARCHAR);

        BeanTable.DATA_TYPE_MAP.put(java.sql.Date.class, DataType.DATE);
        BeanTable.DATA_TYPE_MAP.put(LocalDate.class, ExtendDataType.LOCAL_DATE);
        BeanTable.DATA_TYPE_MAP.put(java.sql.Time.class, DataType.TIME);
        BeanTable.DATA_TYPE_MAP.put(LocalTime.class, ExtendDataType.LOCAL_TIME);
        BeanTable.DATA_TYPE_MAP.put(java.sql.Timestamp.class, DataType.TIMESTAMP);
        BeanTable.DATA_TYPE_MAP.put(LocalDateTime.class, ExtendDataType.LOCAL_DATETIME);
        BeanTable.DATA_TYPE_MAP.put(java.util.Date.class, ExtendDataType.UTIL_DATE);
        BeanTable.DATA_TYPE_MAP.put(Calendar.class, ExtendDataType.CALENDAR);
        BeanTable.DATA_TYPE_MAP.put(Instant.class, ExtendDataType.INSTANT);

        BeanTable.DATA_TYPE_MAP.put(UUID.class, DataType.BINARY);
    }

    /** Meta data. */
    private final ITableMetaData metaData;

    /** Record data in bean object. */
    private final Object[][] records;

    /**
     * Constructor for reading bean field from object.
     *
     * @param target      Bean object.
     * @param targetClass Bean class.
     */
    public BeanTable(Object target, Class<?> targetClass) {
        BeanType.Info info = BeanType.analyze(target, targetClass);

        Object[] beans;
        if (info.isArray()) {
            beans = (Object[]) target;
        } else if (info.isList()) {
            beans = ((List<?>) target).toArray();
        } else {
            beans = new Object[] { target };
        }

        // フィールド一覧を取得
        Field[] fields = FieldUtils.getAllFields(info.getActualClass());

        this.records = new Object[beans.length][fields.length];
        for (int row = 0; row < beans.length; row++) {
            Object bean = beans[row];
            for (int col = 0; col < fields.length; col++) {
                Field field = fields[col];
                Object value;
                try {
                    value = FieldUtils.readField(field, bean, true);
                } catch (IllegalAccessException e) {
                    throw new ReflectionRuntimeException("Failed to read field.", e);
                }

                if (value instanceof Byte[]) {
                    // ラッパークラスのByte型の配列をプリミティブのbyte型の配列に変換
                    value = ArrayUtils.toPrimitive(Byte[].class.cast(value));
                } else if (value instanceof char[]) {
                    value = new String(char[].class.cast(value));
                } else if (value instanceof Character[]) {
                    // ラッパークラスのCharacter型の配列をプリミティブのchar型の配列に変換し、文字列化
                    value = new String(ArrayUtils.toPrimitive(Character[].class.cast(value)));
                } else if (value instanceof UUID) {
                    try {
                        value = Hex.decodeHex(StringUtils.remove(UUID.class.cast(value).toString(), '-'));
                    } catch (DecoderException e) {
                        BeanTable.logger.error("Cannot convert UUID to binary. : " + UUID.class.cast(value), e);
                    }
                }

                this.records[row][col] = value;
            }
        }

        // メタデータを生成
        Column[] columns = Arrays.stream(fields)
                .map(field -> new Column(field.getName(),
                        BeanTable.DATA_TYPE_MAP.getOrDefault(field.getType(), DataType.UNKNOWN)))
                .toArray(Column[]::new);
        this.metaData = new DefaultTableMetaData(info.getActualClass().getSimpleName(), columns);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getRowCount() {
        return this.records.length;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ITableMetaData getTableMetaData() {
        return this.metaData;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getValue(int row, String column) throws DataSetException {
        this.assertValidRowIndex(row);

        int columnIndex = this.getColumnIndex(column);
        return this.records[row][columnIndex];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "BeanTable [metaData=" + this.metaData + ", recordList=" + this.records + "]";
    }
}
