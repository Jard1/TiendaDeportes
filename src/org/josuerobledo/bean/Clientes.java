
package org.josuerobledo.bean;


public class Clientes {
    private int CodigoCliente;
    private String Nombre;
    private String Nit;
    private String Direccion;

    public Clientes() {
        
    }

    public Clientes(int CodigoCliente, String Nombre, String Nit, String Direccion) {
        this.CodigoCliente = CodigoCliente;
        this.Nombre = Nombre;
        this.Nit = Nit;
        this.Direccion = Direccion;
    }

    public int getCodigoCliente() {
        return CodigoCliente;
    }

    public void setCodigoCliente(int CodigoCliente) {
        this.CodigoCliente = CodigoCliente;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getNit() {
        return Nit;
    }

    public void setNit(String Nit) {
        this.Nit = Nit;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String Direccion) {
        this.Direccion = Direccion;
    }
    
    public String toString(){
        return CodigoCliente+" - "+getNombre();
    }
}
