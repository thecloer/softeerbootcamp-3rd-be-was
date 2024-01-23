package model.User;

import annotation.Column;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class UserBuilder {
    private String userId;
    private String password;
    private String name;
    private String email;

    static public User fromStringifiedJson(String stringifiedJson) { // TODO: 리플렉션으로 어노테이션 맴버에 매핑하는 부분 추출
        stringifiedJson = stringifiedJson.trim();
        if (!(stringifiedJson.startsWith("{") && stringifiedJson.endsWith("}"))) {
            throw new IllegalArgumentException("JSON 형식이 아닙니다. (괄호가 없습니다.)");
        }

        stringifiedJson = stringifiedJson.substring(1, stringifiedJson.length() - 1);
        String[] keyValuePairs = stringifiedJson.split(",");

        Map<String, String> keyValueMap = new HashMap<>();
        for (String keyValuePair : keyValuePairs) {
            String[] keyValue = keyValuePair.split(":");
            if (keyValue.length != 2) {
                throw new IllegalArgumentException("JSON 형식이 아닙니다. (key:value 쌍이 아닙니다.)");
            }

            String key = keyValue[0].trim();
            if (key.startsWith("\"") && key.endsWith("\"")) {
                key = key.substring(1, key.length() - 1);
            }

            String value = keyValue[1].trim();
            if (value.startsWith("\"") && value.endsWith("\"")) {
                value = value.substring(1, value.length() - 1);
            }

            keyValueMap.put(key, value);
        }

        User user = new User("", "", "", "");

        Class<User> userClass = User.class;
        for (Field field : userClass.getDeclaredFields()) {
            if(!field.isAnnotationPresent(Column.class)) {
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
