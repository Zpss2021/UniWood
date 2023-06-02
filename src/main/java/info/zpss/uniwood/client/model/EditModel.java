package info.zpss.uniwood.client.model;

import info.zpss.uniwood.client.controller.MainController;
import info.zpss.uniwood.client.entity.User;
import info.zpss.uniwood.client.util.interfaces.Model;

public class EditModel implements Model {
    private Integer id;
    private String username;
    private String newPassword;
    private String newPwdConfirm;
    private String university;
    private String avatarBase64;

    public EditModel() {
        init();
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public void setNewPwdConfirm(String newPwdConfirm) {
        this.newPwdConfirm = newPwdConfirm;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public void setAvatarBase64(String avatarBase64) {
        this.avatarBase64 = avatarBase64;
    }

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getNewPwdConfirm() {
        return newPwdConfirm;
    }

    public String getUniversity() {
        return university;
    }

    public String getAvatarBase64() {
        return avatarBase64;
    }

    @Override
    public void init() {
        User loginUser = MainController.getInstance().getModel().getLoginUser();
        id = loginUser.getId();
        username = loginUser.getUsername();
        university = loginUser.getUniversity();
        avatarBase64 = loginUser.getAvatar();
    }
}
