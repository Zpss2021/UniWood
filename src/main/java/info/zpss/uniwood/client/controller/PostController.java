package info.zpss.uniwood.client.controller;

import info.zpss.uniwood.client.Main;
import info.zpss.uniwood.client.entity.Floor;
import info.zpss.uniwood.client.item.FloorItem;
import info.zpss.uniwood.client.model.PostModel;
import info.zpss.uniwood.client.util.interfaces.Controller;
import info.zpss.uniwood.client.view.PostView;
import info.zpss.uniwood.client.view.window.PostWindow;

import javax.swing.*;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.TimeoutException;

public class PostController implements Controller<PostModel, PostView> {
    private static final PostController INSTANCE;
    private static final PostModel model;
    private static final PostView view;
    private static boolean registered;

    static {
        model = new PostModel();
        view = new PostWindow();
        INSTANCE = new PostController();
        registered = false;
    }

    private PostController() {
    }

    public static PostController getInstance() {
        return INSTANCE;
    }

    @Override
    public PostModel getModel() {
        return model;
    }

    @Override
    public PostView getView() {
        return view;
    }

    @Override
    public void register() {
        if (!registered) {
            registered = true;
            try {
                String title = model.getFloor(1).getContent();
                view.setTitle(title.length() > 16 ? title.substring(0, 16) + "..." : title);
                view.getShareBtn().addActionListener(e -> sharePost());
                view.getFavorBtn().addActionListener(e -> favorPost());
                view.getReplyBtn().addActionListener(e -> replyPost());
                view.getRefreshBtn().addActionListener(e -> refreshPost());
                view.getPrevBtn().addActionListener(e -> prevPage());
                view.getNextBtn().addActionListener(e -> nextPage());
            } catch (InterruptedException | TimeoutException e) {
                Main.logger().add(e, Thread.currentThread());
            }
        }
    }

    private void sharePost() {
    }

    private void favorPost() {
    }

    private void replyPost() {
    }

    private void refreshPost() {
    }

    private void prevPage() {
        try {
            List<Floor> floors = model.getPrevPageFloors();
            Vector<FloorItem> floorItems = new Vector<>();
            for (Floor floor : floors)
                floorItems.add(new FloorItem(floor));
            view.getFloorPanel().setListData(floorItems);
        } catch (InterruptedException | TimeoutException e) {
            Main.logger().add(e, Thread.currentThread());
            SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(view.getComponent(), "加载失败，请检查网络连接！",
                    "错误", JOptionPane.ERROR_MESSAGE));
        }
    }

    private void nextPage() {
        try {
            List<Floor> floors = model.getNextPageFloors();
            Vector<FloorItem> floorItems = new Vector<>();
            for (Floor floor : floors)
                floorItems.add(new FloorItem(floor));
            view.getFloorPanel().setListData(floorItems);
        } catch (InterruptedException | TimeoutException e) {
            Main.logger().add(e, Thread.currentThread());
            SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(view.getComponent(), "加载失败，请检查网络连接！",
                    "错误", JOptionPane.ERROR_MESSAGE));
        }
    }

    public void setFloors() throws InterruptedException, TimeoutException {
        List<Floor> floors = model.getNextPageFloors();
        Vector<FloorItem> floorItems = new Vector<>();
        for (Floor floor : floors)
            floorItems.add(new FloorItem(floor));
        view.getFloorPanel().setListData(floorItems);
    }

}
