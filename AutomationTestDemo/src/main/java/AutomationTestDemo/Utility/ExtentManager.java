package AutomationTestDemo.Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {

	private static ExtentReports extent;

	public static ExtentReports getInstance() {
		if (extent == null) {

			// ✅ Generate timestamped report name
			String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
			String reportDir = System.getProperty("user.dir") + "/Reports/ExtentReport";
			String reportPath = reportDir + "/ExtentReport_" + timestamp + ".html";
			new File(reportPath).getParentFile().mkdirs();

			// ✅ Initialize ExtentSparkReporter
			ExtentSparkReporter path = new ExtentSparkReporter(reportPath);
			path.config().setEncoding("UTF-8");

			// ✅ Load projectName dynamically from config.properties
			try (FileInputStream fis = new FileInputStream("src/main/resources/config.properties")) {
				Properties prop = new Properties();
				prop.load(fis);

				String projectName = prop.getProperty("projectName", "Automation Framework");
				path.config().setDocumentTitle(projectName + " - Test Report");
				path.config().setReportName(projectName + " Automation");
			} catch (IOException e) {
				System.out.println("⚠️ Could not read projectName from config.properties, using default values.");
				path.config().setDocumentTitle("Automation Framework - Test Report");
				path.config().setReportName("Automation Framework Automation");
			}

			// ✅ Create ExtentReports instance
			extent = new ExtentReports();
			extent.attachReporter(path);

			// ✅ Add environment info
			String envValue = "Not Set";
			try (FileInputStream fis = new FileInputStream("src/main/resources/config.properties")) {
				Properties prop = new Properties();
				prop.load(fis);
				String env = prop.getProperty("env", "").trim();
				if (!env.isEmpty()) {
					envValue = env.toUpperCase();
				}
			} catch (IOException e) {
				System.out.println("⚠️ Could not read env from config.properties");
			}

			extent.setSystemInfo("OS", System.getProperty("os.name"));
			extent.setSystemInfo("OS Version", System.getProperty("os.version"));
			extent.setSystemInfo("System User", System.getProperty("user.name"));
			extent.setSystemInfo("Java Version", System.getProperty("java.version"));
			extent.setSystemInfo("Environment", envValue);
		}
		return extent;
	}
}
