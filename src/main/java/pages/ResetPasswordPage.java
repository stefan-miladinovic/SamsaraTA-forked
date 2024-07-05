package pages;

import data.PageUrlPaths;
import data.Time;
import org.openqa.selenium.WebDriver;
import utils.LoggerUtils;

public class ResetPasswordPage extends CommonLoggedOutPageClass {

    private final String RESET_PASSWORD_PAGE_URL = getPageUrl(PageUrlPaths.RESET_PASSWORD_PAGE);
    
    public ResetPasswordPage(WebDriver driver) {
        super(driver);
    }    

    public ResetPasswordPage open() {
        return open(true);
    }

    public ResetPasswordPage open(boolean bVerify) {
        LoggerUtils.log.debug("open(" + RESET_PASSWORD_PAGE_URL + ")");
        openUrl(RESET_PASSWORD_PAGE_URL);
        if (bVerify) {
            verifyResetPasswordPage();
        }
        return this;
    }

    public ResetPasswordPage verifyResetPasswordPage() {
        LoggerUtils.log.debug("verifyResetPasswordPage()");
        waitForUrlChange(RESET_PASSWORD_PAGE_URL, Time.TIME_SHORTER);
        return this;
    }
}
