use
supermercadodb;
create table produtos
(
    produto_id              Integer NOT NULL auto_increment primary key,
    produto_descricao       varchar(266),
    produto_preco_de_compra decimal(6, 2),
    produto_preco_de_venda  decimal(6, 2),
    lucro_liquido           decimal(6, 2),
    produto_codigo          bigint,
    produto_quantidade      integer,
    produto_data_criacao    varchar(266)
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
    venda_id    Integer NOT NULL auto_increment primary key,
    venda_data  varchar(266),
    venda_valor decimal(6, 2)
);

create table vendas_produtos
(
    venda_produto_id Integer NOT NULL auto_increment primary key,
    vendafk          Integer NOT null,
    produtofk        Integer NOT NULL,
    foreign key (produtofk) references produtos (produto_id),
    foreign key (vendafk) references vendas (venda_id)
);

create table tipo_do_produto
(
    tipo_do_produto_id   Integer NOT NULL auto_increment primary key,
    nome_tipo_do_produto varchar(266)
);

ALTER TABLE produtos
    add tipo_do_produto_fk Integer;
ALTER TABLE produtos
    add foreign key (tipo_do_produto_fk) references tipo_do_produto (tipo_do_produto_id);

create table cargos
(
    cargo_id   Integer NOT NULL auto_increment primary key,
    cargo_nome varchar(266)
);


