package bi.jasperIntegration;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

import bi.jasperIntegration.groovy.ReportsManager;

public class ReportsMain {

	/**
	 * TODO - DOCUMENT ME
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		String reportsXml = "reports.xml";

		for (int i = 0; i < args.length; i++) {
			if ("-reports".equals(args[i])) {
				reportsXml = args[++i];
			}
		}

		try {
			Driver d = (Driver) Class.forName("sun.jdbc.odbc.JdbcOdbcDriver")
					.newInstance();
			Connection c = DriverManager
					.getConnection("jdbc:odbc:project1");
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
