package listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utils.ScreenshotUtils;

public class TestListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        Object browser = result.getTestContext().getAttribute("browser");
        String browserLabel = browser == null ? "unknown" : browser.toString();
        System.out.printf("Starting test: %s on %s%n", result.getName(), browserLabel);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String screenshotPath = ScreenshotUtils.capture(result.getName());
        System.out.printf("Test failed: %s. Screenshot: %s%n", result.getName(), screenshotPath);
    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.printf("Finished suite: %s%n", context.getName());
    }
}
