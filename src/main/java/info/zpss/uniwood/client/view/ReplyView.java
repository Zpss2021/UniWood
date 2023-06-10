package info.zpss.uniwood.client.view;

import info.zpss.uniwood.client.util.interfaces.View;

import javax.swing.*;

public interface ReplyView extends View {
    JTextArea getReplyArea();
    JButton getReplyButton();
    void setTitle(String title);

    JTextField getTitleText();
}
