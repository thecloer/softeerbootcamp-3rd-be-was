package controller;

import db.Database;
import exception.BadRequestException;
import model.Post.Post;
import model.Post.PostBuilder;
import session.Session;
import util.UriHelper;
import util.http.HttpRequest;
import util.http.HttpResponse;
import util.http.HttpStatus;

public class PostController {

    private final Database database;

    public PostController(Database database) {
        this.database = database;
    }

    public HttpResponse createPost(HttpRequest request) {
        Post body = PostBuilder.fromStringifiedJson(request.getBody());

        if (body.getTitle() == null || body.getTitle().isEmpty())
            throw new BadRequestException("title이 입력되지 않았습니다.");
        if (body.getContents() == null || body.getContents().isEmpty())
            throw new BadRequestException("content가 입력되지 않았습니다.");

        Session session = request.getSession();

        String userId = session.getAttribute("userId");
        String authorName = session.getAttribute("username");

        Post post = new PostBuilder()
                .title(body.getTitle())
                .authorId(userId)
                .author(authorName)
                .content(body.getContents())
                .build();

        database.addPost(post);

        String postUri = "/post/show.html?postId=" + UriHelper.encode(post.getPostId());

        return new HttpResponse()
                .setStatus(HttpStatus.CREATED)
                .setField("Location", postUri);
    }
}
