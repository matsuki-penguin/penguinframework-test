create table profile (
    id bigint not null identity,
    name varchar(255) not null,
    birthday date,
    primary key (id)
);
