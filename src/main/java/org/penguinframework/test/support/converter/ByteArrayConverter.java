package org.penguinframework.test.support.converter;

import org.apache.commons.beanutils.converters.AbstractConverter;
import org.apache.commons.lang3.ArrayUtils;
import org.penguinframework.test.support.datatype.ExtBlobDataType;

public class ByteArrayConverter extends AbstractConverter {

    @Override
    protected <T> T convertToType(Class<T> type, Object value) throws Throwable {
        if (type == byte[].class) {
            return type.cast(new ExtBlobDataType().typeCast(value));
        } else if (type == Byte[].class) {
            return type.cast(ArrayUtils.toObject(byte[].class.cast(new ExtBlobDataType().typeCast(value))));
        }
        throw new IllegalArgumentException("Default type is missing");
    }

    @Override
    protected Class<?> getDefaultType() {
        return byte[].class;
    }
}
