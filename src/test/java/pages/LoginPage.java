package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.WaitUtils;

public class LoginPage {
    private final WebDriver driver;
    private final WaitUtils waitUtils;

    private final By usernameInput = By.id("username");
    private final By passwordInput = By.id("password");
    private final By loginButton = By.cssSelector("button[type='submit']");
    private final By flashMessage = By.id("flash");
    private final By pageHeader = By.cssSelector("div.example h2");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.waitUtils = new WaitUtils(driver);
    }

    public void login(String username, String password) {
        waitUtils.waitForVisible(usernameInput).clear();
        driver.findElement(usernameInput).sendKeys(username);
        waitUtils.waitForVisible(passwordInput).clear();
        driver.findElement(passwordInput).sendKeys(password);
        waitUtils.waitForVisible(loginButton).click();
    }

    public String getFlashMessage() {
        return waitUtils.waitForVisible(flashMessage).getText().trim();
    }

    public String getPageHeader() {
        return waitUtils.waitForVisible(pageHeader).getText().trim();
    }
}
