package info.zpss.uniwood.client.view;

import info.zpss.uniwood.client.model.UserCenterModel;
import info.zpss.uniwood.client.util.interfaces.View;

import javax.swing.*;

public interface UserCenterView extends View {
    JLabel getFollowingLabel(); // 关注数
    JLabel getFollowerLabel();  // 粉丝数
    JButton getFavorButton();
    JButton getPostButton();
    JButton getFollowOrEditButton();
    void setUser(UserCenterModel model);
}
