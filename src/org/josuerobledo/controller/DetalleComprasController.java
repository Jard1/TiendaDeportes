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
import org.josuerobledo.bean.DetalleCompras;
import org.josuerobledo.bean.Productos;
import org.josuerobledo.db.ConexionSql;
import org.josuerobledo.sistema.Principal;


public class DetalleComprasController implements Initializable{

    @FXML private TableColumn ColCostoU;
    @FXML private Button btnReporte;
    @FXML private TableColumn colCodigo;
    @FXML private ComboBox cmbDocumento;
    @FXML private TableColumn ColDocumento;
    @FXML private Button btnNuevo;
    @FXML private TableColumn ColCantidad;
    @FXML private ComboBox cmbCodigo;
    @FXML private TableView tblDetalleCompras;
    @FXML private Button btnEditar;
    @FXML private TextField txtCantidad;
    @FXML private Button btnEliminar;
    @FXML private ComboBox cmbProducto;
    @FXML private TableColumn ColProducto;
    @FXML private ImageView imaInicio;
    @FXML private TextField txtPrecio;
    private ObservableList<DetalleCompras> ListaDetalleCompras;
    private Principal escenarioPrincipal;
    private byte contNuevo;
    private byte contEditar;
    private int CantidadActual; // la finalidad de esto lo voy a explicar mas abajo en los metodos: BotonEditar y Editar
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        cmbCodigo.setDisable(true);
        cmbDocumento.setDisable(true);
        txtCantidad.setDisable(true);
        cmbProducto.setDisable(true);
        txtPrecio.setDisable(true);
        
