package model;

import model.User.User;
import model.User.UserBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    @DisplayName("fromStringifiedJson를 이용한 User 생성")
    void fromStringifiedJson() {
        String stringifiedJson = "{\"userId\":\"test_id\",\"password\":\"test_pw\",\"name\":\"test_name\",\"email\":\"test_email\"}";

        User user = UserBuilder.fromStringifiedJson(stringifiedJson);

        User expected = new User("test_id", "test_pw", "test_name", "test_email");
        assertEquals(expected.toString(), user.toString());
    }
}