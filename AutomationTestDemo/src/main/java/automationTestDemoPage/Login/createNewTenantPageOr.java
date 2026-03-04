package automationTestDemoPage.Login;

import java.net.http.WebSocket;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class createNewTenantPageOr {

	@FindBy(xpath = "//a[normalize-space()='Tenants']")

	protected WebElement clickOnTenantSideBar;

	@FindBy(xpath = "//a[normalize-space()='Tenants']")

	protected WebElement clickOnAddTenantBtn;
	
   @FindBy(xpath = "//a[normalize-space()='Add tenant']")
   protected WebElement ClickAddTenantBtn;
   
   @FindBy(xpath = "//input[@id=\"id_name\"]")
   protected WebElement ClickOnTenantNameInputFild;
   
   @FindBy(xpath = "(//input[@name='program_permissions_bhi'])[2]")
   protected WebElement SelectProgrampermissions;
   
   @FindBy(xpath = "//input[@value=\"text_sms\"]")
   protected WebElement SelectFeatures;
   
   //(//input[@id='id_features_0'])[1]
   
   @FindBy(xpath = "//input[@name=\"code\"]")
   protected WebElement ClickOnCode;
   
   @FindBy(xpath = "//input[@value=\"Save\"]")
   protected WebElement CliclonSaveBtnToSaveTheTenant;
   
   
}
