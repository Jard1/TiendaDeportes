
package org.josuerobledo.db;

import java.sql.Connection;
import java.sql.DriverManager;
import com.microsoft.sqlserver.jdbc.SQLServerDriver;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;


public class ConexionSql {
    
    private Connection coneccion;
    private static ConexionSql instancia;
    private Statement sentencia;
    //private Connection conexion;
    
    public ConexionSql() {
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
            coneccion = DriverManager.getConnection("jdbc:sqlserver://localhost:0;instanceName=MSSQLSERVER;dataBaseName=DBSports2014453","sa","123");
           // coneccion = DriverManager.getConnection("jdbc:sqlserver://LABC26-17:0;instanceName=SQLKINALQUINTO;dataBaseName=DBSports2014453","2014453","guatemala");
        
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
        } catch(InstantiationException e) {
            e.printStackTrace();
            } catch(IllegalAccessException e){
                e.printStackTrace();
            } catch(SQLException e) {
                e.printStackTrace();
            }
    }
    
    
    /*public ConexionSql(){ //public Connection CadenaConexion(){
        coneccion=null;
        
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");//.newInstance(); 
            coneccion=DriverManager.getConnection("jdbc:sqlserver://LABC26-17:0;instanceName=SQLKINALQUINTO;dataBaseName=DBSports2014453;user=2014453;password=guatemala");
            //cn=DriverManager.getConnection("jdbc:sqlserver://Arturo:0;instanceName=SQLKINALQUINTO;dataBaseName=DBSports2014453","sa","1234");
            
            /* por si acaso:
            * jdbc:sqlserver://LABC26-17\SQLKINALQUINTO:0;databaseName=DBSports2014453 [2014453 on Default schema]
            
            
        }catch(ClassNotFoundException e) {
            e.printStackTrace();
        } catch(SQLException e) {
            e.printStackTrace();
        }
        
        //return cn;
    }*/
    
    public static ConexionSql getInstancia(){
        if(instancia==null){
            instancia = new ConexionSql();
        }return instancia;
        
    }

    public Connection getConeccion() {
        return coneccion;
    }

    public void setConeccion(Connection coneccion) {
        this.coneccion = coneccion;
    }
    
    

    /*public String toString(){
        return getCodigoCategoria();
    }
    */
    
    
    
}
