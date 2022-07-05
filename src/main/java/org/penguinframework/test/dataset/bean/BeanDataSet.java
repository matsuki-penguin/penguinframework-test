package org.penguinframework.test.dataset.bean;

import java.util.Collection;

import org.dbunit.database.AmbiguousTableNameException;
import org.dbunit.dataset.AbstractDataSet;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.DefaultTableIterator;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.ITableIterator;
import org.dbunit.dataset.OrderedTableNameMap;

public class BeanDataSet extends AbstractDataSet {
    /** Map with table names as keys and table contents as values. */
    private final OrderedTableNameMap tables;

    /**
     * Constructor for reading bean field from object.
     *
     * @param target      Bean object.
     * @param targetClass Bean class.
     * @throws AmbiguousTableNameException
     */
    public BeanDataSet(Object target, Class<?> targetClass) throws AmbiguousTableNameException {
        this.tables = super.createTableNameMap();

        ITable table = new BeanTable(target, targetClass);
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