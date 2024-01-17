package controller;

import model.User;
import service.UserService;
import util.HttpRequest;
import util.HttpResponse;
import util.HttpStatus;
import util.UriHelper;

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

            response.status(HttpStatus.FOUND)
                    .addHeader("Location", "/user/profile.html?userId=" + UriHelper.encode(user.getUserId()))
                    .send();

        } catch (IllegalArgumentException e) {
            response.status(HttpStatus.FOUND)
                    .addHeader("Location", "/user/form.html?message=" + UriHelper.encode(e.getMessage()))
                    .send();
        }
    }
}