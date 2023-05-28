package info.zpss.uniwood.client.view;

import info.zpss.uniwood.client.util.Avatar;
import info.zpss.uniwood.client.util.interfaces.View;

import javax.swing.*;

public interface RegisterView extends View {
    void setAvatar(Avatar avatar);
    JButton getRegisterBtn();
    JButton getSetAvatarBtn();
    JTextField getUsernameText();
    JPasswordField getPasswordText();
    JPasswordField getPwdConfirmText();
    JComboBox<String> getUniversityCombo();
}
