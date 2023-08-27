create database dbSistemaVentas;
use dbSistemaVentas;
create table categoria(
id int not null primary key auto_increment,
nombre varchar(20) not null unique,
descripcion varchar(255),
activo bit(1) default 1
);
create table articulo(
id int not null primary key auto_increment,
categoria_id int not null,
codigo varchar(50),
nombre varchar(100) not null unique,
precio_venta decimal(11,2),
stock int not null,
descripcion varchar(255),
imagen varchar(50),
activo bit(1) not null default 1,
constraint fk_articulo_categoria foreign key (categoria_id) references categoria (id) on delete cascade
);
create table rol(
id int primary key not null auto_increment,
nombre varchar(20) not null unique,
descripcion varchar(255)
);
create table usuario(
id int not null primary key auto_increment,
rol_id int not null,
nombre varchar(70) not null unique,
tipo_documento varchar(20),
num_documento varchar(20),
direccion varchar(70),
telefono varchar(15),
email varchar(50) not null unique,	
clave varchar(128),
activo bit(1) not null default 1,
constraint fk_usuario_rol foreign key(rol_id) references rol(id) on delete cascade
);
create table persona(
id int not null primary key auto_increment,
tipo_persona varchar(20) not null,
nombre varchar(70) not null,
tipo_documento varchar(20),
num_documento varchar(20),
direccion varchar(70),
telefono varchar(15),
email varchar(50) not null unique,
activo bit(1) not null default 1
);
create table ingreso(
id int not null primary key,
persona_id int not null,
usuario_id int not null,
tipo_comprobante varchar(20) not null,
serie_comprobante varchar(7),
num_comprobante varchar(10) not null,
fecha datetime not null,
impuesto decimal(4,2) not null,
total decimal(11,2) not null,
estado varchar(20) not null default 'estado',
constraint fk_ingreso_persona foreign key(persona_id) references persona(id) on delete cascade,
constraint fk_ingreso_usuario foreign key(usuario_id) references usuario(id) on delete cascade
);
create table detalle_ingreso(
id int not null primary key auto_increment,
ingreso_id int not null,
articulo_id int not null,
cantidad int,
precio decimal(11,2),
constraint fk_detalle_ingreso_ingreso foreign key(ingreso_id) references ingreso(id) on delete cascade on update cascade,
constraint fk_detalle_ingreso_articulo foreign key(articulo_id) references articulo(id)  on delete cascade
);
create table venta(
id int not null primary key auto_increment,
persona_id int not null,
usuario_id int not null,
tipo_comprobante varchar(20) not null,
serie_comprobante varchar(7),
num_comprobante varchar(10) not null,
fecha datetime,
impuesto  decimal(4,2),
total decimal(11,2),
estado varchar(20) default 'Aceptado',
constraint fk_venta_persona foreign key(persona_id) references persona(id) on delete cascade,
constraint fk_venta_usuario foreign key(usuario_id) references usuario(id) on delete cascade
);
create table detalle_venta(
id int not null primary key auto_increment,
venta_id int not null,
articulo_id int not null,
cantidad int not null,
precio decimal(11,2) not null,
descuento decimal(11,2) not null,
constraint fk_detalle_venta_venta foreign key(venta_id) references venta(id) on delete cascade on update cascade,
constraint fk_detalle_venta_articulo foreign key(articulo_id) references articulo(id) on delete cascade
);
