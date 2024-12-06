package pages;

import data.PageUrlPaths;
import data.Time;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import utils.LoggerUtils;
import utils.PropertiesUtils;

public class ProfilePage extends CommonLoggedInPageClass {

    // Page Url Path
    private final String PROFILE_PAGE_URL = getPageUrl(PageUrlPaths.PROFILE_PAGE);

    @FindBy(xpath = "//form[@id='apply-avatar-form']/span[@class='warning-msg']")
    private WebElement profileImageMessage;

    @FindBy(xpath="//form[@id='apply-avatar-form']//input[@type='file' and @name='image']")
    private WebElement profileImageFilePathTextField;

    @FindBy(xpath="//form[@id='apply-avatar-form']//button[@type='submit']")
    private WebElement uploadImageButton;

    @FindBy(id="username")
    private WebElement usernameTextField;

    @FindBy(id="userId")
    private WebElement userIdTextField;

    public ProfilePage(WebDriver driver) {
        super(driver);
    }

    public ProfilePage open() {
        return open(true);
    }

    public ProfilePage open(boolean bVerify) {
        LoggerUtils.log.debug("open(" + PROFILE_PAGE_URL + ")");
        openUrl(PROFILE_PAGE_URL);
        if (bVerify) {
            verifyProfilePage();
        }
        return this;
    }

    public ProfilePage verifyProfilePage() {
        LoggerUtils.log.debug("verifyProfilePage()");
        waitForUrlChange(PROFILE_PAGE_URL, Time.TIME_SHORTER);
        waitUntilPageIsReady(Time.TIME_SHORT);
        return this;
    }

    public boolean isProfileImageFilePathTextFieldDisplayed() {
        LoggerUtils.log.debug("isProfileImageFilePathTextFieldDisplayed()");
        return isWebElementDisplayed(profileImageFilePathTextField);
    }

    public boolean isProfileImageFilePathTextFieldEnabled() {
        LoggerUtils.log.debug("isProfileImageFilePathTextFieldEnabled()");
        Assert.assertTrue(isProfileImageFilePathTextFieldDisplayed(), "Profile Image File Path Text Field is NOT displayed on Profile Page!");
        return isWebElementEnabled(profileImageFilePathTextField);
    }

    private ProfilePage typeProfileImageFilePath(String sFilePath) {
        Assert.assertTrue(isProfileImageFilePathTextFieldEnabled(), "Profile Image Text Field is NOT enabled on Profile Page!");
        clearAndTypeTextToWebElement(profileImageFilePathTextField, sFilePath);
        return this;
    }

    public String getProfileImageFilePath() {
        LoggerUtils.log.debug("getProfileImageFilePath()");
        Assert.assertTrue(isProfileImageFilePathTextFieldDisplayed(), "Profile Image File Path Text Field is NOT displayed on Profile Page!");
        return getValueFromWebElement(profileImageFilePathTextField);
    }

    public boolean isUploadImageButtonDisplayed() {
        LoggerUtils.log.debug("isUploadImageButtonDisplayed()");
        return isWebElementDisplayed(uploadImageButton);
    }

    public boolean isUploadImageButtonEnabled() {
        LoggerUtils.log.debug("isUploadImageButtonEnabled()");
        Assert.assertTrue(isUploadImageButtonDisplayed(), "Upload Image Button is NOT displayed on Profile Page!");
        return isWebElementEnabled(uploadImageButton);
    }

    public String getUploadImageButtonTitle() {
        LoggerUtils.log.debug("getUploadImageButtonTitle()");
        Assert.assertTrue(isUploadImageButtonDisplayed(), "Upload Image Button is NOT displayed on Profile Page!");
        return getTextFromWebElement(uploadImageButton);
    }

    public ProfilePage clickUploadImageButton() {
        LoggerUtils.log.debug("clickUploadImageButton()");
        Assert.assertTrue(isUploadImageButtonEnabled(), "Upload Image Button is NOT enabled on Profile Page!");
        clickOnWebElement(uploadImageButton);
        ProfilePage profilePage = new ProfilePage(driver);
        return profilePage.verifyProfilePage();
    }

    public ProfilePage uploadProfileImage(String sFileName) {
        LoggerUtils.log.debug("uploadProfileImage(" + sFileName + ")");
        String sFilePath = System.getProperty("user.dir") + PropertiesUtils.getImagesFolder() + sFileName;
        LoggerUtils.log.info("Profile Image File Path: " + sFilePath);
        typeProfileImageFilePath(sFilePath);
        return clickUploadImageButton();
    }

    public boolean isProfileImageMessageDisplayed() {
        LoggerUtils.log.debug("isProfileImageMessageDisplayed()");
        return isWebElementDisplayed(profileImageMessage);
    }

