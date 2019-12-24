
package org.josuerobledo.bean;


public class CorreoClientes {
    private int CodigoEmailCliente;
    private String Email;
    private String Descripcion;
    private int CodigoCliente;

    public CorreoClientes() {
        
    }

    public CorreoClientes(int CodigoEmailCliente, String Email, String Descripcion, int CodigoCliente) {
        this.CodigoEmailCliente = CodigoEmailCliente;
        this.Email = Email;
        this.Descripcion = Descripcion;
        this.CodigoCliente = CodigoCliente;
    }

    public int getCodigoEmailCliente() {
        return CodigoEmailCliente;
    }

    public void setCodigoEmailCliente(int CodigoEmailCliente) {
        this.CodigoEmailCliente = CodigoEmailCliente;
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

    public int getCodigoCliente() {
        return CodigoCliente;
    }

    public void setCodigoCliente(int CodigoCliente) {
        this.CodigoCliente = CodigoCliente;
    }
    
    public String toString(){
        return getCodigoEmailCliente()+"";
    }
}
