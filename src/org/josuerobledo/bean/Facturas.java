
package org.josuerobledo.bean;

import java.sql.Date;


public class Facturas {
    private int NumeroFactura;
    private Date Fecha;
    private String Nit;
    private String Estado;
    private double Total;
    private int CodigoCliente;

    public Facturas() {
        
    }

    public Facturas(int NumeroFactura, Date Fecha, String Nit, String Estado, double Total, int CodigoCliente) {
        this.NumeroFactura = NumeroFactura;
        this.Fecha = Fecha;
        this.Nit = Nit;
        this.Estado = Estado;
        this.Total = Total;
        this.CodigoCliente = CodigoCliente;
    }

    public int getNumeroFactura() {
        return NumeroFactura;
    }

    public void setNumeroFactura(int NumeroFactura) {
        this.NumeroFactura = NumeroFactura;
    }

    public Date getFecha() {
        return Fecha;
    }

    public void setFecha(Date Fecha) {
        this.Fecha = Fecha;
    }

    public String getNit() {
        return Nit;
    }

    public void setNit(String Nit) {
        this.Nit = Nit;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String Estado) {
        this.Estado = Estado;
    }

    public double getTotal() {
        return Total;
    }

    public void setTotal(double Total) {
        this.Total = Total;
    }

    public int getCodigoCliente() {
        return CodigoCliente;
    }

    public void setCodigoCliente(int CodigoCliente) {
        this.CodigoCliente = CodigoCliente;
    }
    
    public String toString(){
        return getNumeroFactura()+"";
    }
       
    
}
