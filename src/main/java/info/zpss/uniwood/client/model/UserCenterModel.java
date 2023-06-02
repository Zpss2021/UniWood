package info.zpss.uniwood.client.model;

import info.zpss.uniwood.client.Main;
import info.zpss.uniwood.client.entity.Post;
import info.zpss.uniwood.client.entity.User;
import info.zpss.uniwood.client.util.interfaces.Model;
import info.zpss.uniwood.common.Command;
import info.zpss.uniwood.common.MsgProto;

import java.util.List;

public class UserCenterModel implements Model {
    private User user;

    public UserCenterModel() {
        this.init();
    }

    public Integer getId() {
        return user.getId();
    }

    public String getUsername() {
        return user.getUsername();
    }

    public String getAvatar() {
        return user.getAvatar();
    }

    public String getUniversity() {
        return user.getUniversity();
    }

    public List<User> getFollowings() throws InterruptedException {
        if (user.getFollowings() != null)
            return user.getFollowings();
        new Thread(() -> Main.connection().send(MsgProto.build(Command.FOLW_LIST, user.getId().toString()))).start();
        Thread.sleep(200);
        return getFollowings();
    }

    public List<User> getFollowers() throws InterruptedException {
        if (user.getFollowers() != null)
            return user.getFollowers();
        new Thread(() -> Main.connection().send(MsgProto.build(Command.FANS_LIST, user.getId().toString()))).start();
        Thread.sleep(200);
        return getFollowers();
    }

    public List<Post> getPosts() throws InterruptedException {
        if (user.getPosts() != null)
            return user.getPosts();
        new Thread(() -> Main.connection().send(MsgProto.build(Command.POST_LIST, user.getId().toString()))).start();
        Thread.sleep(200);
        return getPosts();
    }

    public void update(MsgProto msg) {
        user.update(msg);
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void init() {
        this.user = null;
    }
}
