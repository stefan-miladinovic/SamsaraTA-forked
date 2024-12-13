package utils;

public class EmailConnection {

    // pop3 or imap -> For Gmail we will use IMAP secure (imaps)
    private static final String DEFAULT_PROTOCOL = "imaps";
    private static final String DEFAULT_HOST = "imap.gmail.com";
    private static final String DEFAULT_PORT = "993";
    private static final String CONNECTION_TIMEOUT = "10000";

    private final String protocol;
    private final String host;
    private final String port;
    private final String email;
    private final String password;

    public EmailConnection(String email, String password) {
        this.email = email;
        this.password = password;
        protocol = DEFAULT_PROTOCOL;
        host = DEFAULT_HOST;
        port = DEFAULT_PORT;

        // TODO: Setup Connection
    }

    public EmailConnection(String protocol, String host, String port, String email, String password) {
        this.protocol = protocol;
        this.host = host;
        this.port = port;
        this.email = email;
        this.password = password;

        // TODO: Setup Connection
    }
}
