package model.Post;

import annotation.Column;
import model.Model;

import java.util.Date;

public class Post extends Model {
    private String postId;
    @Column("title")
    private String title;
    private String authorId;
    private String author;
    @Column("contents")
    private String contents;
    private Date createdAt;

    private Post() {
        super();
    }

    public Post(String postId, String title, String authorId, String author, String contents, Date createdAt) {
        this.postId = postId;
        this.title = title;
        this.authorId = authorId;
        this.author = author;
        this.contents = contents;
        this.createdAt = createdAt;
    }

    public String getPostId() {
        return postId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthorId() {
        return authorId;
    }

    public String getAuthor() {
        return author;
    }

    public String getContents() {
        return contents;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("Post [postId=").append(postId)
                .append(", title=").append(title)
                .append(", authorId=").append(authorId)
                .append(", author=").append(author)
                .append(", content=").append(contents)
                .append("]")
                .toString();
    }
}
