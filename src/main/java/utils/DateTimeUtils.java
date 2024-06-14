package utils;

public class DateTimeUtils {

    public static void wait(int seconds) {
        LoggerUtils.log.trace("wait(" + seconds + ")");
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            LoggerUtils.log.warn("InterruptedException in Thread.sleep(" + seconds + "). Message: " + e.getMessage());
        }
    }
}
