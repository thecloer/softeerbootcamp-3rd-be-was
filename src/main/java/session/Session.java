package session;

import java.util.HashMap;
import java.util.Map;

public class Session {

    private final String sessionId;
    private final Map<String, String> attributes = new HashMap<>();
    private Long lastAccessedTime;

    public Session(String sessionId) {
        this.sessionId = sessionId;
        renew();
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setAttribute(String key, String value) {
        attributes.put(key, value);
    }

    public String getAttribute(String key) {
        return attributes.getOrDefault(key, null); // TODO: default 값 생각
    }

    public void renew() {
        lastAccessedTime = System.currentTimeMillis();
    }

    public Boolean isExpired() {
        return System.currentTimeMillis() - lastAccessedTime > SessionManager.SESSION_TTL;
    }

    @Override
    public String toString() {
        return "Session { " +
                "sessionId='" + sessionId + '\'' +
                ", attributes=" + attributes +
                ", lastAccessedTime=" + lastAccessedTime +
                " }";
    }
}
