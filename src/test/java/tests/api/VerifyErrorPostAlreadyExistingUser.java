package tests.api;

import data.ApiCalls;
import data.CommonStrings;
import data.Groups;
import objects.ApiError;
import objects.User;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import tests.BaseTestClass;
import utils.DateTimeUtils;
import utils.LoggerUtils;
import utils.RestApiUtils;

import java.util.Date;

@Test(groups = {Groups.API, Groups.USERS})
public class VerifyErrorPostAlreadyExistingUser extends BaseTestClass {
    private final String sTestName = this.getClass().getName();

    private User user;

    private boolean bCreated = false;

    @BeforeMethod
    public void setUpTest(ITestContext testContext) {
        LoggerUtils.log.debug("[SETUP TEST] " + sTestName);

        user = User.createNewUniqueUser("AlreadyExistingUser");
        RestApiUtils.postUser(user);
        user.setCreatedAt(RestApiUtils.getUser(user.getUsername()).getCreatedAt());
        bCreated = true;
        LoggerUtils.log.info(user);
    }

    @Test
    public void test() {
        LoggerUtils.log.debug("[START TEST] " + sTestName);

        int iExpectedStatusCode = 500;
        String sExpectedError = CommonStrings.getApiErrorInternalServerError();
        String sExpectedException = CommonStrings.getApiExceptionIllegalArgumentException();
        String sExpectedMessage = CommonStrings.getApiMessageAlreadyExistingUser(user.getUsername());
        String sExpectedPath = ApiCalls.createPostUserApiCallRelativePath();
        Date expectedDateTime = DateTimeUtils.getCurrentDateTime();

        ApiError error = RestApiUtils.postUserError(user);
        LoggerUtils.log.info("API Error: " + error);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(error.getStatus(), iExpectedStatusCode, "Wrong Status Code!");
        softAssert.assertEquals(error.getError(), sExpectedError, "Wrong Error!");
        softAssert.assertEquals(error.getException(), sExpectedException, "Wrong Exception!");
        softAssert.assertEquals(error.getMessage(), sExpectedMessage, "Wrong Message!");
        softAssert.assertEquals(error.getPath(), sExpectedPath, "Wrong Path!");
        softAssert.assertTrue(DateTimeUtils.compareDateTime(error.getTimestamp(), expectedDateTime, 180), "Wrong Timestamp! Expected: " + expectedDateTime + ". Actual: " + error.getTimestamp() + ".");
        softAssert.assertAll("Wrong Error Response Details!");

    }

    @AfterMethod(alwaysRun = true)
    public void tearDownTest(ITestResult testResult) {
        LoggerUtils.log.debug("[END TEST] " + sTestName);
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
