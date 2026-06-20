package utils;

import driver.DriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class ScreenshotUtils {
    private static final DateTimeFormatter TIMESTAMP_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS");

    private ScreenshotUtils() {
    }

    public static String capture(String testName) {
        String timestamp = LocalDateTime.now().format(TIMESTAMP_FORMAT);
        Path screenshotDirectory = ReportManager.getScreenshotDirectory();
        Path destination = screenshotDirectory.resolve(testName + "_" + timestamp + ".png");

        try {
            Files.createDirectories(screenshotDirectory);
            Path source = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.FILE).toPath();
            Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
            return ReportManager.getReportDirectory().relativize(destination).toString().replace("\\", "/");
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to capture screenshot for test: " + testName, exception);
        }
    }
}
