package controller;

import db.Database;
import exception.BadRequestException;
import exception.RedirectException;
import model.Post.Post;
import model.User.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pipeline.responseProcessor.templateEngine.TemplateComponents;
import session.Session;
import util.FileReader;
import util.Formatter;
import util.UriHelper;
import util.http.*;

import java.io.IOException;
import java.util.Collection;

public class ResourceController {

    private static final Logger logger = LoggerFactory.getLogger(ResourceController.class);

    private static final String TEMPLATE = "src/main/resources/templates";
    private static final String STATIC = "src/main/resources/static";

    private final Database database;

    public ResourceController(Database database) {
        this.database = database;
    }

    public HttpResponse homePage(HttpRequest request) {
        HttpResponse response = makeBaseTemplateResponse("/index.html"); // TODO: 로그인 상태에 따라 컨트롤러 분리
        response.setTemplateData("postListComponent", TemplateComponents.postList(database.findAllPosts()));
        if (!request.isLoggedIn())
            return response;

        Session session = request.getSession();
        String username = session.getAttribute("username");

        return response.setTemplateData("name", username);
    }

    public HttpResponse profilePage(HttpRequest request) {
        HttpResponse response = makeBaseTemplateResponse(request.getPath());
        if (!request.isLoggedIn())
            return response;

        Session session = request.getSession();
        String userId = session.getAttribute("userId");
        if (userId == null)
            throw new RedirectException("/user/login.html");

        User user = database.findUserById(userId);
        if (user == null)
            throw new BadRequestException("존재하지 않는 사용자입니다.");

        return response
                .setTemplateData("name", user.getName())
                .setTemplateData("email", user.getEmail());
    }

    public HttpResponse postPage(HttpRequest request) {
        String queryParamPostId = request.getQueryParam("postid");
        if (queryParamPostId.isEmpty())
            throw new RedirectException("/index.html");

        String postId = UriHelper.decode(queryParamPostId);

        Post post = database.findPostById(postId);
        if (post == null)
            throw new BadRequestException("존재하지 않는 글입니다.");

        String createdDate = Formatter.dateToString(post.getCreatedAt());

        return makeBaseTemplateResponse(request.getPath())
                .setTemplateData("title", post.getTitle())
                .setTemplateData("author", post.getAuthor())
                .setTemplateData("contents", post.getContents())
                .setTemplateData("createdDate", createdDate);
    }

    public HttpResponse userListPage(HttpRequest request) {
        HttpResponse response = makeBaseTemplateResponse(request.getPath());
        if (!request.isLoggedIn())
            return response;

        Collection<User> users = database.findAllUsers();

        String userListComponent = TemplateComponents.userList(users);

        return response
                .setTemplateData("userListComponent", userListComponent);
    }

    public HttpResponse staticHandler(HttpRequest request) {
        String path = request.getPath();
        ContentType contentType = ContentType.getContentType(path);
        String base = (contentType == ContentType.HTML) ? TEMPLATE : STATIC;

        byte[] body = read(base + path);
        return new HttpResponse()
                .setStatus(HttpStatus.OK)
                .setContentType(contentType)
                .setBody(body);
    }

    private HttpResponse makeBaseTemplateResponse(String path) {
        return new HttpResponse()
                .setStatus(HttpStatus.OK)
                .setContentType(ContentType.HTML)
                .setBody(read(TEMPLATE + path));
    }

    private byte[] read(String path) {
        try {
            return FileReader.read(path);
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new RedirectException("/404.html");
        }
    }
}
