package org.penguinframework.test.database.annotation;

/**
 * Excelのシートのメタ情報を指定するアノテーション。
 */
public @interface SheetMeta {
    /**
     * Excelファイルのシート名を指定。
     * 
     * @return Excelファイルのシート名
     */
    String sheet();

    /**
     * データベースのテーブル名を指定。
     * 
     * @return データベースのテーブル名
     */
    String table() default "";

    /**
     * 読み込み対象外とするExcelの列名を指定
     * 
     * @return 読み込み対象外とするExcelの列名
     */
    String[] ignoreCols() default {};
}
