package pipeline;

import controller.PostController;
import controller.ResourceController;
import controller.UserController;

import java.util.Map;
import java.util.function.Function;

import util.http.HttpRequest;
import util.http.HttpResponse;
import webserver.ApplicationContainer;

import java.util.Collections;
import java.util.HashMap;

public class Router {

    private static final UserController userController = ApplicationContainer.getUserController();
    private static final PostController postController = ApplicationContainer.getPostController();
    private static final ResourceController resourceController = ApplicationContainer.getResourceController();

    private static final Map<String, Function<HttpRequest, HttpResponse>> ROUTING_TABLE;
    private static final Map<String, String> REDIRECT_TABLE;

    static {
        ROUTING_TABLE = Collections.unmodifiableMap(new HashMap<>() {{
            put("GET /index.html", resourceController::homePage);
            put("GET /user/profile.html", resourceController::profilePage);
            put("GET /user/list.html", resourceController::userListPage);
            put("GET /post/show.html", resourceController::postPage);

            put("POST /user/create", userController::signUp);
            put("POST /user/login", userController::login);
            put("GET /user/logout", userController::logout);
            put("POST /post/create", postController::createPost);
        }});

        REDIRECT_TABLE = Collections.unmodifiableMap(new HashMap<>() {{
            put("GET /", "GET /index.html");
        }});
    }

    public static HttpResponse route(HttpRequest httpRequest) {
        String routeKey = getRouteKey(httpRequest);
        Function<HttpRequest, HttpResponse> handler = ROUTING_TABLE.getOrDefault(routeKey, resourceController::staticHandler);
        return handler.apply(httpRequest);
    }

    public static String getRouteKey(HttpRequest httpRequest) {
        String routeKey = httpRequest.getMethod() + " " + httpRequest.getPath();
        return redirect(routeKey);
    }

    private static String redirect(String routeKey) {
        return REDIRECT_TABLE.getOrDefault(routeKey, routeKey);
    }
}
