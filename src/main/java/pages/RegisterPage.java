package pages;

import data.PageUrlPaths;
import data.Time;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import utils.LoggerUtils;

public class RegisterPage extends CommonLoggedOutPageClass {

    // Page Url Path
    private final String REGISTER_PAGE_URL = getPageUrl(PageUrlPaths.REGISTER_PAGE);

    // Page Factory Locators
    @FindBy(id = "username")
    private WebElement usernameTextField;

    @FindBy(id = "firstName")
    private WebElement firstNameTextField;

    @FindBy(id = "lastName")
    private WebElement lastNameTextField;

    @FindBy(id = "email")
    private WebElement emailTextField;

    @FindBy(id = "about")
    private WebElement aboutTextField;

    @FindBy(id = "secretQuestion")
    private WebElement secretQuestionTextField;

    @FindBy(id = "secretAnswer")
    private WebElement secretAnswerTextField;

    @FindBy(id = "password")
    private WebElement passwordTextField;

    @FindBy(id = "repassword")
    private WebElement confirmPasswordTextField;

    @FindBy(id = "usernameMessage")
    private WebElement usernameErrorMessage;

    @FindBy(id = "firstNameMessage")
    private WebElement firstNameErrorMessage;

    @FindBy(id = "lastNameMessage")
    private WebElement lastNameErrorMessage;

    @FindBy(id = "emailMessage")
    private WebElement emailErrorMessage;

    @FindBy(id = "aboutMessage")
    private WebElement aboutErrorMessage;

    @FindBy(id = "questionMessage")
    private WebElement secretQuestionErrorMessage;

    @FindBy(id = "answerMessage")
    private WebElement secretAnswerErrorMessage;

    @FindBy(id = "passwordMessage")
    private WebElement passwordErrorMessage;

    @FindBy(id = "repasswordMessage")
    private WebElement confirmPasswordErrorMessage;

    @FindBy(id = "submitButton")
    private WebElement signUpButton;

    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    public RegisterPage open() {
        return open(true);
    }

    public RegisterPage open(boolean bVerify) {
        LoggerUtils.log.debug("open(" + REGISTER_PAGE_URL + ")");
        openUrl(REGISTER_PAGE_URL);
        if (bVerify) {
            verifyRegisterPage();
        }
        return this;
    }

    public RegisterPage verifyRegisterPage() {
        LoggerUtils.log.debug("verifyRegisterPage()");
        waitForUrlChange(REGISTER_PAGE_URL, Time.TIME_SHORTER);
        return this;
    }

    public boolean isUsernameTextFieldDisplayed() {
        LoggerUtils.log.debug("isUsernameTextFieldDisplayed()");
        return isWebElementDisplayed(usernameTextField);
    }

    public boolean isUsernameTextFieldEnabled() {
        LoggerUtils.log.debug("isUsernameTextFieldEnabled()");
        Assert.assertTrue(isUsernameTextFieldDisplayed(), "Username Text Field is NOT displayed on Register Page!");
        return isWebElementEnabled(usernameTextField);
    }

    public RegisterPage typeUsername(String sUsername) {
        LoggerUtils.log.debug("typeUsername(" + sUsername + ")");
        Assert.assertTrue(isUsernameTextFieldEnabled(), "Username Text Field is NOT enabled on Register Page!");
        clearAndTypeTextToWebElement(usernameTextField, sUsername);
        return this;
    }

    public String getUsername() {
        LoggerUtils.log.debug("getUsername()");
        Assert.assertTrue(isUsernameTextFieldDisplayed(), "Username Text Field is NOT displayed on Register Page!");
        return getValueFromWebElement(usernameTextField);
    }

    public boolean isFirstNameTextFieldDisplayed() {
        LoggerUtils.log.debug("isFirstNameTextFieldDisplayed()");
        return isWebElementDisplayed(firstNameTextField);
    }

    public boolean isFirstNameTextFieldEnabled() {
        LoggerUtils.log.debug("isFirstNameTextFieldEnabled()");
        Assert.assertTrue(isFirstNameTextFieldDisplayed(), "First Name Text Field is NOT displayed on Register Page!");
        return isWebElementEnabled(firstNameTextField);
    }

    public RegisterPage typeFirstName(String sFirstName) {
        LoggerUtils.log.debug("typeFirstName(" + sFirstName + ")");
        Assert.assertTrue(isFirstNameTextFieldEnabled(), "First Name Text Field is NOT enabled on Register Page!");
        clearAndTypeTextToWebElement(firstNameTextField, sFirstName);
        return this;
    }

    public String getFirstName() {
        LoggerUtils.log.debug("getFirstName()");
        Assert.assertTrue(isFirstNameTextFieldDisplayed(), "First Name Text Field is NOT displayed on Register Page!");
        return getValueFromWebElement(firstNameTextField);
    }

