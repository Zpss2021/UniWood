package info.zpss.uniwood.client.controller;

import info.zpss.uniwood.client.entity.User;
import info.zpss.uniwood.client.model.UserCenterModel;
import info.zpss.uniwood.client.util.ImageLoader;
import info.zpss.uniwood.client.util.interfaces.Controller;
import info.zpss.uniwood.client.view.UserCenterView;
import info.zpss.uniwood.client.view.dialog.UserCenterDialog;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UserCenterController implements Controller<UserCenterModel, UserCenterView> {
    private static final UserCenterController INSTANCE;
    private static final UserCenterModel model;
    private static final UserCenterView view;
    private static boolean registered;

    static {
        model = new UserCenterModel();
        view = new UserCenterDialog(MainController.getInstance().getView().getComponent());
        INSTANCE = new UserCenterController();
        registered = false;
    }

    private UserCenterController() {
    }

    public static UserCenterController getInstance() {
        return INSTANCE;
    }

    @Override
    public UserCenterModel getModel() {
        return model;
    }

    @Override
    public UserCenterView getView() {
        return view;
    }

    @Override
    public void register() {
        if (!registered) {
            registered = true;
            view.getFollowingLabel().addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    toFollowing();
                }
            });
            view.getFollowerLabel().addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    toFollower();
                }
            });
            view.getFavorButton().addActionListener(e -> toFavor());
            view.getPostButton().addActionListener(e -> toPost());
        }
        for (ActionListener l : view.getFollowOrEditButton().getActionListeners())
            view.getFollowOrEditButton().removeActionListener(l);
        if (MainController.getInstance().getModel().getLoginUser().getId().equals(model.getId())) {
            view.getFollowOrEditButton().setIcon(ImageLoader.load("images/自定义.png", 24, 24));
            view.getFollowOrEditButton().setText("编辑");
            view.getFollowOrEditButton().addActionListener(e -> toEdit());
        } else {
            view.getFollowOrEditButton().setIcon(ImageLoader.load("images/加.png", 24, 24));
            view.getFollowOrEditButton().setText("关注");
            view.getFollowOrEditButton().addActionListener(e -> toFollow());
        }
    }

    public void setUser(User user) {
        model.setUser(user);
        view.setUser(model);
    }

    private void toFollowing() {
        JOptionPane.showMessageDialog(view.getComponent(), "功能尚未实现，敬请期待",
                "关注列表", JOptionPane.INFORMATION_MESSAGE);
    }

    private void toFollower() {
        JOptionPane.showMessageDialog(view.getComponent(), "功能尚未实现，敬请期待",
                "粉丝列表", JOptionPane.INFORMATION_MESSAGE);
    }

    private void toFavor() {
        SwingUtilities.invokeLater(() -> {
            UserFavorListController.getInstance().register();
            UserFavorListController.getInstance().setFavors();
        });
        UserFavorListController.getInstance().getView().showWindow(view.getComponent());
    }

    private void toPost() {
        SwingUtilities.invokeLater(() -> {
            UserPostListController.getInstance().register();
            UserPostListController.getInstance().setPosts();
        });
        UserPostListController.getInstance().getView().showWindow(view.getComponent());
    }

    private void toEdit() {
        EditController.getInstance().register();
        EditController.getInstance().getView().showWindow(view.getComponent());
    }

    private void toFollow() {
        JOptionPane.showMessageDialog(view.getComponent(), "功能尚未实现，敬请期待",
                "关注", JOptionPane.INFORMATION_MESSAGE);
    }
}