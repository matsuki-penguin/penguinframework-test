package org.penguinframework.test.database;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.dbunit.DatabaseUnitException;
import org.junit.platform.commons.util.AnnotationUtils;
import org.penguinframework.test.database.adapter.CsvTableFileAdapter;
import org.penguinframework.test.database.adapter.ExcelTableFileAdapter;
import org.penguinframework.test.database.adapter.TableFileAdapter;
import org.penguinframework.test.database.annotation.DatabaseMeta;
import org.penguinframework.test.database.annotation.TableValueSource;
import org.penguinframework.test.meta.CsvMeta;
import org.penguinframework.test.meta.ExcelMeta;
import org.penguinframework.test.type.FileType;

public class TableLoader {
    private TableLoader() {
    }

    public static void load(Method targetMethod, Connection connection, DatabaseMeta databaseMeta)
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
            TableLoader.loadFromAnnotation(tableValueSource, targetClass, connection, databaseMeta);
        }
    }

    private static void loadFromAnnotation(TableValueSource tableValueSource, Class<?> targetClass,
            Connection connection, DatabaseMeta databaseMeta) throws DatabaseUnitException, SQLException, IOException {
        // 読み込むファイルのURLオブジェクトを生成
        URL url = targetClass.getResource(StringUtils.firstNonEmpty(tableValueSource.value(), tableValueSource.path()));

        TableFileAdapter fileAdapter;
        switch (FileType.valueOf(url)) {
        case EXCEL:
            fileAdapter = new ExcelTableFileAdapter(connection, connection.getSchema(), databaseMeta,
                    ExcelMeta.of(tableValueSource.excelMeta()));
            break;
        case CSV:
            fileAdapter = new CsvTableFileAdapter(connection, connection.getSchema(),
                    tableValueSource.csvMeta().table(), databaseMeta, CsvMeta.of(tableValueSource.csvMeta()));
            break;
        default:
            return;
        }
        fileAdapter.load(tableValueSource.operation().getDatabaseOperation(), url);
    }
}
