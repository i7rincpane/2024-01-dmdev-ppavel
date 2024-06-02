--liquibase formatted sql

--changeset dnsshop:1
CREATE TABLE producer
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

--changeset dnsshop:2
CREATE TABLE category
(
    id        BIGSERIAL PRIMARY KEY,
    parent_id BIGINT REFERENCES category (id),
    name      VARCHAR(255) NOT NULL UNIQUE,
    image     VARCHAR(64)
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
    count       INT DEFAULT 0,
    category_id BIGINT REFERENCES category (id) NOT NULL,
    image     VARCHAR(64)
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
create table basket
(
    id      BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users (id) NOT NULL,
    sum     NUMERIC(19, 2) DEFAULT 0,
    count   INT            DEFAULT 0
);

--changeset dnsshop:9
create table basket_product
(
    id          BIGSERIAL PRIMARY KEY,
    count       INT            DEFAULT 0,
    basket_id   BIGINT REFERENCES basket (id)  NOT NULL,
    product_id  BIGINT REFERENCES product (id) NOT NULL,
    sum         NUMERIC(19, 2) DEFAULT 0,
    is_selected BOOLEAN        DEFAULT true,
    UNIQUE (basket_id, product_id)
);

--changeset dnsshop:10
create table orders
(
    id           BIGSERIAL PRIMARY KEY,
    created_at   TIMESTAMP                     NOT NULL,
    updated_at   TIMESTAMP,
    count        INT            DEFAULT 0,
    sum          NUMERIC(19, 2) DEFAULT 0,
    user_id      BIGINT REFERENCES users (id)  NOT NULL,
    basket_id    BIGINT REFERENCES basket (id) NOT NULL,
    order_status VARCHAR(128)                  NOT NULL
);

--changeset dnsshop:11
create table order_product
(
    id         BIGSERIAL PRIMARY KEY,
    count      INT            DEFAULT 0,
    order_id   BIGINT REFERENCES orders (id)  NOT NULL,
    product_id BIGINT REFERENCES product (id) NOT NULL,
    sum        NUMERIC(19, 2) DEFAULT 0,
    UNIQUE (order_id, product_id)
);