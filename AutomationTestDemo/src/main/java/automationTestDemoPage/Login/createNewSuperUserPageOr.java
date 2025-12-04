package automationTestDemoPage.Login;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class createNewSuperUserPageOr {

	// Sidebar & Navigation
	@FindBy(xpath = "//a[@href='/admin/users/user/']")
	protected WebElement userSidebarLink;

	@FindBy(xpath = "//*[@id='content-main']/ul/li/a")
	protected WebElement addUserBtn;

	// Create User Fields
	@FindBy(xpath = "//input[@id='id_email']")
	protected WebElement emailInput;

	@FindBy(xpath = "//input[@id='id_password1']")
	protected WebElement passwordInput;

	@FindBy(xpath = "//input[@id='id_password2']")
	protected WebElement confirmPasswordInput;

	@FindBy(xpath = "//input[@name='_save']")
	protected WebElement saveBtn;

	@FindBy(xpath = "//a[@title='Hide toolbar']")
	protected WebElement hideToolbarBtn;

	// Success & Error Messages
	@FindBy(xpath = "(//li[@class='success'])[1]")
	protected WebElement successMsg;

	@FindBy(xpath = "//li[normalize-space()='User with this Email already exists.']")
	protected WebElement duplicateUserMsg;

	@FindBy(xpath = "(//li[normalize-space()='This password is too common.'])[1]")
	protected WebElement commonPasswordMsg;

	@FindBy(xpath = "//li[contains(text(),'The two password fields didn’t match.')]")
	protected WebElement passwordMismatchMsg;

	@FindBy(xpath = "//li[normalize-space()='This field is required.']")
	protected WebElement fieldRequiredMsg;

	@FindBy(xpath = "//li[normalize-space()='Enter a valid email address.']")
	protected WebElement invalidEmailMsg;

	// User Profile
	@FindBy(xpath = "//a[normalize-space()='sharad.bhalke@medivoxx.com']")
	protected WebElement openUserLink;

	@FindBy(xpath = "//h2[normalize-space()='Sharad2 Bhalke']")
	protected WebElement userTitle;

	@FindBy(xpath = "//input[@id='id_first_name']")
	protected WebElement firstNameInput;

	@FindBy(xpath = "//li[@class='success']")
	protected WebElement updateSuccessMsg;

	// Check boxes
	@FindBy(xpath = "//input[@id='id_is_active']")
	protected WebElement activeCheckbox;

	@FindBy(xpath = "//input[@id='id_is_superuser']")
	protected WebElement superUserCheckbox;

	@FindBy(xpath = "//input[@id='id_is_tenant_admin']")
	protected WebElement tenantAdminCheckbox;

	// Logout Section
	@FindBy(xpath = "//button[@type='submit']")
	protected WebElement logoutBtn;

	@FindBy(xpath = "//a[normalize-space()='Log in again']")
	protected WebElement loginAgainLink;

	// Login Fields
	@FindBy(xpath = "//input[@name='username']")
	protected WebElement loginEmailInput;

	@FindBy(xpath = "//input[@type='password']")
	protected WebElement loginPasswordInput;

	@FindBy(xpath = "//input[@type='submit']")
	protected WebElement loginBtn;

	@FindBy(xpath = "//p[@class='errornote']")
	protected WebElement invalidCredentialsMsg;

	// Save Options
	@FindBy(xpath = "//input[@value='Save and add another']")
	protected WebElement saveAndAddAnotherBtn;

	@FindBy(xpath = "//input[@value='Save and continue editing']")
	protected WebElement saveAndContinueBtn;

	// Select Check box for the delete users
	@FindBy(xpath = "//input[@value='307']")
	protected WebElement selectUserCheckbox;
	
	@FindBy(xpath = "//select[@name='action']")
	protected WebElement clickAction;
	
	@FindBy(xpath = "//button[normalize-space()='Go']")
	protected WebElement goButton;
	
	@FindBy(xpath = "//input[@value='Yes, I’m sure']")
	protected WebElement iamsureBtn;
	
	@FindBy(xpath = "//input[@type='checkbox' and contains(@class,'action-select')]")
	public List<WebElement> userCheckboxes;
	
	@FindBy(xpath = "//li[@class='success']")
	protected WebElement deleteUser;
}
