package org.penguinframework.test.dataset.bean;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.dbunit.dataset.AbstractTable;
import org.dbunit.dataset.Column;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.DefaultTableMetaData;
import org.dbunit.dataset.ITableMetaData;
import org.penguinframework.test.exception.ReflectionRuntimeException;
import org.penguinframework.test.support.BeanType;
import org.penguinframework.test.support.datatype.ExtDataType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BeanTable extends AbstractTable {
    /** Logger object. */
    private static final Logger logger = LoggerFactory.getLogger(BeanTable.class);

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

                // TODO UUIDの16進数をバイナリに変換したものを値としているが、UUID値のプレーンテキストにする
                if (value instanceof UUID) {
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
                .map(field -> new Column(field.getName(), ExtDataType.forType(field.getType()))).toArray(Column[]::new);
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
