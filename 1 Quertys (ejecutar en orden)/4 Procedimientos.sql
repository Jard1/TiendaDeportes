use DBSports2014453
go

----------------------------------------------TELFONO CLIENTES-------------------------------------------

---------------Insertar Datos en TelefonoClientes-----------------

create procedure SP_InsertarTelefonoClientes(@Descripcion varchar(max), @Numero varchar(20), @CodigoCliente int)
as
begin
	insert into TelefonoClientes(Descripcion,Numero,CodigoCliente) 
	values (@Descripcion,@Numero,@CodigoCliente)
end
go

-------------------------Listar en TelefonoClientes----------------------------------
create procedure VerTelefonoClientes
as
	select* from TelefonoClientes

go

-------------Actualizar datos en TelefonoClientes----------------
create procedure SP_ActualizarTelefonoClientes (@Codigo int, @Descripcion varchar(max),@Numero varchar(20), @CodigoCliente int )
as
begin
	update TelefonoClientes set Descripcion=@Descripcion, Numero=@Numero,CodigoCliente=@CodigoCliente
	where CodigoTelefonoCliente=@Codigo
end
go

------------Buscar en TelefonoClientes-------------
create procedure SP_BuscarTelefonoClientes (@Codigo int)
as
begin
	select* from TelefonoClientes where @Codigo=CodigoTelefonoCliente
end
go

--------------------------Eliminar datos en TelefonoClientes----------------------------
create procedure SP_EliminarTelefonoClientes (@Codigo int )
as
begin
	delete from TelefonoClientes WHERE CodigoTelefonoCliente=@Codigo
end
go


---------------------------------------------------CORREO CLIENTES-----------------------------------------------------

-------------------------Listar en EmailCliente----------------------------------
create procedure VerEmailCliente
as
	select* from EmailCliente

go

---------------Insertar Datos en EmailCliente-----------------

create procedure SP_InsertarEmailCliente(@Descripcion varchar(max), @Email varchar(max), @CodigoCliente int)
as
begin
	insert into EmailCliente(Descripcion,Email,CodigoCliente) 
	values (@Descripcion,@Email,@CodigoCliente)
end
go

-------------Actualizar datos en EmailCliente----------------
create procedure SP_ActualizarEmailCliente (@Codigo int, @Descripcion varchar(max),@Email varchar(max), @CodigoCliente int )
as
begin
	update EmailCliente set Descripcion=@Descripcion, Email=@Email,CodigoCliente=@CodigoCliente
	where CodigoEmailCliente=@Codigo
end
go

------------Buscar en EmailCliente-------------
create procedure SP_BuscarEmailCliente (@Codigo int)
as
begin
	select* from EmailCliente where @Codigo=CodigoEmailCliente
end
go

--------------------------Eliminar datos en TelefonoClientes----------------------------
create procedure SP_EliminarEmailCliente (@Codigo int )
as
begin
	delete from EmailCliente WHERE CodigoEmailCliente=@Codigo
end
go

-----------------------------------------------------------TELEFONO PROVEEDORES------------------------------------------------------------------

-------------------------Listar en TelefonoProveedores----------------------------------
create procedure VerTelefonoProveedores
as
	select* from TelefonoProveedores
go

---------------Insertar Datos en TelefonoProveedores-----------------

create procedure SP_InsertarTelefonoProveedores(@Descripcion varchar(max), @Numero varchar(max), @CodigoProveedor int)
as
begin
	insert into TelefonoProveedores(Descripcion,Numero,CodigoProveedor) 
	values (@Descripcion,@Numero,@CodigoProveedor)
end
go

-------------Actualizar datos en TelefonoProveedores----------------
create procedure SP_ActualizarTelefonoProveedores (@Codigo int, @Descripcion varchar(max),@Numero varchar(max), @CodigoProveedor int )
as
begin
	update TelefonoProveedores set Descripcion=@Descripcion, Numero=@Numero,CodigoProveedor=@CodigoProveedor
	where CodigoTelefonoProveedor=@Codigo
end
go

------------Buscar en TelefonoProveedores-------------
create procedure SP_BuscarTelefonoProveedores (@Codigo int)
as
begin
	select* from TelefonoProveedores where @Codigo=CodigoTelefonoProveedor
end
go

--------------------------Eliminar datos en TelefonoProveedores----------------------------
create procedure SP_EliminarTelefonoProveedores (@Codigo int )
as
begin
	delete from TelefonoProveedores WHERE CodigoTelefonoProveedor=@Codigo
end
go

--------------------------------------------------------------CORREO PROVEEDORES------------------------------------------------------------------

-------------------------Listar en EmailProveedores----------------------------------
create procedure VerEmailProveedores
as
	select* from EmailProveedores
go

---------------Insertar Datos en EmailProveedores-----------------

create procedure SP_InsertarEmailProveedores(@Descripcion varchar(max), @Email varchar(max), @CodigoProveedor int)
as
begin
	insert into EmailProveedores(Descripcion,Email,CodigoProveedor) 
	values (@Descripcion,@Email,@CodigoProveedor)
end
go

-------------Actualizar datos en EmailProveedores----------------
create procedure SP_ActualizarEmailProveedores (@Codigo int, @Descripcion varchar(max),@Email varchar(max), @CodigoProveedor int )
as
begin
	update EmailProveedores set Descripcion=@Descripcion, Email=@Email,CodigoProveedor=@CodigoProveedor
	where CodigoEmailProveedor=@Codigo
end
go

------------Buscar en EmailProveedores-------------
create procedure SP_BuscarEmailProveedores (@Codigo int)
as
begin
	select* from EmailProveedores where @Codigo=CodigoEmailProveedor
end
go

--------------------------Eliminar datos en EmailProveedores----------------------------
create procedure SP_EliminarEmailProveedores (@Codigo int )
as
begin
	delete from EmailProveedores WHERE CodigoEmailProveedor=@Codigo
end
go



