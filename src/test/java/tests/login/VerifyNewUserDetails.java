package tests.login;

import data.Groups;
import data.Time;
import objects.User;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.LoginPage;
import pages.UserDetailsDialogBox;
import pages.UsersPage;
import pages.WelcomePage;
import tests.BaseTestClass;
import utils.DateTimeUtils;
import utils.LoggerUtils;
import utils.RestApiUtils;

@Test(groups = {Groups.REGRESSION, Groups.SANITY, Groups.USERS})
public class VerifyNewUserDetails extends BaseTestClass {

    private final String sTestName = this.getClass().getName();
    private WebDriver driver;

    private User user;

    private boolean bCreated = false;

    @BeforeMethod
    public void setUpTest(ITestContext testContext) {
        LoggerUtils.log.debug("[SETUP TEST] " + sTestName);
        driver = setUpDriver();

        user = User.createNewUniqueUser("NewUserDetails");
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

        // Click Login Button
        WelcomePage welcomePage = loginPage.login(user);
        DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

        UsersPage usersPage = welcomePage.clickUsersTab();
        DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

        usersPage.search(user.getUsername());
        DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

        UserDetailsDialogBox userDetailsDialogBox = usersPage.clickUserDetailsIconInUsersTable(user.getUsername());
        DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(userDetailsDialogBox.getUsername(), user.getUsername(), "Username is NOT correct!");
        softAssert.assertEquals(userDetailsDialogBox.getFirstName(), user.getFirstName(), "First Name is NOT correct!");
        softAssert.assertEquals(userDetailsDialogBox.getLastName(), user.getLastName(), "Last Name is NOT correct!");
        softAssert.assertEquals(userDetailsDialogBox.getAboutText(), user.getAbout(), "About Text is NOT correct!");
        softAssert.assertTrue(DateTimeUtils.compareDateTime(userDetailsDialogBox.getCreatedAtDate(), user.getCreatedAt(), 120), "Created At Date is NOT correct! Expected: " + user.getCreatedAt() + ". Actual: " + userDetailsDialogBox.getCreatedAtDate() + ".");
        softAssert.assertAll("Wrong User Details in 'User Details' DialogBox for User '" + user.getUsername() + "'!");

        usersPage = userDetailsDialogBox.clickCloseButton();
        DateTimeUtils.wait(Time.TIME_DEMONSTRATION);
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
