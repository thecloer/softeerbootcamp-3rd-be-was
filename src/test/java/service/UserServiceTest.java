package service;

import db.Database;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserServiceTest {

    private Database database;
    private UserService userService;

    @BeforeEach
    public void setUp() {
        database = new Database();
        userService = new UserService(database);
    }

    @Test
    public void 회원생성_성공() {
        // given
        String userId = "test_id";
        String password = "test_pw";
        String name = "test_name";
        String email = "test_email";

        // when
        User User = userService.create(userId, password, name, email);

        // then
        User findUser = database.findUserById(userId);
        assertThat(findUser).isEqualTo(User);
    }

    @Test
    public void 회원생성_실패_아이디_중복() {
        // given
        String userId = "test_id";
        String password = "test_pw";
        String name = "test_name";
        String email = "test_email";
        userService.create(userId, password, name, email);

        String password2 = "test_pw2";
        String name2 = "test_name2";
        String email2 = "test_email2";

        // when
        IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class,
                () -> userService.create(userId, password2, name2, email2)
        );

        // then
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 아이디입니다.");
    }

    @Test
    public void 회원생성_실패_아이디_입력_형식_예외() {
        // given
        String emptyId = "";
        String nullId = null;
        String password = "test_pw";
        String name = "test_name";
        String email = "test_email";

        // when
        IllegalArgumentException emptyException = assertThrows(
                IllegalArgumentException.class,
                () -> userService.create(emptyId, password, name, email)
        );
        IllegalArgumentException nullException = assertThrows(
                IllegalArgumentException.class,
                () -> userService.create(nullId, password, name, email)
        );

        // then
        assertThat(emptyException.getMessage()).isEqualTo("userId가 입력되지 않았습니다.");
        assertThat(nullException.getMessage()).isEqualTo("userId가 입력되지 않았습니다.");
    }
}
