package org.penguinframework.test.support;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.List;

public class BeanType {

    public static class Info {
        private Class<?> actualClass;
        private boolean array = false;
        private boolean list = false;

        public Class<?> getActualClass() {
            return this.actualClass;
        }

        public void setActualClass(Class<?> actualClass) {
            this.actualClass = actualClass;
        }

        public boolean isArray() {
            return this.array;
        }

        public void setArray(boolean array) {
            this.array = array;
        }

        public boolean isList() {
            return this.list;
        }

        public void setList(boolean list) {
            this.list = list;
        }
    }

    public static Info analyze(Type type) {
        Info info = new Info();

        Class<?> actualClass = null;
        if (type instanceof Class) {
            actualClass = Class.class.cast(type);
            if (List.class.isAssignableFrom(actualClass)) {
                // 型パラメータが指定されていないjava.util.Listはエラー
                throw new IllegalArgumentException("No type parameter is specified for the java.util.List.");
            } else if (actualClass.isArray()) {
                info.setArray(true);
                actualClass = actualClass.getComponentType();
            }
        } else if (type instanceof ParameterizedType) {
            actualClass = Class.class.cast(ParameterizedType.class.cast(type).getRawType());
            if (List.class.isAssignableFrom(actualClass)) {
                info.setList(true);
                Type[] argTypes = ParameterizedType.class.cast(type).getActualTypeArguments();
                if (argTypes[0] instanceof Class) {
                    actualClass = Class.class.cast(argTypes[0]);
                } else if (argTypes[0] instanceof ParameterizedType) {
                    actualClass = Class.class.cast(ParameterizedType.class.cast(argTypes[0]).getRawType());
                } else if (argTypes[0] instanceof WildcardType) {
                    // ワイルドカードの型パラメータはエラー
                    throw new IllegalArgumentException(
                            "A wildcard is specified for the type parameter of the java.util.List.");
                } else if (argTypes[0] instanceof TypeVariable<?>) {
                    // 総称型の型パラメータはエラー
                    throw new IllegalArgumentException(
                            "A generic type is specified for the type parameter of the java.util.List.");
                } else if (argTypes[0] instanceof GenericArrayType) {
                    // 総称型の配列の型パラメータはエラー
                    throw new IllegalArgumentException(
                            "A generic array is specified as a type parameter of type java.util.List.");
                }
            }
        } else if (type instanceof TypeVariable<?>) {
            // 総称型の型パラメータはエラー
            throw new IllegalArgumentException("A generic type is specified for the field type.");
        } else if (type instanceof GenericArrayType) {
            // 総称型の配列の型パラメータはエラー
            throw new IllegalArgumentException("A generic array is specified for the field type.");
        }

        info.setActualClass(actualClass);

        return info;
    }

    public static Info analyze(Object object, Class<?> clazz) {
        Info info = new Info();

        Class<?> actualClass = object.getClass();
        if (List.class.isAssignableFrom(actualClass)) {
            info.setList(true);
        } else if (actualClass.isArray()) {
            info.setArray(true);
        }

        info.setActualClass(clazz);

        return info;
    }
}
