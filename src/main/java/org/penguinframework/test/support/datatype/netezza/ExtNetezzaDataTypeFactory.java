package org.penguinframework.test.support.datatype.netezza;

import org.dbunit.dataset.datatype.DataType;
import org.dbunit.dataset.datatype.DataTypeException;
import org.dbunit.ext.netezza.NetezzaDataTypeFactory;
import org.penguinframework.test.support.datatype.ExtDataType;

public class ExtNetezzaDataTypeFactory extends NetezzaDataTypeFactory {

    @Override
    public DataType createDataType(int sqlType, String sqlTypeName) throws DataTypeException {
        DataType dataType = super.createDataType(sqlType, sqlTypeName);

        return ExtDataType.remapForDataType(dataType);
    }
}
