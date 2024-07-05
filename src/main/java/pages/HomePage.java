package pages;

import data.PageUrlPaths;
import data.Time;
import org.openqa.selenium.WebDriver;
import utils.LoggerUtils;

public class HomePage extends CommonLoggedInPageClass {

    private final String HOME_PAGE_URL = getPageUrl(PageUrlPaths.HOME_PAGE);
    
    public HomePage(WebDriver driver) {
        super(driver);
    }

    public HomePage open() {
        return open(true);
    }

    public HomePage open(boolean bVerify) {
        LoggerUtils.log.debug("open(" + HOME_PAGE_URL + ")");
        openUrl(HOME_PAGE_URL);
        if (bVerify) {
            verifyHomePage();
        }
        return this;
    }

    public HomePage verifyHomePage() {
        LoggerUtils.log.debug("verifyHomePage()");
        waitForUrlChange(HOME_PAGE_URL, Time.TIME_SHORTER);
        return this;
    }
}
