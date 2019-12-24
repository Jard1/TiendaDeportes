
package org.josuerobledo.bean;


public class Productos {
    
    private int CodigoProducto;
    private String DescripcionProducto;
    private int Existencia;
    private double PrecioUnitario;
    private double PrecioPorDocena;
    private double PrecioPorMayor;
    private int CodigoCategoria;
    private int CodigoMarca;
    private int CodigoTalla;
    private int Ganancia;

    public Productos() {
        
    }

    public Productos(int CodigoProducto, String DescripcionProducto, int Existencia, double PrecioUnitario, double PrecioPorDocena, double PrecioPorMayor, int CodigoCategoria, int CodigoMarca, int CodigoTalla, int Ganancia) {
        this.CodigoProducto = CodigoProducto;
        this.DescripcionProducto = DescripcionProducto;
        this.Existencia = Existencia;
        this.PrecioUnitario = PrecioUnitario;
        this.PrecioPorDocena = PrecioPorDocena;
        this.PrecioPorMayor = PrecioPorMayor;
        this.CodigoCategoria = CodigoCategoria;
        this.CodigoMarca = CodigoMarca;
        this.CodigoTalla = CodigoTalla;
        this.Ganancia = Ganancia;
    }

    

    public int getCodigoProducto() {
        return CodigoProducto;
    }

    public void setCodigoProducto(int CodigoProducto) {
        this.CodigoProducto = CodigoProducto;
    }

    public String getDescripcionProducto() {
        return DescripcionProducto;
    }

    public void setDescripcionProducto(String DescripcionProducto) {
        this.DescripcionProducto = DescripcionProducto;
    }

    public int getExistencia() {
        return Existencia;
    }

    public void setExistencia(int Existencia) {
        this.Existencia = Existencia;
    }

    public double getPrecioUnitario() {
        return PrecioUnitario;
    }

    public void setPrecioUnitario(double PrecioUnitario) {
        this.PrecioUnitario = PrecioUnitario;
    }

    public double getPrecioPorDocena() {
        return PrecioPorDocena;
    }

    public void setPrecioPorDocena(double PrecioPorDocena) {
        this.PrecioPorDocena = PrecioPorDocena;
    }

    public double getPrecioPorMayor() {
        return PrecioPorMayor;
    }

    public void setPrecioPorMayor(double PrecioPorMayor) {
        this.PrecioPorMayor = PrecioPorMayor;
    }

    public int getCodigoCategoria() {
        return CodigoCategoria;
    }

    public void setCodigoCategoria(int CodigoCategoria) {
        this.CodigoCategoria = CodigoCategoria;
    }

    public int getCodigoMarca() {
        return CodigoMarca;
    }

    public void setCodigoMarca(int CodigoMarca) {
        this.CodigoMarca = CodigoMarca;
    }

    public int getCodigoTalla() {
        return CodigoTalla;
    }

    public void setCodigoTalla(int CodigoTalla) {
        this.CodigoTalla = CodigoTalla;
    }

    public int getGanancia() {
        return Ganancia;
    }

    public void setGanancia(int Ganancia) {
        this.Ganancia = Ganancia;
    }
    
    
    public String toString(){
        return getCodigoProducto()+" - "+getDescripcionProducto();
    }
    
}
