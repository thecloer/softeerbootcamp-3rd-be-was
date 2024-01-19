package controller;

import db.Database;
import model.User;
import util.*;
import util.http.ContentType;
import util.http.HttpRequest;
import util.http.HttpResponse;
import util.http.HttpStatus;

public class UserController {

    private final Database database;

    public UserController(Database database) {
        this.database = database;
    }

    public void signUp(HttpRequest request, HttpResponse response) {
        try {
            String userId = request.getQueryParam(User.USER_ID);
            String password = request.getQueryParam(User.PASSWORD);
            String name = request.getQueryParam(User.NAME);
            String email = request.getQueryParam(User.EMAIL);

            if (userId == null || userId.isEmpty())
                throw new IllegalArgumentException("userId가 입력되지 않았습니다.");
            if (password == null || password.isEmpty())
                throw new IllegalArgumentException("password가 입력되지 않았습니다.");
            if (name == null || name.isEmpty())
                throw new IllegalArgumentException("name이 입력되지 않았습니다.");
            if (email == null || email.isEmpty())
                throw new IllegalArgumentException("email이 입력되지 않았습니다.");

            if (database.findUserById(userId) != null)
                throw new IllegalArgumentException("이미 존재하는 아이디입니다.");

            User user = new User.Builder()
                    .userId(userId)
                    .password(password)
                    .name(name)
                    .email(email)
                    .build();

            database.addUser(user);

            response.status(HttpStatus.CREATED)
                    .addHeader("Location", "/user/profile.html?userId=" + UriHelper.encode(user.getUserId()))
                    .send();

        } catch (IllegalArgumentException e) {
            response.status(HttpStatus.BAD_REQUEST)
                    .contentType(ContentType.JSON)
                    .body("{\"message\":\"" + e.getMessage() + "\"}")
                    .send();
        }
    }
}
