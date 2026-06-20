package listeners;

import enums.STATUS;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utils.LoggerManager;
import utils.ReportManager;
import utils.ScreenshotUtils;
import utils.TestLogger;

public class TestListener implements ITestListener, IInvokedMethodListener {

    @Override
    public void onTestStart(ITestResult result) {
        Object browser = result.getTestContext().getAttribute("browser");
        String browserLabel = browser == null ? "unknown" : browser.toString();
        try {
            LoggerManager.getLogger().logTestStep("Starting test on browser: " + browserLabel, STATUS.INFO);
        } catch (IllegalStateException exception) {
            System.out.printf("Starting test: %s on %s%n", result.getName(), browserLabel);
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        try {
            LoggerManager.getLogger().logTestStep("Test failed: " + result.getName(), STATUS.FAIL);
        } catch (IllegalStateException exception) {
            String screenshotPath = ScreenshotUtils.capture(result.getName());
            System.out.printf("Test failed: %s. Screenshot: %s%n", result.getName(), screenshotPath);
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        try {
            LoggerManager.getLogger().logTestStep("Test passed: " + result.getName(), STATUS.PASS);
        } catch (IllegalStateException exception) {
            System.out.printf("Test passed: %s%n", result.getName());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        try {
            LoggerManager.getLogger().logTestStep("Test skipped: " + result.getName(), STATUS.WARNING);
        } catch (IllegalStateException exception) {
            System.out.printf("Test skipped: %s%n", result.getName());
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        try {
            LoggerManager.getLogger().logTestStep("Finished suite: " + context.getName(), STATUS.INFO);
        } catch (IllegalStateException exception) {
            System.out.printf("Finished suite: %s%n", context.getName());
        }
        ReportManager.flushReport();
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        if (!method.isTestMethod()) {
            return;
        }

        try {
            TestLogger testLogger = LoggerManager.getLogger();
            if (testLogger.hasFailureStep() && testResult.getThrowable() == null) {
                testResult.setStatus(ITestResult.FAILURE);
                testResult.setThrowable(
                        new AssertionError("Test marked failed from logger: " + testLogger.getLastFailureMessage())
                );
            }
        } catch (IllegalStateException exception) {
            System.out.printf("Unable to sync logger failure to TestNG for test: %s%n", testResult.getName());
        }
    }
}
