package info.zpss.uniwood.client.view;

import info.zpss.uniwood.client.util.interfaces.View;

import javax.swing.*;

public interface PublishView extends View {
    JTextField getZoneField();
    JTextArea getContentArea();
    JButton getPublishButton();
    void setTitle(String title);
}
