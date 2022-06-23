package org.penguinframework.test.bean.adapter;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.dbunit.dataset.Column;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.ITable;

public abstract class BeanFileAdapter {
    protected Class<?> actualClass;
    protected boolean isArray = false;
    protected boolean isList = false;

    private static final Set<Class<?>> SUPPORT_TYPE = new HashSet<>(Arrays.asList(boolean.class, byte.class, char.class,
            short.class, int.class, long.class, float.class, double.class, Boolean.class, Byte.class, Character.class,
            Short.class, Integer.class, Long.class, Float.class, Double.class, String.class, BigInteger.class,
            BigDecimal.class, Class.class, java.util.Date.class, java.sql.Date.class, java.sql.Time.class,
            java.sql.Timestamp.class, Calendar.class, LocalDate.class, LocalTime.class, LocalDateTime.class,
            Instant.class, File.class, URL.class));

    public abstract Object load(URL url, Type type) throws ReflectiveOperationException, DataSetException, IOException;

    protected void analyzeType(Type type) {
        if (type instanceof Class) {
            this.actualClass = Class.class.cast(type);
            if (this.actualClass == List.class) {
                // 型パラメータが指定されていないjava.util.Listはエラー
                throw new IllegalArgumentException("No type parameter is specified for the java.util.List.");
            } else if (this.actualClass.isArray()) {
                this.isArray = true;
                this.actualClass = this.actualClass.getComponentType();
            }
        } else if (type instanceof ParameterizedType) {
            this.actualClass = Class.class.cast(ParameterizedType.class.cast(type).getRawType());
            if (this.actualClass == List.class) {
                this.isList = true;
                Type[] argTypes = ParameterizedType.class.cast(type).getActualTypeArguments();
                if (argTypes[0] instanceof Class) {
                    this.actualClass = Class.class.cast(argTypes[0]);
                } else if (argTypes[0] instanceof ParameterizedType) {
                    this.actualClass = Class.class.cast(ParameterizedType.class.cast(argTypes[0]).getRawType());
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
    }

    protected Object toBean(ITable table) throws ReflectiveOperationException, DataSetException {
        Object bean;
        if (this.isArray) {
            bean = Array.newInstance(this.actualClass, table.getRowCount());
            for (int i = 0; i < table.getRowCount(); i++) {
                Array.set(bean, i, this.toSingleBean(table, i, this.actualClass));
            }
        } else if (this.isList) {
            List<Object> beanList = new ArrayList<>();
            for (int i = 0; i < table.getRowCount(); i++) {
                beanList.add(this.toSingleBean(table, i, this.actualClass));
            }
            bean = beanList;
        } else {
            bean = this.toSingleBean(table, 0, this.actualClass);
        }

        return bean;
    }

    private Object toSingleBean(ITable table, int row, Class<?> clazz)
            throws ReflectiveOperationException, DataSetException {
        // Beanのインスタンスを生成
        Object bean = clazz.getDeclaredConstructor().newInstance();

        for (Column column : table.getTableMetaData().getColumns()) {
            Field field = Arrays.stream(FieldUtils.getAllFields(clazz))
                    .filter(f -> f.getName().equals(column.getColumnName())).findFirst().orElse(null);

            if (field == null) {
                continue;
            }

            Type type = field.getGenericType();
            if (type instanceof ParameterizedType) {
                type = ParameterizedType.class.cast(type).getRawType();
            } else if (type instanceof TypeVariable<?> || type instanceof GenericArrayType
                    || type instanceof WildcardType) {
                throw new IllegalArgumentException(
                        "Generic types and types with wildcard specifications are not supported. : "
                                + field.getGenericType().getTypeName() + " " + field.getName());
            }

            if (!BeanFileAdapter.SUPPORT_TYPE.contains(type)) {
                // フィールドの型がサポートしていない型の場合、例外をスロー
                throw new IllegalArgumentException(
                        "Type not supported. : " + Class.class.cast(type).getName() + " " + field.getName());
            }

            Object columnValue = table.getValue(row, column.getColumnName());

            Object value;
            if (type == java.util.Date.class) {
                value = ConvertUtils.convert(columnValue, java.sql.Timestamp.class);
                value = new java.util.Date(java.sql.Timestamp.class.cast(value).getTime());
            } else if (type == Calendar.class) {
                value = ConvertUtils.convert(columnValue, java.sql.Timestamp.class);
                value = DateUtils.toCalendar(new java.util.Date(java.sql.Timestamp.class.cast(value).getTime()));
            } else if (type == LocalDate.class) {
                value = ConvertUtils.convert(columnValue, java.sql.Date.class);
                value = java.sql.Date.class.cast(value).toLocalDate();
            } else if (type == LocalTime.class) {
                // java.sql.Time経由だと秒未満の値が失われるので、java.sql.Timestamp経由で変換
                if (columnValue instanceof String) {
                    columnValue = "1970-1-1 ".concat(String.class.cast(columnValue));
                }
                value = ConvertUtils.convert(columnValue, java.sql.Timestamp.class);
                value = java.sql.Timestamp.class.cast(value).toLocalDateTime().toLocalTime();
            } else if (type == LocalDateTime.class) {
                value = ConvertUtils.convert(columnValue, java.sql.Timestamp.class);
                value = java.sql.Timestamp.class.cast(value).toLocalDateTime();
            } else if (type == Instant.class) {
                value = ConvertUtils.convert(columnValue, java.sql.Timestamp.class);
                value = java.sql.Timestamp.class.cast(value).toInstant();
            } else {
                value = ConvertUtils.convert(columnValue, Class.class.cast(type));
            }

            if (type == java.sql.Date.class) {
                // java.sql.Dateの場合、時間部分は0クリア (Excelの日時形式から変換された場合にクリアされていないため)
                java.util.Date adjustDate = java.sql.Date.class.cast(value);
                adjustDate = DateUtils.truncate(adjustDate, Calendar.DAY_OF_MONTH);
                value = new java.sql.Date(adjustDate.getTime());
            } else if (type == java.sql.Time.class) {
                // java.sql.Timeの場合、日付部分は1970-1-1、ミリ秒部分は0でクリア (Excelの日時形式から変換された場合にクリアされていないため)
                java.util.Date adjustDate = java.sql.Time.class.cast(value);
                adjustDate = DateUtils.setYears(adjustDate, 1970);
                adjustDate = DateUtils.setMonths(adjustDate, 0);
                adjustDate = DateUtils.setDays(adjustDate, 1);
                adjustDate = DateUtils.setMilliseconds(adjustDate, 0);
                value = new java.sql.Time(adjustDate.getTime());
            }

            try {
                // Setterメソッドがある場合、Setterメソッド経由で値を設定
                Method setterMethod = new PropertyDescriptor(field.getName(), clazz).getWriteMethod();
                setterMethod.invoke(bean, value);
            } catch (IntrospectionException | IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException | NullPointerException e) {
                // Setterメソッドがない、Setterメソッドにアクセスできない場合、フィールドに直接値を設定
                FieldUtils.writeField(field, bean, value, true);
            }
        }

        return bean;
    }
}
