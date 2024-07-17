package utils;

import data.Time;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.testng.Assert;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class WebDriverUtils {

    public static WebDriver setUpDriver() {
        WebDriver driver = null;

        // String sDriversFolder = PropertiesUtils.getDriversFolder();
        // String sPathDriverChrome = sDriversFolder + "chromedriver.exe";
        // String sPathDriverFirefox = sDriversFolder + "geckodriver.exe";
        // String sPathDriverEdge = sDriversFolder + "msedgedriver.exe";

        String sHubUrl = PropertiesUtils.getHubUrl();

        String sBrowser = PropertiesUtils.getBrowser();
        boolean bRemote = PropertiesUtils.getRemote();
        boolean bHeadless = PropertiesUtils.getHeadless();

        LoggerUtils.log.debug("setUpDriver(Browser: " + sBrowser + ", Remote: " + bRemote + ", Headless: " + bHeadless +")");

        try {
            switch (sBrowser) {
                case "chrome": {
                    ChromeOptions options = new ChromeOptions();
                    if (bHeadless) {
                        options.addArguments("--silent");
                    }
                    if (bRemote) {
                        RemoteWebDriver remoteDriver = new RemoteWebDriver(new URL(sHubUrl), options);
                        driver = remoteDriver;
                    } else {
                        //System.setProperty("webdriver.chrome.driver", sPathDriverChrome);
                        driver = new ChromeDriver(options);
                    }
                    break;
                }
                case "firefox": {
                    FirefoxOptions options = new FirefoxOptions();
                    if (bHeadless) {
                        options.addArguments("--silent");
                    }
                    if (bRemote) {
                        RemoteWebDriver remoteDriver = new RemoteWebDriver(new URL(sHubUrl), options);
                        driver = remoteDriver;
                    } else {
                        //System.setProperty("webdriver.gecko.driver", sPathDriverFirefox);
                        driver = new FirefoxDriver(options);
                    }
                    break;
                }
                case "edge": {
                    EdgeOptions options = new EdgeOptions();
                    if (bHeadless) {
                        options.addArguments("--silent");
                    }
                    if (bRemote) {
                        RemoteWebDriver remoteDriver = new RemoteWebDriver(new URL(sHubUrl), options);
                        driver = remoteDriver;
                    } else {
                        //System.setProperty("webdriver.edge.driver", sPathDriverEdge);
                        driver = new EdgeDriver(options);
                    }
                    break;
                }
                default: {
                    Assert.fail("Cannot create driver! Browser '" + sBrowser + "' is not recognized");
                }
            }
        } catch (MalformedURLException e) {
            Assert.fail("Cannot create driver! Error Message: " + e.getMessage());
        }

        // Setup Implicit Waits
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Time.IMPLICIT_TIMEOUT));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(Time.PAGE_LOAD_TIMEOUT));
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(Time.ASYNC_SCRIPT_TIMEOUT));

        // setup driver
        driver.manage().window().maximize();


        return driver;
    }

    private static SessionId getSessionID(WebDriver driver) {
        LoggerUtils.log.trace("getSessionID()");
        RemoteWebDriver remoteDriver = (RemoteWebDriver) driver;
        return remoteDriver.getSessionId();
    }

    public static boolean hasDriverQuit(WebDriver driver) {
        LoggerUtils.log.trace("hasDriverQuit()");
        if(driver != null) {
            return getSessionID(driver) == null;
        } else {
            return true;
        }
    }

    public static void quitDriver(WebDriver driver) {
        LoggerUtils.log.debug("quitDriver()");
        if(!hasDriverQuit(driver)) {
            driver.quit();
        }
    }

    public static void setImplicitWait(WebDriver driver, int timeout) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeout));
    }
}
