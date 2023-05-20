package info.zpss.uniwood.desktop.client.model.view;

import info.zpss.uniwood.desktop.client.model.entity.User;

public class MainModel {
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
