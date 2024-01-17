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
