drop table if exists profile;

create table profile (
    id bigserial,
    name varchar(255) not null,
    birthday date,
    primary key (id)
);

drop table if exists basic_type;

create table basic_type (
    integer_type integer,         -- Mapped to java.lang.Integer
    long_type bigint,             -- Mapped to java.lang.Long
    boolean_type bool,         -- Mapped to java.lang.Boolean
    float_type real,              -- Mapped to java.lang.Float
    double_type double precision, -- Mapped to java.lang.Double
    biginteger_type numeric(30),      -- Mapped to java.math.BigInteger
    bigdecimal_type numeric(30, 16),     -- Mapped to java.math.BigDecimal
    string_type varchar,          -- Mapped to java.lang.String
    byte_array_type bytea,         -- Mapped to byte[]
    primary key (integer_type)
);

drop table if exists datetime_type;

create table datetime_type (
    integer_type integer,         -- Mapped to java.lang.Integer
    date_type date,               -- Mapped to java.sql.Date or java.time.LocalDate
    time_type time(6),            -- Mapped to java.sql.Time or java.time.LocalTime
    timestamp_type timestamp(6),  -- Mapped to java.sql.Timestamp or java.time.LocalDateTime
    primary key (integer_type)
);
