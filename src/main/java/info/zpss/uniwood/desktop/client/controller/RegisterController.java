package info.zpss.uniwood.desktop.client.controller;

import info.zpss.uniwood.desktop.client.Main;
import info.zpss.uniwood.desktop.client.model.entity.User;
import info.zpss.uniwood.desktop.client.model.view.RegisterModel;
import info.zpss.uniwood.desktop.client.util.Avatar;
import info.zpss.uniwood.desktop.client.util.Controller;
import info.zpss.uniwood.desktop.client.util.View;
import info.zpss.uniwood.desktop.client.view.RegisterView;
import info.zpss.uniwood.desktop.client.view.window.RegisterWindow;
import info.zpss.uniwood.desktop.common.Command;
import info.zpss.uniwood.desktop.common.ProtoMsg;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.util.Arrays;

public class RegisterController implements Controller {
    private static final RegisterController INSTANCE;
    private static final RegisterModel model;
    private static final RegisterView view;
    private static boolean registered;

    static {
        model = new RegisterModel();
        view = new RegisterWindow();
        INSTANCE = new RegisterController();
        registered = false;
    }

    private RegisterController() {
    }

    public static RegisterController getInstance() {
        return INSTANCE;
    }

    @Override
    public void register() {
        if (!registered) {
            registered = true;
            view.getRegisterBtn().addActionListener(e -> userRegister());
            view.getSetAvatarBtn().addActionListener(e -> setAvatar());
            try {
                String[] universities = model.getUniversities();
                view.getUniversityCombo().setModel(new DefaultComboBoxModel<>(universities));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public View getView() {
        return view;
    }

    public RegisterModel getModel() {
        return model;
    }

    private void userRegister() {
        String username = view.getUsernameText().getText().trim();
        char[] password = view.getPasswordText().getPassword();
        char[] pwdConfirm = view.getPwdConfirmText().getPassword();
        String university = (String) view.getUniversityCombo().getSelectedItem();
        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(view.getComponent(), "用户名不能为空！",
                    "注册", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (password == null || password.length == 0) {
            JOptionPane.showMessageDialog(view.getComponent(), "密码不能为空！",
                    "注册", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (pwdConfirm == null || pwdConfirm.length == 0) {
            JOptionPane.showMessageDialog(view.getComponent(), "确认密码不能为空！",
                    "注册", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!Arrays.equals(password, pwdConfirm)) {
            JOptionPane.showMessageDialog(view.getComponent(), "两次输入的密码不一致！",
                    "注册", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (username.length() < 3 || username.length() > 20) {
            JOptionPane.showMessageDialog(view.getComponent(), "用户名长度必须在3-20之间！",
                    "注册", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (password.length < 6 || password.length > 20) {
            JOptionPane.showMessageDialog(view.getComponent(), "密码长度必须在6-20之间！",
                    "注册", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (username.contains(" ") || username.contains("|")) {
            JOptionPane.showMessageDialog(view.getComponent(), "用户名含有非法字符！",
                    "注册", JOptionPane.WARNING_MESSAGE);
            return;
        }
        for (char c : password)
            if (c == ' ' || c == '|') {
                JOptionPane.showMessageDialog(view.getComponent(), "密码含有非法字符！",
                        "注册", JOptionPane.WARNING_MESSAGE);
                return;
            }
        if (university == null) {
            JOptionPane.showMessageDialog(view.getComponent(), "请选择学校！",
                    "注册", JOptionPane.WARNING_MESSAGE);
            return;
        }
        model.setUsername(username);
        model.setPassword(new String(password));
        model.setUniversity(university);
        ProtoMsg msg = ProtoMsg.build(Command.REGISTER, model.getUsername(),
                model.getPassword(), model.getUniversity(), model.getAvatarBase64());
        Main.connection().send(msg);
    }

    private void setAvatar() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setMultiSelectionEnabled(false);
        fileChooser.setDialogTitle("选择头像");
        fileChooser.setApproveButtonText("确定");
        fileChooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.isDirectory() || f.getName().endsWith(".jpg") || f.getName().endsWith(".png");
            }

            @Override
            public String getDescription() {
                return "图片文件(*.jpg, *.png)";
            }
        });
        int result = fileChooser.showOpenDialog(view.getComponent());
        if (result == JFileChooser.APPROVE_OPTION) {
            Avatar avatar = new Avatar();
            avatar.fromFile(fileChooser.getSelectedFile());
            model.setAvatarBase64(avatar.toBase64());
            view.setAvatar(avatar);
        }
    }

    public void registerSuccess(User registeredUser) {
        JOptionPane.showMessageDialog(view.getComponent(), String.format("用户%s注册成功！",
                registeredUser.getUsername()), "注册", JOptionPane.INFORMATION_MESSAGE);
        MainController.getInstance().loginSuccess(registeredUser);
        view.hideWindow();
    }

    public void registerFailed(String message) {
        JOptionPane.showMessageDialog(view.getComponent(), message, "注册", JOptionPane.WARNING_MESSAGE);
    }
}
