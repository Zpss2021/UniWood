package info.zpss.uniwood.client.model;

import info.zpss.uniwood.client.Main;
import info.zpss.uniwood.client.controller.UserCenterController;
import info.zpss.uniwood.client.entity.Post;
import info.zpss.uniwood.client.util.interfaces.Model;
import info.zpss.uniwood.common.Command;
import info.zpss.uniwood.common.MsgProto;

import java.util.List;
import java.util.concurrent.TimeoutException;

public class UserFavorListModel implements Model {
    private List<Post> favors;

    public UserFavorListModel() {
        init();
    }

    public Integer getUserId() {
        return UserCenterController.getInstance().getModel().getId();
    }

    public List<Post> getFavors(int count) throws TimeoutException, InterruptedException {
        if (favors != null)
            return favors;
        if (count == 0)
            new Thread(() -> Main.connection().send(MsgProto.build(Command.FAVOR_LIST,
                    getUserId().toString()))).start();
        if (count > Main.maxWaitCycle())
            throw new TimeoutException();
        Thread.sleep(Main.waitCycleMills(count));
        return getFavors(count + 1);
    }

    public void setFavors(List<Post> favors) {
        this.favors = favors;
    }

    @Override
    public void init() {
        favors = null;
    }
}
