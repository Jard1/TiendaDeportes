
package org.josuerobledo.bean;

public class DetalleFacturas {
    
    private int CodigoDetalleFactura;
    private double Precio;
    private int Cantidad;
    private int NumeroFactura;
    private int CodigoProducto;

    public DetalleFacturas() {
        
    }

    public DetalleFacturas(int CodigoDetalleFactura, double Precio, int Cantidad, int NumeroFactura, int CodigoProducto) {
        this.CodigoDetalleFactura = CodigoDetalleFactura;
        this.Precio = Precio;
        this.Cantidad = Cantidad;
        this.NumeroFactura = NumeroFactura;
        this.CodigoProducto = CodigoProducto;
    }

    public int getCodigoDetalleFactura() {
        return CodigoDetalleFactura;
    }

    public void setCodigoDetalleFactura(int CodigoDetalleFactura) {
        this.CodigoDetalleFactura = CodigoDetalleFactura;
    }

    public double getPrecio() {
        return Precio;
    }

    public void setPrecio(double Precio) {
        this.Precio = Precio;
    }

    public int getCantidad() {
        return Cantidad;
    }

    public void setCantidad(int Cantidad) {
        this.Cantidad = Cantidad;
    }

    public int getNumeroFactura() {
        return NumeroFactura;
    }

    public void setNumeroFactura(int NumeroFactura) {
        this.NumeroFactura = NumeroFactura;
    }

    public int getCodigoProducto() {
        return CodigoProducto;
    }

    public void setCodigoProducto(int CodigoProducto) {
        this.CodigoProducto = CodigoProducto;
    }
    
    public String toString(){
        return getCodigoDetalleFactura()+"";
    }
    
            
}
