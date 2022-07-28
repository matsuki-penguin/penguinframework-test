package org.penguinframework.test.database.adapter;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.operation.DatabaseOperation;
import org.penguinframework.test.database.annotation.DatabaseMeta;
import org.penguinframework.test.support.DatabaseUtils;
import org.penguinframework.test.type.Platform;

public abstract class TableFileAdapter {
    protected IDatabaseConnection connection;

    protected Platform platform;

    protected TableFileAdapter(Connection connection, DatabaseMeta databaseMeta) throws DatabaseUnitException {
        super();

        this.platform = databaseMeta == null ? Platform.DEFAULT : databaseMeta.platform();
        this.connection = DatabaseUtils.getDatabaseConnection(connection, this.platform);
    }

    protected TableFileAdapter(Connection connection, String schema, DatabaseMeta databaseMeta)
            throws DatabaseUnitException {
        super();

        this.platform = databaseMeta == null ? Platform.DEFAULT : databaseMeta.platform();
        this.connection = DatabaseUtils.getDatabaseConnection(connection, schema, this.platform);
    }

    public abstract void load(DatabaseOperation databaseOperation, URL url)
            throws IOException, SQLException, DatabaseUnitException;
}
