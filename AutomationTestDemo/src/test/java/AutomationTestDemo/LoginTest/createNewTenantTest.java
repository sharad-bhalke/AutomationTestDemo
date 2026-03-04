package AutomationTestDemo.LoginTest;

import org.testng.annotations.Test;

import AutomationTestDemo.BaseClass.BaseClass;
import automationTestDemoPage.Login.createNewTenantPage;
import automationTestDemoPage.Login.loginPage;

public class createNewTenantTest extends BaseClass {

    private createNewTenantPage createTenantPage;
    private String username;
    private String password;

    private void initPage() {
        createTenantPage = new createNewTenantPage(wu);
    }

    /* -------- Reusable Login Method -------- */
    private void loginAsSuperAdmin() {
        loginPage lgn = new loginPage(wu);
        username = wu.getConfig("username");
        password = wu.getConfig("password");
        lgn.loginWithValidCredentials(username, password);
    }

    @Test(priority = 27)
    public void createNewTenant_13740() {
        loginAsSuperAdmin();
        
        initPage();
        createTenantPage.createNewTenant();
        
    }
    
    
}
