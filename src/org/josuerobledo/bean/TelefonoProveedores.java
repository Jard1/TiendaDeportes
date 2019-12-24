
package org.josuerobledo.bean;

public class TelefonoProveedores {
    private int CodigoTelefonoProveedor;
    private String Numero;
    private String Descripcion;
    private int CodigoProveedor;

    public TelefonoProveedores() {
        
    }

    public TelefonoProveedores(int CodigoTelefonoProveedor, String Numero, String Descripcion, int CodigoProveedor) {
        this.CodigoTelefonoProveedor = CodigoTelefonoProveedor;
        this.Numero = Numero;
        this.Descripcion = Descripcion;
        this.CodigoProveedor = CodigoProveedor;
    }

    public int getCodigoTelefonoProveedor() {
        return CodigoTelefonoProveedor;
    }

    public void setCodigoTelefonoProveedor(int CodigoTelefonoProveedor) {
        this.CodigoTelefonoProveedor = CodigoTelefonoProveedor;
    }

    public String getNumero() {
        return Numero;
    }

    public void setNumero(String Numero) {
        this.Numero = Numero;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    public int getCodigoProveedor() {
        return CodigoProveedor;
    }

    public void setCodigoProveedor(int CodigoProveedor) {
        this.CodigoProveedor = CodigoProveedor;
    }
    
    public String toString(){
        return CodigoTelefonoProveedor+"";
    }
    
    
}
//escena.get