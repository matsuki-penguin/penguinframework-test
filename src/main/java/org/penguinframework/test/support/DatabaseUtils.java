package org.penguinframework.test.support;

import java.sql.Connection;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.ext.mssql.InsertIdentityOperation;
import org.penguinframework.test.exception.ReflectionRuntimeException;
import org.penguinframework.test.type.Platform;

public class DatabaseUtils {

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
}
