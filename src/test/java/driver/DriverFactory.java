package driver;

import config.FrameworkConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public final class DriverFactory {
    private DriverFactory() {
    }

    public static WebDriver createDriver(String browserName) {
        String executionType = FrameworkConfig.get("execution.type").trim().toLowerCase();
        boolean headless = FrameworkConfig.getBoolean("headless");

        WebDriver driver = "grid".equals(executionType)
                ? createRemoteDriver(browserName, headless)
                : createLocalDriver(browserName, headless);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(FrameworkConfig.getInt("implicit.wait.seconds")));
        driver.manage().window().maximize();
        return driver;
    }

    private static WebDriver createLocalDriver(String browserName, boolean headless) {
        return switch (browserName.trim().toLowerCase()) {
            case "firefox" -> {
                WebDriverManager.firefoxdriver().setup();
                yield new FirefoxDriver(getFirefoxOptions(headless));
            }
            case "edge" -> {
                WebDriverManager.edgedriver().setup();
                yield new EdgeDriver(getEdgeOptions(headless));
            }
            case "chrome" -> {
                WebDriverManager.chromedriver().setup();
                yield new ChromeDriver(getChromeOptions(headless));
            }
            default -> throw new IllegalArgumentException("Unsupported browser: " + browserName);
        };
    }

    private static WebDriver createRemoteDriver(String browserName, boolean headless) {
        try {
            return new RemoteWebDriver(new URL(FrameworkConfig.get("grid.url")), getCapabilities(browserName, headless));
        } catch (MalformedURLException exception) {
            throw new IllegalStateException("Invalid Grid URL", exception);
        }
    }

    private static MutableCapabilities getCapabilities(String browserName, boolean headless) {
        return switch (browserName.trim().toLowerCase()) {
            case "firefox" -> getFirefoxOptions(headless);
            case "edge" -> getEdgeOptions(headless);
            case "chrome" -> getChromeOptions(headless);
            default -> throw new IllegalArgumentException("Unsupported browser: " + browserName);
        };
    }

    private static ChromeOptions getChromeOptions(boolean headless) {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        if (headless) {
            options.addArguments("--headless=new");
        }
        return options;
    }

    private static FirefoxOptions getFirefoxOptions(boolean headless) {
        FirefoxOptions options = new FirefoxOptions();
        if (headless) {
            options.addArguments("-headless");
        }
        return options;
    }

    private static EdgeOptions getEdgeOptions(boolean headless) {
        EdgeOptions options = new EdgeOptions();
        if (headless) {
            options.addArguments("--headless=new");
        }
        return options;
    }
}
