
package org.josuerobledo.sistema;

import java.io.IOException;
import java.io.InputStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.josuerobledo.controller.CategoriasController;
import org.josuerobledo.controller.ClientesController;
import org.josuerobledo.controller.ComprasController;
import org.josuerobledo.controller.CorreoClienteController;
import org.josuerobledo.controller.CorreoProveedoresController;
import org.josuerobledo.controller.DetalleComprasController;
import org.josuerobledo.controller.DetalleFacturaController;
import org.josuerobledo.controller.FacturasController;
import org.josuerobledo.controller.MenuPrincipalController;
import org.josuerobledo.controller.MarcasController;
import org.josuerobledo.controller.ProductosController;
import org.josuerobledo.controller.ProveedoresController;
import org.josuerobledo.controller.TallasController;
import org.josuerobledo.controller.TelefonoClientesController;
import org.josuerobledo.controller.TelefonoProveedoresController;



public class Principal extends Application {
    
    private final String UbicacionVista = "/org/josuerobledo/view/"; //En este String se va a guardar La ubicacion de la carpeta vista 
    private Stage escenarioPrincipal;                                     
    private Scene escena;
    
    
    
    public static void main(String[] args) {
        launch(args);
    }
    
   @Override
    public void start(Stage escenarioPrincipal){
        this.escenarioPrincipal= escenarioPrincipal;
        escenarioPrincipal.setTitle("Menu Principal");// EL titulo de la ventana
        menuPrincipal();
        escenarioPrincipal.show();
    }
    
    //********************************************************** Escenarios************************************************************
    
    public void menuPrincipal(){ 
        try {
            MenuPrincipalController menuPrincipal =(MenuPrincipalController) cambiarEscena("MenuPrincipal.fxml", 658, 482);
            menuPrincipal.setEscenarioPrincipal(this);
            escenarioPrincipal.setTitle("Menu Principal"); // EL titulo de la ventana
            escenarioPrincipal.setResizable(false); //eso es para que no se pueda maximizar la ventana
        } catch(Exception e){
            e.printStackTrace(); 
        }
        
    }
    
    public void Categoria(){
        try{
            CategoriasController Categoria = (CategoriasController) cambiarEscena("Categorias.fxml",573,437);
            Categoria.setEscenarioPrincipal(this);
            escenarioPrincipal.setTitle("Ventana Categorias"); // EL titulo de la ventana
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void Marcas(){
        try{
            MarcasController Marcas = (MarcasController) cambiarEscena("Marcas.fxml",600,434);
            Marcas.setEscenarioPrincipal(this);
            escenarioPrincipal.setTitle("Ventana Marcas"); // EL titulo de la ventana
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void Tallas(){
        try{
            TallasController Tallas = (TallasController) cambiarEscena("Tallas.fxml",600,443);
            Tallas.setEscenarioPrincipal(this);
            escenarioPrincipal.setTitle("Ventana Tallas"); // EL titulo de la ventana
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void Productos(){
        try{
            ProductosController Productos = (ProductosController) cambiarEscena("Producto.fxml",813,607);
            Productos.setEscenarioPrincipal(this);
            escenarioPrincipal.setTitle("Ventana Producto"); // EL titulo de la ventana
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void Compras(){
        try{
            ComprasController Compras = (ComprasController) cambiarEscena("Compras.fxml",603,549);
            Compras.setEscenarioPrincipal(this);
            escenarioPrincipal.setTitle("Ventana Compras");
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    public void Proveedores(){
        try{
            ProveedoresController Proveedores = (ProveedoresController) cambiarEscena("Proveedores.fxml",813,542);
            Proveedores.setEscenarioPrincipal(this);
            escenarioPrincipal.setTitle("Ventana Proveedores");
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    public void TelefonoProveedores(){
        try{
            TelefonoProveedoresController TelefonoProveedores = (TelefonoProveedoresController) cambiarEscena("TelefonoProveedores.fxml",507,515);
            TelefonoProveedores.setEscenarioPrincipal(this);
            escenarioPrincipal.setTitle("Ventana Telefonos Proveedores");
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    public void CorreoProveedores(){
        try{
            CorreoProveedoresController CorreoProveedores = (CorreoProveedoresController) cambiarEscena("CorreoProveedores.fxml",516,532);
            CorreoProveedores.setEscenarioPrincipal(this);
            escenarioPrincipal.setTitle("Ventana Correos Proveedores");
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    public void Clientes(){
        try{
            ClientesController clientesController = (ClientesController) cambiarEscena("Clientes.fxml",541,573);
            clientesController.setEscenarioPrincipal(this);
            escenarioPrincipal.setTitle("Ventana Clientes");
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    public void TelefonoClientes(){
        try{
            TelefonoClientesController telefonoClientesController = (TelefonoClientesController) cambiarEscena("TelefonoClientes.fxml",514,543);
            telefonoClientesController.setEscenarioPrincipal(this);
            escenarioPrincipal.setTitle("Ventana Clientes");
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    public void CorreoClientes(){
        try{
            CorreoClienteController correoClienteController = (CorreoClienteController) cambiarEscena("CorreoCliente.fxml",540,541);
            correoClienteController.setEscenarioPrincipal(this);
            escenarioPrincipal.setTitle("Ventana Correos Clientes");
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    public void Facturas(){
        try{
            FacturasController facturasController = (FacturasController) cambiarEscena("Facturas.fxml",750,542);
            facturasController.setEscenarioPrincipal(this);
            escenarioPrincipal.setTitle("Ventana Facturas");
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    public void DetalleFacturas(){
        try{
            DetalleFacturaController detalleFacturaController = (DetalleFacturaController) cambiarEscena("DetalleFactura.fxml",600,552);
            detalleFacturaController.setEscenarioPrincipal(this);
            escenarioPrincipal.setTitle("Ventana Detalle Facturas");
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    public void DetalleCompras(){
        try{
            DetalleComprasController detalleComprasController = (DetalleComprasController) cambiarEscena("DetalleCompras.fxml",634,562);
            detalleComprasController.setEscenarioPrincipal(this);
            escenarioPrincipal.setTitle("Ventana Detalle Compras");
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
     //El metodo de cambiar escenas
    public Initializable cambiarEscena(String Archivofxml, int ancho, int alto) throws Exception {
        Initializable resultado = null;
        
        FXMLLoader cargadorFXML = new FXMLLoader();
        InputStream archivo = Principal.class.getResourceAsStream(UbicacionVista +Archivofxml);
        cargadorFXML.setBuilderFactory(new JavaFXBuilderFactory());
        cargadorFXML.setLocation(Principal.class.getResource(UbicacionVista + Archivofxml));
        escena = new Scene((AnchorPane)cargadorFXML.load(archivo), ancho, alto);
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.sizeToScene();
        resultado=(Initializable) cargadorFXML.getController();
        return resultado;
    } 
    

   
   
    
}
