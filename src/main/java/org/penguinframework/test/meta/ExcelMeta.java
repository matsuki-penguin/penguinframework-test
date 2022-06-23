package org.penguinframework.test.meta;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

import org.penguinframework.test.bean.annotation.BeanExcelMeta;
import org.penguinframework.test.database.annotation.SheetMapping;
import org.penguinframework.test.database.annotation.TableExcelMeta;

public class ExcelMeta extends Meta {

    private Map<String, String> remapSheetName = Collections.emptyMap();

    protected ExcelMeta() {
        super();
    }

    public static ExcelMeta of(TableExcelMeta annotation) {
        Map<String, String> remapTableName = Arrays.stream(annotation.sheetMapping())
                .collect(Collectors.toMap(SheetMapping::sheet, SheetMapping::table, (name1, name2) -> name1));
        return new ExcelMeta().remapSheetName(remapTableName);
    }

    public static ExcelMeta of(BeanExcelMeta annotation) {
        return new ExcelMeta();
    }

    public ExcelMeta remapSheetName(Map<String, String> remapSheetName) {
        this.remapSheetName = remapSheetName;
        return this;
    }

    public Map<String, String> remapSheetName() {
        return this.remapSheetName;
    }
}
