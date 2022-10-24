drop table if exists profile;

create table profile (
    id bigint auto_increment,
    name varchar(255) not null,
    birthday date,
    primary key (id)
);

drop table if exists basic_type;

create table basic_type (
    integer_type int,         -- Mapped to java.lang.Integer
    long_type bigint,             -- Mapped to java.lang.Long
    boolean_type boolean,         -- Mapped to java.lang.Boolean
    float_type float(9, 8),              -- Mapped to java.lang.Float
    double_type double, -- Mapped to java.lang.Double
    biginteger_type numeric(31),      -- Mapped to java.math.BigDecimal (not java.math.BigInteger)
    bigdecimal_type numeric(31, 16),     -- Mapped to java.math.BigDecimal
    string_type varchar(2000),          -- Mapped to java.lang.String
    byte_array_type blob,         -- Mapped to byte[]
    primary key (integer_type)
);

drop table if exists datetime_type;

create table datetime_type (
    integer_type int,         -- Mapped to java.lang.Integer
    date_type date,               -- Mapped to java.sql.Date or java.time.LocalDate
    time_type time(6),            -- Mapped to java.sql.Time or java.time.LocalTime (秒未満の精度はマイクロ秒)
    timestamp_type timestamp(6),  -- Mapped to java.sql.Timestamp or java.time.LocalDateTime (秒未満の精度はマイクロ秒)
    primary key (integer_type)
);
