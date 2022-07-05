package org.penguinframework.example.bean;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.UUID;

public class AllTypeBean {
    private boolean primitiveBoolean;
    private Boolean wrapperBoolean;
    private byte primitiveByte;
    private Byte wrapperByte;
    private char primitiveChar;
    private Character wrapperCharacter;
    private short primitiveShort;
    private Short wrapperShort;
    private int primitiveInt;
    private Integer wrapperInteger;
    private long primitiveLong;
    private Long wrapperLong;

    private float primitiveFloat;
    private Float wrapperFloat;
    private double primitiveDouble;
    private Double wrapperDouble;

    private BigInteger bigInteger;
    private BigDecimal bigDecimal;

    private byte[] primitiveByteArray;
    private Byte[] wrapperByteArray;
    private char[] primitiveCharArray;
    private Character[] wrapperCharacterArray;

    private String string;

    private java.sql.Date sqlDate;
    private LocalDate localDate;
    private java.sql.Time sqlTime;
    private LocalTime localTime;
    private java.sql.Timestamp sqlTimestamp;
    private LocalDateTime localDateTime;
    private java.util.Date utilDate;
    private Calendar calendar;
    private Instant instant;

    private UUID uuid;

    public boolean isPrimitiveBoolean() {
        return this.primitiveBoolean;
    }

    public void setPrimitiveBoolean(boolean primitiveBoolean) {
        this.primitiveBoolean = primitiveBoolean;
    }

    public Boolean getWrapperBoolean() {
        return this.wrapperBoolean;
    }

    public void setWrapperBoolean(Boolean wrapperBoolean) {
        this.wrapperBoolean = wrapperBoolean;
    }

    public byte getPrimitiveByte() {
        return this.primitiveByte;
    }

    public void setPrimitiveByte(byte primitiveByte) {
        this.primitiveByte = primitiveByte;
    }

    public Byte getWrapperByte() {
        return this.wrapperByte;
    }

    public void setWrapperByte(Byte wrapperByte) {
        this.wrapperByte = wrapperByte;
    }

    public char getPrimitiveChar() {
        return this.primitiveChar;
    }

    public void setPrimitiveChar(char primitiveChar) {
        this.primitiveChar = primitiveChar;
    }

    public Character getWrapperCharacter() {
        return this.wrapperCharacter;
    }

    public void setWrapperCharacter(Character wrapperCharacter) {
        this.wrapperCharacter = wrapperCharacter;
    }

    public short getPrimitiveShort() {
        return this.primitiveShort;
    }

    public void setPrimitiveShort(short primitiveShort) {
        this.primitiveShort = primitiveShort;
    }

    public Short getWrapperShort() {
        return this.wrapperShort;
    }

    public void setWrapperShort(Short wrapperShort) {
        this.wrapperShort = wrapperShort;
    }

    public int getPrimitiveInt() {
        return this.primitiveInt;
    }

    public void setPrimitiveInt(int primitiveInt) {
        this.primitiveInt = primitiveInt;
    }

    public Integer getWrapperInteger() {
        return this.wrapperInteger;
    }

    public void setWrapperInteger(Integer wrapperInteger) {
        this.wrapperInteger = wrapperInteger;
    }

    public long getPrimitiveLong() {
        return this.primitiveLong;
    }

    public void setPrimitiveLong(long primitiveLong) {
        this.primitiveLong = primitiveLong;
    }

    public Long getWrapperLong() {
        return this.wrapperLong;
    }

    public void setWrapperLong(Long wrapperLong) {
        this.wrapperLong = wrapperLong;
    }

    public float getPrimitiveFloat() {
        return this.primitiveFloat;
    }

    public void setPrimitiveFloat(float primitiveFloat) {
        this.primitiveFloat = primitiveFloat;
    }

    public Float getWrapperFloat() {
        return this.wrapperFloat;
    }

    public void setWrapperFloat(Float wrapperFloat) {
        this.wrapperFloat = wrapperFloat;
    }

    public double getPrimitiveDouble() {
        return this.primitiveDouble;
    }

    public void setPrimitiveDouble(double primitiveDouble) {
        this.primitiveDouble = primitiveDouble;
    }

    public Double getWrapperDouble() {
        return this.wrapperDouble;
    }

    public void setWrapperDouble(Double wrapperDouble) {
        this.wrapperDouble = wrapperDouble;
    }

    public BigInteger getBigInteger() {
        return this.bigInteger;
    }

    public void setBigInteger(BigInteger bigInteger) {
        this.bigInteger = bigInteger;
    }

    public BigDecimal getBigDecimal() {
        return this.bigDecimal;
    }

    public void setBigDecimal(BigDecimal bigDecimal) {
        this.bigDecimal = bigDecimal;
    }

    public byte[] getPrimitiveByteArray() {
        return this.primitiveByteArray;
    }

    public void setPrimitiveByteArray(byte[] primitiveByteArray) {
        this.primitiveByteArray = primitiveByteArray;
    }

    public Byte[] getWrapperByteArray() {
        return this.wrapperByteArray;
    }

    public void setWrapperByteArray(Byte[] wrapperByteArray) {
        this.wrapperByteArray = wrapperByteArray;
    }

    public char[] getPrimitiveCharArray() {
        return this.primitiveCharArray;
    }

    public void setPrimitiveCharArray(char[] primitiveCharArray) {
        this.primitiveCharArray = primitiveCharArray;
    }

    public Character[] getWrapperCharacterArray() {
        return this.wrapperCharacterArray;
    }

    public void setWrapperCharacterArray(Character[] wrapperCharacterArray) {
        this.wrapperCharacterArray = wrapperCharacterArray;
    }

    public String getString() {
        return this.string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public java.sql.Date getSqlDate() {
        return this.sqlDate;
    }

    public void setSqlDate(java.sql.Date sqlDate) {
        this.sqlDate = sqlDate;
    }

    public LocalDate getLocalDate() {
        return this.localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public java.sql.Time getSqlTime() {
        return this.sqlTime;
    }

    public void setSqlTime(java.sql.Time sqlTime) {
        this.sqlTime = sqlTime;
    }

    public LocalTime getLocalTime() {
        return this.localTime;
    }

    public void setLocalTime(LocalTime localTime) {
        this.localTime = localTime;
    }

    public java.sql.Timestamp getSqlTimestamp() {
        return this.sqlTimestamp;
    }

    public void setSqlTimestamp(java.sql.Timestamp sqlTimestamp) {
        this.sqlTimestamp = sqlTimestamp;
    }

    public LocalDateTime getLocalDateTime() {
        return this.localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public java.util.Date getUtilDate() {
        return this.utilDate;
    }

    public void setUtilDate(java.util.Date utilDate) {
        this.utilDate = utilDate;
    }

    public Calendar getCalendar() {
        return this.calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public Instant getInstant() {
        return this.instant;
    }

    public void setInstant(Instant instant) {
        this.instant = instant;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
