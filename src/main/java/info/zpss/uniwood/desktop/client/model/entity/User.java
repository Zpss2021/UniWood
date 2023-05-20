package info.zpss.uniwood.desktop.client.model.entity;

import info.zpss.uniwood.desktop.client.util.Entity;
import info.zpss.uniwood.desktop.common.Command;
import info.zpss.uniwood.desktop.common.ProtoMsg;

import java.util.List;

// TODO 用户
public class User implements Entity {
    private Integer id;
    private String username;
    private String avatar;
    private String university;
    private String status;
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

    @Override
    public void update(ProtoMsg msg) {
        if (msg.cmd == Command.LOGIN_SUCCESS) {
            id = Integer.valueOf(msg.args[0]);
            username = msg.args[1];
            avatar = msg.args[2];
            university = msg.args[3];
        }
        // TODO
    }
}
