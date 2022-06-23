package org.penguinframework.test.database.adapter;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;

import org.dbunit.DatabaseUnitException;
import org.dbunit.operation.DatabaseOperation;
import org.penguinframework.test.dataset.excel.ExcelDataSet;
import org.penguinframework.test.meta.ExcelMeta;

public class ExcelTableFileAdapter extends TableFileAdapter {
    private final ExcelMeta meta;

    public ExcelTableFileAdapter(Connection connection, ExcelMeta meta) throws DatabaseUnitException {
        super(connection);
        this.meta = meta;
    }

    public ExcelTableFileAdapter(Connection connection, String schema, ExcelMeta meta) throws DatabaseUnitException {
        super(connection, schema);
        this.meta = meta;
    }

    @Override
    public void load(DatabaseOperation databaseOperation, URL url)
            throws IOException, SQLException, DatabaseUnitException {
        databaseOperation.execute(this.connection, new ExcelDataSet(url, this.meta));
    }
}
