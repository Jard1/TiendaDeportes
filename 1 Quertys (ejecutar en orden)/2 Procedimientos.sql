use  DBSports2014453
go


--------------------------------------CATEGORIA--------------------------------------------------

----------Insertar Datos en Categoria-----------

create procedure SP_InsertarCategoria (@Descripcion varchar(max) )
as
begin
	insert into Categoria(DescripcionCategoria) values (@Descripcion)
end
go

exec SP_InsertarCategoria 'Tenis de Mesa'
go
---------Actualizar datos en Categoria-----------

create procedure SP_ActualizarCategoria (@Codigo int, @Descripcion varchar(max) )
as
begin
	update Categoria set DescripcionCategoria=@Descripcion
	where CodigoCategoria=@Codigo
end
go

---------Eliminar datos en Categoria---------

create procedure SP_EliminarCategoria (@Codigo int)
as
begin
	delete from Categoria WHERE CodigoCategoria=@Codigo
end
go

------------Listar en Categoria-----------
create procedure VerCategoria
as
	select* from Categoria
go

------------Buscar en Categoria-------------
create procedure SP_BuscarCategoria (@Codigo int)
as
begin
	select* from Categoria where @Codigo=CodigoCategoria
end
go


-----------------------------------------------MARCAS-----------------------------------------------------

---------------Insertar Datos en Marcas-----------------

create procedure SP_InsertarMarca (@Descripcion varchar(max) )
as
begin
	insert into Marcas(DescripcionMarcas) values (@Descripcion)
end
go

exec SP_InsertarMarca 'Adidas'
go
-------------Actualizar datos en Marcas----------------
create procedure SP_ActualizarMarca (@Codigo int, @Descripcion varchar(max) )
as
begin
	update Marcas set DescripcionMarcas=@Descripcion
	where CodigoMarca=@Codigo
end
go

------------Buscar en Marcas-------------
create procedure SP_BuscarMarca (@Codigo int)
as
begin
	select* from Marcas where @Codigo=CodigoMarca
end
go


--------------------------Eliminar datos en Marcas----------------------------
create procedure SP_EliminarMarca (@Codigo int)
as
begin
	delete from Marcas WHERE CodigoMarca=@Codigo
end
go

-------------------------Listar en Marca----------------------------------
create procedure VerMarca
as
	select* from Marcas
go


----------------------------------------------Talla--------------------------------------------------------

---------------Insertar Datos en Talla-----------------

create procedure SP_InsertarTalla (@Descripcion varchar(max) )
as
begin
	insert into Talla(DescripcionTalla) values (@Descripcion)
end
go

exec SP_InsertarTalla 'Talla 30'
go
-------------Actualizar datos en Talla----------------
create procedure SP_ActualizarTalla (@Codigo int, @Descripcion varchar(max) )
as
begin
	update Talla set DescripcionTalla=@Descripcion
	where CodigoTalla=@Codigo
end
go

--------------------------Eliminar datos en Talla----------------------------
create procedure SP_EliminarTalla (@Codigo int )
as
begin
	delete from Talla WHERE CodigoTalla=@Codigo
end
go

-------------------------Listar en Talla----------------------------------
create procedure VerTalla
as
	select* from Talla
go

------------Buscar en Talla-------------
create procedure SP_BuscarTalla (@Codigo int)
as
begin
	select* from Talla where @Codigo=CodigoTalla
end
go


