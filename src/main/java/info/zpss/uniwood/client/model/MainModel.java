package info.zpss.uniwood.client.model;

import info.zpss.uniwood.client.entity.User;
import info.zpss.uniwood.client.util.interfaces.Model;

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

    @Override
    public void init() {
        loginUser = null;
    }
}
