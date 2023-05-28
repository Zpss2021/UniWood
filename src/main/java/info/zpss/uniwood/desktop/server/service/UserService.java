package info.zpss.uniwood.desktop.server.service;

import info.zpss.uniwood.desktop.server.entity.User;
import info.zpss.uniwood.desktop.server.service.impl.UserServiceImpl;

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
