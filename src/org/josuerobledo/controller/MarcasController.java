
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
import org.josuerobledo.bean.Marcas;
import org.josuerobledo.db.ConexionSql;
import org.josuerobledo.sistema.Principal;


public class MarcasController implements Initializable {
    @FXML private Button btnReporte;
    @FXML private TableColumn colMarca;
    @FXML private ComboBox cmbMarcas;
    @FXML private TableColumn colCodigoMarca;
    @FXML private Button btnEliminar;
    @FXML private Button btnNuevo;
    @FXML private TextField txtDescripcion;
    @FXML private Button btnEditar;
    @FXML private TableView <Marcas> tvlMarcas;
    @FXML private ImageView imaInicio;
    private ObservableList<Marcas> ListaMarcas;
    private Principal escenarioPrincipal;
    private byte contNuevo;
    private byte contEditar;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /*Como este metodo se ejecuta primero, entonces en un principio
        estos botones estan deshabilitados*/
        txtDescripcion.setDisable(true);
        cargarDatos(); //ejecuta el metodo de cargar datos
    } 
    
     //metodo para cargar los datos a la tabla
    private void cargarDatos(){ 
        tvlMarcas.setItems(getMarcas()); //agrega los datos a la tabla y ejecuta la observable list
        colCodigoMarca.setCellValueFactory(new PropertyValueFactory<Marcas, Integer>("CodigoMarca"));
        colMarca.setCellValueFactory(new PropertyValueFactory<Marcas, String>("DescripcionMarcas"));
        
        cmbMarcas.setItems(getMarcas()); //para agregar los items al combobox
    }
    
    // la obserbable list y para guardar los elementos en un array 
    protected ObservableList<Marcas> getMarcas(){
        ArrayList<Marcas> lista = new ArrayList<Marcas>();
        try {
            PreparedStatement procedimiento = ConexionSql.getInstancia().getConeccion().prepareCall("{call VerMarca}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Marcas(resultado.getInt("CodigoMarca"), resultado.getString("DescripcionMarcas")));
                    
            }
            
        } catch(SQLException e){
            e.printStackTrace(); 
            JOptionPane.showMessageDialog(null,"Codigo del error: "+e.getErrorCode()+"\n\n"+"Detalles: \n"+e.getMessage(), "Se ha Producido un Error", 2);
        }
        
        return ListaMarcas = FXCollections.observableArrayList(lista);
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
        Marcas registro = new Marcas();
        registro.setDescripcionMarcas(txtDescripcion.getText() );
        
        try{
            PreparedStatement procedimiento = ConexionSql.getInstancia().getConeccion().prepareCall("{call SP_InsertarMarca(?)}");
            procedimiento.setString (1,registro.getDescripcionMarcas());
            procedimiento.execute();
            ListaMarcas.add(registro);
             
        }catch(SQLException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Codigo del error: "+e.getErrorCode()+"\n\n"+"Detalles: \n"+e.getMessage(), "Se ha Producido un Error", 2);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"¡Error en los datos introducidos! \n Ingrese los datos correctos y pruebe otra vez.","Error",2);
        }
    }
    
    //Para editar los datos
    private void Editar(){
        try{
            PreparedStatement procedimiento = ConexionSql.getInstancia().getConeccion().prepareCall("{call SP_ActualizarMarca (?,?)}");
            Marcas registro = (Marcas)tvlMarcas.getSelectionModel().getSelectedItem(); 
            registro.setDescripcionMarcas(txtDescripcion.getText()); 
            registro.setCodigoMarca(((Marcas)cmbMarcas.getSelectionModel().getSelectedItem()).getCodigoMarca());
            procedimiento.setInt (1,registro.getCodigoMarca()); 
            procedimiento.setString (2, registro.getDescripcionMarcas()); 
            procedimiento.execute(); 
        }catch(SQLException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Codigo del error: "+e.getErrorCode()+"\n\n"+"Detalles: \n"+e.getMessage(), "Se ha Producido un Error", 2);
        }catch(Exception e){
             JOptionPane.showMessageDialog(null,"¡Error en los datos introducidos! \n Ingrese los datos correctos y pruebe otra vez.","Error",2);
        }
        
    }
    
    
     //este metodo sirve para hacer la busqueda del dato segun el codigo seleccionado en la accion seleccionar elementos
    private Marcas buscarMarca(int CodigoMarca){
        Marcas resultado= null;
        try {
            PreparedStatement procedimiento = ConexionSql.getInstancia().getConeccion().prepareCall("{call SP_BuscarMarca(?)}");
            procedimiento.setInt(1, CodigoMarca);
            ResultSet registro= procedimiento.executeQuery();
            while (registro.next()){
                resultado = new Marcas(registro.getInt("CodigoMarca"), registro.getString("DescripcionMarcas"));
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
        
        txtDescripcion.setDisable(true);
        cmbMarcas.setDisable(false);
        
        btnNuevo.setText("Nuevo");
        btnEliminar.setText("Eliminar");
        btnEditar.setText("Editar");
        btnReporte.setText("Reporte");
        
        txtDescripcion.setText("");
        cmbMarcas.setValue(null);
        //cmbMarcas.setValue("");  si le pongo esto da error porque no puede comvertir de String a Marcas
    }
    
     
    
    
   //************************************Eventos**************************************
    @FXML
    private void BotonNuevo(ActionEvent event){
        //al ejecutarse este metodo los botones se habilitan 
        contNuevo++;
        //al ejecutarse este metodo los botones se habilitan 
        if(contNuevo==1){
            
            btnEditar.setDisable(true);
            btnReporte.setDisable(true);
            cmbMarcas.setDisable(true);
            txtDescripcion.setDisable(false);
            cmbMarcas.setValue(null); //Inserte para resetear el combobox
            txtDescripcion.setText("");
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
            if(tvlMarcas.getSelectionModel().getSelectedItem() != null){ 
                try{
                    //hay que preguntar si esta seguro de borrarlo, por seguridad
                    int confirmar = JOptionPane.showConfirmDialog(null,"¿En realidad quiere borrar este dato?", "Advertencia",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                    if(confirmar==JOptionPane.YES_OPTION){
                        PreparedStatement procedimiento = ConexionSql.getInstancia().getConeccion().prepareCall("{call SP_EliminarMarca(?)}");
                        procedimiento.setInt(1, ((Marcas)tvlMarcas.getSelectionModel().getSelectedItem()).getCodigoMarca());
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
            if(tvlMarcas.getSelectionModel().getSelectedItem() != null){
                btnNuevo.setDisable(true);
                btnEliminar.setDisable(true);
                cmbMarcas.setDisable(true);
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
         if(tvlMarcas.getSelectionModel().getSelectedItem()!=null){
             
            cmbMarcas.setDisable(false);
            txtDescripcion.setDisable(false);
            cmbMarcas.getSelectionModel().select(buscarMarca(((Marcas)tvlMarcas.getSelectionModel().getSelectedItem()).getCodigoMarca()));
            txtDescripcion.setText(((Marcas)tvlMarcas.getSelectionModel().getSelectedItem()).getDescripcionMarcas());
         }
    }
    
    @FXML
    private void RegresarInicio(MouseEvent  event){
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
        if(cmbMarcas.getSelectionModel().getSelectedItem()!=null){
            txtDescripcion.setText(buscarMarca( ((Marcas)cmbMarcas.getSelectionModel().getSelectedItem()).getCodigoMarca() ).getDescripcionMarcas() );
            txtDescripcion.setDisable(false);
        } 
    }
}
