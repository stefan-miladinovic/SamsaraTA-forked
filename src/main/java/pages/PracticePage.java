package pages;

import data.PageUrlPaths;
import data.Time;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import utils.LoggerUtils;

public class PracticePage extends CommonLoggedInPageClass {

    private final String PRACTICE_PAGE_URL = getPageUrl(PageUrlPaths.PRACTICE_PAGE);

    private final String draggableImageLocatorString = "img#drag-image";  // "img.draggable-image"
    private final String dragAreaLocatorString = "div#drag-area";
    private final String dropAreaLocatorString = "div#drop-area";

    private final By draggableImageLocator = By.id("drag-image");

    @FindBy(id = "logo")
    private WebElement logoFrame;

    @FindBy(xpath="//a[@id='logo-image']/img")
    private WebElement samsaraLogo;

    @FindBy(xpath="//div[@id='useless-tooltip']/p[contains(@class, 'h4 heading')]")
    private WebElement uselessTooltipTitle;

    @FindBy(id = "useless-tooltip-text")
    private WebElement uselessTooltip;

    @FindBy(id = "drag-image")
    private WebElement draggableImage;

    @FindBy(xpath = "//div[@id='drag-area']/img[@id='drag-image']")
    private WebElement draggableImageInsideDragArea;

    @FindBy(xpath = "//div[@id='drop-area']/img[@id='drag-image']")
    private WebElement draggableImageInsideDropArea;

    @FindBy(id = "drag-area")
    private WebElement dragArea;

    @FindBy(id = "drop-area")
    private WebElement dropArea;

    @FindBy(id = "drag-and-drop-message")
    private WebElement dragAndDropMessage;

    public PracticePage(WebDriver driver) {
        super(driver);
    }

    public PracticePage open() {
        return open(true);
    }

    public PracticePage open(boolean bVerify) {
        LoggerUtils.log.debug("open(" + PRACTICE_PAGE_URL + ")");
        openUrl(PRACTICE_PAGE_URL);
        if (bVerify) {
            verifyPracticePage();
        }
        return this;
    }

    public PracticePage verifyPracticePage() {
        LoggerUtils.log.debug("verifyPracticePage()");
        waitForUrlChange(PRACTICE_PAGE_URL, Time.TIME_SHORTER);
        waitUntilPageIsReady(Time.TIME_SHORTER);
        return this;
    }

    public boolean isSamsaraLogoDisplayed() {
        LoggerUtils.log.debug("isSamsaraImageDisplayed()");
        switchToFrame(logoFrame);
        boolean bDisplayed = isWebElementDisplayed(samsaraLogo, Time.TIME_SHORT);
        switchToDefaultContent();
        return bDisplayed;
    }

    public WelcomePage clickSamsaraLogo() {
        LoggerUtils.log.debug("clickSamsaraLogo()");
        switchToFrame(logoFrame);
        clickOnWebElement(samsaraLogo);
        switchToDefaultContent();
        WelcomePage welcomePage = new WelcomePage(driver);
        return welcomePage.verifyWelcomePage();
    }

    public boolean isUselessTooltipDisplayed() {
        LoggerUtils.log.debug("isUselessTooltipDisplayed()");
        return isWebElementDisplayed(uselessTooltip);
    }

    public String getUselessTooltip() {
        LoggerUtils.log.debug("getUselessTooltip()");
        moveMouseToWebElement(uselessTooltipTitle);
        Assert.assertTrue(isUselessTooltipDisplayed(), "Useless Tooltip is NOT displayed!");
        return getTextFromWebElement(uselessTooltip);
    }

    public boolean isDragAndDropMessageDisplayed() {
        LoggerUtils.log.debug("isDragAndDropMessageDisplayed()");
        return isWebElementDisplayed(dragAndDropMessage);
    }

    public String getDragAndDropMessage() {
        LoggerUtils.log.debug("getDragAndDropMessage()");
        Assert.assertTrue(isDragAndDropMessageDisplayed(), "Drag and Drop Message is NOT displayed!");
        return getTextFromWebElement(dragAndDropMessage);
    }

    public boolean isDraggableImageDisplayed() {
        LoggerUtils.log.debug("isDraggableImageDisplayed()");
        return isWebElementDisplayed(draggableImage);
    }

    public boolean isDraggableImagePresentInDragArea() {
        LoggerUtils.log.debug("isDraggableImagePresentInDragArea()");
        //return isNestedWebElementDisplayed(dragArea, draggableImageLocator);
        return isWebElementDisplayed(draggableImageInsideDragArea);

    }

    public boolean isDraggableImagePresentInDropArea() {
        LoggerUtils.log.debug("isDraggableImagePresentInDropArea()");
        //return isNestedWebElementDisplayed(dropArea, draggableImageLocator);
        return isWebElementDisplayed(draggableImageInsideDropArea);
    }

    public PracticePage dragAndDropImage() {
        LoggerUtils.log.debug("dragAndDropImage()");
        Assert.assertTrue(isDraggableImageDisplayed(), "Draggable Image is NOT displayed on Practice Page!");
        //doDragAndDrop(draggableImage, dropArea);
        doDragAndDropJS(draggableImageLocatorString, dropAreaLocatorString);
        PracticePage practicePage = new PracticePage(driver);
        return practicePage.verifyPracticePage();
    }

}
