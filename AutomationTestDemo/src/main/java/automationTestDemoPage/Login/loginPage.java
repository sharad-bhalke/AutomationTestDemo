package automationTestDemoPage.Login;

import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import AutomationTestDemo.Utility.WebUtility;

public class loginPage extends loginPageOr {

	private WebUtility wu;

	public loginPage(WebUtility util) {
		wu = util;
		PageFactory.initElements(util.getDriver(), this);
	}

	// Common validation method
	private void validateMessage(String expectedValidationMessage) {
		String actualMessage = wu.getText(lblErrorMessage);
		Assert.assertEquals(actualMessage.trim(), expectedValidationMessage.trim(), "❌ Validation message mismatch");
		wu.logInfo("✅ Validation message matched: " + actualMessage);
	}

	/*
	 * Verify Super User can log in with a valid email and valid password_(Id-13704)
	 */
	public void loginWithValidCredentials(String username, String password) {
		wu.sendKeys(UName, username);
		wu.sendKeys(UPass, password);
		wu.clickElement(btnHideToolbar);
		wu.clickElement(loginBtn);
	}

	/*
	 * Verify Super User cannot log in with a valid email and invalid
	 * password_(Id-13705)
	 */
	public void loginWithWrongPassword(String username, String password, String expectedValidationMessage) {
		wu.sendKeys(UName, username);
		wu.sendKeys(UPass, password);
		wu.clickElement(loginBtn);
		validateMessage(expectedValidationMessage);
	}

	/*
	 * Verify Super User cannot log in with an invalid email and valid
	 * password_(Id-13706)
	 */
	public void loginWithWrongEmail(String username, String password, String expectedValidationMessage) {
		wu.sendKeys(UName, username);
		wu.sendKeys(UPass, password);
		wu.clickElement(loginBtn);
		validateMessage(expectedValidationMessage);
	}

	/*
	 * Verify Super User cannot login with an invalid email and invalid
	 * password_(Id-13707)
	 */
	public void loginWithWrongEmailAndPassword(String username, String password, String expectedValidationMessage) {
		wu.sendKeys(UName, username);
		wu.sendKeys(UPass, password);
		wu.clickElement(loginBtn);
		validateMessage(expectedValidationMessage);
	}

	/*
	 * Verify Super User cannot login without email and password_(Id-13709)
	 */
	public void loginWithoutEmailAndPassword(String expectedValidationMessage) {
		wu.clickElement(loginBtn);
		validateMessage(expectedValidationMessage);
	}

}
