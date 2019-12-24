
package org.josuerobledo.bean;


public class Marcas {
    private int CodigoMarca;
    private String DescripcionMarcas;
    

    public Marcas() {
        
    }

    
    public Marcas(int CodigoMarca, String DescripcionMarcas) {
        this.CodigoMarca = CodigoMarca;
        this.DescripcionMarcas = DescripcionMarcas;
    }
    

    public int getCodigoMarca() {
        return CodigoMarca;
    }

    public void setCodigoMarca(int CodigoMarca) {
        this.CodigoMarca = CodigoMarca;
    }

    public String getDescripcionMarcas() {
        return DescripcionMarcas;
    }

    public void setDescripcionMarcas(String DescripcionMarcas) {
        this.DescripcionMarcas = DescripcionMarcas;
    }
    
    public String toString(){
        return getCodigoMarca()+" - "+getDescripcionMarcas();
    }
}
