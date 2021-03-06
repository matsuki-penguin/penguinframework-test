package org.penguinframework.test.bean.adapter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;

import org.apache.commons.lang3.StringUtils;
import org.dbunit.dataset.DataSetException;
import org.penguinframework.test.dataset.excel.ExcelDataSet;
import org.penguinframework.test.meta.ExcelMeta;
import org.penguinframework.test.support.BeanType;

public class ExcelBeanFileAdapter extends BeanFileAdapter {
    private final String sheet;
    private final ExcelMeta meta;

    public ExcelBeanFileAdapter(String sheet, ExcelMeta meta) {
        super();
        this.sheet = sheet;
        this.meta = meta;
    }

    @Override
    public Object load(URL url, Type type) throws ReflectiveOperationException, DataSetException, IOException {
        BeanType.Info info = BeanType.analyze(type);

        String sheetName = StringUtils.firstNonEmpty(this.sheet, info.getActualClass().getSimpleName());
        return this.toBean(new ExcelDataSet(url, this.meta).getTable(sheetName), info);
    }
}
