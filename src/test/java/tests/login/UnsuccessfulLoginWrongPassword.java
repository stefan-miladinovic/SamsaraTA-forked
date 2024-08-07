package tests.login;

import data.CommonStrings;
import data.Groups;
import data.Time;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import tests.BaseTestClass;
import utils.DateTimeUtils;
import utils.LoggerUtils;
import utils.PropertiesUtils;

@Test(groups = {Groups.REGRESSION, Groups.LOGIN})
public class UnsuccessfulLoginWrongPassword extends BaseTestClass {

    private final String sTestName = this.getClass().getName();
    private WebDriver driver;

    private String sUsername;
    private String sPassword;

    @BeforeMethod
    public void setUpTest(ITestContext testContext) {
        LoggerUtils.log.debug("[SETUP TEST] " + sTestName);
        driver = setUpDriver();

        sUsername = PropertiesUtils.getAdminUsername();
        sPassword = "wrong_password";
    }

    @Test
    public void test() {
        LoggerUtils.log.debug("[START TEST] " + sTestName);

        String sExpectedErrorMessage = CommonStrings.getLoginErrorMessage();

        LoginPage loginPage = new LoginPage(driver).open();
        DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

        loginPage.typeUsername(sUsername);
        DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

        // Type Password
        loginPage.typePassword(sPassword);
        DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

        // Click Login Button
        loginPage = loginPage.clickLoginButtonNoProgress();
        DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

        String sErrorMessage = loginPage.getErrorMessage();
        Assert.assertEquals(sErrorMessage, sExpectedErrorMessage, "Wrong Error Message!");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDownTest(ITestResult testResult) {
        LoggerUtils.log.debug("[END TEST] " + sTestName);
        tearDown(driver, testResult);
    }
}
