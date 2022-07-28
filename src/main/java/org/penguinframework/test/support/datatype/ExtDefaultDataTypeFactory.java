package org.penguinframework.test.support.datatype;

import org.dbunit.dataset.datatype.DataType;
import org.dbunit.dataset.datatype.DataTypeException;
import org.dbunit.dataset.datatype.DefaultDataTypeFactory;

public class ExtDefaultDataTypeFactory extends DefaultDataTypeFactory {

    @Override
    public DataType createDataType(int sqlType, String sqlTypeName) throws DataTypeException {
        DataType dataType = super.createDataType(sqlType, sqlTypeName);

        return ExtDataType.remapForDataType(dataType);
    }
}
