package pages;

import data.PageUrlPaths;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import utils.LoggerUtils;

public abstract class CommonLoggedInPageClass extends CommonPageClass {

    protected CommonLoggedInPageClass(WebDriver driver) {
        super(driver);
    }

    private final String headerLocatorString = "//header[@id='headContainer']";
    private final By usersTabLocator = By.xpath(headerLocatorString + "//a[@href='" + PageUrlPaths.USERS_PAGE + "']");


    public boolean isUsersTabDisplayed() {
        LoggerUtils.log.debug("isUsersTabDisplayed()");
        return isWebElementDisplayed(usersTabLocator);
    }

    public boolean isUsersTabEnabled() {
        LoggerUtils.log.debug("isUsersTabEnabled()");
        Assert.assertTrue(isUsersTabDisplayed(), "Users Tab is NOT displayed on Navigation Bar!");
        WebElement usersTab = getWebElement(usersTabLocator);
        return isWebElementEnabled(usersTab);
    }

    public String getUsersTabTitle() {
        LoggerUtils.log.debug("getUsersTabTitle()");
        Assert.assertTrue(isUsersTabDisplayed(), "Users Tab is NOT displayed on Navigation Bar!");
        WebElement usersTab = getWebElement(usersTabLocator);
        return getTextFromWebElement(usersTab);
    }

    public UsersPage clickUsersTab() {
        LoggerUtils.log.debug("clickUsersTab()");
        Assert.assertTrue(isUsersTabEnabled(), "Users Tab is NOT enabled on Navigation Bar!");
        WebElement usersTab = getWebElement(usersTabLocator);
        clickOnWebElement(usersTab);
        UsersPage usersPage = new UsersPage(driver);
        return usersPage.verifyUsersPage();
    }


}
