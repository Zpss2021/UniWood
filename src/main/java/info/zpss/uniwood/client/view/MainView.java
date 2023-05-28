package info.zpss.uniwood.client.view;

import info.zpss.uniwood.client.view.window.MainWindow;
import info.zpss.uniwood.client.util.interfaces.View;

import javax.swing.*;

public interface MainView extends View {
    JButton getLoginButton();
    JButton getRegisterButton();
    MainWindow.UserPanel getUserPanel();
}
