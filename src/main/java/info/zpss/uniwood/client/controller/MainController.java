package info.zpss.uniwood.client.controller;

import info.zpss.uniwood.client.entity.User;
import info.zpss.uniwood.client.model.MainModel;
import info.zpss.uniwood.client.util.interfaces.Controller;
import info.zpss.uniwood.client.view.MainView;
import info.zpss.uniwood.client.view.window.MainWindow;
import info.zpss.uniwood.client.view.dialog.UserCenterDialog;
import info.zpss.uniwood.common.Command;
import info.zpss.uniwood.client.Main;
import info.zpss.uniwood.common.MsgProto;

import java.awt.event.ActionListener;

public class MainController implements Controller<MainModel, MainView> {
    private static final MainController INSTANCE;
    private static final MainModel model;
    private static final MainView view;

    static {
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
    public MainModel getModel() {
        return model;
    }

    @Override
    public MainView getView() {
        return view;
    }

    @Override
    public void register() {
        for (ActionListener l : view.getLoginOrUserCenterButton().getActionListeners())
            view.getLoginOrUserCenterButton().removeActionListener(l);
        for (ActionListener l : view.getRegisterOrLogoutButton().getActionListeners())
            view.getRegisterOrLogoutButton().removeActionListener(l);
        ActionListener loginListener = e -> userLogin(),
                registerListener = e -> userRegister(),
                userCenterListener = e -> userCenter(),
                logoutListener = e -> userLogout();
        if (model.getLoginUser() != null) {
            view.getLoginOrUserCenterButton().addActionListener(userCenterListener);
            view.getRegisterOrLogoutButton().addActionListener(logoutListener);
        } else {
            view.getRegisterOrLogoutButton().addActionListener(registerListener);
            view.getLoginOrUserCenterButton().addActionListener(loginListener);
        }
    }

    private void userLogin() {
        LoginController.getInstance().register();
        LoginController.getInstance().getView().showWindow(view.getComponent());
    }

    private void userRegister() {
        RegisterController.getInstance().register();
        RegisterController.getInstance().getView().showWindow(view.getComponent());
    }

    private void userCenter() { // TODO
        UserCenterDialog window = new UserCenterDialog(view.getComponent());
        window.showWindow(view.getComponent());
    }

    private void userLogout() {
        Main.connection().send(MsgProto.build(Command.LOGOUT));
        model.init();
        view.getUserPanel().setLogout();
        register();
    }

    public void loginSuccess(User loginUser) {
        model.setLoginUser(loginUser);
        view.getUserPanel().setLogin(loginUser.getAvatar());
        register();
    }
}
