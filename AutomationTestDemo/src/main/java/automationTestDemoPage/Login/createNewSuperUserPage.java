package automationTestDemoPage.Login;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import AutomationTestDemo.Utility.WebUtility;

public class createNewSuperUserPage extends createNewSuperUserPageOr {

	private WebUtility wu;

	public createNewSuperUserPage(WebUtility util) {
		wu = util;
		PageFactory.initElements(util.getDriver(), this);
	}

	private void enterUserDtl(String username, String password, String confirmPassword) {
		wu.clickElement(userSidebarLink);
		wu.clickElement(addUserBtn);
		wu.sendKeys(emailInput, username);
		wu.sendKeys(passwordInput, password);
		wu.sendKeys(confirmPasswordInput, confirmPassword);
	}

	public void saveUser(String username, String password, String confirmPassword) {
		enterUserDtl(username, password, confirmPassword);
		wu.clickElement(saveBtn);
	}

	public void saveAndAddAnother(String username, String password, String confirmPassword) {
		enterUserDtl(username, password, confirmPassword);
		wu.clickElement(saveAndAddAnotherBtn);
	}

	public void saveAndContinueEditing(String username, String password, String confirmPassword) {
		enterUserDtl(username, password, confirmPassword);
		wu.clickElement(saveAndContinueBtn);
	}

	public void logout_() {
		wu.clickElement(logoutBtn);
		wu.clickElement(loginAgainLink);
		wu.logInfo("Logout Successfully ");
	}

	public void relogin(String username, String password) {
		wu.sendKeys(loginEmailInput, username);
		wu.sendKeys(loginPasswordInput, password);
		wu.clickElement(hideToolbarBtn);
		wu.clickElement(loginBtn);
		wu.logInfo("Relogin Successfully");
	}

	private void validateMessage(WebElement locator, String expectedValidationMessage) {
		String actual = wu.getText(locator).trim();
		Assert.assertEquals(actual, expectedValidationMessage.trim(), "❌ Validation message mismatch");
		wu.logInfo("✅ Validation message matched: " + actual);
	}

	public void crtNewSuperAdminUser(String username, String password, String confirmPassword,
			String expectedValidationMessage) {
		saveUser(username, password, confirmPassword);
		validateMessage(successMsg, expectedValidationMessage);
	}

	public void crtUserWithExistUName(String username, String password, String confirmPassword,
			String expectedValidationMessage) {
		saveUser(username, password, confirmPassword);
		validateMessage(duplicateUserMsg, expectedValidationMessage);

	}

	public void crtUserWithWkPass(String username, String password, String confirmPassword,
			String expectedValidationMessage) {
		saveUser(username, password, confirmPassword);
		validateMessage(commonPasswordMsg, expectedValidationMessage);

	}

	public void crtUserWithMisPass(String username, String password, String confirmPassword,
			String expectedValidationMessage) {
		saveUser(username, password, confirmPassword);
		validateMessage(passwordMismatchMsg, expectedValidationMessage);

	}

	public void viewUserDetails(String expectedValidationMessage) {
		wu.clickElement(userSidebarLink);
		wu.clickElement(openUserLink);
		validateMessage(userTitle, expectedValidationMessage);

	}

	public void updateUserDetails(String expectedValidationMessage) {
		wu.clickElement(userSidebarLink);
		wu.clickElement(openUserLink);
		wu.clearElement(firstNameInput);
		wu.sendKeys(firstNameInput, "Sharad2");
		wu.scrollAndClick(saveBtn);
		validateMessage(updateSuccessMsg, expectedValidationMessage);

	}

	public void removePermissionsAndLogin(String username, String password, String expectedValidationMessage) {
		wu.clickElement(userSidebarLink);
		wu.clickElement(openUserLink);

		wu.deselectIfSelected(activeCheckbox);
		wu.deselectIfSelected(superUserCheckbox);
		wu.deselectIfSelected(tenantAdminCheckbox);

		wu.scrollAndClick(saveBtn);
		logout_();
		relogin(username, password);

		if (wu.isElementDisplayed(invalidCredentialsMsg)) {
			String actual = wu.getText(invalidCredentialsMsg).trim();
			Assert.assertEquals(actual, expectedValidationMessage,
					"❌ Validation message mismatch AFTER DE-SELECTING permissions.");
		} else {
			Assert.fail("❌ Expected validation message NOT displayed after removing permissions!");
		}
	}

