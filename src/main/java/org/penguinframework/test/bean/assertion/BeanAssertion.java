package org.penguinframework.test.bean.assertion;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.dbunit.Assertion;
import org.dbunit.DatabaseUnitException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.penguinframework.test.dataset.bean.BeanDataSet;
import org.penguinframework.test.dataset.csv.CsvDataSet;
import org.penguinframework.test.dataset.excel.ExcelDataSet;
import org.penguinframework.test.exception.AssertRuntimeException;
import org.penguinframework.test.meta.CsvMeta;
import org.penguinframework.test.meta.ExcelMeta;
import org.penguinframework.test.meta.Meta;
import org.penguinframework.test.type.FileType;

public class BeanAssertion {

    private final Class<?> testClass;

    public BeanAssertion(Class<?> testClass) {
        super();
        this.testClass = testClass;
    }

    public void assertEquals(String expectedFilePath, Object actualBean) {
        this.assertEquals(expectedFilePath, null, actualBean);
    }

    public void assertEquals(String expectedFilePath, Meta meta, Object actualBean) {
        if (actualBean == null) {
            throw new IllegalArgumentException(
                    "Bean is null. Use org.junit.jupiter.api.Assertions.assertNull to verify.");
        }

        Class<?> beanClass = actualBean.getClass();
        if (beanClass.isArray()) {
            beanClass = beanClass.getComponentType();
        } else if (List.class.isAssignableFrom(beanClass)) {
            throw new IllegalArgumentException(
                    "Use the 'assertEquals(String, List<?>, Class<?>)' signature since the expected value is of type java.util.List.");
        }
        this.assertObjectEquals(expectedFilePath, meta, actualBean, beanClass);
    }

    public void assertEquals(String expectedFilePath, List<?> actualBeanList, Class<?> beanClass) {
        this.assertEquals(expectedFilePath, null, actualBeanList, beanClass);
    }

    public void assertEquals(String expectedFilePath, Meta meta, List<?> actualBeanList, Class<?> beanClass) {
        this.assertObjectEquals(expectedFilePath, meta, actualBeanList, beanClass);
    }

    private void assertObjectEquals(String expectedFilePath, Meta meta, Object actualBean, Class<?> beanClass) {
        if (actualBean == null) {
            throw new IllegalArgumentException(
                    "Bean is null. Use org.junit.jupiter.api.Assertions.assertNull to verify.");
        }

        URL url = this.testClass.getResource(expectedFilePath);
        FileType fileType = FileType.valueOf(url);
        String[] ignoreCols = null;

        String actualClassName = beanClass.getSimpleName();

        try {
            IDataSet expectedDataSet;
            switch (fileType) {
            case EXCEL:
                ExcelMeta excelMeta = (meta instanceof ExcelMeta) ? ExcelMeta.class.cast(meta) : Meta.excel();
                expectedDataSet = new ExcelDataSet(url, excelMeta);
                ignoreCols = excelMeta.ignoreCols().get(actualClassName);
                break;
            case CSV:
                CsvMeta csvMeta = (meta instanceof CsvMeta) ? CsvMeta.class.cast(meta) : Meta.csv();
                expectedDataSet = new CsvDataSet(url, actualClassName, csvMeta);
                ignoreCols = csvMeta.ignoreCols();
                break;
            default:
                throw new IllegalArgumentException("Unknown file type. : " + expectedFilePath);
            }
            ITable expectedTable = expectedDataSet.getTable(actualClassName);

            IDataSet dataSet = new BeanDataSet(actualBean, beanClass);
            ITable actualTable = dataSet.getTable(actualClassName);

            Assertion.assertEqualsIgnoreCols(expectedTable, actualTable,
                    ObjectUtils.defaultIfNull(ignoreCols, new String[] {}));
        } catch (IOException | DatabaseUnitException e) {
            throw new AssertRuntimeException(e);
        }
    }
}
