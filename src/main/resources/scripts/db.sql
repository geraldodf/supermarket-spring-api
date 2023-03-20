create
database supermarketdb;
use
supermarketdb;

create table products
(
    product_id            Integer NOT NULL auto_increment primary key,
    product_description   varchar(266),
    product_price_buy     decimal(6, 2),
    product_price_sale    decimal(6, 2),
    product_net_profit    decimal(6, 2),
    product_bar_code      bigint,
    product_quantity      integer,
    product_creation_date varchar(266)
);

create table users
(
    user_id                   Integer NOT NULL auto_increment primary key,
    user_name                 varchar(266),
    user_surname              varchar(266),
    user_phone_number         varchar(266),
    user_phone_number_reserve varchar(266),
    user_email                varchar(266),
    user_password             varchar(266)

);

create table sales
(
    sale_id    Integer NOT NULL auto_increment primary key,
    sale_date  varchar(266),
    sale_value decimal(6, 2)
);

create table product_sales
(
    product_sales_id Integer NOT NULL auto_increment primary key,
    sale_fk          Integer NOT null,
    product_fk       Integer NOT NULL,
    product_price    integer,
    quantity_product decimal(6, 2),
    foreign key (product_fk) references products (product_id),
    foreign key (sale_fk) references sales (sale_id)
);

create table product_type
(
    product_type_id   Integer NOT NULL auto_increment primary key,
    name_product_type varchar(266)
);

ALTER TABLE products
    add product_type_fk Integer;
ALTER TABLE products
    add foreign key (product_type_fk) references product_type (product_type_id);

create table roles
(
    role_id   Integer NOT NULL auto_increment primary key,
    role_name varchar(266)
);