package gserver;

import org.apache.log4j.Logger;

/**
 * @author Alexander Kanunnikov
 */

public class UserSession {
    private static final Logger LOGGER = Logger.getLogger(UserSession.class);

    private String userName;
    private int userId;
    private int sessionId;

    public UserSession(int sessionId, String userName, int userId) {
        this.userName = userName;
        this.userId = userId;
        this.sessionId = sessionId;
    }

    public String getUserName() {
        return userName;
    }

    public int getUserId() {
        return userId;
    }

    public int getSessionId() {
        return sessionId;
    }

}