package info.zpss.uniwood.server.dao;

import info.zpss.uniwood.server.entity.Follow;
import info.zpss.uniwood.server.entity.User;
import info.zpss.uniwood.server.entity.UserPost;
import info.zpss.uniwood.server.entity.UserZone;

import java.sql.SQLException;
import java.util.List;

public interface UserDao {
    User getUser(Integer userID);

    User login(String username, String password);

    User register(String username, String password, String university, String avatarBase64);

    List<User> getUsers();

    String getStatus(Integer userID);

    List<User> getFollowers(Integer userID);

    List<User> getFollowings(Integer userID);

    void addUser(User user);

    void addUserZone(UserZone userZone);

    void addUserPost(UserPost userPost);

    void addFollow(Follow follow);

//    void deleteUser(Integer id);  // 不提供删除用户的功能，只提供禁用用户的功能（即将用户状态设置为“已禁用”）

    void updateUser(User user) throws SQLException;

    void updateStatus(Integer userID, String status);

    void offlineAll();

    void removeUserPost(UserPost userPost);
}