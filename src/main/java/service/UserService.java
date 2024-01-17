package service;

import db.Database;
import model.User;

public class UserService {

    private final Database database;

    public UserService(Database database) {
        this.database = database;
    }

    public User create(String userId, String password, String name, String email) {

        if (userId == null || userId.isEmpty())
            throw new IllegalArgumentException("userId가 입력되지 않았습니다.");
        if (password == null || password.isEmpty())
            throw new IllegalArgumentException("password가 입력되지 않았습니다.");
        if (name == null || name.isEmpty())
            throw new IllegalArgumentException("name이 입력되지 않았습니다.");
        if (email == null || email.isEmpty())
            throw new IllegalArgumentException("email이 입력되지 않았습니다.");

        if (database.findUserById(userId) != null)
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");

        User user = new User(userId, password, name, email);
        database.addUser(user);

        return user;
    }
}
