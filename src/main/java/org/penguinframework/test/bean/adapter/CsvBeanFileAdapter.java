package org.penguinframework.test.bean.adapter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.nio.charset.Charset;

import org.apache.commons.csv.CSVFormat;
import org.dbunit.dataset.DataSetException;
import org.penguinframework.test.dataset.csv.CsvDataSet;

public class CsvBeanFileAdapter extends BeanFileAdapter {
    private final Charset charset;
    private final CSVFormat csvFormat;

    public CsvBeanFileAdapter(Charset charset, CSVFormat csvFormat) {
        super();
        this.charset = charset;
        this.csvFormat = csvFormat;
    }

    @Override
    public Object load(URL url, Type type) throws ReflectiveOperationException, DataSetException, IOException {
        this.analyzeType(type);

        return this.toBean(new CsvDataSet(url, this.actualClass.getSimpleName(), this.charset, this.csvFormat)
                .getTable(this.actualClass.getSimpleName()));
    }
}
