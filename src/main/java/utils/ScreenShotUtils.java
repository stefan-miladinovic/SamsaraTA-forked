package utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;

public class ScreenShotUtils {

    private static final String screenShotPath = System.getProperty("user.dir") + PropertiesUtils.getScreenshotsFolder();

    private static String createScreenShotPath(String sTestName) {
        return screenShotPath + sTestName + ".png";
    }

    public static String takeScreenShot(WebDriver driver, String sTestName) {
        LoggerUtils.log.trace("takeScreenShot(" + sTestName + ")");

        String sFilePath = createScreenShotPath(sTestName);

        if(WebDriverUtils.hasDriverQuit(driver)) {
            LoggerUtils.log.warn("ScreenShot for test '" + sTestName + "' could not be taken! Driver instance has already quit!");
            return null;
        }

        TakesScreenshot screenshot = (TakesScreenshot) driver;
        File srcFile = screenshot.getScreenshotAs(OutputType.FILE);
        File dstFile = new File(sFilePath);
        try {
            FileUtils.copyFile(srcFile, dstFile);
            LoggerUtils.log.info("ScreenShot for test '" + sTestName + "' is saved: " + sFilePath);
        } catch (IOException exc) {
            LoggerUtils.log.warn("ScreenShot for test '" + sTestName + "' could not be saved in file '" + sFilePath + "'. Message: " + exc.getMessage());
            return null;
        }
        return sFilePath;
    }
}
