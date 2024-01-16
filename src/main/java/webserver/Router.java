package webserver;

import controller.ResourceController;
import util.HttpRequest;
import util.HttpResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class Router {

    private static final Map<String, BiConsumer<HttpRequest, HttpResponse>> ROUTE_MAP = new HashMap<>();

    public static BiConsumer<HttpRequest, HttpResponse> route(HttpRequest httpRequest) {
        String routeKey = httpRequest.getMethod() + " " + httpRequest.getPath();
        return ROUTE_MAP.getOrDefault(routeKey, ResourceController::resourceHandler);
    }
}
