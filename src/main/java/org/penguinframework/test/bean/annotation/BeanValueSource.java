package org.penguinframework.test.bean.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.penguinframework.test.type.FileType;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
public @interface BeanValueSource {
    /**
     * File path to read.
     *
     * @return File path.
     */
    String value() default "";

    /**
     * File path to read (Alias).
     *
     * @return File path.
     */
    String path() default "";

    FileType type() default FileType.UNKNOWN;

    BeanExcelMeta excelMeta() default @BeanExcelMeta;

    BeanCsvMeta csvMeta() default @BeanCsvMeta;
}
