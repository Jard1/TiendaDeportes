
package org.josuerobledo.controller;

import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javax.swing.JOptionPane;
import org.josuerobledo.bean.Clientes;
import org.josuerobledo.bean.Facturas;
import org.josuerobledo.db.ConexionSql;
import org.josuerobledo.reporte.GenerarReportes;
import org.josuerobledo.sistema.Principal;

public class FacturasController implements Initializable  {

    @FXML private Button btnReporte;
    @FXML private ComboBox cmbNumero;
    @FXML private ComboBox cmbCliente;
    @FXML private TableColumn colNumero;
    @FXML private DatePicker DPFecha;
    @FXML private TableColumn ColTotal;
    @FXML private Button btnNuevo;
    @FXML private TextField txtNit;
    @FXML private TableColumn ColFecha;
    @FXML private TableView tblFacturas;
    @FXML private Button btnEditar;
    @FXML private Button btnDetalle;
    @FXML private TableColumn ColEstado;
    @FXML private Button btnEliminar;
    @FXML private TableColumn ColNit;
    @FXML private ImageView imaInicio;
    @FXML private TableColumn ColCliente;
    @FXML private TextField txtEstado;
    private ObservableList<Facturas> ListaFacturas;
    private Principal escenarioPrincipal;
    private byte contNuevo;
    private byte contEditar;

    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cmbCliente.setDisable(true);
        DPFecha.setDisable(true);
        txtNit.setDisable(true);
        txtEstado.setDisable(true);
        btnDetalle.setDisable(true);
        
