
package org.josuerobledo.bean;

import java.sql.Date;

public class Compras {
    private int NumeroDocumento;
    private String Direccion;
    private Date Fecha;
    private double Total;
    private  int CodigoProveedor;

    public Compras() {
    }

    public Compras(int NumeroDocumento, String Direccion, Date Fecha, double Total, int CodigoProveedor) {
        this.NumeroDocumento = NumeroDocumento;
        this.Direccion = Direccion;
        this.Fecha = Fecha;
        this.Total = Total;
        this.CodigoProveedor = CodigoProveedor;
    }

    public int getNumeroDocumento() {
        return NumeroDocumento;
    }

    public void setNumeroDocumento(int NumeroDocumento) {
        this.NumeroDocumento = NumeroDocumento;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String Direccion) {
        this.Direccion = Direccion;
    }

    public Date getFecha() {
        return Fecha;
    }

    public void setFecha(Date Fecha) {
        this.Fecha = Fecha;
    }

    public double getTotal() {
        return Total;
    }

    public void setTotal(double Total) {
        this.Total = Total;
    }
    

    public int getCodigoProveedor() {
        return CodigoProveedor;
    }

    public void setCodigoProveedor(int CodigoProveedor) {
        this.CodigoProveedor = CodigoProveedor;
    }


    public String toString() {
        return getNumeroDocumento() + "";
    }
    
    
    
}