    public String getProfileImageMessage() {
        LoggerUtils.log.debug("getProfileImageMessage()");
        Assert.assertTrue(isProfileImageMessageDisplayed(), "Profile Image Message is NOT displayed!");
        return getTextFromWebElement(profileImageMessage);
    }

    public boolean isUsernameTextFieldDisplayed() {
        LoggerUtils.log.debug("isUsernameTextFieldDisplayed()");
        return isWebElementDisplayed(usernameTextField);
    }

    public boolean isUsernameTextFieldEnabled() {
        LoggerUtils.log.debug("isUsernameTextFieldEnabled()");
        Assert.assertTrue(isUsernameTextFieldDisplayed(), "Username Text Field is NOT displayed on Profile Page!");
        return isWebElementEnabled(usernameTextField);
    }

    public boolean isUsernameTextFieldReadOnly() {
        LoggerUtils.log.debug("isUsernameTextFieldReadOnly()");
        Assert.assertTrue(isUsernameTextFieldDisplayed(), "Username Text Field is NOT displayed on Profile Page!");
        return isWebElementReadOnly(usernameTextField);
    }

    public ProfilePage typeUsername(String sUsername) {
        LoggerUtils.log.debug("typeUsername(" + sUsername + ")");
        if(isUsernameTextFieldReadOnly()) {
            removeAttributeFromWebElement(usernameTextField,"readonly");
            clearAndTypeTextToWebElement(usernameTextField, sUsername);
            setAttributeToWebElement(usernameTextField, "readonly", "readonly");
        } else {
            clearAndTypeTextToWebElement(usernameTextField, sUsername);
        }
        return this;
    }

    /*
    public ProfilePage typeUsername(String sUsername) {
        LoggerUtils.log.debug("typeUsername(" + sUsername + ")");
        if(!isUsernameTextFieldEnabled()) {
            removeAttributeFromWebElement(usernameTextField, "disabled");
            clearAndTypeTextToWebElement(usernameTextField, sUsername);
            setAttributeToWebElement(usernameTextField, "disabled", "true");
        } else {
            clearAndTypeTextToWebElement(usernameTextField, sUsername);
        }
        return this;
    }
     */

    public String getUsername() {
        LoggerUtils.log.debug("getUsername()");
        Assert.assertTrue(isUsernameTextFieldDisplayed(), "Username Text Field is NOT displayed on Profile Page!");
        return getValueFromWebElement(usernameTextField);
    }

    public boolean isUserIdTextFieldDisplayed() {
        LoggerUtils.log.debug("isUserIdTextFieldDisplayed()");
        return isWebElementDisplayed(userIdTextField);
    }

    public boolean isUserIdTextFieldEnabled() {
        LoggerUtils.log.debug("isUserIdTextFieldEnabled()");
        //Assert.assertTrue(isUserIdTextFieldDisplayed(), "UserID Text Field is NOT displayed on Profile Page!");
        return isWebElementEnabled(userIdTextField);
    }

    /*
    public ProfilePage typeUserID(String sUserID) {
        LoggerUtils.log.debug("typeUserID(" + sUserID + ")");
        if(!isUserIdTextFieldDisplayed()) {
            setCssStyleToWebElement(userIdTextField, "display", "block");
            Assert.assertTrue(isUserIdTextFieldEnabled(), "UserID Text Field is NOT enabled on Profile Page!");
            clearAndTypeTextToWebElement(userIdTextField, sUserID);
            setCssStyleToWebElement(userIdTextField, "display", "none");
        } else {
            Assert.assertTrue(isUserIdTextFieldEnabled(), "UserID Text Field is NOT enabled on Profile Page!");
            clearAndTypeTextToWebElement(userIdTextField, sUserID);
        }
        return this;
    }
    */


    public ProfilePage typeUserID(String sUserID) {
        LoggerUtils.log.debug("typeUserID(" + sUserID + ")");
        if(!isUserIdTextFieldDisplayed()) {
            setCssStyleToWebElement(userIdTextField, "visibility", "visible");
            Assert.assertTrue(isUserIdTextFieldEnabled(), "UserID Text Field is NOT enabled on Profile Page!");
            clearAndTypeTextToWebElement(userIdTextField, sUserID);
            setCssStyleToWebElement(userIdTextField, "visibility", "hidden");
        } else {
            Assert.assertTrue(isUserIdTextFieldEnabled(), "UserID Text Field is NOT enabled on Profile Page!");
            clearAndTypeTextToWebElement(userIdTextField, sUserID);
        }
        return this;
    }


    public String getUserID() {
        LoggerUtils.log.debug("getUserID()");
        Assert.assertTrue(isUserIdTextFieldDisplayed(), "UserID Text Field is NOT displayed on Profile Page!");
        return getValueFromWebElement(userIdTextField);
    }


}
