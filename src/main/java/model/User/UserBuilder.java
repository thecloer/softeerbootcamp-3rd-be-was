package model.User;

import util.ModelConverter;

public class UserBuilder {
    private String userId;
    private String password;
    private String name;
    private String email;

    static public User fromStringifiedJson(String stringifiedJson) {
        return ModelConverter.convert(stringifiedJson, User.class);
    }

    public UserBuilder userId(String userId) {
        this.userId = userId;
        return this;
    }

    public UserBuilder password(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder name(String name) {
        this.name = name;
        return this;
    }

    public UserBuilder email(String email) {
        this.email = email;
        return this;
    }

    public User build() {
        return new User(userId, password, name, email);
    }
}
