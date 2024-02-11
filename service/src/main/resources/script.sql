CREATE TABLE categories
(
    id     SERIAL PRIMARY KEY,
    code   INT UNIQUE,
    name   VARCHAR(255),
    parent INT
);

CREATE TABLE users
(
    id         BIGSERIAL PRIMARY KEY,
    email      VARCHAR(255) NOT NULL UNIQUE,
    password   VARCHAR(255),
    name       VARCHAR(255),
    patronimic VARCHAR(255),
    surname    VARCHAR(255),
    telephone  VARCHAR(255)
);

CREATE TABLE product
(
    id            BIGSERIAL PRIMARY KEY,
    color         VARCHAR(255),
    model         VARCHAR(255),
    name          VARCHAR(255),
    price         DOUBLE PRECISION,
    producer      VARCHAR(255),
    categories_id INT REFERENCES categories (id)
);

CREATE TABLE property
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    unit VARCHAR(255)
);

CREATE TABLE product_property
(
    id          BIGSERIAL PRIMARY KEY,
    value       VARCHAR(255),
    product_id  BIGINT REFERENCES product (id),
    property_id BIGINT REFERENCES property (id)
);

CREATE TABLE storage
(
    id     BIGSERIAL PRIMARY KEY,
    house  VARCHAR(255),
    name   VARCHAR(255),
    street VARCHAR(255),
    town   VARCHAR(255)
);

CREATE TABLE product_store
(
    id         BIGSERIAL PRIMARY KEY,
    count      INT,
    product_id BIGINT REFERENCES product (id),
    storage_id BIGINT REFERENCES storage (id)
);

create table orders
(
    id           BIGSERIAL PRIMARY KEY,
    date_create  TIMESTAMP,
    date_update  TIMESTAMP,
    order_status VARCHAR(255),
    sum          DOUBLE PRECISION,
    user_id      BIGINT REFERENCES users (id),
    storage_id   BIGINT REFERENCES users (id)
);

create table product_orders
(
    id         BIGSERIAL PRIMARY KEY,
    count      INT,
    orders_id  BIGINT REFERENCES orders (id),
    product_id BIGINT REFERENCES product (id)
);
