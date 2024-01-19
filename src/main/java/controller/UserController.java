package controller;

import model.User;
import service.UserService;
import util.*;
import util.http.ContentType;
import util.http.HttpRequest;
import util.http.HttpResponse;
import util.http.HttpStatus;

public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public void signUp(HttpRequest request, HttpResponse response) {
        try {
            User user = userService.create(
                    request.getQueryParam(User.USER_ID),
                    request.getQueryParam(User.PASSWORD),
                    request.getQueryParam(User.NAME),
                    request.getQueryParam(User.EMAIL)
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