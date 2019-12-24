
package org.josuerobledo.bean;


public class Categoria {
    private int CodigoCategoria;
    private String DescripcionCategoria;
    
    public Categoria(){
        
    }
    
    public Categoria(int CodigoCategoria, String DescripcionCategoria) {
        this.CodigoCategoria = CodigoCategoria;
        this.DescripcionCategoria = DescripcionCategoria;
    }
    
    
    public int getCodigoCategoria() {
        return CodigoCategoria;
    }

    public void setCodigoCategoria(int CodigoCategoria) {
        this.CodigoCategoria = CodigoCategoria;
    }

    public String getDescripcionCategoria() {
        return DescripcionCategoria;
    }

    public void setDescripcionCategoria(String DescripcionCategoria) {
        this.DescripcionCategoria = DescripcionCategoria;
    }
    
    public String toString(){
        return getCodigoCategoria()+" - "+getDescripcionCategoria();
    }

    
    
    
}
