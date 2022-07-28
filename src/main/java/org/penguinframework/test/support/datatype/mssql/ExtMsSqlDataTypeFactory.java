package org.penguinframework.test.support.datatype.mssql;

import org.dbunit.dataset.datatype.DataType;
import org.dbunit.dataset.datatype.DataTypeException;
import org.dbunit.ext.mssql.MsSqlDataTypeFactory;
import org.penguinframework.test.support.datatype.ExtDataType;

public class ExtMsSqlDataTypeFactory extends MsSqlDataTypeFactory {

    @Override
    public DataType createDataType(int sqlType, String sqlTypeName) throws DataTypeException {
        DataType dataType = super.createDataType(sqlType, sqlTypeName);

        return ExtDataType.remapForDataType(dataType);
    }
}
