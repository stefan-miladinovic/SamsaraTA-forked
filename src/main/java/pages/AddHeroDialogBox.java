package pages;

import data.Time;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import utils.LoggerUtils;

public class AddHeroDialogBox extends BasePageClass {

    private final String addHeroDialogBoxString = "//div[@id='addHeroModal']";
    private final By addHeroDialogBoxLocator = By.id("addHeroModal");
    private final By cancelButtonLocator = By.xpath(addHeroDialogBoxString + "//button[contains(@class, 'btn-default')]");

    @FindBy(id = "type")
    private WebElement heroClass;

    public AddHeroDialogBox(WebDriver driver) {
        super(driver);
    }

    public AddHeroDialogBox verifyAddHeroDialogBox() {
        LoggerUtils.log.debug("verifyAddHeroDialogBox()");
        Assert.assertTrue(isAddHeroDialogBoxOpened(Time.TIME_SHORTER), "AddHero DialogBox is NOT opened!");
        return this;
    }

    private boolean isAddHeroDialogBoxOpened(int timeout) {
        LoggerUtils.log.debug("isAddHeroDialogBoxOpened()");
        WebElement addHeroDialogBox = getWebElement(addHeroDialogBoxLocator, timeout);
        return isWebElementVisible(addHeroDialogBox, timeout);
    }

    private boolean isAddHeroDialogBoxClosed(int timeout) {
        LoggerUtils.log.debug("isAddHeroDialogBoxClosed()");
        WebElement addHeroDialogBox = getWebElement(addHeroDialogBoxLocator, timeout);
        return isWebElementInvisible(addHeroDialogBox, timeout);
    }

    public boolean isCancelButtonDisplayed() {
        LoggerUtils.log.debug("isCancelButtonDisplayed()");
        return isWebElementDisplayed(cancelButtonLocator);
    }

    public boolean isCancelButtonEnabled() {
        LoggerUtils.log.debug("isCancelButtonEnabled()");
        Assert.assertTrue(isCancelButtonDisplayed(), "Cancel Button is NOT displayed on AddHero DialogBox!");
        WebElement cancelButton = getWebElement(cancelButtonLocator);
        return isWebElementEnabled(cancelButton);
    }

    public String getCancelButtonTitle() {
        LoggerUtils.log.debug("getCancelButtonTitle()");
        Assert.assertTrue(isCancelButtonDisplayed(), "Cancel Button is NOT displayed on AddHero DialogBox!");
        WebElement cancelButton = getWebElement(cancelButtonLocator);
        return getTextFromWebElement(cancelButton);
    }

    public HeroesPage clickCancelButton() {
        Assert.assertTrue(isCancelButtonEnabled(), "Cancel Button is NOT enabled on AddHero DialogBox!");
        WebElement cancelButton = getWebElement(cancelButtonLocator);
        clickOnWebElement(cancelButton);
        Assert.assertTrue(isAddHeroDialogBoxClosed(Time.TIME_SHORTER), "AddHero DialogBox is NOT closed!");
        HeroesPage heroesPage = new HeroesPage(driver);
        return heroesPage.verifyHeroesPage();
    }

    public boolean isHeroClassDropDownListDisplayed() {
        LoggerUtils.log.debug("isHeroClassDropDownListDisplayed()");
        return isDropDownListDisplayed(heroClass);
    }

    public boolean isHeroClassDropDownListEnabled() {
        LoggerUtils.log.debug("isHeroClassDropDownListEnabled()");
        return isDropDownListEnabled(heroClass);
    }

    public String getSelectedHeroClass() {
        LoggerUtils.log.debug("getSelectedHeroClass()");
        return getSelectedDropDownListOption(heroClass);
    }

    public void selectHeroClass(String sHeroClass) {
        LoggerUtils.log.debug("selectHeroClass(" + sHeroClass + ")");
        selectDropDownListOptionByText(heroClass, sHeroClass);
    }

}
