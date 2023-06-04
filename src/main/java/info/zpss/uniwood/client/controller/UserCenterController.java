package info.zpss.uniwood.client.controller;

import info.zpss.uniwood.client.entity.User;
import info.zpss.uniwood.client.model.UserCenterModel;
import info.zpss.uniwood.client.util.interfaces.Controller;
import info.zpss.uniwood.client.view.UserCenterView;
import info.zpss.uniwood.client.view.dialog.UserCenterDialog;

import javax.swing.*;
import java.awt.*;
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
            view.getFollowOrEditButton().setIcon(new ImageIcon(new ImageIcon("src/main/resources/自定义.png")
                    .getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
            view.getFollowOrEditButton().setText("编辑");
            view.getFollowOrEditButton().addActionListener(e -> toEdit());
        } else {
            view.getFollowOrEditButton().setIcon(new ImageIcon(new ImageIcon("src/main/resources/加.png")
                    .getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
            view.getFollowOrEditButton().setText("关注");
            view.getFollowOrEditButton().addActionListener(e -> toFollow());
        }
    }

    public void setUser(User user) {
        model.setUser(user);
        view.setUser(model);
    }

    private void toFollowing() {
//        FollowingController.getInstance().show(); TODO:关注列表
    }

    private void toFollower() {
//        FollowerController.getInstance().show(); TODO:粉丝列表
    }

    private void toFavor() {
//        FavorController.getInstance().show(); TODO:收藏列表
    }

    private void toPost() {
//        PostController.getInstance().show(); TODO:贴子列表
    }

    private void toEdit() {
        EditController.getInstance().register();
        EditController.getInstance().getView().showWindow(view.getComponent());
    }

    private void toFollow() {
//        FollowController.getInstance().show(); TODO:关注
    }
}