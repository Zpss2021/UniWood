package info.zpss.uniwood.desktop.server.mapper;

import info.zpss.uniwood.desktop.server.model.*;

import java.util.List;

public interface UserMapper {
    User getUser(Integer userID);

    User login(String username, String password);

    List<User> getUsers();

    String getStatus(Integer userID);

    List<User> getFollowers(Integer userID);

    List<User> getFollowings(Integer userID);

    void addUser(User user);

    void addUserZone(UserZone userZone);

    void addUserPost(UserPost userPost);

    void addFollow(Follow follow);

//    void deleteUser(Integer id);  // 不提供删除用户的功能，只提供禁用用户的功能（即将用户状态设置为“已禁用”）

    void updateUser(User user);

    void updateStatus(Integer userID, String status);
}