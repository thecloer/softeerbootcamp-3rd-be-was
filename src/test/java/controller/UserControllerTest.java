package controller;

import db.Database;
import org.junit.jupiter.api.BeforeEach;

public class UserControllerTest {

    private UserController userController;

    @BeforeEach
    public void setUp() {
        Database database = new Database();
        userController = new UserController(database);
    }
}
