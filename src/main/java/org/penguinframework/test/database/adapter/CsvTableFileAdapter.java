package org.penguinframework.test.database.adapter;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;

import org.dbunit.DatabaseUnitException;
import org.dbunit.ext.mssql.InsertIdentityOperation;
import org.dbunit.operation.DatabaseOperation;
import org.penguinframework.test.database.annotation.DatabaseMeta;
import org.penguinframework.test.dataset.csv.CsvDataSet;
import org.penguinframework.test.meta.CsvMeta;
import org.penguinframework.test.type.Platform;

public class CsvTableFileAdapter extends TableFileAdapter {
    private final String tableName;

    private final CsvMeta meta;

    public CsvTableFileAdapter(Connection connection, String tableName, DatabaseMeta databaseMeta, CsvMeta meta)
            throws DatabaseUnitException {
        super(connection, databaseMeta);
        this.tableName = tableName;
        this.meta = meta;
    }

    public CsvTableFileAdapter(Connection connection, String schema, String tableName, DatabaseMeta databaseMeta,
            CsvMeta meta) throws DatabaseUnitException {
        super(connection, schema, databaseMeta);
        this.tableName = tableName;
        this.meta = meta;
    }

    @Override
    public void load(DatabaseOperation databaseOperation, URL url) throws SQLException, DatabaseUnitException {
        (this.platform == Platform.MSSQL ? new InsertIdentityOperation(databaseOperation) : databaseOperation)
                .execute(this.connection, new CsvDataSet(url, this.tableName, this.meta));
    }
}
