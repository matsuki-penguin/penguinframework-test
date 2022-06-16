package org.penguinframework.test.database.adapter;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import org.dbunit.DatabaseUnitException;
import org.dbunit.operation.DatabaseOperation;
import org.penguinframework.test.dataset.excel.ExcelDataSet;

public class ExcelTableFileAdapter extends TableFileAdapter {
    private final Map<String, String> remapTableName;

    public ExcelTableFileAdapter(Connection connection, Map<String, String> remapTableName) throws DatabaseUnitException {
        super(connection);
        this.remapTableName = remapTableName;
    }

    public ExcelTableFileAdapter(Connection connection, String schema, Map<String, String> remapTableName)
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
