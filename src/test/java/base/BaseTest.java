package base;

import config.FrameworkConfig;
import driver.DriverFactory;
import driver.DriverManager;
import listeners.TestListener;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.Listeners;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import utils.LoggerManager;
import utils.ReportManager;
import utils.TestLogger;

import java.time.Duration;

@Listeners(TestListener.class)
public abstract class BaseTest {

    @BeforeMethod(alwaysRun = true)
    @Parameters({"browser"})
    public void setUp(@Optional String suiteBrowser, ITestContext context, ITestResult result) {
        String browser = resolveBrowser(suiteBrowser);
        WebDriver driver = DriverFactory.createDriver(browser);
        DriverManager.setDriver(driver);
        LoggerManager.setLogger(new TestLogger(result.getMethod().getMethodName()));
        context.setAttribute("browser", browser);
        open("/");
        DriverManager.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        DriverManager.getDriver().manage().window().maximize();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        try {
            DriverManager.getDriver().quit();
        } finally {
            ReportManager.flushReport();
            DriverManager.unload();
            LoggerManager.unload();
        }
    }

    protected WebDriver getDriver() {
        return DriverManager.getDriver();
    }

    protected TestLogger getLogger() {
        return LoggerManager.getLogger();
    }

    protected void open(String path) {
        String normalizedPath = path.startsWith("/") ? path : "/" + path;
        DriverManager.getDriver().get(FrameworkConfig.get("app.base.url") + normalizedPath);
    }

    private String resolveBrowser(String suiteBrowser) {
        if (suiteBrowser != null && !suiteBrowser.isBlank()) {
            return suiteBrowser;
        }
        return FrameworkConfig.get("browser");
    }
}
