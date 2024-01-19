package webserver;

import db.Database;
import controller.ResourceController;
import controller.UserController;

public class ApplicationContainer {

    private static final Database database = new Database();

    private static final ResourceController resourceController = new ResourceController();
    private static final UserController userController = new UserController(database);

    public static ResourceController getResourceController() {
        return resourceController;
    }

    public static UserController getUserController() {
        return userController;
    }
}
