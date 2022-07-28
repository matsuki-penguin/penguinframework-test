package org.penguinframework.test.type;

import org.dbunit.dataset.datatype.IDataTypeFactory;
import org.penguinframework.test.support.datatype.ExtDefaultDataTypeFactory;
import org.penguinframework.test.support.datatype.db2.ExtDb2DataTypeFactory;
import org.penguinframework.test.support.datatype.h2.ExtH2DataTypeFactory;
import org.penguinframework.test.support.datatype.hsqldb.ExtHsqldbDataTypeFactory;
import org.penguinframework.test.support.datatype.mckoi.ExtMckoiDataTypeFactory;
import org.penguinframework.test.support.datatype.mssql.ExtMsSqlDataTypeFactory;
import org.penguinframework.test.support.datatype.mysql.ExtMySqlDataTypeFactory;
import org.penguinframework.test.support.datatype.netezza.ExtNetezzaDataTypeFactory;
import org.penguinframework.test.support.datatype.oracle.ExtOracle10DataTypeFactory;
import org.penguinframework.test.support.datatype.oracle.ExtOracleDataTypeFactory;
import org.penguinframework.test.support.datatype.postgresql.ExtPostgresqlDataTypeFactory;

public enum Platform {
    DEFAULT(ExtDefaultDataTypeFactory.class), DB2(ExtDb2DataTypeFactory.class), H2(ExtH2DataTypeFactory.class),
    HSQLDB(ExtHsqldbDataTypeFactory.class), MCKOI(ExtMckoiDataTypeFactory.class), MSSQL(ExtMsSqlDataTypeFactory.class),
    MYSQL(ExtMySqlDataTypeFactory.class), NETEZZA(ExtNetezzaDataTypeFactory.class),
    ORACLE(ExtOracleDataTypeFactory.class), ORACLE10(ExtOracle10DataTypeFactory.class),
    POSTGRESQL(ExtPostgresqlDataTypeFactory.class);

    private Class<? extends IDataTypeFactory> dataTypeFactoryClass;

    private Platform(Class<? extends IDataTypeFactory> dataTypeFactoryClass) {
        this.dataTypeFactoryClass = dataTypeFactoryClass;
    }

    public Class<? extends IDataTypeFactory> getDataTypeFactoryClass() {
        return this.dataTypeFactoryClass;
    }
}
