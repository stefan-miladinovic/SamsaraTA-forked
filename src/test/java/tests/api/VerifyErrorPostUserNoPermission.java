package tests.api;

import data.ApiCalls;
import data.CommonStrings;
import data.Groups;
import objects.ApiError;
import objects.User;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import tests.BaseTestClass;
import utils.DateTimeUtils;
import utils.LoggerUtils;
import utils.PropertiesUtils;
import utils.RestApiUtils;

import java.util.Date;

@Test(groups = {Groups.API, Groups.USERS})
public class VerifyErrorPostUserNoPermission extends BaseTestClass {

    private final String sTestName = this.getClass().getName();

    private User newUser;

    private String sUsername = PropertiesUtils.getUsername();
    private String sPassword = PropertiesUtils.getPassword();

    private boolean bCreated = false;


    @BeforeMethod
    public void setUpTest(ITestContext testContext) {
        LoggerUtils.log.debug("[SETUP TEST] " + sTestName);

        newUser = User.createNewUniqueUser("NoPermissionUser");
        LoggerUtils.log.info(newUser);
    }

    @Test
    public void test() {
        LoggerUtils.log.debug("[START TEST] " + sTestName);

        int iExpectedStatusCode = 403;
        String sExpectedError = CommonStrings.getApiErrorForbidden();
        String sExpectedMessage = CommonStrings.getApiMessageAccessDenied();
        String sExpectedPath = ApiCalls.createPostUserApiCallRelativePath();
        Date expectedDateTime = DateTimeUtils.getCurrentDateTime();

        ApiError error = RestApiUtils.postUserError(newUser, sUsername, sPassword);
        LoggerUtils.log.info("API Error: " + error);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(error.getStatus(), iExpectedStatusCode, "Wrong Status Code!");
        softAssert.assertEquals(error.getError(), sExpectedError, "Wrong Error!");
        softAssert.assertNull(error.getException(), "Exception should NOT exist!");
        softAssert.assertEquals(error.getMessage(), sExpectedMessage, "Wrong Message!");
        softAssert.assertEquals(error.getPath(), sExpectedPath, "Wrong Path!");
        softAssert.assertTrue(DateTimeUtils.compareDateTime(error.getTimestamp(), expectedDateTime, 180), "Wrong Timestamp! Expected: " + expectedDateTime + ". Actual: " + error.getTimestamp() + ".");
        softAssert.assertAll("Wrong Error Response Details!");

        Assert.assertFalse(RestApiUtils.checkIfUserExists(newUser.getUsername()), "User '" + newUser.getUsername() + "' should NOT be created!");

    }

    @AfterMethod(alwaysRun = true)
    public void tearDownTest(ITestResult testResult) {
        LoggerUtils.log.debug("[END TEST] " + sTestName);
    }
}
