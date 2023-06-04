package info.zpss.uniwood.client.controller;

import info.zpss.uniwood.client.Main;
import info.zpss.uniwood.client.entity.Floor;
import info.zpss.uniwood.client.item.FloorItem;
import info.zpss.uniwood.client.model.PostModel;
import info.zpss.uniwood.client.util.interfaces.Controller;
import info.zpss.uniwood.client.view.PostView;
import info.zpss.uniwood.client.view.window.PostWindow;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.TimeoutException;

public class PostController implements Controller<PostModel, PostView> {
    private static final PostController INSTANCE;
    private static final PostModel model;
    private static final PostView view;

    static {
        model = new PostModel();
        view = new PostWindow();
        INSTANCE = new PostController();
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
            view.getShareBtn().addActionListener(e -> sharePost());
            view.getFavorBtn().addActionListener(e -> favorPost());
            view.getReplyBtn().addActionListener(e -> replyPost());
            view.getRefreshBtn().addActionListener(e -> refreshPost());
            view.getPrevBtn().addActionListener(e -> prevPost());
            view.getNextBtn().addActionListener(e -> nextPost());
        } catch (InterruptedException | TimeoutException e) {
            Main.logger().add(e, Thread.currentThread());
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

    private void prevPost() {
    }

    private void nextPost() {
    }

    public void setFloors() throws InterruptedException, TimeoutException {
        List<Floor> floors = model.getFloors();
        Vector<FloorItem> floorItems = new Vector<>();
        for (Floor floor : floors)
            floorItems.add(new FloorItem(floor));
        view.getFloorPanel().setListData(floorItems);
    }

}
