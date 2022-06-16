package org.penguinframework.test.database.adapter;

import java.net.URL;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.csv.CSVFormat;
import org.dbunit.DatabaseUnitException;
import org.dbunit.operation.DatabaseOperation;
import org.penguinframework.test.dataset.csv.CsvDataSet;

public class CsvTableFileAdapter extends TableFileAdapter {
    private final String tableName;
    private final Charset charset;
    private final CSVFormat csvFormat;

    public CsvTableFileAdapter(Connection connection, String tableName, Charset charset, CSVFormat csvFormat)
            throws DatabaseUnitException {
        super(connection);
        this.tableName = tableName;
        this.charset = charset;
        this.csvFormat = csvFormat;
    }

    public CsvTableFileAdapter(Connection connection, String schema, String tableName, Charset charset, CSVFormat csvFormat)
            throws DatabaseUnitException {
        super(connection, schema);
        this.tableName = tableName;
        this.charset = charset;
        this.csvFormat = csvFormat;
    }

    @Override
    public void load(DatabaseOperation databaseOperation, URL url) throws SQLException, DatabaseUnitException {
        databaseOperation.execute(this.connection, new CsvDataSet(url, this.tableName, this.charset, this.csvFormat));
    }
}
