package util;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

public class UriHelperTest {

    @Test
    public void 쿼리파싱_성공() {
        // given
        String queryString = "userId=test_id&password=test_pw&name=test_name&email=test_email";

        // when
        Map<String, String> queries = UriHelper.parseQueryString(queryString);

        // then
        assertThat(queries)
                .hasSize(4)
                .contains(
                        entry("userId", "test_id"),
                        entry("password", "test_pw"),
                        entry("name", "test_name"),
                        entry("email", "test_email")
                );
    }

    @Test
    public void 쿼리파싱_잘못된_쿼리() {
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
