package org.penguinframework.test.database.assertion;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;

import org.dbunit.Assertion;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.penguinframework.test.database.annotation.DatabaseMeta;
import org.penguinframework.test.dataset.csv.CsvDataSet;
import org.penguinframework.test.dataset.excel.ExcelDataSet;
import org.penguinframework.test.exception.AssertRuntimeException;
import org.penguinframework.test.meta.CsvMeta;
import org.penguinframework.test.meta.ExcelMeta;
import org.penguinframework.test.meta.Meta;
import org.penguinframework.test.support.DatabaseUtils;
import org.penguinframework.test.type.FileType;
import org.penguinframework.test.type.Platform;

public class TableAssertion {

    private final Class<?> testClass;

    private final IDatabaseConnection databaseConnection;

    public TableAssertion(Connection connection, Class<?> testClass, DatabaseMeta databaseMeta)
            throws DatabaseUnitException, SQLException {
        super();
        this.testClass = testClass;
        Platform platform = databaseMeta == null ? Platform.DEFAULT : databaseMeta.platform();
        this.databaseConnection = DatabaseUtils.getDatabaseConnection(connection, connection.getSchema(), platform);

    }

    public void assertEquals(String expectedFilePath, String actualTableName) {
        this.assertEquals(expectedFilePath, null, actualTableName);
    }

    public void assertEquals(String expectedFilePath, Meta meta, String actualTableName) {
        URL url = this.testClass.getResource(expectedFilePath);
        FileType fileType = FileType.valueOf(url);

        try {
            IDataSet expectedDataSet;
            switch (fileType) {
            case EXCEL:
                ExcelMeta excelMeta = (meta instanceof ExcelMeta) ? ExcelMeta.class.cast(meta) : Meta.excel();
                expectedDataSet = new ExcelDataSet(url, excelMeta);
                break;
            case CSV:
                CsvMeta csvMeta = (meta instanceof CsvMeta) ? CsvMeta.class.cast(meta) : Meta.csv();
                expectedDataSet = new CsvDataSet(url, actualTableName, csvMeta);
                break;
            default:
                throw new IllegalArgumentException("Unknown file type. : " + expectedFilePath);
            }
            ITable expectedTable = expectedDataSet.getTable(actualTableName);

            IDataSet dataSet = this.databaseConnection.createDataSet();
            ITable actualTable = dataSet.getTable(actualTableName);

            Assertion.assertEquals(expectedTable, actualTable);
        } catch (IOException | DatabaseUnitException | SQLException e) {
            throw new AssertRuntimeException(e);
        }
    }
}
