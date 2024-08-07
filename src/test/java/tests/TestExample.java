package tests;

import data.CommonStrings;
import data.Time;
import objects.Hero;
import objects.User;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import utils.DateTimeUtils;
import utils.LoggerUtils;
import utils.PropertiesUtils;
import utils.RestApiUtils;

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

        } finally {
            quitDriver(driver);
        }
    }


    @Test
    public void testSuccessfulLoginLogout() {
        WebDriver driver = setUpDriver();
        String sTestName = "testSuccessfulLoginLogout";
        boolean bSuccess = false;

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
            bSuccess = true;

        } finally {
            tearDown(driver, bSuccess, sTestName);
        }
    }

    @Test
    public void testUnsuccessfulLoginWrongPassword() {
        WebDriver driver = setUpDriver();
        String sTestName = "testUnsuccessfulLoginWrongPassword";
        String sExpectedErrorMessage = CommonStrings.getLoginErrorMessage();
        boolean bSuccess = false;

        try {
            String sUsername = PropertiesUtils.getAdminUsername();
            String sPassword = "wrong_password";
            DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

            // Open Samsara
            LoginPage loginPage = new LoginPage(driver).open();
            DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

            Assert.assertFalse(loginPage.isErrorMessageDisplayed(), "Error Message should NOT be displayed on Login Page by default!");

            // Type Username
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
            bSuccess = true;

        } finally {
            tearDown(driver, bSuccess, sTestName);
        }
    }

    @Test
    public void searchUser() {
        WebDriver driver = setUpDriver();

        String sTestUserUsername = "finn";
        String sTestUserDisplayName = "Finn Mertens";
        String sExpectedDeleteUserMessage = CommonStrings.getDeleteUserMessage(sTestUserUsername, sTestUserDisplayName);
        LoggerUtils.log.info("EXPECTED DELETE MESSAGE: " + sExpectedDeleteUserMessage);

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

            // Open UserHeroes Dialog Box
            UserHeroesDialogBox userHeroesDialogBox = usersPage.clickHeroCountLinkInUsersTable("finn");
            DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

            usersPage = userHeroesDialogBox.clickCloseButton();
            DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

            // Open UserDetails Dialog Box
            UserDetailsDialogBox userDetailsDialogBox = usersPage.clickUserDetailsIconInUsersTable("finn");
            DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

            usersPage = userDetailsDialogBox.clickCloseButton();
            DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

            // Open EditUser Dialog Box
            EditUserDialogBox editUserDialogBox = usersPage.clickEditUserIconInUsersTable("finn");
            DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

            usersPage = editUserDialogBox.clickCancelButton();
            DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

            // Open DeleteUser Dialog Box
            DeleteUserDialogBox deleteUserDialogBox = usersPage.clickDeleteUserIconInUsersTable("finn");
            DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

            String sDeleteUserMessage = sDeleteUserMessage = deleteUserDialogBox.getDeleteUserMessage();
            Assert.assertEquals(sDeleteUserMessage, sExpectedDeleteUserMessage, "Wrong Delete User Message!");

            usersPage = deleteUserDialogBox.clickCancelButton();
            DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

        } finally {
            quitDriver(driver);
        }
    }

    @Test
    public void chuckNorris() {

        WebDriver driver = setUpDriver();

        String sUsername = PropertiesUtils.getAdminUsername();
        String sPassword = PropertiesUtils.getAdminPassword();

        try {
            // Open Samsara
            LoginPage loginPage = new LoginPage(driver).open();
            DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

            // Login to Samsara
            WelcomePage welcomePage = loginPage.login(sUsername, sPassword);
            DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

            // Navigate to Admin Page
            AdminPage adminPage = welcomePage.clickAdminTab();
            DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

            adminPage.uncheckChuckNorrisCheckBox();
            DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

            adminPage.uncheckChuckNorrisCheckBox();
            DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

            adminPage.checkChuckNorrisCheckBox();
            DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

            adminPage.checkChuckNorrisCheckBox();
            DateTimeUtils.wait(Time.TIME_DEMONSTRATION);
        } finally {
            quitDriver(driver);
        }
    }

    @Test
    public void createNewHero() {

        WebDriver driver = setUpDriver();

        String sUsername = PropertiesUtils.getAdminUsername();
        String sPassword = PropertiesUtils.getAdminPassword();

        try {

            // Open Samsara
            LoginPage loginPage = new LoginPage(driver).open();
            DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

            // Login to Samsara
            WelcomePage welcomePage = loginPage.login(sUsername, sPassword);
            DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

            // Navigate to Heroes Page
            HeroesPage heroesPage = welcomePage.clickHeroesTab();
            DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

            // Open AddHero DialogBox
            AddHeroDialogBox addHeroDialogBox = heroesPage.clickAddNewHeroButton();
            DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

            LoggerUtils.log.info("Selected Class: " + addHeroDialogBox.getSelectedHeroClass());

            addHeroDialogBox.selectHeroClass("Thief");
            DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

            LoggerUtils.log.info("Selected Class: " + addHeroDialogBox.getSelectedHeroClass());

        } finally {
            quitDriver(driver);
        }
    }

    @Test
    public void getUserRestApiTest() {

        String jsonUser = RestApiUtils.getUserJsonFormat("dedoje");
        LoggerUtils.log.debug(jsonUser);

        User user = RestApiUtils.getUser("dedoje");
        LoggerUtils.log.debug(user);
    }

    @Test
    public void createNewUser() {

        User user = User.createNewUniqueUser("NewUser");
        LoggerUtils.log.info(user);

        Hero hero = Hero.createNewUniqueHero(user, "NewHero");
        LoggerUtils.log.info(hero);

        user.addHero(hero);
        LoggerUtils.log.info(user);

    }
}
