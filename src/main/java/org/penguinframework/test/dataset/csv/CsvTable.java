package org.penguinframework.test.dataset.csv;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.compress.utils.FileNameUtils;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.input.BOMInputStream;
import org.apache.commons.lang3.StringUtils;
import org.dbunit.dataset.AbstractTable;
import org.dbunit.dataset.Column;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.DefaultTableMetaData;
import org.dbunit.dataset.ITableMetaData;
import org.dbunit.dataset.datatype.DataType;
import org.penguinframework.test.meta.CsvMeta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CsvTable extends AbstractTable {
    /** Logger object. */
    private static final Logger logger = LoggerFactory.getLogger(CsvTable.class);

    /** Meta data. */
    private final ITableMetaData metaData;

    /** Record data in CSV file. */
    private final List<CSVRecord> recordList;

    /** String representing null. */
    private final String nullString;

    /**
     * Constructor for reading CSV files from URL and character set.
     *
     * @param url       CSV file URL object.
     * @param tableName Table name to be set for metadata. If null, the file name is
     *                  used.
     * @param meta      CSV file meta data.
     */
    public CsvTable(URL url, String tableName, CsvMeta meta) {
        List<String> headerNameList;
        try (BOMInputStream in = new BOMInputStream(url.openStream());
                InputStreamReader reader = new InputStreamReader(in, meta.encoding());
                CSVParser csvParser = meta.format().getCsvFormat().builder().setHeader().setSkipHeaderRecord(true)
                        .build().parse(reader)) {
            headerNameList = csvParser.getHeaderNames();
            this.recordList = csvParser.getRecords();
        } catch (IOException e) {
            CsvTable.logger.error("Error in CSV file input process.", e);
            throw new UncheckedIOException(e);
        }

        // 拡張子を除くファイル名、ヘッダ行(1行目)からメタデータを生成
        Column[] columns = this.recordList.isEmpty() ? new Column[0] : this.createMetaData(headerNameList);
        this.metaData = new DefaultTableMetaData(
                StringUtils.defaultIfEmpty(tableName, FileNameUtils.getBaseName(url.getPath())), columns);

        this.nullString = meta.nullString();
    }

    private Column[] createMetaData(List<String> headerNameList) {
        List<Column> columnList = new ArrayList<>();
        for (int i = 0; i < headerNameList.size(); i++) {
            String headerName = StringUtils.trim(headerNameList.get(i));

            if (StringUtils.isEmpty(headerName)) {
                CsvTable.logger.debug(
                        "The column name of column # {} is empty - will skip here assuming the last column was reached",
                        String.valueOf(i));
                break;
            }

            columnList.add(new Column(headerName, DataType.UNKNOWN));
        }

        return columnList.toArray(new Column[0]);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getRowCount() {
        return this.recordList.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ITableMetaData getTableMetaData() {
        return this.metaData;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getValue(int row, String column) throws DataSetException {
        this.assertValidRowIndex(row);

        int columnIndex = this.getColumnIndex(column);
        String value = this.recordList.get(row).get(columnIndex);
        return this.nullString.equals(value) ? null : value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "CsvTable [metaData=" + this.metaData + ", recordList=" + this.recordList + "]";
    }
}