    public boolean isLastNameTextFieldDisplayed() {
        LoggerUtils.log.debug("isLastNameTextFieldDisplayed()");
        return isWebElementDisplayed(lastNameTextField);
    }

    public boolean isLastNameTextFieldEnabled() {
        LoggerUtils.log.debug("isLastNameTextFieldEnabled()");
        Assert.assertTrue(isLastNameTextFieldDisplayed(), "Last Name Text Field is NOT displayed on Register Page!");
        return isWebElementEnabled(lastNameTextField);
    }

    public RegisterPage typeLastName(String sLastName) {
        LoggerUtils.log.debug("typeLastName(" + sLastName + ")");
        Assert.assertTrue(isLastNameTextFieldEnabled(), "Last Name Text Field is NOT enabled on Register Page!");
        clearAndTypeTextToWebElement(lastNameTextField, sLastName);
        return this;
    }

    public String getLastName() {
        LoggerUtils.log.debug("getLastName()");
        Assert.assertTrue(isLastNameTextFieldDisplayed(), "Last Name Text Field is NOT displayed on Register Page!");
        return getValueFromWebElement(lastNameTextField);
    }

    public boolean isEmailTextFieldDisplayed() {
        LoggerUtils.log.debug("isEmailTextFieldDisplayed()");
        return isWebElementDisplayed(emailTextField);
    }

    public boolean isEmailTextFieldEnabled() {
        LoggerUtils.log.debug("isEmailTextFieldEnabled()");
        Assert.assertTrue(isEmailTextFieldDisplayed(), "Email Text Field is NOT displayed on Register Page!");
        return isWebElementEnabled(emailTextField);
    }

    public RegisterPage typeEmail(String sEmail) {
        LoggerUtils.log.debug("typeEmail(" + sEmail + ")");
        Assert.assertTrue(isEmailTextFieldEnabled(), "Email Text Field is NOT enabled on Register Page!");
        clearAndTypeTextToWebElement(emailTextField, sEmail);
        return this;
    }

    public String getEmail() {
        LoggerUtils.log.debug("getEmail()");
        Assert.assertTrue(isEmailTextFieldDisplayed(), "Email Text Field is NOT displayed on Register Page!");
        return getValueFromWebElement(emailTextField);
    }

    public boolean isAboutTextFieldDisplayed() {
        LoggerUtils.log.debug("isAboutTextFieldDisplayed()");
        return isWebElementDisplayed(aboutTextField);
    }

    public boolean isAboutTextFieldEnabled() {
        LoggerUtils.log.debug("isAboutTextFieldEnabled()");
        Assert.assertTrue(isAboutTextFieldDisplayed(), "About Text Field is NOT displayed on Register Page!");
        return isWebElementEnabled(aboutTextField);
    }

    public RegisterPage typeAbout(String sAbout) {
        LoggerUtils.log.debug("typeAbout(" + sAbout + ")");
        Assert.assertTrue(isAboutTextFieldEnabled(), "About Text Field is NOT enabled on Register Page!");
        clearAndTypeTextToWebElement(aboutTextField, sAbout);
        return this;
    }

    public String getAbout() {
        LoggerUtils.log.debug("getAbout()");
        Assert.assertTrue(isAboutTextFieldDisplayed(), "About Text Field is NOT displayed on Register Page!");
        return getValueFromWebElement(aboutTextField);
    }

    public boolean isSecretQuestionTextFieldDisplayed() {
        LoggerUtils.log.debug("isSecretQuestionTextFieldDisplayed()");
        return isWebElementDisplayed(secretQuestionTextField);
    }

    public boolean isSecretQuestionTextFieldEnabled() {
        LoggerUtils.log.debug("isSecretQuestionTextFieldEnabled()");
        Assert.assertTrue(isSecretQuestionTextFieldDisplayed(), "Secret Question Text Field is NOT displayed on Register Page!");
        return isWebElementEnabled(secretQuestionTextField);
    }

    public RegisterPage typeSecretQuestion(String sSecretQuestion) {
        LoggerUtils.log.debug("typeSecretQuestion(" + sSecretQuestion + ")");
        Assert.assertTrue(isSecretQuestionTextFieldEnabled(), "Secret Question Text Field is NOT enabled on Register Page!");
        clearAndTypeTextToWebElement(secretQuestionTextField, sSecretQuestion);
        return this;
    }

    public String getSecretQuestion() {
        LoggerUtils.log.debug("getSecretQuestion()");
        Assert.assertTrue(isSecretQuestionTextFieldDisplayed(), "Secret Question Text Field is NOT displayed on Register Page!");
        return getValueFromWebElement(secretQuestionTextField);
    }

    public boolean isSecretAnswerTextFieldDisplayed() {
        LoggerUtils.log.debug("isSecretAnswerTextFieldDisplayed()");
        return isWebElementDisplayed(secretAnswerTextField);
    }

