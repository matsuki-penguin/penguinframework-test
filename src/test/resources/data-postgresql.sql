insert into profile (
    id,
    name,
    birthday
) values (
    1,
    'init',
    '1900-1-1'
);

insert into basic_type (
    integer_type,
    long_type,
    boolean_type,
    float_type,
    double_type,
    biginteger_type,
    bigdecimal_type,
    string_type,
    byte_array_type
) values (
    100,
    1234567890123,
    true,
    1.7320508,
    1.4142110356,
    987654321987654321,
    3.14159265358979,
    'penguin!',
    decode('616263', 'hex')
);

insert into datetime_type (
    integer_type,
    date_type,
    time_type,
    timestamp_type
) values (
    100,
    '2020-12-24',
    '1:23:4.567890123',
    '2020-12-24 1:23:4.567890123'
);
