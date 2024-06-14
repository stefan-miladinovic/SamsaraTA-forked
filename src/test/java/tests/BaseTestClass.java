package tests;

import org.openqa.selenium.WebDriver;
import utils.WebDriverUtils;

public abstract class BaseTestClass {

    protected WebDriver setUpDriver() {
        WebDriver driver = WebDriverUtils.setUpDriver();
        // additional setup related to application
        return driver;
    }

    protected void quitDriver(WebDriver driver) {
        WebDriverUtils.quitDriver(driver);
    }
}
