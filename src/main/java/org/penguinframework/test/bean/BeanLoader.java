package org.penguinframework.test.bean;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.dbunit.dataset.DataSetException;
import org.junit.platform.commons.util.AnnotationUtils;
import org.penguinframework.test.bean.adapter.CsvBeanFileAdapter;
import org.penguinframework.test.bean.adapter.ExcelBeanFileAdapter;
import org.penguinframework.test.bean.adapter.BeanFileAdapter;
import org.penguinframework.test.bean.annotation.BeanValueSource;
import org.penguinframework.test.type.FileType;

/**
 * Load an instance of bean.
 */
public class BeanLoader {
    /**
     * Private constructor to suppress instantiation.
     */
    private BeanLoader() {
    }

    /**
     * Initialize the field specified by the BeanValueSource annotation contained in
     * the target instance.
     *
     * @param targetInstance Target instance.
     * @throws ReflectiveOperationException
     * @throws DataSetException
     * @throws IOException
     */
    public static void initFields(Object targetInstance)
            throws ReflectiveOperationException, DataSetException, IOException {
        Class<?> targetClass = targetInstance.getClass();
        List<Field> fields = AnnotationUtils.findAnnotatedFields(targetClass, BeanValueSource.class, f -> true);
        for (Field field : fields) {
            Object value = BeanLoader.load(field);
            try {
                // Setterメソッドがある場合、Setterメソッド経由で値を設定
                Method setterMethod = new PropertyDescriptor(field.getName(), targetClass).getWriteMethod();
                setterMethod.invoke(targetInstance, value);
            } catch (IntrospectionException | IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException | NullPointerException e) {
                // Setterメソッドがない、Setterメソッドにアクセスできない場合、フィールドに直接値を設定
                FieldUtils.writeField(field, targetInstance, value, true);
            }
        }
    }

    /**
     * Creates an instance for the instance field with a BeanValueSource annotation.
     *
     * @param targetField Target field.
     * @return Generated instances.
     * @throws ReflectiveOperationException
     * @throws DataSetException
     * @throws IOException
     */
    public static Object load(Field targetField) throws ReflectiveOperationException, DataSetException, IOException {
        Class<?> targetClass = targetField.getDeclaringClass();

        // 引数に指定されているBeanValueSourceアノテーションを取得
        BeanValueSource beanValueSource = targetField.getAnnotation(BeanValueSource.class);

        return BeanLoader.createInstanceFromAnnotation(beanValueSource, targetClass, targetField.getGenericType());
    }

    /**
     * Creates an instance for a method parameter with a BeanValueSource annotation.
     *
     * @param targetParameter Target parameter.
     * @return Generated instances.
     * @throws ReflectiveOperationException
     * @throws DataSetException
     * @throws IOException
     */
    public static Object load(Parameter targetParameter)
            throws ReflectiveOperationException, DataSetException, IOException {
        Class<?> targetClass = targetParameter.getDeclaringExecutable().getDeclaringClass();

        // 引数に指定されているBeanValueSourceアノテーションを取得
        BeanValueSource beanValueSource = targetParameter.getAnnotation(BeanValueSource.class);

        return BeanLoader.createInstanceFromAnnotation(beanValueSource, targetClass,
                targetParameter.getParameterizedType());
    }

    /**
     * Create an instance according to the type of the variable from the attribute
     * value of the BeanValueSource annotation.
     *
     * @param beanValueSource A BeanValueSource annotation with information on the
     *                        instance to be created.
     * @param targetClass     The class that contains the variable with the
     *                        BeanValueSource annotation.
     * @param targetType      The type of the instance to be created.
     * @return Generated instances.
     * @throws ReflectiveOperationException
     * @throws DataSetException
     * @throws IOException
     */
    private static Object createInstanceFromAnnotation(BeanValueSource beanValueSource, Class<?> targetClass,
            Type targetType) throws ReflectiveOperationException, DataSetException, IOException {
        String path = StringUtils.firstNonEmpty(beanValueSource.value(), beanValueSource.path());
        URL url = targetClass.getResource(path);
        if (url == null) {
            throw new FileNotFoundException("File not found. : " + path);
        }

        FileType fileType = beanValueSource.type();
        if (fileType == FileType.UNKNOWN) {
            fileType = FileType.valueOf(url);
        }

        BeanFileAdapter fileAdapter;
        switch (fileType) {
        case EXCEL:
            fileAdapter = new ExcelBeanFileAdapter(beanValueSource.excelMeta().sheet());
            break;
        case CSV:
            fileAdapter = new CsvBeanFileAdapter(Charset.forName(beanValueSource.csvMeta().encoding()),
                    beanValueSource.csvMeta().format().getCsvFormat());
            break;
        default:
            throw new IllegalArgumentException("Unknown file type. : " + path);
        }
        return fileAdapter.load(url, targetType);
    }
}
