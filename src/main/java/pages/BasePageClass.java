package pages;

import data.Time;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import utils.JavaScriptUtils;
import utils.LoggerUtils;
import utils.PropertiesUtils;
import utils.WebDriverUtils;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Function;

public abstract class BasePageClass {

    protected WebDriver driver;

    protected BasePageClass(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
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

    protected WebElement getWebElement(By locator, int timeout, int pollingTime) {
        LoggerUtils.log.trace("getWebElement(" + locator + ", " + timeout + ", " + pollingTime + ")");
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(timeout))
                .pollingEvery(Duration.ofSeconds(pollingTime))
                .ignoring(NoSuchElementException.class);
        WebElement element  = wait.until(new Function<WebDriver, WebElement>() {
            @Override
            public WebElement apply(WebDriver webDriver) {
                return driver.findElement(locator);
            }
        });
        return element;
    }

    protected List<WebElement> getWebElements(By locator) {
        LoggerUtils.log.trace("getWebElements(" + locator + ")");
        return driver.findElements(locator);
    }

    protected WebElement getNestedWebElement(WebElement element, By locator) {
        LoggerUtils.log.trace("getNestedWebElement(" + element + ", " + locator + ")");
        return element.findElement(locator);
    }

    protected List<WebElement> getNestedWebElements(WebElement element, By locator) {
        LoggerUtils.log.trace("getNestedWebElements(" + element + ", " + locator + ")");
        return element.findElements(locator);
    }

