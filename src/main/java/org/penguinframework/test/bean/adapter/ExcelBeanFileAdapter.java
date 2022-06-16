package org.penguinframework.test.bean.adapter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.Collections;

import org.apache.commons.lang3.StringUtils;
import org.dbunit.dataset.DataSetException;
import org.penguinframework.test.dataset.excel.ExcelDataSet;

public class ExcelBeanFileAdapter extends BeanFileAdapter {
    private final String sheet;

    public ExcelBeanFileAdapter(String sheet) {
        super();
        this.sheet = sheet;
    }

    @Override
    public Object load(URL url, Type type) throws ReflectiveOperationException, DataSetException, IOException {
        this.analyzeType(type);

        String sheetName = StringUtils.firstNonEmpty(this.sheet, this.actualClass.getSimpleName());
        return this.toBean(new ExcelDataSet(url, Collections.emptyMap()).getTable(sheetName));
    }
}
