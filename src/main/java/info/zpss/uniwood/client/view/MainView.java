package info.zpss.uniwood.client.view;

import info.zpss.uniwood.client.util.interfaces.View;
import info.zpss.uniwood.client.view.window.MainWindow.*;

import javax.swing.*;
import java.awt.*;

public interface MainView extends View {
    void initGlobalFont(Font font);

    JButton getLoginOrUserCenterButton();

    JButton getRegisterOrLogoutButton();

    UserPanel getUserPanel();

    ZonePanel getZonePanel();

    PostPanel getPostPanel();

    BtnPanel getButtonPanel();

    SearchPanel getSearchPanel();
}
