package org.penguinframework.test.database.adapter;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import org.dbunit.DatabaseUnitException;
import org.dbunit.operation.DatabaseOperation;
import org.penguinframework.test.dataset.excel.ExcelDataSet;

public class ExcelFileAdapter extends FileAdapter {
    private final Map<String, String> remapTableName;

    public ExcelFileAdapter(Connection connection, Map<String, String> remapTableName) throws DatabaseUnitException {
        super(connection);
        this.remapTableName = remapTableName;
    }

    public ExcelFileAdapter(Connection connection, String schema, Map<String, String> remapTableName)
            throws DatabaseUnitException {
        super(connection, schema);
        this.remapTableName = remapTableName;
    }

    @Override
    public void load(DatabaseOperation databaseOperation, URL url)
            throws IOException, SQLException, DatabaseUnitException {
        databaseOperation.execute(this.connection, new ExcelDataSet(url, this.remapTableName));
    }
}
