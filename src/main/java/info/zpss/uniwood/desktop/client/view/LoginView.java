package info.zpss.uniwood.desktop.client.view;

import info.zpss.uniwood.desktop.client.util.interfaces.View;

import javax.swing.*;

public interface LoginView extends View {
    JTextField getUsernameText();
    JTextField getPasswordText();
    JButton getLoginButton();
}
