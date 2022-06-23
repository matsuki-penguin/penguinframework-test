package org.penguinframework.test.database.assertion;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;

import org.dbunit.Assertion;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.penguinframework.test.dataset.excel.ExcelDataSet;
import org.penguinframework.test.exception.AssertRuntimeException;
import org.penguinframework.test.meta.Meta;

public class ExcelTableAssertion {

    private final Connection connection;

    private final Class<?> testClass;

    public ExcelTableAssertion(Connection connection, Class<?> testClass) {
        super();
        this.connection = connection;
        this.testClass = testClass;
    }

    public void assertEquals(String expectedFilePath, String actualTableName) {
        URL url = this.testClass.getResource(expectedFilePath);

        try {
            IDataSet expectedDataSet = new ExcelDataSet(url, Meta.excel());
            ITable expectedTable = expectedDataSet.getTable(actualTableName);

            DatabaseConnection databaseConnection = new DatabaseConnection(this.connection,
                    this.connection.getSchema());
            IDataSet dataSet = databaseConnection.createDataSet();
            ITable actualTable = dataSet.getTable(actualTableName);

            Assertion.assertEquals(expectedTable, actualTable);
        } catch (IOException | DatabaseUnitException | SQLException e) {
            throw new AssertRuntimeException(e);
        }
    }
}
