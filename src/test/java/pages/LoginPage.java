package pages;

import driver.DriverManager;
import enums.STATUS;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.LoggerManager;
import utils.TestLogger;
import utils.WaitUtils;

public class LoginPage {
    private final WebDriver driver;
    private final WaitUtils waitUtils;
    private final TestLogger logger;

    public final By label_login = By.xpath("//h5[contains(@class,'login-title') and text()='Login']");
    public final By label_username = By.xpath("//label[normalize-space()='Username']");
    public final By label_password = By.xpath("//label[normalize-space()='Password']");
    public final By textbox_username = By.xpath("//input[@name='username']");
    public final By textbox_password = By.xpath("//input[@name='password']");
    public final By icon_username = By.xpath("//i[contains(@class,'bi-person')]");
    public final By icon_password = By.xpath("//i[contains(@class,'bi-key')]");
    public final By button_login = By.xpath("//button[@type='submit']");
    public final By text_forgot_password = By.xpath("//p[contains(@class,'orangehrm-login-forgot-header')]");
    public final By error_login = By.cssSelector(".oxd-input-field-error-message");
    private final By flashMessage = By.id("flash");
    private final By pageHeader = By.cssSelector("div.example h2");

    public LoginPage() {
        this.driver = DriverManager.getDriver();
        this.waitUtils = new WaitUtils(driver);
        this.logger = LoggerManager.getLogger();
    }


    public boolean verifyLoginPage(){
        boolean flag=false;
        try {
            logger.logTestStep("Verifying login page UI", STATUS.INFO);
            waitUtils.waitForVisible(label_login);
            if(driver.findElement(label_login).isDisplayed()){
                flag=true;
                logger.logTestStep("Login page verified successfully", STATUS.PASS);
            }
        }
        catch (Exception e) {
            logger.logTestStep("Exception while verifying login page: " + e.getMessage(), STATUS.FAIL);
        }
        return flag;
    }

    public String getFlashMessage() {
        return waitUtils.waitForVisible(flashMessage).getText().trim();
    }

    public String getPageHeader() {
        return waitUtils.waitForVisible(pageHeader).getText().trim();
    }
}
