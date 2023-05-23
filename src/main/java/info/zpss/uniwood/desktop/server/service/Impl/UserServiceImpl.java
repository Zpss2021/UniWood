package info.zpss.uniwood.desktop.server.service.Impl;

import info.zpss.uniwood.desktop.server.mapper.Impl.UserMapperImpl;
import info.zpss.uniwood.desktop.server.mapper.UserMapper;
import info.zpss.uniwood.desktop.server.model.User;
import info.zpss.uniwood.desktop.server.service.UserService;

public class UserServiceImpl implements UserService {
    private static final UserServiceImpl INSTANCE;
    private static final UserMapper userMapper;

    static {
        INSTANCE = new UserServiceImpl();
        userMapper = UserMapperImpl.getInstance();
    }

    private UserServiceImpl() {
    }

    public static UserServiceImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public User login(String username, String password) {
        return userMapper.login(username, password);
    }

    @Override
    public void onlineUser(Integer userId) {
        userMapper.updateStatus(userId, "ONLINE");
    }

    @Override
    public void offlineUser(Integer userId) {
        userMapper.updateStatus(userId, "OFFLINE");
    }

    @Override
    public void offlineAll() {
        userMapper.offlineAll();
    }

    @Override
    public void disableUser(Integer userId) {
        userMapper.updateStatus(userId, "DISABLED");
    }
    // TODO
}
