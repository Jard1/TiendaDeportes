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
import org.josuerobledo.bean.Tallas;
import org.josuerobledo.db.ConexionSql;
import org.josuerobledo.sistema.Principal;


public class TallasController implements Initializable {
    
    @FXML private TableColumn colCodigoTalla;
    @FXML private Button btnReporte;
    @FXML private TableColumn colTalla;
    @FXML private Button btnEliminar;
    @FXML private TextField txtTalla;
    @FXML private Button btnNuevo;
    @FXML private TableView tblTallas;
    @FXML private ComboBox cmbTallas;
    @FXML private ImageView imaInicio;
    @FXML private Button btnEditar;
    private ObservableList<Tallas> ListaTallas;
    private Principal escenarioPrincipal;
    private byte contNuevo=0;
    private byte contEditar=0;
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /*Como este metodo se ejecuta primero, entonces en un principio
        estos botones estan deshabilitados*/
        cmbTallas.setDisable(false);
        txtTalla.setDisable(true);
        cargarDatos(); //ejecuta el metodo de cargar datos 
        
    }

    
     //metodo para cargar los datos a la tabla
    private void cargarDatos(){ 
        tblTallas.setItems(getTallas()); //agrega los datos a la tabla y ejecuta la observable list
        colCodigoTalla.setCellValueFactory(new PropertyValueFactory<Tallas, Integer>("CodigoTalla"));
        colTalla.setCellValueFactory(new PropertyValueFactory<Tallas, String>("DescripcionTalla"));
        
        cmbTallas.setItems(getTallas()); //para agregar los items al combobox
    }
    
    
    //la obsetvable list para guardar los elementos en un array 
    protected ObservableList<Tallas> getTallas(){
        ArrayList<Tallas> lista = new ArrayList<Tallas>();
        try {
            PreparedStatement procedimiento = ConexionSql.getInstancia().getConeccion().prepareCall("{call VerTalla}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Tallas(resultado.getInt("CodigoTalla"), resultado.getString("DescripcionTalla")));
                
            }
            
        } catch(SQLException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Codigo del error: "+e.getErrorCode()+"\n\n"+"Detalles: \n"+e.getMessage(), "Se ha Producido un Error", 2);
            
        }
        
        return ListaTallas = FXCollections.observableArrayList(lista);
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
        Tallas registro = new Tallas();
        registro.setDescripcionTalla(txtTalla.getText() );
        
        try{
            PreparedStatement procedimiento = ConexionSql.getInstancia().getConeccion().prepareCall("{call SP_InsertarTalla(?)}");
            procedimiento.setString (1,registro.getDescripcionTalla());
            procedimiento.execute();
            ListaTallas.add(registro);
             
        }catch(SQLException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Codigo del error: "+e.getErrorCode()+"\n\n"+"Detalles: \n"+e.getMessage(), "Se ha Producido un Error", 2);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"¡Error en los datos introducidos! \n Ingrese los datos correctos y pruebe otra vez.","Error",2);
        }
    }
    
    //Metodo para poder editar los datos
    private void Editar(){
        try{
            PreparedStatement procedimiento = ConexionSql.getInstancia().getConeccion().prepareCall("{call SP_ActualizarTalla (?,?)}");
            Tallas registro = (Tallas)tblTallas.getSelectionModel().getSelectedItem(); 
            
            registro.setDescripcionTalla( txtTalla.getText() ); 
            registro.setCodigoTalla(((Tallas)cmbTallas.getSelectionModel().getSelectedItem()).getCodigoTalla());
            procedimiento.setInt (1,registro.getCodigoTalla()); 
            procedimiento.setObject(2, registro.getDescripcionTalla()); 
            procedimiento.execute(); 
        }catch(SQLException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Codigo del error: "+e.getErrorCode()+"\n\n"+"Detalles: \n"+e.getMessage(), "Se ha Producido un Error", 2);
        }catch(Exception e){
             JOptionPane.showMessageDialog(null,"¡Error en los datos introducidos! \n Ingrese los datos correctos y pruebe otra vez.","Error",2);
        }
        
        
    }
 
    //este metodo sirve para hacer la busqueda del dato segun el codigo seleccionado en la accion seleccionar elementos
    
    private Tallas buscarTalla(int CodigoTalla){
        Tallas resultado= null;
        try {
            PreparedStatement procedimiento = ConexionSql.getInstancia().getConeccion().prepareCall("{call SP_BuscarTalla(?)}");
            procedimiento.setInt(1, CodigoTalla);
            ResultSet registro= procedimiento.executeQuery();
            while (registro.next()){
                resultado = new Tallas(registro.getInt("CodigoTalla"), registro.getString("DescripcionTalla"));
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
        
        txtTalla.setDisable(true);
        cmbTallas.setDisable(true);
        btnNuevo.setText("Nuevo");
        btnEliminar.setText("Eliminar");
        btnEditar.setText("Editar");
        btnReporte.setText("Reporte");
        
        txtTalla.setText("");
        cmbTallas.setDisable(false);
        cmbTallas.setValue(null);
       // cmbTallas.setValue("");  si le pongo esto da error porque no puede comvertir de String a Tallas
    }
    
    
    
    //*********************************************Eventos*************************************************
    @FXML
    private void BotonNuevo(ActionEvent event) {
        contNuevo++;
        //al ejecutarse este metodo los botones se habilitan 
        if(contNuevo==1){
            
            btnEditar.setDisable(true);
            btnReporte.setDisable(true);
            cmbTallas.setDisable(true);
            
            txtTalla.setDisable(false);
            txtTalla.setText("");
            cmbTallas.setValue(null); //Inserte para resetear el combobox
            btnNuevo.setText("Guardar");
            btnEliminar.setText("Cancelar");
            /*btnEditar.setText("Editar");
            btnReporte.setText("Reporte");*/
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
            if(tblTallas.getSelectionModel().getSelectedItem() != null){ 
                try{
                    //hay que preguntar si esta seguro de borrarlo, por seguridad
                    int confirmar = JOptionPane.showConfirmDialog(null,"¿En realidad quiere borrar este dato?", "Advertencia",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                    if(confirmar==JOptionPane.YES_OPTION){
                        PreparedStatement procedimiento = ConexionSql.getInstancia().getConeccion().prepareCall("{call SP_EliminarTalla (?)}");
                        procedimiento.setInt(1, ((Tallas)tblTallas.getSelectionModel().getSelectedItem()).getCodigoTalla());
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
            if(tblTallas.getSelectionModel().getSelectedItem() != null){
                btnNuevo.setDisable(true);
                btnEliminar.setDisable(true);
                cmbTallas.setDisable(true);
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
        if(tblTallas.getSelectionModel().getSelectedItem()!=null){
            cmbTallas.setDisable(false);
            txtTalla.setDisable(false);
            cmbTallas.getSelectionModel().select(buscarTalla(((Tallas)tblTallas.getSelectionModel().getSelectedItem()).getCodigoTalla()));
            txtTalla.setText(((Tallas)tblTallas.getSelectionModel().getSelectedItem()).getDescripcionTalla());
        }
        
    }
    

    @FXML
    private void RegresarInicio(MouseEvent event) {
        /*En este metodo no puede ser ActionEvent como los otros porque las imagenes
        no soportan OnAction y al usar el OnMouseClicked en el fxml este metodo tiene que
        ser MouseEvent
        */
        escenarioPrincipal.menuPrincipal();
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
        
        if(cmbTallas.getSelectionModel().getSelectedItem()!=null){
            txtTalla.setText(buscarTalla( ((Tallas)cmbTallas.getSelectionModel().getSelectedItem()).getCodigoTalla() ).getDescripcionTalla() );
            txtTalla.setDisable(false);
        }
        
    }
}
