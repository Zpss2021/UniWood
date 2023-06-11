package info.zpss.uniwood.client.model;

import info.zpss.uniwood.client.controller.UserCenterController;
import info.zpss.uniwood.client.entity.Post;
import info.zpss.uniwood.client.util.interfaces.Model;

import java.util.List;
import java.util.concurrent.TimeoutException;

public class UserPostListModel implements Model {
    public Integer getUserId() {
        return UserCenterController.getInstance().getModel().getId();
    }

    public List<Post> getPosts() throws InterruptedException, TimeoutException {
        return UserCenterController.getInstance().getModel().getPosts(0);
    }

    @Override
    public void init() {
        UserCenterController.getInstance().getModel().clearPosts();
    }
}
