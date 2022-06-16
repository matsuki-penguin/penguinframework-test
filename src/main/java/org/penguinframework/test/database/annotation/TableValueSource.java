package org.penguinframework.test.database.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.penguinframework.test.type.OperationType;

@Repeatable(TableValueSources.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
public @interface TableValueSource {
    /**
     * 読み込むExcelファイル名を指定。
     *
     * @return 読み込むExcelファイル名
     */
    String value() default "";

    String path() default "";

    TableExcelMeta excelMeta() default @TableExcelMeta;

    TableCsvMeta csvMeta() default @TableCsvMeta;

    OperationType operation() default OperationType.CLEAN_INSERT;
}
