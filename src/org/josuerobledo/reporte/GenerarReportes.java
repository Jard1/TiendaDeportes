
package org.josuerobledo.reporte;

import net.sf.jasperreports.engine.util.JRLoader;//carga el reporte
import net.sf.jasperreports.engine.JasperReport;//crea los informes para exportarlos
import net.sf.jasperreports.engine.JasperPrint; //para imprimir
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.view.JasperViewer;
import org.josuerobledo.db.ConexionSql;
import java.util.Map;
import java.io.InputStream;

public class GenerarReportes {
    
    	public static void mostrarReporte(String nombreReporte, String Titulo, Map parametros){
		InputStream reporte = GenerarReportes.class.getResourceAsStream(nombreReporte);
		try{
			JasperReport reporteMaestro = (JasperReport)JRLoader.loadObject(reporte);
			JasperPrint reporteImpreso = JasperFillManager.fillReport(reporteMaestro,parametros,ConexionSql.getInstancia().getConeccion() );
			JasperViewer visor = new JasperViewer(reporteImpreso, false);
			
			visor.setTitle(Titulo);
			
			visor.setVisible(true);
		
		}catch(Exception e){
			e.printStackTrace();
		}
	
	}
    
}
