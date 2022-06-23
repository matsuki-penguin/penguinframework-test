package org.penguinframework.test.dataset.csv;

import java.net.URL;
import java.util.Collection;

import org.dbunit.database.AmbiguousTableNameException;
import org.dbunit.dataset.AbstractDataSet;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.DefaultTableIterator;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.ITableIterator;
import org.dbunit.dataset.OrderedTableNameMap;
import org.penguinframework.test.meta.CsvMeta;

public class CsvDataSet extends AbstractDataSet {
    /** Map with table names as keys and table contents as values. */
    private final OrderedTableNameMap tables;

    /**
     * Constructor for reading CSV files from URL and character set.
     *
     * @param url       CSV file URL object.
     * @param tableName Table name to be set for metadata. If null, the file name is
     *                  used.
     * @param meta      CSV file meta data.
     * @throws AmbiguousTableNameException
     */
    public CsvDataSet(URL url, String tableName, CsvMeta meta) throws AmbiguousTableNameException {
        this.tables = super.createTableNameMap();

        ITable table = new CsvTable(url, tableName, meta);
        this.tables.add(table.getTableMetaData().getTableName(), table);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ITableIterator createIterator(boolean reversed) throws DataSetException {
        Collection<?> orderedValues = this.tables.orderedValues();
        return new DefaultTableIterator(orderedValues.toArray(new ITable[0]), reversed);
    }
}