package info.zpss.uniwood.desktop.server.model;

import java.util.List;

// TODO 用户
public class User {
    private Integer id;
    private String username;
    private String password;
    private String avatar;
    private String university;
    private String status;
    private List<User> followers;   // 关注我的人
    private List<User> followings;  // 我关注的人
    private List<Zone> zones;
    private List<Post> posts;

    public User() {
    }

    public User(Integer id, String username, String password, String avatar, String university) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.avatar = avatar;
        this.university = university;
    }

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getUniversity() {
        return university;
    }

    public String getStatus() {
        return status;
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

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setFollowers(List<User> followers) {
        this.followers = followers;
    }

    public void setFollowings(List<User> followings) {
        this.followings = followings;
    }

    public void setZones(List<Zone> zones) {
        this.zones = zones;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", avatar='" + avatar + '\'' +
                ", university='" + university + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
