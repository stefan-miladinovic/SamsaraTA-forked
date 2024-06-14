package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.LoggerUtils;
import utils.PropertiesUtils;

import java.time.Duration;
import java.util.List;

public abstract class BasePageClass {

    protected WebDriver driver;

    protected BasePageClass(WebDriver driver) {
        this.driver = driver;
    }

    protected String getPageUrl(String sPath) {
        LoggerUtils.log.trace("getPageUrl(" + sPath + ")");
        return PropertiesUtils.getBaseUrl() + sPath;
    }

    protected void openUrl(String url) {
        LoggerUtils.log.trace("openUrl(" + url + ")");
        driver.get(url);
    }

    private WebDriverWait getWebDriverWait(int timeout) {
        LoggerUtils.log.trace("getWebDriverWait(" + timeout + ")");
        return new WebDriverWait(driver, Duration.ofSeconds(timeout));
    }

    protected WebElement getWebElement(By locator) {
        LoggerUtils.log.trace("getWebElement(" + locator + ")");
        return driver.findElement(locator);
    }

    protected WebElement getWebElement(By locator, int timeout) {
        LoggerUtils.log.trace("getWebElement(" + locator + ", " + timeout + ")");
        WebDriverWait wait = getWebDriverWait(timeout);
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    protected List<WebElement> getWebElements(By locator) {
        LoggerUtils.log.trace("getWebElements(" + locator + ")");
        return driver.findElements(locator);
    }

    protected WebElement waitForWebElementToBeClickable(WebElement element, int timeout) {
        LoggerUtils.log.trace("waitForWebElementToBeClickable(" + element + ", " + timeout + ")");
        WebDriverWait wait = getWebDriverWait(timeout);
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    protected WebElement waitForWebElementToBeVisible(WebElement element, int timeout) {
        LoggerUtils.log.trace("waitForWebElementToBeVisible(" + element + ", " + timeout + ")");
        WebDriverWait wait = getWebDriverWait(timeout);
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    protected boolean waitForWebElementToBeInvisible(WebElement element, int timeout) {
        LoggerUtils.log.trace("waitForWebElementToBeInvisible(" + element + ", " + timeout + ")");
        WebDriverWait wait = getWebDriverWait(timeout);
        return wait.until(ExpectedConditions.invisibilityOf(element));
    }

    protected boolean isWebElementDisplayed(By locator) {
        LoggerUtils.log.trace("isWebElementDisplayed(" + locator + ")");
        try {
            WebElement webElement = getWebElement(locator);
            return webElement.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    protected boolean isWebElementDisplayed(By locator, int timeout) {
        LoggerUtils.log.trace("isWebElementDisplayed(" + locator + ", " + timeout + ")");
        try {
            WebElement webElement = getWebElement(locator, timeout);
            return webElement.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

//    protected boolean isWebElementDisplayed(By locator) {
//        LoggerUtils.log.trace("isWebElementDisplayed(" + locator + ")");
//
//            List<WebElement> webElements = getWebElements(locator);
//            if(!webElements.isEmpty()) {
//                return webElements.get(0).isDisplayed();
//            } else {
//                return false;
//            }
//    }

    protected boolean isWebElementEnabled(WebElement element) {
        LoggerUtils.log.trace("isWebElementEnabled(" + element + ")");
        return element.isEnabled();
    }

    protected boolean isWebElementEnabled(WebElement element, int timeout) {
        LoggerUtils.log.trace("isWebElementEnabled(" + element + ", " + timeout + ")");
        try {
            WebElement webElement = waitForWebElementToBeClickable(element, timeout);
            return webElement != null;

        } catch (Exception e) {
            return false;
        }
    }

    protected boolean isWebElementVisible(WebElement element, int timeout) {
        LoggerUtils.log.trace("isWebElementVisible(" + element + ", " + timeout + ")");
        try {
            WebElement webElement = waitForWebElementToBeVisible(element, timeout);
            return webElement != null;
        } catch (Exception e) {
            return false;
        }
    }

    protected boolean isWebElementInvisible(WebElement element, int timeout) {
        LoggerUtils.log.trace("isWebElementInvisible(" + element + ", " + timeout + ")");
        try {
            return waitForWebElementToBeInvisible(element, timeout);
        } catch (Exception e) {
            return true;
        }
    }

    protected String getAttributeFromWebElement(WebElement element, String attribute) {
        LoggerUtils.log.trace("getAttributeFromWebElement(" + element + ", " + attribute + ")");
        return element.getAttribute(attribute);
    }

    protected String getValueFromWebElement(WebElement element) {
        LoggerUtils.log.trace("getValueFromWebElement(" + element + ")");
        return getAttributeFromWebElement(element, "value");
    }

    protected String getValueFromWebElementJS(WebElement element) {
        LoggerUtils.log.trace("getValueFromWebElementJS(" + element + ")");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        return (String) js.executeScript("return arguments[0].value", element);
    }

    protected String getPlaceholderFromWebElement(WebElement element) {
        LoggerUtils.log.trace("getPlaceholderFromWebElement(" + element + ")");
        return getAttributeFromWebElement(element, "placeholder");
    }

    protected void clickOnWebElement(WebElement element) {
        LoggerUtils.log.trace("clickOnWebElement(" + element + ")");
        element.click();
    }

    protected void clickOnWebElementJS(WebElement element) {
        LoggerUtils.log.trace("clickOnWebElementJS(" + element + ")");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click()", element);
    }

    protected void typeTextToWebElement(WebElement element, String text) {
        LoggerUtils.log.trace("typeTextToWebElement(" + element + ", " + text + ")");
        element.sendKeys(text);
    }

    protected void clearAndTypeTextToWebElement(WebElement element, String text) {
        LoggerUtils.log.trace("clearAndTypeTextToWebElement(" + element + ", " + text + ")");
        element.clear();
        element.sendKeys(text);
    }

    protected String getTextFromWebElement(WebElement element) {
        LoggerUtils.log.trace("getTextFromWebElement(" + element + ")");
        return element.getText();
    }

    protected void waitForUrlChange(String url, int timeout) {
        LoggerUtils.log.trace("waitForUrlChange(" + url + ", " + timeout + ")");
        WebDriverWait wait = getWebDriverWait(timeout);
        wait.until(ExpectedConditions.urlContains(url));
    }

    protected void waitForUrlChangeToExactUrl(String url, int timeout) {
        LoggerUtils.log.trace("waitForUrlChangeToExactUrl(" + url + ", " + timeout + ")");
        WebDriverWait wait = getWebDriverWait(timeout);
        wait.until(ExpectedConditions.urlToBe(url));
    }

}
