package info.zpss.uniwood.desktop.client.model;

import java.util.List;

// 用户
public class User {
    private Integer id;
    private String username;
//    private String password;
    private String avatar;
    private String university;
    private List<User> followers;
    private List<User> followings;
    private List<Zone> zones;
    private List<Post> posts;

    public User() {
    }
}
