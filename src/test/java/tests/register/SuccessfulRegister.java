package tests.register;

import data.CommonStrings;
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
import pages.RegisterPage;
import tests.BaseTestClass;
import utils.DateTimeUtils;
import utils.LoggerUtils;

import java.util.Date;

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
        LoggerUtils.log.info(user);
        bCreated = false;

        Date currentDateTime = DateTimeUtils.getCurrentDateTime();
        LoggerUtils.log.info("Date: " + currentDateTime.toString());

        String formattedDateTime = DateTimeUtils.getFormattedDateTime(currentDateTime, "EEEE dd-MMMM-yyyy HH:mm:ss z");
        LoggerUtils.log.info("Formatted Date: " + formattedDateTime);
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
        bCreated = true;

        String sRegisterSuccessMessage = loginPage.getSuccessMessage();
        Assert.assertEquals(sRegisterSuccessMessage, sExpectedRegisterSuccessMessage, "Wrong Success Message");

        // Check if User exists in database
        
    }

    @AfterMethod
    public void tearDownTest(ITestResult testResult) {
        LoggerUtils.log.debug("[END TEST] " + sTestName);
        tearDown(driver, testResult);
        if(bCreated) {
            try {
             // 2. Delete Rest Api call to delete user
            } finally {
                quitDriver(driver);
            }
        }
    }
}
