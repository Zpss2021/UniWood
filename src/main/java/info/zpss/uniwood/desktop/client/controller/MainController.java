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
    private static boolean registered;

    static {
        model = new MainModel();
        view = new MainWindow();
        INSTANCE = new MainController();
        registered = false;
    }

    private MainController() {
    }

    public static MainController getInstance() {
        return INSTANCE;
    }

    @Override
    public void register() {
        if (!registered) {
            registered = true;
            view.getLoginButton().addActionListener(e -> userLogin());
            view.getRegisterButton().addActionListener(e -> userRegister());
        }
    }

    @Override
    public View getView() {
        return view;
    }

    private void userLogin() {
        LoginController.getInstance().register();
        LoginController.getInstance().getView().showWindow(view.getComponent());
    }

    private void userRegister() {
        RegisterController.getInstance().register();
        RegisterController.getInstance().getView().showWindow(view.getComponent());
    }

    public void loginSuccess(User loginUser) {
        model.setLoginUser(loginUser);
        view.getLoginButton().setText(loginUser.getUsername());
        view.getUserPanel().setAvatar(loginUser.getAvatar());
    }
}
