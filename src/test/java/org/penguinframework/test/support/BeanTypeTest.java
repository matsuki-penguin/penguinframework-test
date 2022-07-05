package org.penguinframework.test.support;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.penguinframework.test.support.BeanType.Info;

@DisplayName("FileAdapter")
public class BeanTypeTest {

    @Nested
    @DisplayName("void analyzeType(Type type)")
    class analyzeType_type {

        private String methodName = "analyze";

        @Test
        @DisplayName("配列、java.util.Listのいずれでもない型が正しく解析できること")
        @Tag("normal")
        void singleBean() throws Exception {
            class MockClass {
                @SuppressWarnings("unused")
                public Profile profile;
            }

            Type type = FieldUtils.getDeclaredField(MockClass.class, "profile").getGenericType();

            BeanType.Info info = Info.class.cast(MethodUtils.invokeStaticMethod(BeanType.class, this.methodName, type));
            Assertions.assertEquals(Profile.class, info.getActualClass());
            Assertions.assertEquals(false, info.isArray());
            Assertions.assertEquals(false, info.isList());
        }

        @Test
        @DisplayName("配列型が正しく解析できること")
        @Tag("normal")
        void arrayBean() throws Exception {
            class MockClass {
                @SuppressWarnings("unused")
                public Profile[] profiles;
            }

            Type type = FieldUtils.getDeclaredField(MockClass.class, "profiles").getGenericType();

            BeanType.Info info = Info.class.cast(MethodUtils.invokeStaticMethod(BeanType.class, this.methodName, type));
            Assertions.assertEquals(Profile.class, info.getActualClass());
            Assertions.assertEquals(true, info.isArray());
            Assertions.assertEquals(false, info.isList());
        }

        @Test
        @DisplayName("java.util.List型が正しく解析できること")
        @Tag("normal")
        void listBean() throws Exception {
            class MockClass {
                @SuppressWarnings("unused")
                public List<Profile> profileList;
            }

            Type type = FieldUtils.getDeclaredField(MockClass.class, "profileList").getGenericType();

            BeanType.Info info = Info.class.cast(MethodUtils.invokeStaticMethod(BeanType.class, this.methodName, type));
            Assertions.assertEquals(Profile.class, info.getActualClass());
            Assertions.assertEquals(false, info.isArray());
            Assertions.assertEquals(true, info.isList());
        }

        @Test
        @DisplayName("java.util.List型の型パラメータにさらに型パラメータがあるフィールドが正しく解析できること")
        @Tag("normal")
        void listInParamTypeBean() throws Exception {
            class MockClass {
                @SuppressWarnings("unused")
                public List<Class<?>> classList;
            }

            Type type = FieldUtils.getDeclaredField(MockClass.class, "classList").getGenericType();

            BeanType.Info info = Info.class.cast(MethodUtils.invokeStaticMethod(BeanType.class, this.methodName, type));
            Assertions.assertEquals(Class.class, info.getActualClass());
            Assertions.assertEquals(false, info.isArray());
            Assertions.assertEquals(true, info.isList());
        }

        @Test
        @DisplayName("総称型が指定されているフィールドはエラーとなること")
        @Tag("error")
        void typeVariable() {
            class MockClass<T> {
                @SuppressWarnings("unused")
                public T array;
            }

            Type type = FieldUtils.getDeclaredField(MockClass.class, "array").getGenericType();

            InvocationTargetException e = Assertions.assertThrows(InvocationTargetException.class,
                    () -> MethodUtils.invokeStaticMethod(BeanType.class, this.methodName, type));
            Assertions.assertInstanceOf(IllegalArgumentException.class, e.getCause());
            Assertions.assertTrue(StringUtils.containsIgnoreCase(e.getCause().getMessage(), "generic"));
        }

