package info.zpss.uniwood.desktop.client.view;

import info.zpss.uniwood.desktop.client.util.interfaces.View;
import info.zpss.uniwood.desktop.client.view.panel.UserPanel;

import javax.swing.*;

public interface MainView extends View {
    JButton getLoginButton();
    JButton getRegisterButton();
    UserPanel getUserPanel();
}
