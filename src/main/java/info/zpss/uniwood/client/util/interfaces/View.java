package info.zpss.uniwood.client.util.interfaces;

import java.awt.*;

public interface View {
    void showWindow(Component parent);
    void hideWindow();
    Component getComponent();
}