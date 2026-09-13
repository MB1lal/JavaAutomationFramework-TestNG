package com.automation.driver;

import com.automation.config.ConfigReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * Creates and holds one WebDriver per test thread so tests can run in parallel safely.
 */
public final class DriverFactory {

    private static final Logger log = LogManager.getLogger(DriverFactory.class);
    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    private DriverFactory() {
    }

    public static WebDriver getDriver() {
        WebDriver driver = DRIVER.get();
        if (driver == null) {
            throw new IllegalStateException("WebDriver was not created. Did @BeforeMethod run?");
        }
        return driver;
    }

    public static void createDriver() {
        ConfigReader config = ConfigReader.getInstance();
        String browser = config.get("browser", "chrome");
        boolean headless = config.getBoolean("headless", true);

        WebDriver driver = switch (browser.toLowerCase()) {
            case "firefox" -> new FirefoxDriver(firefoxOptions(headless));
            case "chrome" -> new ChromeDriver(chromeOptions(headless));
            default -> throw new IllegalArgumentException("Unsupported browser: " + browser);
        };

        driver.manage().timeouts().pageLoadTimeout(
                Duration.ofSeconds(config.getInt("page.load.timeout.seconds", 60)));
        DRIVER.set(driver);
        log.info("Created {} driver (headless={})", browser, headless);
    }

    public static void quitDriver() {
        WebDriver driver = DRIVER.get();
        if (driver != null) {
            driver.quit();
            DRIVER.remove();
            log.info("Quit driver");
        }
    }

    public static String downloadDir() {
        Path dir = Paths.get(System.getProperty("user.dir"), "test-output", "downloads").toAbsolutePath();
        dir.toFile().mkdirs();
        return dir.toString();
    }

    private static ChromeOptions chromeOptions(boolean headless) {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        if (headless) {
            options.addArguments("--headless=new");
        }
        options.addArguments("--no-sandbox", "--disable-dev-shm-usage", "--window-size=1920,1080",
                "--incognito", "--remote-allow-origins=*");
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("download.default_directory", downloadDir());
        prefs.put("download.prompt_for_download", false);
        options.setExperimentalOption("prefs", prefs);
        return options;
    }

    private static FirefoxOptions firefoxOptions(boolean headless) {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();
        if (headless) {
            options.addArguments("--headless");
        }
        options.addPreference("browser.download.folderList", 2);
        options.addPreference("browser.download.dir", downloadDir());
        options.addPreference("browser.helperApps.neverAsk.saveToDisk", "application/json,text/plain");
        return options;
    }
}
