package info.zpss.uniwood.desktop.server.mapper;

import info.zpss.uniwood.desktop.server.model.*;

import java.util.List;

public interface UserMapper {
    User getUser(Integer userID);
    List<User> getUsers();
    List<User> getUserFollowers(Integer userID);
    List<User> getUserFollowings(Integer userID);
    List<Zone> getUserZones(Integer userID);
    List<Post> getUserPosts(Integer userID);
}
