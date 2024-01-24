package controller;

import db.Database;
import exception.BadRequestException;
import model.User.User;
import model.User.UserBuilder;
import util.http.*;

public class UserController {

    private final Database database;

    public UserController(Database database) {
        this.database = database;
    }

    public HttpResponse signUp(HttpRequest request) {
        User user = UserBuilder.fromStringifiedJson(request.getBody());

        if (user.getUserId().isEmpty())
            throw new BadRequestException("userId가 입력되지 않았습니다.");
        if (user.getPassword().isEmpty())
            throw new BadRequestException("password가 입력되지 않았습니다.");
        if (user.getName().isEmpty())
            throw new BadRequestException("name이 입력되지 않았습니다.");
        if (user.getEmail().isEmpty())
            throw new BadRequestException("email이 입력되지 않았습니다.");

        if (database.findUserById(user.getUserId()) != null)
            throw new BadRequestException("이미 존재하는 아이디입니다.");

        database.addUser(user); // TODO: 비밀번호 암호화

        return new HttpResponse.Builder()
                .status(HttpStatus.CREATED)
                .setHeader("Location", "/")
                .build();
    }

    public HttpResponse login(HttpRequest request) {
        User user = UserBuilder.fromStringifiedJson(request.getBody());

        if (user.getUserId().isEmpty())
            throw new BadRequestException("userId가 입력되지 않았습니다.");
        if (user.getPassword().isEmpty())
            throw new BadRequestException("password가 입력되지 않았습니다.");

        User foundUser = database.findUserById(user.getUserId());
        if (foundUser == null)
            throw new BadRequestException("존재하지 않는 아이디입니다.");

        if (!foundUser.getPassword().equals(user.getPassword())) // TODO: PW 일급객체 만들어 PW관련 로직 넣기
            throw new BadRequestException("비밀번호가 일치하지 않습니다.");

        // TODO: 쿠키, 세션으로 로그인 유지

        return new HttpResponse.Builder()
                .status(HttpStatus.OK)
                .setHeader("Location", "/")
                .build();
    }
}
