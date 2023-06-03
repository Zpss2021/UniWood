package info.zpss.uniwood.client.model;

import info.zpss.uniwood.client.Main;
import info.zpss.uniwood.client.entity.Post;
import info.zpss.uniwood.client.entity.User;
import info.zpss.uniwood.client.entity.Zone;
import info.zpss.uniwood.client.util.interfaces.Model;
import info.zpss.uniwood.common.Command;
import info.zpss.uniwood.common.MsgProto;

import java.util.List;
import java.util.concurrent.TimeoutException;

public class MainModel implements Model {
    private User loginUser;

    public MainModel() {
        this.init();
    }

    public User getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(User loginUser) {
        this.loginUser = loginUser;
    }

    public List<Zone> getZones(int count) throws InterruptedException, TimeoutException {
        if (loginUser.getZones() != null)
            return loginUser.getZones();
        if (count == 0)
            new Thread(() -> Main.connection().send(MsgProto.build(Command.ZONE_LIST,
                    loginUser.getId().toString()))).start();
        if (count > Main.maxWaitCycle())
            throw new TimeoutException();
        Thread.sleep(Main.waitCycleMills(count));
        return getZones(count + 1);
    }

    public List<Post> getPosts(Integer zoneID, int count) throws InterruptedException, TimeoutException {
        if (loginUser.getPosts() != null)
            return loginUser.getPosts();
        if (count == 0)
            new Thread(() -> Main.connection().send(MsgProto.build(Command.ZONE_POST,
                    zoneID.toString()))).start();
        if (count > Main.maxWaitCycle())
            throw new TimeoutException();
        Thread.sleep(Main.waitCycleMills(count));
        return getPosts(zoneID, count + 1);
    }

    @Override
    public void init() {
        loginUser = null;
    }
}
