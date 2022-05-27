package org.penguinframework.test.database.annotation;

/**
 * Excelのシートとデータベースのテーブルの対応を指定するアノテーション。
 */
public @interface SheetMapping {
    /**
     * Excelファイルのシート名を指定。
     * @return Excelファイルのシート名
     */
    String sheet();

    /**
     * データベースのテーブル名を指定。
     * @return データベースのテーブル名
     */
    String table();
}
