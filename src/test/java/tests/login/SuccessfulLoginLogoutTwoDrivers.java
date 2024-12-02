package tests.login;

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
import pages.HeroesPage;
import pages.LoginPage;
import pages.UsersPage;
import pages.WelcomePage;
import tests.BaseTestClass;
import utils.DateTimeUtils;
import utils.LoggerUtils;
import utils.PropertiesUtils;
import utils.RestApiUtils;

@Test(groups = {Groups.REGRESSION, Groups.SANITY, Groups.LOGIN})
public class SuccessfulLoginLogoutTwoDrivers extends BaseTestClass {

    private final String sTestName = this.getClass().getName();
    private WebDriver driver1;
    private WebDriver driver2;

    private String adminUsername;
    private String adminPassword;

    private User user;

    private boolean bCreated = false;

    @BeforeMethod
    public void setUpTest(ITestContext testContext) {
        LoggerUtils.log.debug("[SETUP TEST] " + sTestName);
        driver1 = setUpDriver();
        driver2 = setUpDriver();

        adminUsername = PropertiesUtils.getAdminUsername();
        adminPassword = PropertiesUtils.getAdminPassword();

        user = User.createNewUniqueUser("TwoDrivers");
        RestApiUtils.postUser(user);
        user.setCreatedAt(RestApiUtils.getUser(user.getUsername()).getCreatedAt());
        bCreated = true;
        LoggerUtils.log.info(user);
    }

    @Test
    public void test() {
        LoggerUtils.log.debug("[START TEST] " + sTestName);

        LoginPage loginPage1 = new LoginPage(driver1).open();
        DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

        WelcomePage welcomePage1 = loginPage1.login(adminUsername, adminPassword);
        DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

        LoginPage loginPage2 = new LoginPage(driver2).open();
        DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

        WelcomePage welcomePage2 = loginPage2.login(user);
        DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

        UsersPage usersPage1 = welcomePage1.clickUsersTab();
        DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

        HeroesPage heroesPage2 = welcomePage2.clickHeroesTab();
        DateTimeUtils.wait(Time.TIME_SHORT);

        Assert.fail("FAILURE!");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDownTest(ITestResult testResult) {
        LoggerUtils.log.debug("[END TEST] " + sTestName);
        tearDown(new WebDriver[]{driver1, driver2}, testResult);
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
