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

    // Practice Page
    public static String getUselessTooltipText() {
        return getLocaleString("USELESS_TOOLTIP_TEXT");
    }

    // Profile Page
    public static String getProfileImageSavedMessage() {
        return getLocaleString("PROFILE_IMAGE_SAVED");
    }

    // DeleteUser DialogBox
    public static String getDeleteUserMessage(String sUsername, String sDisplayName) {
        return getLocaleString("DELETE_USER_MESSAGE").replace("%USERNAME%", sUsername).replace("%FULL_NAME%", sDisplayName);
    }

    // API Errors
    public static String getApiErrorInternalServerError() {
        return getLocaleString("API_ERROR_INTERNAL_SERVER_ERROR");
    }
    public static String getApiErrorForbidden() {
        return getLocaleString("API_ERROR_FORBIDDEN");
    }

    // API Exceptions
    public static String getApiExceptionIllegalArgumentException() {
        return getLocaleString("API_EXCEPTION_ILLEGAL_ARGUMENT_EXCEPTION");
    }

    // API Messages
    public static String getApiMessageNonExistingUser(String sUsername) {
        return getLocaleString("API_MESSAGE_NON_EXISTING_USER").replace("%USERNAME%", sUsername);
    }
    public static String getApiMessageAlreadyExistingUser(String sUsername) {
        return getLocaleString("API_MESSAGE_ALREADY_EXISTING_USER").replace("%USERNAME%", sUsername);
    }
    public static String getApiMessageAccessDenied() {
        return getLocaleString("API_MESSAGE_ACCESS_DENIED");
    }
    public static String getApiMessageEmailNotSpecified() {
        return getLocaleString("API_MESSAGE_EMAIL_NOT_SPECIFIED");
    }
}
