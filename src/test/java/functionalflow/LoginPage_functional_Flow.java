package functionalflow;

import driver.DriverManager;
import enums.STATUS;
import model.UiTestCaseData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pages.LoginPage;
import reusablemethods.VerificationMethods;
import utils.LoggerManager;
import utils.TestLogger;
import utils.WaitUtils;

import java.util.List;

public class LoginPage_functional_Flow {
    private final WebDriver driver;
    private final WaitUtils waitUtils;
    private final TestLogger logger;
    private final LoginPage loginPage;
    private final VerificationMethods verificationMethods;

    public LoginPage_functional_Flow() {
        this.driver = DriverManager.getDriver();
        this.waitUtils = new WaitUtils(driver);
        this.logger = LoggerManager.getLogger();
        this.loginPage = new LoginPage();
        this.verificationMethods = new VerificationMethods(waitUtils);
    }

    public void verify_LoginPage_Functional_Flow(List<UiTestCaseData> testDataList) {
        for (UiTestCaseData testData : testDataList) {
            By labelLocator = null;
            By fieldLocator = null;
            By errorLocator = null;
            By iconLocator = null;
            By misceliniousLocator = null;
            String fieldType = testData.getFieldType();
            String fieldName = testData.getFieldName() == null ? "" : testData.getFieldName().trim();
            String keyword = testData.getKeyword() == null ? "" : testData.getKeyword().trim();
            String fieldValue = testData.getFieldValue() == null ? "" : testData.getFieldValue().trim();
            String expectedResult = testData.getExpectedResult() == null ? "" : testData.getExpectedResult().trim();

            switch (fieldName) {
                case "Login":
                    labelLocator = this.loginPage.label_login;
                    fieldLocator = this.loginPage.button_login;
                    break;
                case "Username":
                case "Username.":
                    labelLocator = this.loginPage.label_username;
                    fieldLocator = this.loginPage.textbox_username;
                    iconLocator = this.loginPage.icon_username;
                    break;
                case "Password":
                    labelLocator = this.loginPage.label_password;
                    fieldLocator = this.loginPage.textbox_password;
                    iconLocator = this.loginPage.icon_password;
                    break;
                default:
                    logger.logTestStep("No locator mapped for fieldName : " + fieldName, STATUS.WARNING);
                    break;
            }

            verificationMethods.UiVerification(
                    labelLocator,
                    fieldLocator,
                    errorLocator,
                    iconLocator,
                    misceliniousLocator,
                    fieldType,
                    fieldName,
                    keyword,
                    fieldValue,
                    expectedResult
            );
        }
    }
}
