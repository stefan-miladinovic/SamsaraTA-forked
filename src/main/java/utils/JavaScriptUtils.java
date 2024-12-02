package utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JavaScriptUtils {

    private static final String sDragAndDropJavaScriptFilePath = "javascript/drag-and-drop_simulator.js";
    private static final String sDragAndDropJavaScriptFilePath2 = "javascript/drag-and-drop_helper.js";

    private static String loadJavaScriptFile(String sFilePath) {
        String sJavaScript;

        /*  Using Java Files Class
        String sFileFullPath = System.getProperty("user.dir") + "/src/main/resources/" + sFilePath;
        try {
            sJavaScript = Files.readString(Paths.get(sFileFullPath));
        } catch (IOException e) {
            Assert.fail("Cannot read JavaScript file '" + sFileFullPath + "'. Message: " + e.getMessage());
        }
         */

        /* Using CommonsIO FileUtils Class
        String sFileFullPath = System.getProperty("user.dir") + "/src/main/resources/" + sFilePath;
        try {
            File javaScriptFile = new File(sFileFullPath);
            sJavaScript = FileUtils.readFileToString(javaScriptFile, StandardCharsets.UTF_8);
        } catch (IOException e) {
            Assert.fail("Cannot read JavaScript file '" + sFileFullPath + "'. Message: " + e.getMessage());
        }
        */

        // Using GetResourceAsStream with ClassLoader
        InputStream inputStream = PropertiesUtils.class.getClassLoader().getResourceAsStream(sFilePath);
        Assert.assertNotNull(inputStream, "Cannot read JavaScript file '" + sFilePath + "'");
        StringBuilder sJavaScriptStringBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while((line = reader.readLine()) != null) {
                sJavaScriptStringBuilder.append(line).append(" ");
            }
        } catch (IOException e) {
            Assert.fail("Cannot read JavaScript file '" + sFilePath + "'. Message: " + e.getMessage());
        }
        sJavaScript = sJavaScriptStringBuilder.toString();

        return sJavaScript;
    }

    public static void simulateDragAndDrop(WebDriver driver, String sourceLocator, String destinationLocator) {
        LoggerUtils.log.trace("simulateDragAndDrop()");
        String sJavaScript = loadJavaScriptFile(sDragAndDropJavaScriptFilePath);
        sJavaScript = sJavaScript + "DndSimulator.simulate('" + sourceLocator + "', '" + destinationLocator + "')";
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(sJavaScript);
    }

    public static void simulateDragAndDrop2(WebDriver driver, String sourceLocator, String destinationLocator) {
        LoggerUtils.log.trace("simulateDragAndDrop2()");
        String sJavaScript = loadJavaScriptFile(sDragAndDropJavaScriptFilePath2);
        sJavaScript = sJavaScript + "$('" + sourceLocator + "').simulateDragDrop({ dropTarget: '" + destinationLocator + "'});";
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(sJavaScript);
    }
}
