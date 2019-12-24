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
import org.josuerobledo.db.ConexionSql;
import org.josuerobledo.sistema.Principal;


public class ClientesController implements Initializable {

    @FXML private Button btnTelefonos;
    @FXML private TextField txtDireccion;
    @FXML private TableColumn colNit;
    @FXML private Button btnNuevo;
    @FXML private ComboBox cmbCodigo;
    @FXML private TextField txtNit;
    @FXML private TableColumn colNombre;
    @FXML private TableView tblClientes;
    @FXML private Button btnEditar;
    @FXML private TextField txtNombre;
    @FXML private TableColumn colDireccion;
    @FXML private TableColumn colCodigoClientes;
    @FXML private Button btnEliminar;
    @FXML private Button btnCorreo;
    @FXML private ImageView imaInicio;
    @FXML private Button btnReporte;
    private ObservableList <Clientes> ListaClientes;
    private Principal escenarioPrincipal;
    private byte contNuevo;
    private byte contEditar;
    protected int Cliente;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /*Como este metodo se ejecuta primero, entonces en un principio
        estos botones estan deshabilitados*/
        txtNombre.setDisable(true);
        txtNit.setDisable(true);
        txtDireccion.setDisable(true);
        btnTelefonos.setDisable(true);
        btnCorreo.setDisable(true);
        
