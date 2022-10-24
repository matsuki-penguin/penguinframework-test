package org.penguinframework.test.meta;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

import org.penguinframework.test.bean.annotation.BeanExcelMeta;
import org.penguinframework.test.database.annotation.SheetMeta;
import org.penguinframework.test.database.annotation.TableExcelMeta;

public class ExcelMeta extends Meta {

    private Map<String, String> remapSheetName = Collections.emptyMap();
    private Map<String, String[]> ignoreCols = Collections.emptyMap();

    protected ExcelMeta() {
        super();
    }

    public static ExcelMeta of(TableExcelMeta annotation) {
        Map<String, String> remapTableName = Arrays.stream(annotation.sheetMeta())
                .filter(meta -> !meta.table().equals(""))
                .collect(Collectors.toMap(SheetMeta::sheet, SheetMeta::table, (name1, name2) -> name1));
        Map<String, String[]> ignoreCols = Arrays.stream(annotation.sheetMeta())
                .collect(Collectors.toMap(SheetMeta::sheet, SheetMeta::ignoreCols, (name1, name2) -> name1));
        return new ExcelMeta().remapSheetName(remapTableName).ignoreCols(ignoreCols);
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

    public ExcelMeta ignoreCols(Map<String, String[]> ignoreCols) {
        this.ignoreCols = ignoreCols;
        return this;
    }

    public Map<String, String[]> ignoreCols() {
        return this.ignoreCols;
    }
}
