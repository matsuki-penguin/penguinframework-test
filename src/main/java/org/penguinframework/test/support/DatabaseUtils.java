package org.penguinframework.test.support;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.ext.mssql.InsertIdentityOperation;
import org.penguinframework.test.exception.ReflectionRuntimeException;
import org.penguinframework.test.type.Platform;

public class DatabaseUtils {

    private static final String POSTGRESQL_DRIVER_NAME = "PostgreSQL";
    private static final String MSSQL_DRIVER_NAME = "SQL Server";

    private DatabaseUtils() {
    }

    public static IDatabaseConnection getDatabaseConnection(Connection connection, Platform platform)
            throws DatabaseUnitException {
        return getDatabaseConnection(connection, null, platform);
    }

    public static IDatabaseConnection getDatabaseConnection(Connection connection, String schema, Platform platform)
            throws DatabaseUnitException {
        IDatabaseConnection databaseConnection;
        if (schema == null) {
            databaseConnection = new DatabaseConnection(connection);
        } else {
            databaseConnection = new DatabaseConnection(connection, schema);
        }

        DatabaseConfig config = databaseConnection.getConfig();

        try {
            config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY,
                    platform.getDataTypeFactoryClass().getDeclaredConstructor().newInstance());
        } catch (ReflectiveOperationException | IllegalArgumentException | SecurityException e) {
            throw new ReflectionRuntimeException("Failed to create an instance of DataTypeFactory. : "
                    + platform.getDataTypeFactoryClass().getName(), e);
        }

        config.setProperty(DatabaseConfig.PROPERTY_IDENTITY_COLUMN_FILTER,
                InsertIdentityOperation.IDENTITY_FILTER_EXTENDED);

        return databaseConnection;
    }

    /**
     * JDBCドライバがJDBC 4.2以上であるか否かを取得する。
     * 
     * @param databaseMetaData
     * @return
     * @throws SQLException
     */
    public static boolean isSupportedJava8(DatabaseMetaData databaseMetaData) throws SQLException {
        return databaseMetaData.getJDBCMajorVersion() > 4
                || (databaseMetaData.getJDBCMajorVersion() == 4 && databaseMetaData.getJDBCMinorVersion() >= 2);
    }

    public static boolean isMssql(DatabaseMetaData databaseMetaData) throws SQLException {
        return databaseMetaData.getDriverName().contains(DatabaseUtils.MSSQL_DRIVER_NAME);
    }

    public static boolean isPostgresql(DatabaseMetaData databaseMetaData) throws SQLException {
        return databaseMetaData.getDriverName().contains(DatabaseUtils.POSTGRESQL_DRIVER_NAME);
    }
}
