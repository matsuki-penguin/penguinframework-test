drop table if exists profile;

create table profile (
    id bigserial,
    name varchar(255) not null,
    birthday date,
    primary key (id)
);
