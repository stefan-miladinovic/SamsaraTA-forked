package tests;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import utils.LoggerUtils;
import utils.PropertiesUtils;
import utils.ScreenShotUtils;
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

    @Deprecated
    protected void tearDown(WebDriver driver, boolean bSuccess, String sTestName) {
        try {
            if (!bSuccess) {
                ScreenShotUtils.takeScreenShot(driver, sTestName);
            }
        } finally {
            quitDriver(driver);
        }
    }

    protected void tearDown(WebDriver driver, ITestResult testResult) {
        String sTestName = testResult.getTestClass().getName();
        String sFileName = sTestName + "_" + testResult.getEndMillis();
        try {
            if(testResult.getStatus() == ITestResult.FAILURE && PropertiesUtils.getTakeScreenshots()) {
                ScreenShotUtils.takeScreenShot(driver, sFileName);
            }
        } catch (AssertionError | Exception e) {
            LoggerUtils.log.error("Exception occurred in tearDown(" + sTestName + ")! Message: " + e.getMessage());
        }
        finally {
            quitDriver(driver);
        }
    }
}
