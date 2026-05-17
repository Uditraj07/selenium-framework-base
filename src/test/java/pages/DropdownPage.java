package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import utils.WaitUtils;

public class DropdownPage {
    private final WebDriver driver;
    private final WaitUtils waitUtils;

    private final By dropdown = By.id("dropdown");

    public DropdownPage(WebDriver driver) {
        this.driver = driver;
        this.waitUtils = new WaitUtils(driver);
    }

    public void selectByVisibleText(String visibleText) {
        waitUtils.waitForVisible(dropdown);
        new Select(driver.findElement(dropdown)).selectByVisibleText(visibleText);
    }

    public String getSelectedOption() {
        waitUtils.waitForVisible(dropdown);
        return new Select(driver.findElement(dropdown)).getFirstSelectedOption().getText().trim();
    }
}
