package utils;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import enums.STATUS;

import java.util.concurrent.atomic.AtomicInteger;

public class TestLogger {
    private final String testName;
    private final ExtentTest extentTest;
    private final AtomicInteger stepCounter = new AtomicInteger(0);
    private volatile boolean failureLogged;
    private volatile String lastFailureMessage;

    public TestLogger(String testName) {
        this.testName = testName;
        this.extentTest = ReportManager.getExtentReports().createTest(testName);
        ReportManager.flushReport();
    }

    public void logTestStep(String message, STATUS status) {
        int stepNumber = stepCounter.incrementAndGet();
        String stepMessage = "STEP-" + stepNumber + " - " + message;
        String screenshotPath = ScreenshotUtils.capture(testName + "_step_" + stepNumber);

        if (status == STATUS.FAIL) {
            failureLogged = true;
            lastFailureMessage = stepMessage;
        }

        extentTest.log(
                mapStatus(status),
                stepMessage,
                MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build()
        );
        ReportManager.flushReport();
    }

    public boolean hasFailureStep() {
        return failureLogged;
    }

    public String getLastFailureMessage() {
        return lastFailureMessage;
    }

    private com.aventstack.extentreports.Status mapStatus(STATUS status) {
        return switch (status) {
            case PASS -> com.aventstack.extentreports.Status.PASS;
            case FAIL -> com.aventstack.extentreports.Status.FAIL;
            case WARNING -> com.aventstack.extentreports.Status.WARNING;
            case INFO -> com.aventstack.extentreports.Status.INFO;
        };
    }
}
