package webserver;

import controller.ResourceController;
import controller.UserController;
import db.Database;

public class BeanContainer {
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