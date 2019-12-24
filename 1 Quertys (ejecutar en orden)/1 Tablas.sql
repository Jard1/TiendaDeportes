create database DBSports2014453
go

use DBSports2014453
go


----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
create table Categoria(
	CodigoCategoria int not null primary key identity,
	DescripcionCategoria varchar(max)
)
go	

create table Marcas(
	CodigoMarca int not null primary key identity,
	DescripcionMarcas varchar(max)
)
go
	
create table Talla(
	CodigoTalla int not null primary key identity,
	DescripcionTalla varchar(max)
)
go



create table Producto(
	CodigoProducto int not null primary key identity,
	DescripcionProducto varchar(max),
	Existencia int null default 0,
	Ganancia int,

	PrecioUnitario money null default 0.00,
	PrecioPorDocena money null default 0.00,
	PrecioPorMayor money null default 0.00,
	
	
	CodigoCategoria int,
	CodigoMarca int,
	CodigoTalla int,

	Constraint FK_Producto_Categoria foreign key(CodigoCategoria) references Categoria(CodigoCategoria),
	Constraint FK_Producto_Marca foreign key (CodigoMarca) references Marcas(CodigoMarca),
	Constraint FK_Producto_Talla foreign key (CodigoTalla) references Talla(CodigoTalla)
)
go

-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

create table Proveedores(
	CodigoProveedor int primary key not null identity,
	Direccion varchar(max),
	ContactoPrincipal varchar(max),
	RazonSocial varchar(max),
	PaginaWeb  varchar(max),
	Nit varchar(20),
	
)
go

create table TelefonoProveedores(
	CodigoTelefonoProveedor int primary key not null identity,
	Numero varchar(8),
	Descripcion varchar(max),
	CodigoProveedor int

	Constraint FK_TelefonoProveedores_Proveedores foreign key(CodigoProveedor) references Proveedores(CodigoProveedor)
)
go

create table EmailProveedores(
	CodigoEmailProveedor int primary key not null identity,
	Email varchar(max),
	Descripcion varchar(max),
	CodigoProveedor int

	Constraint FK_EmailProveedores_Proveedores foreign key (CodigoProveedor) references Proveedores(CodigoProveedor)
)

-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


-- printStackTrace()
create table Clientes(
	CodigoCliente int not null primary key identity(1,1) ,
	Nombre varchar(256) not null,
	nit varchar(20) not null,
	Direccion varchar(256) not null
)
go

create table TelefonoClientes(
	CodigoTelefonoCliente int not null primary key identity,
	Descripcion varchar(max),
	Numero varchar(8),
	CodigoCliente int

	constraint FK_TelefonoClientes_Clientes foreign key (CodigoCliente) references CLientes(CodigoCliente)
)
go

create table EmailCliente(
	CodigoEmailCliente int not null primary key identity,
	Email varchar(max),
	Descripcion varchar(max),
	CodigoCliente int

	constraint FK_EmailCliente_Clientes foreign key (CodigoCliente) references CLientes(CodigoCliente)
)

----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


create table Facturas(
	NumeroFactura int not null primary key identity,
	Fecha date,
	Nit varchar(256),
	Estado varchar(30),--**
	Total money null default 0.00,

	CodigoCliente int not null,
	constraint FK_Facturas_Clientes foreign key (CodigoCliente) references Clientes(CodigoCliente)
)
go


create table DetalleFactura(
	CodigoDetalleFactura int not null primary key identity,
	Precio money null default 0.00,
	Cantidad int,

	NumeroFactura int,
	CodigoProducto int,

	constraint FK_DetalleFactura_Factura foreign key (NumeroFactura) references Facturas(NumeroFactura),
	constraint FK_DetalleFactura_Producto foreign key (CodigoProducto) references Producto(CodigoProducto)
)
go




---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

create table Compras(
	NumeroDocumento int not null primary key identity,
	Direccion varchar(max),
	Fecha date,
	Total money  null default 0.00,

	CodigoProveedor int,

	Constraint FK_Compras_Proveedores foreign key (CodigoProveedor) references Proveedores(CodigoProveedor)
)
go

create table DetalleCompras(

	CodigoDetalleCompras int not null primary key identity,
	CodigoProducto int,
	Cantidad int,
	CostoUnitario money null default 0.00,
	NumeroDocumento int,
	

	constraint FK_DetalleCompras_Producto foreign key (CodigoProducto) references Producto(CodigoProducto),
	constraint FK_DetalleCompras_Compras foreign key (NumeroDocumento) references Compras(NumeroDocumento)
)
go

