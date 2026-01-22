package utils;

import config.ConfigManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import java.time.Duration;

public class DriverFactory {

    private static WebDriver driver;

    private DriverFactory() {} // prevent instantiation

    // ===================== GET DRIVER =====================
    public static WebDriver getDriver() {
        if (driver == null) {
            String browser = ConfigManager.getProperty("browser").toLowerCase();
            switch (browser) {
                case "chrome":
                    driver = new ChromeDriver();
                    break;
                case "firefox":
                    driver = new FirefoxDriver();
                    break;
                default:
                    throw new RuntimeException("Browser not supported: " + browser);
            }

            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        }
        return driver;
    }

    // ===================== QUIT DRIVER =====================
    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null; // reset for next usage
        }
    }
}
