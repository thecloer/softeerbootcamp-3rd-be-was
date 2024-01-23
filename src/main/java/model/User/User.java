package model.User;

import annotation.Column;
import model.Model;

public class User extends Model {
    @Column("userId")
    private String userId;
    @Column("password")
    private String password;
    @Column("name")
    private String name;
    @Column("email")
    private String email;

    private User() {
        super();
    }

    public User(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("User [userId=").append(userId)
                .append(", password=").append(password)
                .append(", name=").append(name)
                .append(", email=").append(email)
                .append("]")
                .toString();
    }
}
