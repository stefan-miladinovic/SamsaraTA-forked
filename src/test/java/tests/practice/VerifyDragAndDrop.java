package tests.practice;

import data.Groups;
import data.Time;
import objects.User;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.LoginPage;
import pages.PracticePage;
import pages.WelcomePage;
import tests.BaseTestClass;
import utils.DateTimeUtils;
import utils.LoggerUtils;
import utils.RestApiUtils;


@Test(groups = {Groups.REGRESSION, Groups.MOUSE})
public class VerifyDragAndDrop extends BaseTestClass {

    private final String sTestName = this.getClass().getName();
    private WebDriver driver;

    private User user;

    private boolean bCreated = false;

    @BeforeMethod
    public void setUpTest(ITestContext testContext) {
        LoggerUtils.log.debug("[SETUP TEST] " + sTestName);
        driver = setUpDriver();

        user = User.createNewUniqueUser("DragAndDrop");
        RestApiUtils.postUser(user);
        user.setCreatedAt(RestApiUtils.getUser(user.getUsername()).getCreatedAt());
        bCreated = true;
        LoggerUtils.log.info(user);
    }

    @Test
    public void test() {
        LoggerUtils.log.debug("[START TEST] " + sTestName);

        LoginPage loginPage = new LoginPage(driver).open();
        DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

        WelcomePage welcomePage = loginPage.login(user);
        DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

        PracticePage practicePage = welcomePage.clickPracticeTab();
        DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(practicePage.isDraggableImagePresentInDragArea(), "Draggable Image is NOT present in Drag Area before Drag & Drop action!");
        softAssert.assertFalse(practicePage.isDraggableImagePresentInDropArea(), "Draggable Image should NOT be present in Drop Area before Drag & Drop action!");
        softAssert.assertFalse(practicePage.isDragAndDropMessageDisplayed(), "Drag and Drop Message should NOT be displayed before Drag & Drop action!");
        softAssert.assertAll();

        practicePage.dragAndDropImage();

        softAssert.assertFalse(practicePage.isDraggableImagePresentInDragArea(), "Draggable Image should NOT be present in Drag Area after Drag & Drop action!");
        softAssert.assertTrue(practicePage.isDraggableImagePresentInDropArea(), "Draggable Image is NOT present in Drop Area after Drag & Drop action!");
        softAssert.assertTrue(practicePage.isDragAndDropMessageDisplayed(), "Drag and Drop Message is NOT displayed after Drag & Drop action!");
        softAssert.assertAll();
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
