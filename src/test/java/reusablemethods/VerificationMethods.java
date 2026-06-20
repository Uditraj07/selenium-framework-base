package reusablemethods;

import driver.DriverManager;
import enums.STATUS;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import utils.LoggerManager;
import utils.TestLogger;
import utils.WaitUtils;

public class VerificationMethods {
    private final WaitUtils waitUtils;
    private final TestLogger logger;
    private final WebDriver driver;

    public VerificationMethods(WaitUtils waitUtils) {
        this.waitUtils = waitUtils;
        this.logger = LoggerManager.getLogger();
        this.driver= DriverManager.getDriver();
    }

    public void UiVerification(
            By labelLocator,
            By fieldLocator,
            By errorLocator,
            By iconLocator,
            By misceliniousLocator,
            String fieldType,
            String fieldName,
            String keyword,
            String fieldValue,
            String expectedResult
    )
    {
        if(fieldType.equalsIgnoreCase("label") && keyword.equalsIgnoreCase("Available")){
            try {
                WebElement ele=driver.findElement(labelLocator);
                JavascriptExecutor jse=(JavascriptExecutor)driver;
                jse.executeScript("arguments[0].scrollIntoView(true);", ele);
                if(ele.isDisplayed()){
                    highlightField(ele);
                    logger.logTestStep(fieldName + " label is displayed", STATUS.PASS);
                }else {
                    logger.logTestStep(fieldName + " label is not displayed", STATUS.FAIL);
                }
            } catch (Exception e) {
                logger.logTestStep("Exception while verifying the label"+e.getMessage(), STATUS.FAIL);
            }
        }
        else if(fieldType.equalsIgnoreCase("textbox") && keyword.equalsIgnoreCase("PlaceHolder")){
            try {
                WebElement ele=driver.findElement(fieldLocator);
                JavascriptExecutor jse=(JavascriptExecutor)driver;
                jse.executeScript("arguments[0].scrollIntoView(true);", ele);
                if(ele.isDisplayed()){
                    highlightField(ele);
                    logger.logTestStep(fieldName + " text box is displayed", STATUS.PASS);
                }
                else{
                    logger.logTestStep(fieldName + " text box is not displayed", STATUS.FAIL);
                    Assert.fail(fieldName + " text box is not displayed");
                }
                String actualValue=ele.getAttribute("placeholder");
                if(actualValue.equalsIgnoreCase(expectedResult)){
                    logger.logTestStep("Expected Result Matched With Actual Result. Expected: "+expectedResult+" Actual Result:"+actualValue, STATUS.PASS);
                }
                else{
                    logger.logTestStep("Expected Result does not Match With Actual Result. Expected: "+expectedResult+" Actual Result:"+actualValue, STATUS.FAIL);
                }
            } catch (Exception e) {
                logger.logTestStep("Exception while verifying the text with PlaceHolder"+e.getMessage(), STATUS.FAIL);
            }
        }
        else if(fieldType.equalsIgnoreCase("icon") && keyword.equalsIgnoreCase("Available")){
            try {
                WebElement ele=driver.findElement(iconLocator);
                JavascriptExecutor jse=(JavascriptExecutor)driver;
                jse.executeScript("arguments[0].scrollIntoView(true);", ele);
                if(ele.isDisplayed() && ele.getAttribute("class").contains("icon")){
                    highlightField(ele);
                    logger.logTestStep(fieldName + " icon is displayed", STATUS.PASS);
                }else {
                    logger.logTestStep(fieldName + " icon is not displayed", STATUS.FAIL);
                }
            } catch (Exception e) {
                logger.logTestStep("Exception while verifying the icon"+e.getMessage(), STATUS.FAIL);
            }
        }

    }

    public void highlightField(WebElement ele) {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        String originalStyle = ele.getAttribute("style");

        for (int i = 0; i < 2; i++) {
            jse.executeScript(
                    "arguments[0].setAttribute('style', arguments[1] + '; border: 2px solid gray;');",
                    ele,
                    originalStyle == null ? "" : originalStyle
            );
            pause(200);
            jse.executeScript(
                    "arguments[0].setAttribute('style', arguments[1]);",
                    ele,
                    originalStyle == null ? "" : originalStyle
            );
            pause(200);
        }
    }

    private void pause(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Interrupted while highlighting element", exception);
        }
    }
}
