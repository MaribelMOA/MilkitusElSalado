create database if not exists MilkitusElSalado;

USE MilkitusElSalado;
Create table if not exists  Empleado(
	ID_Empleado int NOT NULL,
    Nombre varchar(20),
    ApellidoPaterno varchar(20),
	ApellidoMaterno varchar(20),
    Password  varchar(20),
    primary key (ID_Empleado)
);

Create table if not exists  Menu_Comida(
	ID_Comida varchar(5) NOT NULL,
    Nombre varchar(20),
    Precio double,
	primary key (ID_Comida)
);

Create table if not exists  Menu_Bebida(
	ID_Bebida varchar(5) NOT NULL,
    Nombre varchar(20),
    Precio double,
	Tipo varchar(1),
	primary key (ID_Bebida)
);
    

Create table if not exists  Pedido(
	No_Pedido int NOT NULL auto_increment,
    Fecha_Orden TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	Precio_Total double,
    Extra double,
    Precio_Pagado double,
    Cambio double,
    Estado varchar(2),
	primary key (No_Pedido)
);
Create table if not exists  Orden_Comida(
	Id_OrdenC int NOT NULL auto_increment,
    No_Pedido int NOT NULL,
    ID_Comida varchar(5) NOT NULL,
    Cantidad int,
	primary key (ID_OrdenC),
    constraint FK_Pedido_No_Pedido
    foreign key(No_Pedido)
    References Pedido(No_Pedido)
    ON DELETE RESTRICT ON UPDATE 
    CASCADE,
    constraint FK_Menu_Comida_ID_Comida
    foreign key(ID_Comida)
    References Menu_Comida(ID_Comida)
    ON DELETE RESTRICT ON UPDATE 
    CASCADE
);

Create table if not exists  Orden_Bebida(
	Id_OrdenB int NOT NULL auto_increment,
    No_Pedido int NOT NULL,
    ID_Bebida varchar(5) NOT NULL,
    Cantidad int,
	primary key (ID_OrdenB),
    constraint FK_Pedido_No_Pedido2
    foreign key(No_Pedido)
    References Pedido(No_Pedido)
    ON DELETE RESTRICT ON UPDATE 
    CASCADE,
    constraint FK_Menu_Bebida_ID_Bebida
    foreign key(ID_Bebida)
    References Menu_Bebida(ID_Bebida)
    ON DELETE RESTRICT ON UPDATE 
    CASCADE
);

ALTER TABLE  Menu_Comida MODIFY  Nombre varchar(50);
ALTER TABLE  Menu_Bebida MODIFY  Nombre varchar(50);
INSERT Menu_Comida VALUES
('T1','Tacos de Pescado',35),
('T2','Tacos de Camaron',45),
('T3','Tacos de Marlin',45),
('T4','Tacos de Pulpo enchilado',45),
('E1','Enchilados',50),
('TC1','Tostadas de ceviche de camaron',45),
('TC2','Tostadas de ceviche de pescado',35),
('CM','Coctel Mediano',50),
('CC','Coctel Chico',40),
('CM1','Caldo de marisco chico',60),
('CM2','Caldo de mediano',65),
('CM3','Caldo de grande',70),
('A1','Aguachile',50),
('C1','Consome',20),
('O1','Ostion',20)
;

SELECT * FROM Menu_Comida;

INSERT Menu_Bebida VALUES
('A1','Agua de Jamaica',25,'P'),
('A2','Agua de horchata',25,'P'),
('S1','Soda',30,'P'),
('S2','Soda Vidrio',35,'V');

SELECT * FROM Menu_Bebida;

INSERT empleado VALUES
(1,'Luis','Mendoza','Vazquez','milkitus123'),
(2, 'Pedro','Salas','Mascarena','milkitus123'),
(3,'Juan','Castellano','Lopez','milkitus123'),
(4,'Benjamin','Segoviano','Ruiz','milkitus123')
;

SELECT * FROM  empleado;

INSERT pedido(Fecha_Orden,Estado) VALUES
('2023-03-23 13:15:00','EP');

SELECT * FROM  PEDIDO;

ALTER TABLE orden_comida ADD subtotal double;
ALTER TABLE orden_bebida ADD subtotal double;

SELECT * FROM orden_comida;
