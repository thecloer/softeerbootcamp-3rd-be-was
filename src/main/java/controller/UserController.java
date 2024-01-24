package controller;

import db.Database;
import model.User.User;
import model.User.UserBuilder;
import util.JSON;
import util.http.ContentType;
import util.http.HttpRequest;
import util.http.HttpResponse;
import util.http.HttpStatus;

import java.util.Map;

public class UserController {

    private final Database database;

    public UserController(Database database) {
        this.database = database;
    }

    public HttpResponse signUp(HttpRequest request) {
        try {
            User user = UserBuilder.fromStringifiedJson(request.getBody());

            if (user.getUserId().isEmpty())
                throw new IllegalArgumentException("userId가 입력되지 않았습니다.");
            if (user.getPassword().isEmpty())
                throw new IllegalArgumentException("password가 입력되지 않았습니다.");
            if (user.getName().isEmpty())
                throw new IllegalArgumentException("name이 입력되지 않았습니다.");
            if (user.getEmail().isEmpty())
                throw new IllegalArgumentException("email이 입력되지 않았습니다.");

            if (database.findUserById(user.getUserId()) != null)
                throw new IllegalArgumentException("이미 존재하는 아이디입니다.");

            database.addUser(user);

            return new HttpResponse.Builder()
                    .status(HttpStatus.CREATED)
                    .setHeader("Location", "/")
                    .build();

        } catch (IllegalArgumentException e) {
            String message = JSON.stringify(Map.of("message", e.getMessage()));
            return new HttpResponse.Builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .contentType(ContentType.JSON)
                    .body(message)
                    .build();
        }
    }
}
