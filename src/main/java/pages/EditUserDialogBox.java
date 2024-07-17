package pages;

import data.Time;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import utils.LoggerUtils;

public class EditUserDialogBox extends BasePageClass {

    private final String editUserDialogBoxString = "//div[@id='editUserModal']";
    private final By editUserDialogBoxLocator = By.id("editUserModal");
    private final By cancelButtonLocator = By.xpath(editUserDialogBoxString + "//button[contains(@class, 'btn-default')]");

    public EditUserDialogBox(WebDriver driver) {
        super(driver);
    }

    public EditUserDialogBox verifyEditUserDialogBox() {
        LoggerUtils.log.debug("verifyEditUserDialogBox()");
        Assert.assertTrue(isEditUserDialogBoxOpened(Time.TIME_SHORTER), "EditUser DialogBox is NOT opened!");
        return this;
    }

    private boolean isEditUserDialogBoxOpened(int timeout) {
        LoggerUtils.log.debug("isEditUserDialogBoxOpened()");
        WebElement editUserDialogBox = getWebElement(editUserDialogBoxLocator, timeout);
        return isWebElementVisible(editUserDialogBox, timeout);
    }

    private boolean isEditUserDialogBoxClosed(int timeout) {
        LoggerUtils.log.debug("isEditUserDialogBoxClosed()");
        WebElement editUserDialogBox = getWebElement(editUserDialogBoxLocator, timeout);
        return isWebElementInvisible(editUserDialogBox, timeout);
    }

    public boolean isCancelButtonDisplayed() {
        LoggerUtils.log.debug("isCancelButtonDisplayed()");
        return isWebElementDisplayed(cancelButtonLocator);
    }

    public boolean isCancelButtonEnabled() {
        LoggerUtils.log.debug("isCancelButtonEnabled()");
        Assert.assertTrue(isCancelButtonDisplayed(), "Cancel Button is NOT displayed on EditUser DialogBox!");
        WebElement cancelButton = getWebElement(cancelButtonLocator);
        return isWebElementEnabled(cancelButton);
    }

    public String getCancelButtonTitle() {
        LoggerUtils.log.debug("getCancelButtonTitle()");
        Assert.assertTrue(isCancelButtonDisplayed(), "Cancel Button is NOT displayed on EditUser DialogBox!");
        WebElement cancelButton = getWebElement(cancelButtonLocator);
        return getTextFromWebElement(cancelButton);
    }

    public UsersPage clickCancelButton() {
        Assert.assertTrue(isCancelButtonEnabled(), "Cancel Button is NOT enabled on EditUser DialogBox!");
        WebElement cancelButton = getWebElement(cancelButtonLocator);
        clickOnWebElement(cancelButton);
        Assert.assertTrue(isEditUserDialogBoxClosed(Time.TIME_SHORTER), "EditUser DialogBox is NOT closed!");
        UsersPage usersPage = new UsersPage(driver);
        return usersPage.verifyUsersPage();
    }
    
}
