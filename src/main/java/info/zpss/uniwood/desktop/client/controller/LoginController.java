package info.zpss.uniwood.desktop.client.controller;

import info.zpss.uniwood.desktop.client.Main;
import info.zpss.uniwood.desktop.client.model.entity.User;
import info.zpss.uniwood.desktop.client.model.view.LoginModel;
import info.zpss.uniwood.desktop.client.util.Controller;
import info.zpss.uniwood.desktop.client.util.View;
import info.zpss.uniwood.desktop.client.view.LoginView;
import info.zpss.uniwood.desktop.client.view.window.LoginWindow;
import info.zpss.uniwood.desktop.common.Command;
import info.zpss.uniwood.desktop.common.ProtoMsg;

import javax.swing.*;

public class LoginController implements Controller {
    private static final LoginController INSTANCE;
    private static final LoginModel model;
    private static final LoginView view;

    static {
        model = new LoginModel();
        view = new LoginWindow();
        INSTANCE = new LoginController();
    }

    private LoginController() {
    }

    public static LoginController getInstance() {
        return INSTANCE;
    }

    @Override
    public void register() {
        view.getLoginButton().addActionListener(e -> login());
    }

    @Override
    public View getView() {
        return view;
    }

    private void login() {
        String username = view.getUsernameText().getText().trim();
        String password = view.getPasswordText().getText();
        if(username.isEmpty()) {
            JOptionPane.showMessageDialog(view.getComponent(), "用户名不能为空！",
                    "登录", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if(password.isEmpty()) {
            JOptionPane.showMessageDialog(view.getComponent(), "密码不能为空！",
                    "登录", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if(username.contains("|")) {
            JOptionPane.showMessageDialog(view.getComponent(), "用户名含有非法字符！",
                    "登录", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if(password.contains("|")) {
            JOptionPane.showMessageDialog(view.getComponent(), "密码含有非法字符！",
                    "登录", JOptionPane.WARNING_MESSAGE);
            return;
        }
        model.setUsername(username);
        model.setPassword(password);
        ProtoMsg msg = ProtoMsg.build(Command.LOGIN, model.getUsername(), model.getPassword());
        Main.connection().send(msg);
    }

    public void loginSuccess(User loginUser) {
        MainController.getInstance().loginSuccess(loginUser);
        view.hideWindow();
    }

    public void loginFailed() {
        JOptionPane.showMessageDialog(view.getComponent(), "用户登录失败，请检查用户名和密码后重试",
                "登录", JOptionPane.WARNING_MESSAGE);
    }
}
