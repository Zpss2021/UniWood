package info.zpss.uniwood.client.controller;

import info.zpss.uniwood.client.Main;
import info.zpss.uniwood.client.entity.User;
import info.zpss.uniwood.client.model.LoginModel;
import info.zpss.uniwood.client.util.interfaces.Controller;
import info.zpss.uniwood.client.view.LoginView;
import info.zpss.uniwood.client.view.dialog.LoginDialog;
import info.zpss.uniwood.common.Command;
import info.zpss.uniwood.common.MsgProto;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginController implements Controller<LoginModel, LoginView> {
    private static final LoginController INSTANCE;
    private static final LoginModel model;
    private static final LoginView view;
    private static boolean registered;

    static {
        model = new LoginModel();
        view = new LoginDialog(MainController.getInstance().getView().getComponent());
        INSTANCE = new LoginController();
        registered = false;
    }

    private LoginController() {
    }

    public static LoginController getInstance() {
        return INSTANCE;
    }

    @Override
    public LoginModel getModel() {
        return model;
    }


    @Override
    public LoginView getView() {
        return view;
    }

    @Override
    public void register() {
        if (!registered) {
            registered = true;
            view.getLoginButton().addActionListener(e -> login());
            view.getRegisterLabel().addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    toRegister();
                }
            });
        }
    }

    private void login() {
        String username = view.getUsernameText().getText().trim();
        String password = view.getPasswordText().getText();
        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(view.getComponent(), "用户名不能为空！",
                    "登录", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (password.isEmpty()) {
            JOptionPane.showMessageDialog(view.getComponent(), "密码不能为空！",
                    "登录", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (username.contains("|")) {
            JOptionPane.showMessageDialog(view.getComponent(), "用户名含有非法字符！",
                    "登录", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (password.contains("|")) {
            JOptionPane.showMessageDialog(view.getComponent(), "密码含有非法字符！",
                    "登录", JOptionPane.WARNING_MESSAGE);
            return;
        }
        model.setUsername(username);
        model.setPassword(password);
        MsgProto msg = MsgProto.build(Command.LOGIN, model.getUsername(), model.getPassword());
        Main.connection().send(msg);
    }

    private void toRegister() {
        view.hideWindow();
        RegisterController.getInstance().register();
        RegisterController.getInstance().getView().showWindow(view.getComponent());
    }

    public void loginSuccess(User loginUser) {
        MainController.getInstance().loginSuccess(loginUser);
        view.hideWindow();
    }

    public void loginFailed(String message) {
        JOptionPane.showMessageDialog(view.getComponent(), message, "登录", JOptionPane.WARNING_MESSAGE);
    }
}
