package info.zpss.uniwood.client.controller;

import info.zpss.uniwood.client.Main;
import info.zpss.uniwood.client.builder.UserBuilder;
import info.zpss.uniwood.client.entity.Post;
import info.zpss.uniwood.client.model.UserFavorListModel;
import info.zpss.uniwood.client.util.interfaces.Controller;
import info.zpss.uniwood.client.view.UserFavorListView;
import info.zpss.uniwood.client.view.dialog.UserListDialog;
import info.zpss.uniwood.common.Command;
import info.zpss.uniwood.common.MsgProto;

import javax.swing.*;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class UserFavorListController implements Controller<UserFavorListModel, UserFavorListView> {
    private static final UserFavorListController INSTANCE;
    private static final UserFavorListModel model;
    private static final UserFavorListView view;
    private static boolean registered;

    static {
        model = new UserFavorListModel();
        view = new UserListDialog(MainController.getInstance().getView().getComponent());
        INSTANCE = new UserFavorListController();
        registered = false;
    }

    private UserFavorListController() {
    }

    public static UserFavorListController getInstance() {
        return INSTANCE;
    }

    @Override
    public UserFavorListModel getModel() {
        return model;
    }

    @Override
    public UserFavorListView getView() {
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
        view.getDeleteBtn().setText("取消收藏");
        view.getOpenBtn().setText("打开收藏");
        try {
            view.setTitle(String.format("%s-收藏列表", UserBuilder.getInstance().get(model.getUserId()).getUsername()));
        } catch (InterruptedException | TimeoutException e) {
            Main.logger().add(e, Thread.currentThread());
            JOptionPane.showMessageDialog(view.getComponent(),
                    "加载失败，请检查网络连接！", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void setFavors() {
        try {
            model.init();
            List<Post> favors = model.getFavors(0);
            view.setFavorList(favors);
        } catch (TimeoutException | InterruptedException e) {
            Main.logger().add(e, Thread.currentThread());
            JOptionPane.showMessageDialog(view.getComponent(),
                    "加载失败，请检查网络连接！", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void delete() {
        String title = view.getUserFavorList().getSelectedValue();
        if (title == null) {
            JOptionPane.showMessageDialog(view.getComponent(),
                    "请选择取消收藏的贴子！", "收藏", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Integer postID = Integer.valueOf(title.substring(1, title.indexOf(" ")));
        Main.connection().send(MsgProto.build(
                Command.UNFAVOR,
                MainController.getInstance().getModel().getLoginUser().getId().toString(),
                String.valueOf(postID)
        ));
        SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(view.getComponent(),
                String.format("贴子#%d取消收藏成功！", postID), "收藏贴子", JOptionPane.INFORMATION_MESSAGE));
        setFavors();
    }

    private void open() {
        String title = view.getUserFavorList().getSelectedValue();
        if (title == null) {
            JOptionPane.showMessageDialog(view.getComponent(),
                    "请选择要打开的的收藏！", "收藏", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int postID = Integer.parseInt(title.substring(1, title.indexOf(" ")));
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
