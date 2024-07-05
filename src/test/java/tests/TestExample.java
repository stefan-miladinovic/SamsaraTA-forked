package tests;

import data.Time;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import pages.*;
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

            loginPage.getPageTitle();

            // Type Username
            loginPage.typeUsername(sUsername);
            DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

            // Type Password
            loginPage.typePassword(sPassword);
            DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

            // Click Login Button
            WelcomePage welcomePage = loginPage.clickLoginButton();
            DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

            welcomePage.getPageTitle();

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

    @Test
    public void searchUser() {
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

            // Go to Users Page
            UsersPage usersPage = welcomePage.clickUsersTab();
            DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

            LoggerUtils.log.info("Display Name: " + usersPage.getDisplayNameInUsersTable("finn"));
            LoggerUtils.log.info("Hero Count: " + usersPage.getHeroCountInUsersTable("finn"));

            // Open User Heroes Dialog Box
            UserHeroesDialogBox userHeroesDialogBox = usersPage.clickHeroCountLinkInUsersTable("finn");
            DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

            usersPage = userHeroesDialogBox.clickCloseButton();
            DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

            // Open User Details Dialog Box
            UserDetailsDialogBox userDetailsDialogBox = usersPage.clickUserDetailsIconInUsersTable("finn");
            DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

            usersPage = userDetailsDialogBox.clickCloseButton();
            DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

        } finally {
            quitDriver(driver);
        }
    }
}
