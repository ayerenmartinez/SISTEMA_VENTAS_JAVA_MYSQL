create database dbSistemaVentas;
use dbSistemaVentas;
create table categoria(
id int not null primary key auto_increment,
nombre varchar(20) not null unique,
descripcion varchar(255),
activo bit(1) default 1
);
select * from categoria;