package controller;

import db.Database;
import model.User.User;
import model.User.UserBuilder;
import util.JSON;
import util.http.ContentType;
import util.http.HttpRequest;
import util.http.HttpResponse;
import util.http.HttpStatus;

import java.util.Map;

public class UserController {

    private final Database database;

    public UserController(Database database) {
        this.database = database;
    }

    public HttpResponse signUp(HttpRequest request) {
        try {
            User user = UserBuilder.fromStringifiedJson(request.getBody());

            if (user.getUserId().isEmpty())
                throw new IllegalArgumentException("userId가 입력되지 않았습니다.");
            if (user.getPassword().isEmpty())
                throw new IllegalArgumentException("password가 입력되지 않았습니다.");
            if (user.getName().isEmpty())
                throw new IllegalArgumentException("name이 입력되지 않았습니다.");
            if (user.getEmail().isEmpty())
                throw new IllegalArgumentException("email이 입력되지 않았습니다.");

            if (database.findUserById(user.getUserId()) != null)
                throw new IllegalArgumentException("이미 존재하는 아이디입니다.");

            database.addUser(user); // TODO: 비밀번호 암호화

            return new HttpResponse.Builder()
                    .status(HttpStatus.CREATED)
                    .setHeader("Location", "/")
                    .build();

        } catch (IllegalArgumentException e) {
            String message = JSON.stringify(Map.of("message", e.getMessage()));
            return new HttpResponse.Builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .contentType(ContentType.JSON)
                    .body(message)
                    .build();
        }
    }

    public HttpResponse login(HttpRequest request) {
        try {
            User user = UserBuilder.fromStringifiedJson(request.getBody());

            if (user.getUserId().isEmpty())
                throw new IllegalArgumentException("userId가 입력되지 않았습니다.");
            if (user.getPassword().isEmpty())
                throw new IllegalArgumentException("password가 입력되지 않았습니다.");

            User foundUser = database.findUserById(user.getUserId());
            if (foundUser == null)
                throw new IllegalArgumentException("존재하지 않는 아이디입니다.");

            if (!foundUser.getPassword().equals(user.getPassword())) // TODO: PW 일급객체 만들어 PW관련 로직 넣기
                throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");

            // TODO: 쿠키, 세션으로 로그인 유지

            return new HttpResponse.Builder()
                    .status(HttpStatus.OK)
                    .setHeader("Location", "/")
                    .build();

        } catch (IllegalArgumentException e) {
            String message = JSON.stringify(Map.of("message", e.getMessage())); // TODO: 에러 메세지 응답 중복 부분 클래스로 추출
            return new HttpResponse.Builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .contentType(ContentType.JSON)
                    .body(message)
                    .build();
        }
    }
}
