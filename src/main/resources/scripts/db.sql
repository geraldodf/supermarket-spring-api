create table produtos
(
    produto_id              Integer NOT NULL auto_increment primary key,
    produto_descricao       varchar(266),
    produto_preco_de_compra decimal(6, 2),
    produto_preco_de_venda  decimal(6, 2),
    lucro_liquido           decimal(6, 2),
    produto_codigo          bigint,
    produto_quantidade      integer,
    produto_data_criaco     varchar(266)
);

create table usuarios
(
    usuario_id    Integer NOT NULL auto_increment primary key,
    usuario_nome  varchar(266),
    usuario_cargo varchar(266),
    usuario_senha bigint
);

create table vendas
(
    venda_id   Integer NOT NULL auto_increment primary key,
    venda_data varchar(266)
);

create table vendas_produtos
(
    venda_produto_id Integer NOT NULL auto_increment primary key,
    vendafk          Integer,
    produtofk        Integer,
    foreign key (produtofk) references produtos (produto_id),
    foreign key (vendafk) references vendas (venda_id)
);


