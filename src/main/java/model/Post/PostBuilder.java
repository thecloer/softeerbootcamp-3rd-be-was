package model.Post;

import util.ModelConverter;

import java.util.Date;
import java.util.UUID;

public class PostBuilder {
    private String title;
    private String author;
    private String content;

    static public Post fromStringifiedJson(String stringifiedJson) {
        return ModelConverter.convert(stringifiedJson, Post.class);
    }

    public PostBuilder title(String title) {
        this.title = title;
        return this;
    }

    public PostBuilder author(String author) {
        this.author = author;
        return this;
    }

    public PostBuilder content(String content) {
        this.content = content;
        return this;
    }

    public Post build() {
        String postId = UUID.randomUUID().toString();
        return new Post(postId, title, author, content, new Date());
    }
}