        cargarDatos();    
    }
    
    //metodo para cargar los datos a la tabla
    private void cargarDatos(){ 
        tblDetalleCompras.setItems(getDetalleCompras()); //agrega los datos a la tabla y ejecuta la observable list
        colCodigo.setCellValueFactory(new PropertyValueFactory<DetalleCompras, Integer>("CodigoDetalleCompras"));
        ColProducto.setCellValueFactory(new PropertyValueFactory<DetalleCompras, Double>("CodigoProducto"));
        ColCantidad.setCellValueFactory(new PropertyValueFactory<DetalleCompras, Integer>("Cantidad"));
        ColCostoU.setCellValueFactory(new PropertyValueFactory<DetalleCompras, Integer>("CostoUnitario"));
        ColDocumento.setCellValueFactory(new PropertyValueFactory<DetalleCompras, Integer>("NumeroDocumento"));
        
        cmbCodigo.setItems(getDetalleCompras()); //para agregar los items al combobox
        
        //para agregar los datos de los proveedores
        ComprasController comprasController = new ComprasController();
        cmbDocumento.setItems(comprasController.getCompras());
        
        ProductosController productosController = new ProductosController();
        cmbProducto.setItems(productosController.getProductos());
        
        if(comprasController.getCompras().size()==0){
            JOptionPane.showMessageDialog(null,"¡Primero ingrese una Compra!","Error al ingresar a Detalle Compras",2); 
        }
        if(productosController.getProductos().size()==0){
            JOptionPane.showMessageDialog(null,"¡Primero ingrese un Producto!","Error al ingresar a Detalle Compras",2); 
        }
        
    }
    
    //la observable list y para guardar los elementos en un array 
    private ObservableList<DetalleCompras> getDetalleCompras(){
        ArrayList<DetalleCompras> lista = new ArrayList<DetalleCompras>();
        try {
            PreparedStatement procedimiento = ConexionSql.getInstancia().getConeccion().prepareCall("{call VerDetalleCompras}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new DetalleCompras(resultado.getInt("CodigoDetalleCompras"), resultado.getInt("CodigoProducto"),
                resultado.getInt("Cantidad"),resultado.getDouble("CostoUnitario"), resultado.getInt("NumeroDocumento") ) );    
            }
            
        } catch(SQLException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Codigo del error: "+e.getErrorCode()+"\n\n"+"Detalles: \n"+e.getMessage(), "Se ha Producido un Error", 2);
        }
        
        return ListaDetalleCompras = FXCollections.observableArrayList(lista);
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
            DetalleCompras registro = new DetalleCompras();
            
            registro.setCodigoProducto(((Productos)cmbProducto.getSelectionModel().getSelectedItem()).getCodigoProducto());
            registro.setCantidad( Integer.parseInt(txtCantidad.getText() ) ); 
            registro.setNumeroDocumento( Integer.parseInt(cmbDocumento.getValue().toString() ) ); //para convetir de string a doble
            registro.setCostoUnitario(Double.parseDouble(txtPrecio.getText() ) );
        
            
            PreparedStatement procedimiento = ConexionSql.getInstancia().getConeccion().prepareCall("{call SP_InsertarDetalleCompras(?,?,?,?)}");
            procedimiento.setInt (1,registro.getCodigoProducto() );
            procedimiento.setInt(2,registro.getCantidad() );
            procedimiento.setInt(3,registro.getNumeroDocumento() );
            procedimiento.setDouble(4,registro.getCostoUnitario() );
            
            procedimiento.execute();
            
            ListaDetalleCompras.add(registro);
             
        }catch(SQLException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Codigo del error: "+e.getErrorCode()+"\n\n"+"Detalles: \n"+e.getMessage(), "Se ha Producido un Error", 2);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"¡Error en los datos introducidos! \n Ingrese los datos correctos y pruebe otra vez.","Error",2);
        }
        
    }
    
    private void Editar(){
        try{
            
            
            PreparedStatement procedimiento = ConexionSql.getInstancia().getConeccion().prepareCall("{call SP_ActualizarDetalleCompras (?,?,?,?,?,?)}");
            DetalleCompras registro = (DetalleCompras)tblDetalleCompras.getSelectionModel().getSelectedItem(); 
            
            registro.setCodigoProducto(((Productos)cmbProducto.getSelectionModel().getSelectedItem()).getCodigoProducto());
            registro.setCantidad( Integer.parseInt(txtCantidad.getText() ) ); 
            registro.setNumeroDocumento( Integer.parseInt(cmbDocumento.getValue().toString() ) );
            registro.setCostoUnitario(Double.parseDouble(txtPrecio.getText() ) );
            registro.setCodigoDetalleCompras(((DetalleCompras)cmbCodigo.getSelectionModel().getSelectedItem()).getCodigoDetalleCompras() );
            
            procedimiento.setInt(1, CantidadActual);/* aqui le agregamos el parametro que solicita el procedimiento 
            SP_ActualizarDetalleCompras si en el Query donde creo el procedimiento se explica mas la funcion*/
            procedimiento.setInt (2,registro.getCodigoDetalleCompras() ); 
            procedimiento.setInt (3,registro.getCodigoProducto() );
            procedimiento.setInt(4,registro.getCantidad() );
            procedimiento.setInt(5,registro.getNumeroDocumento() );
            procedimiento.setDouble(6,registro.getCostoUnitario() );
            
            procedimiento.execute(); 
            
        }catch(SQLException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Codigo del error: "+e.getErrorCode()+"\n\n"+"Detalles: \n"+e.getMessage(), "Se ha Producido un Error", 2);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"¡Error en los datos introducidos! \n Ingrese los datos correctos y pruebe otra vez.","Error",2);
        }
        
    }
    
        //este metodo sirve para hacer la busqueda del dato segun el codigo seleccionado en la accion seleccionar elementos
    private DetalleCompras buscarDetalleCompras(int CodigoDetalleCompras){
        DetalleCompras resultado= null;
        try {
            PreparedStatement procedimiento = ConexionSql.getInstancia().getConeccion().prepareCall("{call SP_BuscarDetalleCompras(?)}");
            procedimiento.setInt(1, CodigoDetalleCompras);
            ResultSet registro= procedimiento.executeQuery();
            while (registro.next()){
                resultado = new DetalleCompras(registro.getInt("CodigoDetalleCompras"), registro.getInt("CodigoProducto"), registro.getInt("Cantidad"),
                registro.getDouble("CostoUnitario"), registro.getInt("NumeroDocumento")  );
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
        
        cmbCodigo.setDisable(true);
        cmbDocumento.setDisable(true);
        txtCantidad.setDisable(true);
        cmbProducto.setDisable(true);
        txtPrecio.setDisable(true);
        
        btnNuevo.setText("Nuevo");
        btnEliminar.setText("Eliminar");
        btnEditar.setText("Editar");
        btnReporte.setText("Regresar");
        
 
        txtCantidad.setText("");
        cmbDocumento.setValue("");
        cmbProducto.setValue("");
        cmbCodigo.setValue("");
    }
    
    //************************************************************EVENTOS*********************************************
    
    @FXML
    private void BotonNuevo(ActionEvent event) {
        contNuevo++;
        //al ejecutarse este metodo los botones se habilitan 
        if(contNuevo==1){
            
            btnEditar.setDisable(true);
            btnReporte.setDisable(true);
            cmbCodigo.setDisable(true);
            
            cmbDocumento.setDisable(false);
            txtCantidad.setDisable(false);
            cmbProducto.setDisable(false);
            txtPrecio.setDisable(false);

            txtCantidad.setText("");
            cmbDocumento.setValue("");
            cmbProducto.setValue(""); //Inserte para resetear el combobox
            cmbCodigo.setValue("");
            
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
            if(tblDetalleCompras.getSelectionModel().getSelectedItem() != null){ 
                try{
                    //hay que preguntar si esta seguro de borrarlo, por seguridad
                    int confirmar = JOptionPane.showConfirmDialog(null,"¿En realidad quiere borrar este dato?", "Advertencia",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                    if(confirmar==JOptionPane.YES_OPTION){
                        PreparedStatement procedimiento = ConexionSql.getInstancia().getConeccion().prepareCall("{call SP_EliminarDetalleCompras (?)}");
                        procedimiento.setInt(1, ((DetalleCompras)tblDetalleCompras.getSelectionModel().getSelectedItem()).getCodigoDetalleCompras());
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
            if(tblDetalleCompras.getSelectionModel().getSelectedItem() != null){
                btnNuevo.setDisable(true);
                btnEliminar.setDisable(true);
                cmbCodigo.setDisable(true);
                cmbProducto.setValue("");
                
                btnEditar.setText("Actualizar");
                btnReporte.setText("Cancelar");
                
                CantidadActual=Integer.parseInt(txtCantidad.getText());
                /*esto es para guardar la cantidad actual antes de que el usuario la edite
                para poder restarsela a la cantidad de productos y asi obtener la existencia que estaba 
                antes de hacer una compra*/
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
    
    //este metodo sirve para mostrar el elemento selecionado en la tabla y se ejecuta al precionar un elemeto de la tabla
    @FXML
    private void seleccionarElementos(MouseEvent event){
        if(tblDetalleCompras.getSelectionModel().getSelectedItem()!=null){
            
            cmbCodigo.setDisable(false);
            cmbDocumento.setDisable(false);
            txtCantidad.setDisable(false);
            cmbProducto.setDisable(false);
            txtPrecio.setDisable(false);

            cmbCodigo.getSelectionModel().select(buscarDetalleCompras(((DetalleCompras)tblDetalleCompras.getSelectionModel().getSelectedItem()).getCodigoDetalleCompras()));
            cmbDocumento.setValue(((DetalleCompras)tblDetalleCompras.getSelectionModel().getSelectedItem()).getNumeroDocumento() );
            cmbProducto.setValue(((DetalleCompras)tblDetalleCompras.getSelectionModel().getSelectedItem()).getCodigoProducto() );
            
            txtCantidad.setText( String.valueOf( ( (DetalleCompras)tblDetalleCompras.getSelectionModel().getSelectedItem()).getCantidad() ) );
            txtPrecio.setText(String.valueOf( ( (DetalleCompras)tblDetalleCompras.getSelectionModel().getSelectedItem() ).getCostoUnitario () ) );
        }
    }
    
    @FXML
    private void BotonRegresar(ActionEvent event){
        if(contEditar==1){//si ContEditar vale uno quiere decir que el boton de reporte tiene que hacer la accion de cancelar
            EstadoInicial();
            contEditar=0;
            CantidadActual=0; //para poner la cantidad actual en cero si el usuario preciona cancelar
        }
        else{
           escenarioPrincipal.Compras();
        }  
    }
    
    @FXML
    private void RegresarInicio(MouseEvent event) {
        escenarioPrincipal.menuPrincipal();
    }
}
