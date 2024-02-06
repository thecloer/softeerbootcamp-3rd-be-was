package util;

import exception.BadRequestException;
import model.User.UserBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Map;
import java.util.stream.Stream;

import static java.util.Map.entry;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class JSONTest {

    @Test
    @DisplayName("유효한 문자열 형태의 JSON을 파싱하면 Map<String, String> 형태로 변환된다.")
    void JsonParse_ShouldReturnMap() {
        // given
        String stringifiedJson = "{\"key1\":\"value1\", \"key2\":\"value2\", \"key3\":null}";

        // when
        Map<String, String> parsed = JSON.parse(stringifiedJson);

        // then
        assertThat(parsed)
                .hasSize(3)
                .contains(entry("key1", "value1"), entry("key2", "value2"), entry("key3", ""));
    }

    @ParameterizedTest
    @MethodSource("invalidStringifiedJsons")
    @DisplayName("유효하지 않은 문자열 형태의 JSON을 이용한 User 객체 생성은 예외를 발생시킨다.")
    void JsonParse_WhenInvalidStringifiedJSON_ThrowException(String stringifiedJson, String expectedExceptionMessage) {
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
