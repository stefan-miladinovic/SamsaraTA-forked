package tests.login;

import data.CommonStrings;
import data.Groups;
import data.Time;
import objects.User;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.WelcomePage;
import tests.BaseTestClass;
import utils.DateTimeUtils;
import utils.LoggerUtils;
import utils.RestApiUtils;

@Test(groups = {Groups.REGRESSION, Groups.SANITY, Groups.LOGIN})
public class SuccessfulLoginLogout extends BaseTestClass {

    private final String sTestName = this.getClass().getName();
    private WebDriver driver;

    private User user;

    private boolean bCreated = false;

    @BeforeMethod
    public void setUpTest(ITestContext testContext) {
        LoggerUtils.log.debug("[SETUP TEST] " + sTestName);
        driver = setUpDriver();

        user = User.createNewUniqueUser("SuccessfulLogin");
        RestApiUtils.postUser(user);
        user.setCreatedAt(RestApiUtils.getUser(user.getUsername()).getCreatedAt());
        bCreated = true;
        LoggerUtils.log.info(user);
    }

    @Test
    public void test() {
        LoggerUtils.log.debug("[START TEST] " + sTestName);

        // Verify login with newly created user
        String sExpectedWelcomePageTitle = CommonStrings.getWelcomePageTitle();

        LoginPage loginPage = new LoginPage(driver).open();
        DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

        loginPage.typeUsername(user.getUsername());
        DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

        // Type Password
        loginPage.typePassword(user.getPassword());
        DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

        // Click Login Button
        WelcomePage welcomePage = loginPage.clickLoginButton();
        DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

        // Verify WelcomePage Title
        String sWelcomePageTitle = welcomePage.getPageTitle();
        Assert.assertEquals(sWelcomePageTitle, sExpectedWelcomePageTitle, "Wrong WelcomePage Title!");
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
