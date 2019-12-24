use DBSports2014453
go
-----------------------------------------REPORTE DE COMPRAS--------------------------------------------------------

create proc SP_ReporteCompras @Nodocumento int
as
begin
	select Compras.NumeroDocumento, Compras.Fecha, Compras.Direccion, Compras.Total, Proveedores.RazonSocial from Compras inner join Proveedores on Compras.CodigoProveedor=Proveedores.CodigoProveedor
	where @Nodocumento=NumeroDocumento
end
go

create proc SP_ReporteDetalleCompras @Nodocumento int
as
begin
	select Producto.DescripcionProducto, DetalleCompras.Cantidad, DetalleCompras.CostoUnitario from DetalleCompras inner join Producto  on DetalleCompras.CodigoProducto=Producto.CodigoProducto
	where @Nodocumento = NumeroDocumento

end
go

create proc SP_ReporteProducto
as
begin
	select Producto.DescripcionProducto, Categoria.DescripcionCategoria, Marcas.DescripcionMarcas, 
	Talla.DescripcionTalla, Producto.Existencia, Producto.PrecioUnitario, Producto.PrecioPorDocena,
	Producto.PrecioPorMayor, Producto.Ganancia

	from Producto inner join Categoria  on Producto.CodigoCategoria=Categoria.CodigoCategoria
	inner join Marcas on Producto.CodigoMarca= Marcas.CodigoMarca inner join Talla on Producto.CodigoTalla=Talla.CodigoTalla

end
go

create proc SP_ReporteFactura @NumeroFactura int
as
begin
	select Facturas.NumeroFactura, Facturas.Fecha, Facturas.Nit, Facturas.Estado, Facturas.Total, Clientes.Nombre

	from Facturas inner join Clientes  on Facturas.CodigoCliente=Clientes.CodigoCliente
	where @NumeroFactura = NumeroFactura

end
go

create proc SP_ReporteDetalleFactura @NumeroFactura int
as
begin
	select Producto.DescripcionProducto, DetalleFactura.Precio, DetalleFactura.Cantidad

	from DetalleFactura inner join Producto  on DetalleFactura.CodigoProducto=Producto.CodigoProducto
	where @NumeroFactura = NumeroFactura

end




