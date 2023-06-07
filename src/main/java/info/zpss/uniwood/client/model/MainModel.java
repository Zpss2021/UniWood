package info.zpss.uniwood.client.model;

import info.zpss.uniwood.client.Main;
import info.zpss.uniwood.client.controller.MainController;
import info.zpss.uniwood.client.entity.Post;
import info.zpss.uniwood.client.entity.User;
import info.zpss.uniwood.client.entity.Zone;
import info.zpss.uniwood.client.util.interfaces.Model;
import info.zpss.uniwood.common.Command;
import info.zpss.uniwood.common.MsgProto;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class MainModel implements Model {
    private User loginUser;
    private List<Post> zonePosts;
    private static final int pageSize = 2;
    private int fromPostCount;

    public MainModel() {
        this.init();
    }

    public User getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(User loginUser) {
        this.loginUser = loginUser;
    }

    public void setZonePosts(List<Post> zonePosts) {
        this.zonePosts = zonePosts;
    }

    public void setFromPostCount(int fromPostCount) {
        this.fromPostCount = fromPostCount;
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

    public List<Post> pageEnd() throws InterruptedException, TimeoutException {
        fromPostCount = 0;
        SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(MainController.getInstance()
                .getView().getComponent(), "已到达最后一页！"));
        return getPosts(0);
    }

    public List<Post> getNextPagePosts() throws InterruptedException, TimeoutException {
        if (zonePosts != null)
            zonePosts.clear();
        if (fromPostCount == 0)
            fromPostCount = pageSize;
        List<Post> posts = getPosts(0);
        fromPostCount += posts.size();
        return posts;
    }

    public List<Post> getPrevPagePosts() throws InterruptedException, TimeoutException {
        if (zonePosts != null)
            zonePosts.clear();
        fromPostCount -= pageSize;
        if (fromPostCount < 0) {
            fromPostCount = 0;
            SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(MainController.getInstance()
                    .getView().getComponent(), "已到达第一页！"));
        }
        return getPosts(0);
    }

    public List<Post> getPosts(int count) throws InterruptedException, TimeoutException {
        int zoneID = MainController.getInstance().getZoneID();
        if (zonePosts != null)
            if (zonePosts.size() != 0) {
                if (zonePosts.get(0).getZone().getId().equals(zoneID))
                    return zonePosts;
            } else {
                zonePosts = null;
                return new ArrayList<>();
            }
        if (count == 0)
            new Thread(() -> Main.connection().send(MsgProto.build(Command.ZONE_POST,
                    Integer.toString(zoneID),
                    Integer.toString(fromPostCount),
                    Integer.toString(pageSize)
            ))).start();
        if (count > Main.maxWaitCycle())
            throw new TimeoutException();
        Thread.sleep(Main.waitCycleMills(count));
        return getPosts(count + 1);
    }

    @Override
    public void init() {
        loginUser = null;
        zonePosts = null;
        fromPostCount = 0;
    }
}
