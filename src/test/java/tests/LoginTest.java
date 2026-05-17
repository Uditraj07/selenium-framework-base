package tests;

import base.BaseTest;
import data.TestDataProviders;
import model.LoginTestData;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.SecureAreaPage;

public class LoginTest extends BaseTest {

    @Test(dataProvider = "loginData", dataProviderClass = TestDataProviders.class)
    public void verifyLoginScenarios(LoginTestData testData) {
        open("/login");
        LoginPage loginPage = new LoginPage(getDriver());
        SecureAreaPage secureAreaPage = new SecureAreaPage(getDriver());

        loginPage.login(testData.getUsername(), testData.getPassword());
        String flashMessage = loginPage.getFlashMessage();

        Assert.assertTrue(
                flashMessage.contains(testData.getExpectedMessage()),
                "Flash message should contain the expected text"
        );

        if (testData.isValidLogin()) {
            Assert.assertEquals(secureAreaPage.getPageHeader(), "Secure Area", "Valid login should land on secure area");
        }
    }
}
