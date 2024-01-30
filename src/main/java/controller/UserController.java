package controller;

import db.Database;
import exception.BadRequestException;
import model.User.User;
import model.User.UserBuilder;
import session.Session;
import session.SessionManager;
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

        return new HttpResponse()
                .setStatus(HttpStatus.CREATED)
                .setField("Location", "/");
    }

    public HttpResponse login(HttpRequest request) {
        User body = UserBuilder.fromStringifiedJson(request.getBody());

        if (body.getUserId().isEmpty())
            throw new BadRequestException("userId가 입력되지 않았습니다.");
        if (body.getPassword().isEmpty())
            throw new BadRequestException("password가 입력되지 않았습니다.");

        User foundUser = database.findUserById(body.getUserId());
        if (foundUser == null)
            throw new BadRequestException("존재하지 않는 아이디입니다.");

        if (!foundUser.getPassword().equals(body.getPassword())) // TODO: PW 일급객체 만들어 PW관련 로직 넣기
            throw new BadRequestException("비밀번호가 일치하지 않습니다.");

        Session session = SessionManager.createSession();
        session.setAttribute("userId", body.getUserId()); // TODO: 세션에 들어간 데이터 "userId" 상수로 관리
        session.setAttribute("username", foundUser.getName());
        String cookie = SessionManager.toCookieString(session);

        return new HttpResponse()
                .setStatus(HttpStatus.FOUND)
                .setField("Location", "/") // TODO: redirect("/");
                .addCookie(cookie);
    }

    public HttpResponse logout(HttpRequest request) {
        Session session = request.getSession();
        request.setSession(null);
        SessionManager.distroySession(session.getSessionId());
        String cookie = SessionManager.toCookieString(session, 0L);

        return new HttpResponse()
                .setStatus(HttpStatus.FOUND)
                .setField("Location", "/")
                .addCookie(cookie);
    }
}
