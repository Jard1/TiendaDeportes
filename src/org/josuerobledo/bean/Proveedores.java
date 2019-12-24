
package org.josuerobledo.bean;


public class Proveedores {
    private int CodigoProveedor;
    private String Direccion;
    private String ContactoPrincipal;
    private String RazonSocial;
    private String PaginaWeb;
    private String Nit;

    public Proveedores() {
    }

    public Proveedores(int CodigoProveedor, String Direccion, String ContactoPrincipal, String RazonSocial, String PaginaWeb, String Nit) {
        this.CodigoProveedor = CodigoProveedor;
        this.Direccion = Direccion;
        this.ContactoPrincipal = ContactoPrincipal;
        this.RazonSocial = RazonSocial;
        this.PaginaWeb = PaginaWeb;
        this.Nit = Nit;
    }

    public int getCodigoProveedor() {
        return CodigoProveedor;
    }

    public void setCodigoProveedor(int CodigoProveedor) {
        this.CodigoProveedor = CodigoProveedor;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String Direccion) {
        this.Direccion = Direccion;
    }

    public String getContactoPrincipal() {
        return ContactoPrincipal;
    }

    public void setContactoPrincipal(String ContactoPrincipal) {
        this.ContactoPrincipal = ContactoPrincipal;
    }

    public String getRazonSocial() {
        return RazonSocial;
    }

    public void setRazonSocial(String RazonSocial) {
        this.RazonSocial = RazonSocial;
    }

    public String getPaginaWeb() {
        return PaginaWeb;
    }

    public void setPaginaWeb(String PaginaWeb) {
        this.PaginaWeb = PaginaWeb;
    }

    public String getNit() {
        return Nit;
    }

    public void setNit(String Nit) {
        this.Nit = Nit;
    }
    
    
    
    public String toString(){
        return getCodigoProveedor() + " - "+getRazonSocial();
    }
            
}
