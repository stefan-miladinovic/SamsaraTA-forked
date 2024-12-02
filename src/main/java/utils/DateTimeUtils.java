package utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTimeUtils {

    public static void wait(int seconds) {
        LoggerUtils.log.trace("wait(" + seconds + ")");
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            LoggerUtils.log.warn("InterruptedException in Thread.sleep(" + seconds + "). Message: " + e.getMessage());
        }
    }

    public static Date getCurrentDateTime() {
        Calendar cal = Calendar.getInstance();
        return cal.getTime();
    }

    public static Date getDateTime(long milliSeconds) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(milliSeconds);
        return cal.getTime();
    }

    // "EEE dd/MMMM/yyyy HH:mm:ss
    // Thu 21/November/2024 12:31:24
    public static String getFormattedDateTime(Date date, String pattern) {
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(date);
    }

    public static String getFormattedCurrentDateTime(String pattern) {
        Date date = getCurrentDateTime();
        return getFormattedDateTime(date, pattern);
    }

    public static String getDateTimeStamp() {
        return getFormattedCurrentDateTime("yyMMddHHmmss");
    }

    public static Date getParsedDateTime(String sDateTime, String pattern) {
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        Date date = null;
        try {
             date = dateFormat.parse(sDateTime);
        } catch (ParseException e) {
            Assert.fail("Cannot parse date '" + sDateTime + "' using pattern '" + pattern + "'! Message: " + e.getMessage());
        }
        return date;
    }

    public static boolean compareDateTime(Date date1, Date date2, int threshold) {
        long diff = (date2.getTime() - date1.getTime())/1000;
        LoggerUtils.log.debug("Comparing dates (Date 1: " + date1 + ", Date 2: " + date2 + "). Difference: " + diff + " seconds. Threshold: " + threshold + ".");
        return Math.abs(diff) <= threshold;
    }
    //                var browserDate = new Date();
    //                var year = browserDate.getFullYear();
    //                var month = browserDate.getMonth();  // Zero based: 0 - January, 1 - February, ... , 11 - December)
    //                var date = browserDate.getDate();
    //                var hour = browserDate.getHours();
    //                var minute = browserDate.getMinutes();
    //                var second = browserDate.getSeconds();
    //                var offset = browserDate.getTimezoneOffset(); // Time difference in minutes from UTC

    public static long getBrowserTimeZoneOffset(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        long offset = (long) js.executeScript("var browserDateTime = new Date(); return browserDateTime.getTimezoneOffset();");
        return offset;
    }

    // Intl.DateTimeFormat().resolvedOptions().timeZone
    public static String getBrowserTimeZone(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        //String sBrowserTimeZone = (String) js.executeScript("return Intl.DateTimeFormat().resolvedOptions().timeZone;");
        String sBrowserDateTimeString = getBrowserDateTimeString(driver);
        LoggerUtils.log.info("Browser DateTime String: " + sBrowserDateTimeString);
        String sBrowserTimeZone = sBrowserDateTimeString.substring(sBrowserDateTimeString.lastIndexOf(" ") + 1);
        LoggerUtils.log.info("Browser TimeZone String: " + sBrowserTimeZone);
        return sBrowserTimeZone;
    }

    private static String getBrowserDateTimeString(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        // var browserDateTime = new Date().getTime();
        // return Intl.DateTimeFormat('en-US', { dateStyle: 'full', timeStyle: 'long' }).format(browserDateTime);

        // Thursday 21 November 2024 at 12:39:44 CET
        String sBrowserDateTime = (String) js.executeScript("var browserDateTime = new Date().getTime(); return Intl.DateTimeFormat('en-GB', { dateStyle: 'full', timeStyle: 'long' }).format(browserDateTime);");
        sBrowserDateTime = sBrowserDateTime.replace(" at ", " ");
        return sBrowserDateTime;
    }

    public static Date getBrowserDateTime(WebDriver driver) {
        String sBrowserDateTime = getBrowserDateTimeString(driver);
        // Thursday 5 November 2024 12:39:44 CET
        String sPattern = "EEEE d MMMM yyyy HH:mm:ss z";
        return getParsedDateTime(sBrowserDateTime, sPattern);
    }

}
