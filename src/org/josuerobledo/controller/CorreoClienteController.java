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
import org.josuerobledo.bean.Clientes;
import org.josuerobledo.bean.CorreoClientes;
import org.josuerobledo.db.ConexionSql;
import org.josuerobledo.sistema.Principal;

public class CorreoClienteController implements Initializable {

    @FXML private ComboBox cmbCliente;
    @FXML private TableColumn colCodigo;
    @FXML private Button btnNuevo;
    @FXML private ComboBox cmbCodigo;
    @FXML private Button btnEditar;
    @FXML private TextField txtCorreo;
    @FXML private TableColumn colCliente;
    @FXML private TableColumn colDescripcion;
    @FXML private Button btnRegresar;
    @FXML private Button btnEliminar;
    @FXML private TableColumn colCorreo;
    @FXML private ImageView imaInicio;
    @FXML private TextField txtDescripcion;
    @FXML private TableView tblCorreoClientes;
    private Principal escenarioPrincipal;
    private byte contNuevo;
    private byte contEditar;
    private ObservableList<CorreoClientes> ListaCorreoClientes;
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cmbCodigo.setDisable(true);
        txtCorreo.setDisable(true);
        txtDescripcion.setDisable(true);
        cmbCliente.setDisable(true); 
        cargarDatos();
    }
    
    private void cargarDatos(){ 
        tblCorreoClientes.setItems(getCorreoClientes()); //agrega los datos a la tabla y ejecuta la observable list
        colCodigo.setCellValueFactory(new PropertyValueFactory<CorreoClientes, Integer>("CodigoEmailCliente"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<CorreoClientes, String>("Descripcion"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<CorreoClientes, String>("Email"));
        colCliente.setCellValueFactory(new PropertyValueFactory<CorreoClientes, Double>("CodigoCliente"));
        
        
        cmbCodigo.setItems(getCorreoClientes()); //para agregar los items al combobox
        
        //para agregar los datos de los proveedores
        ClientesController clientesController = new ClientesController();
        cmbCliente.setItems(clientesController.getClientes() );
        
        if(clientesController.getClientes().size()==0){
            JOptionPane.showMessageDialog(null,"¡Primero ingrese un Cliente!","Error al ingresar a Correo Clientes",2); 
        }
    }
    
        
    
    private ObservableList<CorreoClientes> getCorreoClientes(){
        ArrayList<CorreoClientes> lista = new ArrayList<CorreoClientes>();
        try {
            PreparedStatement procedimiento = ConexionSql.getInstancia().getConeccion().prepareCall("{call VerEmailCliente}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new CorreoClientes(resultado.getInt("CodigoEmailCliente"), resultado.getString("Email"),
                resultado.getString("Descripcion"),resultado.getInt("CodigoCliente") ) );    
            }
            
        } catch(SQLException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Codigo del error: "+e.getErrorCode()+"\n\n"+"Detalles: \n"+e.getMessage(), "Se ha Producido un Error", 2);
            
        }
        
        return ListaCorreoClientes = FXCollections.observableArrayList(lista);
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
        CorreoClientes registro = new CorreoClientes();
        
        registro.setDescripcion(txtDescripcion.getText() );
        registro.setEmail(txtCorreo.getText() );
        registro.setCodigoCliente(((Clientes)cmbCliente.getSelectionModel().getSelectedItem()).getCodigoCliente() );
        
        try{
            PreparedStatement procedimiento = ConexionSql.getInstancia().getConeccion().prepareCall("{call SP_InsertarEmailCliente(?,?,?)}");
            procedimiento.setString (1,registro.getDescripcion() );
            procedimiento.setString(2,registro.getEmail() );
            procedimiento.setInt(3,registro.getCodigoCliente() );
            
            procedimiento.execute();

            ListaCorreoClientes.add(registro);
             
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
            PreparedStatement procedimiento = ConexionSql.getInstancia().getConeccion().prepareCall("{call SP_ActualizarEmailCliente (?,?,?,?)}");
            CorreoClientes registro = (CorreoClientes)tblCorreoClientes.getSelectionModel().getSelectedItem(); 
           
            
            registro.setDescripcion(txtDescripcion.getText() );
            registro.setEmail(txtCorreo.getText() );
            registro.setCodigoCliente(((Clientes)cmbCliente.getSelectionModel().getSelectedItem()).getCodigoCliente());
            
            
            registro.setCodigoEmailCliente(((CorreoClientes)cmbCodigo.getSelectionModel().getSelectedItem()).getCodigoEmailCliente() );
            
            procedimiento.setInt (1,registro.getCodigoEmailCliente() ); 
            procedimiento.setString (2, registro.getDescripcion() ); 
            procedimiento.setString(3,registro.getEmail() );
            procedimiento.setInt(4,registro.getCodigoCliente() );
            
            procedimiento.execute(); 
            
        }catch(SQLException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Codigo del error: "+e.getErrorCode()+"\n\n"+"Detalles: \n"+e.getMessage(), "Se ha Producido un Error", 2);
        }catch(Exception e){
             JOptionPane.showMessageDialog(null,"¡Error en los datos introducidos! \n Ingrese los datos correctos y pruebe otra vez.","Error",2);
        }
        
    }
    
    private CorreoClientes buscarCorreoClientes(int CodigoEmailCliente){
        CorreoClientes resultado= null;
        try {
            PreparedStatement procedimiento = ConexionSql.getInstancia().getConeccion().prepareCall("{call SP_BuscarEmailCliente(?)}");
            procedimiento.setInt(1, CodigoEmailCliente);
            ResultSet registro= procedimiento.executeQuery();
            while (registro.next()){
                resultado = new CorreoClientes(registro.getInt("CodigoEmailCliente"), registro.getString("Descripcion"), registro.getString("Email"),
                registro.getInt("CodigoCliente") );
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
        cmbCliente.setDisable(true);
        txtCorreo.setDisable(true);
        txtDescripcion.setDisable(true);

        btnNuevo.setText("Nuevo");
        btnEliminar.setText("Eliminar");
        btnEditar.setText("Editar");
        btnRegresar.setText("Regresar");
        
        txtCorreo.setText("");
        txtDescripcion.setText("");
        
        cmbCodigo.setValue("");
        cmbCliente.setValue("");
        
    }
    
    
    
    //*******************************************Eventos***********************************
    
    
    @FXML
    private void BotonNuevo(ActionEvent event) {
        contNuevo++;
        if(contNuevo==1){
            
            btnEditar.setDisable(true);
            btnRegresar.setDisable(true);
            cmbCodigo.setDisable(true);

            cmbCliente.setDisable(false);
            txtCorreo.setDisable(false);
            txtDescripcion.setDisable(false);

            cmbCodigo.setValue("");
            cmbCliente.setValue("");
            txtCorreo.setText("");
            txtDescripcion.setText("");

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
            if(tblCorreoClientes.getSelectionModel().getSelectedItem() != null){ 
                try{
                    //hay que preguntar si esta seguro de borrarlo, por seguridad
                    int confirmar = JOptionPane.showConfirmDialog(null,"¿En realidad quiere borrar este dato?", "Advertencia",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                    if(confirmar==JOptionPane.YES_OPTION){
                        PreparedStatement procedimiento = ConexionSql.getInstancia().getConeccion().prepareCall("{call SP_EliminarEmailCliente (?)}");
                        procedimiento.setInt(1, ((CorreoClientes)tblCorreoClientes.getSelectionModel().getSelectedItem()).getCodigoEmailCliente());
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
            if(tblCorreoClientes.getSelectionModel().getSelectedItem() != null){
                btnNuevo.setDisable(true);
                btnEliminar.setDisable(true);
                cmbCodigo.setDisable(true);
                
                cmbCliente.setValue("");
                
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
            escenarioPrincipal.Clientes();
        }
        
    }
    
    @FXML
    private void RegresarInicio(MouseEvent event) {
        escenarioPrincipal.menuPrincipal();
    }

    @FXML
    private void seleccionarElementos(){
        
        if(tblCorreoClientes.getSelectionModel().getSelectedItem()!=null){
            cmbCodigo.setDisable(false);
            cmbCliente.setDisable(false);
            txtCorreo.setDisable(false);
            txtDescripcion.setDisable(false);

            cmbCodigo.getSelectionModel().select(buscarCorreoClientes(((CorreoClientes)tblCorreoClientes.getSelectionModel().getSelectedItem()).getCodigoEmailCliente()));
            cmbCliente.setValue(((CorreoClientes)tblCorreoClientes.getSelectionModel().getSelectedItem()).getCodigoCliente());
            txtCorreo.setText(((CorreoClientes) tblCorreoClientes.getSelectionModel().getSelectedItem()).getEmail());
            txtDescripcion.setText(((CorreoClientes) tblCorreoClientes.getSelectionModel().getSelectedItem()).getDescripcion());

        }
        
    } 
}