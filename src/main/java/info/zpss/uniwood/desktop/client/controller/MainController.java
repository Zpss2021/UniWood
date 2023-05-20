package info.zpss.uniwood.desktop.client.controller;

import info.zpss.uniwood.desktop.client.model.entity.User;
import info.zpss.uniwood.desktop.client.model.view.MainModel;
import info.zpss.uniwood.desktop.client.util.Controller;
import info.zpss.uniwood.desktop.client.util.View;
import info.zpss.uniwood.desktop.client.view.MainView;
import info.zpss.uniwood.desktop.client.view.window.MainWindow;

public class MainController implements Controller {
    private static final MainController INSTANCE;
    private static final MainModel model;
    private static final MainView view;

    static  {
        model = new MainModel();
        view = new MainWindow();
        INSTANCE = new MainController();
    }

    private MainController() {
    }

    public static MainController getInstance() {
        return INSTANCE;
    }

    @Override
    public void register() {
        view.getLoginButton().addActionListener(e -> userLogin());
        view.getRegisterButton().addActionListener(e -> userRegister());
    }

    @Override
    public View getView() {
        return view;
    }

    private void userLogin() {
        LoginController.getInstance().register();
        LoginController.getInstance().getView().setParent(view.getComponent());
        LoginController.getInstance().getView().showWindow();
    }

    private void userRegister() {

    }

    public void loginSuccess(User loginUser) {
        model.setLoginUser(loginUser);
        view.getLoginButton().setText("欢迎您，" + loginUser.getUsername());
    }
}
