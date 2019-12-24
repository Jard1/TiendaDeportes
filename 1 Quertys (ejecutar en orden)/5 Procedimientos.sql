use  DBSports2014453
go

------------------------------------------------------FACTURAS----------------------------------------------------------------
-------------------------Listar en Facturas----------------------------------
create procedure VerFacturas
as
	select* from Facturas
go

---------------Insertar Datos en Facturas-----------------

create procedure SP_InsertarFacturas(@Fecha date, @Nit varchar(max), @Estado varchar(30),@CodigoCliente int )
as
begin
	insert into Facturas(Fecha,Nit,Estado,CodigoCliente ) 
	values (@Fecha,@Nit,@Estado,@CodigoCliente)
	
	
end
go

-------------Actualizar datos en Facturas----------------
create procedure SP_ActualizarFacturas (@Codigo int, @Fecha date,@Nit varchar(max), @Estado varchar(30), @CodigoCliente int )
as
begin
	update Facturas set Fecha=@Fecha, Nit=@Nit,Estado=@Estado,CodigoCliente=@CodigoCliente
	where NumeroFactura=@Codigo
end
go

------------Buscar en Facturas-------------
create procedure SP_BuscarFacturas(@Codigo int)
as
begin
	select* from Facturas where @Codigo=NumeroFactura
end
go

--------------------------Eliminar datos en Facturas----------------------------
create procedure SP_EliminarFacturas (@Codigo int )
as
begin
	delete from Facturas WHERE NumeroFactura=@Codigo
end
go

---------------------------------------------------------DETALLE FACTURAS----------------------------------------------------------------

-------------------------Listar en DetalleFactura----------------------------------
create procedure VerDetalleFactura
as
	select* from DetalleFactura
go

---------------Insertar Datos en DetalleFactura-----------------

create procedure SP_InsertarDetalleFactura( @Cantidad int, @NumeroFactura int, @CodigoProducto int)
as
begin

	declare @Existencia int
	select @Existencia = Existencia from Producto where @CodigoProducto=CodigoProducto
	declare @Precio money
	
	
	if(@Cantidad>0 and @Cantidad<=@Existencia)
	begin
	
		update Producto set Existencia=Existencia-@Cantidad
		
		if(@Cantidad<=9)
		begin
			select @Precio = PrecioUnitario from Producto where @CodigoProducto=CodigoProducto
		
			print 'costoUnitario'
			
		end

		else if(@Cantidad<25)
		begin
			select @Precio = PrecioPorDocena from Producto where @CodigoProducto=CodigoProducto
			print 'costoPorDocena'
		end

		else
		begin
			select @Precio = PrecioPorMayor from Producto where @CodigoProducto=CodigoProducto
			print 'costoPorMayor'
		end
		
		update Facturas set Total+=(@Precio*@Cantidad) where @NumeroFactura=@NumeroFactura

		insert into DetalleFactura(Cantidad,NumeroFactura,CodigoProducto,Precio) 
		values (@Cantidad,@NumeroFactura,@CodigoProducto,@Precio)

	end
	else
	begin
		print 'Lo sentimos ya no tenemos ese producto'
	end
	
end
go



-------------Actualizar datos en DetalleFactura----------------
create procedure SP_ActualizarDetalleFactura (@Codigo int, @Cantidad int, @NumeroFactura int, @CodigoProducto int)
as
begin

	declare @Existencia int
	select @Existencia = Existencia from Producto where @CodigoProducto=CodigoProducto
	
	if(@Cantidad>0 and @Cantidad<=@Existencia)
	begin
		update DetalleFactura set  Cantidad=@Cantidad,NumeroFactura=@NumeroFactura,CodigoProducto=@CodigoProducto
		where CodigoDetalleFactura=@Codigo
		
		
		update Facturas set Total+=(@Precio*@Cantidad) where @NumeroFactura=@NumeroFactura
		
	end
	
	else
	begin
		print 'Lo sentimos ya no tenemos ese producto'
	end
end
go

------------Buscar en DetalleFactura-------------
create procedure SP_BuscarDetalleFactura(@Codigo int)
as
begin
	select* from DetalleFactura where @Codigo=CodigoDetalleFactura
end
go

--------------------------Eliminar datos en DetalleFactura----------------------------
create procedure SP_EliminarDetalleFactura (@Codigo int )
as
begin
	delete from DetalleFactura WHERE CodigoDetalleFactura=@Codigo
