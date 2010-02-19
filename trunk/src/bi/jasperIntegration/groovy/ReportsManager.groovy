package bi.jasperIntegration.groovy

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import java.io.File;
import java.sql.Connection;

import groovy.util.slurpersupport.GPathResult;


class ReportsManager {
	def directory;
	
	void createReports(String paramXml, Connection conn) {
		GPathResult reports = new XmlSlurper().parseText( paramXml);
		
		directory = reports.'@reportsDir';
		
		reports.each {
			GPathResult report = it;
			JasperReport jr = loadReport(report.'@name');
			
			report.param.each {
				println it;
			}
		}
	}
	
	JasperReport loadReport(String reportName){
		File f = new File(directory, reportName + '.jrxml');
		jasperDesign = JRXmlLoader.load(jrxmlInputStream);
		return JasperCompileManager.compileReport(jasperDesign);
	}
	
}
