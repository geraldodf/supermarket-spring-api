CREATE TABLE products
(
    product_id            SERIAL PRIMARY KEY,
    product_description   VARCHAR(266),
    product_price_buy     DECIMAL(6, 2),
    product_price_sale    DECIMAL(6, 2),
    product_net_profit    DECIMAL(6, 2),
    product_bar_code      BIGINT,
    product_quantity      INT,
    product_creation_date VARCHAR(266)
);

CREATE TABLE users
(
    user_id                   SERIAL PRIMARY KEY,
    user_name                 VARCHAR(266),
    user_surname              VARCHAR(266),
    user_phone_number         VARCHAR(266),
    user_phone_number_reserve VARCHAR(266),
    user_email                VARCHAR(266),
    user_password             VARCHAR(266)
);

CREATE TABLE addresses
(
    address_id              SERIAL PRIMARY KEY,
    address_street_name     VARCHAR(266),
    address_number          BIGINT,
    address_complement      VARCHAR(266),
    address_reference_point VARCHAR(266),
    address_neighborhood    VARCHAR(266),
    address_cep             VARCHAR(266),
    address_city            VARCHAR(266),
    address_uf              VARCHAR(266),
    address_fk              INT,
    FOREIGN KEY (address_fk) REFERENCES users (user_id)
);

CREATE TABLE sales
(
    sale_id    SERIAL PRIMARY KEY,
    sale_date  VARCHAR(266),
    sale_value DECIMAL(6, 2)
);

CREATE TABLE product_sales
(
    product_sales_id SERIAL PRIMARY KEY,
    sale_fk          INT NOT NULL,
    product_fk       INT NOT NULL,
    product_price    INT,
    quantity_product DECIMAL(6, 2),
    FOREIGN KEY (product_fk) REFERENCES products (product_id),
    FOREIGN KEY (sale_fk) REFERENCES sales (sale_id)
);

CREATE TABLE product_type
(
    product_type_id   SERIAL PRIMARY KEY,
    name_product_type VARCHAR(266)
);

ALTER TABLE products
    ADD product_type_fk INT;

ALTER TABLE products
    ADD CONSTRAINT fk_product_type
        FOREIGN KEY (product_type_fk)
            REFERENCES product_type (product_type_id);

CREATE TABLE roles
(
    role_id   SERIAL PRIMARY KEY,
    role_name VARCHAR(266)
);
