package webserver;

import db.Database;
import controller.ResourceController;
import controller.UserController;
import pipeline.requestProcessor.AuthFilter;
import pipeline.RequestPipeline;

public class ApplicationContainer {

    private static final Database database = new Database();

    private static final ResourceController resourceController = new ResourceController();
    private static final UserController userController = new UserController(database);

    private static final RequestPipeline requestPipeline = new RequestPipeline();

    static {
        requestPipeline.addRequestProcessor(new AuthFilter());
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
