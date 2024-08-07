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
    private final By searchTextFieldLocator = By.id("search");
    private final By searchButtonLocator = By.xpath("//div[@id='custom-search-input']//i[contains(@class, 'glyphicon-search')]");
    private final By addNewUserButtonLocator = By.xpath("//div[@class='row']//a[contains(@class,'btn-info') and contains(@onclick,'openAddUserModal')]");

    private final String usersTableLocatorString = "//table[@id='users-table']";
    private final By usersTableLocator = By.id("users-table");
    private final By usernameTableColumnLocator = By.xpath("//table[@id='users-table']//td[1]");

    @FindBy(id="users-table")
    WebElement usersTable;

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
        // wait for dom structure to be completed
        // wait/check the presence of specific/problematic web element
        return this;
    }

    // Search Text Field
    public boolean isSearchTextFieldDisplayed() {
        LoggerUtils.log.debug("isSearchTextFieldDisplayed()");
        return isWebElementDisplayed(searchTextFieldLocator);
    }

    public boolean isSearchTextFieldEnabled() {
        LoggerUtils.log.debug("isSearchTextFieldEnabled()");
        Assert.assertTrue(isSearchTextFieldDisplayed(), "Search Text Field is NOT displayed on Users Page!");
        WebElement searchTextField = getWebElement(searchTextFieldLocator);
        return isWebElementEnabled(searchTextField);
    }

    public UsersPage typeSearchText(String sSearchText) {
        LoggerUtils.log.debug("typeSearchText(" + sSearchText + ")");
        Assert.assertTrue(isSearchTextFieldEnabled(), "Search Text Field is NOT enabled on Users Page!");
        WebElement searchTextField = getWebElement(searchTextFieldLocator);
        clearAndTypeTextToWebElement(searchTextField, sSearchText);
        return this;
    }

    public String getSearchText() {
        LoggerUtils.log.debug("getSearchText()");
        Assert.assertTrue(isSearchTextFieldDisplayed(), "Search Text Field is NOT displayed on Users Page!");
        WebElement searchTextField = getWebElement(searchTextFieldLocator);
        return getValueFromWebElement(searchTextField);
    }

    public String getSearchPlaceholder() {
        LoggerUtils.log.debug("getSearchPlaceholder()");
        Assert.assertTrue(isSearchTextFieldDisplayed(), "Search Text Field is NOT displayed on Users Page!");
        WebElement searchTextField = getWebElement(searchTextFieldLocator);
        return getPlaceholderFromWebElement(searchTextField);
    }

    // Search Button
    public boolean isSearchButtonDisplayed() {
        LoggerUtils.log.debug("isSearchButtonDisplayed()");
        return isWebElementDisplayed(searchButtonLocator);
    }

    public boolean isSearchButtonEnabled() {
        LoggerUtils.log.debug("isSearchButtonEnabled()");
        Assert.assertTrue(isSearchButtonDisplayed(), "Search Button is NOT displayed on Users Page!");
        WebElement searchButton = getWebElement(searchButtonLocator);
        return isWebElementEnabled(searchButton);
    }

    public void clickSearchButton() {
        LoggerUtils.log.debug("clickSearchButton()");
        Assert.assertTrue(isSearchButtonEnabled(), "Search Button is NOT enabled on Users Page!");
        WebElement searchButton = getWebElement(searchButtonLocator);
        clickOnWebElement(searchButton);
    }

    public boolean isAddNewUserButtonDisplayed() {
        LoggerUtils.log.debug("isAddNewUserButtonDisplayed()");
        return isWebElementDisplayed(addNewUserButtonLocator);
    }

    public boolean isAddNewUserButtonEnabled() {
        LoggerUtils.log.debug("isAddNewUserButtonEnabled()");
        Assert.assertTrue(isAddNewUserButtonDisplayed(), "Add New User Button is NOT displayed on Users Page!");
        WebElement addNewUserButton = getWebElement(addNewUserButtonLocator);
        return isWebElementEnabled(addNewUserButton);
    }

    public String getAddNewUserButtonTitle() {
        LoggerUtils.log.debug("getAddNewUserButtonTitle()");
        Assert.assertTrue(isAddNewUserButtonDisplayed(), "Add New User Button is NOT displayed on Users Page!");
        WebElement addNewUserButton = getWebElement(addNewUserButtonLocator);
        return getTextFromWebElement(addNewUserButton);
    }

    public AddUserDialogBox clickAddNewUserButton() {
        LoggerUtils.log.debug("clickAddNewUserButton()");
        Assert.assertTrue(isAddNewUserButtonEnabled(), "Add New User Button is NOT enabled on Users Page!");
        WebElement addNewUserButton = getWebElement(addNewUserButtonLocator);
        clickOnWebElement(addNewUserButton);
        AddUserDialogBox addUserDialogBox = new AddUserDialogBox(driver);
        return addUserDialogBox.verifyAddUserDialogBox();
    }

    private String createXpathForUsernameInUsersTable(String sUsername) {
        return usersTableLocatorString + "//td[1][text()='" + sUsername + "']";
        //return ".//td[1][text()='" + sUsername + "']";
    }

    public boolean isUserPresentInUsersTable(String sUsername) {
        LoggerUtils.log.debug("isUserPresentInUsersTable(" + sUsername + ")");
        String xPath = createXpathForUsernameInUsersTable(sUsername);
        return isWebElementDisplayed(By.xpath(xPath));
    }

    private String createXpathForDisplayNameInUsersTable(String sUsername) {
        return createXpathForUsernameInUsersTable(sUsername) + "/following-sibling::td[1]";
    }

    public String getDisplayNameInUsersTable(String sUsername) {
        LoggerUtils.log.debug("getDisplayNameInUsersTable(" + sUsername + ")");
        Assert.assertTrue(isUserPresentInUsersTable(sUsername), "User '" + sUsername + "' is NOT present in Users Table!");
        String xPath = createXpathForDisplayNameInUsersTable(sUsername);
        WebElement displayName = getWebElement(By.xpath(xPath));
        //WebElement displayName = getNestedWebElement(usersTable, By.xpath(xPath));
        return getTextFromWebElement(displayName);
    }

    private String createXpathForHeroCountInUsersTable(String sUsername) {
        return createXpathForUsernameInUsersTable(sUsername) + "/following-sibling::td[2]";
    }

    private WebElement getHeroCountLinkWebElementInUsersTable(String sUsername) {
        Assert.assertTrue(isUserPresentInUsersTable(sUsername), "User '" + sUsername + "' is NOT present in Users Table!");
        String xPath = createXpathForHeroCountInUsersTable(sUsername);
        return getWebElement(By.xpath(xPath));
    }

    public String getHeroCountInUsersTable(String sUsername) {
        LoggerUtils.log.debug("getHeroCountInUsersTable(" + sUsername + ")");
        WebElement heroCountLink = getHeroCountLinkWebElementInUsersTable(sUsername);
        return getTextFromWebElement(heroCountLink);
    }

    public UserHeroesDialogBox clickHeroCountLinkInUsersTable(String sUsername) {
        LoggerUtils.log.debug("clickHeroCountLinkInUsersTable(" + sUsername + ")");
        WebElement heroCountLink = getHeroCountLinkWebElementInUsersTable(sUsername);
        clickOnWebElement(heroCountLink);
        UserHeroesDialogBox userHeroesDialogBox = new UserHeroesDialogBox(driver);
        return userHeroesDialogBox.verifyUserHeroesDialogBox();
    }

    private String createXpathForUserIconsInUsersTable(String sUsername) {
        return createXpathForUsernameInUsersTable(sUsername) + "/following-sibling::td[3]";
    }

    private String createXpathForUserDetailsIconInUsersTable(String sUsername) {
        return createXpathForUserIconsInUsersTable(sUsername) + "/a[contains(@class, 'btn-info')]";
    }

    public boolean isUserDetailsIconPresentInUsersTable(String sUsername) {
        LoggerUtils.log.debug("isUserDetailsIconPresentInUsersTable(" + sUsername + ")");
        Assert.assertTrue(isUserPresentInUsersTable(sUsername), "User '" + sUsername + "' is NOT present in Users Table!");
        String xPath = createXpathForUserDetailsIconInUsersTable(sUsername);
        return isWebElementDisplayed(By.xpath(xPath));
    }

    private WebElement getUserDetailsIconWebElementInUsersTable(String sUsername) {
        Assert.assertTrue(isUserPresentInUsersTable(sUsername), "User '" + sUsername + "' is NOT present in Users Table!");
        String xPath = createXpathForUserDetailsIconInUsersTable(sUsername);
        return getWebElement(By.xpath(xPath));
    }

    public boolean isUserDetailsIconEnabledInUsersTable(String sUsername) {
        LoggerUtils.log.debug("isUserDetailsIconEnabledInUsersTable(" + sUsername + ")");
        WebElement userDetailsIcon = getUserDetailsIconWebElementInUsersTable(sUsername);
        return isWebElementEnabled(userDetailsIcon);
    }

    public UserDetailsDialogBox clickUserDetailsIconInUsersTable(String sUsername) {
        LoggerUtils.log.debug("clickUserDetailsIconInUsersTable(" + sUsername + ")");
        WebElement userDetailsIcon = getUserDetailsIconWebElementInUsersTable(sUsername);
        clickOnWebElement(userDetailsIcon);
        UserDetailsDialogBox userDetailsDialogBox = new UserDetailsDialogBox(driver);
        return userDetailsDialogBox.verifyUserDetailsDialogBox();
    }

    private String createXpathForEditUserIconInUsersTable(String sUsername) {
        return createXpathForUserIconsInUsersTable(sUsername) + "/a[contains(@class, 'btn-success')]";
    }

    public boolean isEditUserIconPresentInUsersTable(String sUsername) {
        LoggerUtils.log.debug("isEditUserIconPresentInUsersTable(" + sUsername + ")");
        Assert.assertTrue(isUserPresentInUsersTable(sUsername), "User '" + sUsername + "' is NOT present in Users Table!");
        String xPath = createXpathForEditUserIconInUsersTable(sUsername);
        return isWebElementDisplayed(By.xpath(xPath));
    }

    private WebElement getEditUserIconWebElementInUsersTable(String sUsername) {
        Assert.assertTrue(isUserPresentInUsersTable(sUsername), "User '" + sUsername + "' is NOT present in Users Table!");
        String xPath = createXpathForEditUserIconInUsersTable(sUsername);
        return getWebElement(By.xpath(xPath));
    }

    public boolean isEditUserIconEnabledInUsersTable(String sUsername) {
        LoggerUtils.log.debug("isEditUserIconEnabledInUsersTable(" + sUsername + ")");
        WebElement EditUserIcon = getEditUserIconWebElementInUsersTable(sUsername);
        return isWebElementEnabled(EditUserIcon);
    }

    public EditUserDialogBox clickEditUserIconInUsersTable(String sUsername) {
        LoggerUtils.log.debug("clickEditUserIconInUsersTable(" + sUsername + ")");
        WebElement EditUserIcon = getEditUserIconWebElementInUsersTable(sUsername);
        clickOnWebElement(EditUserIcon);
        EditUserDialogBox EditUserDialogBox = new EditUserDialogBox(driver);
        return EditUserDialogBox.verifyEditUserDialogBox();
    }

    private String createXpathForDeleteUserIconInUsersTable(String sUsername) {
        return createXpathForUserIconsInUsersTable(sUsername) + "/a[contains(@class, 'btn-danger')]";
    }

    public boolean isDeleteUserIconPresentInUsersTable(String sUsername) {
        LoggerUtils.log.debug("isDeleteUserIconPresentInUsersTable(" + sUsername + ")");
        Assert.assertTrue(isUserPresentInUsersTable(sUsername), "User '" + sUsername + "' is NOT present in Users Table!");
        String xPath = createXpathForDeleteUserIconInUsersTable(sUsername);
        return isWebElementDisplayed(By.xpath(xPath));
    }

    private WebElement getDeleteUserIconWebElementInUsersTable(String sUsername) {
        Assert.assertTrue(isUserPresentInUsersTable(sUsername), "User '" + sUsername + "' is NOT present in Users Table!");
        String xPath = createXpathForDeleteUserIconInUsersTable(sUsername);
        return getWebElement(By.xpath(xPath));
    }

    public boolean isDeleteUserIconEnabledInUsersTable(String sUsername) {
        LoggerUtils.log.debug("isDeleteUserIconEnabledInUsersTable(" + sUsername + ")");
        WebElement DeleteUserIcon = getDeleteUserIconWebElementInUsersTable(sUsername);
        return isWebElementEnabled(DeleteUserIcon);
    }

    public DeleteUserDialogBox clickDeleteUserIconInUsersTable(String sUsername) {
        LoggerUtils.log.debug("clickDeleteUserIconInUsersTable(" + sUsername + ")");
        WebElement DeleteUserIcon = getDeleteUserIconWebElementInUsersTable(sUsername);
        clickOnWebElement(DeleteUserIcon);
        DeleteUserDialogBox DeleteUserDialogBox = new DeleteUserDialogBox(driver);
        return DeleteUserDialogBox.verifyDeleteUserDialogBox();
    }

    public UsersPage search(String sSearchText) {
        LoggerUtils.log.debug("search(" + sSearchText + ")");
        typeSearchText(sSearchText);
        clickSearchButton();
        UsersPage usersPage = new UsersPage(driver);
        return usersPage.verifyUsersPage();
    }

//    private int getTableRow(String sUsername) {
//        List<WebElement> tableRows = getWebElements(usernameTableColumnLocator);
//        for (int i = 0; i < tableRows.size(); i++) {
//            if (getTextFromWebElement(tableRows.get(i)).equals(sUsername)) {
//                return i+1;
//            }
//        }
//        return 0;
//    }
//
//    public String getDisplayName(String sUsername) {
//        int index = getTableRow(sUsername);
//        Assert.assertNotEquals(index, 0, "Username '" + sUsername + "' doesn't exist in Users table!");
//        String xPath = "//table[@id='users-table']//tr[" + index + "]/td[2]";
//        LoggerUtils.log.debug("Display Name XPATH: " + xPath);
//        WebElement displayName = getWebElement(By.xpath(xPath));
//        return getTextFromWebElement(displayName);
//    }
}
