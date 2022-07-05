package org.penguinframework.test.dataset.excel;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.dbunit.dataset.AbstractTable;
import org.dbunit.dataset.Column;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.DefaultTableMetaData;
import org.dbunit.dataset.ITableMetaData;
import org.dbunit.dataset.datatype.DataType;
import org.dbunit.dataset.datatype.DataTypeException;
import org.dbunit.dataset.excel.XlsDataSetWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExcelTable extends AbstractTable {
    /** Logger object. */
    private static final Logger logger = LoggerFactory.getLogger(ExcelTable.class);

    /** Meta data. */
    private final ITableMetaData metaData;

    /** Record data in Excel file. */
    private final Sheet sheet;

    /** Decimal format symbols. */
    private final DecimalFormatSymbols symbols = new DecimalFormatSymbols();

    public ExcelTable(String sheetName, Sheet sheet) {
        int rowCount = sheet.getLastRowNum();
        if (rowCount >= 0 && sheet.getRow(0) != null) {
            this.metaData = ExcelTable.createMetaData(sheetName, sheet.getRow(0));
        } else {
            this.metaData = new DefaultTableMetaData(sheetName, new Column[0]);
        }

        this.sheet = sheet;

        this.symbols.setDecimalSeparator('.');
    }

    static ITableMetaData createMetaData(String tableName, Row sampleRow) {
        List<Column> columnList = new ArrayList<>();
        for (int i = sampleRow.getFirstCellNum(); i < sampleRow.getLastCellNum(); i++) {
            String columnName = StringUtils.trim(sampleRow.getCell(i).getRichStringCellValue().getString());

            if (StringUtils.isEmpty(columnName)) {
                ExcelTable.logger.debug(
                        "The column name of column # {} is empty - will skip here assuming the last column was reached",
                        String.valueOf(i));
                break;
            }

            columnList.add(new Column(columnName, DataType.UNKNOWN));
        }

        return new DefaultTableMetaData(tableName, columnList.toArray(new Column[0]));
    }

    @Override
    public int getRowCount() {
        return this.sheet.getLastRowNum();
    }

    @Override
    public ITableMetaData getTableMetaData() {
        return this.metaData;
    }

    @Override
    public Object getValue(int row, String column) throws DataSetException {
        this.assertValidRowIndex(row);

        int columnIndex = this.getColumnIndex(column);
        Cell cell = this.sheet.getRow(row + 1).getCell(columnIndex);
        if (cell == null) {
            return null;
        }

        switch (cell.getCellType()) {
        case NUMERIC:
            if (DateUtil.isCellDateFormatted(cell)) {
                return this.getDateValue(cell);
            } else if (XlsDataSetWriter.DATE_FORMAT_AS_NUMBER_DBUNIT
                    .equals(cell.getCellStyle().getDataFormatString())) {
                // 小数部を切り捨て
                return Long.valueOf(BigDecimal.valueOf(cell.getNumericCellValue()).longValue());
            } else {
                return this.getNumericValue(cell);
            }

        case STRING:
            return cell.getRichStringCellValue().getString();

        case FORMULA:
            throw new DataTypeException("Formula not supported at row=" + row + ", column=" + column);

        case BLANK:
            return null;

        case BOOLEAN:
            return Boolean.valueOf(cell.getBooleanCellValue());

        case ERROR:
            throw new DataTypeException("Error at row=" + row + ", column=" + column);

        default:
            throw new DataTypeException("Unsupported type at row=" + row + ", column=" + column);
        }
    }

    private Long getDateValue(Cell cell) {
        // Excelの日付シリアル値(1900年1月1日からの日数)をJavaのDate型に変換
        double dateSerial = cell.getNumericCellValue();
        if (((int) Math.floor(dateSerial)) == 0) {
            // Excelで日付部分が存在しないシリアル値の場合、日付部分を1970-1-1に修正
            dateSerial += 25569;
        }
        Date date = DateUtil.getJavaDate(dateSerial);

        // JavaのDate型を1970年1月1日00:00:00 GMTからのミリ秒数に変換
        return Long.valueOf(date.getTime());
    }

    private BigDecimal getNumericValue(Cell cell) {
        String formatString = cell.getCellStyle().getDataFormatString();
        double cellValue = cell.getNumericCellValue();

        // フォーマットが未設定、"General"、"@"以外の場合
        BigDecimal result = null;
        if (formatString != null && !formatString.equals("General") && !formatString.equals("@")) {
            ExcelTable.logger.debug("formatString={}", formatString);
            DecimalFormat nf = new DecimalFormat(formatString, this.symbols);
            String resultString = nf.format(cellValue);
            try {
                result = new BigDecimal(resultString);
            } catch (NumberFormatException e) {
                ExcelTable.logger.debug("Exception occurred while trying create a BigDecimal. value={}", resultString);
            }
        }

        if (result == null) {
            String resultString = String.valueOf(cellValue);
            if (resultString.endsWith(".0")) {
                resultString = resultString.substring(0, resultString.length() - 2);
            }
            result = new BigDecimal(resultString);
        }
        return result;
    }

    @Override
    public String toString() {
        return "ExcelTable [metaData=" + this.metaData + ", _sheet=" + this.sheet + ", symbols=" + this.symbols + "]";
    }
}
