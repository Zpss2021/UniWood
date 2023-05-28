package info.zpss.uniwood.desktop.client.model;

import info.zpss.uniwood.desktop.client.entity.User;
import info.zpss.uniwood.desktop.client.util.interfaces.Model;

public class MainModel implements Model {
    private User loginUser;

    public MainModel() {
        this.loginUser = new User();
    }

    public User getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(User loginUser) {
        this.loginUser = loginUser;
    }
}
