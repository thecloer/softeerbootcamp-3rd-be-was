package webserver;

import controller.ResourceController;
import controller.UserController;
import util.http.HttpRequest;
import util.http.HttpResponse;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class Router {

    private static final UserController userController = BeanContainer.getUserController();
    private static final ResourceController resourceController = BeanContainer.getResourceController();

    private static final Map<String, BiConsumer<HttpRequest, HttpResponse>> ROUTE_MAP;
    static {
        Map<String, BiConsumer<HttpRequest, HttpResponse>> routeMap = new HashMap<>();
        routeMap.put("GET /user/create", userController::signUp);

        ROUTE_MAP = Collections.unmodifiableMap(routeMap);
    }

    public static BiConsumer<HttpRequest, HttpResponse> route(HttpRequest httpRequest) {
        String routeKey = httpRequest.getMethod() + " " + httpRequest.getPath();
        return ROUTE_MAP.getOrDefault(routeKey, resourceController::resourceHandler);
    }
}
