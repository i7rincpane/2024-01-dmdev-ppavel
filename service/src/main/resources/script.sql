CREATE TABLE product_type
(
    id        SERIAL PRIMARY KEY,
    parent_id INT REFERENCES product_type (id),
    name      VARCHAR(255) NOT NULL UNIQUE
);

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

CREATE TABLE product
(
    id              BIGSERIAL PRIMARY KEY,
    code            INT                              NOT NULL UNIQUE,
    model           VARCHAR(255),
    name            VARCHAR(255)                     NOT NULL,
    price           NUMERIC(19, 2)                   NOT NULL,
    producer        VARCHAR(255),
    count           INT                              NOT NULL,
    product_type_id INT REFERENCES product_type (id) NOT NULL
);

CREATE TABLE property
(
    id    BIGSERIAL PRIMARY KEY,
    name  VARCHAR(255) NOT NULL,
    value VARCHAR(255) NOT NULL,
    unit  VARCHAR(255),
    count INTEGER,
    UNIQUE (name, value)
);

CREATE TABLE product_property
(
    id          BIGSERIAL PRIMARY KEY,
    product_id  BIGINT REFERENCES product (id)  NOT NULL,
    property_id BIGINT REFERENCES property (id) NOT NULL,
    UNIQUE (product_id, property_id)
);

create table orders
(
    id           BIGSERIAL PRIMARY KEY,
    created_at   TIMESTAMP                    NOT NULL,
    updated_at   TIMESTAMP                    NOT NULL,
    sum          NUMERIC(19, 2)               NOT NULL,
    user_id      BIGINT REFERENCES users (id) NOT NULL,
    order_status VARCHAR(128)                 NOT NULL
);

create table product_order
(
    id         BIGSERIAL PRIMARY KEY,
    count      INT                            NOT NULL,
    order_id   BIGINT REFERENCES orders (id)  NOT NULL,
    product_id BIGINT REFERENCES product (id) NOT NULL,
    UNIQUE (product_id, order_id)
);
