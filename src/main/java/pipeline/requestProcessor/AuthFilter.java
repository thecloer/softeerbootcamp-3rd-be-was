package pipeline.requestProcessor;

import exception.RedirectException;
import session.Session;
import session.SessionManager;
import util.http.HttpRequest;
import pipeline.Router;

import java.util.Set;

public class AuthFilter implements RequestProcessor {

    private static final Set<String> AUTHENTICATED = Set.of(
            "GET /user/profile.html",
            "GET /user/list.html"
    );
    private static final Set<String> UNAUTHENTICATED = Set.of(
            "GET /user/login.html",
            "GET /user/form.html"
    );

    @Override
    public HttpRequest process(HttpRequest request) {
        String routeKey = Router.getRouteKey(request);
        Session session = extractSession(request);

        session = checkAndRenewSession(session);

        if (AUTHENTICATED.contains(routeKey) && session == null)
            throw new RedirectException("/user/login.html");

        if (UNAUTHENTICATED.contains(routeKey) && session != null)
            throw new RedirectException("/");

        request.setSession(session);

        return request;
    }

    private static Session extractSession(HttpRequest request) {
        String cookie = request.getProperty("cookie");
        String sessionId = extractCookie(cookie, SessionManager.COOKIE_KEY);
        return SessionManager.getSession(sessionId);
    }

    private static String extractCookie(String cookieString, String key) {
        if(cookieString == null)
            return "";

        String prefix = key + "=";
        int startIndex = cookieString.indexOf(prefix);
        if (startIndex == -1)
            return "";
        startIndex += prefix.length();

        int endIndex = cookieString.indexOf(";", startIndex);
        if (endIndex == -1)
            endIndex = cookieString.length();

        return cookieString.substring(startIndex, endIndex);
    }

    private static Session checkAndRenewSession(Session session) {
        if (session == null)
            return null;

        if (session.isExpired()) {
            SessionManager.distroySession(session.getSessionId());
            return null;
        }

        session.renew();
        return session;
    }
}
