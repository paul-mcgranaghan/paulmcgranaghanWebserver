DROP TABLE IF EXISTS "User";

DROP SEQUENCE IF EXISTS User_ID_Seq;

CREATE TABLE "User"
(
    name         varchar(255),
    age          integer,
    user_id      varchar(255),
    last_updated timestamp,
    email        varchar(255)
);

CREATE SEQUENCE User_ID_Seq
START WITH 4
INCREMENT BY 1;