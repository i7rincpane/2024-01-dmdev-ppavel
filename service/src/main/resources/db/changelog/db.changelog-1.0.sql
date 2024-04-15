--liquibase formatted sql

--changeset dnsshop:1
CREATE TABLE producer
(
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(255)                    NOT NULL
);

--changeset dnsshop:2
CREATE TABLE category
(
    id        BIGSERIAL PRIMARY KEY,
    parent_id BIGINT REFERENCES category (id),
    name      VARCHAR(255) NOT NULL UNIQUE
);

--changeset dnsshop:3
CREATE TABLE users
(
    id         BIGSERIAL PRIMARY KEY,
    email      VARCHAR(255) NOT NULL UNIQUE,
    password   VARCHAR(255) NOT NULL DEFAULT '{noop}123',
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
    code        INT                             NOT NULL UNIQUE,
    model       VARCHAR(255),
    price       NUMERIC(19, 2)                  NOT NULL,
    producer_id BIGINT REFERENCES producer (id) NOT NULL,
    count       INT                             NOT NULL,
    category_id BIGINT REFERENCES category (id) NOT NULL
);

--changeset dnsshop:5
CREATE TABLE property
(
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(255)                    NOT NULL,
    category_id BIGINT REFERENCES category (id) NOT NULL,
    unit        VARCHAR(255),
    dtype       VARCHAR(31)                     NOT NULL,
    UNIQUE (category_id, name)
);

--changeset dnsshop:6
CREATE TABLE string_classifier
(
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(255)                    NOT NULL,
    property_id BIGINT REFERENCES property (id) NOT NULL,
    UNIQUE (property_id, name)
);

--changeset dnsshop:7
CREATE TABLE product_property
(
    id                   BIGSERIAL PRIMARY KEY,
    product_id           BIGINT REFERENCES product (id)  NOT NULL,
    property_id          BIGINT REFERENCES property (id) NOT NULL,
    string_classifier_id BIGINT REFERENCES string_classifier (id),
    text_value           VARCHAR(255),
    number_value         INT,
    float_value          DOUBLE PRECISION,
    date_value           TIMESTAMP,
    boolean_value        BOOLEAN,

    UNIQUE (product_id, property_id)
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