package org.penguinframework.test.meta;

public abstract class Meta {
    public static CsvMeta csv() {
        return new CsvMeta();
    }

    public static ExcelMeta excel() {
        return new ExcelMeta();
    }
}
