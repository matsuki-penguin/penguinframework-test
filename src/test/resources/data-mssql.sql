truncate table profile;

set identity_insert profile on;

insert into profile (
    id,
    name,
    birthday
) values (
    1,
    'init',
    '1900-1-1'
);

set identity_insert profile off;
