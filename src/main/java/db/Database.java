package db;

import model.User.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

public class Database {

    private static final Logger logger = LoggerFactory.getLogger(Database.class);

    private final ConcurrentHashMap<String, User> users = new ConcurrentHashMap<>();

    public void addUser(User user) {
        users.put(user.getUserId(), user);
        logger.debug("[회원 생성] id: {}", user.getUserId());
    }

    public User findUserById(String userId) {
        return users.get(userId);
    }

    public Collection<User> findAll() {
        return users.values();
    }
}
