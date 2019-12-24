use DBSports2014453
go
-----------------------------------------------PRODUCTO---------------------------------------------------

---------------Insertar Datos en Producto-----------------

create procedure SP_InsertarProducto (@DescripcionProducto varchar(max),
 @CodigoCategoria int, @CodigoMarca int, @CodigoTalla int,@Ganancia int )
as
begin
	insert into Producto(DescripcionProducto,CodigoCategoria,CodigoMarca,CodigoTalla,Ganancia) 
	values (@DescripcionProducto,@CodigoCategoria,@CodigoMarca,@CodigoTalla,@Ganancia)
end
go

-------------Actualizar datos en Producto----------------

create procedure SP_ActualizarProducto (@Codigo int,@DescripcionProducto varchar(max),
 @CodigoCategoria int, @CodigoMarca int, @CodigoTalla int,@Ganancia int )
as
begin
	update Producto set DescripcionProducto=@DescripcionProducto,CodigoCategoria=@CodigoCategoria,
	CodigoMarca=@CodigoMarca,CodigoTalla=@CodigoTalla, Ganancia=@Ganancia

	where CodigoProducto=@Codigo
end
go

--------------------------Eliminar datos en Producto----------------------------
create procedure SP_EliminarProducto (@Codigo int)
as
begin
	delete from Producto WHERE CodigoProducto=@Codigo
end
go

-------------------------Listar en Producto----------------------------------
create procedure VerProducto
as
	select* from Producto

go


------------Buscar en PRoducto-------------
create procedure SP_BuscarProducto(@Codigo int)
as
begin
	select* from Producto where @Codigo=CodigoProducto
end
go
--------------------------------------------------------COMPRAS------------------------------------------------------------------------

---------------Insertar Datos en Compras-----------------

create procedure SP_InsertarCompras (@Direccion varchar(max), @Fecha date,@CodigoProveedor int)
as
begin
	insert into Compras(Direccion,Fecha,CodigoProveedor) values (@Direccion,@Fecha,@CodigoProveedor)
	
end
go

--------Listar en Compras------------
create procedure VerCompra
as
begin
	select * from Compras
end
go
-------------Actualizar datos en Compras----------------

create procedure SP_ActualizarCompras (@Codigo int, @Direccion varchar(max), @Fecha date, @CodigoProveedor int )
as
begin
	update Compras set Direccion=@Direccion,Fecha=@Fecha,CodigoProveedor=@CodigoProveedor
	where NumeroDocumento=@Codigo
end
go

------------Buscar en Compras-------------
create procedure SP_BuscarCompras(@Codigo int)
as
begin
	select* from Compras where @Codigo=NumeroDocumento
end
go

--------------------------Eliminar datos en Compras----------------------------
create procedure SP_EliminarCompras (@Codigo int)
as
begin
	delete from Compras WHERE NumeroDocumento=@Codigo
end
go


---------------------------------------------------------Proveedores--------------------------------------------------------




---------------Insertar Datos en Proveedores-----------------

create procedure SP_InsertarProveedores (@Direccion varchar(max), @ContactoPrincipal varchar(max), @RazonSocial varchar(max),
@PaginaWeb varchar(max), @Nit varchar(20) )
as
begin
	insert into Proveedores(Direccion,ContactoPrincipal,RazonSocial,PaginaWeb,Nit)
	values (@Direccion,@ContactoPrincipal,@RazonSocial,@PaginaWeb,@Nit)
end
go

exec SP_InsertarProveedores '13 calle a zona 7 kaminal juyu 2','Juan Perez','Operadora de Tiendas S.A','www.hola.com','3008078-5'
go
-------------Actualizar datos en Proveedores----------------

create procedure SP_ActualizarProveedores (@Codigo int, @Direccion varchar(max), @ContactoPrincipal varchar(max), @RazonSocial varchar(max),
@PaginaWeb varchar(max), @Nit varchar(20))
as
begin
	update Proveedores set Direccion=@Direccion,ContactoPrincipal=@ContactoPrincipal,RazonSocial=@RazonSocial,
	PaginaWeb=@PaginaWeb,Nit=@Nit
	where CodigoProveedor=@Codigo
end
go
-------------------------Listar en Proveedores----------------------------------
create procedure VerProveedores
as
	select* from Proveedores

go

------------Buscar en Proveedores-------------
create procedure SP_BuscarProveedor(@Codigo int)
as
begin
	select* from Proveedores where @Codigo=CodigoProveedor
end
go

---------------Eliminar Proveedores----------------
create procedure SP_EliminarProveedores (@Codigo int)
as
begin
	delete from Proveedores WHERE CodigoProveedor=@Codigo
end
go

--------------------------------------------------------------------CLIENTES------------------------------------------------------------------

---------------Insertar Datos en Clientes-----------------

create procedure SP_InsertarClientes (@Nombre varchar(max), @nit varchar(20), @Direccion varchar(max))
as
begin
	insert into Clientes(Nombre,nit,Direccion)
	values (@Nombre,@nit,@Direccion)
end
go

exec SP_InsertarClientes 'Juan Jose Arevalo', '48453415-9','10 calle C zona 1'
go

-------------------------Listar en Clientes----------------------------------
create procedure VerClientes
as
	select* from Clientes

go

-------------Actualizar datos en Clientes----------------

create procedure SP_ActualizarClientes (@Codigo int, @Nombre varchar(max), @nit varchar(20), @Direccion varchar(max) )
as
begin
	update Clientes set Nombre=@Nombre,nit=@nit,Direccion=@Direccion
	where CodigoCliente=@Codigo
end
go

------------Buscar en Clientes-------------
create procedure SP_BuscarClientes(@Codigo int)
as
begin
	select* from Clientes where @Codigo=CodigoCliente
end
go

---------------Eliminar Proveedores----------------
create procedure SP_EliminarClientes (@Codigo int)
as
begin
	delete from Clientes WHERE CodigoCliente=@Codigo
end
go

