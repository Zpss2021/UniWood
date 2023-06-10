package info.zpss.uniwood.client.view;

import info.zpss.uniwood.client.entity.Post;
import info.zpss.uniwood.client.util.interfaces.View;

import javax.swing.*;
import java.util.List;

public interface UserFavorListView extends View {
    JButton getDeleteBtn();
    JButton getOpenBtn();
    JList<String> getUserFavorList();
    void setTitle(String title);
    void setFavorList(List<Post> favorList);
}