end
go



------------------------------------------------------------DETALLE COMPRAS-----------------------------------------------------

-------------------------Listar en DetalleCompras----------------------------------
create procedure VerDetalleCompras
as
	select* from DetalleCompras
go

---------------Insertar Datos en DetalleCompras-----------------

create procedure SP_InsertarDetalleCompras(@CodigoProducto int, @Cantidad int, @NumeroDocumento int,@CostoUnitario money)
as
begin
	--insertamos los datos en la tabla
	insert into DetalleCompras(CodigoProducto,Cantidad,NumeroDocumento,CostoUnitario) 
	values (@CodigoProducto,@Cantidad,@NumeroDocumento,@CostoUnitario)
	
	declare @Precio money --en esta variable vamos a guardar la multiplicacion de la cantidad por los productos para sacar el total
	
	declare @Ganancia money-- en esta variable voy a guardar la ganancia que el usuario escojio para su producto que esta en la tabla productos
	
	select @Ganancia = Ganancia from Producto where @CodigoProducto=CodigoProducto --aqui guardo el dato de ganancia de tabla productos en la variable
	
	set  @Ganancia= @Ganancia/100 /*aqui lo divido entre cien para sacar el porcentaje Ej: si el usuario eligio 50, 50/100 es 0.5 y luego lo multiplico
								   por el producto para sacar el porcentaje*/
	
	
	select @Precio =  (@Cantidad*@CostoUnitario)--para sacar el total
	
	update Compras set Total += @Precio where @NumeroDocumento=NumeroDocumento--para agregar el total a la tabla de compras

	update Producto set Existencia+=@Cantidad where @CodigoProducto=CodigoProducto--para agregar la cantidad a las existencias de ese producto
	
	update Producto set PrecioUnitario=@CostoUnitario+(@CostoUnitario*@Ganancia) where @CodigoProducto=CodigoProducto --aqui se saca el precio unitario segun el porcentaje
	
	update Producto set PrecioPorDocena=PrecioUnitario-(PrecioUnitario*0.05) where @CodigoProducto=CodigoProducto
	
	update Producto set PrecioPorMayor=PrecioUnitario-(PrecioUnitario*0.07) where @CodigoProducto=CodigoProducto
	
end
go


-------------Actualizar datos en DetalleCompras----------------
create procedure SP_ActualizarDetalleCompras (@CantidadActual int, @Codigo int, @CodigoProducto int, @Cantidad int, @NumeroDocumento int, @CostoUnitario money)
as
begin
	declare @Precio money
	declare @ExistenciaI int /* en esta variable se guardara la existencia inicial que estaba 
							 antes de hacer una compra, que seria igual a la cantidad actual en la tabla producto,
							 menos la cantidad que el ingreso en el procedimiento de insertar,  la cual vamos a
							 guardar en la variable @CantidadActual*/

	update DetalleCompras set CodigoProducto=@CodigoProducto, Cantidad=@Cantidad,NumeroDocumento=@NumeroDocumento, CostoUnitario=@CostoUnitario
	where CodigoDetalleCompras=@Codigo --para actualizar los datos
	
	
	select @Precio =  (@Cantidad*@CostoUnitario) --para sacar el total

	update Compras set Total = @Precio where @NumeroDocumento=NumeroDocumento --aqui le agregamos el total a la tabla compras

	
	select @ExistenciaI = Existencia from Producto where @CodigoProducto=CodigoProducto --a existencia inicial le agegamos
	
	set @ExistenciaI = @ExistenciaI-@CantidadActual /* a @ExistenciaI le restamos la  @CantidadActual
													para que nos de la existencia que habia antes de hacer
													una compra*/

	update Producto set Existencia=@ExistenciaI+@Cantidad where @CodigoProducto=CodigoProducto

end
go

------------Buscar en DetalleCompras-------------
create procedure SP_BuscarDetalleCompras(@Codigo int)
as
begin
	select* from DetalleCompras where @Codigo=CodigoDetalleCompras
end
go

--------------------------Eliminar datos en DetalleCompras----------------------------
create procedure SP_EliminarDetalleCompras (@Codigo int )
as
begin
	delete from DetalleCompras WHERE CodigoDetalleCompras=@Codigo
end
go