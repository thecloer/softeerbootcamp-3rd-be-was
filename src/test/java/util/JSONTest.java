package util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static java.util.Map.entry;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class JSONTest {

    @Test
    @DisplayName("JSON.parse() 성공: 문자열, null")
    void parse() {
        // given
        String stringifiedJson = "{\"key1\":\"value1\", \"key2\":\"value2\", \"key3\":null}";

        // when
        Map<String, String> parsed = JSON.parse(stringifiedJson);

        // then
        assertThat(parsed)
                .hasSize(3)
                .contains(entry("key1", "value1"), entry("key2", "value2"), entry("key3", ""));
    }

    @Test
    @DisplayName("JSON.parse() 실패: JSON 형식이 아님 (괄호가 없음)")
    void parse_fail_no_bracket() {
        // given
        String stringifiedJson = "\"key1\":\"value1\", \"key2\":\"value2\", \"key3\":null";

        // when & then
        assertThatThrownBy(() -> JSON.parse(stringifiedJson))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("JSON 형식이 아닙니다. (괄호가 없습니다.)");
    }

    @Test
    @DisplayName("JSON.parse() 실패: JSON 형식이 아님 (key:value 쌍이 아님)")
    void parse_fail_no_colon() {
        // given
        String stringifiedJson = "{\"key1\"\"value1\", \"key2\":\"value2\", \"key3\":null}";

        // when & then
        assertThatThrownBy(() -> JSON.parse(stringifiedJson))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("JSON 형식이 아닙니다. (key:value 쌍이 아닙니다.)");
    }

    @Test
    @DisplayName("JSON.parse() 실패: JSON 형식이 아님 (key 형식이 잘못됨)")
    void parse_fail_wrong_key() {
        // given
        String stringifiedJson = "{\"key1:\"value1\", \"key2\":\"value2\", \"key3\":null}";

        // when & then
        assertThatThrownBy(() -> JSON.parse(stringifiedJson))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("JSON 형식이 아닙니다. (key 형식이 잘못됐습니다.)");
    }
}
