package info.zpss.uniwood.client.controller;

import info.zpss.uniwood.client.Main;
import info.zpss.uniwood.client.entity.User;
import info.zpss.uniwood.client.model.EditModel;
import info.zpss.uniwood.client.util.Avatar;
import info.zpss.uniwood.client.util.interfaces.Controller;
import info.zpss.uniwood.client.view.EditView;
import info.zpss.uniwood.client.view.dialog.RegisterOrEditDialog;
import info.zpss.uniwood.common.Command;
import info.zpss.uniwood.common.MsgProto;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.util.Arrays;
import java.util.concurrent.TimeoutException;

public class EditController implements Controller<EditModel, EditView> {
    private static final EditController INSTANCE;
    private static final EditModel model;
    private static final EditView view;
    private static boolean registered;

    static {
        model = new EditModel();
        view = new RegisterOrEditDialog(MainController.getInstance().getView().getComponent());
        INSTANCE = new EditController();
        registered = false;
    }

    private EditController() {
    }

    public static EditController getInstance() {
        return INSTANCE;
    }

    @Override
    public EditModel getModel() {
        return model;
    }

    @Override
    public EditView getView() {
        return view;
    }

    @Override
    public void register() {
        if (!registered) {
            registered = true;
            view.setUser(MainController.getInstance().getModel().getLoginUser());
            view.getEditBtn().addActionListener(e -> userEdit());
            view.getSetAvatarBtn().addActionListener(e -> setAvatar());
            try {
                String[] universities = RegisterController.getInstance().getModel().getUniversities(0);
                view.getUniversityCombo().setModel(new DefaultComboBoxModel<>(universities));
            } catch (InterruptedException | TimeoutException e) {
                Main.logger().add(e, Thread.currentThread());
            }
        }
    }

    public void editSuccess() {
        JOptionPane.showMessageDialog(view.getComponent(), "修改成功！",
                "修改信息", JOptionPane.INFORMATION_MESSAGE);
        MainController.getInstance().loginSuccess(new User(model.getId(), model.getUsername(),
                model.getUniversity(), model.getAvatarBase64()));
        view.hideWindow();
        model.init();
        UserCenterController.getInstance().getModel().init();
        UserCenterController.getInstance().getView().hideWindow();
    }

    public void editFailed(String reason) {
        JOptionPane.showMessageDialog(view.getComponent(), reason,
                "修改信息", JOptionPane.WARNING_MESSAGE);
    }

    private void userEdit() {
        String username = view.getUsernameText().getText().trim();
        char[] newPassword = view.getNewPasswordText().getPassword();
        char[] newPwdConfirm = view.getNewPwdConfirmText().getPassword();
        String university = (String) view.getUniversityCombo().getSelectedItem();
        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(view.getComponent(), "用户名不能为空！",
                    "修改信息", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (newPassword == null || newPassword.length == 0) {
            JOptionPane.showMessageDialog(view.getComponent(), "新密码不能为空！",
                    "修改信息", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (newPwdConfirm == null || newPwdConfirm.length == 0) {
            JOptionPane.showMessageDialog(view.getComponent(), "确认密码不能为空！",
                    "修改信息", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!Arrays.equals(newPassword, newPwdConfirm)) {
            JOptionPane.showMessageDialog(view.getComponent(), "两次输入的密码不一致！",
                    "修改信息", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (username.length() < 3 || username.length() > 20) {
            JOptionPane.showMessageDialog(view.getComponent(), "用户名长度必须在3-20之间！",
                    "修改信息", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (newPassword.length < 6 || newPassword.length > 20) {
            JOptionPane.showMessageDialog(view.getComponent(), "新密码长度必须在6-20之间！",
                    "修改信息", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (username.contains(" ") || username.contains("|")) {
            JOptionPane.showMessageDialog(view.getComponent(), "用户名含有非法字符！",
                    "修改信息", JOptionPane.WARNING_MESSAGE);
            return;
        }
        for (char c : newPassword)
            if (c == ' ' || c == '|') {
                JOptionPane.showMessageDialog(view.getComponent(), "新密码含有非法字符！",
                        "修改信息", JOptionPane.WARNING_MESSAGE);
                return;
            }
        if (university == null) {
            JOptionPane.showMessageDialog(view.getComponent(), "请选择学校！",
                    "修改信息", JOptionPane.WARNING_MESSAGE);
            return;
        }
        model.setUsername(username);
        model.setNewPassword(new String(newPassword));
        model.setUniversity(university);
        MsgProto msg = MsgProto.build(Command.EDIT_INFO, model.getId().toString(), model.getUsername(),
                model.getNewPassword(), model.getUniversity(), model.getAvatarBase64());
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
}
