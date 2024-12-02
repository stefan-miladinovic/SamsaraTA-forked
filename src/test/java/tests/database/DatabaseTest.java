package tests.database;

import data.Groups;
import objects.DatabaseHero;
import objects.DatabaseUser;
import objects.User;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tests.BaseTestClass;
import utils.DatabaseUtils;
import utils.LoggerUtils;

import java.util.List;

@Test(groups = {Groups.DATABASE})
public class DatabaseTest extends BaseTestClass {

    private final String sTestName = this.getClass().getName();


    @BeforeMethod
    public void setUpTest(ITestContext testContext) {
        LoggerUtils.log.debug("[SETUP TEST] " + sTestName);

    }

    @Test
    public void test() {
        LoggerUtils.log.debug("[START TEST] " + sTestName);

        String result1 = DatabaseUtils.getUserID("dedoje");
        LoggerUtils.log.info("User ID: " + result1);

        List<String> result2 = DatabaseUtils.getAllUsernames();
        LoggerUtils.log.info("Usernames: " + result2);

        DatabaseUser result3a = DatabaseUtils.getDatabaseUser("dedoje");
        LoggerUtils.log.info("User: " + result3a);

        User result3b = DatabaseUtils.getUser("dedoje");
        LoggerUtils.log.info("User: " + result3b);

        List<DatabaseHero> result4 = DatabaseUtils.getHeroesForUser("3");
        for(DatabaseHero hero : result4) {
            LoggerUtils.log.info(hero);
        }
    }

    @AfterMethod(alwaysRun = true)
    public void tearDownTest(ITestResult testResult) {
        LoggerUtils.log.debug("[END TEST] " + sTestName);
    }
}
