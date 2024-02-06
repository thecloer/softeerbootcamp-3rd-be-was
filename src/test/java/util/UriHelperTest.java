package util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

public class UriHelperTest {

    @Test
    @DisplayName("유효한 쿼리스트링을 파싱하면 key값이 소문자인 Map<String, String> 형태로 변환된다.")
    public void UriHelperParseQueryString_ShouldReturnLowercaseKeyMap() {
        // given
        String queryString = "USERID=test_id&PASSWORD=test_pw&name=test_name&email=test_email";

        // when
        Map<String, String> queries = UriHelper.parseQueryString(queryString);

        // then
        assertThat(queries)
                .hasSize(4)
                .contains(
                        entry("userid", "test_id"),
                        entry("password", "test_pw"),
                        entry("name", "test_name"),
                        entry("email", "test_email")
                );
    }

    @Test
    @DisplayName("쿼리스트링의 유효하지 않은 키-값은 무시한다.")
    public void UriHelperParseQueryString_WhenInvalidKeyValue_IgnoreThem() {
        // given
        String queryString = "&==&&userId&test_id&password=test_pw&name=test_name&email=test_email&&";

        // when
        Map<String, String> queries = UriHelper.parseQueryString(queryString);

        // then
        assertThat(queries)
                .hasSize(3)
                .contains(
                        entry("password", "test_pw"),
                        entry("name", "test_name"),
                        entry("email", "test_email")
                );
    }
}
