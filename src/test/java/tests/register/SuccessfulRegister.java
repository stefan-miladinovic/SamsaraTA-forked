package tests.register;

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
import org.testng.asserts.SoftAssert;
import pages.*;
import tests.BaseTestClass;
import utils.DateTimeUtils;
import utils.LoggerUtils;
import utils.RestApiUtils;

import java.util.Date;

@Test(groups = {Groups.REGRESSION, Groups.SANITY, Groups.REGISTER})
public class SuccessfulRegister extends BaseTestClass {

    private final String sTestName = this.getClass().getName();
    private WebDriver driver;

    private User user;

    private boolean bCreated;

    @BeforeMethod
    public void setUpTest(ITestContext testContext) {
        LoggerUtils.log.debug("[SETUP TEST] " + sTestName);
        driver = setUpDriver();

        user = User.createNewUniqueUser("SuccessfulRegister");
        bCreated = false;
    }

    @Test
    public void test() {
        LoggerUtils.log.debug("[START TEST] " + sTestName);

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

        Assert.assertTrue(RestApiUtils.checkIfUserExists(user.getUsername()), "User '" + user.getUsername() + " is NOT created!");

        User createdUser = RestApiUtils.getUser(user.getUsername());
        LoggerUtils.log.info(createdUser);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(createdUser.getFirstName(), user.getFirstName(), "Wrong First Name!");
        softAssert.assertEquals(createdUser.getLastName(), user.getLastName(), "Wrong Last Name!");
        softAssert.assertEquals(createdUser.getEmail(), user.getEmail(), "Wrong Email!");
        softAssert.assertEquals(createdUser.getAbout(), user.getAbout(), "Wrong About Text!");
        softAssert.assertTrue(DateTimeUtils.compareDateTime(createdUser.getCreatedAt(), user.getCreatedAt(), 60), "Wrong CreatedAt Date!");
        softAssert.assertEquals(createdUser.getSecretAnswer(), user.getSecretAnswer(), "Wrong Secret Answer!");
        softAssert.assertEquals(createdUser.getHeroCount(), user.getHeroCount(), "Wrong Hero Count!");
        softAssert.assertAll("Wrong User Details are saved in Database for User '" + user.getUsername() + "'!");

        WelcomePage welcomePage = loginPage.login(user);
        DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

        UsersPage usersPage = welcomePage.clickUsersTab();
        DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

        usersPage.search(user.getUsername());
        DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

        UserDetailsDialogBox userDetailsDialogBox = usersPage.clickUserDetailsIconInUsersTable(user.getUsername());
        DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

        Date date = userDetailsDialogBox.getCreatedAtDate();
        Assert.assertTrue(DateTimeUtils.compareDateTime(date, createdUser.getCreatedAt(), 60),"Wrong CreatedAt Date on UserDetails DialogBox");
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
