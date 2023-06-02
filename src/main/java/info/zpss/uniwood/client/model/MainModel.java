package info.zpss.uniwood.client.model;

import info.zpss.uniwood.client.Main;
import info.zpss.uniwood.client.entity.User;
import info.zpss.uniwood.client.entity.Zone;
import info.zpss.uniwood.client.util.interfaces.Model;
import info.zpss.uniwood.common.Command;
import info.zpss.uniwood.common.MsgProto;

import java.util.List;

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

    public List<Zone> getZones() throws InterruptedException {
        if(loginUser.getZones() != null)
            return loginUser.getZones();
        new Thread(() -> Main.connection().send(MsgProto.build(Command.ZONE_LIST,
                loginUser.getId().toString()))).start();
        Thread.sleep(200);
        return getZones();
    }

    @Override
    public void init() {
        loginUser = null;
    }
}
