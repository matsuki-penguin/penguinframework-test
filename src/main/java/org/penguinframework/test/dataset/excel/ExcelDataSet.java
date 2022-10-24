package org.penguinframework.test.dataset.excel;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.dbunit.dataset.AbstractDataSet;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.DefaultTableIterator;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.ITableIterator;
import org.dbunit.dataset.OrderedTableNameMap;
import org.penguinframework.test.meta.ExcelMeta;

public class ExcelDataSet extends AbstractDataSet {

    private final OrderedTableNameMap tables;

    /**
     * Creates a new XlsDataSet object that loads the specified Excel document.
     *
     * @param url  Excel file URL object.
     * @param meta Excel file meta data.
     * @throws IOException
     * @throws DataSetException
     */
    public ExcelDataSet(URL url, ExcelMeta meta) throws IOException, DataSetException {
        this.tables = super.createTableNameMap();

        try (Workbook workbook = WorkbookFactory.create(url.openStream())) {
            int sheetCount = workbook.getNumberOfSheets();
            for (int i = 0; i < sheetCount; i++) {
                String sheetName = meta.remapSheetName().getOrDefault(workbook.getSheetName(i),
                        workbook.getSheetName(i));
                ITable table = new ExcelTable(sheetName, workbook.getSheetAt(i),
                        meta.ignoreCols().get(workbook.getSheetName(i)));
                this.tables.add(table.getTableMetaData().getTableName(), table);
            }
        } catch (EncryptedDocumentException e) {
            throw new IOException(e);
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