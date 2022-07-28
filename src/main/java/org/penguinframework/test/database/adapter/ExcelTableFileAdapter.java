package org.penguinframework.test.database.adapter;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;

import org.dbunit.DatabaseUnitException;
import org.dbunit.ext.mssql.InsertIdentityOperation;
import org.dbunit.operation.DatabaseOperation;
import org.penguinframework.test.database.annotation.DatabaseMeta;
import org.penguinframework.test.dataset.excel.ExcelDataSet;
import org.penguinframework.test.meta.ExcelMeta;
import org.penguinframework.test.type.Platform;

public class ExcelTableFileAdapter extends TableFileAdapter {
    private final ExcelMeta meta;

    public ExcelTableFileAdapter(Connection connection, DatabaseMeta databaseMeta, ExcelMeta meta)
            throws DatabaseUnitException {
        super(connection, databaseMeta);
        this.meta = meta;
    }

    public ExcelTableFileAdapter(Connection connection, String schema, DatabaseMeta databaseMeta, ExcelMeta meta)
            throws DatabaseUnitException {
        super(connection, schema, databaseMeta);
        this.meta = meta;
    }

    @Override
    public void load(DatabaseOperation databaseOperation, URL url)
            throws IOException, SQLException, DatabaseUnitException {
        (this.platform == Platform.MSSQL ? new InsertIdentityOperation(databaseOperation) : databaseOperation)
                .execute(this.connection, new ExcelDataSet(url, this.meta));
    }
}
