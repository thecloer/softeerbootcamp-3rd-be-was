package controller;

import db.Database;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequest;
import util.HttpResponse;
import util.HttpStatus;

import java.util.Map;

public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final Database database;

    public UserController(Database database) {
        this.database = database;
    }

    public void signUp(HttpRequest request, HttpResponse response) {
        try {
            Map<String, String> queries = request.getQueries();

            User user = new User(
                    queries.get(User.USER_ID),
                    queries.get(User.PASSWORD),
                    queries.get(User.NAME),
                    queries.get(User.EMAIL)
            );

            if (database.findUserById(user.getUserId()) != null)
                throw new IllegalArgumentException("이미 존재하는 아이디입니다.");

            database.addUser(user);
            logger.debug("[회원가입] id: {}", user.getUserId());

            response.status(HttpStatus.FOUND)
                    .addHeader("Location", "/user/profile.html")
                    .send();

        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
            response.status(HttpStatus.FOUND)
                    .addHeader("Location", "/user/form.html")
                    .body(e.getMessage().getBytes())
                    .send();
        }
    }
}