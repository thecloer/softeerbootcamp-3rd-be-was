package webserver;

import db.Database;
import controller.ResourceController;
import controller.UserController;
import pipeline.requestProcessor.AuthFilter;
import pipeline.RequestPipeline;
import pipeline.responseProcessor.SessionCookieRefresher;
import pipeline.responseProcessor.templateEngine.TemplateRenderer;

public class ApplicationContainer {

    private static final Database database = new Database();

    private static final ResourceController resourceController = new ResourceController(database);
    private static final UserController userController = new UserController(database);

    private static final RequestPipeline requestPipeline = new RequestPipeline();

    static {
        requestPipeline.addRequestProcessor(new AuthFilter());

        requestPipeline.addResponseProcessor(new TemplateRenderer());
        requestPipeline.addResponseProcessor(new SessionCookieRefresher());
    }

    public static ResourceController getResourceController() {
        return resourceController;
    }

    public static UserController getUserController() {
        return userController;
    }

    public static RequestPipeline getRequestPipeline() {
        return requestPipeline;
    }
}
