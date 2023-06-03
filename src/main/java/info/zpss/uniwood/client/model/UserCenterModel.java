package info.zpss.uniwood.client.model;

import info.zpss.uniwood.client.Main;
import info.zpss.uniwood.client.entity.Post;
import info.zpss.uniwood.client.entity.User;
import info.zpss.uniwood.client.util.interfaces.Model;
import info.zpss.uniwood.common.Command;
import info.zpss.uniwood.common.MsgProto;

import java.util.List;
import java.util.concurrent.TimeoutException;

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

    public List<User> getFollowings(int count) throws InterruptedException, TimeoutException {
        if (user.getFollowings() != null)
            return user.getFollowings();
        if(count == 0)
            new Thread(() -> Main.connection().send(MsgProto.build(Command.FOLW_LIST, user.getId().toString()))).start();
        if(count > Main.maxWaitCycle())
            throw new TimeoutException();
        Thread.sleep(Main.waitCycleMills(count));
        return getFollowings(count + 1);
    }

    public List<User> getFollowers(int count) throws InterruptedException, TimeoutException {
        if (user.getFollowers() != null)
            return user.getFollowers();
        if(count == 0)
            new Thread(() -> Main.connection().send(MsgProto.build(Command.FANS_LIST, user.getId().toString()))).start();
        if(count > Main.maxWaitCycle())
            throw new TimeoutException();
        Thread.sleep(Main.waitCycleMills(count));
        return getFollowers(count + 1);
    }

    public List<Post> getPosts(int count) throws InterruptedException, TimeoutException {
        if (user.getPosts() != null)
            return user.getPosts();
        if(count == 0)
            new Thread(() -> Main.connection().send(MsgProto.build(Command.POST_LIST, user.getId().toString()))).start();
        if(count > Main.maxWaitCycle())
            throw new TimeoutException();
        Thread.sleep(Main.waitCycleMills(count));
        return getPosts(count + 1);
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
