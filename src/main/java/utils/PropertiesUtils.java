package utils;

import org.testng.Assert;

import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtils {

    private static final String sPropertiesPath = "common.properties";

    private static final Properties properties = loadPropertiesFile();

    public static Properties loadPropertiesFile(String sFilePath) {
        Properties properties = new Properties();
        InputStream inputStream = PropertiesUtils.class.getClassLoader().getResourceAsStream(sFilePath);
        try {
            //sFilePath = System.getProperty("user.dir") + "/src/main/resources/common.properties";
            //FileReader reader = new FileReader(sFilePath);
            //properties.load(reader);
            properties.load(inputStream);
        } catch (Exception e) {
            Assert.fail("Cannot load " + sFilePath + " file! Message: " + e.getMessage());
        }
        return properties;
    }

    public static Properties loadPropertiesFile() {
        return loadPropertiesFile(sPropertiesPath);
    }

    private static String getProperty(String sProperty) {
        String sResult = properties.getProperty(sProperty);
        Assert.assertNotNull(sResult, "Cannot find property '" + sProperty + "' in " + sPropertiesPath + " file!");
        return sResult;
    }

    public static String getBrowser() {
        return getProperty("browser").toLowerCase();
    }

    public static String getEnvironment() {
        return getProperty("environment").toLowerCase();
    }

    public static String getLocale() {
        return getProperty("locale").toLowerCase();
    }

    private static String getLocalBaseUrl() {
        return getProperty("localBaseUrl");
    }

    private static String getTestBaseUrl() {
        return getProperty("testBaseUrl");
    }

    private static String getStageBaseUrl() {
        return getProperty("stageBaseUrl");
    }

    private static String getProdBaseUrl() {
        return getProperty("prodBaseUrl");
    }

    public static String getBaseUrl() {
        String sEnvironment = getEnvironment().toLowerCase();
        return getBaseUrl(sEnvironment);
    }

    public static String getBaseUrl(String sEnvironment) {
        String sBaseUrl = null;
        switch (sEnvironment) {
            case "local" : {
                sBaseUrl = getLocalBaseUrl();
                break;
            }
            case "test" : {
                sBaseUrl = getTestBaseUrl();
                break;
            }
            case "stage" : {
                sBaseUrl = getStageBaseUrl();
                break;
            }
            case "prod" : {
                sBaseUrl = getProdBaseUrl();
                break;
            }
            default : {
                Assert.fail("Cannot get BaseUrl! Environment '" + sEnvironment + "' is not recognized!");
            }
        }
        return sBaseUrl;
    }

    public static boolean getRemote() {
        String sRemote = getProperty("remote");
        sRemote = sRemote.toLowerCase();
        if (!(sRemote.equals("true") || sRemote.equals("false"))) {
            Assert.fail("Cannot convert 'Remote' property value '" + sRemote + "' to boolean value!");
        }
        return Boolean.parseBoolean(sRemote);
    }

    public static boolean getHeadless() {
        String sHeadless = getProperty("headless");
        sHeadless = sHeadless.toLowerCase();
        if (!(sHeadless.equals("true") || sHeadless.equals("false"))) {
            Assert.fail("Cannot convert 'Headless' property value '" + sHeadless + "' to boolean value!");
        }
        return Boolean.parseBoolean(sHeadless);
    }

    public static String getUsername() {
        return getProperty("username");
    }

    public static String getPassword() {
        return getProperty("password");
    }

    public static String getDefaultPassword() {
        return getProperty("defaultPassword");
    }

    public static String getAdminUsername() {
        return getProperty("adminUsername");
    }

    public static String getAdminPassword() {
        return getProperty("adminPassword");
    }

    public static String getDriversFolder() {
        return getProperty("driversFolder");
    }

    public static String getDefaultSecretQuestion() {
        return getProperty("defaultSecretQuestion");
    }

    public static String getDefaultSecretAnswer() {
        return getProperty("defaultSecretAnswer");
    }

    public static String getScreenshotsFolder() {
        return getProperty("screenshotsFolder");
    }

    public static String getHubUrl() {
        return getProperty("hubUrl");
    }

    public static boolean getTakeScreenshots() {
        String sTakeScreenshots = getProperty("takeScreenshots");
        sTakeScreenshots = sTakeScreenshots.toLowerCase();
        if (!(sTakeScreenshots.equals("true") || sTakeScreenshots.equals("false"))) {
            Assert.fail("Cannot convert 'TakeScreenshots' property value '" + sTakeScreenshots + "' to boolean value!");
        }
        return Boolean.parseBoolean(sTakeScreenshots);
    }
}
