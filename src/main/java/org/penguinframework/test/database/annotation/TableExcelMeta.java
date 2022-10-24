package org.penguinframework.test.database.annotation;

public @interface TableExcelMeta {
    /**
     * Excelのシートとデータベースのテーブルの対応を指定。
     *
     * @return Excelのシートとデータベースのテーブルの対応
     */
    SheetMeta[] sheetMeta() default {};
}
