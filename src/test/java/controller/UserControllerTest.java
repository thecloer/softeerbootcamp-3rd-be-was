package controller;

import db.Database;
import model.User.User;
import model.User.UserBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.http.*;

import static org.assertj.core.api.Assertions.assertThat;

public class UserControllerTest {

    private Database database;
    private UserController userController;

    @BeforeEach
    public void setUp() {
        database = new Database();
        userController = new UserController(database);
    }

    @Test
    public void 회원가입_성공() {
        // given
        HttpRequest request = new HttpRequest.Builder()
                .method(HttpMethod.GET)
                .uri("/user/create?userId=test_id&password=test_pw&name=test_name&email=test_email")
                .protocol("HTTP/1.1")
                .build();

        // when
        HttpResponse response = userController.signUp(request);

        // then
        HttpResponse expected = new HttpResponse.Builder()
                .status(HttpStatus.CREATED)
                .setHeader("Location", "/user/profile.html?userId=test_id")
                .build();

        assertThat(response.toString())
                .isEqualTo(expected.toString());
    }

    @Test
    public void 회원가입_실패_중복_아이디() {
        // given
        User existingUser = new UserBuilder()
                .userId("test_id")
                .password("test_pw")
                .name("test_name")
                .email("test_email")
                .build();

        database.addUser(existingUser);

        HttpRequest request = new HttpRequest.Builder()
                .method(HttpMethod.GET)
                .uri("/user/create?userId=test_id&password=test_pw2&name=test_name2&email=test_email2")
                .protocol("HTTP/1.1")
                .build();

        // when
        HttpResponse response = userController.signUp(request);

        // then
        HttpResponse expected = new HttpResponse.Builder()
                .status(HttpStatus.BAD_REQUEST)
                .contentType(ContentType.JSON)
                .body("{\"message\":\"이미 존재하는 아이디입니다.\"}")
                .build();

        assertThat(response.toString())
                .isEqualTo(expected.toString());
    }

    @Test
    public void 회원가입_실패_입력_형식_예외() {
        // given
        HttpRequest request = new HttpRequest.Builder()
                .method(HttpMethod.GET)
                .uri("/user/create?userId===&password=test_pw2&name=test_name2&email=test_email2&==&=&====&")
                .protocol("HTTP/1.1")
                .build();

        // when
        HttpResponse response = userController.signUp(request);

        // then
        HttpResponse expected = new HttpResponse.Builder()
                .status(HttpStatus.BAD_REQUEST)
                .contentType(ContentType.JSON)
                .body("{\"message\":\"userId가 입력되지 않았습니다.\"}")
                .build();

        assertThat(response.toString())
                .isEqualTo(expected.toString());
    }
}
