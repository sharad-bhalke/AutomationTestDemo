package AutomationTestDemo.BaseClass;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import AutomationTestDemo.Utility.WebUtility;

public class BaseClass {

	protected WebUtility wu = WebUtility.getInstance();

	// ============Launch browser before each test method==============//

	@BeforeMethod(alwaysRun = true)
	@Parameters({ "env" })
	public void launchbrowser(@Optional("") String xmlEnv) {
		try {
			if (xmlEnv != null && !xmlEnv.isBlank()) {
				System.setProperty("env", xmlEnv);
			}
			wu = WebUtility.getInstance();
			wu.launchBrowser();
			wu.logInfo("✅ Browser launched successfully for environment: " + xmlEnv);
		} catch (Exception e) {
			wu.logError("❌ Failed to initialize browser: " + e.getMessage());
			throw e;
		}
	}

	// ========================DataProvider========================//

	@DataProvider(name = "LoginData")
	public Object[][] LoginData(Method method) {

		String path = "src/test/resources/LoginTestData.xlsx";

		// Get calling class name
		String className = method.getDeclaringClass().getSimpleName().toLowerCase();

		String sheetName;

		if (className.contains("createnewusertest") || className.contains("create") || className.contains("user")
				|| className.contains("perm")) {
			sheetName = "Messages";
		} else if (className.contains("login")) {
			sheetName = "LoginData";
		} else {
			sheetName = "LoginData";
		}

		wu.logInfo("📄 Sheet selected: " + sheetName);

		// 🔹 Read trimmed sheet data
		Object[][] allData = wu.getTrimmedSheetData(sheetName, path);

		List<Object[]> filtered = new ArrayList<>();
		String testName = method.getName().trim();

		// 🔹 Match only rows where first column == test method name
		for (Object[] row : allData) {
			if (row.length == 0 || row[0] == null)
				continue;

			if (testName.equalsIgnoreCase(row[0].toString().trim())) {
				Object[] args = new Object[method.getParameterCount()];

				for (int i = 0; i < args.length; i++) {
					args[i] = (i < row.length) ? row[i] : "";
				}

				filtered.add(args);
			}
		}

		// 🔹 If matching row found → return only that row
		if (!filtered.isEmpty()) {
			return filtered.toArray(new Object[0][]);
		}

		// 🔹 If no match → return first row of sheet (safe fallback)
		wu.logInfo("⚠ No matching row for: " + testName + " → using first row.");

		Object[] fallback = new Object[method.getParameterCount()];
		for (int i = 0; i < fallback.length; i++) {
			fallback[i] = (i < allData[0].length) ? allData[0][i] : "";
		}

		return new Object[][] { fallback };
	}

	// ============Quit browser after each test method==============//

	@AfterMethod(alwaysRun = true)
	public void quitBrowser() {
		try {
			if (wu != null) {
				wu.quitDriver();
				wu.logInfo("🧹 Browser closed successfully.");
			}
		} catch (Exception e) {
			System.out.println("⚠️ Error closing browser: " + e.getMessage());
		}
	}
}
