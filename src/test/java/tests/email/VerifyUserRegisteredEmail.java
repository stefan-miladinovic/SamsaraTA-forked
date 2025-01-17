package tests.email;

import data.CommonStrings;
import data.Groups;
import data.Time;
import objects.EmailMessage;
import objects.User;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.LoginPage;
import pages.RegisterPage;
import tests.BaseTestClass;
import utils.*;

@Test(groups = {Groups.REGRESSION, Groups.REGISTER, Groups.USERS, Groups.EMAIL})
public class VerifyUserRegisteredEmail extends BaseTestClass {

    private final String sTestName = this.getClass().getName();
    private WebDriver driver;

    private User user;

    private String sAdminGmailAccount;
    private String sAdminGmailPassword;

    private EmailConnection connection = null;

    private boolean bCreated;

    @BeforeMethod
    public void setUpTest(ITestContext testContext) {
        LoggerUtils.log.debug("[SETUP TEST] " + sTestName);
        driver = setUpDriver();

        sAdminGmailAccount = PropertiesUtils.getAdminGmailAccount();
        sAdminGmailPassword = PropertiesUtils.getAdminGmailPassword();

        user = User.createNewUniqueUser("UserRegisteredEmail");
        bCreated = false;
    }

    @Test
    public void test() {
        LoggerUtils.log.debug("[START TEST] " + sTestName);

        String sExpectedEmailSubject = CommonStrings.getEmailSubjectUserRegistered(user.getUsername());
        String sExpectedEmailBody= CommonStrings.getEmailBodyUserRegistered(user.getUsername());
        String sExpectedAttachmentName = "useless_attachment.txt";
        String sExpectedAttachmentText = CommonStrings.getEmailUselessAttachmentText();

        String sExpectedRegisterSuccessMessage = CommonStrings.getRegisterSuccessMessage();

        LoginPage loginPage = new LoginPage(driver).open();
        DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

        RegisterPage registerPage = loginPage.clickCreateAccountLink();
        DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

        registerPage.typeUsername(user.getUsername());
        registerPage.typeFirstName(user.getFirstName());
        registerPage.typeLastName(user.getLastName());
        registerPage.typeEmail(user.getEmail());
        registerPage.typeAbout(user.getAbout());
        registerPage.typeSecretQuestion(user.getSecretQuestion());
        registerPage.typeSecretAnswer(user.getSecretAnswer());
        registerPage.typePassword(user.getPassword());
        registerPage.typeConfirmPassword(user.getPassword());
        DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

        loginPage = registerPage.clickSignUpButton();
        DateTimeUtils.wait(Time.TIME_DEMONSTRATION);
        user.setCreatedAt(DateTimeUtils.getCurrentDateTime());
        bCreated = true;

        String sRegisterSuccessMessage = loginPage.getSuccessMessage();
        Assert.assertEquals(sRegisterSuccessMessage, sExpectedRegisterSuccessMessage, "Wrong Success Message");
        DateTimeUtils.wait(Time.TIME_SHORT);

        // Establish Connection
        connection = EmailConnection.createEmailConnection(sAdminGmailAccount, sAdminGmailPassword);

        // Get Email Message
        EmailMessage message = connection.getLastMessage(null, sAdminGmailAccount, user.getUsername(), null, null);

        // Verify Email Details
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(message.getSubject(), sExpectedEmailSubject, "Wrong Email Subject!");
        softAssert.assertEquals(message.getBody(), sExpectedEmailBody, "Wrong Email Body!");
        softAssert.assertEquals(message.getAttachmentsCount(), 1, "Wrong number of Attachments!");
        softAssert.assertTrue(message.isAttachmentPresent(sExpectedAttachmentName), "Attachment '" + sExpectedAttachmentName + "' is NOT present!");
        softAssert.assertAll("Wrong Email Details!");

        // Download Attachment and Verify Attachment Content
        String sFilePath = message.downloadAttachment(sExpectedAttachmentName);
        String sAttachmentText = FileUtils.readTextFile(sFilePath);
        Assert.assertEquals(sAttachmentText, sExpectedAttachmentText, "Wrong Attachment Text!");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDownTest(ITestResult testResult) {
        LoggerUtils.log.debug("[END TEST] " + sTestName);
        tearDown(driver, testResult);
        if(bCreated) {
            deleteUser();
        }
        if(connection != null) {
            connection.closeEmailConnection();
        }
    }

    private void deleteUser() {
        LoggerUtils.log.debug("deleteUser()");
        try {
            RestApiUtils.deleteUser(user.getUsername());
        } catch (AssertionError | Exception e) {
            LoggerUtils.log.error("Delete User Failed! Message: " + e.getMessage());
        }
    }
}