    public boolean isSecretAnswerTextFieldEnabled() {
        LoggerUtils.log.debug("isSecretAnswerTextFieldEnabled()");
        Assert.assertTrue(isSecretAnswerTextFieldDisplayed(), "Secret Answer Text Field is NOT displayed on Register Page!");
        return isWebElementEnabled(secretAnswerTextField);
    }

    public RegisterPage typeSecretAnswer(String sSecretAnswer) {
        LoggerUtils.log.debug("typeSecretAnswer(" + sSecretAnswer + ")");
        Assert.assertTrue(isSecretAnswerTextFieldEnabled(), "Secret Answer Text Field is NOT enabled on Register Page!");
        clearAndTypeTextToWebElement(secretAnswerTextField, sSecretAnswer);
        return this;
    }

    public String getSecretAnswer() {
        LoggerUtils.log.debug("getSecretAnswer()");
        Assert.assertTrue(isSecretAnswerTextFieldDisplayed(), "Secret Answer Text Field is NOT displayed on Register Page!");
        return getValueFromWebElement(secretAnswerTextField);
    }

    public boolean isPasswordTextFieldDisplayed() {
        LoggerUtils.log.debug("isPasswordTextFieldDisplayed()");
        return isWebElementDisplayed(passwordTextField);
    }

    public boolean isPasswordTextFieldEnabled() {
        LoggerUtils.log.debug("isPasswordTextFieldEnabled()");
        Assert.assertTrue(isPasswordTextFieldDisplayed(), "Password Text Field is NOT displayed on Register Page!");
        return isWebElementEnabled(passwordTextField);
    }

    public RegisterPage typePassword(String sPassword) {
        LoggerUtils.log.debug("typePassword(" + sPassword + ")");
        Assert.assertTrue(isPasswordTextFieldEnabled(), "Password Text Field is NOT enabled on Register Page!");
        clearAndTypeTextToWebElement(passwordTextField, sPassword);
        return this;
    }

    public String getPassword() {
        LoggerUtils.log.debug("getPassword()");
        Assert.assertTrue(isPasswordTextFieldDisplayed(), "Password Text Field is NOT displayed on Register Page!");
        return getValueFromWebElement(passwordTextField);
    }

    public boolean isConfirmPasswordTextFieldDisplayed() {
        LoggerUtils.log.debug("isConfirmPasswordTextFieldDisplayed()");
        return isWebElementDisplayed(confirmPasswordTextField);
    }

    public boolean isConfirmPasswordTextFieldEnabled() {
        LoggerUtils.log.debug("isConfirmPasswordTextFieldEnabled()");
        Assert.assertTrue(isConfirmPasswordTextFieldDisplayed(), "Confirm Password Text Field is NOT displayed on Register Page!");
        return isWebElementEnabled(confirmPasswordTextField);
    }

    public RegisterPage typeConfirmPassword(String sPassword) {
        LoggerUtils.log.debug("typeConfirmPassword(" + sPassword + ")");
        Assert.assertTrue(isConfirmPasswordTextFieldEnabled(), "Confirm Password Text Field is NOT enabled on Register Page!");
        clearAndTypeTextToWebElement(confirmPasswordTextField, sPassword);
        return this;
    }

    public String getConfirmPassword() {
        LoggerUtils.log.debug("getConfirmPassword()");
        Assert.assertTrue(isConfirmPasswordTextFieldDisplayed(), "Confirm Password Text Field is NOT displayed on Register Page!");
        return getValueFromWebElement(confirmPasswordTextField);
    }

    public boolean isUsernameErrorMessageDisplayed() {
        LoggerUtils.log.debug("isUsernameErrorMessageDisplayed()");
        return isWebElementVisible(usernameErrorMessage, Time.TIME_SHORTEST);
    }

    public String getUsernameErrorMessage() {
        LoggerUtils.log.debug("getUsernameErrorMessage()");
        Assert.assertTrue(isUsernameErrorMessageDisplayed(), "Username Error Message is NOT displayed on Register Page!");
        return getTextFromWebElement(usernameErrorMessage);
    }

    public boolean isSignUpButtonDisplayed() {
        LoggerUtils.log.debug("isSignUpButtonDisplayed()");
        return isWebElementDisplayed(signUpButton);
    }

    public boolean isSignUpButtonEnabled() {
        LoggerUtils.log.debug("isSignUpButtonEnabled()");
        Assert.assertTrue(isSignUpButtonDisplayed(), "'Sign Up' Button is NOT displayed on Register Page!");
        return isWebElementEnabled(signUpButton, Time.TIME_SHORTER);
    }

    public LoginPage clickSignUpButton() {
        LoggerUtils.log.debug("clickSignUpButton()");
        Assert.assertTrue(isSignUpButtonEnabled(), "'Sign Up' Button is NOT enabled on Register Page!");
        clickOnWebElement(signUpButton);
        LoginPage loginPage = new LoginPage(driver);
        return loginPage.verifyLoginPage();
    }
}
