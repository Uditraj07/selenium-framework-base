package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.WaitUtils;

import java.util.List;

public class CheckboxesPage {
    private final WebDriver driver;
    private final WaitUtils waitUtils;

    private final By checkboxes = By.cssSelector("#checkboxes input[type='checkbox']");

    public CheckboxesPage(WebDriver driver) {
        this.driver = driver;
        this.waitUtils = new WaitUtils(driver);
    }

    public void setCheckboxState(int index, boolean shouldBeChecked) {
        WebElement checkbox = getCheckbox(index);
        waitUtils.waitForVisible(checkboxes);
        if (checkbox.isSelected() != shouldBeChecked) {
            checkbox.click();
        }
    }

    public boolean isCheckboxSelected(int index) {
        return getCheckbox(index).isSelected();
    }

    private WebElement getCheckbox(int index) {
        List<WebElement> elements = driver.findElements(checkboxes);
        return elements.get(index);
    }
}
