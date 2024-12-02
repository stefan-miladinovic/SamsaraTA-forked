package pages;

import data.PageUrlPaths;
import data.Time;
import org.openqa.selenium.WebDriver;
import utils.LoggerUtils;

public class ProfilePage extends CommonLoggedInPageClass {

    // Page Url Path
    private final String PROFILE_PAGE_URL = getPageUrl(PageUrlPaths.PROFILE_PAGE);

    public ProfilePage(WebDriver driver) {
        super(driver);
    }

    public ProfilePage open() {
        return open(true);
    }

    public ProfilePage open(boolean bVerify) {
        LoggerUtils.log.debug("open(" + PROFILE_PAGE_URL + ")");
        openUrl(PROFILE_PAGE_URL);
        if (bVerify) {
            verifyProfilePage();
        }
        return this;
    }

    public ProfilePage verifyProfilePage() {
        LoggerUtils.log.debug("verifyProfilePage()");
        waitForUrlChange(PROFILE_PAGE_URL, Time.TIME_SHORTER);
        waitUntilPageIsReady(Time.TIME_SHORT);
        return this;
    }
}
