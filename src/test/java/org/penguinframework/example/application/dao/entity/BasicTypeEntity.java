package org.penguinframework.example.application.dao.entity;

import java.math.BigDecimal;
import java.math.BigInteger;

public class BasicTypeEntity {
    private Integer integerType;
    private Long longType;
    private Boolean booleanType;
    private Double floatType;
    private Double doubleType;
    private BigInteger bigintegerType;
    private BigDecimal bigdecimalType;
    private String stringType;
    private byte[] byteArrayType;

    public Integer getIntegerType() {
        return this.integerType;
    }

    public void setIntegerType(Integer integerType) {
        this.integerType = integerType;
    }

    public Long getLongType() {
        return this.longType;
    }

    public void setLongType(Long longType) {
        this.longType = longType;
    }

    public Boolean getBooleanType() {
        return this.booleanType;
    }

    public void setBooleanType(Boolean booleanType) {
        this.booleanType = booleanType;
    }

    public Double getFloatType() {
        return this.floatType;
    }

    public void setFloatType(Double floatType) {
        this.floatType = floatType;
    }

    public Double getDoubleType() {
        return this.doubleType;
    }

    public void setDoubleType(Double doubleType) {
        this.doubleType = doubleType;
    }

    public BigInteger getBigintegerType() {
        return this.bigintegerType;
    }

    public void setBigintegerType(BigInteger bigintegerType) {
        this.bigintegerType = bigintegerType;
    }

    public BigDecimal getBigdecimalType() {
        return this.bigdecimalType;
    }

    public void setBigdecimalType(BigDecimal bigdecimalType) {
        this.bigdecimalType = bigdecimalType;
    }

    public String getStringType() {
        return this.stringType;
    }

    public void setStringType(String stringType) {
        this.stringType = stringType;
    }

    public byte[] getByteArrayType() {
        return this.byteArrayType;
    }

    public void setByteArrayType(byte[] byteArrayType) {
        this.byteArrayType = byteArrayType;
    }
}
