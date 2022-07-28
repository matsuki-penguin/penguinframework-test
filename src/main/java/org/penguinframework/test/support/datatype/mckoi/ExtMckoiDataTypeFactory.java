package org.penguinframework.test.support.datatype.mckoi;

import org.dbunit.dataset.datatype.DataType;
import org.dbunit.dataset.datatype.DataTypeException;
import org.dbunit.ext.mckoi.MckoiDataTypeFactory;
import org.penguinframework.test.support.datatype.ExtDataType;

public class ExtMckoiDataTypeFactory extends MckoiDataTypeFactory {

    @Override
    public DataType createDataType(int sqlType, String sqlTypeName) throws DataTypeException {
        DataType dataType = super.createDataType(sqlType, sqlTypeName);

        return ExtDataType.remapForDataType(dataType);
    }
}
