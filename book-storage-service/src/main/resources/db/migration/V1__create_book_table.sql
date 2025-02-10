CREATE TABLE Book
(
    id          int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    ISBN        varchar(17) UNIQUE NOT NULL,
    name        varchar(50)        NOT NULL,
    genre       varchar(50)        NOT NULL,
    description varchar(200)       NOT NULL,
    author      varchar(50)        NOT NULL,
    deleted     bool               NOT NULL
);
