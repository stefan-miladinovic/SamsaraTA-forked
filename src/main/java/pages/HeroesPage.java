package pages;

import data.PageUrlPaths;
import data.Time;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import utils.LoggerUtils;

public class HeroesPage extends CommonLoggedInPageClass {

    // Page Url Path
    private final String HEROES_PAGE_URL = getPageUrl(PageUrlPaths.HEROES_PAGE);

    // Locators
    private final By searchTextFieldLocator = By.id("search");
    private final By searchButtonLocator = By.xpath("//div[@id='custom-search-input']//i[contains(@class, 'glyphicon-search')]");
    private final By addNewHeroButtonLocator = By.xpath("//div[@class='row']//a[contains(@class,'btn-info') and contains(@onclick,'openAddHeroModal')]");
    private final By heroesTableLocator = By.id("heroes-table");

    public HeroesPage(WebDriver driver) {
        super(driver);
    }

    public HeroesPage open() {
        return open(true);
    }

    public HeroesPage open(boolean bVerify) {
        LoggerUtils.log.debug("open(" + HEROES_PAGE_URL + ")");
        openUrl(HEROES_PAGE_URL);
        if (bVerify) {
            verifyHeroesPage();
        }
        return this;
    }

    public HeroesPage verifyHeroesPage() {
        LoggerUtils.log.debug("verifyHeroesPage()");
        waitForUrlChange(HEROES_PAGE_URL, Time.TIME_SHORTER);
        return this;
    }

    // Search Text Field
    public boolean isSearchTextFieldDisplayed() {
        LoggerUtils.log.debug("isSearchTextFieldDisplayed()");
        return isWebElementDisplayed(searchTextFieldLocator);
    }

    public boolean isSearchTextFieldEnabled() {
        LoggerUtils.log.debug("isSearchTextFieldEnabled()");
        Assert.assertTrue(isSearchTextFieldDisplayed(), "Search Text Field is NOT displayed on Heroes Page!");
        WebElement searchTextField = getWebElement(searchTextFieldLocator);
        return isWebElementEnabled(searchTextField);
    }

    public HeroesPage typeSearchText(String sSearchText) {
        LoggerUtils.log.debug("typeSearchText(" + sSearchText + ")");
        Assert.assertTrue(isSearchTextFieldEnabled(), "Search Text Field is NOT enabled on Heroes Page!");
        WebElement searchTextField = getWebElement(searchTextFieldLocator);
        clearAndTypeTextToWebElement(searchTextField, sSearchText);
        return this;
    }

    public String getSearchText() {
        LoggerUtils.log.debug("getSearchText()");
        Assert.assertTrue(isSearchTextFieldDisplayed(), "Search Text Field is NOT displayed on Heroes Page!");
        WebElement searchTextField = getWebElement(searchTextFieldLocator);
        return getValueFromWebElement(searchTextField);
    }

    public String getSearchPlaceholder() {
        LoggerUtils.log.debug("getSearchPlaceholder()");
        Assert.assertTrue(isSearchTextFieldDisplayed(), "Search Text Field is NOT displayed on Heroes Page!");
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
        Assert.assertTrue(isSearchButtonDisplayed(), "Search Button is NOT displayed on Heroes Page!");
        WebElement searchButton = getWebElement(searchButtonLocator);
        return isWebElementEnabled(searchButton);
    }

    public void clickSearchButton() {
        LoggerUtils.log.debug("clickSearchButton()");
        Assert.assertTrue(isSearchButtonEnabled(), "Search Button is NOT enabled on Heroes Page!");
        WebElement searchButton = getWebElement(searchButtonLocator);
        clickOnWebElement(searchButton);
    }

    // Add New Hero Button
    public boolean isAddNewHeroButtonDisplayed() {
        LoggerUtils.log.debug("isAddNewHeroButtonDisplayed()");
        return isWebElementDisplayed(addNewHeroButtonLocator);
    }

    public boolean isAddNewHeroButtonEnabled() {
        LoggerUtils.log.debug("isAddNewHeroButtonEnabled()");
        Assert.assertTrue(isAddNewHeroButtonDisplayed(), "Add New Hero Button is NOT displayed on Heroes Page!");
        WebElement addNewHeroButton = getWebElement(addNewHeroButtonLocator);
        return isWebElementEnabled(addNewHeroButton);
    }

    public String getAddNewHeroButtonTitle() {
        LoggerUtils.log.debug("getAddNewHeroButtonTitle()");
        Assert.assertTrue(isAddNewHeroButtonDisplayed(), "Add New Hero Button is NOT displayed on Heroes Page!");
        WebElement addNewHeroButton = getWebElement(addNewHeroButtonLocator);
        return getTextFromWebElement(addNewHeroButton);
    }

    public AddHeroDialogBox clickAddNewHeroButton() {
        LoggerUtils.log.debug("clickAddNewHeroButton()");
        Assert.assertTrue(isAddNewHeroButtonEnabled(), "Add New Hero Button is NOT enabled on Heroes Page!");
        WebElement addNewHeroButton = getWebElement(addNewHeroButtonLocator);
        clickOnWebElement(addNewHeroButton);
        AddHeroDialogBox addHeroDialogBox = new AddHeroDialogBox(driver);
        return addHeroDialogBox.verifyAddHeroDialogBox();
    }
}
