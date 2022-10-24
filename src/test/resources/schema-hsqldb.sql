create table profile (
    id bigint not null identity,
    name varchar(255) not null,
    birthday date,
    primary key (id)
);

create table basic_type (
    integer_type int,         -- Mapped to java.lang.Integer
    long_type bigint,             -- Mapped to java.lang.Long
    boolean_type boolean,         -- Mapped to java.lang.Boolean
    float_type real,              -- Mapped to java.lang.Double
    double_type float, -- Mapped to java.lang.Double
    biginteger_type decimal(31),      -- Mapped to java.math.BigInteger
    bigdecimal_type decimal(31, 16),     -- Mapped to java.math.BigDecimal
    string_type varchar(2000),          -- Mapped to java.lang.String
    byte_array_type varbinary(2000),         -- Mapped to byte[]
    primary key (integer_type)
);

create table datetime_type (
    integer_type int,         -- Mapped to java.lang.Integer
    date_type date,               -- Mapped to java.sql.Date or java.time.LocalDate
    time_type time(9),            -- Mapped to java.sql.Time or java.time.LocalTime
    timestamp_type timestamp(9),  -- Mapped to java.sql.Timestamp or java.time.LocalDateTime
    primary key (integer_type)
);
