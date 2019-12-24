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
import org.josuerobledo.bean.DetalleFacturas;
import org.josuerobledo.bean.Productos;
import org.josuerobledo.db.ConexionSql;
import org.josuerobledo.sistema.Principal;


public class DetalleFacturaController implements Initializable{

    @FXML private TableColumn ColPrecio;
    @FXML private ComboBox cmbFactura;
    @FXML private Button btnRegresar;
    @FXML private TableColumn colCodigo; 
    @FXML private TableColumn ColCantidad;
    @FXML private Button btnNuevo;
    @FXML private TableView tblDetalleFactura; 
    @FXML private ComboBox cmbCodigo;
    @FXML private Button btnEditar;
    @FXML private ComboBox cmbProducto;
    @FXML private Button btnEliminar;
    @FXML private TextField txtCantidad;
    @FXML private TableColumn ColProducto;
    @FXML private ImageView imaInicio;
    @FXML private TableColumn ColFactura;
    private ObservableList<DetalleFacturas> ListaDetalleFacturas;
    private Principal escenarioPrincipal;
    private byte contNuevo;
    private byte contEditar;

    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cmbCodigo.setDisable(true);
        //txtPrecio.setDisable(true);
        txtCantidad.setDisable(true);
        cmbFactura.setDisable(true);
        cmbProducto.setDisable(true);
                
