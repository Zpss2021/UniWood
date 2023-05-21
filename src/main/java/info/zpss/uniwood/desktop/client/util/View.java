package info.zpss.uniwood.desktop.client.util;

import java.awt.*;

public interface View {
    void showWindow(Component parent);
    void hideWindow();
    Component getComponent();
}
