package info.zpss.uniwood.client.controller;

import info.zpss.uniwood.client.Main;
import info.zpss.uniwood.client.entity.Floor;
import info.zpss.uniwood.client.item.FloorItem;
import info.zpss.uniwood.client.model.PostModel;
import info.zpss.uniwood.client.util.interfaces.Controller;
import info.zpss.uniwood.client.view.PostView;
import info.zpss.uniwood.client.view.render.FloorItemRender;
import info.zpss.uniwood.client.view.window.PostWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
        try {
            String title = model.getFloor(1).getContent();
            view.setTitle(title.length() > 16 ? title.substring(0, 16) + "..." : title);
        } catch (InterruptedException | TimeoutException e) {
            Main.logger().add(e, Thread.currentThread());
        }
        if (!registered) {
            registered = true;
            view.getShareBtn().addActionListener(e -> sharePost());
            view.getFavorBtn().addActionListener(e -> favorPost());
            view.getReplyBtn().addActionListener(e -> replyPost());
            view.getRefreshBtn().addActionListener(e -> refreshPost());
            view.getPrevBtn().addActionListener(e -> prevPage());
            view.getNextBtn().addActionListener(e -> nextPage());
        }
    }

    private void sharePost() {
        // TODO
    }

    private void favorPost() {
        // TODO
    }

    private void replyPost() {
        // TODO
    }

    private void refreshPost() {
        try {
            model.setFromFloor(0);
            setFloors();
        } catch (InterruptedException | TimeoutException e) {
            Main.logger().add(e, Thread.currentThread());
            SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(view.getComponent(), "加载失败，请检查网络连接！",
                    "错误", JOptionPane.ERROR_MESSAGE));
        }
    }

    private void prevPage() {
        try {
            List<Floor> floors = model.getPrevPageFloors();
            Vector<FloorItem> floorItems = new Vector<>();
            for (Floor floor : floors)
                floorItems.add(new FloorItem(floor));
            view.getFloorPanel().setListData(floorItems);
            view.rollToTop();
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
            view.rollToTop();
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
        view.rollToTop();
    }

    // 注册楼层列表项，FloorItemRender.register()调用
    public void regFloorItem(FloorItemRender item) {
        item.userPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                UserCenterController.getInstance().setUser(item.getItem().getFloor().getAuthor());
                UserCenterController.getInstance().register();
                UserCenterController.getInstance().getView().showWindow(view.getComponent());
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                item.usernameLbl.setForeground(Color.BLUE);
                item.univLbl.setForeground(Color.BLUE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                item.usernameLbl.setForeground(new Color(80, 80, 150));
                item.univLbl.setForeground(new Color(80, 80, 150));
            }
        });
    }
}
