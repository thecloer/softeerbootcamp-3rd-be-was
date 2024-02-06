package model;

import exception.BadRequestException;
import model.User.User;
import model.User.UserBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserTest {

    @Test
    @DisplayName("문자열 형태의 JSON을 이용해 User 객체를 생성할 수 있다.")
    void userBuilderFromStringifiedJson_InstantiateUserCorrectly() {
        String stringifiedJson = "{\"userId\":\"test_id\",\"password\":\"test_pw\",\"name\":\"test_name\",\"email\":\"test_email\"}";

        User user = UserBuilder.fromStringifiedJson(stringifiedJson);

        assertThat(user)
                .isNotNull()
                .extracting("userId", "password", "name", "email")
                .containsExactly("test_id", "test_pw", "test_name", "test_email");
    }

    @ParameterizedTest
    @MethodSource("invalidStringifiedJsons")
    @DisplayName("유효하지 않은 문자열 형태의 JSON을 이용한 User 객체 생성은 예외를 발생시킨다.")
    void userBuilderFromStringifiedJson_WhenInvalidStringifiedJSON_ThrowException(String stringifiedJson, String expectedExceptionMessage) {
        assertThatThrownBy(() -> UserBuilder.fromStringifiedJson(stringifiedJson))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining(expectedExceptionMessage);
    }

    private static Stream<Arguments> invalidStringifiedJsons() {
        return Stream.of(
                Arguments.of("\"userId\":\"test_id\",\"password\":\"test_pw\",\"name\":\"test_name\",\"email\":\"test_email\"}",
                        "JSON 형식이 아닙니다. (괄호가 없습니다.)"),
                Arguments.of("{\"userId\":,\"password\":\"test_pw\",\"name\":\"test_name\",\"email\":\"test_email\"}",
                        "JSON 형식이 아닙니다. (key:value 쌍이 아닙니다.)"),
                Arguments.of("{:\"test_id\",\"password\":\"test_pw\",\"name\":\"test_name\",\"email\":\"test_email\"}",
                        "JSON 형식이 아닙니다. (key 형식이 잘못됐습니다.)")
        );
    }
}