package pages;

import data.PageUrlPaths;
import data.Time;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import utils.FileUtils;
import utils.LoggerUtils;

public class AdminPage extends CommonLoggedInPageClass {

    private final String ADMIN_PAGE_URL = getPageUrl(PageUrlPaths.ADMIN_PAGE);

    private final By chuckNorrisCheckBoxLocator = By.id("chuckNorris");


    @FindBy(id="chuckNorris")
    private WebElement chuckNorrisCheckBox;

    @FindBy(id="userDetails")
    private WebElement downloadUserDetailsButton;

    public AdminPage(WebDriver driver) {
        super(driver);
    }

    public AdminPage open() {
        return open(true);
    }

    public AdminPage open(boolean bVerify) {
        LoggerUtils.log.debug("open(" + ADMIN_PAGE_URL + ")");
        openUrl(ADMIN_PAGE_URL);
        if (bVerify) {
            verifyAdminPage();
        }
        return this;
    }

    public AdminPage verifyAdminPage() {
        LoggerUtils.log.debug("verifyAdminPage()");
        waitForUrlChange(ADMIN_PAGE_URL, Time.TIME_SHORTER);
        waitUntilPageIsReady(Time.TIME_SHORTER);
        return this;
    }

    public boolean isChuckNorrisCheckBoxDisplayed() {
        LoggerUtils.log.debug("isChuckNorrisCheckBoxDisplayed()");
        return isCheckBoxDisplayed(chuckNorrisCheckBox);
    }

    public boolean isChuckNorrisCheckBoxEnabled() {
        LoggerUtils.log.debug("isChuckNorrisCheckBoxEnabled()");
        return isCheckBoxEnabled(chuckNorrisCheckBox);
    }

    public boolean isChuckNorrisCheckBoxChecked() {
        LoggerUtils.log.debug("isChuckNorrisCheckBoxChecked()");
        return isCheckBoxChecked(chuckNorrisCheckBox);
    }

    public AdminPage checkChuckNorrisCheckBox() {
        LoggerUtils.log.debug("checkChuckNorrisCheckBox()");
        checkCheckBox(chuckNorrisCheckBox);
        AdminPage adminPage = new AdminPage(driver);
        return adminPage.verifyAdminPage();
    }

    public AdminPage uncheckChuckNorrisCheckBox() {
        LoggerUtils.log.debug("uncheckChuckNorrisCheckBox()");
        uncheckCheckBox(chuckNorrisCheckBox);
        AdminPage adminPage = new AdminPage(driver);
        return adminPage.verifyAdminPage();
    }

    public boolean isDownloadUserDetailsButtonDisplayed() {
        LoggerUtils.log.debug("isDownloadUserDetailsButtonDisplayed()");
        return isWebElementDisplayed(downloadUserDetailsButton);
    }

    public boolean isDownloadUserDetailsButtonEnabled() {
        LoggerUtils.log.debug("isDownloadUserDetailsButtonEnabled()");
        Assert.assertTrue(isDownloadUserDetailsButtonDisplayed(), "Download User Details Button is NOT displayed on Admin Page!");
        return isWebElementEnabled(downloadUserDetailsButton);
    }

    public AdminPage clickDownloadUserDetailsButton() {
        LoggerUtils.log.debug("isDownloadUserDetailsButtonEnabled()");
        Assert.assertTrue(isDownloadUserDetailsButtonEnabled(), "Download User Details Button is NOT enabled on Admin Page!");
        clickOnWebElement(downloadUserDetailsButton);
        return this;
    }

    public String downloadUserDetailsFile() {
        LoggerUtils.log.debug("downloadUserDetailsFile()");
        Assert.assertTrue(isDownloadUserDetailsButtonEnabled(), "Download User Details Button is NOT enabled on Admin Page!");
        String href = getAttributeFromWebElement(downloadUserDetailsButton, "href");
        return FileUtils.downloadFile(driver, href);
    }
}