    protected WebElement getNestedWebElement(WebElement element, By locator, int timeout) {
        LoggerUtils.log.trace("getNestedWebElement(" + element + ", " + locator + ", " + timeout + ")");
        WebDriverWait wait = getWebDriverWait(timeout);
        return wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(element, locator));
    }

    protected WebElement waitForWebElementToBeClickable(WebElement element, int timeout) {
        LoggerUtils.log.trace("waitForWebElementToBeClickable(" + element + ", " + timeout + ")");
        WebDriverWait wait = getWebDriverWait(timeout);
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    protected boolean waitForWebElementToBeSelected(WebElement element, int timeout) {
        LoggerUtils.log.trace("waitForWebElementToBeSelected(" + element + ", " + timeout + ")");
        WebDriverWait wait = getWebDriverWait(timeout);
        return wait.until(ExpectedConditions.elementToBeSelected(element));
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

    protected boolean isWebElementDisplayed(WebElement element) {
        LoggerUtils.log.trace("isWebElementDisplayed(" + element + ")");
        try {
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    protected boolean isWebElementDisplayed(WebElement element, int timeout) {
        LoggerUtils.log.trace("isWebElementDisplayed(" + element + ", " + timeout + ")");
        WebDriverUtils.setImplicitWait(driver, timeout);
        boolean bResult = isWebElementDisplayed(element);
        WebDriverUtils.setImplicitWait(driver, Time.IMPLICIT_TIMEOUT);
        return bResult;
    }

    protected boolean isNestedWebElementDisplayed(WebElement element, By locator) {
        LoggerUtils.log.trace("isNestedWebElementDisplayed(" + element + ", " + locator + ")");
        try {
            WebElement webElement = getNestedWebElement(element, locator);
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

    protected boolean isWebElementReadOnly(WebElement element) {
        LoggerUtils.log.trace("isWebElementReadOnly(" + element + ")");
        String sResult = getAttributeFromWebElement(element, "readonly");
        return sResult != null;
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

    protected boolean isWebElementSelected(WebElement element) {
        LoggerUtils.log.trace("isWebElementSelected(" + element + ")");
        return element.isSelected();
    }

    protected boolean isWebElementSelected(WebElement element, int timeout) {
        LoggerUtils.log.trace("isWebElementSelected(" + element + ", " + timeout + ")");
        try {
            return waitForWebElementToBeSelected(element, timeout);
        } catch (Exception e) {
            return false;
        }
    }

    protected String getAttributeFromWebElement(WebElement element, String attribute) {
        LoggerUtils.log.trace("getAttributeFromWebElement(" + element + ", " + attribute + ")");
        return element.getAttribute(attribute);
    }

    protected void setAttributeToWebElement(WebElement element, String attribute, String value) {
        LoggerUtils.log.trace("setAttributeToWebElement(" + element + ", " + attribute + ", " + value + ")");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        // element.setAttribute('attribute_name', 'attribute_new_value')
        String sJavaScript = "arguments[0].setAttribute('" + attribute + "' , '" + value + "')";
        js.executeScript(sJavaScript, element);
    }

    protected void setCssStyleToWebElement(WebElement element, String styleName, String value) {
        LoggerUtils.log.trace("setAttributeToWebElement(" + element + ", " + styleName + ", " + value + ")");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        // element.style.visibility='visible'
        String sJavaScript = "arguments[0].style." + styleName + "='" + value + "';";
        js.executeScript(sJavaScript, element);
    }

    protected void removeAttributeFromWebElement(WebElement element, String attribute) {
        LoggerUtils.log.trace("removeAttributeFromWebElement(" + element + ", " + attribute + ")");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String sJavaScript = "arguments[0].removeAttribute('" + attribute + "')";
        js.executeScript(sJavaScript, element);
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

    /*
    protected void waitUntilPageIsReady(int timeout) {
        LoggerUtils.log.trace("waitUntilPageIsReady(" + timeout + ")");
        WebDriverWait wait = getWebDriverWait(timeout);
        wait.until(new Function<WebDriver, Boolean>() {
            public Boolean apply (WebDriver driver) {
                JavascriptExecutor js = (JavascriptExecutor)  driver;
                return js.executeScript("return document.readyState").equals("complete");
            }
        });
    }
    */

    protected void waitUntilPageIsReady(int timeout) {
        LoggerUtils.log.trace("waitUntilPageIsReady(" + timeout + ")");
        WebDriverWait wait = getWebDriverWait(timeout);
        wait.until(driver -> {
            JavascriptExecutor js = (JavascriptExecutor)  driver;
            return js.executeScript("return document.readyState").equals("complete");
        });
    }

    protected void waitForUrlChangeToExactUrl(String url, int timeout) {
        LoggerUtils.log.trace("waitForUrlChangeToExactUrl(" + url + ", " + timeout + ")");
        WebDriverWait wait = getWebDriverWait(timeout);
        wait.until(ExpectedConditions.urlToBe(url));
    }

    protected boolean isCheckBoxDisplayed(WebElement checkBox) {
        LoggerUtils.log.trace("isCheckBoxDisplayed()");
        return isWebElementDisplayed(checkBox);
    }

    protected boolean isCheckBoxEnabled(WebElement checkBox) {
        LoggerUtils.log.trace("isCheckBoxEnabled()");
        Assert.assertTrue(isCheckBoxDisplayed(checkBox), "CheckBox " + checkBox + " is NOT Displayed");
        return isWebElementEnabled(checkBox);
    }

    protected boolean isCheckBoxChecked(WebElement checkBox) {
        LoggerUtils.log.trace("isCheckBoxChecked()");
        Assert.assertTrue(isCheckBoxDisplayed(checkBox), "CheckBox " + checkBox + " is NOT Displayed");
        return isWebElementSelected(checkBox);
    }

    protected void checkCheckBox(WebElement checkBox) {
        LoggerUtils.log.trace("checkCheckBox()");
        Assert.assertTrue(isCheckBoxEnabled(checkBox), "CheckBox " + checkBox + " is NOT enabled");
        if(!isCheckBoxChecked(checkBox)) {
            clickOnWebElement(checkBox);
        }
    }

    protected void uncheckCheckBox(WebElement checkBox) {
        LoggerUtils.log.trace("uncheckCheckBox()");
        Assert.assertTrue(isCheckBoxEnabled(checkBox), "CheckBox " + checkBox + " is NOT enabled");
        if(isCheckBoxChecked(checkBox)) {
            clickOnWebElement(checkBox);
        }
    }

    protected boolean isRadioButtonGroupDisplayed(WebElement radioButtonGroup) {
        LoggerUtils.log.trace("isRadioButtonGroupDisplayed()");
        return isWebElementDisplayed(radioButtonGroup);
    }

    private String createXpathForRadioButtonOption(String option) {
        return ".//input[@type='radio' and @value='" + option + "']";
    }

    private WebElement getRadioButtonOptionWebElement(WebElement radioButtonGroup, String option) {
        String xPath = createXpathForRadioButtonOption(option);
        return getNestedWebElement(radioButtonGroup, By.xpath(xPath));
    }

    protected boolean isRadioButtonOptionDisplayed(WebElement radioButtonGroup, String option) {
        LoggerUtils.log.trace("isRadioButtonOptionDisplayed(" + option + ")");
        String xPath = createXpathForRadioButtonOption(option);
        return isNestedWebElementDisplayed(radioButtonGroup, By.xpath(xPath));
    }

    protected boolean isRadioButtonOptionEnabled(WebElement radioButtonGroup, String option) {
        LoggerUtils.log.trace("isRadioButtonOptionEnabled(" + option + ")");
        Assert.assertTrue(isRadioButtonOptionDisplayed(radioButtonGroup, option), "RadioButton option '" + option + " is NOT Displayed");
        WebElement radioButtonOption = getRadioButtonOptionWebElement(radioButtonGroup, option);
        return isWebElementEnabled(radioButtonOption);
    }

    protected boolean isRadioButtonOptionSelected(WebElement radioButtonGroup, String option) {
        LoggerUtils.log.trace("isRadioButtonOptionSelected(" + option + ")");
        Assert.assertTrue(isRadioButtonOptionDisplayed(radioButtonGroup, option), "RadioButton option '" + option + " is NOT Displayed");
        WebElement radioButtonOption = getRadioButtonOptionWebElement(radioButtonGroup, option);
        return isWebElementSelected(radioButtonOption);
    }

    protected void selectRadioButtonOption(WebElement radioButtonGroup, String option) {
        LoggerUtils.log.trace("selectRadioButtonOption(" + option + ")");
        Assert.assertTrue(isRadioButtonOptionEnabled(radioButtonGroup, option), "RadioButton option '" + option + " is NOT Enabled");
        WebElement radioButtonOption = getRadioButtonOptionWebElement(radioButtonGroup, option);
        clickOnWebElement(radioButtonOption);
    }

    protected boolean isDropDownListDisplayed(WebElement dropDown) {
        LoggerUtils.log.trace("isDropDownListDisplayed()");
        return isWebElementDisplayed(dropDown);
    }

    protected boolean isDropDownListEnabled(WebElement dropDown) {
        LoggerUtils.log.trace("isDropDownListEnabled()");
        Assert.assertTrue(isDropDownListDisplayed(dropDown), "DropDownList " + dropDown + " is NOT Displayed");
        return isWebElementEnabled(dropDown);
    }

    protected String getSelectedDropDownListOption(WebElement dropDown) {
        LoggerUtils.log.trace("getSelectedDropDownListOption()");
        Assert.assertTrue(isDropDownListDisplayed(dropDown), "DropDownList " + dropDown + " is NOT Displayed");
        Select options = new Select(dropDown);
        WebElement selectedOption = options.getFirstSelectedOption();
        return getTextFromWebElement(selectedOption);
    }

    protected void selectDropDownListOptionByText(WebElement dropDown, String option) {
        LoggerUtils.log.trace("selectDropDownListOptionByText(" + option + ")");
        Assert.assertTrue(isDropDownListEnabled(dropDown), "DropDownList " + dropDown + " is NOT Enabled");
        Select options = new Select(dropDown);
        options.selectByVisibleText(option);
    }

    protected void moveMouseToWebElement(WebElement element) {
        LoggerUtils.log.trace("moveMouseToWebElement(" + element + ")");
        Actions action = new Actions(driver);
        action.moveToElement(element).perform();
    }

    protected void doDragAndDrop(WebElement source, WebElement destination) {
        LoggerUtils.log.trace("doDragAndDrop(" + source + ", " + destination + ")");
        Actions action = new Actions(driver);
        action.dragAndDrop(source, destination).perform();
    }

    protected void doDragAndDropJS(String sourceLocator, String destinationLocator) {
        LoggerUtils.log.trace("doDragAndDropJS(" + sourceLocator + ", " + destinationLocator + ")");
        JavaScriptUtils.simulateDragAndDrop(driver, sourceLocator, destinationLocator);
    }

    protected void switchToFrame(WebElement frame) {
        LoggerUtils.log.trace("switchToFrame(" + frame + ")");
        driver.switchTo().frame(frame);
    }

    protected void switchToDefaultContent() {
        LoggerUtils.log.trace("switchToDefaultContent()");
        driver.switchTo().defaultContent();
    }
}
