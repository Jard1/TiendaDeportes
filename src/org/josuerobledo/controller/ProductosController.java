
package org.josuerobledo.controller;

import java.net.URL;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.josuerobledo.bean.Categoria;
import org.josuerobledo.bean.Marcas;
import org.josuerobledo.bean.Productos;
import org.josuerobledo.bean.Tallas;
import org.josuerobledo.db.ConexionSql;
import org.josuerobledo.reporte.GenerarReportes;
import org.josuerobledo.sistema.Principal;


public class ProductosController implements Initializable {

    @FXML private TableColumn colMarca; 
    @FXML private ComboBox cmbMarca; 
    @FXML private TableColumn colPrecioU; 
    @FXML private Button btnNuevo;
    @FXML private TableColumn colPrecioMayor; 
    @FXML private ComboBox cmbCodigo; 
    @FXML private TableColumn colNombre; 
    @FXML private ComboBox cmbCategoria; 
    @FXML private TableColumn colCategoria; 
    @FXML private TableColumn colPrecio12; 
    @FXML private Button btnEditar;
    @FXML private TableColumn colExistencia; 
    @FXML private TextField txtNombre; 
    @FXML private TableView tblProductos;
    @FXML private TableColumn colTalla; 
    @FXML private Button btnEliminar;
    @FXML private ImageView imaInicio;
    @FXML private ComboBox cmbTalla; 
    @FXML private Button btnReporte;
    @FXML private TableColumn colCodigo; 
    @FXML private ComboBox cmbGanancia;
    @FXML private ObservableList<Productos> ListaProducto;
    private Principal escenarioPrincipal;
    private byte contNuevo;
    private byte contEditar;
     

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txtNombre.setDisable(true);
        cmbMarca.setDisable(true);
        cmbCategoria.setDisable(true);
        cmbTalla.setDisable(true);
        cmbGanancia.setDisable(true);
        cargarDatos();
        
