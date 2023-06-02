package info.zpss.uniwood.client.view;

import info.zpss.uniwood.client.view.window.MainWindow.ZonePanel;
import info.zpss.uniwood.client.view.window.MainWindow.UserPanel;
import info.zpss.uniwood.client.util.interfaces.View;

import javax.swing.*;
import java.awt.*;

public interface MainView extends View {
    void initGlobalFont(Font font);

    JButton getLoginOrUserCenterButton();

    JButton getRegisterOrLogoutButton();

    UserPanel getUserPanel();

    ZonePanel getZonePanel();
}
