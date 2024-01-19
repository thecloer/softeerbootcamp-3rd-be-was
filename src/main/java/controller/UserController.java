package controller;

import model.User;
import service.UserService;
import util.*;
import util.http.ContentType;
import util.http.HttpRequest;
import util.http.HttpResponse;
import util.http.HttpStatus;

import java.util.Map;

public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public void signUp(HttpRequest request, HttpResponse response) {
        try {
            Map<String, String> queries = request.getQueries();

            User user = userService.create(
                    queries.get(User.USER_ID),
                    queries.get(User.PASSWORD),
                    queries.get(User.NAME),
                    queries.get(User.EMAIL)
            );

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