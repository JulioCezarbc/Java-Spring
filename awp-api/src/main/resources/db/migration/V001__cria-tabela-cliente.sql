create table cliente(
    id BIGINT not null auto_increment,
    nome varchar(60) not null,
    email varchar(60) not null,
    telefone varchar(15) not null,

    primary key (id),
    constraint uk_cliente unique (email)
);