        @Test
        @DisplayName("総称型の配列が指定されているフィールドはエラーとなること")
        @Tag("error")
        void genericArrayType() {
            class MockClass<T> {
                @SuppressWarnings("unused")
                public T[] array;
            }

            Type type = FieldUtils.getDeclaredField(MockClass.class, "array").getGenericType();

            InvocationTargetException e = Assertions.assertThrows(InvocationTargetException.class,
                    () -> MethodUtils.invokeStaticMethod(BeanType.class, this.methodName, type));
            Assertions.assertInstanceOf(IllegalArgumentException.class, e.getCause());
            Assertions.assertTrue(StringUtils.containsIgnoreCase(e.getCause().getMessage(), "generic array"));
        }

        @Test
        @DisplayName("型パラメータが指定されていないjava.util.List型のフィールドはエラーとなること")
        @Tag("error")
        void noParameterizedTypeList() {
            class MockClass {
                @SuppressWarnings({ "unused", "rawtypes" })
                public List list;
            }

            Type type = FieldUtils.getDeclaredField(MockClass.class, "list").getGenericType();

            InvocationTargetException e = Assertions.assertThrows(InvocationTargetException.class,
                    () -> MethodUtils.invokeStaticMethod(BeanType.class, this.methodName, type));
            Assertions.assertInstanceOf(IllegalArgumentException.class, e.getCause());
            Assertions.assertTrue(StringUtils.containsIgnoreCase(e.getCause().getMessage(), "no type parameter"));
        }

        @Test
        @DisplayName("型パラメータにワイルドカードが指定されているjava.util.List型のフィールドはエラーとなること")
        @Tag("error")
        void wildcardTypeList() {
            class MockClass {
                @SuppressWarnings("unused")
                public List<?> list;
            }

            Type type = FieldUtils.getDeclaredField(MockClass.class, "list").getGenericType();

            InvocationTargetException e = Assertions.assertThrows(InvocationTargetException.class,
                    () -> MethodUtils.invokeStaticMethod(BeanType.class, this.methodName, type));
            Assertions.assertInstanceOf(IllegalArgumentException.class, e.getCause());
            Assertions.assertTrue(StringUtils.containsIgnoreCase(e.getCause().getMessage(), "wildcard"));
        }

        @Test
        @DisplayName("型パラメータに総称型が指定されているjava.util.List型のフィールドはエラーとなること")
        @Tag("error")
        void typeVariableList() {
            class MockClass<T> {
                @SuppressWarnings("unused")
                public List<T> list;
            }

            Type type = FieldUtils.getDeclaredField(MockClass.class, "list").getGenericType();

            InvocationTargetException e = Assertions.assertThrows(InvocationTargetException.class,
                    () -> MethodUtils.invokeStaticMethod(BeanType.class, this.methodName, type));
            Assertions.assertInstanceOf(IllegalArgumentException.class, e.getCause());
            Assertions.assertTrue(StringUtils.containsIgnoreCase(e.getCause().getMessage(), "generic"));
        }

        @Test
        @DisplayName("型パラメータに総称型の配列が指定されているjava.util.List型のフィールドはエラーとなること")
        @Tag("error")
        void genericArrayTypeList() {
            class MockClass<T> {
                @SuppressWarnings("unused")
                public List<T[]> list;
            }

            Type type = FieldUtils.getDeclaredField(MockClass.class, "list").getGenericType();

            InvocationTargetException e = Assertions.assertThrows(InvocationTargetException.class,
                    () -> MethodUtils.invokeStaticMethod(BeanType.class, this.methodName, type));
            Assertions.assertInstanceOf(IllegalArgumentException.class, e.getCause());
            Assertions.assertTrue(StringUtils.containsIgnoreCase(e.getCause().getMessage(), "generic array"));
        }
    }

    /**
     * テスト用Java Beanクラス。
     */
    public static class Profile {
        private long id;

        private String name;

        private LocalDate birthday;

        public long getId() {
            return this.id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public LocalDate getBirthday() {
            return this.birthday;
        }

        public void setBirthday(LocalDate birthday) {
            this.birthday = birthday;
        }
    }
}
