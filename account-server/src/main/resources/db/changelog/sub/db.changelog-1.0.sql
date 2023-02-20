--liquibase formatted sql

--changeset social_network:1
create table roles
(
    id   bigserial primary key,
    name varchar
);

--changeset social_network:2
insert into roles (name)
values ('User'),
       ('Admin');

--changeset social_network:3
create table users
(
    id         bigserial primary key,
    first_name varchar(30) not null,
    last_name  varchar(30) not null,
    birthday   date,
    bio        varchar(1000),
    country    varchar(30),
    city       varchar(40),
    phone      varchar(20) unique check ( phone != ''
        ),
    role_id    bigint,
    foreign key (role_id) references roles (id)
);

--changeset social_network:4
create table dialogs
(
    id      bigserial primary key,
    name    varchar(100),
    user_id bigint,
    foreign key (user_id) references users (id)
);

--changeset social_network:5
create table messages
(
    id            bigserial primary key,
    dialog_id   bigint,
    foreign key (dialog_id) references dialogs (id),
    user_id       bigint,
    foreign key (user_id) references users (id),
    text_message  varchar(50000) not null check ( text_message != ''
        ),
    time_creation timestamp      not null
);

--changeset social_network:6
create table posts
(
    id            bigserial primary key,
    user_id       bigint,
    foreign key (user_id) references users (id),
    text          varchar(50000) not null check ( text != ''
        ),
    time_creation timestamp      not null
);

--changeset social_network:7
create table search_requests
(
    id            bigserial primary key,
    user_id       bigint,
    foreign key (user_id) references users (id),
    text_request  varchar(70) not null,
    time_creation timestamp
);

--changeset social_network:8
create table authorization_data
(
    email    varchar(100) primary key,
    password varchar(500),
    user_id  bigint,
    foreign key (user_id) references users (id)
);

--changeset social_network:9
create table permissions
(
    id      bigserial primary key,
    name    varchar(180),
    role_id bigint,
    foreign key (role_id) references roles (id)
);
