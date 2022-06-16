package org.penguinframework.test.database.adapter;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.operation.DatabaseOperation;

public abstract class TableFileAdapter {
    protected DatabaseConnection connection;

    protected TableFileAdapter(Connection connection) throws DatabaseUnitException {
        super();
        this.connection = new DatabaseConnection(connection);
    }

    protected TableFileAdapter(Connection connection, String schema) throws DatabaseUnitException {
        super();
        this.connection = new DatabaseConnection(connection, schema);
    }

    public abstract void load(DatabaseOperation databaseOperation, URL url)
            throws IOException, SQLException, DatabaseUnitException;
}
