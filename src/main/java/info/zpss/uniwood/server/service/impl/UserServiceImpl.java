package info.zpss.uniwood.server.service.impl;

import info.zpss.uniwood.server.dao.impl.UserDaoImpl;
import info.zpss.uniwood.server.dao.UserDao;
import info.zpss.uniwood.server.entity.User;
import info.zpss.uniwood.server.entity.UserPost;
import info.zpss.uniwood.server.entity.UserZone;
import info.zpss.uniwood.server.service.UserService;
import info.zpss.uniwood.server.service.ZoneService;

import java.sql.SQLException;
import java.util.List;

public class UserServiceImpl implements UserService {
    private static final UserServiceImpl INSTANCE;
    private static final UserDao userDao;

    static {
        INSTANCE = new UserServiceImpl();
        userDao = UserDaoImpl.getInstance();
    }

    private UserServiceImpl() {
    }

    public static UserServiceImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public User login(String username, String password) {
        return userDao.login(username, password);
    }

    @Override
    public User register(String username, String password, String university, String avatarBase64) {
        User registerUser = userDao.register(username, password, university, avatarBase64);
        if (registerUser != null) {
            UserZone defaultZone = new UserZone();
            UserZone universityZone = new UserZone();
            defaultZone.setUserId(registerUser.getId());
            universityZone.setUserId(registerUser.getId());
            defaultZone.setZoneId(ZoneService.getInstance().getZoneID("广场"));
            universityZone.setZoneId(ZoneService.getInstance().getZoneID(university));
            userDao.addUserZone(defaultZone);
            userDao.addUserZone(universityZone);
        }
        return registerUser;
    }

    @Override
    public void onlineUser(Integer userId) {
        userDao.updateStatus(userId, "ONLINE");
    }

    @Override
    public void offlineUser(Integer userId) {
        userDao.updateStatus(userId, "OFFLINE");
    }

    @Override
    public void offlineAll() {
        userDao.offlineAll();
    }

    @Override
    public void disableUser(Integer userId) {
        userDao.updateStatus(userId, "DISABLED");
    }

    @Override
    public User getUser(Integer userId) {
        return userDao.getUser(userId);
    }

    @Override
    public List<User> getFollowings(Integer userId) {
        return userDao.getFollowings(userId);
    }

    @Override
    public List<User> getFollowers(Integer userId) {
        return userDao.getFollowers(userId);
    }

    @Override
    public User editUser(Integer userId, String username, String password, String university, String avatarBase64) {
        User user = userDao.getUser(userId);
        if (user == null)
            return null;
        if (username != null)
            user.setUsername(username);
        if (password != null)
            user.setPassword(password);
        if (university != null)
            user.setUniversity(university);
        if (avatarBase64 != null)
            user.setAvatar(avatarBase64);
        try {
            userDao.updateUser(user);
        } catch (SQLException e) {
            return null;
        }
        return user;
    }

    @Override
    public void addFavor(Integer userId, Integer postId) {
        UserPost userPost = new UserPost();
        userPost.setUserId(userId);
        userPost.setPostId(postId);
        userDao.addUserPost(userPost);
    }

    @Override
    public void removeFavor(Integer userId, Integer postId) {
        UserPost userPost = new UserPost();
        userPost.setUserId(userId);
        userPost.setPostId(postId);
        userDao.removeUserPost(userPost);
    }
}
