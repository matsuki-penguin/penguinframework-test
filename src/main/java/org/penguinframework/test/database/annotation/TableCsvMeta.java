package org.penguinframework.test.database.annotation;

import org.penguinframework.test.meta.CsvMeta;
import org.penguinframework.test.type.CsvFormatType;

public @interface TableCsvMeta {
    String table() default "";

    String encoding() default CsvMeta.DEFAULT_ENCODING;

    CsvFormatType format() default CsvFormatType.DEFAULT; // = CsvMeta.DEFAULT_FORMAT

    String nullString() default CsvMeta.DEFAULT_NULL_STRING;
}
