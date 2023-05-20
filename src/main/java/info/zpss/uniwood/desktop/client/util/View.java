package info.zpss.uniwood.desktop.client.util;

import java.awt.*;

public interface View {
    void showWindow();
    void hideWindow();
    void setParent(Component parent);
    Component getComponent();
}
