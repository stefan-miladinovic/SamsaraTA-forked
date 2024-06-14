package pages;

import data.PageUrlPaths;
import data.Time;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import utils.LoggerUtils;

public class LoginPage extends CommonLoggedOutPageClass {

    private final String LOGIN_PAGE_URL = getPageUrl(PageUrlPaths.LOGIN_PAGE);

    private final By usernameTextFieldLocator = By.id("username");
    private final By passwordTextFieldLocator = By.id("password");
    private final By loginButtonLocator = By.xpath("//input[@type='submit']");

//    @FindBy(id = "username")
//    private WebElement usernameTextField;
//
//    @FindBy(id = "password")
//    private WebElement passwordTextField;
//
//    @FindBy(xpath = "//input[@type='submit']")
//    private WebElement loginButton;

    // Constructor
    public LoginPage(WebDriver driver) {
        super(driver);
        //PageFactory.initElements(driver, this);
    }

    public LoginPage open() {
        return open(true);

    }

    public LoginPage open(boolean bVerify) {
        LoggerUtils.log.debug("open(" + LOGIN_PAGE_URL + ")");
        openUrl(LOGIN_PAGE_URL);
        if(bVerify) {
            verifyLoginPage();
        }
        return this;
    }

    public LoginPage verifyLoginPage() {
        LoggerUtils.log.debug("verifyLoginPage()");
        waitForUrlChange(LOGIN_PAGE_URL, Time.TIME_SHORTER);
        // wait for dom structure to be completed
        // wait/check the presence of specific/problematic web element
        return this;
    }


    // Username Text Field
    public boolean isUsernameTextFieldDisplayed() {
        LoggerUtils.log.debug("isUsernameTextFieldDisplayed()");
        return isWebElementDisplayed(usernameTextFieldLocator);
    }

    public boolean isUsernameTextFieldEnabled() {
        LoggerUtils.log.debug("isUsernameTextFieldEnabled()");
        Assert.assertTrue(isUsernameTextFieldDisplayed(), "Username Text Field is NOT displayed on Login Page!");
        WebElement usernameTextField = getWebElement(usernameTextFieldLocator);
        return isWebElementEnabled(usernameTextField);
    }

    public void typeUsername(String sUsername) {
        LoggerUtils.log.debug("typeUsername(" + sUsername + ")");
        Assert.assertTrue(isUsernameTextFieldEnabled(), "Username Text Field is NOT enabled on Login Page!");
        WebElement usernameTextField = getWebElement(usernameTextFieldLocator);
        clearAndTypeTextToWebElement(usernameTextField, sUsername);
    }

    public String getUsername() {
        LoggerUtils.log.debug("getUsername()");
        Assert.assertTrue(isUsernameTextFieldDisplayed(), "Username Text Field is NOT displayed on Login Page!");
        WebElement usernameTextField = getWebElement(usernameTextFieldLocator);
        return getValueFromWebElement(usernameTextField);
    }

    public String getUsernamePlaceholder() {
        LoggerUtils.log.debug("getUsernamePlaceholder()");
        Assert.assertTrue(isUsernameTextFieldDisplayed(), "Username Text Field is NOT displayed on Login Page!");
        WebElement usernameTextField = getWebElement(usernameTextFieldLocator);
        return getPlaceholderFromWebElement(usernameTextField);
    }

    // Password Text Field
    public boolean isPasswordTextFieldDisplayed() {
        LoggerUtils.log.debug("isPasswordTextFieldDisplayed()");
        return isWebElementDisplayed(passwordTextFieldLocator);
    }

    public boolean isPasswordTextFieldEnabled() {
        LoggerUtils.log.debug("isPasswordTextFieldEnabled()");
        Assert.assertTrue(isPasswordTextFieldDisplayed(), "Password Text Field is NOT displayed on Login Page!");
        WebElement passwordTextField = getWebElement(passwordTextFieldLocator);
        return isWebElementEnabled(passwordTextField);
    }

    public void typePassword(String sPassword) {
        LoggerUtils.log.debug("typePassword(" + sPassword + ")");
        Assert.assertTrue(isPasswordTextFieldEnabled(), "Password Text Field is NOT enabled on Login Page!");
        WebElement passwordTextField = getWebElement(passwordTextFieldLocator);
        clearAndTypeTextToWebElement(passwordTextField, sPassword);
    }

    public String getPassword() {
        LoggerUtils.log.debug("getPassword()");
        Assert.assertTrue(isPasswordTextFieldDisplayed(), "Password Text Field is NOT displayed on Login Page!");
        WebElement passwordTextField = getWebElement(passwordTextFieldLocator);
        return getValueFromWebElement(passwordTextField);
    }

    public String getPasswordPlaceholder() {
        LoggerUtils.log.debug("getPasswordPlaceholder()");
        Assert.assertTrue(isPasswordTextFieldDisplayed(), "Password Text Field is NOT displayed on Login Page!");
        WebElement passwordTextField = getWebElement(passwordTextFieldLocator);
        return getPlaceholderFromWebElement(passwordTextField);
    }

    // Login Button
    public boolean isLoginButtonDisplayed() {
        LoggerUtils.log.debug("isLoginButtonDisplayed()");
        return isWebElementDisplayed(loginButtonLocator);
    }

    public boolean isLoginButtonEnabled() {
        LoggerUtils.log.debug("isLoginButtonEnabled()");
        Assert.assertTrue(isLoginButtonDisplayed(), "Login Button is NOT displayed on Login Page!");
        WebElement loginButton = getWebElement(loginButtonLocator);
        return isWebElementEnabled(loginButton);
    }

    public String getLoginButtonTitle() {
        LoggerUtils.log.debug("getLoginButtonTitle()");
        Assert.assertTrue(isLoginButtonDisplayed(), "Login Button is NOT displayed on Login Page!");
        WebElement loginButton = getWebElement(loginButtonLocator);
        return getValueFromWebElement(loginButton);
    }

    private void clickLoginButtonNoVerification() {
        Assert.assertTrue(isLoginButtonEnabled(), "Login Button is NOT enabled on Login Page!");
        WebElement loginButton = getWebElement(loginButtonLocator);
        clickOnWebElement(loginButton);
    }

    public WelcomePage clickLoginButton() {
        LoggerUtils.log.debug("clickLoginButton()");
        clickLoginButtonNoVerification();
        WelcomePage welcomePage = new WelcomePage(driver);
        return welcomePage.verifyWelcomePage();
    }

    public LoginPage clickLoginButtonNoProgress() {
        LoggerUtils.log.debug("clickLoginButtonNoProgress()");
        clickLoginButtonNoVerification();
        LoginPage loginPage = new LoginPage(driver);
        return loginPage.verifyLoginPage();
    }

    @SuppressWarnings("unchecked")
    public <T> T clickLoginButton(boolean bProgress) {
        LoggerUtils.log.debug("clickLoginButton(" + bProgress + ")");
        clickLoginButtonNoVerification();
        if (bProgress) {
            WelcomePage welcomePage = new WelcomePage(driver);
            return (T) welcomePage.verifyWelcomePage();
        } else {
            LoginPage loginPage = new LoginPage(driver);
            return (T) loginPage.verifyLoginPage();
        }
    }
}
