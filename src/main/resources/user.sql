drop table user;
create table user
(
    id     int auto_increment
        primary key,
    age    int          null,
    name   varchar(100) null,
    school varchar(100) null,
    score  int          null,
    deleted int          default 0,
    gmt_create timestamp          null,
    gmt_modified timestamp          null
);