        //para que en el combobox de ganancia tenga los items con los numeros del uno al 100
        for(byte i=0;i<=100 ;i++){
            cmbGanancia.getItems().addAll(i);
        }
        
        
    }
    
    //metodo para cargar los datos a la tabla
    private void cargarDatos(){ 
        tblProductos.setItems(getProductos()); //agrega los datos a la tabla y ejecuta la observable list
        colCodigo.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("CodigoProducto"));
        colNombre.setCellValueFactory(new PropertyValueFactory<Productos, String>("DescripcionProducto"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("CodigoCategoria"));
        colMarca.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("CodigoMarca") );
        colTalla.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("CodigoTalla") );
        colPrecioU.setCellValueFactory(new PropertyValueFactory<Productos, Double>("PrecioUnitario") );
        colPrecio12.setCellValueFactory(new PropertyValueFactory<Productos, Double>("PrecioPorDocena") );
        colPrecioMayor.setCellValueFactory(new PropertyValueFactory<Productos, Double>("PrecioPorMayor") );
        colExistencia.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("Existencia") );
        
        cmbCodigo.setItems(getProductos()); //para agregar los items al combobox
        
        
        //para agregar los datos de la llaves foraneas en los combobox
        CategoriasController categoriaController = new CategoriasController();
        cmbCategoria.setItems(categoriaController.getCategorias() );
        
        MarcasController marcaController = new MarcasController();
        cmbMarca.setItems(marcaController.getMarcas() );
        
        TallasController tallasController = new TallasController();
        cmbTalla.setItems(tallasController.getTallas() );
        
        
        //estos mensajes de error los da si en categorias, marcas o en tallas no hay datos
        if(categoriaController.getCategorias().size()==0){
            JOptionPane.showMessageDialog(null,"¡Primero ingrese una Categoria!","Error al ingresar a productos",2);  
        }
        if(marcaController.getMarcas().size()==0){
            JOptionPane.showMessageDialog(null,"¡Primero ingrese una Marca!","Error al ingresar a productos",2);  
        }
        if(tallasController.getTallas().size()==0){
           JOptionPane.showMessageDialog(null,"¡Primero ingrese una Talla!","Error al ingresar a productos",2);  
        }
        
    }
    
    //para guardar los elementos en un array 
    protected ObservableList<Productos> getProductos(){
        ArrayList<Productos> lista = new ArrayList<Productos>();
        try {
            PreparedStatement procedimiento = ConexionSql.getInstancia().getConeccion().prepareCall("{call VerProducto}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Productos(resultado.getInt("CodigoProducto"), resultado.getString("DescripcionProducto"),
                resultado.getInt("Existencia"), resultado.getDouble("PrecioUnitario"), resultado.getDouble("PrecioPorDocena"),resultado.getDouble("PrecioPorMayor"),
                resultado.getInt("CodigoCategoria"), resultado.getInt("CodigoMarca"), resultado.getInt("CodigoTalla"),resultado.getInt("Ganancia") ) );
                 
            }
            
        } catch(SQLException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Codigo del error: "+e.getErrorCode()+"\n\n"+"Detalles: \n"+e.getMessage(), "Se ha Producido un Error", 2);
            
        }
        
        return ListaProducto = FXCollections.observableArrayList(lista);
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
            Productos registro = new Productos();
            registro.setDescripcionProducto(txtNombre.getText() );

            registro.setCodigoCategoria( ((Categoria)cmbCategoria.getSelectionModel().getSelectedItem()).getCodigoCategoria() ); //esto es una prueba con los combobox        registro.setCodigoMarca(1);//Integer.parseInt(cmbMarca.getValue().toString() )  //Integer.parseInt(cmbCategoria.getValue().toString() ) //esto es una prueba con los combobox
            registro.setCodigoMarca( ((Marcas)cmbMarca.getSelectionModel().getSelectedItem()).getCodigoMarca() );  //Integer.parseInt(cmbCategoria.getValue().toString() ) //esto es una prueba con los combobox
            registro.setCodigoTalla( ((Tallas)cmbTalla.getSelectionModel().getSelectedItem()).getCodigoTalla() );   //esto es una prueba con los combobox
            registro.setGanancia( Integer.parseInt(cmbGanancia.getValue().toString() ) );
            
            PreparedStatement procedimiento = ConexionSql.getInstancia().getConeccion().prepareCall("{call SP_InsertarProducto(?,?,?,?,?)}");
            procedimiento.setString (1,registro.getDescripcionProducto());
            procedimiento.setInt(2,registro.getCodigoCategoria() );
            procedimiento.setInt(3,registro.getCodigoMarca() );
            procedimiento.setInt(4,registro.getCodigoTalla() );
            procedimiento.setInt(5,registro.getGanancia() );
            procedimiento.execute();
            ListaProducto.add(registro);
             
        }catch(SQLException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Codigo del error: "+e.getErrorCode()+"\n\n"+"Detalles: \n"+e.getMessage(), "Se ha Producido un Error", 2);
        }catch(Exception e){
           JOptionPane.showMessageDialog(null,"¡Error en los datos introducidos! \n Ingrese los datos correctos y pruebe otra vez.","Error",2);
        }
    }
    
    private void Editar(){
        try{
            PreparedStatement procedimiento = ConexionSql.getInstancia().getConeccion().prepareCall("{call SP_ActualizarProducto (?,?,?,?,?,?)}");
            Productos registro = (Productos)tblProductos.getSelectionModel().getSelectedItem(); 
            
            registro.setDescripcionProducto(txtNombre.getText()); 
            registro.setCodigoCategoria(((Categoria)cmbCategoria.getSelectionModel().getSelectedItem()).getCodigoCategoria());
            registro.setCodigoMarca(((Marcas)cmbMarca.getSelectionModel().getSelectedItem()).getCodigoMarca());
            registro.setCodigoTalla(((Tallas)cmbTalla.getSelectionModel().getSelectedItem()).getCodigoTalla());
            registro.setGanancia( Integer.parseInt(cmbGanancia.getValue().toString() ) );
            
            registro.setCodigoProducto(((Productos)cmbCodigo.getSelectionModel().getSelectedItem()).getCodigoProducto());
            
            procedimiento.setInt (1,registro.getCodigoProducto() ); 
            procedimiento.setString (2, registro.getDescripcionProducto() ); 
            procedimiento.setInt(3,registro.getCodigoCategoria());
            procedimiento.setInt(4,registro.getCodigoMarca() );
            procedimiento.setInt(5, registro.getCodigoTalla() );
            procedimiento.setInt(6,registro.getGanancia() );
            
            procedimiento.execute(); 
            
        }catch(SQLException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Codigo del error: "+e.getErrorCode()+"\n\n"+"Detalles: \n"+e.getMessage(), "Se ha Producido un Error", 2);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"¡Error en los datos introducidos! \n Ingrese los datos correctos y pruebe otra vez.","Error",2);
        }
        
    }
    
    //este metodo sirve para hacer la busqueda del dato segun el codigo seleccionado en la accion seleccionar elementos
    private Productos buscarProductos(int CodigoProducto){
        Productos resultado= null;
        try {
            PreparedStatement procedimiento = ConexionSql.getInstancia().getConeccion().prepareCall("{call SP_BuscarProducto(?)}");
            procedimiento.setInt(1, CodigoProducto);
            ResultSet registro= procedimiento.executeQuery();
            while (registro.next()){
                resultado = new Productos(registro.getInt("CodigoProducto"), registro.getString("DescripcionProducto"),
                registro.getInt("Existencia"), registro.getDouble("PrecioUnitario"), registro.getDouble("PrecioPorDocena"),registro.getDouble("PrecioPorMayor"),
                registro.getInt("CodigoCategoria"), registro.getInt("CodigoMarca"), registro.getInt("CodigoTalla"),registro.getInt("Ganancia")  );
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
        cmbMarca.setDisable(true);
        cmbCategoria.setDisable(true);
        cmbTalla.setDisable(true);
        cmbGanancia.setDisable(true);

        
        btnNuevo.setText("Nuevo");
        btnEliminar.setText("Eliminar");
        btnEditar.setText("Editar");
        btnReporte.setText("Reporte");
        
        txtNombre.setText("");
        cmbMarca.setValue("");
        cmbCategoria.setValue("");
        cmbTalla.setValue("");
        cmbCodigo.setValue(null);
        cmbGanancia.setValue("");
    }
    
    private void Reporte(){
        if(tblProductos.getSelectionModel().getSelectedItem()!=null){
            
            Map parametro = new HashMap();

            GenerarReportes.mostrarReporte("ReporteProducto.jasper", "Reporte de productos", null);
            
        }else{
            JOptionPane.showMessageDialog(null,"Tiene que seleccionar un elemento para hacer un Reporte", "ERROR",2);
        }
    }
    
    
    //**************************************Eventos***************************************
    @FXML
    private void BotonNuevo(ActionEvent event) {
        contNuevo++;
        //al ejecutarse este metodo los botones se habilitan 
        if(contNuevo==1){
            cmbCodigo.setDisable(true);
            btnReporte.setDisable(true);
            btnEditar.setDisable(true);
            
            txtNombre.setDisable(false);
            cmbMarca.setDisable(false);
            cmbCategoria.setDisable(false);
            cmbTalla.setDisable(false);
            btnEliminar.setDisable(false);
            cmbGanancia.setDisable(false);
            
            btnNuevo.setText("Guardar");
            btnEliminar.setText("Cancelar");
            

            txtNombre.setText("");
            cmbMarca.setValue("");
            cmbCategoria.setValue("");
            cmbTalla.setValue("");
            cmbGanancia.setValue("");
        }
        else{
            //la accion de guardar
            contNuevo=0;
            
            AgregarDatos();
            cargarDatos();
            EstadoInicial();
        }
    }
    
    @FXML
    private void RegresarInicio() {
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
            if(tblProductos.getSelectionModel().getSelectedItem() != null){ 
                try{
                    //hay que preguntar si esta seguro de borrarlo, por seguridad
                    int confirmar = JOptionPane.showConfirmDialog(null,"¿En realidad quiere borrar este dato?", "Advertencia",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                    if(confirmar==JOptionPane.YES_OPTION){
                        PreparedStatement procedimiento = ConexionSql.getInstancia().getConeccion().prepareCall("{call SP_EliminarProducto (?)}");
                        procedimiento.setInt(1, ((Productos)tblProductos.getSelectionModel().getSelectedItem()).getCodigoProducto());
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
            if(tblProductos.getSelectionModel().getSelectedItem() != null){
                btnNuevo.setDisable(true);
                btnEliminar.setDisable(true);
                cmbCodigo.setDisable(true);
                
                btnEditar.setText("Actualizar");
                btnReporte.setText("Cancelar");
                cmbMarca.setValue("");
                cmbCategoria.setValue("");        
                cmbTalla.setValue("");      
                cmbGanancia.setValue("");
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
        if(tblProductos.getSelectionModel().getSelectedItem()!=null){
            
            cmbCodigo.setDisable(false);
            txtNombre.setDisable(false);
            cmbMarca.setDisable(false);
            cmbCategoria.setDisable(false);
            cmbTalla.setDisable(false);
            cmbGanancia.setDisable(false);
            
            cmbCodigo.getSelectionModel().select(buscarProductos(((Productos)tblProductos.getSelectionModel().getSelectedItem()).getCodigoProducto()));
            
            txtNombre.setText(((Productos)tblProductos.getSelectionModel().getSelectedItem() ).getDescripcionProducto());
            
            cmbMarca.setValue(((Productos)tblProductos.getSelectionModel().getSelectedItem()).getCodigoMarca());//inserte para selecionar la marca
            cmbCategoria.setValue(((Productos)tblProductos.getSelectionModel().getSelectedItem()).getCodigoCategoria());//inserte para selecionar la categoria
            cmbTalla.setValue(((Productos)tblProductos.getSelectionModel().getSelectedItem()).getCodigoTalla());//inserte para selecionar la talla
            cmbGanancia.setValue(((Productos)tblProductos.getSelectionModel().getSelectedItem()).getGanancia());
            
        }
    }
    
    @FXML
    private void seleccionarCombobox(){

        /*solo junte esto:
            explicacion en los controladores de Categorias, Marcas o Tallas
        */
        

        if(cmbCodigo.getSelectionModel().getSelectedItem()!=null){
            
            txtNombre.setDisable(false);
            cmbMarca.setDisable(false);
            cmbCategoria.setDisable(false);
            cmbTalla.setDisable(false);
            cmbGanancia.setDisable(false);
            
            txtNombre.setText( buscarProductos( ((Productos)cmbCodigo.getSelectionModel().getSelectedItem()).getCodigoProducto() ).getDescripcionProducto() );
            
            cmbMarca.setValue(buscarProductos( ((Productos)cmbCodigo.getSelectionModel().getSelectedItem()).getCodigoProducto() ).getCodigoMarca() );
            cmbCategoria.setValue(buscarProductos( ((Productos)cmbCodigo.getSelectionModel().getSelectedItem()).getCodigoProducto() ).getCodigoCategoria() );
            cmbTalla.setValue(buscarProductos( ((Productos)cmbCodigo.getSelectionModel().getSelectedItem()).getCodigoProducto() ).getCodigoTalla() );
            cmbGanancia.setValue(buscarProductos( ((Productos)cmbCodigo.getSelectionModel().getSelectedItem()).getCodigoProducto() ).getGanancia() );
        }
        
    }
    
}
