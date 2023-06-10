package info.zpss.uniwood.client.controller;

import info.zpss.uniwood.client.Main;
import info.zpss.uniwood.client.builder.UserBuilder;
import info.zpss.uniwood.client.entity.Post;
import info.zpss.uniwood.client.model.UserPostListModel;
import info.zpss.uniwood.client.util.interfaces.Controller;
import info.zpss.uniwood.client.view.UserPostListView;
import info.zpss.uniwood.client.view.dialog.UserListDialog;
import info.zpss.uniwood.common.Command;
import info.zpss.uniwood.common.MsgProto;

import javax.swing.*;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class UserPostListController implements Controller<UserPostListModel, UserPostListView> {
    private static final UserPostListController INSTANCE;
    private static final UserPostListModel model;
    private static final UserPostListView view;
    private static boolean registered;

    static {
        model = new UserPostListModel();
        view = new UserListDialog(MainController.getInstance().getView().getComponent());
        INSTANCE = new UserPostListController();
        registered = false;
    }

    private UserPostListController() {
    }

    public static UserPostListController getInstance() {
        return INSTANCE;
    }

    @Override
    public UserPostListModel getModel() {
        return model;
    }

    @Override
    public UserPostListView getView() {
        return view;
    }

    @Override
    public void register() {
        if (!registered) {
            registered = true;
            view.getDeleteBtn().addActionListener(e -> delete());
            view.getOpenBtn().addActionListener(e -> open());
        }
        view.getDeleteBtn()
                .setVisible(MainController.getInstance().getModel().getLoginUser().getId().equals(model.getUserId()));
        view.getDeleteBtn().setText("删除贴子");
        view.getOpenBtn().setText("打开贴子");
        try {
            view.setTitle(String.format("%s-发贴列表", UserBuilder.getInstance().get(model.getUserId()).getUsername()));
        } catch (InterruptedException | TimeoutException e) {
            Main.logger().add(e, Thread.currentThread());
            JOptionPane.showMessageDialog(view.getComponent(),
                    "加载失败，请检查网络连接！", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void setPosts() {
        try {
            model.init();
            List<Post> posts = model.getPosts();
            view.setPostList(posts);
        } catch (InterruptedException | TimeoutException e) {
            Main.logger().add(e, Thread.currentThread());
            JOptionPane.showMessageDialog(view.getComponent(),
                    "加载失败，请检查网络连接！", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void delete() {
        String title = view.getUserPostList().getSelectedValue();
        if (title == null) {
            JOptionPane.showMessageDialog(view.getComponent(),
                    "请选择要删除的贴子！", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int postID = Integer.parseInt(title.substring(1, title.indexOf(' ')));
        Main.connection().send(MsgProto.build(Command.DEL_POST, Integer.toString(postID)));
        SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(view.getComponent(),
                String.format("贴子#%d删除成功！", postID), "删除贴子", JOptionPane.INFORMATION_MESSAGE));
        setPosts();
    }

    private void open() {
        String title = view.getUserPostList().getSelectedValue();
        if (title == null) {
            JOptionPane.showMessageDialog(view.getComponent(),
                    "请选择要打开的贴子！", "发贴列表", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int postID = Integer.parseInt(title.substring(1, title.indexOf(' ')));
        try {
            MainController.getInstance().setFloors(postID);
        } catch (InterruptedException | TimeoutException e) {
            Main.logger().add(e, Thread.currentThread());
            JOptionPane.showMessageDialog(view.getComponent(),
                    "加载失败，请检查网络连接！", "错误", JOptionPane.ERROR_MESSAGE);
        }
        UserCenterController.getInstance().getView().hideWindow();
        view.hideWindow();
    }
}
