package pages;

import data.Time;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import utils.DateTimeUtils;
import utils.LoggerUtils;

import java.util.Date;

public class UserDetailsDialogBox extends BasePageClass {

    private final String userDetailsDialogBoxString = "//div[@id='userModal']";
    private final By userDetailsDialogBoxLocator = By.id("userModal");
    private final By closeButtonLocator = By.xpath(userDetailsDialogBoxString + "//button[contains(@class, 'btn-default')]");

    private final String userDetailsMediaBodyString = userDetailsDialogBoxString + "//div[@class='media-body']";

    @FindBy(xpath = userDetailsMediaBodyString + "//span[@class='username']")
    private WebElement usernameText;

    @FindBy(xpath = userDetailsMediaBodyString + "//span[@class='firstName']")
    private WebElement firstNameText;

    @FindBy(xpath = userDetailsMediaBodyString + "//span[@class='lastName']")
    private WebElement lastNameText;

    @FindBy(xpath = userDetailsMediaBodyString + "//span[@class='created']")
    private WebElement createdAtText;

    @FindBy(xpath = userDetailsMediaBodyString + "//span[@class='about']")
    private WebElement aboutText;
    
    public UserDetailsDialogBox(WebDriver driver) {
        super(driver);
    }

    public UserDetailsDialogBox verifyUserDetailsDialogBox() {
        LoggerUtils.log.debug("verifyUserDetailsDialogBox()");
        Assert.assertTrue(isUserDetailsDialogBoxOpened(Time.TIME_SHORTER), "UserDetails DialogBox is NOT opened!");
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

    public boolean isCreatedAtDateDisplayed() {
        LoggerUtils.log.debug("isCreatedAtDateDisplayed()");
        return isWebElementDisplayed(createdAtText);
    }

    private String getCreatedAtString() {
        Assert.assertTrue(isCreatedAtDateDisplayed(), "CreatedAt Date is NOT displayed on UserDetails Dialog Box!");
        return getTextFromWebElement(createdAtText);
    }

    // TODO: Implement comparing time zones in case of remote machines
    public Date getCreatedAtDate() {
        String sDateTime = getCreatedAtString();
        String sPattern = "dd.MM.yyyy. HH:mm";
        Date date = DateTimeUtils.getParsedDateTime(sDateTime, sPattern);
        return date;
    }

    public boolean isCloseButtonDisplayed() {
        LoggerUtils.log.debug("isCloseButtonDisplayed()");
        return isWebElementDisplayed(closeButtonLocator);
    }

    public boolean isCloseButtonEnabled() {
        LoggerUtils.log.debug("isCloseButtonEnabled()");
        Assert.assertTrue(isCloseButtonDisplayed(), "Close Button is NOT displayed on UserDetails DialogBox!");
        WebElement closeButton = getWebElement(closeButtonLocator);
        return isWebElementEnabled(closeButton);
    }

    public String getCloseButtonTitle() {
        LoggerUtils.log.debug("getCloseButtonTitle()");
        Assert.assertTrue(isCloseButtonDisplayed(), "Close Button is NOT displayed on UserDetails DialogBox!");
        WebElement closeButton = getWebElement(closeButtonLocator);
        return getTextFromWebElement(closeButton);
    }

    public UsersPage clickCloseButton() {
        LoggerUtils.log.debug("clickCloseButton()");
        Assert.assertTrue(isCloseButtonEnabled(), "Close Button is NOT enabled on UserDetails DialogBox!");
        WebElement closeButton = getWebElement(closeButtonLocator);
        clickOnWebElement(closeButton);
        Assert.assertTrue(isUserDetailsDialogBoxClosed(Time.TIME_SHORTER), "UserDetails DialogBox is NOT closed!");
        UsersPage usersPage = new UsersPage(driver);
        return usersPage.verifyUsersPage();
    }
}
