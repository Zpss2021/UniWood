package info.zpss.uniwood.client.controller;

import info.zpss.uniwood.client.entity.User;
import info.zpss.uniwood.client.model.MainModel;
import info.zpss.uniwood.client.util.interfaces.Controller;
import info.zpss.uniwood.client.view.MainView;
import info.zpss.uniwood.client.view.window.MainWindow;

public class MainController implements Controller<MainModel, MainView> {
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
    public MainModel getModel() {
        return model;
    }


    @Override
    public MainView getView() {
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
