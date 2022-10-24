package org.penguinframework.test.meta;

import java.nio.charset.Charset;

import org.penguinframework.test.bean.annotation.BeanCsvMeta;
import org.penguinframework.test.database.annotation.TableCsvMeta;
import org.penguinframework.test.type.CsvFormatType;

public class CsvMeta extends Meta {

    /** Default CSV file character set name. */
    public static final String DEFAULT_ENCODING = "utf-8";

    /** Default CSV file character set. */
    public static final Charset DEFAULT_CHARSET = Charset.forName(CsvMeta.DEFAULT_ENCODING);

    /** Default CSV file format. */
    public static final CsvFormatType DEFAULT_FORMAT = CsvFormatType.DEFAULT;

    /** Default string representing null. */
    public static final String DEFAULT_NULL_STRING = "null";

    private Charset encoding = Charset.forName(CsvMeta.DEFAULT_ENCODING);
    private CsvFormatType format = CsvMeta.DEFAULT_FORMAT;
    private String nullString = CsvMeta.DEFAULT_NULL_STRING;
    private String[] ignoreCols = new String[] {};

    protected CsvMeta() {
        super();
    }

    public static CsvMeta of(TableCsvMeta annotation) {
        return new CsvMeta().encoding(annotation.encoding()).format(annotation.format())
                .nullString(annotation.nullString()).ignoreCols(annotation.ignoreCols());
    }

    public static CsvMeta of(BeanCsvMeta annotation) {
        return new CsvMeta().encoding(annotation.encoding()).format(annotation.format())
                .nullString(annotation.nullString());
    }

    public CsvMeta encoding(Charset encoding) {
        this.encoding = encoding;
        return this;
    }

    public CsvMeta encoding(String encoding) {
        this.encoding = Charset.forName(encoding);
        return this;
    }

    public Charset encoding() {
        return this.encoding;
    }

    public CsvMeta format(CsvFormatType format) {
        this.format = format;
        return this;
    }

    public CsvFormatType format() {
        return this.format;
    }

    public CsvMeta nullString(String nullString) {
        this.nullString = nullString;
        return this;
    }

    public String nullString() {
        return this.nullString;
    }

    public CsvMeta ignoreCols(String[] ignoreCols) {
        this.ignoreCols = ignoreCols;
        return this;
    }

    public String[] ignoreCols() {
        return this.ignoreCols;
    }
}
