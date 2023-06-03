package info.zpss.uniwood.client.entity;

import info.zpss.uniwood.client.Main;
import info.zpss.uniwood.client.builder.PostBuilder;
import info.zpss.uniwood.client.builder.UserBuilder;
import info.zpss.uniwood.client.builder.ZoneBuilder;
import info.zpss.uniwood.common.MsgProto;
import info.zpss.uniwood.client.util.interfaces.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

// TODO 用户
public class User implements Entity {
    private Integer id;
    private String username;
    private String university;
    private String avatar;
    private String status;
    private List<User> followings;  // 关注
    private List<User> followers;   // 粉丝
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

    public List<User> getFollowings() {
        return followings;
    }

    public List<User> getFollowers() {
        return followers;
    }

    public List<Zone> getZones() {
        return zones;
    }

    public List<Post> getPosts() {
        return posts;
    }

    @Override
    public void update(MsgProto msg) {
        try {
            switch (msg.cmd) {
                case USER_INFO:
                case LOGIN_SUCCESS:
                case REGISTER_SUCCESS:
                    id = Integer.valueOf(msg.args[0]);
                    username = msg.args[1];
                    university = msg.args[2];
                    avatar = msg.args[3];
                    break;
                case FOLW_LIST:
                    ArrayList<User> folw = new ArrayList<>();
                    for (String s : msg.args)
                        folw.add(UserBuilder.getInstance().get(Integer.parseInt(s)));
                    this.followings = folw;
                    break;
                case FANS_LIST:
                    ArrayList<User> fans = new ArrayList<>();
                    for (String s : msg.args)
                        fans.add(UserBuilder.getInstance().get(Integer.parseInt(s)));
                    this.followers = fans;
                    break;
                case POST_LIST:
                    ArrayList<Post> post = new ArrayList<>();
                    for (String s : msg.args)
                        post.add(PostBuilder.getInstance().get(Integer.parseInt(s)));
                    this.posts = post;
                    break;
                case ZONE_LIST:
                    ArrayList<Zone> zone = new ArrayList<>();
                    for (String s : msg.args)
                        zone.add(ZoneBuilder.getInstance().get(Integer.parseInt(s)));
                    this.zones = zone;
                    break;
            }
        } catch (InterruptedException | TimeoutException e) {
            Main.logger().add(String.format("用户%d信息更新失败", id), Thread.currentThread());
            Main.logger().add(e, Thread.currentThread());
        }
    }
}
