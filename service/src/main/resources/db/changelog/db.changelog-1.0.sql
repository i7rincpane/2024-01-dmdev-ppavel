--liquibase formatted sql

--changeset dnsshop:1
CREATE TABLE product_type
(
    id        SERIAL PRIMARY KEY,
    parent_id INT REFERENCES product_type (id),
    name      VARCHAR(255) NOT NULL UNIQUE
);

--changeset dnsshop:2
CREATE TABLE users
(
    id         BIGSERIAL PRIMARY KEY,
    email      VARCHAR(255) NOT NULL UNIQUE,
    password   VARCHAR(255) NOT NULL,
    name       VARCHAR(255),
    patronimic VARCHAR(255),
    surname    VARCHAR(255),
    telephone  VARCHAR(255)
);

--changeset dnsshop:3
CREATE TABLE product
(
    id              BIGSERIAL PRIMARY KEY,
    code            INT                              NOT NULL UNIQUE,
    model           VARCHAR(255),
    price           NUMERIC(19, 2)                   NOT NULL,
    producer        VARCHAR(255),
    count           INT                              NOT NULL,
    product_type_id INT REFERENCES product_type (id) NOT NULL
);

--changeset dnsshop:4
CREATE TABLE property_info
(
    id    SERIAL PRIMARY KEY,
    name  VARCHAR(255) NOT NULL,
    unit  VARCHAR(255),
    dtype VARCHAR(31)  not null
);

--changeset dnsshop:5
CREATE TABLE property
(
    id               BIGSERIAL PRIMARY KEY,
    property_info_id INT REFERENCES property_info (id) NOT NULL,
    string_value     VARCHAR(255),
    integer_value    INT,
    double_value     NUMERIC(19, 2),
    date_value       TIMESTAMP,
    is_value         BOOLEAN
);

--changeset dnsshop:6
CREATE TABLE product_property
(
    id          BIGSERIAL PRIMARY KEY,
    product_id  BIGINT REFERENCES product (id)  NOT NULL,
    property_id BIGINT REFERENCES property (id) NOT NULL,
    UNIQUE (product_id, property_id)
);

--changeset dnsshop:7
create table orders
(
    id           BIGSERIAL PRIMARY KEY,
    created_at   TIMESTAMP                    NOT NULL,
    updated_at   TIMESTAMP                    NOT NULL,
    sum          NUMERIC(19, 2)               NOT NULL,
    user_id      BIGINT REFERENCES users (id) NOT NULL,
    order_status VARCHAR(128)                 NOT NULL
);

--changeset dnsshop:8
create table product_order
(
    id         BIGSERIAL PRIMARY KEY,
    count      INT                            NOT NULL,
    order_id   BIGINT REFERENCES orders (id)  NOT NULL,
    product_id BIGINT REFERENCES product (id) NOT NULL,
    UNIQUE (product_id, order_id)
);