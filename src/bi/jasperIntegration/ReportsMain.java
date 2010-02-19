package bi.jasperIntegration;

import bi.jasperIntegration.groovy.ReportsManager;

public class ReportsMain {

	/**
	 * TODO - DOCUMENT ME
	 * @param args
	 */
	public static void main(String[] args) {
		String reportsXml = "reports.xml";
		
		for (int i = 0; i < args.length; i++) {
			if ("-reports".equals(args[i])) {
				reportsXml = args[++i];
			}
 		}
		new ReportsManager().createReports(reportsXml, null);
	}

}
