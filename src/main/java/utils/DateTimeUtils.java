package utils;

import java.text.DateFormat;
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

    public static boolean compareDateTime(Date date1, Date date2, int threshold) {
        long diff = (date2.getTime() - date1.getTime())/1000;
        LoggerUtils.log.debug("Comparing dates (Date 1: " + date1 + ", Date 2: " + date2 + "). Difference: " + diff + " seconds. Threshold: " + threshold + ".");
        return Math.abs(diff) <= threshold;
    }
}
