
package org.josuerobledo.bean;


public class CorreoProveedores {
    
    private int CodigoEmailProveedor;
    private String Email;
    private String Descripcion;
    private int CodigoProveedor;

    public CorreoProveedores() {
        
    }

    public CorreoProveedores(int CodigoEmailProveedor, String Email, String Descripcion, int CodigoProveedor) {
        this.CodigoEmailProveedor = CodigoEmailProveedor;
        this.Email = Email;
        this.Descripcion = Descripcion;
        this.CodigoProveedor = CodigoProveedor;
    }

    public int getCodigoEmailProveedor() {
        return CodigoEmailProveedor;
    }

    public void setCodigoEmailProveedor(int CodigoEmailProveedor) {
        this.CodigoEmailProveedor = CodigoEmailProveedor;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
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
        return getCodigoEmailProveedor()+"";
    }
    
}
