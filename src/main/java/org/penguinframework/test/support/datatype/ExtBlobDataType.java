package org.penguinframework.test.support.datatype;

import org.apache.commons.lang3.ArrayUtils;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.datatype.BlobDataType;
import org.dbunit.dataset.datatype.TypeCastException;

public class ExtBlobDataType extends BlobDataType {

    @Override
    public Object typeCast(Object value) throws TypeCastException {
        if (value == null || value == ITable.NO_VALUE) {
            return null;
        }

        if (value instanceof Byte[]) {
            return ArrayUtils.toPrimitive(Byte[].class.cast(value));
        }

        return super.typeCast(value);
    }
}
