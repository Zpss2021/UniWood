package info.zpss.uniwood.server.service;

import info.zpss.uniwood.server.entity.User;
import info.zpss.uniwood.server.service.impl.UserServiceImpl;

public interface UserService {
    // TODO
    static UserService getInstance() {
        return UserServiceImpl.getInstance();
    }
    User login(String username, String password);
    User register(String username, String password, String university, String avatarBase64);
    void onlineUser(Integer userId);
    void offlineUser(Integer userId);
    void offlineAll();
    void disableUser(Integer userId);
}
