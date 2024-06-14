package pages;

import data.PageUrlPaths;
import data.Time;
import org.openqa.selenium.WebDriver;
import utils.LoggerUtils;

public class WelcomePage extends CommonLoggedInPageClass {

    private final String WELCOME_PAGE_URL = getPageUrl(PageUrlPaths.WELCOME_PAGE);

    public WelcomePage(WebDriver driver) {
        super(driver);
    }

    public WelcomePage open() {
        return open(true);

    }

    public WelcomePage open(boolean bVerify) {
        LoggerUtils.log.debug("open(" + WELCOME_PAGE_URL + ")");
        openUrl(WELCOME_PAGE_URL);
        if(bVerify) {
            verifyWelcomePage();
        }
        return this;
    }

    public WelcomePage verifyWelcomePage() {
        LoggerUtils.log.debug("verifyWelcomePage()");
        waitForUrlChangeToExactUrl(WELCOME_PAGE_URL, Time.TIME_SHORTER);
        // wait for dom structure to be completed
        // wait/check the presence of specific/problematic web element
        return this;
    }
}
