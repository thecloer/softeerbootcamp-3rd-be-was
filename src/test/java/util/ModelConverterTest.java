package util;

import model.User.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ModelConverterTest {

    @Test
    @DisplayName("convert() User 성공: 메서드는 stringifiedJson을 User 객체로 변환")
    void convertUserByStringifiedJson() {
        String stringifiedJson = "{\"userId\":\"test_id\",\"password\":\"test_pw\",\"name\":\"test_name\",\"email\":\"test_email\"}";

        User user = ModelConverter.convert(stringifiedJson, User.class);

        User expected = new User("test_id", "test_pw", "test_name", "test_email");
        assertEquals(expected.toString(), user.toString());
    }
}
