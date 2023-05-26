package info.zpss.uniwood.desktop.server.service.Impl;

import info.zpss.uniwood.desktop.server.mapper.Impl.UserMapperImpl;
import info.zpss.uniwood.desktop.server.mapper.UserMapper;
import info.zpss.uniwood.desktop.server.model.User;
import info.zpss.uniwood.desktop.server.model.UserZone;
import info.zpss.uniwood.desktop.server.service.UserService;
import info.zpss.uniwood.desktop.server.service.ZoneService;

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
    public User register(String username, String password, String university, String avatarBase64) {
        User registerUser = userMapper.register(username, password, university, avatarBase64);
        if (registerUser != null) {
            UserZone defaultZone = new UserZone();
            UserZone universityZone = new UserZone();
            defaultZone.setUserId(registerUser.getId());
            universityZone.setUserId(registerUser.getId());
            defaultZone.setZoneId(ZoneService.getInstance().getZoneID("广场"));
            universityZone.setZoneId(ZoneService.getInstance().getZoneID(university));
            userMapper.addUserZone(defaultZone);
            userMapper.addUserZone(universityZone);
        }
        return registerUser;
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
