package session;

import java.util.HashMap;
import java.util.Map;

public class Session {

    private final String sessionId;
    private final Map<String, String> attributes = new HashMap<>();
    private Long lastAccessedTime;

    public Session(String sessionId) {
        this.sessionId = sessionId;
        setLastAccessedTime();
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

    public void setLastAccessedTime() {
        lastAccessedTime = System.currentTimeMillis();
    }

    public Boolean isExpired(Long ttl) {
        return System.currentTimeMillis() - lastAccessedTime > ttl;
    }
}
