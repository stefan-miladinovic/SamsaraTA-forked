package pages;

import data.PageUrlPaths;
import data.Time;
import org.openqa.selenium.WebDriver;
import utils.LoggerUtils;

public class RegisterPage extends CommonLoggedOutPageClass {

    private final String REGISTER_PAGE_URL = getPageUrl(PageUrlPaths.REGISTER_PAGE);

    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    public RegisterPage open() {
        return open(true);
    }

    public RegisterPage open(boolean bVerify) {
        LoggerUtils.log.debug("open(" + REGISTER_PAGE_URL + ")");
        openUrl(REGISTER_PAGE_URL);
        if (bVerify) {
            verifyRegisterPage();
        }
        return this;
    }

    public RegisterPage verifyRegisterPage() {
        LoggerUtils.log.debug("verifyRegisterPage()");
        waitForUrlChange(REGISTER_PAGE_URL, Time.TIME_SHORTER);
        return this;
    }
}
