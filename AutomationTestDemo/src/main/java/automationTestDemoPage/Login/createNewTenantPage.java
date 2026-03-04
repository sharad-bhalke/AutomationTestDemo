package automationTestDemoPage.Login;

import org.openqa.selenium.support.PageFactory;
import AutomationTestDemo.Utility.WebUtility;

public class createNewTenantPage extends createNewTenantPageOr {

    private WebUtility wu;

    public createNewTenantPage(WebUtility util) {
        this.wu = util;
        PageFactory.initElements(util.getDriver(), this);
    }

    public void createNewTenant() {
        wu.scrollAndClick(clickOnTenantSideBar);
        wu.clickElement(clickOnAddTenantBtn);
        wu.clickElement(ClickAddTenantBtn);
        
        wu.clickElement(ClickOnTenantNameInputFild);
        wu.sendKeys(ClickOnTenantNameInputFild, "Tenant1");

        wu.clickElement(SelectProgrampermissions);
        wu.clickElement(SelectFeatures);

        wu.clickElement(ClickOnCode);
        wu.sendKeys(ClickOnCode, "TE1");

        wu.scrollAndClick(CliclonSaveBtnToSaveTheTenant);

    }
}
