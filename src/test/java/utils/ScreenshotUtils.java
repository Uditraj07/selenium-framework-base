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
    private ScreenshotUtils() {
    }

    public static String capture(String testName) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS"));
        Path screenshotDirectory = Path.of("test-output", "screenshots");
        Path destination = screenshotDirectory.resolve(testName + "_" + timestamp + ".png");

        try {
            Files.createDirectories(screenshotDirectory);
            Path source = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.FILE).toPath();
            Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
            return destination.toString();
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to capture screenshot for test: " + testName, exception);
        }
    }
}
