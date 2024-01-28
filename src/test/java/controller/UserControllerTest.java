package controller;

import db.Database;
import exception.BadRequestException;
import model.User.User;
import model.User.UserBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;
import util.JSON;
import util.http.*;

import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class UserControllerTest {

    private Database database;
    private UserController userController;

    @BeforeEach
    public void setUp() {
        database = new Database();
        userController = new UserController(database);
    }

    @Test
    @DisplayName("모든 필드가 유효한 회원가입 요청은 사용자 정보를 DB에 저장 하고 Location=/ 필드와 함께 201 Created 응답을 받는다.")
    public void signUp_WhenAllFieldsAreValid_ShouldReturnCreatedWithLocation() {
        // given
        HttpRequest request = new HttpRequest.Builder()
                .method(HttpMethod.POST)
                .uri("/user/create")
                .protocol("HTTP/1.1")
                .body(JSON.stringify(
                        Map.of(
                                "userId", "test_id",
                                "password", "test_pw",
                                "name", "test_name",
                                "email", "test_email"
                        )
                ))
                .build();


        // when
        HttpResponse response = userController.signUp(request);


        // then
        User savedUser = database.findUserById("test_id");

        assertThat(savedUser)
                .isNotNull()
                .extracting("userId", "password", "name", "email")
                .containsExactly("test_id", "test_pw", "test_name", "test_email");

        assertThat(response)
                .extracting("statusCode", "statusMessage", "contentType", "body")
                .containsExactly(201, "Created", ContentType.NONE, new byte[0]);

        assertThat(response.getFields())
                .contains("Location: /\r\n");
    }

    @Test
    @DisplayName("DB에 이미 저장된 ID로 회원가입을 요청받는 경우 예외 메시지와 함께 BadRequestException을 반환한다.")
    public void signup_WhenUserExists_ShouldThrowBadRequestException() {
        // given
        User existingUser = new UserBuilder()
                .userId("same_id")
                .password("pw")
                .name("name")
                .email("email")
                .build();

        database.addUser(existingUser);

        HttpRequest request = new HttpRequest.Builder()
                .method(HttpMethod.POST)
                .uri("/user/create")
                .protocol("HTTP/1.1")
                .body(JSON.stringify(
                        Map.of(
                                "userId", "same_id",
                                "password", "different_pw",
                                "name", "different_name",
                                "email", "different_email")
                ))
                .build();

        // when, then
        assertThatThrownBy(() -> userController.signUp(request))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("이미 존재하는 아이디입니다.");
    }

    @ParameterizedTest
    @MethodSource("invalidSignUpRequests")
    @DisplayName("유효하지 않는 필드 값으로 회원가입을 요청받는 경우 예외 메시지와 함께 BadRequestException을 반환한다.")
    public void signUp_WhenRequiredFieldIsEmpty_ShouldThrowBadRequestException(
            String userId, String password, String name, String email, String expectedMessage) {

        // given
        HttpRequest request = new HttpRequest.Builder()
                .method(HttpMethod.POST)
                .uri("/user/create")
                .protocol("HTTP/1.1")
                .body(JSON.stringify(
                        Map.of(
                                "userId", userId,
                                "password", password,
                                "name", name,
                                "email", email
                        )
                ))
                .build();

        // when, then
        assertThatThrownBy(() -> userController.signUp(request))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(expectedMessage);
    }

    private static Stream<Arguments> invalidSignUpRequests() {
        return Stream.of(
                Arguments.of("", "test_pw", "test_name", "test_email", "userId가 입력되지 않았습니다."),
                Arguments.of("test_id", "", "test_name", "test_email", "password가 입력되지 않았습니다."),
                Arguments.of("test_id", "test_pw", "", "test_email", "name이 입력되지 않았습니다."),
                Arguments.of("test_id", "test_pw", "test_name", "", "email이 입력되지 않았습니다.")
        );
    }
}
