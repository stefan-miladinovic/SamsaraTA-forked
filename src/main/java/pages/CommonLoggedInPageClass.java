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
    private final By homeTabLocator = By.xpath(headerLocatorString + "//a[@href='" + PageUrlPaths.HOME_PAGE + "']");
    private final By usersTabLocator = By.xpath(headerLocatorString + "//a[@href='" + PageUrlPaths.USERS_PAGE + "']");
    private final By heroesTabLocator = By.xpath(headerLocatorString + "//a[@href='" + PageUrlPaths.HEROES_PAGE + "']");
    private final By apiTabLocator = By.xpath(headerLocatorString + "//a[@href='" + PageUrlPaths.API_PAGE + "']");
    private final By galleryTabLocator = By.xpath(headerLocatorString + "//a[@href='" + PageUrlPaths.GALLERY_PAGE + "']");
    private final By practiceTabLocator = By.xpath(headerLocatorString + "//a[@href='" + PageUrlPaths.PRACTICE_PAGE + "']");
    private final By adminTabLocator = By.xpath(headerLocatorString + "//a[@href='" + PageUrlPaths.ADMIN_PAGE + "']");
    private final By profileLinkLocator = By.xpath(headerLocatorString + "//a[@href='" + PageUrlPaths.PROFILE_PAGE + "']");

    public boolean isHomeTabDisplayed() {
        LoggerUtils.log.debug("isHomeTabDisplayed()");
        return isWebElementDisplayed(homeTabLocator);
    }

    public boolean isHomeTabEnabled() {
        LoggerUtils.log.debug("isHomeTabEnabled()");
        Assert.assertTrue(isHomeTabDisplayed(), "Home Tab is NOT displayed on Navigation Bar!");
        WebElement homeTab = getWebElement(homeTabLocator);
        return isWebElementEnabled(homeTab);
    }

    public String getHomeTabTitle() {
        LoggerUtils.log.debug("getHomeTabTitle()");
        Assert.assertTrue(isHomeTabDisplayed(), "Home Tab is NOT displayed on Navigation Bar!");
        WebElement homeTab = getWebElement(homeTabLocator);
        return getTextFromWebElement(homeTab);
    }

    public HomePage clickHomeTab() {
        LoggerUtils.log.debug("clickHomeTab()");
        Assert.assertTrue(isHomeTabEnabled(), "Home Tab is NOT enabled on Navigation Bar!");
        WebElement homeTab = getWebElement(homeTabLocator);
        clickOnWebElement(homeTab);
        HomePage homePage = new HomePage(driver);
        return homePage.verifyHomePage();
    }

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

    public boolean isHeroesTabDisplayed() {
        LoggerUtils.log.debug("isHeroesTabDisplayed()");
        return isWebElementDisplayed(heroesTabLocator);
    }

    public boolean isHeroesTabEnabled() {
        LoggerUtils.log.debug("isHeroesTabEnabled()");
        Assert.assertTrue(isHeroesTabDisplayed(), "Heroes Tab is NOT displayed on Navigation Bar!");
        WebElement heroesTab = getWebElement(heroesTabLocator);
        return isWebElementEnabled(heroesTab);
    }

    public String getHeroesTabTitle() {
        LoggerUtils.log.debug("getHeroesTabTitle()");
        Assert.assertTrue(isHeroesTabDisplayed(), "Heroes Tab is NOT displayed on Navigation Bar!");
        WebElement heroesTab = getWebElement(heroesTabLocator);
        return getTextFromWebElement(heroesTab);
    }

    public HeroesPage clickHeroesTab() {
        LoggerUtils.log.debug("clickHeroesTab()");
        Assert.assertTrue(isHeroesTabEnabled(), "Heroes Tab is NOT enabled on Navigation Bar!");
        WebElement heroesTab = getWebElement(heroesTabLocator);
        clickOnWebElement(heroesTab);
        HeroesPage heroesPage = new HeroesPage(driver);
        return heroesPage.verifyHeroesPage();
    }

    public boolean isAdminTabDisplayed() {
        LoggerUtils.log.debug("isAdminTabDisplayed()");
        return isWebElementDisplayed(adminTabLocator);
    }

    public boolean isAdminTabEnabled() {
        LoggerUtils.log.debug("isAdminTabEnabled()");
        Assert.assertTrue(isAdminTabDisplayed(), "Admin Tab is NOT displayed on Navigation Bar!");
        WebElement adminTab = getWebElement(adminTabLocator);
        return isWebElementEnabled(adminTab);
    }

    public String getAdminTabTitle() {
        LoggerUtils.log.debug("getAdminTabTitle()");
        Assert.assertTrue(isAdminTabDisplayed(), "Admin Tab is NOT displayed on Navigation Bar!");
        WebElement adminTab = getWebElement(adminTabLocator);
        return getTextFromWebElement(adminTab);
    }

    public AdminPage clickAdminTab() {
        LoggerUtils.log.debug("clickAdminTab()");
        Assert.assertTrue(isAdminTabEnabled(), "Admin Tab is NOT enabled on Navigation Bar!");
        WebElement adminTab = getWebElement(adminTabLocator);
        clickOnWebElement(adminTab);
        AdminPage adminPage = new AdminPage(driver);
        return adminPage.verifyAdminPage();
    }
}
