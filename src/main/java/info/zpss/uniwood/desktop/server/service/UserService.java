package info.zpss.uniwood.desktop.server.service;

import info.zpss.uniwood.desktop.server.model.User;
import info.zpss.uniwood.desktop.server.service.Impl.UserServiceImpl;

public interface UserService {
    // TODO
    static UserService getInstance() {
        return UserServiceImpl.getInstance();
    }
    User login(String username, String password);
    void onlineUser(Integer userId);
    void offlineUser(Integer userId);
    void offlineAll();
    void disableUser(Integer userId);
}
