
package org.josuerobledo.bean;


public class Tallas {
    private int CodigoTalla;
    private String DescripcionTalla;

    public Tallas() {
        
    }

    
    public Tallas(int CodigoTalla, String DescripcionTalla) {
        this.CodigoTalla = CodigoTalla;
        this.DescripcionTalla = DescripcionTalla;
    }

    public int getCodigoTalla() {
        return CodigoTalla;
    }

    public void setCodigoTalla(int CodigoTalla) {
        this.CodigoTalla = CodigoTalla;
    }

    public String getDescripcionTalla() {
        return DescripcionTalla;
    }

    public void setDescripcionTalla(String DescripcionTalla) {
        this.DescripcionTalla = DescripcionTalla;
    }
    
    public String toString(){
        return getCodigoTalla()+" - "+getDescripcionTalla();
    }
    
}
