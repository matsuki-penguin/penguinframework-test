package org.penguinframework.test.support.converter;

import org.apache.commons.lang3.reflect.MethodUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@DisplayName("AbstractDateTimeConverter")
public class AbstractDateTimeConverterTest {

    @Nested
    @DisplayName("String toStringType(final Class<?> type)")
    class toStringType_class {

        private String methodName = "toStringType";
        private AbstractDateTimeConverter instance = new CalendarConverter();

        @Test
        @DisplayName("java.langパッケージ配下のクラス名を単純名で取得できること")
        @Tag("normal")
        void javaLangClass() throws Exception {
            String result = String.class
                    .cast(MethodUtils.invokeMethod(this.instance, true, this.methodName, java.lang.String.class));
            Assertions.assertEquals("String", result);
        }

        @Test
        @DisplayName("java.utilパッケージ配下のクラス名を単純名で取得できること")
        @Tag("normal")
        void javaUtilClass() throws Exception {
            String result = String.class
                    .cast(MethodUtils.invokeMethod(this.instance, true, this.methodName, java.util.List.class));
            Assertions.assertEquals("List", result);
        }

        @Test
        @DisplayName("java.mathパッケージ配下のクラス名を単純名で取得できること")
        @Tag("normal")
        void javaMathClass() throws Exception {
            String result = String.class
                    .cast(MethodUtils.invokeMethod(this.instance, true, this.methodName, java.math.BigDecimal.class));
            Assertions.assertEquals("BigDecimal", result);
        }

        @Test
        @DisplayName("java.lang、java.util、java.mathパッケージ配下以外のクラス名を完全修飾名で取得できること")
        @Tag("normal")
        void javaIoClass() throws Exception {
            String result = String.class
                    .cast(MethodUtils.invokeMethod(this.instance, true, this.methodName, java.io.InputStream.class));
            Assertions.assertEquals("java.io.InputStream", result);
        }

        @Test
        @DisplayName("コンバータパッケージ配下のクラス名を単純名で取得できること")
        @Tag("normal")
        void converterClass() throws Exception {
            String result = String.class.cast(MethodUtils.invokeMethod(this.instance, true, this.methodName,
                    org.penguinframework.test.support.converter.CalendarConverter.class));
            Assertions.assertEquals("CalendarConverter", result);
        }

        @Test
        @DisplayName("1次元配列のクラス名を取得できること")
        @Tag("normal")
        void singleArrayClass() throws Exception {
            String result = String.class
                    .cast(MethodUtils.invokeMethod(this.instance, true, this.methodName, java.lang.String[].class));
            Assertions.assertEquals("String[]", result);
        }

        @Test
        @DisplayName("多次元配列のクラス名を取得できること")
        @Tag("normal")
        void multiArrayClass() throws Exception {
            String result = String.class
                    .cast(MethodUtils.invokeMethod(this.instance, true, this.methodName, java.lang.String[][][].class));
            Assertions.assertEquals("String[][][]", result);
        }

        @Test
        @DisplayName("null指定時のクラス名を取得できること")
        @Tag("normal")
        void nullClass() throws Exception {
            String result = String.class.cast(MethodUtils.invokeMethod(this.instance, true, this.methodName,
                    new Object[] { null }, new Class<?>[] { Class.class }));
            Assertions.assertEquals("null", result);
        }

        @Test
        @DisplayName("java.langパッケージ配下の別パッケージのクラス名を単純名で取得できること")
        @Tag("normal")
        void javaLangReflectClass() throws Exception {
            String result = String.class.cast(
                    MethodUtils.invokeMethod(this.instance, true, this.methodName, java.lang.reflect.Array.class));
            Assertions.assertEquals("Array", result);
        }
    }
}
