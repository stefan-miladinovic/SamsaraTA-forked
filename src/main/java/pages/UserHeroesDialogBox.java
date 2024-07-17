package pages;

import data.Time;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import utils.LoggerUtils;

public class UserHeroesDialogBox extends BasePageClass {

    private final String userHeroesDialogBoxString = "//div[@id='heroesModal']";
    private final By userHeroesDialogBoxLocator = By.id("heroesModal");
    private final By closeButtonLocator = By.xpath(userHeroesDialogBoxString + "//button[contains(@class, 'btn-default')]");

    public UserHeroesDialogBox(WebDriver driver) {
        super(driver);
    }

    public UserHeroesDialogBox verifyUserHeroesDialogBox() {
        LoggerUtils.log.debug("verifyUserHeroesDialogBox()");
        Assert.assertTrue(isUserHeroesDialogBoxOpened(Time.TIME_SHORTER), "UserHeroes DialogBox is NOT opened!");
        return this;
    }

    private boolean isUserHeroesDialogBoxOpened(int timeout) {
        LoggerUtils.log.debug("isUserHeroesDialogBoxOpened()");
        WebElement userHeroesDialogBox = getWebElement(userHeroesDialogBoxLocator, timeout);
        return isWebElementVisible(userHeroesDialogBox, timeout);
    }

    private boolean isUserHeroesDialogBoxClosed(int timeout) {
        LoggerUtils.log.debug("isUserHeroesDialogBoxClosed()");
        WebElement userHeroesDialogBox = getWebElement(userHeroesDialogBoxLocator, timeout);
        return isWebElementInvisible(userHeroesDialogBox, timeout);
    }

    public boolean isCloseButtonDisplayed() {
        LoggerUtils.log.debug("isCloseButtonDisplayed()");
        return isWebElementDisplayed(closeButtonLocator);
    }

    public boolean isCloseButtonEnabled() {
        LoggerUtils.log.debug("isCloseButtonEnabled()");
        Assert.assertTrue(isCloseButtonDisplayed(), "Close Button is NOT displayed on UserHeroes DialogBox!");
        WebElement closeButton = getWebElement(closeButtonLocator);
        return isWebElementEnabled(closeButton);
    }

    public String getCloseButtonTitle() {
        LoggerUtils.log.debug("getCloseButtonTitle()");
        Assert.assertTrue(isCloseButtonDisplayed(), "Close Button is NOT displayed on UserHeroes DialogBox!");
        WebElement closeButton = getWebElement(closeButtonLocator);
        return getTextFromWebElement(closeButton);
    }

    public UsersPage clickCloseButton() {
        LoggerUtils.log.debug("clickCloseButton()");
        Assert.assertTrue(isCloseButtonEnabled(), "Close Button is NOT enabled on UserHeroes DialogBox!");
        WebElement closeButton = getWebElement(closeButtonLocator);
        clickOnWebElement(closeButton);
        Assert.assertTrue(isUserHeroesDialogBoxClosed(Time.TIME_SHORTER), "UserHeroes DialogBox is NOT closed!");
        UsersPage usersPage = new UsersPage(driver);
        return usersPage.verifyUsersPage();
    }
}
