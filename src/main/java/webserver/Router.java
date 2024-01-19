package webserver;

import controller.ResourceController;
import controller.UserController;

import java.util.Map;
import java.util.function.Function;

import util.http.HttpRequest;
import util.http.HttpResponse;

import java.util.Collections;
import java.util.HashMap;

public class Router {

    private static final UserController userController = BeanContainer.getUserController();
    private static final ResourceController resourceController = BeanContainer.getResourceController();

    private static final Map<String, Function<HttpRequest, HttpResponse>> ROUTE_MAP;

    static {
        Map<String, Function<HttpRequest, HttpResponse>> routeMap = new HashMap<>();
        routeMap.put("GET /user/create", userController::signUp);

        ROUTE_MAP = Collections.unmodifiableMap(routeMap);
    }

    public static HttpResponse route(HttpRequest httpRequest) {
        String routeKey = httpRequest.getMethod() + " " + httpRequest.getPath();
        Function<HttpRequest, HttpResponse> handler = ROUTE_MAP.getOrDefault(routeKey, resourceController::resourceHandler);
        return handler.apply(httpRequest);
    }
}
