package info.zpss.uniwood.desktop.client.model;

import java.util.List;

// TODO 用户
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

    public User(Integer id, String username, String avatar, String university) {
        this.id = id;
        this.username = username;
        this.avatar = avatar;
        this.university = university;
    }

//    public User(Integer id, String username, String avatar, String university,
//                List<User> followers, List<User> followings, List<Zone> zones, List<Post> posts) {
//        this.id = id;
//        this.username = username;
//        this.avatar = avatar;
//        this.university = university;
//        this.followers = followers;
//        this.followings = followings;
//        this.zones = zones;
//        this.posts = posts;
//    }

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getUniversity() {
        return university;
    }

    public List<User> getFollowers() {
        return followers;
    }

    public List<User> getFollowings() {
        return followings;
    }

    public List<Zone> getZones() {
        return zones;
    }

    public List<Post> getPosts() {
        return posts;
    }
}
