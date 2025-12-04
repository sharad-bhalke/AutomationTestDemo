package AutomationTestDemo.LoginTest;

import java.util.Random;
import org.testng.annotations.Test;
import AutomationTestDemo.BaseClass.BaseClass;
import automationTestDemoPage.Login.createNewSuperUserPage;
import automationTestDemoPage.Login.loginPage;

public class createNewUserTest extends BaseClass {

	private createNewSuperUserPage newUserPage;
	private String username;
	private String password;
	private String username1;
	private String password1;
	private String Passwordconfirmation;

	/* ---------------Reusable methods-------------------------------------- */
	private void loginAsSuperAdmin() {
		loginPage lgn = new loginPage(wu);
		username = wu.getConfig("username");
		password = wu.getConfig("password");
		lgn.loginWithValidCredentials(username, password);
	}

	private void genUniqUsrData() {
		long ts = System.currentTimeMillis() % 10;
		int randomNum = new Random().nextInt(9) + 1;

		username1 = "User" + ts + randomNum + "@gmail.com";
		password1 = "Pass@1" + ts + randomNum;
		Passwordconfirmation = password1;
	}

	private void genInvUsrData() {
		long ts = System.currentTimeMillis() % 100;
		int randomNum = new Random().nextInt(900) + 10;

		username1 = "A123" + ts + randomNum + "A222";
		password1 = "Pass@1" + ts + randomNum;
		Passwordconfirmation = password1;
	}

	private void initPage() {
		newUserPage = new createNewSuperUserPage(wu);
	}

	/* ------------------------Test Cases----------------------- */

	@Test(priority = 6, dataProvider = "LoginData")
	public void createSupUsrWithValDtl_13709(String testCase, String expectedMessage) {
		loginAsSuperAdmin();
		genUniqUsrData();
		initPage();
		newUserPage.crtNewSuperAdminUser(username1, password1, Passwordconfirmation, expectedMessage);
	}

	@Test(priority = 7, dataProvider = "LoginData")
	public void crtSupUsrWithDupEmail_13716(String testCase, String ExpectedValidationMessage) {
		loginAsSuperAdmin();
		genUniqUsrData();
		initPage();
		newUserPage.crtUserWithExistUName(username, password1, Passwordconfirmation, ExpectedValidationMessage);
	}

	@Test(priority = 8, dataProvider = "LoginData")
	public void crtSupUsrWithWkPass_13717(String testCase, String ExpectedValidationMessage) {
		loginAsSuperAdmin();
		genUniqUsrData();
		password1 = "asd123";
		Passwordconfirmation = password1;
		initPage();
		newUserPage.crtUserWithWkPass(username1, password1, Passwordconfirmation, ExpectedValidationMessage);
	}

	@Test(priority = 9, dataProvider = "LoginData")
	public void crtSupUsrWithMisPass_13718(String testCase, String ExpectedValidationMessage) {
		loginAsSuperAdmin();
		genUniqUsrData();
		initPage();
		newUserPage.crtUserWithMisPass(username1, password, Passwordconfirmation, ExpectedValidationMessage);
	}

	@Test(priority = 10, dataProvider = "LoginData")
	public void viewExistUsrDtl_13722(String testCase, String ExpectedValidationMessage) {
		loginAsSuperAdmin();
		initPage();
		newUserPage.viewUserDetails(ExpectedValidationMessage);
	}

	@Test(priority = 11, dataProvider = "LoginData")
	public void updUsrDtl_13723(String testCase, String ExpectedValidationMessage) {
		loginAsSuperAdmin();
		initPage();
		newUserPage.updateUserDetails(ExpectedValidationMessage);
	}

	@Test(priority = 12, dataProvider = "LoginData")
	public void rmvUsrPerm_13739(String testCase, String username, String password, String ExpectedValidationMessage) {
		loginAsSuperAdmin();
		initPage();
		newUserPage.removePermissionsAndLogin(username, password, ExpectedValidationMessage);
		wu.logInfo("✅ All permissions are removed successfully.");
	}

