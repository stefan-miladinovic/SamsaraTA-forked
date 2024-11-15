package tests.evaluation;

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
import pages.LoginPage;
import pages.WelcomePage;
import tests.BaseTestClass;
import utils.DateTimeUtils;
import utils.LoggerUtils;
import utils.PropertiesUtils;
import utils.RestApiUtils;

@Test(groups = {Groups.EVALUATION})
public class EvaluateLoginPage extends BaseTestClass {

    private final String sTestName = this.getClass().getName();
    private WebDriver driver;

    @BeforeMethod
    public void setUpTest(ITestContext testContext) {
        LoggerUtils.log.debug("[SETUP TEST] " + sTestName);
        driver = setUpDriver();
    }

    @Test
    public void test() {
        LoggerUtils.log.debug("[START TEST] " + sTestName);

        LoginPage loginPage = new LoginPage(driver).open();
        DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

        SoftAssert softAssert = new SoftAssert();

        softAssert.assertTrue(loginPage.isUsernameTextFieldDisplayed(), "Username Text Field is NOT displayed on Login Page!");
        softAssert.assertTrue(loginPage.isPasswordTextFieldDisplayed(), "Password Text Field is NOT displayed on Login Page!");
        softAssert.assertTrue(loginPage.isLoginButtonDisplayed(), "Login Button is NOT displayed on Login Page!");
        softAssert.assertTrue(loginPage.isCreateAccountLinkDisplayed(), "Create Account Link is NOT displayed on Login Page!");
        softAssert.assertTrue(loginPage.isResetPasswordLinkDisplayed(), "Reset Password Link is NOT displayed on Login Page!");

        softAssert.assertAll("At least one Web Element cannot be located on Login Page! Locator(s) changed or bug?");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDownTest(ITestResult testResult) {
        LoggerUtils.log.debug("[END TEST] " + sTestName);
        tearDown(driver, testResult);
    }
}
