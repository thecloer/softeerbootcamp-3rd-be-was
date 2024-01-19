package controller;

import db.Database;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.UserService;
import util.http.ContentType;
import util.http.HttpRequest;
import util.http.HttpResponse;
import util.http.HttpStatus;

import java.io.ByteArrayOutputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class UserControllerTest {

    private UserController userController;

    @BeforeEach
    public void setUp() {
        Database database = new Database();
        UserService userService = new UserService(database);
        userController = new UserController(userService);
    }

    @Test
    public void 회원가입() {
        // given
        HttpRequest request = new HttpRequest.Builder()
                .method("POST")
                .uri("/user/create?userId=test_id&password=test_pw&name=test_name&email=test_email")
                .protocol("HTTP/1.1")
                .build();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        HttpResponse response = new HttpResponse(out);

        // when
        userController.signUp(request, response);

        // then
        HttpResponse expectedResponse = new HttpResponse(out)
                .status(HttpStatus.FOUND)
                .contentType(ContentType.OCTET_STREAM)
                .addHeader("Location", "/user/profile.html?userId=test_id");

        assertThat(response.toString()).isEqualTo(expectedResponse.toString());
    }
}


