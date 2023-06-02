package info.zpss.uniwood.client.view;

import info.zpss.uniwood.client.entity.User;
import info.zpss.uniwood.client.util.Avatar;
import info.zpss.uniwood.client.util.interfaces.View;

import javax.swing.*;

public interface EditView extends View {
    void setAvatar(Avatar avatar);
    void setUser(User user);
    JButton getEditBtn();
    JButton getSetAvatarBtn();
    JTextField getUsernameText();
    JPasswordField getNewPasswordText();
    JPasswordField getNewPwdConfirmText();
    JComboBox<String> getUniversityCombo();
}