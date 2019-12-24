package org.josuerobledo.controller;

import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javax.swing.JOptionPane;
import org.josuerobledo.bean.Compras;
import org.josuerobledo.bean.Proveedores;
import org.josuerobledo.db.ConexionSql;
import org.josuerobledo.reporte.GenerarReportes;
import org.josuerobledo.sistema.Principal;


public class ComprasController implements Initializable  {
    @FXML private ComboBox cmbProveedor;
    @FXML private TableColumn ColProveedor;
    @FXML private TextField txtDireccion;
    @FXML private TableColumn ColTotal;
    @FXML private TableColumn colNumeroDocumento;
    @FXML private Button btnNuevo;
    @FXML private TableColumn ColFecha;
    @FXML private Button btnEditar;
    @FXML private TableView tblCompras;
    @FXML private TableColumn colDireccion;
    @FXML private Button btnEliminar;
    @FXML private Button btnReporte;
    @FXML private Button btnDetalle;
    @FXML private ComboBox cmbCodigo;
    @FXML private DatePicker DPfechaCompras;
    private ObservableList<Compras> ListaCompras;
    private Principal escenarioPrincipal;
    private byte contNuevo;
    private byte contEditar;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /*Como este metodo se ejecuta primero, entonces en un principio
        estos botones estan deshabilitados*/
        DPfechaCompras.setDisable(true);        
        txtDireccion.setDisable(true);
        btnDetalle.setDisable(true);
        //txtFecha.setDisable(true);
        cmbProveedor.setDisable(true);
        cargarDatos();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
        
        
    }
    

    //metodo para cargar los datos a la tabla
    private void cargarDatos(){ 
        tblCompras.setItems(getCompras()); //agrega los datos a la tabla y ejecuta la observable list
        colNumeroDocumento.setCellValueFactory(new PropertyValueFactory<Compras, Integer>("NumeroDocumento"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<Compras, String>("Direccion"));
        ColFecha.setCellValueFactory(new PropertyValueFactory<Compras, Date>("Fecha"));
        ColTotal.setCellValueFactory(new PropertyValueFactory<Compras, Double>("Total"));
        ColProveedor.setCellValueFactory(new PropertyValueFactory<Compras, Integer>("CodigoProveedor"));
        
        cmbCodigo.setItems(getCompras()); //para agregar los items al combobox
        
        //para agregar los datos de los proveedores
        ProveedoresController proveedoresController = new ProveedoresController();
        cmbProveedor.setItems(proveedoresController.getProveedores());
        
        if(proveedoresController.getProveedores().size()==0){
            JOptionPane.showMessageDialog(null,"¡Primero ingrese un Proveedor!","Error al ingresar a Compras",2); 
        }
    }
    
     //la observable list y para guardar los elementos en un array 
    protected ObservableList<Compras> getCompras(){
        ArrayList<Compras> lista = new ArrayList<Compras>();
        try {
            PreparedStatement procedimiento = ConexionSql.getInstancia().getConeccion().prepareCall("{call VerCompra}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Compras(resultado.getInt("NumeroDocumento"), resultado.getString("Direccion"),
                resultado.getDate("Fecha"),resultado.getDouble("Total"), resultado.getInt("CodigoProveedor")  ) );    
            }
            
        } catch(SQLException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Codigo del error: "+e.getErrorCode()+"\n\n"+"Detalles: \n"+e.getMessage(), "Se ha Producido un Error", 2);
            
        }
        
        return ListaCompras = FXCollections.observableArrayList(lista);
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
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    
        try{
            
            Compras registro = new Compras();
            registro.setDireccion(txtDireccion.getText() );                                                                                                                                                                                                                                                                                                                                                                                                                                                                             
            
            registro.setFecha( java.sql.Date.valueOf( DPfechaCompras.getValue() ) ); //eso es para convertir de local date o date y si se quiere hacer al reves: sqlDate.toLocalDate();
            registro.setCodigoProveedor(((Proveedores)cmbProveedor.getSelectionModel().getSelectedItem()).getCodigoProveedor());//***aqui tendria que obtener el valor de combobox
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
            PreparedStatement procedimiento = ConexionSql.getInstancia().getConeccion().prepareCall("{call SP_InsertarCompras(?,?,?)}");
            procedimiento.setString (1,registro.getDireccion() );
            procedimiento.setDate(2,registro.getFecha() );
            procedimiento.setInt(3,registro.getCodigoProveedor() );
            procedimiento.execute();
            ListaCompras.add(registro);  

             
        }catch(SQLException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Codigo del error: "+e.getErrorCode()+"\n\n"+"Detalles: \n"+e.getMessage(), "Se ha Producido un Error", 2);
        }catch(Exception e){
           JOptionPane.showMessageDialog(null,"¡Error en los datos introducidos! \n Ingrese los datos correctos y pruebe otra vez.","Error",2);
        }
    }
    
    
    private void Editar(){
        try{
            PreparedStatement procedimiento = ConexionSql.getInstancia().getConeccion().prepareCall("{call SP_ActualizarCompras (?,?,?,?)}");
            Compras registro = (Compras)tblCompras.getSelectionModel().getSelectedItem(); 
            registro.setDireccion(txtDireccion.getText()); 
            registro.setFecha(java.sql.Date.valueOf( DPfechaCompras.getValue() ) );
            registro.setCodigoProveedor(((Proveedores)cmbProveedor.getSelectionModel().getSelectedItem()).getCodigoProveedor()); //**aqui para seleccionar el combo box
            
            
            registro.setNumeroDocumento(((Compras)cmbCodigo.getSelectionModel().getSelectedItem()).getNumeroDocumento() );
            
            procedimiento.setInt (1,registro.getNumeroDocumento() ); 
            procedimiento.setString (2, registro.getDireccion() ); 
            procedimiento.setDate(3,registro.getFecha() );
            procedimiento.setInt(4,registro.getCodigoProveedor() );
            
            procedimiento.execute(); 
        }catch(SQLException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Codigo del error: "+e.getErrorCode()+"\n\n"+"Detalles: \n"+e.getMessage(), "Se ha Producido un Error", 2);
        }catch(Exception e){
           JOptionPane.showMessageDialog(null,"¡Error en los datos introducidos! \n Ingrese los datos correctos y pruebe otra vez.","Error",2);
        }
        
    }
    
        //este metodo sirve para hacer la busqueda del dato segun el codigo seleccionado en la accion seleccionar elementos
    private Compras buscarCompras(int NumeroDocumento){
        Compras resultado= null;
        try {
            PreparedStatement procedimiento = ConexionSql.getInstancia().getConeccion().prepareCall("{call SP_BuscarCompras(?)}");
            procedimiento.setInt(1, NumeroDocumento);
            ResultSet registro= procedimiento.executeQuery();
            while (registro.next()){
                resultado = new Compras(registro.getInt("NumeroDocumento"), registro.getString("Direccion"), registro.getDate("Fecha"),
                registro.getDouble("Total"), registro.getInt("CodigoProveedor") );
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
        DPfechaCompras.setDisable(true);
        cmbProveedor.setDisable(true);
        btnDetalle.setDisable(true);
        
        btnNuevo.setText("Nuevo");
        btnEliminar.setText("Eliminar");
        btnEditar.setText("Editar");
        btnReporte.setText("Reporte");
        
        txtDireccion.setText("");
        cmbCodigo.setValue(null);
        DPfechaCompras.setValue(null);
        cmbProveedor.setValue("");
    }
    
    private void Reporte(){
        if(tblCompras.getSelectionModel().getSelectedItem()!=null){
            
            Map parametro = new HashMap();
            int numeroDocumento = ((Compras)tblCompras.getSelectionModel().getSelectedItem()).getNumeroDocumento();
            parametro.put("_NumeroDocumento", numeroDocumento);

            GenerarReportes.mostrarReporte("ReporteCompras.jasper", "Reporte de compras", parametro);
            
        }else{
            JOptionPane.showMessageDialog(null,"Tiene que seleccionar un elemento para hacer un Reporte", "ERROR",2);
        }
    }
    
    
    //**************************************Eventos*************************************************
    @FXML
    private void BotonNuevo(ActionEvent event) {
        contNuevo++;
        //al ejecutarse este metodo los botones se habilitan 
        if(contNuevo==1){
            
            btnEditar.setDisable(true);
            btnReporte.setDisable(true);
            cmbCodigo.setDisable(true);
            btnDetalle.setDisable(true);
            
            txtDireccion.setDisable(false);
            cmbProveedor.setDisable(false);
            DPfechaCompras.setDisable(false);
            
            
            txtDireccion.setText("");
            DPfechaCompras.setValue(null);
            cmbProveedor.setValue(""); //Inserte para resetear el combobox
            cmbCodigo.setValue(null);
            btnNuevo.setText("Guardar");
            btnEliminar.setText("Cancelar");
            btnEditar.setText("Editar");
            btnReporte.setText("Reporte");
            
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
            if(tblCompras.getSelectionModel().getSelectedItem() != null){ 
                try{
                    //hay que preguntar si esta seguro de borrarlo, por seguridad
                    int confirmar = JOptionPane.showConfirmDialog(null,"¿En realidad quiere borrar este dato?", "Advertencia",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                    if(confirmar==JOptionPane.YES_OPTION){
                        PreparedStatement procedimiento = ConexionSql.getInstancia().getConeccion().prepareCall("{call SP_EliminarCompras (?)}");
                        procedimiento.setInt(1, ((Compras)tblCompras.getSelectionModel().getSelectedItem()).getNumeroDocumento());
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
            if(tblCompras.getSelectionModel().getSelectedItem() != null){
                btnNuevo.setDisable(true);
                btnEliminar.setDisable(true);
                cmbCodigo.setDisable(true);
                btnDetalle.setDisable(true);
                
                cmbProveedor.setValue("");
                
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
            Reporte();
            
        }
        
    }
    
    
    //este metodo sirve para mostrar el elemento selecionado en la tabla y se ejecuta al precionar un elemeto de la tabla
    @FXML
    private void seleccionarElementos(){
        if(tblCompras.getSelectionModel().getSelectedItem()!=null){
            cmbCodigo.setDisable(false);
            txtDireccion.setDisable(false);
            DPfechaCompras.setDisable(false);
            cmbProveedor.setDisable(false);
            btnDetalle.setDisable(false);

            cmbCodigo.getSelectionModel().select(buscarCompras(((Compras)tblCompras.getSelectionModel().getSelectedItem()).getNumeroDocumento()));
            txtDireccion.setText(((Compras)tblCompras.getSelectionModel().getSelectedItem()).getDireccion() );
            
            cmbProveedor.setValue(((Compras)tblCompras.getSelectionModel().getSelectedItem()).getCodigoProveedor());
            DPfechaCompras.setValue(((Compras)tblCompras.getSelectionModel().getSelectedItem()).getFecha().toLocalDate());

        }
    }
    
    @FXML
    private void seleccionarCombobox(){

        /*solo junte esto:
        */
        if(cmbCodigo.getSelectionModel().getSelectedItem()!=null){
            
            cmbProveedor.setDisable(false);
            txtDireccion.setDisable(false);
            DPfechaCompras.setDisable(false);
            btnDetalle.setDisable(false);
            
            cmbProveedor.setValue(buscarCompras( ((Compras)cmbCodigo.getSelectionModel().getSelectedItem()).getNumeroDocumento() ).getCodigoProveedor() );
            DPfechaCompras.setValue(buscarCompras(((Compras)cmbCodigo.getSelectionModel().getSelectedItem()).getNumeroDocumento() ).getFecha().toLocalDate() );
            txtDireccion.setText(buscarCompras(((Compras)cmbCodigo.getSelectionModel().getSelectedItem() ).getNumeroDocumento() ).getDireccion());
        }
        
    }
    
    @FXML
    private void RegresarInicio(MouseEvent event) {
        escenarioPrincipal.menuPrincipal();
    }
    
    @FXML
    private void DetalleCompras(ActionEvent event){
        escenarioPrincipal.DetalleCompras();
        
    }
}
