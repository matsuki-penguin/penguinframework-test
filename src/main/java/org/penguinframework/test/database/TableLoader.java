package org.penguinframework.test.database;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.dbunit.DatabaseUnitException;
import org.dbunit.operation.DatabaseOperation;
import org.junit.platform.commons.util.AnnotationUtils;
import org.penguinframework.test.database.adapter.CsvFileAdapter;
import org.penguinframework.test.database.adapter.ExcelFileAdapter;
import org.penguinframework.test.database.adapter.FileAdapter;
import org.penguinframework.test.database.annotation.SheetMapping;
import org.penguinframework.test.database.annotation.TableValueSource;

public class TableLoader {
    private enum FileType {
        EXCEL, CSV
    }

    private static final Map<String, FileType> FILE_TYPE_MAP = Map.of("xls", FileType.EXCEL, "xlsx", FileType.EXCEL,
            "xlsm", FileType.EXCEL, "csv", FileType.CSV);

    private TableLoader() {
    }

    public static void load(Method targetMethod, Connection connection)
            throws DatabaseUnitException, IOException, SQLException {
        Class<?> targetClass = targetMethod.getDeclaringClass();

        // クラスに指定されているTableValueSourceアノテーションを取得
        List<TableValueSource> tableValueSourceListForClass = AnnotationUtils.findRepeatableAnnotations(targetClass,
                TableValueSource.class);

        // メソッドに指定されているTableValueSourceアノテーションを取得
        List<TableValueSource> tableValueSourceListForMethod = AnnotationUtils.findRepeatableAnnotations(targetMethod,
                TableValueSource.class);

        // アノテーションリストを結合
        List<TableValueSource> tableValueSourceList = Stream
                .concat(tableValueSourceListForClass.stream(), tableValueSourceListForMethod.stream())
                .collect(Collectors.toList());

        for (TableValueSource tableValueSource : tableValueSourceList) {
            TableLoader.loadFromAnnotation(tableValueSource, targetClass, connection);
        }
    }

    private static void loadFromAnnotation(TableValueSource tableValueSource, Class<?> targetClass,
            Connection connection) throws DatabaseUnitException, SQLException, IOException {
        DatabaseOperation databaseOperation = null;
        switch (tableValueSource.operation()) {
        case CLEAN_INSERT:
            databaseOperation = DatabaseOperation.CLEAN_INSERT;
            break;
        case INSERT:
            databaseOperation = DatabaseOperation.INSERT;
            break;
        }

        CSVFormat csvFormat = null;
        switch (tableValueSource.csvMeta().format()) {
        case DEFAULT:
            csvFormat = CSVFormat.DEFAULT;
            break;
        case EXCEL:
            csvFormat = CSVFormat.EXCEL;
            break;
        case INFORMIX_UNLOAD:
            csvFormat = CSVFormat.INFORMIX_UNLOAD;
            break;
        case INFORMIX_UNLOAD_CSV:
            csvFormat = CSVFormat.INFORMIX_UNLOAD_CSV;
            break;
        case MONGODB_CSV:
            csvFormat = CSVFormat.MONGODB_CSV;
            break;
        case MONGODB_TSV:
            csvFormat = CSVFormat.MONGODB_TSV;
            break;
        case MYSQL:
            csvFormat = CSVFormat.MYSQL;
            break;
        case ORACLE:
            csvFormat = CSVFormat.ORACLE;
            break;
        case POSTGRESQL_CSV:
            csvFormat = CSVFormat.POSTGRESQL_CSV;
            break;
        case POSTGRESQL_TEXT:
            csvFormat = CSVFormat.POSTGRESQL_TEXT;
            break;
        case RFC4180, TDF:
            csvFormat = CSVFormat.TDF;
            break;
        }

        // 読み込むファイルのURLオブジェクトを生成
        URL url = targetClass.getResource(StringUtils.firstNonEmpty(tableValueSource.value(), tableValueSource.path()));

        FileType fileType = TableLoader.FILE_TYPE_MAP.get(FilenameUtils.getExtension(url.getFile()));
        FileAdapter fileAdapter;
        switch (fileType) {
        case EXCEL:
            Map<String, String> remapTableName = Arrays.stream(tableValueSource.excelMeta().sheetMapping())
                    .collect(Collectors.toMap(SheetMapping::sheet, SheetMapping::table, (name1, name2) -> name1));
            fileAdapter = new ExcelFileAdapter(connection, connection.getSchema(), remapTableName);
            break;
        case CSV:
            fileAdapter = new CsvFileAdapter(connection, connection.getSchema(), tableValueSource.csvMeta().table(),
                    Charset.forName(tableValueSource.csvMeta().fileEncoding()), csvFormat);
            break;
        default:
            return;
        }
        fileAdapter.load(databaseOperation, url);
    }
}
