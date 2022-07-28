package org.penguinframework.test.support.datatype.db2;

import org.dbunit.dataset.datatype.DataType;
import org.dbunit.dataset.datatype.DataTypeException;
import org.dbunit.ext.db2.Db2DataTypeFactory;
import org.penguinframework.test.support.datatype.ExtDataType;

public class ExtDb2DataTypeFactory extends Db2DataTypeFactory {

    @Override
    public DataType createDataType(int sqlType, String sqlTypeName) throws DataTypeException {
        DataType dataType = super.createDataType(sqlType, sqlTypeName);

        return ExtDataType.remapForDataType(dataType);
    }
}
