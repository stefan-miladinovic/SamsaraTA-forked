package pages;

import data.PageUrlPaths;
import data.Time;
import objects.User;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import utils.LoggerUtils;

public class LoginPage extends CommonLoggedOutPageClass {

    private final String LOGIN_PAGE_URL = getPageUrl(PageUrlPaths.LOGIN_PAGE);

    private final String loginBoxLocatorString = "div#loginbox";
    private final By usernameTextFieldLocator = By.id("username");
    private final By passwordTextFieldLocator = By.id("password");
    //private final By loginButtonLocator = By.cssSelector(loginBoxLocatorString + " input.btn-primary");
    private final By errorMessageLocator = By.cssSelector("#loginbox div.alert-danger");
    //private final By errorMessageLocator = By.xpath("//div[text()='Invalid username and/or password!']");
    private final By successMessageLocator = By.cssSelector("#loginbox div.alert-success");

//    @FindBy(id = "username")
//    private WebElement usernameTextField;
//
//    @FindBy(id = "password")
//    private WebElement passwordTextField;
//
    @FindBy(xpath = "//input[@type='submit']")
    private WebElement loginButton;

    // Constructor
    public LoginPage(WebDriver driver) {
        super(driver);
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
        waitUntilPageIsReady(Time.TIME_SHORTER);
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

    public LoginPage typeUsername(String sUsername) {
        LoggerUtils.log.debug("typeUsername(" + sUsername + ")");
        Assert.assertTrue(isUsernameTextFieldEnabled(), "Username Text Field is NOT enabled on Login Page!");
        WebElement usernameTextField = getWebElement(usernameTextFieldLocator);
        clearAndTypeTextToWebElement(usernameTextField, sUsername);
        return this;
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

    public LoginPage typePassword(String sPassword) {
        LoggerUtils.log.debug("typePassword(" + sPassword + ")");
        Assert.assertTrue(isPasswordTextFieldEnabled(), "Password Text Field is NOT enabled on Login Page!");
        WebElement passwordTextField = getWebElement(passwordTextFieldLocator);
        clearAndTypeTextToWebElement(passwordTextField, sPassword);
        return this;
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
        return isWebElementDisplayed(loginButton);
    }

    public boolean isLoginButtonEnabled() {
        LoggerUtils.log.debug("isLoginButtonEnabled()");
        Assert.assertTrue(isLoginButtonDisplayed(), "Login Button is NOT displayed on Login Page!");
        return isWebElementEnabled(loginButton);
    }

    public String getLoginButtonTitle() {
        LoggerUtils.log.debug("getLoginButtonTitle()");
        Assert.assertTrue(isLoginButtonDisplayed(), "Login Button is NOT displayed on Login Page!");
        return getValueFromWebElement(loginButton);
    }

    private void clickLoginButtonNoVerification() {
        Assert.assertTrue(isLoginButtonEnabled(), "Login Button is NOT enabled on Login Page!");
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

    // Error Message
    public boolean isErrorMessageDisplayed() {
        LoggerUtils.log.debug("isErrorMessageDisplayed()");
        return isWebElementDisplayed(errorMessageLocator);
    }

    public String getErrorMessage() {
        LoggerUtils.log.debug("getErrorMessage()");
        Assert.assertTrue(isErrorMessageDisplayed(), "Error Message is NOT displayed on Login Page!");
        WebElement errorMessage = getWebElement(errorMessageLocator);
        return getTextFromWebElement(errorMessage);
    }

    // Success Message
    public boolean isSuccessMessageDisplayed() {
        LoggerUtils.log.debug("isSuccessMessageDisplayed()");
        return isWebElementDisplayed(successMessageLocator);
    }

    public String getSuccessMessage() {
        LoggerUtils.log.debug("getSuccessMessage()");
        Assert.assertTrue(isSuccessMessageDisplayed(), "Success Message is NOT displayed on Login Page!");
        WebElement successMessage = getWebElement(successMessageLocator);
        return getTextFromWebElement(successMessage);
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

    /**
     * Login to Samsara
     *
     * @param {String} sUsername - User's username
     * @param {String} sPassword - User's password
     * @return {WelcomePage} New instance of Welcome Page
     */
    public WelcomePage login(String sUsername, String sPassword) {
        LoggerUtils.log.info("login(" + sUsername + ", " + sPassword + ")");
        typeUsername(sUsername);
        typePassword(sPassword);
        return clickLoginButton();
    }

    /**
     * Login to Samsara
     * @param user {User} User details
     * @return {WelcomePage} New instance of Welcome Page
     */
    public WelcomePage login(User user) {
        return login(user.getUsername(), user.getPassword());
    }
}
