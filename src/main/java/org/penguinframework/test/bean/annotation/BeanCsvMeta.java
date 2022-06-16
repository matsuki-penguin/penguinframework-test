package org.penguinframework.test.bean.annotation;

import org.penguinframework.test.type.CsvFormatType;

public @interface BeanCsvMeta {
    String encoding() default "utf-8";

    CsvFormatType format() default CsvFormatType.DEFAULT;
}
