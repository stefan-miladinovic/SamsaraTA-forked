package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import utils.LoggerUtils;

public class CommonPageClass extends BasePageClass {

    private final By pageTitleLocator = By.xpath("//div[contains(@class, 'panel-title')]");

    protected CommonPageClass(WebDriver driver) {
        super(driver);
    }

    public boolean isPageTitleDisplayed() {
        LoggerUtils.log.debug("isPageTitleDisplayed()");
        return isWebElementDisplayed(pageTitleLocator);
    }

    public String getPageTitle() {
        LoggerUtils.log.debug("getPageTitle()");
        Assert.assertTrue(isPageTitleDisplayed(), "Page Title is NOT displayed!");
        WebElement pageTitle = getWebElement(pageTitleLocator);
        return getTextFromWebElement(pageTitle);
    }
}
