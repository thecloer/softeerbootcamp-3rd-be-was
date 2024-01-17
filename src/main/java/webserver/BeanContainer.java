package webserver;

import controller.ResourceController;
import controller.UserController;
import db.Database;
import service.UserService;

public class BeanContainer {

    private static final Database database = new Database();

    private static final UserService userService = new UserService(database);

    private static final ResourceController resourceController = new ResourceController();
    private static final UserController userController = new UserController(userService);

    public static ResourceController getResourceController() {
        return resourceController;
    }
    public static UserController getUserController() {
        return userController;
    }
}