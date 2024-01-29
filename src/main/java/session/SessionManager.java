package session;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {

    public static final String COOKIE_KEY = "sid";
    public static final Long SESSION_TTL = 3600 * 1000L;
    private static final Long DEFAULT_COOKIE_TTL = 3600L;

    // TODO: 일정 시간 마다 만료된 세션을 삭제 처리 해야한다.
    // -> 세션 GC 스레드 생성, (SESSION_TTL / 2) 마다 스레드 실행하면 세션 TTL 보장 가능 but 성능 저하. SESSION_TTL * k 마다 실행해도 될듯
    private static final ConcurrentHashMap<String, Session> storage = new ConcurrentHashMap<>();

    public static Session createSession() {
        String sessionId = UUID.randomUUID().toString();
        Session session = new Session(sessionId);
        storage.put(sessionId, session);
        return session;
    }

    public static Session distroySession(String sessionId) {
        return storage.remove(sessionId);
    }

    public static Session getSession(String sessionId) {
        return storage.get(sessionId);
    }

    public static void setSession(Session session) {
        storage.put(session.getSessionId(), session);
    }

    public static String toCookieString(Session session) {
        return toCookieString(session, DEFAULT_COOKIE_TTL);
    }
    public static String toCookieString(Session session, Long ttl) {
        return COOKIE_KEY + "=" + session.getSessionId() + "; HttpOnly; Path=/; Max-Age=" + ttl;
    }
}
