DROP TABLE IF EXISTS "User";

create table "User"
(
    name         varchar(255),
    age          integer,
    user_id      varchar(255),
    last_updated timestamp,
    email        varchar(255)
);