        cargarDatos();
    }
   

    //metodo para cargar los datos a la tabla
    private void cargarDatos(){ 
        tblFacturas.setItems(getFacturas()); //agrega los datos a la tabla y ejecuta la observable list
        colNumero.setCellValueFactory(new PropertyValueFactory<Facturas, Integer>("NumeroFactura"));
        ColCliente.setCellValueFactory(new PropertyValueFactory<Facturas, Integer>("CodigoCliente"));
        ColFecha.setCellValueFactory(new PropertyValueFactory<Facturas, Date>("Fecha"));
        ColNit.setCellValueFactory(new PropertyValueFactory<Facturas, String>("Nit"));
        ColEstado.setCellValueFactory(new PropertyValueFactory<Facturas, String>("Estado"));
        ColTotal.setCellValueFactory(new PropertyValueFactory<Facturas,Double>("Total") );
        
        cmbNumero.setItems(getFacturas()); //para agregar los items al combobox
        
        //para agregar los datos de los proveedores
        ClientesController clientesController = new ClientesController();
        cmbCliente.setItems(clientesController.getClientes());
        
        if(clientesController.getClientes().size()==0){
            JOptionPane.showMessageDialog(null,"¡Primero ingrese un Cliente!","Error al ingresar a Facturas",2); 
        }
    }

    //la observable list y para guardar los elementos en un array 
    protected ObservableList<Facturas> getFacturas(){
        ArrayList<Facturas> lista = new ArrayList<Facturas>();
        try {
            PreparedStatement procedimiento = ConexionSql.getInstancia().getConeccion().prepareCall("{call VerFacturas}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Facturas(resultado.getInt("NumeroFactura"), resultado.getDate("Fecha"),
                resultado.getString("Nit"),resultado.getString("Estado"), resultado.getDouble("Total"),
                resultado.getInt("CodigoCliente")  ) );    
            }
            
        } catch(SQLException e){
            e.printStackTrace(); 
            JOptionPane.showMessageDialog(null,"Codigo del error: "+e.getErrorCode()+"\n\n"+"Detalles: \n"+e.getMessage(), "Se ha Producido un Error", 2);
        }
        
        return ListaFacturas = FXCollections.observableArrayList(lista);
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
            Facturas registro = new Facturas();
            registro.setEstado(txtEstado.getText() );
            registro.setFecha( java.sql.Date.valueOf( DPFecha.getValue() ) ); //eso es para convertir de local date o date y si se quiere hacer al reves: sqlDate.toLocalDate();
            registro.setNit(txtNit.getText() );

            registro.setCodigoCliente(((Clientes)cmbCliente.getSelectionModel().getSelectedItem()).getCodigoCliente() );//***aqui tendria que obtener el valor de combobox
        
            
            PreparedStatement procedimiento = ConexionSql.getInstancia().getConeccion().prepareCall("{call SP_InsertarFacturas(?,?,?,?)}");
            procedimiento.setDate (1,registro.getFecha() );
            procedimiento.setString(2,registro.getNit() );
            procedimiento.setString(3,registro.getEstado() );
            procedimiento.setInt(4,registro.getCodigoCliente());
            procedimiento.execute();
            
            ListaFacturas.add(registro);
             
        }catch(SQLException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Codigo del error: "+e.getErrorCode()+"\n\n"+"Detalles: \n"+e.getMessage(), "Se ha Producido un Error", 2);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"¡Error en los datos introducidos! \n Ingrese los datos correctos y pruebe otra vez.","Error",2);
        }
        
    }
    
    private void Editar(){
        try{
            PreparedStatement procedimiento = ConexionSql.getInstancia().getConeccion().prepareCall("{call SP_ActualizarFacturas (?,?,?,?,?)}");
            
            Facturas registro = (Facturas)tblFacturas.getSelectionModel().getSelectedItem(); 
            
            registro.setNit(txtNit.getText() );
            registro.setEstado(txtEstado.getText() );
            registro.setFecha(java.sql.Date.valueOf( DPFecha.getValue() ) );
            registro.setCodigoCliente(((Clientes)cmbCliente.getSelectionModel().getSelectedItem()).getCodigoCliente());
            
            registro.setNumeroFactura(((Facturas)cmbNumero.getSelectionModel().getSelectedItem()).getNumeroFactura() );
            
            procedimiento.setInt (1,registro.getNumeroFactura() ); 
            procedimiento.setDate (2, registro.getFecha() ); 
            procedimiento.setString(3,registro.getNit() );
            procedimiento.setString(4,registro.getEstado() );
            procedimiento.setInt(5,registro.getCodigoCliente() );
            
            procedimiento.execute(); 
        }catch(SQLException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Codigo del error: "+e.getErrorCode()+"\n\n"+"Detalles: \n"+e.getMessage(), "Se ha Producido un Error", 2);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"¡Error en los datos introducidos! \n Ingrese los datos correctos y pruebe otra vez.","Error",2);
        }
        
    }
    
    //este metodo sirve para hacer la busqueda del dato segun el codigo seleccionado en la accion seleccionar elementos
    private Facturas buscarFacturas(int NumeroFactura){
        Facturas resultado= null;
        try {
            PreparedStatement procedimiento = ConexionSql.getInstancia().getConeccion().prepareCall("{call SP_BuscarFacturas(?)}");
            procedimiento.setInt(1, NumeroFactura);
            ResultSet registro= procedimiento.executeQuery();
            while (registro.next()){
                resultado = new Facturas(registro.getInt("NumeroFactura"), registro.getDate("Fecha"), registro.getString("Nit"),
                registro.getString("Estado"), registro.getDouble("Total"),registro.getInt("CodigoCliente") );
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
        cmbNumero.setDisable(false);
        
        cmbCliente.setDisable(true);
        DPFecha.setDisable(true);
        txtNit.setDisable(true);
        txtEstado.setDisable(true);
        btnDetalle.setDisable(true);
        
        btnNuevo.setText("Nuevo");
        btnEliminar.setText("Eliminar");
        btnEditar.setText("Editar");
        btnReporte.setText("Reporte");
        
        txtEstado.setText("");
        txtNit.setText("");
        DPFecha.setValue(null);
        cmbCliente.setValue("");
        cmbNumero.setValue(null);
    }
    
    private void Reporte(){
        if(tblFacturas.getSelectionModel().getSelectedItem()!=null){
            
            Map parametro = new HashMap();
            int NumeroFactura = ((Facturas)tblFacturas.getSelectionModel().getSelectedItem()).getNumeroFactura();
            parametro.put("_NumeroFactura", NumeroFactura);

            GenerarReportes.mostrarReporte("ReporteFactura.jasper", "Reporte de Facturas", parametro);
            
        }else{
            JOptionPane.showMessageDialog(null,"Tiene que seleccionar un elemento para hacer un Reporte", "ERROR",2);
        }
    }
    
    
    //****************************************************************EVENTOS**********************************
    @FXML
    private void BotonNuevo(ActionEvent event) {
        contNuevo++;
        //al ejecutarse este metodo los botones se habilitan 
        if(contNuevo==1){
            
            btnEditar.setDisable(true);
            btnReporte.setDisable(true);
            cmbNumero.setDisable(true);
            btnDetalle.setDisable(true);
            
            cmbCliente.setDisable(false);
            DPFecha.setDisable(false);
            txtNit.setDisable(false);
            txtEstado.setDisable(false);

            txtNit.setText("");
            DPFecha.setValue(null);
            txtEstado.setText("");
            cmbCliente.setValue(""); //Inserte para resetear el combobox
            cmbNumero.setValue(null);
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
            if(tblFacturas.getSelectionModel().getSelectedItem() != null){ 
                try{
                    //hay que preguntar si esta seguro de borrarlo, por seguridad
                    int confirmar = JOptionPane.showConfirmDialog(null,"¿En realidad quiere borrar este dato?", "Advertencia",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                    
                    if(confirmar==JOptionPane.YES_OPTION){
                        PreparedStatement procedimiento = ConexionSql.getInstancia().getConeccion().prepareCall("{call SP_EliminarFacturas (?)}");
                        procedimiento.setInt(1, ((Facturas)tblFacturas.getSelectionModel().getSelectedItem()).getNumeroFactura());
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
            if(tblFacturas.getSelectionModel().getSelectedItem() != null){
                btnNuevo.setDisable(true);
                btnEliminar.setDisable(true);
                cmbNumero.setDisable(true);
                btnDetalle.setDisable(true);
                cmbCliente.setValue("");
                
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
        if(tblFacturas.getSelectionModel().getSelectedItem()!=null){
            cmbNumero.setDisable(false);
            cmbCliente.setDisable(false);
            DPFecha.setDisable(false);
            txtNit.setDisable(false);
            txtEstado.setDisable(false);
            btnDetalle.setDisable(false);
            

            cmbNumero.getSelectionModel().select(buscarFacturas(((Facturas)tblFacturas.getSelectionModel().getSelectedItem()).getNumeroFactura()));
            cmbCliente.setValue(((Facturas)tblFacturas.getSelectionModel().getSelectedItem()).getCodigoCliente());
            
            txtNit.setText(((Facturas)tblFacturas.getSelectionModel().getSelectedItem()).getNit() );
            txtEstado.setText(((Facturas)tblFacturas.getSelectionModel().getSelectedItem()).getEstado());
            DPFecha.setValue(((Facturas)tblFacturas.getSelectionModel().getSelectedItem()).getFecha().toLocalDate());

        }
    }
    
    @FXML
    private void seleccionarCombobox(){

        /*solo junte esto:
        
            txtNombre.setText( buscarCategoria(3).4 )
        */
        if(cmbNumero.getSelectionModel().getSelectedItem()!=null){
            
            cmbCliente.setDisable(false);
            txtNit.setDisable(false);
            DPFecha.setDisable(false);
            txtEstado.setDisable(false);
            
            cmbCliente.setValue(buscarFacturas( ((Facturas)cmbNumero.getSelectionModel().getSelectedItem()).getNumeroFactura() ).getCodigoCliente() );
            txtNit.setText(buscarFacturas(((Facturas)cmbNumero.getSelectionModel().getSelectedItem()).getNumeroFactura() ).getNit() );
            DPFecha.setValue(buscarFacturas(((Facturas)cmbNumero.getSelectionModel().getSelectedItem() ).getNumeroFactura() ).getFecha().toLocalDate() );
            txtEstado.setText(buscarFacturas(((Facturas)cmbNumero.getSelectionModel().getSelectedItem()).getNumeroFactura()).getEstado());
        
        }
        
    }
    
    @FXML
    private void RegresarInicio(MouseEvent event) {
        escenarioPrincipal.menuPrincipal();
    }
    
    @FXML
    private void VentanaDetalle(ActionEvent event){
        escenarioPrincipal.DetalleFacturas();
    }
    
}
