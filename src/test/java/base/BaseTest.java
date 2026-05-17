package base;

import config.FrameworkConfig;
import driver.DriverFactory;
import driver.DriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public abstract class BaseTest {

    @BeforeMethod(alwaysRun = true)
    @Parameters({"browser"})
    public void setUp(@Optional String suiteBrowser, ITestContext context) {
        String browser = resolveBrowser(suiteBrowser);
        WebDriver driver = DriverFactory.createDriver(browser);
        DriverManager.setDriver(driver);
        context.setAttribute("browser", browser);
        open("/");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        try {
            DriverManager.getDriver().quit();
        } finally {
            DriverManager.unload();
        }
    }

    protected WebDriver getDriver() {
        return DriverManager.getDriver();
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
