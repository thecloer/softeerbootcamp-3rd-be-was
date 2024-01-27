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

    private static final UserController userController = ApplicationContainer.getUserController();
    private static final ResourceController resourceController = ApplicationContainer.getResourceController();

    private static final Map<String, Function<HttpRequest, HttpResponse>> ROUTING_TABLE;

    static {
        Map<String, Function<HttpRequest, HttpResponse>> routeMap = new HashMap<>();
        routeMap.put("POST /user/create", userController::signUp);
        routeMap.put("POST /user/login", userController::login);

        ROUTING_TABLE = Collections.unmodifiableMap(routeMap);
    }

    public static HttpResponse route(HttpRequest httpRequest) {
        String routeKey = getRouteKey(httpRequest);
        Function<HttpRequest, HttpResponse> handler = ROUTING_TABLE.getOrDefault(routeKey, resourceController::resourceHandler);
        return handler.apply(httpRequest);
    }

    public static String getRouteKey(HttpRequest httpRequest) {
        return httpRequest.getMethod() + " " + httpRequest.getPath();
    }
}
