package org.penguinframework.test.bean.annotation;

public @interface BeanExcelMeta {
    String sheet() default "";

    /**
     * 読み込み対象外とする列名を指定
     * 
     * @return 読み込み対象外とする列名
     */
    String[] ignoreCols() default {};
}
