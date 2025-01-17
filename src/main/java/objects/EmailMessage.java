package objects;

import jakarta.mail.*;
import jakarta.mail.internet.MimeBodyPart;
import org.testng.Assert;
import utils.DateTimeUtils;
import utils.LoggerUtils;
import utils.PropertiesUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class EmailMessage {

    private Message message;
    private String body;
    private String subject;
    private LinkedHashMap<String, String> messageHeaders = new LinkedHashMap<>();
    private Date receivedDateTime;
    private Date sentDateTime;
    private ArrayList<Part> attachments = new ArrayList<>();

    public EmailMessage(Message m) {
        this.message = m;
        setSubject();
        setMessageHeaders();
        setReceivedDateTime();
        setSentDateTime();
        setBody();
        setAttachments();
    }

    private void setSubject() {
        LoggerUtils.log.trace("[EMAIL MESSAGE] setSubject()");
        try {
            subject = message.getSubject();
        } catch (MessagingException e) {
            Assert.fail("Error getting message subject! Message: " + e.getMessage());
        }
    }

    private void setBody() {
        LoggerUtils.log.trace("[EMAIL MESSAGE] setBody()");
        try {
            body = getTextFromPart(message);
        } catch (MessagingException | IOException e) {
            Assert.fail("Error getting message body! Message: " + e.getMessage());
        }
    }

    private String getTextFromPart(Part part) throws MessagingException, IOException {
        if(part.isMimeType("text/*")) {
            String content = (String) part.getContent();
            return content;
        } else if (part.isMimeType("multipart/*")) {
            Multipart multiPart = (Multipart) part.getContent();
            for(int i = 0; i < multiPart.getCount(); i++) {
                String content = getTextFromPart(multiPart.getBodyPart(i));
                if (content != null) {
                    return content;
                }
            }
        }
        return null;
    }

    private void setReceivedDateTime() {
        LoggerUtils.log.trace("[EMAIL MESSAGE] setReceivedDateTime()");
        try {
            sentDateTime = message.getReceivedDate();
        } catch (MessagingException e) {
            Assert.fail("Error getting message received date! Message: " + e.getMessage());
        }
    }

    private void setSentDateTime() {
        LoggerUtils.log.trace("[EMAIL MESSAGE] setSentDateTime()");
        try {
            sentDateTime = message.getSentDate();
        } catch (MessagingException e) {
            Assert.fail("Error getting message sent date! Message: " + e.getMessage());
        }
    }

    private void setMessageHeaders() {
        LoggerUtils.log.trace("[EMAIL MESSAGE] setMessageHeaders()");
        try {
            Enumeration<Header> headers = message.getAllHeaders();
            while (headers.hasMoreElements()) {
                Header header = headers.nextElement();
                messageHeaders.put(header.getName(), header.getValue());
            }
        } catch (MessagingException e) {
            Assert.fail("Error getting message headers! Message: " + e.getMessage());
        }
    }

    private Multipart getMultiPart(Part part) {
        LoggerUtils.log.trace("[EMAIL MESSAGE] getMultiPart()");
        Multipart multiPart = null;
        try {
            if(part.getContentType().contains("multipart")) {
                multiPart = (Multipart) part.getContent();
            }
        } catch (MessagingException | IOException e){
            Assert.fail("Cannot get Multipart. Message: " + e.getMessage());
        }
        return multiPart;
    }

    private void setAttachments() {
        LoggerUtils.log.trace("[EMAIL MESSAGE] setAttachments()");
        try {
            Multipart multiPart = getMultiPart(message);
            if(multiPart != null) {
                for(int i = 0; i < multiPart.getCount(); i++) {
                    MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(i);
                    String disposition = part.getDisposition();
                    if(disposition != null) {
                        if(disposition.equalsIgnoreCase(Part.ATTACHMENT)) {
                            attachments.add(part);
                        }
                    }
                }
            }
        } catch (MessagingException e) {
            Assert.fail("Error getting message attachments! Message: " + e.getMessage());
        }
    }

    public String getSubject() {
        LoggerUtils.log.debug("[EMAIL MESSAGE] getSubject()");
        return subject;
    }

    public Date getReceivedDateTime() {
        LoggerUtils.log.debug("[EMAIL MESSAGE] getReceivedDateTime()");
        String sReceivedHeader = getHeaderText("Received");
        String sReceivedDateString = sReceivedHeader.substring(sReceivedHeader.lastIndexOf(';') + 1).replace("\r", "").replace("\n", "").trim();
        String sPattern = "EEE, dd MMM yyyy HH:mm:ss Z (z)";
        return DateTimeUtils.getParsedDateTime(sReceivedDateString, sPattern);
    }

    public Date getSentDateTime() {
        LoggerUtils.log.debug("[EMAIL MESSAGE] getSentDateTime()");
        return sentDateTime;
    }

    private boolean isHeaderPresent(String header) {
        LoggerUtils.log.trace("[EMAIL MESSAGE] isHeaderPresent(" + header + ")");
        return messageHeaders.containsKey(header);
    }

    private String getHeaderText(String header) {
        LoggerUtils.log.trace("[EMAIL MESSAGE] getHeaderText(" + header + ")");
        Assert.assertTrue(isHeaderPresent(header), "Header '" + header + "' is NOT present in message!");
        return messageHeaders.get(header);
    }
    public HashMap<String, String> getAllMessageHeaders() {
        return messageHeaders;
    }

    public String getFromAddress() {
        LoggerUtils.log.debug("[EMAIL MESSAGE] getFromAddress()");
        return getHeaderText("From");
    }

    public String getDeliveredToAddress() {
        LoggerUtils.log.debug("[EMAIL MESSAGE] getDeliveredToAddress()");
        return getHeaderText("Delivered-To");
    }

    public List<String> getToAddresses() {
        LoggerUtils.log.debug("[EMAIL MESSAGE] getToAddresses()");
        String sToAddresses = getHeaderText("To");
        String[] arrayToAddresses = sToAddresses.split(",");
        return Arrays.asList(arrayToAddresses);
    }

    public List<String> getCcAddresses() {
        LoggerUtils.log.debug("[EMAIL MESSAGE] getCcAddresses()");
        String sCcAddresses = getHeaderText("Cc");
        String[] arrayCcAddresses = sCcAddresses.split(",");
        return Arrays.asList(arrayCcAddresses);
    }

    public String getBody() {
        LoggerUtils.log.debug("[EMAIL MESSAGE] getBody()");
        return body;
    }

    public int getAttachmentsCount() {
        LoggerUtils.log.debug("[EMAIL MESSAGE] getAttachmentsCount()");
        return attachments.size();
    }

    public boolean isAttachmentPresent(String sAttachmentName) {
        LoggerUtils.log.debug("[EMAIL MESSAGE] isAttachmentPresent(" + sAttachmentName + ")");
        boolean bFound = false;
        try {
            for(Part part : attachments) {
                String attachmentName = part.getFileName();
                if(attachmentName.equals(sAttachmentName)) {
                    bFound = true;
                    break;
                }
            }
        } catch (MessagingException e) {
            Assert.fail("Cannot check attachment '" + sAttachmentName + "'! Message: " + e.getMessage());
        }
        return bFound;
    }

    public String downloadAttachment(String sAttachmentName) {
        LoggerUtils.log.debug("[EMAIL MESSAGE] downloadAttachment(" + sAttachmentName + ")");

        String sFilesFolder = PropertiesUtils.getFilesFolder();
        String sFileName = sAttachmentName.replace(" ", "_").replace("-", "_");
        String sFilePath = null;

        boolean bFound = false;

        try {
            for(Part part : attachments) {
                String attachmentName = part.getFileName();
                if(attachmentName.equals(sAttachmentName)) {
                    bFound = true;

                    InputStream inputStream = part.getInputStream();

                    Files.createDirectories(Paths.get(sFilesFolder));
                    sFilePath = sFilesFolder + sFileName;

                    FileOutputStream outputStream = new FileOutputStream(sFilePath);

                    int bytesRead = -1;
                    byte[] buffer = new byte[4096];
                    while((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }

                    outputStream.close();
                    inputStream.close();
                    LoggerUtils.log.info("ATTACHMENT DOWNLOADED: " + sFilePath);
                    break;
                }
            }
            if(!bFound) {
                Assert.fail("Attachment '" + sAttachmentName + "' is NOT found!");
            }

        } catch (MessagingException | IOException e) {
            Assert.fail("Cannot download attachment '" + sAttachmentName + "'! Message: " + e.getMessage());
        }
        return sFilePath;
    }

}
