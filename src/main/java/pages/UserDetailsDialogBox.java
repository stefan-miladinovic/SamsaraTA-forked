package pages;

import data.Time;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import utils.LoggerUtils;

public class UserDetailsDialogBox extends BasePageClass {

    private final String userDetailsDialogBoxString = "//div[@id='userModal']";
    private final By userDetailsDialogBoxLocator = By.id("userModal");
    private final By closeButtonLocator = By.xpath(userDetailsDialogBoxString + "//button[contains(@class, 'btn-default')]");
    
    public UserDetailsDialogBox(WebDriver driver) {
        super(driver);
    }

    public UserDetailsDialogBox verifyUserDetailsDialogBox() {
        LoggerUtils.log.debug("verifyUserDetailsDialogBox()");
        Assert.assertTrue(isUserDetailsDialogBoxOpened(Time.TIME_SHORTER), "User Details DialogBox is NOT opened!");
        return this;
    }

    private boolean isUserDetailsDialogBoxOpened(int timeout) {
        LoggerUtils.log.debug("isUserDetailsDialogBoxOpened()");
        WebElement userDetailsDialogBox = getWebElement(userDetailsDialogBoxLocator, timeout);
        return isWebElementVisible(userDetailsDialogBox, timeout);
    }

    private boolean isUserDetailsDialogBoxClosed(int timeout) {
        LoggerUtils.log.debug("isUserDetailsDialogBoxClosed()");
        WebElement userDetailsDialogBox = getWebElement(userDetailsDialogBoxLocator, timeout);
        return isWebElementInvisible(userDetailsDialogBox, timeout);
    }

    public boolean isCloseButtonDisplayed() {
        LoggerUtils.log.debug("isCloseButtonDisplayed()");
        return isWebElementDisplayed(closeButtonLocator);
    }

    public boolean isCloseButtonEnabled() {
        LoggerUtils.log.debug("isCloseButtonEnabled()");
        Assert.assertTrue(isCloseButtonDisplayed(), "Close Button is NOT displayed on User Details DialogBox!");
        WebElement closeButton = getWebElement(closeButtonLocator);
        return isWebElementEnabled(closeButton);
    }

    public String getCloseButtonTitle() {
        LoggerUtils.log.debug("getCloseButtonTitle()");
        Assert.assertTrue(isCloseButtonDisplayed(), "Close Button is NOT displayed on User Details DialogBox!");
        WebElement closeButton = getWebElement(closeButtonLocator);
        return getTextFromWebElement(closeButton);
    }

    public UsersPage clickCloseButton() {
        LoggerUtils.log.debug("clickCloseButton()");
        Assert.assertTrue(isCloseButtonEnabled(), "Close Button is NOT enabled on User Details DialogBox!");
        WebElement closeButton = getWebElement(closeButtonLocator);
        clickOnWebElement(closeButton);
        Assert.assertTrue(isUserDetailsDialogBoxClosed(Time.TIME_SHORTER), "User Details DialogBox is NOT closed!");
        UsersPage usersPage = new UsersPage(driver);
        return usersPage.verifyUsersPage();
    }
}
