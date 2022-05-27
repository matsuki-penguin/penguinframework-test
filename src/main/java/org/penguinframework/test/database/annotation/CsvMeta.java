package org.penguinframework.test.database.annotation;

import org.penguinframework.test.database.annotation.type.FormatType;

public @interface CsvMeta {
    String table() default "";

    String fileEncoding() default "utf-8";

    FormatType format() default FormatType.DEFAULT;
}
