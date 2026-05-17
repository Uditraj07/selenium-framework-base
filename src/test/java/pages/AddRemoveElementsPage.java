package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.WaitUtils;

import java.util.List;

public class AddRemoveElementsPage {
    private final WebDriver driver;
    private final WaitUtils waitUtils;

    private final By addElementButton = By.xpath("//button[text()='Add Element']");
    private final By deleteButtons = By.cssSelector("button.added-manually");

    public AddRemoveElementsPage(WebDriver driver) {
        this.driver = driver;
        this.waitUtils = new WaitUtils(driver);
    }

    public void addElement() {
        waitUtils.waitForVisible(addElementButton).click();
    }

    public void removeElement() {
        waitUtils.waitForVisible(deleteButtons).click();
    }

    public int getDeleteButtonCount() {
        List<?> buttons = driver.findElements(deleteButtons);
        return buttons.size();
    }
}
