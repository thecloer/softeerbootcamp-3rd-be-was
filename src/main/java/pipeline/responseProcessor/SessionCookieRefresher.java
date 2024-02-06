package pipeline.responseProcessor;

import session.Session;
import session.SessionManager;
import util.http.HttpRequest;
import util.http.HttpResponse;

public class SessionCookieRefresher implements ResponseProcessor {
    @Override
    public HttpResponse process(HttpRequest request, HttpResponse response) {
        if (!request.isLoggedIn())
            return response;

        Session session = request.getSession();
        String cookie = SessionManager.toCookieString(session);
        response.addCookie(cookie);

        return response;
    }
}
