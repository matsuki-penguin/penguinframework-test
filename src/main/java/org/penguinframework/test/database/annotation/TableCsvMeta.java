package org.penguinframework.test.database.annotation;

import org.penguinframework.test.type.CsvFormatType;

public @interface TableCsvMeta {
    String table() default "";

    String encoding() default "utf-8";

    CsvFormatType format() default CsvFormatType.DEFAULT;
}
