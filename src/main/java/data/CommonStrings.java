package data;

import org.testng.Assert;
import utils.PropertiesUtils;

import java.util.Properties;

public final class CommonStrings {

    private static final String sLocaleFile = "locale_" + PropertiesUtils.getLocale() + ".loc";
    private static final String sLocalePath = "\\locale\\" + sLocaleFile;

    private static final Properties locale = PropertiesUtils.loadPropertiesFile(sLocalePath);

    private static String getLocaleString(String sLocaleStringName) {
        String sText = locale.getProperty(sLocaleStringName);
        Assert.assertNotNull(sText, "String '" + sLocaleStringName + "' doesn't exist in file " + sLocaleFile + "!");
        return sText;
    }


    // Login Page
    public static String getLoginErrorMessage() {
        return getLocaleString("LOGIN_ERROR_MESSAGE");
    }

    public static String getLogoutSuccessMessage() {
        return getLocaleString("LOGOUT_SUCCESS_MESSAGE");
    }

    public static String getRegisterSuccessMessage() {
        return getLocaleString("REGISTER_SUCCESS_MESSAGE");
    }

    // Welcome Page
    public static String getWelcomePageTitle() {
        return getLocaleString("WELCOME_PAGE_TITLE");
    }

    // DeleteUser DialogBox
    public static String getDeleteUserMessage(String sUsername, String sDisplayName) {
        return getLocaleString("DELETE_USER_MESSAGE").replace("%USERNAME%", sUsername).replace("%FULL_NAME%", sDisplayName);
    }
}
