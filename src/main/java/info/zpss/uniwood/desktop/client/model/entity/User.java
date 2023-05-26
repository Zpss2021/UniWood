package info.zpss.uniwood.desktop.client.model.entity;

import info.zpss.uniwood.desktop.client.util.Entity;
import info.zpss.uniwood.desktop.common.Command;
import info.zpss.uniwood.desktop.common.ProtoMsg;

import java.util.List;

// TODO 用户
public class User implements Entity {
    private Integer id;
    private String username;
    private String university;
    private String avatar;
    private String status;
    private List<User> followers;
    private List<User> followings;
    private List<Zone> zones;
    private List<Post> posts;

    public User() {
    }

    public User(Integer id, String username, String university, String avatar) {
        this.id = id;
        this.username = username;
        this.university = university;
        this.avatar = avatar;
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
            university = msg.args[2];
            avatar = msg.args[3];
        } else if (msg.cmd == Command.REGISTER_SUCCESS) {
            id = Integer.valueOf(msg.args[0]);
            username = msg.args[1];
            university = msg.args[2];
            avatar = msg.args[3];
        }
    }
}
