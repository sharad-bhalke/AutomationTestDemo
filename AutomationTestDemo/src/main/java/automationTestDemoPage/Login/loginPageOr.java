package automationTestDemoPage.Login;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class loginPageOr {

	@FindBy(id = "id_username")
	protected WebElement UName;

	@FindBy(id = "id_password")
	protected WebElement UPass;

	@FindBy(xpath = "//input[@type='submit']")
	protected WebElement loginBtn;

	// error message
	@FindBy(xpath = "//p[@class='errornote']")
	protected WebElement lblErrorMessage;

	@FindBy(xpath = "//a[@title='Hide toolbar']")
	protected WebElement btnHideToolbar;

}
