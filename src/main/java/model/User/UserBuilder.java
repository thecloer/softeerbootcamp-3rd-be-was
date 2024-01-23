package model.User;

import annotation.Column;
import util.JSON;

import java.lang.reflect.Field;
import java.util.Map;

public class UserBuilder {
    private String userId;
    private String password;
    private String name;
    private String email;

    static public User fromStringifiedJson(String stringifiedJson) {
        Map<String, String> keyValueMap = JSON.parse(stringifiedJson);

        User user = new User("", "", "", "");
        // TODO: 리플렉션으로 어노테이션 맴버에 매핑하는 부분 추출
        Class<User> userClass = User.class;
        for (Field field : userClass.getDeclaredFields()) {
            if (!field.isAnnotationPresent(Column.class)) {
                continue;
            }

            Column column = field.getAnnotation(Column.class);
            String key = column.value();
            if (!keyValueMap.containsKey(key)) {
                continue;
            }

            String value = keyValueMap.get(key);

            try {
                field.setAccessible(true);
                field.set(user, value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                throw new RuntimeException("User 객체 생성에 실패했습니다. (" + e.getMessage() + ")");
            }
        }

        return user;
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
