drop table if exists profile;

create table profile (
    id bigint generated BY default as identity (start with 1 increment by 1),
    name varchar(255) not null,
    birthday date,
    primary key (id)
);
