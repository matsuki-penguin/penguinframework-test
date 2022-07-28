package org.penguinframework.test.support.datatype.oracle;

import org.dbunit.dataset.datatype.DataType;
import org.dbunit.dataset.datatype.DataTypeException;
import org.dbunit.ext.oracle.OracleDataTypeFactory;
import org.penguinframework.test.support.datatype.ExtDataType;

public class ExtOracleDataTypeFactory extends OracleDataTypeFactory {

    @Override
    public DataType createDataType(int sqlType, String sqlTypeName) throws DataTypeException {
        DataType dataType = super.createDataType(sqlType, sqlTypeName);

        return ExtDataType.remapForDataType(dataType);
    }
}
