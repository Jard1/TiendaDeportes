
package org.josuerobledo.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javax.swing.JOptionPane;
import org.josuerobledo.bean.Proveedores;
import org.josuerobledo.db.ConexionSql;
import org.josuerobledo.sistema.Principal;


public class ProveedoresController implements Initializable {

    @FXML private TableColumn colDireccion;
    @FXML private Button btnNuevo;
    @FXML protected TableView tblProveedores;
    @FXML private TableColumn colPagina;
    @FXML private TableColumn colNit;
    @FXML private ComboBox cmbCodigo;
    @FXML private Button btnEditar;
    @FXML private TextField txtNit;
    @FXML private Button btnCorreo;
    @FXML private TableColumn colCodigoProveedores;
    @FXML private TableColumn colContacto;
    @FXML protected TableColumn colRazon;
    @FXML private TextField txtPagina;
    @FXML private Button btnTelefonos;
    @FXML private Button btnEliminar;
    @FXML private TextField txtRazon;
    @FXML private ImageView imaInicio;
    @FXML private TextField txtDireccion;
    @FXML private TextField txtContacto;
    @FXML private Button btnReporte;
    private byte contNuevo;
    private byte contEditar;
    private Principal escenarioPrincipal;
    private ObservableList <Proveedores> ListaProveedores;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         /*Como este metodo se ejecuta primero, entonces en un principio
        estos botones estan deshabilitados*/
        txtDireccion.setDisable(true);
        txtContacto.setDisable(true);
        btnTelefonos.setDisable(true);
        txtRazon.setDisable(true);
        txtPagina.setDisable(true);
        txtNit.setDisable(true);
        btnCorreo.setDisable(true);     
        
