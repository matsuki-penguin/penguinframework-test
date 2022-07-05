package org.penguinframework.test.support.datatype;

import org.dbunit.dataset.datatype.DataType;

public abstract class ExtendDataType {
    public static final DataType LOCAL_DATE = new LocalDateDataType();
    public static final DataType LOCAL_TIME = new LocalTimeDataType();
    public static final DataType LOCAL_DATETIME = new LocalDateTimeDataType();
    public static final DataType UTIL_DATE = new UtilDateDataType();
    public static final DataType CALENDAR = new CalendarDataType();
    public static final DataType INSTANT = new InstantDataType();

    private static final DataType[] TYPES = { ExtendDataType.LOCAL_DATE, ExtendDataType.LOCAL_TIME,
            ExtendDataType.LOCAL_DATETIME, ExtendDataType.UTIL_DATE, ExtendDataType.CALENDAR, ExtendDataType.INSTANT };
}
