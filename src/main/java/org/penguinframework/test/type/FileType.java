package org.penguinframework.test.type;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;

public enum FileType {
    EXCEL, CSV, UNKNOWN;

    private static final Map<String, FileType> FILE_TYPE_MAP = new HashMap<>();

    static {
        FileType.FILE_TYPE_MAP.put("xls", FileType.EXCEL);
        FileType.FILE_TYPE_MAP.put("xlsx", FileType.EXCEL);
        FileType.FILE_TYPE_MAP.put("xlsm", FileType.EXCEL);
        FileType.FILE_TYPE_MAP.put("csv", FileType.CSV);
        FileType.FILE_TYPE_MAP.put("tsv", FileType.CSV);
    }

    public static FileType valueOf(URL url) {
        return FileType.FILE_TYPE_MAP.getOrDefault(FilenameUtils.getExtension(url.getFile()), UNKNOWN);
    }
}
