create table profile (
    id bigint IDENTITY(1, 1) NOT null,
    name varchar(255) not null,
    birthday date,
    primary key (id)
);
