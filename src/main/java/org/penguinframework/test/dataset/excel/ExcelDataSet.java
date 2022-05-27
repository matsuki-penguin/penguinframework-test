package org.penguinframework.test.dataset.excel;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.dbunit.dataset.AbstractDataSet;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.DefaultTableIterator;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.ITableIterator;
import org.dbunit.dataset.OrderedTableNameMap;

public class ExcelDataSet extends AbstractDataSet {

    private final OrderedTableNameMap tables;

    public ExcelDataSet(URL url) throws IOException, DataSetException {
        this(url, Collections.emptyMap());
    }

    /**
     * Creates a new XlsDataSet object that loads the specified Excel document.
     */
    public ExcelDataSet(URL url, Map<String, String> remapSheetName) throws IOException, DataSetException {
        this.tables = super.createTableNameMap();

        Workbook workbook;
        try {
            workbook = WorkbookFactory.create(url.openStream());
        } catch (EncryptedDocumentException e) {
            throw new IOException(e);
        }

        int sheetCount = workbook.getNumberOfSheets();
        for (int i = 0; i < sheetCount; i++) {
            String sheetName = remapSheetName.getOrDefault(workbook.getSheetName(i), workbook.getSheetName(i));
            ITable table = new ExcelTable(sheetName, workbook.getSheetAt(i));
            this.tables.add(table.getTableMetaData().getTableName(), table);
        }
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