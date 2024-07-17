package pages;

import data.Time;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import utils.LoggerUtils;

public class DeleteUserDialogBox extends BasePageClass {

    private final String deleteUserDialogBoxString = "//div[@id='deleteUserModal']";
    private final By deleteUserDialogBoxLocator = By.id("deleteUserModal");
    private final By cancelButtonLocator = By.xpath(deleteUserDialogBoxString + "//button[contains(@class, 'btn-default')]");
    private final By deleteUserMessageLocator = By.xpath(deleteUserDialogBoxString + "//div[@class='modal-body']/p");
    
    public DeleteUserDialogBox(WebDriver driver) {
        super(driver);
    }

    public DeleteUserDialogBox verifyDeleteUserDialogBox() {
        LoggerUtils.log.debug("verifyDeleteUserDialogBox()");
        Assert.assertTrue(isDeleteUserDialogBoxOpened(Time.TIME_SHORTER), "DeleteUser DialogBox is NOT opened!");
        return this;
    }

    private boolean isDeleteUserDialogBoxOpened(int timeout) {
        LoggerUtils.log.debug("isDeleteUserDialogBoxOpened()");
        WebElement deleteUserDialogBox = getWebElement(deleteUserDialogBoxLocator, timeout);
        return isWebElementVisible(deleteUserDialogBox, timeout);
    }

    private boolean isDeleteUserDialogBoxClosed(int timeout) {
        LoggerUtils.log.debug("isDeleteUserDialogBoxClosed()");
        WebElement deleteUserDialogBox = getWebElement(deleteUserDialogBoxLocator, timeout);
        return isWebElementInvisible(deleteUserDialogBox, timeout);
    }

    public boolean isCancelButtonDisplayed() {
        LoggerUtils.log.debug("isCancelButtonDisplayed()");
        return isWebElementDisplayed(cancelButtonLocator);
    }

    public boolean isCancelButtonEnabled() {
        LoggerUtils.log.debug("isCancelButtonEnabled()");
        Assert.assertTrue(isCancelButtonDisplayed(), "Cancel Button is NOT displayed on DeleteUser DialogBox!");
        WebElement cancelButton = getWebElement(cancelButtonLocator);
        return isWebElementEnabled(cancelButton);
    }

    public String getCancelButtonTitle() {
        LoggerUtils.log.debug("getCancelButtonTitle()");
        Assert.assertTrue(isCancelButtonDisplayed(), "Cancel Button is NOT displayed on DeleteUser DialogBox!");
        WebElement cancelButton = getWebElement(cancelButtonLocator);
        return getTextFromWebElement(cancelButton);
    }

    public UsersPage clickCancelButton() {
        Assert.assertTrue(isCancelButtonEnabled(), "Cancel Button is NOT enabled on DeleteUser DialogBox!");
        WebElement cancelButton = getWebElement(cancelButtonLocator);
        clickOnWebElement(cancelButton);
        Assert.assertTrue(isDeleteUserDialogBoxClosed(Time.TIME_SHORTER), "DeleteUser DialogBox is NOT closed!");
        UsersPage usersPage = new UsersPage(driver);
        return usersPage.verifyUsersPage();
    }

    public boolean isDeleteUserMessageDisplayed() {
        LoggerUtils.log.debug("isDeleteUserMessageDisplayed()");
        return isWebElementDisplayed(deleteUserMessageLocator);
    }

    public String getDeleteUserMessage() {
        LoggerUtils.log.debug("getDeleteUserMessage()");
        Assert.assertTrue(isDeleteUserMessageDisplayed(), "Delete User Message is NOT displayed on DeleteUser DialogBox!");
        WebElement errorMessage = getWebElement(deleteUserMessageLocator);
        return getTextFromWebElement(errorMessage);
    }
    
}
