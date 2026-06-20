package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class ReportManager {
    private static final DateTimeFormatter RUN_TIMESTAMP = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

    private static ExtentReports extentReports;
    private static Path reportDirectory;
    private static Path screenshotDirectory;

    private ReportManager() {
    }

    public static synchronized ExtentReports getExtentReports() {
        if (extentReports == null) {
            initializeReport();
        }
        return extentReports;
    }

    public static synchronized Path getReportDirectory() {
        if (reportDirectory == null) {
            initializeReport();
        }
        return reportDirectory;
    }

    public static synchronized Path getScreenshotDirectory() {
        if (screenshotDirectory == null) {
            initializeReport();
        }
        return screenshotDirectory;
    }

    public static synchronized void flushReport() {
        if (extentReports != null) {
            extentReports.flush();
        }
    }

    private static void initializeReport() {
        try {
            String folderName = LocalDateTime.now().format(RUN_TIMESTAMP) + "_ExtentReport";
            reportDirectory = Path.of("Reports", folderName);
            screenshotDirectory = reportDirectory.resolve("screenshots");
            Files.createDirectories(screenshotDirectory);

            Path reportFile = reportDirectory.resolve("ExtentReport.html");
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportFile.toString());
            sparkReporter.config().setDocumentTitle("Automation Execution Report");
            sparkReporter.config().setReportName("Extent Execution Report");

            extentReports = new ExtentReports();
            extentReports.attachReporter(sparkReporter);
            extentReports.setSystemInfo("reportFolder", reportDirectory.toAbsolutePath().toString());
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to initialize Extent report", exception);
        }
    }
}