	public void addPermissionsAndLogin(String username, String password, String expectedValidationMessage) {
		wu.clickElement(userSidebarLink);
		wu.clickElement(openUserLink);

		wu.selectIfNotSelected(activeCheckbox);
		wu.selectIfNotSelected(superUserCheckbox);
		wu.selectIfNotSelected(tenantAdminCheckbox);

		wu.scrollAndClick(saveBtn);
		logout_();
		relogin(username, password);
	}

	public void crtUserNameFldBlk(String username, String password, String confirmPassword,
			String expectedValidationMessage) {
		saveUser(username, password, confirmPassword);
		validateMessage(fieldRequiredMsg, expectedValidationMessage);

	}

	public void crtUserPassFldBlk(String username, String password, String confirmPassword,
			String expectedValidationMessage) {
		crtUserNameFldBlk(username, password, confirmPassword, expectedValidationMessage);
		validateMessage(fieldRequiredMsg, expectedValidationMessage);

	}

	public void crtUserConPassFldBlk(String username, String password, String confirmPassword,
			String expectedValidationMessage) {
		crtUserNameFldBlk(username, password, confirmPassword, expectedValidationMessage);
		validateMessage(fieldRequiredMsg, expectedValidationMessage);

	}

	public void crtUserWithValDtlSave(String username, String password, String confirmPassword,
			String expectedValidationMessage) {
		crtUserNameFldBlk(username, password, confirmPassword, expectedValidationMessage);

	}

	public void crtUserWithValDtlSaveAddAno(String username, String password, String confirmPassword,
			String expectedValidationMessage) {
		saveAndAddAnother(username, password, confirmPassword);
		validateMessage(successMsg, expectedValidationMessage);

	}

	public void crtUserWithValDtlSaveConEdti(String username, String password, String confirmPassword,
			String expectedValidationMessage) {
		saveAndContinueEditing(username, password, confirmPassword);
		validateMessage(successMsg, expectedValidationMessage);

	}

	public void crtUserWithInvEmailSave(String username, String password, String confirmPassword,
			String expectedValidationMessage) {
		saveUser(username, password, confirmPassword);
		validateMessage(invalidEmailMsg, expectedValidationMessage);

	}

	public void crtUserWithInvEmailSaveAddAno(String username, String password, String confirmPassword,
			String expectedValidationMessage) {
		saveAndAddAnother(username, password, confirmPassword);
		validateMessage(invalidEmailMsg, expectedValidationMessage);

	}

	public void crtUserWithInvEmailSaveConEdti(String username, String password, String confirmPassword,
			String expectedValidationMessage) {
		saveAndContinueEditing(username, password, confirmPassword);
		validateMessage(invalidEmailMsg, expectedValidationMessage);

	}

	public void deleteUser(String expectedValidationMessage) {
		wu.clickElement(userSidebarLink);
		wu.scrollAndClick(selectUserCheckbox);

		wu.clickWithActions(clickAction);
		wu.selectByVisibleText(clickAction, "Delete selected users");
		wu.clickElement(goButton);
		wu.scrollAndClick(iamsureBtn);
		validateMessage(deleteUser, expectedValidationMessage);

	}

	public void deleteMultipleUser(int count, String expectedValidationMessage) {

		wu.clickElement(userSidebarLink);

		int total = userCheckboxes.size();
		wu.scrollAndClick(userCheckboxes.get(total - 2)); // 2nd last
		wu.scrollAndClick(userCheckboxes.get(total - 3)); // 3rd last
		wu.clickWithActions(clickAction);
		wu.selectByVisibleText(clickAction, "Delete selected users");
		wu.clickElement(goButton);
		wu.scrollAndClick(iamsureBtn);
		validateMessage(deleteUser, expectedValidationMessage);

	}

}
