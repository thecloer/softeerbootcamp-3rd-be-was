package model;

public class User {
    public static String USER_ID = "userId";
    public static String PASSWORD = "password";
    public static String NAME = "name";
    public static String EMAIL = "email";
    private String userId;
    private String password;
    private String name;
    private String email;

    public User(String userId, String password, String name, String email) {
        validate(userId, password, name, email);
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
        return "User [userId=" + userId + ", password=" + password + ", name=" + name + ", email=" + email + "]";
    }

    private static void validate(String userId, String password, String name, String email) {
        if (userId == null || userId.isEmpty())
            throw new IllegalArgumentException("userId가 없습니다.");
        if (password == null || password.isEmpty())
            throw new IllegalArgumentException("password가 없습니다");
        if (name == null || name.isEmpty())
            throw new IllegalArgumentException("name이 없습니다");
        if (email == null || email.isEmpty())
            throw new IllegalArgumentException("email이 없습니다");
    }
}
