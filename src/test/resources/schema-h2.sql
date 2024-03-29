create table profile (
    id bigint generated by default as identity,
    name varchar(255) not null,
    birthday date,
    primary key (id)
);

create table all_type (
    int_type int,
    boolean_type boolean,
    tinyint_type tinyint,
    smallint_type smallint,
    bigint_type bigint,
    identity_type identity,
    decimal_type decimal(20,15),
    double_type double,
    real_type real,
    time_type time(9),
    date_type date,
    timestamp_type timestamp(9),
    binary_type binary(3),
    other_type other,
    varchar_type varchar,
    varchar_ignorecase_type varchar_ignorecase,
    char_type char,
    blob_type blob,
    clob_type clob,
    uuid_type uuid,
    array_type boolean array,
    primary key (identity_type)
);
