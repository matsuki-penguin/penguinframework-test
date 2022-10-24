package org.penguinframework.example.application.dao.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class DateTimeTypeEntity {
    private Integer integerType;
    private LocalDate dateType;
    private LocalTime timeType;
    private LocalDateTime timestampType;

    public Integer getIntegerType() {
        return this.integerType;
    }

    public void setIntegerType(Integer integerType) {
        this.integerType = integerType;
    }

    public LocalDate getDateType() {
        return this.dateType;
    }

    public void setDateType(LocalDate dateType) {
        this.dateType = dateType;
    }

    public LocalTime getTimeType() {
        return this.timeType;
    }

    public void setTimeType(LocalTime timeType) {
        this.timeType = timeType;
    }

    public LocalDateTime getTimestampType() {
        return this.timestampType;
    }

    public void setTimestampType(LocalDateTime timestampType) {
        this.timestampType = timestampType;
    }
}
