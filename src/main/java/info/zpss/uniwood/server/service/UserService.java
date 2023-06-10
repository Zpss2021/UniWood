package info.zpss.uniwood.server.service;

import info.zpss.uniwood.server.entity.User;
import info.zpss.uniwood.server.service.impl.UserServiceImpl;

import java.util.List;

public interface UserService {
    static UserService getInstance() {
        return UserServiceImpl.getInstance();
    }

    User login(String username, String password);

    User register(String username, String password, String university, String avatarBase64);

    void onlineUser(Integer userId);

    void offlineUser(Integer userId);

    void offlineAll();

    void disableUser(Integer userId);

    User getUser(Integer userId);

    List<User> getFollowings(Integer userId);

    List<User> getFollowers(Integer userId);

    User editUser(Integer userId, String username, String password, String university, String avatarBase64);

    void addFavor(Integer userId, Integer postId);

    void removeFavor(Integer userId, Integer postId);
}
