
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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javax.swing.JOptionPane;
import org.josuerobledo.bean.CorreoProveedores;
import org.josuerobledo.bean.Proveedores;
import org.josuerobledo.db.ConexionSql;
import org.josuerobledo.sistema.Principal;


public class CorreoProveedoresController implements Initializable {

    
    @FXML private ComboBox cmbProveedor;
    @FXML private TableColumn colCodigo;
    @FXML private Button btnNuevo;
    @FXML private ComboBox cmbCodigo;
    @FXML private TextField txtCorreo;
    @FXML private Button btnEditar;
    @FXML private TableColumn colProveedor;
    @FXML private Button btnRegresar;
    @FXML private TableColumn colDescripcion;
    @FXML private Button btnEliminar;
    @FXML private TextField txtDescripcion;
    @FXML private ImageView imaInicio;
    @FXML private TableView tblCorreoProveedores;
    @FXML private TableColumn colCorreo;
    @FXML private ObservableList<CorreoProveedores> ListaCorreoProveedores;
    
    private Principal escenarioPrincipal;
    private byte contNuevo;
    private byte contEditar;
    
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        cmbCodigo.setDisable(true);
        txtCorreo.setDisable(true);
        txtDescripcion.setDisable(true);
        cmbProveedor.setDisable(true);
        
