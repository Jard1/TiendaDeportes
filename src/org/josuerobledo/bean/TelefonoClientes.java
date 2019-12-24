
package org.josuerobledo.bean;

public class TelefonoClientes {
    private int CodigoTelefonoCliente;
    private String Descripcion;
    private String Numero;
    private int CodigoCliente;

    public TelefonoClientes() {
    }

    public TelefonoClientes(int CodigoTelefonoCliente, String Descripcion, String Numero, int CodigoCliente) {
        this.CodigoTelefonoCliente = CodigoTelefonoCliente;
        this.Descripcion = Descripcion;
        this.Numero = Numero;
        this.CodigoCliente = CodigoCliente;
    }

    public int getCodigoTelefonoCliente() {
        return CodigoTelefonoCliente;
    }

    public void setCodigoTelefonoCliente(int CodigoTelefonoCliente) {
        this.CodigoTelefonoCliente = CodigoTelefonoCliente;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    public String getNumero() {
        return Numero;
    }

    public void setNumero(String Numero) {
        this.Numero = Numero;
    }

    public int getCodigoCliente() {
        return CodigoCliente;
    }

    public void setCodigoCliente(int CodigoCliente) {
        this.CodigoCliente = CodigoCliente;
    }
    
    public String toString(){
        return getCodigoTelefonoCliente() + "";
    }
}
