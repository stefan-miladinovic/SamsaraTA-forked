package utils;

import jakarta.mail.*;
import jakarta.mail.search.*;
import objects.EmailMessage;
import org.eclipse.angus.mail.imap.YoungerTerm;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.Properties;

public class EmailConnection {

    // pop3 or imap or imaps -> For Gmail we will use IMAP secure (imaps)
    private static final String DEFAULT_PROTOCOL = "imaps";
    private static final String DEFAULT_HOST = "imap.gmail.com";
    private static final String DEFAULT_PORT = "993";
    private static final String CONNECTION_TIMEOUT = "10000";

    private final String protocol;
    private final String host;
    private final String port;
    private final String email;
    private final String password;

    private Store store;
    private Folder inbox;
    private Folder trash;
    private Folder spam;

    public enum FolderStatus {
        READ_ONLY, READ_WRITE
    }

    private EmailConnection(String protocol, String host, String port, String email, String password) {
        this.protocol = protocol;
        this.host = host;
        this.port = port;
        this.email = email;
        this.password = password;
        setupConnection();
        try {
            inbox = store.getFolder("INBOX");
            trash = store.getFolder("[Gmail]/Trash");
            spam = store.getFolder("[Gmail]/Spam");
            openFolder(inbox, FolderStatus.READ_WRITE);
        } catch (MessagingException e) {
            Assert.fail("Cannot create email connection '" + email + "'. Message: " + e.getMessage());
        }
    }

    private void setupConnection() {
        LoggerUtils.log.trace("setupConnection()");
        Properties properties = new Properties();
        properties.setProperty("mail." + protocol + ".host", host);
        properties.setProperty("mail." + protocol + ".port", port);
        properties.setProperty("mail." + protocol + ".connectiontimeout", CONNECTION_TIMEOUT);
        properties.setProperty("mail." + protocol + ".timeout", CONNECTION_TIMEOUT);
        Session session = Session.getInstance(properties);
        try {
            store = session.getStore(protocol);
            store.connect(email, password);
        } catch (MessagingException e) {
            Assert.fail("Cannot setup connection to mailbox. Message: " + e.getMessage());
        }
    }

    public static EmailConnection createEmailConnection(String sEmail, String sPassword) {
        String sRootEmail = extractRootEmailAccount(sEmail);
        LoggerUtils.log.debug("createEmailConnection(" + sRootEmail + ", " + sPassword + ")");
        return new EmailConnection(DEFAULT_PROTOCOL, DEFAULT_HOST, DEFAULT_PORT, sRootEmail, sPassword);
    }

    public static EmailConnection createEmailConnection(String sProtocol, String sHost, int iPort, String sEmail, String sPassword) {
        String sRootEmail = extractRootEmailAccount(sEmail);
        LoggerUtils.log.debug("createEmailConnection(" + sProtocol + ", " + sHost + ", " + iPort + ", " + sRootEmail + ", " + sPassword + ")");
        return new EmailConnection(sProtocol, sHost, Integer.toString(iPort), sRootEmail, sPassword);
    }

    public void openFolder(Folder folder, FolderStatus status) {
        LoggerUtils.log.trace("openFolder(" + folder.getName() + ", " + status.toString() + ")");
        try {
            if (!folder.isOpen()) {
                switch (status) {
                    case READ_ONLY:
                        folder.open(Folder.READ_ONLY);
                        break;
                    case READ_WRITE:
                        folder.open(Folder.READ_WRITE);
                        break;
                }
            }
        } catch (MessagingException e) {
            Assert.fail("Cannot open folder '" + folder.getName() + "'. Message: " + e.getMessage());
        }
    }

    public void closeFolder(Folder folder) {
        if (folder != null) {
            try {
                LoggerUtils.log.trace("closeFolder(" + folder.getName() + ")");
                if (folder.isOpen()) {
                    // true - Any message deleted in the opened folder will be completely deleted form the server
                    folder.close(true);
                }
            } catch (MessagingException e) {
                Assert.fail("Cannot close folder '" + folder.getName() + "'. Message: " + e.getMessage());
            }
        }
    }

    public void closeEmailConnection() {
        LoggerUtils.log.trace("closeEmailConnection(" + email + ")");
        if (store != null) {
            try {
                if (store.isConnected()) {
                    closeFolder(inbox);
                    store.close();
                }
            } catch (MessagingException e) {
                Assert.fail("Cannot close email connection '" + email + "'. Message: " + e.getMessage());
            }
        }
    }

    private static String extractRootEmailAccount(String email) {
        LoggerUtils.log.trace("extractRootEmailAccount(" + email + ")");
        String emailAccount = email;
        int a = emailAccount.indexOf("+");
        int b = emailAccount.indexOf("@");
        if(a != -1) {
            String s = emailAccount.substring(a, b);
            emailAccount = emailAccount.replace(s, "");
        }
        return emailAccount;
    }

    private Message[] getAllMessages() {
        Message[] messages = null;
        try {
            messages = inbox.getMessages();
        } catch (MessagingException e) {
            Assert.fail("Cannot get messages from email account '" + email + "'. Message: " + e.getMessage());
        }
        return messages;
    }

    public ArrayList<EmailMessage> getMessages(String sender, String recipient, String subjectPart, String bodyPart, Integer withinInterval) {
        Message[] foundMessages = null;
        ArrayList<EmailMessage> messages = null;
        SearchTerm searchTerm = null;

        try {
            if(sender != null) {
                searchTerm = new FromStringTerm(sender);
            }

            if(recipient != null) {
                SearchTerm term = new RecipientStringTerm(Message.RecipientType.TO, recipient);
                if(searchTerm != null) {
                    searchTerm = new AndTerm(term, searchTerm);
                } else {
                    searchTerm = term;
                }
            }

            if(subjectPart != null) {
                SearchTerm term = new SubjectTerm(subjectPart);
                if(searchTerm != null) {
                    searchTerm = new AndTerm(term, searchTerm);
                } else {
                    searchTerm = term;
                }
            }

            if(bodyPart != null) {
                SearchTerm term = new BodyTerm(bodyPart);
                if(searchTerm != null) {
                    searchTerm = new AndTerm(term, searchTerm);
                } else {
                    searchTerm = term;
                }
            }

            if(withinInterval != null) {
                SearchTerm term = new YoungerTerm(withinInterval * 60);
                if(searchTerm != null) {
                    searchTerm = new AndTerm(term, searchTerm);
                } else {
                    searchTerm = term;
                }
            }

            foundMessages = inbox.search(searchTerm, getAllMessages());

            if(foundMessages.length != 0) {
                messages = new ArrayList<>();
                for(Message foundMessage : foundMessages) {
                    messages.add(new EmailMessage(foundMessage));
                }
            }
        } catch (MessagingException e) {
            Assert.fail("Cannot find email messages. Message: " + e.getMessage());
        }
        return messages;
    }

    public EmailMessage getLastMessage(String sender, String recipient, String subjectPart, String bodyPart, Integer withinInterval) {
        LoggerUtils.log.debug("getLastMessage(" + sender + ", " + recipient + ", " + subjectPart + ", " + bodyPart + ", " + withinInterval + ")");
        ArrayList<EmailMessage> messages = getMessages(sender, recipient, subjectPart, bodyPart, withinInterval);
        return messages.get(messages.size() - 1);
    }
}
