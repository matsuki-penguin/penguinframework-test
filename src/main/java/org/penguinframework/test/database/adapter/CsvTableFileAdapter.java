package org.penguinframework.test.database.adapter;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;

import org.dbunit.DatabaseUnitException;
import org.dbunit.operation.DatabaseOperation;
import org.penguinframework.test.dataset.csv.CsvDataSet;
import org.penguinframework.test.meta.CsvMeta;

public class CsvTableFileAdapter extends TableFileAdapter {
    private final String tableName;

    private final CsvMeta meta;

    public CsvTableFileAdapter(Connection connection, String tableName, CsvMeta meta) throws DatabaseUnitException {
        super(connection);
        this.tableName = tableName;
        this.meta = meta;
    }

    public CsvTableFileAdapter(Connection connection, String schema, String tableName, CsvMeta meta)
            throws DatabaseUnitException {
        super(connection, schema);
        this.tableName = tableName;
        this.meta = meta;
    }

    @Override
    public void load(DatabaseOperation databaseOperation, URL url) throws SQLException, DatabaseUnitException {
        databaseOperation.execute(this.connection, new CsvDataSet(url, this.tableName, this.meta));
    }
}
