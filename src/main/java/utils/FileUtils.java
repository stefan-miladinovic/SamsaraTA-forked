package utils;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtils {

    public static String downloadFile(WebDriver driver, String link) {

        String sFilesFolder = PropertiesUtils.getFilesFolder();
        String sFileName = null;
        String sFilePath = null;
        HttpURLConnection connection = null;


        // a) Direct Link with real file name: http://application.com/download/file.txt
        // b) Indirect Link without real file name: http://application.com/download?id=1234

        try {
            URL url = new URL(link);
            LoggerUtils.log.info("LINK: " + link);
            connection = (HttpURLConnection) url.openConnection();
            String sCookies = WebDriverUtils.getCookies(driver);
            LoggerUtils.log.info("COOKIES: " + sCookies);
            connection.addRequestProperty("Cookie", sCookies); // "cookie1name=cookie1value;cookie2name=cookie2value;....
            int iResponseCode = connection.getResponseCode();
            if(iResponseCode == HttpURLConnection.HTTP_OK) {
                String sDisposition = connection.getHeaderField("Content-Disposition");
                LoggerUtils.log.info("CONTENT DISPOSITION: " + sDisposition);
                if(sDisposition != null) {
                    int index = sDisposition.indexOf("filename=");
                    if(index > 0) {
                        sFileName = sDisposition.substring(index + 10, sDisposition.length() - 1);
                    }
                } else {
                    sFileName = link.substring(link.lastIndexOf("/") + 1);
                }

                LoggerUtils.log.info("FILE NAME: " + sFileName);
                sFilePath = sFilesFolder + sFileName;


                InputStream inputStream = connection.getInputStream();

                Files.createDirectories(Paths.get(sFilesFolder));

                FileOutputStream outputStream = new FileOutputStream(sFilePath);

                int bytesRead = -1;
                byte[] buffer = new byte[4096];
                while((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }

                outputStream.close();
                inputStream.close();
                LoggerUtils.log.info("FILE DOWNLOADED: " + sFilePath);

            } else {
                Assert.fail("File from link '" + link + "' cannot be downloaded. Response Code: " + iResponseCode);
            }

        } catch (IOException e) {
            Assert.fail("File from link '" + link + "' cannot be downloaded. Message: " + e.getMessage());
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return sFilePath;
    }

    public static String readTextFile(String sFilePath) {
        LoggerUtils.log.debug("readTextFile(" + sFilePath + ")");
        String sText = null;
        try {
            Path path = Paths.get(sFilePath);
            sText = Files.readString(path, StandardCharsets.UTF_8);
        } catch (IOException e) {
            Assert.fail("Cannot read file '" + sFilePath + "'. Message: " + e.getMessage());
        }
        return sText;
    }

    public static void deleteFile(String sFilePath) {
        LoggerUtils.log.debug("deleteFile(" + sFilePath + ")");
        try {
            Path path = Paths.get(sFilePath);
            Files.deleteIfExists(path);
        } catch (IOException e) {
            Assert.fail("Cannot delete file '" + sFilePath + "'. Message: " + e.getMessage());
        }
    }
}
