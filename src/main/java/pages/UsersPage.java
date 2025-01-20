package pages;

import data.PageUrlPaths;
import data.Time;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import utils.LoggerUtils;

public class UsersPage extends CommonLoggedInPageClass {

    // Page Url Path
    private final String USERS_PAGE_URL = getPageUrl(PageUrlPaths.USERS_PAGE);

    // Locators
//    private final By searchTextFieldLocator = By.id("search");
//    private final By searchButtonLocator = By.xpath("//div[@id='custom-search-input']//i[contains(@class, 'glyphicon-search')]");
//    private final By addNewUserButtonLocator = By.xpath("//div[@class='row']//a[contains(@class,'btn-info') and contains(@onclick,'openAddUserModal')]");

    @FindBy(id = "search")
    private WebElement searchTextField;

    @FindBy(xpath = "//div[@id='custom-search-input']//i[contains(@class, 'glyphicon-search')]")
    private WebElement searchButton;

    @FindBy(xpath = "//div[@class='row']//a[contains(@class,'btn-info') and contains(@onclick,'openAddUserModal')]")
    private WebElement addNewUserButton;


    public UsersPage(WebDriver driver) {
        super(driver);
    }

    public UsersPage open() {
        return open(true);
    }

    public UsersPage open(boolean bVerify) {
        LoggerUtils.log.debug("open(" + USERS_PAGE_URL + ")");
        openUrl(USERS_PAGE_URL);
        if (bVerify) {
            verifyUsersPage();
        }
        return this;
    }

    public UsersPage verifyUsersPage() {
        LoggerUtils.log.debug("verifyUsersPage()");
        waitForUrlChange(USERS_PAGE_URL, Time.TIME_SHORTER);
        waitUntilPageIsReady(Time.TIME_SHORTER);
        return this;
    }

    // Search Text Field
    public boolean isSearchTextFieldDisplayed() {
        LoggerUtils.log.debug("isSearchTextFieldDisplayed()");
        return isWebElementDisplayed(searchTextField);
    }

    public boolean isSearchTextFieldEnabled() {
        LoggerUtils.log.debug("isSearchTextFieldEnabled()");
        Assert.assertTrue(isSearchTextFieldDisplayed(), "Search Text Field is NOT displayed on Users Page!");
        return isWebElementEnabled(searchTextField);
    }

    public UsersPage typeSearchText(String sSearchText) {
        LoggerUtils.log.debug("typeSearchText(" + sSearchText + ")");
        Assert.assertTrue(isSearchTextFieldEnabled(), "Search Text Field is NOT enabled on Users Page!");
        clearAndTypeTextToWebElement(searchTextField, sSearchText);
        return this;
    }

    public String getSearchText() {
        LoggerUtils.log.debug("getSearchText()");
        Assert.assertTrue(isSearchTextFieldDisplayed(), "Search Text Field is NOT displayed on Users Page!");
        return getValueFromWebElement(searchTextField);
    }

    public String getSearchPlaceholder() {
        LoggerUtils.log.debug("getSearchPlaceholder()");
        Assert.assertTrue(isSearchTextFieldDisplayed(), "Search Text Field is NOT displayed on Users Page!");
        return getPlaceholderFromWebElement(searchTextField);
    }

    // Search Button
    public boolean isSearchButtonDisplayed() {
        LoggerUtils.log.debug("isSearchButtonDisplayed()");
        return isWebElementDisplayed(searchButton);
    }

    public boolean isSearchButtonEnabled() {
        LoggerUtils.log.debug("isSearchButtonEnabled()");
        Assert.assertTrue(isSearchButtonDisplayed(), "Search Button is NOT displayed on Users Page!");
        return isWebElementEnabled(searchButton);
    }

    public void clickSearchButton() {
        LoggerUtils.log.debug("clickSearchButton()");
        Assert.assertTrue(isSearchButtonEnabled(), "Search Button is NOT enabled on Users Page!");
        clickOnWebElement(searchButton);
    }

    public boolean isAddNewUserButtonDisplayed() {
        LoggerUtils.log.debug("isAddNewUserButtonDisplayed()");
        return isWebElementDisplayed(addNewUserButton);
    }

    public boolean isAddNewUserButtonEnabled() {
        LoggerUtils.log.debug("isAddNewUserButtonEnabled()");
        Assert.assertTrue(isAddNewUserButtonDisplayed(), "Add New User Button is NOT displayed on Users Page!");
        return isWebElementEnabled(addNewUserButton);
    }

    public String getAddNewUserButtonTitle() {
        LoggerUtils.log.debug("getAddNewUserButtonTitle()");
        Assert.assertTrue(isAddNewUserButtonDisplayed(), "Add New User Button is NOT displayed on Users Page!");
        return getTextFromWebElement(addNewUserButton);
    }

    public AddUserDialogBox clickAddNewUserButton() {
        LoggerUtils.log.debug("clickAddNewUserButton()");
        Assert.assertTrue(isAddNewUserButtonEnabled(), "Add New User Button is NOT enabled on Users Page!");
        clickOnWebElement(addNewUserButton);
        AddUserDialogBox addUserDialogBox = new AddUserDialogBox(driver);
        return addUserDialogBox.verifyAddUserDialogBox();
    }

}
