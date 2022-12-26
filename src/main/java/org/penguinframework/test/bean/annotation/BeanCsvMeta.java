package org.penguinframework.test.bean.annotation;

import org.penguinframework.test.meta.CsvMeta;
import org.penguinframework.test.type.CsvFormatType;

public @interface BeanCsvMeta {
    String encoding() default CsvMeta.DEFAULT_ENCODING;

    CsvFormatType format() default CsvFormatType.DEFAULT; // = CsvMeta.DEFAULT_FORMAT

    String nullString() default CsvMeta.DEFAULT_NULL_STRING;

    /**
     * 読み込み対象外とする列名を指定
     * 
     * @return 読み込み対象外とする列名
     */
    String[] ignoreCols() default {};
}
