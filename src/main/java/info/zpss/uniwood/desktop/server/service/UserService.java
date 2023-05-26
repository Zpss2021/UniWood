package info.zpss.uniwood.desktop.server.service;

import info.zpss.uniwood.desktop.server.model.User;
import info.zpss.uniwood.desktop.server.service.Impl.UserServiceImpl;

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
