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
        waitUntilPageIsReady(Time.TIME_SHORTER);
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

    public boolean isUsernameDisplayed() {
        LoggerUtils.log.debug("isUsernameDisplayed()");
        return isWebElementDisplayed(usernameText);
    }

    public String getUsername() {
        LoggerUtils.log.debug("getUsername()");
        Assert.assertTrue(isUsernameDisplayed(), "Username is NOT displayed on UserDetails Dialog Box!");
        return getTextFromWebElement(usernameText);
    }

    public boolean isFirstNameDisplayed() {
        LoggerUtils.log.debug("isFirstNameDisplayed()");
        return isWebElementDisplayed(firstNameText);
    }

    public String getFirstName() {
        LoggerUtils.log.debug("getFirstName()");
        Assert.assertTrue(isFirstNameDisplayed(), "First Name is NOT displayed on UserDetails Dialog Box!");
        return getTextFromWebElement(firstNameText);
    }

    public boolean isLastNameDisplayed() {
        LoggerUtils.log.debug("isLastNameDisplayed()");
        return isWebElementDisplayed(lastNameText);
    }

    public String getLastName() {
        LoggerUtils.log.debug("getLastName()");
        Assert.assertTrue(isLastNameDisplayed(), "Last Name is NOT displayed on UserDetails Dialog Box!");
        return getTextFromWebElement(lastNameText);
    }

    public boolean isCreatedAtDateDisplayed() {
        LoggerUtils.log.debug("isCreatedAtDateDisplayed()");
        return isWebElementDisplayed(createdAtText);
    }

    private String getCreatedAtString() {
        Assert.assertTrue(isCreatedAtDateDisplayed(), "CreatedAt Date is NOT displayed on UserDetails Dialog Box!");
        return getTextFromWebElement(createdAtText);
    }

    public Date getCreatedAtDate() {
        String sDateTime = getCreatedAtString();
        LoggerUtils.log.info("Created At String: " + sDateTime);
        sDateTime = sDateTime + " " + DateTimeUtils.getBrowserTimeZone(driver);
        LoggerUtils.log.info("Created At String with TimeZone: " + sDateTime);
        // "06.06.2019. 12:03" + " " + "PST"
        String sPattern = "dd.MM.yyyy. HH:mm z";
        Date date = DateTimeUtils.getParsedDateTime(sDateTime, sPattern);
        LoggerUtils.log.info("Created At Date: " + date);
        return date;
    }

    public boolean isAboutTextDisplayed() {
        LoggerUtils.log.debug("isAboutTextDisplayed()");
        return isWebElementDisplayed(aboutText);
    }

    public String getAboutText() {
        LoggerUtils.log.debug("getAboutText()");
        Assert.assertTrue(isAboutTextDisplayed(), "About Text is NOT displayed on UserDetails Dialog Box!");
        return getTextFromWebElement(aboutText);
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
