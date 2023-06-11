package info.zpss.uniwood.client.view;

import info.zpss.uniwood.client.util.interfaces.View;
import info.zpss.uniwood.client.view.window.PostWindow.FloorPanel;

import javax.swing.*;


public interface PostView extends View {
    FloorPanel getFloorPanel();
    JButton getShareBtn();
    JButton getFavorBtn();
    JButton getReplyBtn();
    JButton getRefreshBtn();
    JButton getPrevBtn();
    JButton getNextBtn();
    void setTitle(String s);
    void rollToTop();

    boolean toggleFavor();
}