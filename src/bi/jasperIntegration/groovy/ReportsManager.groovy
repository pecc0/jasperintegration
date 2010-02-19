package bi.jasperIntegration.groovy

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.*;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.engine.JasperPrint;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Connection;

import groovy.util.slurpersupport.GPathResult;


class ReportsManager {
	String directory;
	
	void createReports(String reportsXml, Connection conn) {
		GPathResult reports = new XmlSlurper().parse( reportsXml);
		
		directory = reports.'@reportsDir';
		println "dir= $directory";
		String outputDir = reports.'@outputDir';
		
		reports.report.each {
			//GPathResult report = it;
			println "name=${it.'@name'}";
			def reportName = it.'@name';
			
			JasperReport jr = loadReport(reportName);
			def parameters = [:];
			it.param.each {
				println "param ${it.'@key'}=${it.'@value'}";
				parameters[it.'@key'] = it.'@value';
			}
			JasperPrint jasperPrint = JasperFillManager.fillReport(jr, parameters, conn);
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			
			JRAbstractExporter exporter = null;
			
			def reportFormat = it.format.toString();
			
			String outputFileName = it.outputName; 
			if (outputFileName.length() == 0) {
				outputFileName = reportName
			}
			//CSV
			if("CSV".equalsIgnoreCase(reportFormat)) {
				println("CSV report exported");
				String delimiter = (String)it.delimiter;
				exporter = new JRCsvExporter();
				exporter.setParameter(JRCsvExporterParameter.FIELD_DELIMITER, delimiter);					
				//PDF
			} else if ("PDF".equalsIgnoreCase(reportFormat)) {
				println("PDF report exported");
				exporter = new JRPdfExporter();
				//XLS
			} else if ("XLS".equalsIgnoreCase(reportFormat)) {
				println("XLS report exported");
				exporter = new JRXlsExporter();					
				//HTML
			} else if ("HTML".equalsIgnoreCase(reportFormat)) {
				println("HTML report exported");
				exporter = new JRHtmlExporter();
				//TXT
			} else if ("TXT".equalsIgnoreCase(reportFormat)) {
				println("TXT report exported");
				exporter = new JRTextExporter();
				//RTF
			} else if ("RTF".equalsIgnoreCase(reportFormat)) {
				println("RTF report exported");
				exporter = new JRRtfExporter();
			}
			else{
				println("Error: Format type for " + reportName + " is missing (or not correct):"+reportFormat);
			}
			
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);	
			exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outputFileName);				
			exporter.exportReport();	
			
			saveReport(baos.toByteArray(), new File(outputDir, "$outputFileName.${reportFormat.toLowerCase()}"))
		}
	}
	
	JasperReport loadReport(reportName){
		File f = new File(directory, reportName.toString() + '.jrxml');
		def jasperDesign = JRXmlLoader.load(new FileInputStream(f));
		return JasperCompileManager.compileReport(jasperDesign);
	}
	
	void saveReport(byte[] report, File f) {
		println f;
		FileOutputStream fos = new FileOutputStream(f);
		fos.write(report);
		fos.close();
	}
}