	@Test(priority = 13, dataProvider = "LoginData")
	public void addUsrPerm_13739(String testCase, String username, String password, String ExpectedValidationMessage) {
		loginAsSuperAdmin();
		initPage();
		newUserPage.addPermissionsAndLogin(username, password, ExpectedValidationMessage);
	}

	@Test(priority = 14, dataProvider = "LoginData")
	public void crtSupUsrWithBlkEmail_13719(String testCase, String ExpectedValidationMessage) {
		loginAsSuperAdmin();
		genUniqUsrData();
		initPage();
		newUserPage.crtUserNameFldBlk("", password1, Passwordconfirmation, ExpectedValidationMessage);
	}

	@Test(priority = 15, dataProvider = "LoginData")
	public void crtSupUsrWithBlkPass_13721(String testCase, String ExpectedValidationMessage) {
		loginAsSuperAdmin();
		genUniqUsrData();
		initPage();
		newUserPage.crtUserPassFldBlk(username1, "", Passwordconfirmation, ExpectedValidationMessage);
	}

	@Test(priority = 16, dataProvider = "LoginData")
	public void crtSupUsrWithBlkConPass_13722(String testCase, String ExpectedValidationMessage) {
		loginAsSuperAdmin();
		genUniqUsrData();
		initPage();
		newUserPage.crtUserConPassFldBlk(username1, password1, "", ExpectedValidationMessage);
	}

	@Test(priority = 17, dataProvider = "LoginData")
	public void crtSupUsrWthOutDtl_13720(String testCase, String ExpectedValidationMessage) {
		loginAsSuperAdmin();
		genUniqUsrData();
		initPage();
		newUserPage.crtUserWithValDtlSave("", "", "", ExpectedValidationMessage);
	}

	@Test(priority = 18, dataProvider = "LoginData")
	public void crtSupUsrSaveAddAno_13710(String testCase, String ExpectedValidationMessage) {
		loginAsSuperAdmin();
		genUniqUsrData();
		initPage();
		newUserPage.crtUserWithValDtlSaveAddAno(username1, password1, Passwordconfirmation, ExpectedValidationMessage);
	}

	@Test(priority = 19, dataProvider = "LoginData")
	public void crtSupUsrSaveConEdti_13712(String testCase, String ExpectedValidationMessage) {
		loginAsSuperAdmin();
		genUniqUsrData();
		initPage();
		newUserPage.crtUserWithValDtlSaveConEdti(username1, password1, Passwordconfirmation, ExpectedValidationMessage);
	}

	@Test(priority = 20, dataProvider = "LoginData")
	public void crtUsrInvDtOnSave_13713(String testCase, String ExpectedValidationMessage) {
		loginAsSuperAdmin();
		genInvUsrData();
		initPage();
		newUserPage.crtUserWithInvEmailSave(username1, password1, Passwordconfirmation, ExpectedValidationMessage);
	}

	@Test(priority = 21, dataProvider = "LoginData")
	public void crtUsrInvDtSaveAddAno_13714(String testCase, String ExpectedValidationMessage) {
		loginAsSuperAdmin();
		genInvUsrData();
		initPage();
		newUserPage.crtUserWithInvEmailSaveAddAno(username1, password1, Passwordconfirmation,
				ExpectedValidationMessage);
	}

	@Test(priority = 22, dataProvider = "LoginData")
	public void crtUsrInvDtSaveConEdti_13715(String testCase, String ExpectedValidationMessage) {
		loginAsSuperAdmin();
		genInvUsrData();
		initPage();
		newUserPage.crtUserWithInvEmailSaveConEdti(username1, password1, Passwordconfirmation,
				ExpectedValidationMessage);
	}

	@Test(priority = 23, dataProvider = "LoginData")
	public void delCrtdUsr_13733(String testCase, String ExpectedValidationMessage) {
		loginAsSuperAdmin();
		initPage();
		newUserPage.deleteUser(ExpectedValidationMessage);
	}

	@Test(priority = 24, dataProvider = "LoginData")
	public void deleteMultipleUser_13734(String testCase, String ExpectedValidationMessage) {
		loginAsSuperAdmin();
		initPage();
		newUserPage.deleteMultipleUser(2, ExpectedValidationMessage);
	}
}
