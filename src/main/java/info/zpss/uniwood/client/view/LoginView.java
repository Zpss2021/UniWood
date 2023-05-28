package info.zpss.uniwood.client.view;

import info.zpss.uniwood.client.util.interfaces.View;

import javax.swing.*;

public interface LoginView extends View {
    JTextField getUsernameText();
    JTextField getPasswordText();
    JButton getLoginButton();
}
