package org.penguinframework.test.support.datatype;

import org.apache.commons.lang3.ArrayUtils;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.datatype.ClobDataType;
import org.dbunit.dataset.datatype.TypeCastException;

public class ExtClobDataType extends ClobDataType {

    @Override
    public Object typeCast(Object value) throws TypeCastException {
        if (value == null || value == ITable.NO_VALUE) {
            return null;
        }

        if (value instanceof char[]) {
            return new String(char[].class.cast(value));
        }

        if (value instanceof Character[]) {
            return new String(ArrayUtils.toPrimitive(Character[].class.cast(value)));
        }

        return super.typeCast(value);
    }
}
