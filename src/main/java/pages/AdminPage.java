package pages;

import data.PageUrlPaths;
import data.Time;
import org.openqa.selenium.WebDriver;
import utils.LoggerUtils;

public class AdminPage extends CommonLoggedInPageClass {

    private final String ADMIN_PAGE_URL = getPageUrl(PageUrlPaths.ADMIN_PAGE);

    public AdminPage(WebDriver driver) {
        super(driver);
    }

    public AdminPage open() {
        return open(true);
    }

    public AdminPage open(boolean bVerify) {
        LoggerUtils.log.debug("open(" + ADMIN_PAGE_URL + ")");
        openUrl(ADMIN_PAGE_URL);
        if (bVerify) {
            verifyAdminPage();
        }
        return this;
    }

    public AdminPage verifyAdminPage() {
        LoggerUtils.log.debug("verifyAdminPage()");
        waitForUrlChange(ADMIN_PAGE_URL, Time.TIME_SHORTER);
        return this;
    }
}