        cargarDatos();
        
    } 
    
     //metodo para cargar los datos a la tabla
    private void cargarDatos(){ 
        tblClientes.setItems(getClientes()); //agrega los datos a la tabla y ejecuta la observable list
        colCodigoClientes.setCellValueFactory(new PropertyValueFactory<Clientes, Integer>("CodigoCliente"));
        colNombre.setCellValueFactory(new PropertyValueFactory<Clientes, String>("Nombre"));
        colNit.setCellValueFactory(new PropertyValueFactory<Clientes, String> ("Nit"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<Clientes, String>("Direccion"));
        
        
        cmbCodigo.setItems(getClientes()); //para agregar los items al combobox
    }
    
    //para guardar los elementos en un array 
    
    protected ObservableList<Clientes> getClientes(){
        ArrayList<Clientes> lista = new ArrayList<Clientes>();
        try {
            PreparedStatement procedimiento = ConexionSql.getInstancia().getConeccion().prepareCall("{call VerClientes}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Clientes(resultado.getInt("CodigoCliente"), resultado.getString("Nombre"),
                resultado.getString("Nit"), resultado.getString("Direccion") ) );
                
                
            }
            
        } catch(SQLException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Codigo del error: "+e.getErrorCode()+"\n\n"+"Detalles: \n"+e.getMessage(), "Se ha Producido un Error", 2);
            
        }
        
        return ListaClientes = FXCollections.observableArrayList(lista);
    }
    
    //getters y setter de la variable de tipo Principal
    
    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    // Metodo para agregar y abajo el de actualizar los datos
    private void AgregarDatos(){
        Clientes registro = new Clientes();
        registro.setNombre(txtNombre.getText() );
        registro.setNit(txtNit.getText() );
        registro.setDireccion(txtDireccion.getText() );
        
        try{
            PreparedStatement procedimiento = ConexionSql.getInstancia().getConeccion().prepareCall("{call SP_InsertarClientes(?,?,?)}");
            procedimiento.setString (1,registro.getNombre());
            procedimiento.setString(2,registro.getNit());
            procedimiento.setString(3,registro.getDireccion());
            procedimiento.execute();
            ListaClientes.add(registro);
             
        }catch(SQLException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Codigo del error: "+e.getErrorCode()+"\n\n"+"Detalles: \n"+e.getMessage(), "Se ha Producido un Error", 2);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"¡Error en los datos introducidos! \n Ingrese los datos correctos y pruebe otra vez.","Error",2);
        }
    }
    
    private void Editar(){
        try{
            PreparedStatement procedimiento = ConexionSql.getInstancia().getConeccion().prepareCall("{call SP_ActualizarClientes (?,?,?,?)}");
            Clientes registro = (Clientes)tblClientes.getSelectionModel().getSelectedItem(); 
            registro.setNombre(txtNombre.getText()); 
            registro.setNit(txtNit.getText());
            registro.setDireccion(txtDireccion.getText());
            
            
            registro.setCodigoCliente(((Clientes)cmbCodigo.getSelectionModel().getSelectedItem()).getCodigoCliente());
            procedimiento.setInt (1,registro.getCodigoCliente() ); 
            procedimiento.setString (2, registro.getNombre() ); 
            procedimiento.setString(3,registro.getNit() );
            procedimiento.setString(4,registro.getDireccion());
            procedimiento.execute(); 
        }catch(SQLException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Codigo del error: "+e.getErrorCode()+"\n\n"+"Detalles: \n"+e.getMessage(), "Se ha Producido un Error", 2);
        }catch(Exception e){
             JOptionPane.showMessageDialog(null,"¡Error en los datos introducidos! \n Ingrese los datos correctos y pruebe otra vez.","Error",2);
        }
        
    }
    
      //este metodo sirve para hacer la busqueda del dato segun el codigo seleccionado en la accion seleccionar elementos
    private Clientes buscarCliente(int CodigoCliente){
        Clientes resultado= null;
        try {
            PreparedStatement procedimiento = ConexionSql.getInstancia().getConeccion().prepareCall("{call SP_BuscarClientes(?)}");
            procedimiento.setInt(1, CodigoCliente);
            ResultSet registro= procedimiento.executeQuery();
            while (registro.next()){
                resultado = new Clientes(registro.getInt("CodigoCliente"), registro.getString("Nombre"), 
                registro.getString("Nit"), registro.getString("Direccion"));
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
        
        txtNombre.setDisable(true);
        txtNit.setDisable(true);
        txtDireccion.setDisable(true);
        btnTelefonos.setDisable(true);
        btnCorreo.setDisable(true);
        
        btnNuevo.setText("Nuevo");
        btnEliminar.setText("Eliminar");
        btnEditar.setText("Editar");
        btnReporte.setText("Reporte");
        
        txtNombre.setText("");
        txtNit.setText("");
        txtDireccion.setText("");
        cmbCodigo.setValue(null);
    }
    
    //**************************************************Eventos*********************************************************

    


    
   @FXML 
    private void BotonNuevo(ActionEvent event) {
         contNuevo++;
        //al ejecutarse este metodo los botones se habilitan 
        if(contNuevo==1){
            
            btnEditar.setDisable(true);
            btnEditar.setDisable(true);
            cmbCodigo.setDisable(true);
            btnCorreo.setDisable(true);
            btnTelefonos.setDisable(true);
            
            txtNombre.setDisable(false);
            txtNit.setDisable(false);
            txtDireccion.setDisable(false);     
            
            txtNombre.setText("");
            txtNit.setText("");
            txtDireccion.setText("");
            
            cmbCodigo.setValue(null); //Inserte para resetear el combobox
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
    private void RegresarInicio(MouseEvent event) {
        escenarioPrincipal.menuPrincipal();
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
            if(tblClientes.getSelectionModel().getSelectedItem() != null){ 
                try{
                    //hay que preguntar si esta seguro de borrarlo, por seguridad
                    int confirmar = JOptionPane.showConfirmDialog(null,"¿En realidad quiere borrar este dato?", "Advertencia",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                    if(confirmar==JOptionPane.YES_OPTION){
                        PreparedStatement procedimiento = ConexionSql.getInstancia().getConeccion().prepareCall("{call SP_EliminarClientes (?)}");
                        procedimiento.setInt(1, ((Clientes)tblClientes.getSelectionModel().getSelectedItem()).getCodigoCliente());
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
            if(tblClientes.getSelectionModel().getSelectedItem() != null){
                btnNuevo.setDisable(true);
                btnEliminar.setDisable(true);
                cmbCodigo.setDisable(true);
                btnTelefonos.setDisable(true);
                btnCorreo.setDisable(true);
                
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
    
    
        //este metodo sirve para mostrar el elemento selecionado en la tabla y se ejecuta al precionar un elemeto de la tabla
    @FXML
    private void seleccionarElementos(){
        if(tblClientes.getSelectionModel().getSelectedItem()!=null){
            cmbCodigo.setDisable(false);
            txtNombre.setDisable(false);
            txtNit.setDisable(false);
            txtDireccion.setDisable(false);
            btnTelefonos.setDisable(false);
            btnCorreo.setDisable(false);

            cmbCodigo.getSelectionModel().select(buscarCliente(((Clientes)tblClientes.getSelectionModel().getSelectedItem()).getCodigoCliente() ) );
            txtNombre.setText(((Clientes)tblClientes.getSelectionModel().getSelectedItem() ).getNombre() );
            txtNit.setText(((Clientes)tblClientes.getSelectionModel().getSelectedItem() ).getNit() );
            txtDireccion.setText(((Clientes)tblClientes.getSelectionModel().getSelectedItem() ).getDireccion() );
        }
    }
    

    @FXML
    private void TelefonosClientes(ActionEvent event){
        escenarioPrincipal.TelefonoClientes();
        Cliente = (((Clientes)cmbCodigo.getSelectionModel().getSelectedItem() ).getCodigoCliente() );
        System.out.println(Cliente);
        
    }
    
    @FXML
    private void CorreosClientes(ActionEvent event){
        escenarioPrincipal.CorreoClientes();
    }
    
    @FXML
    private void seleccionarCombobox(){

        /*solo junte esto:
        1) txtNombre.setText(); para darle un valor al TextField
        2) buscarCategoria(); para buscar un dato
        3)((Categoria)cmbCategoria.getSelectionModel().getSelectedItem()).getCodigoCategoria(); para obtener el codigo selecionado 
        en el combobox y asi buscarlo por ese codigo
        4).getDescripcionCategoria para asignarle solo el valor de la descripcion al texfield
        
            txtNombre.setText( buscarCategoria(3).4 )
        */
        if(cmbCodigo.getSelectionModel().getSelectedItem()!=null){
            
            txtNombre.setDisable(false);
            txtNit.setDisable(false);
            txtDireccion.setDisable(false);
            btnCorreo.setDisable(false);
            btnTelefonos.setDisable(false);
            
            txtNombre.setText(buscarCliente( ((Clientes)cmbCodigo.getSelectionModel().getSelectedItem()).getCodigoCliente() ).getNombre() );
            txtNit.setText(buscarCliente(((Clientes)cmbCodigo.getSelectionModel().getSelectedItem()).getCodigoCliente() ).getNit() );
            txtDireccion.setText(buscarCliente(((Clientes)cmbCodigo.getSelectionModel().getSelectedItem() ).getCodigoCliente() ).getDireccion());
        }
        
    }
    
}