        cargarDatos(); //ejecuta el metodo de cargar datos        
    }  
    
    //metodo para cargar los datos a la tabla
    private void cargarDatos(){ 
        tblProveedores.setItems(getProveedores()); //agrega los datos a la tabla y ejecuta la observable list
        colCodigoProveedores.setCellValueFactory(new PropertyValueFactory<Proveedores, Integer>("CodigoProveedor"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("Direccion") );
        colContacto.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("ContactoPrincipal") );
        colRazon.setCellValueFactory(new PropertyValueFactory<Proveedores, String> ("RazonSocial") );
        colPagina.setCellValueFactory(new PropertyValueFactory <Proveedores, String>("PaginaWeb") );
        colNit.setCellValueFactory(new PropertyValueFactory <Proveedores, String>("Nit"));
        
        
        
        cmbCodigo.setItems(getProveedores()); //para agregar los items al combobox
    }
    
    //para guardar los elementos en un array 
    protected ObservableList<Proveedores> getProveedores(){
        ArrayList<Proveedores> lista = new ArrayList<Proveedores>();
        ResultSet resultado;
        try {
            PreparedStatement procedimiento = ConexionSql.getInstancia().getConeccion().prepareCall("{call VerProveedores}");
            resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Proveedores(resultado.getInt("CodigoProveedor"), resultado.getString("Direccion"),
                resultado.getString("ContactoPrincipal"), resultado.getString("RazonSocial"), resultado.getString("PaginaWeb"),
                resultado.getString("Nit") ) );
            }
            
        } catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Codigo del error: "+e.getErrorCode()+"\n\n"+"Detalles: \n"+e.getMessage(), "Se ha Producido un Error", 2);
            e.printStackTrace();
            
        }
        
        return ListaProveedores = FXCollections.observableArrayList(lista);
    }
    
    //getters y setter de la variable de tipo Principal
    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    // Metodo para agregar datos
    private void AgregarDatos(){
        Proveedores registro = new Proveedores();
        registro.setDireccion(txtDireccion.getText() );
        registro.setContactoPrincipal(txtContacto.getText() );
        registro.setRazonSocial(txtRazon.getText() );
        registro.setPaginaWeb(txtPagina.getText() );
        registro.setNit(txtNit.getText());
        
        try{
            PreparedStatement procedimiento = ConexionSql.getInstancia().getConeccion().prepareCall("{call SP_InsertarProveedores(?,?,?,?,?)}");
            procedimiento.setString (1,registro.getDireccion() );
            procedimiento.setString(2,registro.getContactoPrincipal() );
            procedimiento.setString(3,registro.getRazonSocial() );
            procedimiento.setString(4,registro.getPaginaWeb() );
            procedimiento.setString(5,registro.getNit());
            procedimiento.execute();
            ListaProveedores.add(registro);
             
        }catch(SQLException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Codigo del error: "+e.getErrorCode()+"\n\n"+"Detalles: \n"+e.getMessage(), "Se ha Producido un Error", 2);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"¡Error en los datos introducidos! \n Ingrese los datos correctos y pruebe otra vez.","Error",2);
        }
    }
    
    
     private void Editar(){
        try{
            PreparedStatement procedimiento = ConexionSql.getInstancia().getConeccion().prepareCall("{call SP_ActualizarProveedores (?,?,?,?,?,?)}");
            Proveedores registro = (Proveedores)tblProveedores.getSelectionModel().getSelectedItem(); 
            registro.setDireccion(txtDireccion.getText()); 
            registro.setContactoPrincipal(txtContacto.getText() );
            registro.setRazonSocial(txtRazon.getText() );
            registro.setPaginaWeb(txtPagina.getText() );
            registro.setNit(txtNit.getText() );
            
            registro.setCodigoProveedor(((Proveedores)cmbCodigo.getSelectionModel().getSelectedItem()).getCodigoProveedor());
            
            procedimiento.setInt (1,registro.getCodigoProveedor() ); 
            procedimiento.setString (2, registro.getDireccion() ); 
            procedimiento.setString(3, registro.getContactoPrincipal() );
            procedimiento.setString(4,registro.getRazonSocial() );
            procedimiento.setString(5,registro.getPaginaWeb());
            procedimiento.setString(6,registro.getNit() );
            procedimiento.execute(); 
        }catch(SQLException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Codigo del error: "+e.getErrorCode()+"\n\n"+"Detalles: \n"+e.getMessage(), "Se ha Producido un Error", 2);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"¡Error en los datos introducidos! \n Ingrese los datos correctos y pruebe otra vez.","Error",2);
        }
        
    }
    
      
    //este metodo sirve para hacer la busqueda del dato segun el codigo seleccionado en la accion seleccionar elementos
    
     private Proveedores buscarProveedores(int CodigoProveedor){
        Proveedores resultado= null;
        try {
            PreparedStatement procedimiento = ConexionSql.getInstancia().getConeccion().prepareCall("{call SP_BuscarProveedor(?)}");
            procedimiento.setInt(1, CodigoProveedor);
            ResultSet registro= procedimiento.executeQuery();
            while (registro.next()){
                resultado = new Proveedores(registro.getInt("CodigoProveedor"), registro.getString("Direccion"),
                registro.getString("ContactoPrincipal"), registro.getString("RazonSocial"), registro.getString("PaginaWeb"),
                registro.getString("Nit")  );
            }
                             
        }catch(SQLException e){
            e.printStackTrace();  
            JOptionPane.showMessageDialog(null,"Codigo del error: "+e.getErrorCode()+"\n\n"+"Detalles: \n"+e.getMessage(), "Se ha Producido un Error", 2);
        }
        return resultado;
    }
     
       //este metodo sirve para poner todo en un estado inicial, es decir cuando se ejecuta la ventana por primera vez
    private void EstadoInicial(){
        btnEditar.setDisable(false);
        btnNuevo.setDisable(false);
        btnEliminar.setDisable(false);
        btnReporte.setDisable(false);
        cmbCodigo.setDisable(false);
        
        txtDireccion.setDisable(true);
        txtContacto.setDisable(true);
        txtRazon.setDisable(true);
        txtPagina.setDisable(true);
        txtNit.setDisable(true);
        btnTelefonos.setDisable(true);
        btnCorreo.setDisable(true);

        
        btnNuevo.setText("Nuevo");
        btnEliminar.setText("Eliminar");
        btnEditar.setText("Editar");
        btnReporte.setText("Reporte");
        
        txtDireccion.setText("");
        txtContacto.setText("");
        txtRazon.setText("");
        txtPagina.setText("");
        txtNit.setText("");
                
        cmbCodigo.setValue(null);
    }
    
    //*********************************Eventos************************************
    
    @FXML
    private void BotonNuevo(ActionEvent event) {
        contNuevo++;
        //al ejecutarse este metodo los botones se habilitan 
        if(contNuevo==1){
            
            btnEditar.setDisable(true);
            cmbCodigo.setDisable(true);
            btnReporte.setDisable(true);
            
            txtDireccion.setDisable(false);
            txtContacto.setDisable(false);
            txtRazon.setDisable(false);
            txtPagina.setDisable(false);
            txtNit.setDisable(false);
            
            txtDireccion.setText("");
            txtContacto.setText("");
            txtRazon.setText("");
            txtPagina.setText("");
            txtNit.setText("");
            cmbCodigo.setValue(null); //Inserte para resetear el combobox
            
            btnNuevo.setText("Guardar");
            btnEliminar.setText("Cancelar");
            btnEditar.setText("Editar");
            
        }
        else{
            //la accion de guardar
            contNuevo=0;
            
            AgregarDatos();
            cargarDatos();
            EstadoInicial();//importante que el estado inicial vaya depues de agregar los datos
        }
    }
    
    @FXML
    private void BotonEliminar(ActionEvent event){
        /*si el contador vale uno quiere decir que el usuario apacho el boton nuevo por lo tanto el boton elimnar seria el cancelar
        entonces se ejecuta la accion de cancelar, de lo contrario el usuario no ha precionado nuevo y el boton tiene que hacer la accion 
        de eliminar
        */
        if(contNuevo==1){//accion de cancelar
            EstadoInicial();
            contNuevo=0;
        }
        else{ //la accion de eliminar
            if(tblProveedores.getSelectionModel().getSelectedItem() != null){ 
                try{
                    //hay que preguntar si esta seguro de borrarlo, por seguridad
                    int confirmar = JOptionPane.showConfirmDialog(null,"¿En realidad quiere borrar este dato?", "Advertencia",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                    if(confirmar==JOptionPane.YES_OPTION){
                        PreparedStatement procedimiento = ConexionSql.getInstancia().getConeccion().prepareCall("{call SP_EliminarProveedores (?)}");
                        procedimiento.setInt(1, ((Proveedores)tblProveedores.getSelectionModel().getSelectedItem()).getCodigoProveedor());
                        procedimiento.execute(); 
                        cargarDatos();
                        EstadoInicial();
                    }    
                    
                }catch(SQLException e){
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null,"Codigo del error: "+e.getErrorCode()+"\n\n"+"Detalles: \n"+e.getMessage(), "Se ha Producido un Error", 2);
                }
            }  
            else{
                JOptionPane.showMessageDialog(null,"Tiene que seleccionar un elemento para eliminarlo", "ERROR",2);
                
            }
        }
    }
    
    @FXML
    private void BotonEditar(ActionEvent event){
        contEditar++;
        if(contEditar==1){//esto se ejecuta cuando preciona editar la primera vez
            if(tblProveedores.getSelectionModel().getSelectedItem() != null){
                btnNuevo.setDisable(true);
                btnEliminar.setDisable(true);
                btnTelefonos.setDisable(true);
                btnCorreo.setDisable(true);
                
                cmbCodigo.setDisable(true);
                btnEditar.setText("Actualizar");
                btnReporte.setText("Cancelar");
            }
            else{
                JOptionPane.showMessageDialog(null,"Tiene que seleccionar un elemento para editarlo", "ERROR",2);
                contEditar=0;//sin esto nunca va entrar a la condicion y el valor del contador sera el de las veces que precione editar sin seleccionar nada
            }
        }
        else{ //esta seria la accion de actualizar o cuando el usuario preciona el boton la segunda vez
            Editar();
            EstadoInicial();
            cargarDatos();
            contEditar=0;
        }
        
        
    }
    
    @FXML
    private void BotonReporte(ActionEvent event){
        if(contEditar==1){//si ContEditar vale uno quiere decir que el boton de reporte tiene que hacer la accion de cancelar
            EstadoInicial();
            contEditar=0;
        }
        else{
            JOptionPane.showMessageDialog(null, "En construccion... xdxd");//aqui va lo de reporte
        }
        
    }
    
    
    @FXML
    private void RegresarInicio(MouseEvent event) {
        escenarioPrincipal.menuPrincipal();
    }
    
    @FXML
    private void seleccionarElementos(){
        
        if(tblProveedores.getSelectionModel().getSelectedItem()!=null){
            cmbCodigo.setDisable(false);
            txtDireccion.setDisable(false);
            txtContacto.setDisable(false);
            txtRazon.setDisable(false);
            txtPagina.setDisable(false);
            txtNit.setDisable(false);
            btnTelefonos.setDisable(false);
            btnCorreo.setDisable(false);

            cmbCodigo.getSelectionModel().select(buscarProveedores(((Proveedores)tblProveedores.getSelectionModel().getSelectedItem()).getCodigoProveedor()));
            txtDireccion.setText(((Proveedores)tblProveedores.getSelectionModel().getSelectedItem()).getDireccion());
            txtContacto.setText(((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getContactoPrincipal());
            txtRazon.setText(((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getRazonSocial());
            txtPagina.setText(((Proveedores)tblProveedores.getSelectionModel().getSelectedItem()).getPaginaWeb());
            txtNit.setText(((Proveedores)tblProveedores.getSelectionModel().getSelectedItem()).getNit());
        }
        
    } 
    
    @FXML
    private void seleccionarCombobox(){

        /*solo junte esto:
            explicacion en los controladores de Categorias, Marcas o Tallas
        */
        

        if(cmbCodigo.getSelectionModel().getSelectedItem()!=null){
            
            txtDireccion.setDisable(false);
            txtContacto.setDisable(false);
            txtPagina.setDisable(false);
            txtNit.setDisable(false);
            txtRazon.setDisable(false);
            
            txtContacto.setText( buscarProveedores( ((Proveedores)cmbCodigo.getSelectionModel().getSelectedItem()).getCodigoProveedor() ).getContactoPrincipal() );
            txtDireccion.setText(buscarProveedores( ((Proveedores)cmbCodigo.getSelectionModel().getSelectedItem()).getCodigoProveedor() ).getDireccion() );
            txtPagina.setText(buscarProveedores( ((Proveedores)cmbCodigo.getSelectionModel().getSelectedItem()).getCodigoProveedor() ).getPaginaWeb() );
            txtNit.setText(buscarProveedores( ((Proveedores)cmbCodigo.getSelectionModel().getSelectedItem()).getCodigoProveedor() ).getNit() );
            txtRazon.setText(buscarProveedores( ((Proveedores)cmbCodigo.getSelectionModel().getSelectedItem()).getCodigoProveedor() ).getRazonSocial() );
        }
        
    }
    
    @FXML
    private void TelefonosProveedores(ActionEvent event){
        escenarioPrincipal.TelefonoProveedores();
        
    }
    
    @FXML
    private void CorreosProveedores(ActionEvent event){
        escenarioPrincipal.CorreoProveedores();
    }
    
}
