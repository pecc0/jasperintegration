package bi.jasperIntegration.groovy

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;

import groovy.util.slurpersupport.GPathResult;


class ReportsManager {
	String directory;
	
	void createReports(String reportsXml, Connection conn) {
		GPathResult reports = new XmlSlurper().parse( reportsXml);
		
		directory = reports.'@reportsDir';
		println "dir= $directory";
		
		reports.children().each {
			//GPathResult report = it;
			println "name=${it.'@name'}";
			JasperReport jr = loadReport(it.'@name');
			
			it.param.each {
				println it;
			}
		}
	}
	
	JasperReport loadReport(reportName){
		File f = new File(directory, reportName.toString() + '.jrxml');
		def jasperDesign = JRXmlLoader.load(new FileInputStream(f));
		return JasperCompileManager.compileReport(jasperDesign);
	}
	
}


