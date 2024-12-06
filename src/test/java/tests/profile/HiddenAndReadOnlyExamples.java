package tests.profile;

import data.Time;
import objects.User;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.ProfilePage;
import pages.WelcomePage;
import tests.BaseTestClass;
import utils.DateTimeUtils;
import utils.LoggerUtils;
import utils.RestApiUtils;

public class HiddenAndReadOnlyExamples extends BaseTestClass {

    private final String sTestName = this.getClass().getName();
    private WebDriver driver;

    private User user;

    private boolean bCreated = false;

    @BeforeMethod
    public void setUpTest(ITestContext testContext) {
        LoggerUtils.log.debug("[SETUP TEST] " + sTestName);
        driver = setUpDriver();

        user = User.createNewUniqueUser("HiddenReadOnly");
        RestApiUtils.postUser(user);
        user.setCreatedAt(RestApiUtils.getUser(user.getUsername()).getCreatedAt());
        bCreated = true;
        LoggerUtils.log.info(user);
    }

    @Test
    public void test() {
        LoggerUtils.log.debug("[START TEST] " + sTestName);

        LoginPage loginPage = new LoginPage(driver).open();
        DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

        WelcomePage welcomePage = loginPage.login(user);
        DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

        ProfilePage profilePage = welcomePage.clickProfileLink();
        DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

        LoggerUtils.log.info("UserID displayed? " + profilePage.isUserIdTextFieldDisplayed());
        LoggerUtils.log.info("UserID enabled? " + profilePage.isUserIdTextFieldEnabled());
        profilePage.typeUserID("12345678");
        LoggerUtils.log.info("New UserID: " + profilePage.getUserID());

        LoggerUtils.log.info("Old Username: " + profilePage.getUsername());

        LoggerUtils.log.info("Username displayed? " + profilePage.isUsernameTextFieldDisplayed());
        LoggerUtils.log.info("Username enabled? " + profilePage.isUsernameTextFieldEnabled());
        LoggerUtils.log.info("Username read only? " + profilePage.isUsernameTextFieldReadOnly());

        profilePage.typeUsername("new_username");
        DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

        LoggerUtils.log.info("New Username: " + profilePage.getUsername());
    }

    @AfterMethod(alwaysRun = true)
    public void tearDownTest(ITestResult testResult) {
        LoggerUtils.log.debug("[END TEST] " + sTestName);
        tearDown(driver, testResult);
        if(bCreated) {
            cleanUp();
        }
    }

    private void cleanUp() {
        LoggerUtils.log.debug("cleanUp()");
        try {
            RestApiUtils.deleteUser(user.getUsername());
        } catch (AssertionError | Exception e) {
            LoggerUtils.log.error("Cleaning Up Failed! Message: " + e.getMessage());
        }
    }
}
