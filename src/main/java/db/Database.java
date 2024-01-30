package db;

import model.Post.Post;
import model.User.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

public class Database {

    private static final Logger logger = LoggerFactory.getLogger(Database.class);

    private final ConcurrentHashMap<String, User> users = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Post> posts = new ConcurrentHashMap<>();

    public void addUser(User user) {
        users.put(user.getUserId(), user);
        logger.debug("[회원 생성] id: {}", user.getUserId());
    }

    public User findUserById(String userId) {
        return users.get(userId);
    }

    public Collection<User> findAllUsers() {
        return users.values();
    }

    public void addPost(Post post) {
        posts.put(post.getPostId(), post);
        logger.debug("[게시글 생성] id: {}", post.getTitle());
    }

    public Post findPostById(String postId) {
        return posts.get(postId);
    }

    public Collection<Post> findAllPosts() {
        return posts.values();
    }
}
