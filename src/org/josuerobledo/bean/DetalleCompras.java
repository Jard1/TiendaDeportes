
package org.josuerobledo.bean;


public class DetalleCompras {
    private int CodigoDetalleCompras;
    private int CodigoProducto;
    private int Cantidad;
    private double CostoUnitario;
    private int NumeroDocumento;

    public DetalleCompras() {
        
    }

    public DetalleCompras(int CodigoDetalleCompras, int CodigoProducto, int Cantidad, double CostoUnitario, int NumeroDocumento) {
        this.CodigoDetalleCompras = CodigoDetalleCompras;
        this.CodigoProducto = CodigoProducto;
        this.Cantidad = Cantidad;
        this.CostoUnitario = CostoUnitario;
        this.NumeroDocumento = NumeroDocumento;
    }

    public int getCodigoDetalleCompras() {
        return CodigoDetalleCompras;
    }

    public void setCodigoDetalleCompras(int CodigoDetalleCompras) {
        this.CodigoDetalleCompras = CodigoDetalleCompras;
    }

    public int getCodigoProducto() {
        return CodigoProducto;
    }

    public void setCodigoProducto(int CodigoProducto) {
        this.CodigoProducto = CodigoProducto;
    }

    public int getCantidad() {
        return Cantidad;
    }

    public void setCantidad(int Cantidad) {
        this.Cantidad = Cantidad;
    }

    public double getCostoUnitario() {
        return CostoUnitario;
    }

    public void setCostoUnitario(double CostoUnitario) {
        this.CostoUnitario = CostoUnitario;
    }

    public int getNumeroDocumento() {
        return NumeroDocumento;
    }

    public void setNumeroDocumento(int NumeroDocumento) {
        this.NumeroDocumento = NumeroDocumento;
    }
    
    public String toString(){
        return getCodigoDetalleCompras()+"";
    }

}