        cargarDatos();  
    }
    
    //metodo para cargar los datos a la tabla
    private void cargarDatos(){ 
        tblDetalleFactura.setItems(getDetalleFactura()); //agrega los datos a la tabla y ejecuta la observable list
        colCodigo.setCellValueFactory(new PropertyValueFactory<DetalleFacturas, Integer>("CodigoDetalleFactura"));
        ColPrecio.setCellValueFactory(new PropertyValueFactory<DetalleFacturas, Double>("Precio"));
        ColCantidad.setCellValueFactory(new PropertyValueFactory<DetalleFacturas, Integer>("Cantidad"));
        ColFactura.setCellValueFactory(new PropertyValueFactory<DetalleFacturas, Integer>("NumeroFactura"));
        ColProducto.setCellValueFactory(new PropertyValueFactory<DetalleFacturas, Integer>("CodigoProducto"));
        
        cmbCodigo.setItems(getDetalleFactura()); //para agregar los items al combobox
        
        //para agregar los datos de los proveedores
        FacturasController facturasController = new FacturasController();
        cmbFactura.setItems(facturasController.getFacturas());
        
        ProductosController productosController = new ProductosController();
        cmbProducto.setItems(productosController.getProductos());
        
        if(facturasController.getFacturas().size()==0){
            JOptionPane.showMessageDialog(null,"¡Primero ingrese una Factura!","Error al ingresar a Detalle Facturas",2); 
        }
        if(productosController.getProductos().size()==0){
            JOptionPane.showMessageDialog(null,"¡Primero ingrese un Producto!","Error al ingresar a Detalle Facturas",2); 
        }
        
    }
   
     //la observable list y para guardar los elementos en un array 
    private ObservableList<DetalleFacturas> getDetalleFactura(){
        ArrayList<DetalleFacturas> lista = new ArrayList<DetalleFacturas>();
        try {
            PreparedStatement procedimiento = ConexionSql.getInstancia().getConeccion().prepareCall("{call VerDetalleFactura}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new DetalleFacturas(resultado.getInt("CodigoDetalleFactura"), resultado.getDouble("Precio"),
                resultado.getInt("Cantidad"),resultado.getInt("NumeroFactura"), resultado.getInt("CodigoProducto") ) );    
            }
            
        } catch(SQLException e){
            e.printStackTrace();  
            JOptionPane.showMessageDialog(null,"Codigo del error: "+e.getErrorCode()+"\n\n"+"Detalles: \n"+e.getMessage(), "Se ha Producido un Error", 2);
        }
        
        return ListaDetalleFacturas = FXCollections.observableArrayList(lista);
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
        
    // Metodo para agregar datos
    private void AgregarDatos(){
        try{
            DetalleFacturas registro = new DetalleFacturas();
            
            registro.setCantidad( Integer.parseInt(txtCantidad.getText() ) ); 
            registro.setNumeroFactura( Integer.parseInt(cmbFactura.getValue().toString() ) ); //para convetir de string a doble
            registro.setCodigoProducto(((Productos)cmbProducto.getSelectionModel().getSelectedItem()).getCodigoProducto());
        
            
            PreparedStatement procedimiento = ConexionSql.getInstancia().getConeccion().prepareCall("{call SP_InsertarDetalleFactura(?,?,?)}");
            procedimiento.setInt(1,registro.getCantidad() );
            procedimiento.setInt(2,registro.getNumeroFactura() );
            procedimiento.setInt(3,registro.getCodigoProducto() );
            
            
            procedimiento.execute();
           
            
            
            ListaDetalleFacturas.add(registro);
             
        }catch(SQLException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Codigo del error: "+e.getErrorCode()+"\n\n"+"Detalles: \n"+e.getMessage(), "Se ha Producido un Error", 2);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"¡Error en los datos introducidos! \n Ingrese los datos correctos y pruebe otra vez.","Error",2);
        }

    }
    
    private void Editar(){
        try{
            PreparedStatement procedimiento = ConexionSql.getInstancia().getConeccion().prepareCall("{call SP_ActualizarDetalleFactura (?,?,?,?)}");
            DetalleFacturas registro = (DetalleFacturas)tblDetalleFactura.getSelectionModel().getSelectedItem(); 
            
            registro.setCantidad( Integer.parseInt(txtCantidad.getText() ) ); 
            registro.setNumeroFactura( Integer.parseInt(cmbFactura.getValue().toString() ) ); 
            registro.setCodigoProducto(((Productos)cmbProducto.getSelectionModel().getSelectedItem()).getCodigoProducto());
            
            registro.setCodigoDetalleFactura(((DetalleFacturas)cmbCodigo.getSelectionModel().getSelectedItem()).getCodigoDetalleFactura() );
            
            procedimiento.setInt (1,registro.getCodigoDetalleFactura() ); 
            procedimiento.setInt(2,registro.getCantidad() );
            procedimiento.setInt(3,registro.getNumeroFactura() );
            procedimiento.setInt(4,registro.getCodigoProducto() );
            
            procedimiento.execute(); 
            
        }catch(SQLException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Codigo del error: "+e.getErrorCode()+"\n\n"+"Detalles: \n"+e.getMessage(), "Se ha Producido un Error", 2);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"¡Error en los datos introducidos! \n Ingrese los datos correctos y pruebe otra vez.","Error",2);
        }
        
    }
    
    //este metodo sirve para hacer la busqueda del dato segun el codigo seleccionado en la accion seleccionar elementos
    private DetalleFacturas buscarDetalleFacturas(int CodigoDetalleFactura){
        DetalleFacturas resultado= null;
        try {
            PreparedStatement procedimiento = ConexionSql.getInstancia().getConeccion().prepareCall("{call SP_BuscarDetalleFactura(?)}");
            procedimiento.setInt(1, CodigoDetalleFactura);
            ResultSet registro= procedimiento.executeQuery();
            while (registro.next()){
                resultado = new DetalleFacturas(registro.getInt("CodigoDetalleFactura"), registro.getDouble("Precio"), registro.getInt("Cantidad"),
                registro.getInt("NumeroFactura"), registro.getInt("CodigoProducto")  );
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
        btnRegresar.setDisable(false);
        
        cmbCodigo.setDisable(true);
        txtCantidad.setDisable(true);
        cmbFactura.setDisable(true);
        cmbProducto.setDisable(true);
        
        btnNuevo.setText("Nuevo");
        btnEliminar.setText("Eliminar");
        btnEditar.setText("Editar");
        btnRegresar.setText("Regresar");
        
        txtCantidad.setText("");
        cmbFactura.setValue("");
        cmbProducto.setValue("");
        cmbCodigo.setValue("");
    }
    
    //----------------------------------------------------------EVENTOS---------------------------------------
    
    @FXML
    private void BotonNuevo(ActionEvent event) {
        contNuevo++;
        //al ejecutarse este metodo los botones se habilitan 
        if(contNuevo==1){
            
            btnEditar.setDisable(true);
            btnRegresar.setDisable(true);
            cmbCodigo.setDisable(true);
            
            txtCantidad.setDisable(false);
            cmbFactura.setDisable(false);
            cmbProducto.setDisable(false);
            
            txtCantidad.setText("");
            cmbFactura.setValue("");
            cmbProducto.setValue(""); //Inserte para resetear el combobox
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
            if(tblDetalleFactura.getSelectionModel().getSelectedItem() != null){ 
                try{
                    //hay que preguntar si esta seguro de borrarlo, por seguridad
                    int confirmar = JOptionPane.showConfirmDialog(null,"¿En realidad quiere borrar este dato?", "Advertencia",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                    if(confirmar==JOptionPane.YES_OPTION){
                        PreparedStatement procedimiento = ConexionSql.getInstancia().getConeccion().prepareCall("{call SP_EliminarDetalleFactura (?)}");
                        procedimiento.setInt(1, ((DetalleFacturas)tblDetalleFactura.getSelectionModel().getSelectedItem()).getCodigoDetalleFactura());
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
            if(tblDetalleFactura.getSelectionModel().getSelectedItem() != null){
                btnNuevo.setDisable(true);
                btnEliminar.setDisable(true);
                cmbCodigo.setDisable(true);
                cmbProducto.setValue("");
                
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
            escenarioPrincipal.Facturas();//aqui va lo de reporte
        }
        
    }
    
    //este metodo sirve para mostrar el elemento selecionado en la tabla y se ejecuta al precionar un elemeto de la tabla
    @FXML
    private void seleccionarElementos(MouseEvent event){
        if(tblDetalleFactura.getSelectionModel().getSelectedItem()!=null){
            cmbCodigo.setDisable(false);
            txtCantidad.setDisable(false);
            cmbFactura.setDisable(false);
            cmbProducto.setDisable(false);

            cmbCodigo.getSelectionModel().select(buscarDetalleFacturas(((DetalleFacturas)tblDetalleFactura.getSelectionModel().getSelectedItem()).getCodigoDetalleFactura()));
            cmbFactura.setValue(((DetalleFacturas)tblDetalleFactura.getSelectionModel().getSelectedItem()).getNumeroFactura() );
            cmbProducto.setValue(((DetalleFacturas)tblDetalleFactura.getSelectionModel().getSelectedItem()).getCodigoProducto() );
            
            txtCantidad.setText( String.valueOf( ( (DetalleFacturas)tblDetalleFactura.getSelectionModel().getSelectedItem()).getCantidad() ) );
        }
    }
    
    @FXML
    private void RegresarInicio(MouseEvent event) {
        escenarioPrincipal.menuPrincipal();
    }
    
}
