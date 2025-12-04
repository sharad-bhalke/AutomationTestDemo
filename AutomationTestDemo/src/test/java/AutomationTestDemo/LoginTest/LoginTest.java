package AutomationTestDemo.LoginTest;

import org.testng.annotations.Test;
import AutomationTestDemo.BaseClass.BaseClass;
import automationTestDemoPage.Login.loginPage;

public class LoginTest extends BaseClass {

	loginPage lgn;

	@Test(priority = 1)
	public void validLogin_13704() {
		lgn = new loginPage(wu);
		wu.logInfo("🚀 Starting: Valid Login Test");

		String username = wu.getConfig("username");
		String password = wu.getConfig("password");

		lgn.loginWithValidCredentials(username, password);

		wu.logInfo("✅ Valid Login Test Passed");
	}

	@Test(priority = 2, dataProvider = "LoginData")
	public void loginWithWrongPassword_13705(String testCase, String username, String password,
			String expectedValidationMessage) {
		lgn = new loginPage(wu);
		wu.logInfo("🚀 Starting: Invalid Login Test (Wrong Password)");

		lgn.loginWithWrongPassword(username, password, expectedValidationMessage);

		wu.logInfo("✅ Wrong Password Test Passed");
	}

	@Test(priority = 3, dataProvider = "LoginData")
	public void loginWithWrongEmail_13706(String testCase, String username, String password,
			String expectedValidationMessage) {
		lgn = new loginPage(wu);
		wu.logInfo("🚀 Starting: Invalid Login Test (Wrong Email)");

		lgn.loginWithWrongEmail(username, password, expectedValidationMessage);

		wu.logInfo("✅ Wrong Email Test Passed");
	}

	@Test(priority = 4, dataProvider = "LoginData")
	public void loginWithWrongEmailAndPassword_13707(String testCase, String username, String password,
			String expectedValidationMessage) {
		lgn = new loginPage(wu);
		wu.logInfo("🚀 Starting: Invalid Login Test (Wrong Email & Wrong Password)");

		lgn.loginWithWrongEmailAndPassword(username, password, expectedValidationMessage);

		wu.logInfo("✅ Wrong Email & Password Test Passed");
	}

	@Test(priority = 5, dataProvider = "LoginData")
	public void loginWithoutEmailAndPassword_13708(String testCase, String username, String password,
			String expectedValidationMessage) {
		lgn = new loginPage(wu);
		wu.logInfo("🚀 Starting: Login Without Email & Password");

		lgn.loginWithoutEmailAndPassword(expectedValidationMessage);

		wu.logInfo("✅ Empty Fields Test Passed");
	}
}
