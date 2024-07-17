package pages;

import data.Time;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import utils.LoggerUtils;

public class AddUserDialogBox extends BasePageClass {

    private final String addUserDialogBoxString = "//div[@id='addUserModal']";
    private final By addUserDialogBoxLocator = By.id("addUserModal");
    private final By cancelButtonLocator = By.xpath(addUserDialogBoxString + "//button[contains(@class, 'btn-default')]");

    public AddUserDialogBox(WebDriver driver) {
        super(driver);
    }

    public AddUserDialogBox verifyAddUserDialogBox() {
        LoggerUtils.log.debug("verifyAddUserDialogBox()");
        Assert.assertTrue(isAddUserDialogBoxOpened(Time.TIME_SHORTER), "AddUser DialogBox is NOT opened!");
        return this;
    }

    private boolean isAddUserDialogBoxOpened(int timeout) {
        LoggerUtils.log.debug("isAddUserDialogBoxOpened()");
        WebElement addUserDialogBox = getWebElement(addUserDialogBoxLocator, timeout);
        return isWebElementVisible(addUserDialogBox, timeout);
    }

    private boolean isAddUserDialogBoxClosed(int timeout) {
        LoggerUtils.log.debug("isAddUserDialogBoxClosed()");
        WebElement addUserDialogBox = getWebElement(addUserDialogBoxLocator, timeout);
        return isWebElementInvisible(addUserDialogBox, timeout);
    }

    public boolean isCancelButtonDisplayed() {
        LoggerUtils.log.debug("isCancelButtonDisplayed()");
        return isWebElementDisplayed(cancelButtonLocator);
    }

    public boolean isCancelButtonEnabled() {
        LoggerUtils.log.debug("isCancelButtonEnabled()");
        Assert.assertTrue(isCancelButtonDisplayed(), "Cancel Button is NOT displayed on AddUser DialogBox!");
        WebElement cancelButton = getWebElement(cancelButtonLocator);
        return isWebElementEnabled(cancelButton);
    }

    public String getCancelButtonTitle() {
        LoggerUtils.log.debug("getCancelButtonTitle()");
        Assert.assertTrue(isCancelButtonDisplayed(), "Cancel Button is NOT displayed on AddUser DialogBox!");
        WebElement cancelButton = getWebElement(cancelButtonLocator);
        return getTextFromWebElement(cancelButton);
    }

    public UsersPage clickCancelButton() {
        Assert.assertTrue(isCancelButtonEnabled(), "Cancel Button is NOT enabled on AddUser DialogBox!");
        WebElement cancelButton = getWebElement(cancelButtonLocator);
        clickOnWebElement(cancelButton);
        Assert.assertTrue(isAddUserDialogBoxClosed(Time.TIME_SHORTER), "AddUser DialogBox is NOT closed!");
        UsersPage usersPage = new UsersPage(driver);
        return usersPage.verifyUsersPage();
    }
}