        cargarDatos();
    }   
    
    private void cargarDatos(){ 
        tblCorreoProveedores.setItems(getCorreoProveedores()); //agrega los datos a la tabla y ejecuta la observable list
        colCodigo.setCellValueFactory(new PropertyValueFactory<CorreoProveedores, Integer>("CodigoEmailProveedor"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<CorreoProveedores, String>("Descripcion"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<CorreoProveedores, String>("Email"));
        colProveedor.setCellValueFactory(new PropertyValueFactory<CorreoProveedores, Double>("CodigoProveedor"));
        
        
        cmbCodigo.setItems(getCorreoProveedores()); //para agregar los items al combobox
        
        //para agregar los datos de los proveedores
        ProveedoresController proveedoresController = new ProveedoresController();
        cmbProveedor.setItems(proveedoresController.getProveedores() );
        
        if(proveedoresController.getProveedores().size()==0){
            JOptionPane.showMessageDialog(null,"¡Primero ingrese un Proveedor!","Error al ingresar a Correo Proveedores",2); 
        }
    }
        
    
    private ObservableList<CorreoProveedores> getCorreoProveedores(){
        ArrayList<CorreoProveedores> lista = new ArrayList<CorreoProveedores>();
        try {
            PreparedStatement procedimiento = ConexionSql.getInstancia().getConeccion().prepareCall("{call VerEmailProveedores}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new CorreoProveedores(resultado.getInt("CodigoEmailProveedor"), resultado.getString("Email"),
                resultado.getString("Descripcion"),resultado.getInt("CodigoProveedor") ) );    
            }
            
        } catch(SQLException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Codigo del error: "+e.getErrorCode()+"\n\n"+"Detalles: \n"+e.getMessage(), "Se ha Producido un Error", 2);
            
        }
        
        return ListaCorreoProveedores = FXCollections.observableArrayList(lista);
    }
        

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    // Metodo para agregar datos
    private void AgregarDatos(){
        CorreoProveedores registro = new CorreoProveedores();
        
        registro.setDescripcion(txtDescripcion.getText() );
        registro.setEmail(txtCorreo.getText() );
        registro.setCodigoProveedor(((Proveedores)cmbProveedor.getSelectionModel().getSelectedItem()).getCodigoProveedor());
        
        try{
            PreparedStatement procedimiento = ConexionSql.getInstancia().getConeccion().prepareCall("{call SP_InsertarEmailProveedores(?,?,?)}");
            procedimiento.setString (1,registro.getDescripcion() );
            procedimiento.setString(2,registro.getEmail() );
            procedimiento.setInt(3,registro.getCodigoProveedor() );
            
            procedimiento.execute();

            ListaCorreoProveedores.add(registro);
             
        }catch(SQLException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Codigo del error: "+e.getErrorCode()+"\n\n"+"Detalles: \n"+e.getMessage(), "Se ha Producido un Error", 2);
        }catch(Exception e){
             JOptionPane.showMessageDialog(null,"¡Error en los datos introducidos! \n Ingrese los datos correctos y pruebe otra vez.","Error",2);
        }
    }
    
        //Metodo para editar
    private void Editar(){
        try{
            PreparedStatement procedimiento = ConexionSql.getInstancia().getConeccion().prepareCall("{call SP_ActualizarEmailProveedores (?,?,?,?)}");
            CorreoProveedores registro = (CorreoProveedores)tblCorreoProveedores.getSelectionModel().getSelectedItem(); 
           
            
            registro.setDescripcion(txtDescripcion.getText() );
            registro.setEmail(txtCorreo.getText() );
            registro.setCodigoProveedor(((Proveedores)cmbProveedor.getSelectionModel().getSelectedItem()).getCodigoProveedor());
            
            
            registro.setCodigoEmailProveedor(((CorreoProveedores)cmbCodigo.getSelectionModel().getSelectedItem()).getCodigoEmailProveedor() );
            
            procedimiento.setInt (1,registro.getCodigoEmailProveedor() ); 
            procedimiento.setString (2, registro.getDescripcion() ); 
            procedimiento.setString(3,registro.getEmail() );
            procedimiento.setInt(4,registro.getCodigoProveedor() );
            
            procedimiento.execute(); 
            
        }catch(SQLException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Codigo del error: "+e.getErrorCode()+"\n\n"+"Detalles: \n"+e.getMessage(), "Se ha Producido un Error", 2);
        }catch(Exception e){
             JOptionPane.showMessageDialog(null,"¡Error en los datos introducidos! \n Ingrese los datos correctos y pruebe otra vez.","Error",2);
        }
        
    }
    
    private CorreoProveedores buscarCorreoProveedores(int CodigoEmailProveedor){
        CorreoProveedores resultado= null;
        try {
            PreparedStatement procedimiento = ConexionSql.getInstancia().getConeccion().prepareCall("{call SP_BuscarEmailProveedores(?)}");
            procedimiento.setInt(1, CodigoEmailProveedor);
            ResultSet registro= procedimiento.executeQuery();
            while (registro.next()){
                resultado = new CorreoProveedores(registro.getInt("CodigoEmailProveedor"), registro.getString("Email"), registro.getString("Descripcion"),
                registro.getInt("CodigoProveedor") );
            }
                             
        }catch(SQLException e){
            e.printStackTrace();    
            JOptionPane.showMessageDialog(null,"Codigo del error: "+e.getErrorCode()+"\n\n"+"Detalles: \n"+e.getMessage(), "Se ha Producido un Error", 2);
        }
        return resultado;
    }
    
        private void EstadoInicial(){
        btnEditar.setDisable(false);
        btnNuevo.setDisable(false);
        btnEliminar.setDisable(false);
        btnRegresar.setDisable(false);
        
        cmbCodigo.setDisable(true);
        cmbProveedor.setDisable(true);
        txtCorreo.setDisable(true);
        txtDescripcion.setDisable(true);

        btnNuevo.setText("Nuevo");
        btnEliminar.setText("Eliminar");
        btnEditar.setText("Editar");
        btnRegresar.setText("Regresar");
        
        txtCorreo.setText("");
        txtDescripcion.setText("");
        
        cmbCodigo.setValue("");
        cmbProveedor.setValue("");
        
    }
        
    
    //**************************************************Eventos*******************************************************
    @FXML
    private void BotonNuevo(ActionEvent event) {
        contNuevo++;
        if(contNuevo==1){
            
            btnEditar.setDisable(true);
            btnRegresar.setDisable(true);
            cmbCodigo.setDisable(true);
            
            cmbProveedor.setDisable(false);
            txtCorreo.setDisable(false);
            txtDescripcion.setDisable(false);  
            
            txtCorreo.setText("");
            txtDescripcion.setText("");
            
            cmbCodigo.setValue(""); //Inserte para resetear el combobox
            cmbProveedor.setValue("");
            btnNuevo.setText("Guardar");
            btnEliminar.setText("Cancelar");
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
            if(tblCorreoProveedores.getSelectionModel().getSelectedItem() != null){ 
                try{
                    //hay que preguntar si esta seguro de borrarlo, por seguridad
                    int confirmar = JOptionPane.showConfirmDialog(null,"¿En realidad quiere borrar este dato?", "Advertencia",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                    if(confirmar==JOptionPane.YES_OPTION){
                        PreparedStatement procedimiento = ConexionSql.getInstancia().getConeccion().prepareCall("{call SP_EliminarEmailProveedores (?)}");
                        procedimiento.setInt(1, ((CorreoProveedores)tblCorreoProveedores.getSelectionModel().getSelectedItem()).getCodigoEmailProveedor());
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
            if(tblCorreoProveedores.getSelectionModel().getSelectedItem() != null){
                btnNuevo.setDisable(true);
                btnEliminar.setDisable(true);
                cmbCodigo.setDisable(true);
                
                cmbProveedor.setValue("");
                btnEditar.setText("Actualizar");
                btnRegresar.setText("Cancelar");
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
    private void BotonRegresar(ActionEvent event){
        if(contEditar==1){//si ContEditar vale uno quiere decir que el boton de reporte tiene que hacer la accion de cancelar
            EstadoInicial();
            contEditar=0;
        }
        else{
            escenarioPrincipal.Proveedores();
            //JOptionPane.showMessageDialog(null, "En construccion... xdxd");//aqui va lo de reporte
        }
        
    }
    
    
    @FXML
    private void RegresarInicio(MouseEvent event) {
        escenarioPrincipal.menuPrincipal();
    }
    
    @FXML
    private void seleccionarElementos(){
        
        if(tblCorreoProveedores.getSelectionModel().getSelectedItem()!=null){
            cmbCodigo.setDisable(false);
            cmbProveedor.setDisable(false);
            txtCorreo.setDisable(false);
            txtDescripcion.setDisable(false);

            cmbCodigo.getSelectionModel().select(buscarCorreoProveedores(((CorreoProveedores)tblCorreoProveedores.getSelectionModel().getSelectedItem()).getCodigoEmailProveedor()));
            cmbProveedor.setValue(((CorreoProveedores)tblCorreoProveedores.getSelectionModel().getSelectedItem()).getCodigoProveedor());
            txtCorreo.setText(((CorreoProveedores) tblCorreoProveedores.getSelectionModel().getSelectedItem()).getEmail());
            txtDescripcion.setText(((CorreoProveedores) tblCorreoProveedores.getSelectionModel().getSelectedItem()).getDescripcion());

        }
        
    } 
    
}
