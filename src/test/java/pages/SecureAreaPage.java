package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.WaitUtils;

public class SecureAreaPage {
    private final WaitUtils waitUtils;
    private final By pageHeader = By.cssSelector("div.example h2");
    private final By flashMessage = By.id("flash");
    private final By logoutButton = By.cssSelector("a.button.secondary.radius");

    public SecureAreaPage(WebDriver driver) {
        this.waitUtils = new WaitUtils(driver);
    }

    public String getPageHeader() {
        return waitUtils.waitForVisible(pageHeader).getText().trim();
    }

    public String getFlashMessage() {
        return waitUtils.waitForVisible(flashMessage).getText().trim();
    }

    public void logout() {
        waitUtils.waitForVisible(logoutButton).click();
    }
}
