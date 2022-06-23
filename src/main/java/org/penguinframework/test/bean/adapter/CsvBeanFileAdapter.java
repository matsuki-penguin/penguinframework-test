package org.penguinframework.test.bean.adapter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;

import org.dbunit.dataset.DataSetException;
import org.penguinframework.test.dataset.csv.CsvDataSet;
import org.penguinframework.test.meta.CsvMeta;

public class CsvBeanFileAdapter extends BeanFileAdapter {
    private final CsvMeta meta;

    public CsvBeanFileAdapter(CsvMeta meta) {
        super();
        this.meta = meta;
    }

    @Override
    public Object load(URL url, Type type) throws ReflectiveOperationException, DataSetException, IOException {
        this.analyzeType(type);

        return this.toBean(new CsvDataSet(url, this.actualClass.getSimpleName(), this.meta)
                .getTable(this.actualClass.getSimpleName()));
    }
}
