package info.zpss.uniwood.client.view;

import info.zpss.uniwood.client.entity.Post;
import info.zpss.uniwood.client.util.interfaces.View;

import javax.swing.*;
import java.util.List;

public interface UserPostListView extends View {
    JButton getDeleteBtn();
    JButton getOpenBtn();
    JList<String> getUserPostList();
    void setTitle(String title);
    void setPostList(List<Post> postList);
}
