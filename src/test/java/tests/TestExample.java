package tests;

import data.Time;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import pages.AddUserDialogBox;
import pages.LoginPage;
import pages.UsersPage;
import pages.WelcomePage;
import utils.DateTimeUtils;
import utils.LoggerUtils;
import utils.PropertiesUtils;

public class TestExample extends BaseTestClass {


    @Test
    public void testExample1() {
        // create instance of web driver
        WebDriver driver = setUpDriver();
        try {
            String sUsername = PropertiesUtils.getAdminUsername();
            String sPassword = PropertiesUtils.getAdminPassword();
            DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

            LoginPage loginPage = new LoginPage(driver).open();
            DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

            loginPage.typeUsername(sUsername);
            DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

            loginPage.typePassword(sPassword);
            DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

            WelcomePage welcomePage = loginPage.clickLoginButton();
            DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

            UsersPage usersPage = welcomePage.clickUsersTab();
            DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

            AddUserDialogBox addUserDialogBox = usersPage.clickAddNewUserButton();
            DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

            usersPage = addUserDialogBox.clickCancelButton();
            DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

        }
        finally {
            quitDriver(driver);
        }
    }


    @Test
    public void testSuccessfulLoginLogout() {
        WebDriver driver = setUpDriver();
        try {

            String sUsername = PropertiesUtils.getAdminUsername();
            String sPassword = PropertiesUtils.getAdminPassword();
            DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

            // Open Samsara
            LoginPage loginPage = new LoginPage(driver).open();
            DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

            // Type Username
            loginPage.typeUsername(sUsername);
            DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

            // Type Password
            loginPage.typePassword(sPassword);
            DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

            // Click Login Button
            WelcomePage welcomePage = loginPage.clickLoginButton();
            DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

            // Click Logout link

            // Verify Success Message on Login Page
        }
        finally {
            quitDriver(driver);
        }
    }

    @Test
    public void testUnsuccessfulLoginWrongPassword() {
        WebDriver driver = setUpDriver();
        try {
            String sUsername = PropertiesUtils.getAdminUsername();
            String sPassword = "wrong_password";
            DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

            // Open Samsara
            LoginPage loginPage = new LoginPage(driver).open();
            DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

            // Type Username
            loginPage.typeUsername(sUsername);
            DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

            // Type Password
            loginPage.typePassword(sPassword);
            DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

            // Click Login Button
            loginPage = loginPage.clickLoginButtonNoProgress();
            DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

            // Verify Error Message on Login Page


        } finally {
            quitDriver(driver);
        }
    }
}
