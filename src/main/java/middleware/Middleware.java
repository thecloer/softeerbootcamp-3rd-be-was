package middleware;

import util.http.HttpRequest;

public interface Middleware {
    HttpRequest process(HttpRequest request);
}
