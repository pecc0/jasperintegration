package bi.jasperIntegration;

import java.io.File;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;

import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;

import bi.jasperIntegration.groovy.ReportsManager;

public class ReportsMain {

	/**
	 * TODO - DOCUMENT ME
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		String reportsXml = "reports.xml";
		String connectionString = "jdbc:odbc:BI_DB";
		String driver = "sun.jdbc.odbc.JdbcOdbcDriver";
		for (int i = 0; i < args.length; i++) {
			if ("-reports".equalsIgnoreCase(args[i])) {
				reportsXml = args[++i];
			} else if ("-cs".equalsIgnoreCase(args[i]) || "--connectionstr".equalsIgnoreCase(args[i])) {
				connectionString = args[++i];
			} else if ("-driver".equalsIgnoreCase(args[i])) {
				driver = args[++i];
			}
		}
		System.out.println("reports=" + reportsXml + ";");
		System.out.println("driver=" + driver + ";");
		System.out.println("connectionString=" + connectionString + ";\n");
		
		try {
			@SuppressWarnings("unused")
			Driver d = (Driver) Class.forName(driver)
					.newInstance();
			Connection c = DriverManager
					.getConnection(connectionString);
			new ReportsManager().createReports(reportsXml, c);
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	void saveReport(byte[] report, File f) {

		// FileOutputStream fos = new FileOutputStream(f);
		// fos.write(report);
	}
}
