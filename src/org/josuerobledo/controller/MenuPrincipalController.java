package org.josuerobledo.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javax.swing.JOptionPane;
import org.josuerobledo.sistema.Principal;


public class MenuPrincipalController implements Initializable {
    
    
    private Principal escenarioPrincipal;
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    //************************************Eventos*******************************************
    @FXML
    private void VentanaCategoria(ActionEvent event) {
        escenarioPrincipal.Categoria();
    }
    
    @FXML
    private void VentanaMarcas(ActionEvent event){
        escenarioPrincipal.Marcas();
    }
    
    @FXML
    private void VentanaTallas(ActionEvent event){
        escenarioPrincipal.Tallas();
    }
    
    @FXML
    private void VentanaProductos(ActionEvent event){
        escenarioPrincipal.Productos();
    }
    
    @FXML
    private void VentanaCompras(ActionEvent event){
        escenarioPrincipal.Compras();
    }
    
    @FXML
    private void VentanaProveedores(ActionEvent event){
        escenarioPrincipal.Proveedores();
    }
    
    @FXML
    private void VentanaTelefonoProveedores(ActionEvent event){
        escenarioPrincipal.TelefonoProveedores();
    }
    
    @FXML
    private void CorreoTelefonoProveedores(ActionEvent event){
        escenarioPrincipal.CorreoProveedores();
    }
    
    @FXML
    private void VentanaClientes(ActionEvent event){
        escenarioPrincipal.Clientes();
    }
    
    @FXML
    private void VentanaCorreoClientes(ActionEvent event){
        escenarioPrincipal.CorreoClientes();
    }
    
    @FXML
    private void VentanaTelefonoClientes(ActionEvent event){
        escenarioPrincipal.TelefonoClientes();
    }
    
    @FXML
    private void VentanaFacturas(ActionEvent event){
        escenarioPrincipal.Facturas();
    }
    
    @FXML
    private void VentanaDetalleFacturas(ActionEvent event){
        escenarioPrincipal.DetalleFacturas();
    }
    
    @FXML
    private void VentanaDetalleCompras(ActionEvent event){
        escenarioPrincipal.DetalleCompras();
    }
    
    @FXML
    private void Versiones(ActionEvent event){
        JOptionPane.showMessageDialog(null, "**Version 1 de la fecha 1/05/2017\n \n" + "Errores:\n" +
        "Al seleccionar un campo vacio en la tabla ocurre un error\n " +
        "No se puede cambiar de datos al selecionar el combobox, " +
        "solo se puede al seleccionar la tabla \n \n "+
        "**Version 2 de la fecha 1/05/2017\n\n"+"Errores:\n"   +
        "La vista Compras tiene algunos errores con los procedimientos\n"+
        "Y falta agregar los eventos a los botones\n\n" +
        "**Version 4 de la fecha 09/05/2017\n\n"+"Mejoras: \n "+
        "-Ahora se puede usar el ComboBox para buscar en cualquier vista \n"+
        "-Tambien se pueden usar el teclado para desplazarse entre los datos de cualquier vista\n"+
        "-El Editar de Detalle Compras y Compras ahora si editan con los datos correctos(como costó)"
                
                
                , "Version", 1);
    }

    @FXML
    private void Creditos(ActionEvent event){
        JOptionPane.showMessageDialog(null, "Creditos:\n \n" + "Nombre: Josue Arturo Robledo Duque\n" +
        "Grado: PE5AM\n " +
        "Carne: 2014453 \n"+       
        "2 Bimestre\n" +
        "Profesor: Pedro Armas\n ", "Creditos", 1);
    }

}
