package webserver;

import middleware.Middleware;
import util.http.HttpMessage;
import util.http.HttpRequest;
import exception.HttpBaseException;

import java.util.ArrayList;
import java.util.List;

public class RequestPipeline {

    private final List<Middleware> middlewares = new ArrayList<>();

    public HttpMessage process(HttpRequest request) {
        try {
            request = processMiddlewares(request);
            return Router.route(request);
        } catch (HttpBaseException errorResponse) {
            return errorResponse;
        }
    }

    private HttpRequest processMiddlewares(HttpRequest request) {
        for (Middleware middleware : middlewares)
            request = middleware.process(request);

        return request;
    }

    public RequestPipeline use(Middleware middleware) {
        this.middlewares.add(middleware);
        return this;
    }
}
