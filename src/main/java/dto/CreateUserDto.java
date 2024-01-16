package dto;

import model.User;
import util.HttpRequest;

import java.util.Map;

public class CreateUserDto {
    private final String userId;
    private final String password;
    private final String name;
    private final String email;

    public CreateUserDto(String userId, String password, String name, String email) {
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

    public User toEntity() {
        return new User(userId, password, name, email);
    }

    public static CreateUserDto from(HttpRequest request) {
        Map<String, String> queries = request.getQueries();
        if(queries.size() != 4)
            throw new IllegalArgumentException("쿼리 파라미터의 개수가 맞지 않습니다.");
        if (!queries.containsKey("userId"))
            throw new IllegalArgumentException("쿼리 파라미터에 userId가 없습니다.");
        if (!queries.containsKey("password"))
            throw new IllegalArgumentException("쿼리 파라미터에 password가 없습니다.");
        if (!queries.containsKey("name"))
            throw new IllegalArgumentException("쿼리 파라미터에 name이 없습니다.");
        if (!queries.containsKey("email"))
            throw new IllegalArgumentException("쿼리 파라미터에 email이 없습니다.");

        return new CreateUserDto(
                queries.get("userId"),
                queries.get("password"),
                queries.get("name"),
                queries.get("email")
        );
    }
}