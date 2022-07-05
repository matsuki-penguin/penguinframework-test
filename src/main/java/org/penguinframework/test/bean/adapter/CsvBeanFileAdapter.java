package org.penguinframework.test.bean.adapter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;

import org.dbunit.dataset.DataSetException;
import org.penguinframework.test.dataset.csv.CsvDataSet;
import org.penguinframework.test.meta.CsvMeta;
import org.penguinframework.test.support.BeanType;

public class CsvBeanFileAdapter extends BeanFileAdapter {
    private final CsvMeta meta;

    public CsvBeanFileAdapter(CsvMeta meta) {
        super();
        this.meta = meta;
    }

    @Override
    public Object load(URL url, Type type) throws ReflectiveOperationException, DataSetException, IOException {
        BeanType.Info info = BeanType.analyze(type);

        return this.toBean(new CsvDataSet(url, info.getActualClass().getSimpleName(), this.meta)
                .getTable(info.getActualClass().getSimpleName()), info);
    }
}
