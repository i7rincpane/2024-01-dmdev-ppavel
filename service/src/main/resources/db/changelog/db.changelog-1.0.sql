--liquibase formatted sql

--changeset dnsshop:1
CREATE TABLE producer
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

--changeset dnsshop:2
CREATE TABLE category
(
    id        SERIAL PRIMARY KEY,
    parent_id INT REFERENCES category (id),
    name      VARCHAR(255) NOT NULL UNIQUE
);

--changeset dnsshop:3
CREATE TABLE users
(
    id         BIGSERIAL PRIMARY KEY,
    email      VARCHAR(255) NOT NULL UNIQUE,
    password   VARCHAR(255) NOT NULL,
    name       VARCHAR(255),
    patronimic VARCHAR(255),
    surname    VARCHAR(255),
    role       VARCHAR(32),
    telephone  VARCHAR(255),
    birth_date DATE
);

--changeset dnsshop:4
CREATE TABLE product
(
    id          BIGSERIAL PRIMARY KEY,
    code        INT                          NOT NULL UNIQUE,
    model       VARCHAR(255),
    price       NUMERIC(19, 2)               NOT NULL,
    producer_id INT REFERENCES producer (id) NOT NULL,
    count       INT                          NOT NULL,
    category_id INT REFERENCES category (id) NOT NULL
);

--changeset dnsshop:5
CREATE TABLE property
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(255)                 NOT NULL,
    category_id INT REFERENCES category (id) NOT NULL,
    unit        VARCHAR(255),
    dtype       VARCHAR(31)                  NOT NULL
);

--changeset dnsshop:6
CREATE TABLE property_value
(
    id            BIGSERIAL PRIMARY KEY,
    property_id   INT REFERENCES property (id) NOT NULL,
    text_value    VARCHAR(255),
    number_value  INT,
    float_value   NUMERIC(19, 2),
    date_value    TIMESTAMP,
    boolean_value BOOLEAN
);

--changeset dnsshop:7
CREATE TABLE product_property_value
(
    id                BIGSERIAL PRIMARY KEY,
    product_id        BIGINT REFERENCES product (id)        NOT NULL,
    property_value_id BIGINT REFERENCES property_value (id) NOT NULL,
    UNIQUE (product_id, property_value_id)
);

--changeset dnsshop:8
create table orders
(
    id           BIGSERIAL PRIMARY KEY,
    created_at   TIMESTAMP                    NOT NULL,
    updated_at   TIMESTAMP                    NOT NULL,
    sum          NUMERIC(19, 2)               NOT NULL,
    user_id      BIGINT REFERENCES users (id) NOT NULL,
    order_status VARCHAR(128)                 NOT NULL
);

--changeset dnsshop:9
create table product_order
(
    id         BIGSERIAL PRIMARY KEY,
    count      INT                            NOT NULL,
    order_id   BIGINT REFERENCES orders (id)  NOT NULL,
    product_id BIGINT REFERENCES product (id) NOT NULL,
    UNIQUE (product_id, order_id)
);