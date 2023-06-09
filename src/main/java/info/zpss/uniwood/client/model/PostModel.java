package info.zpss.uniwood.client.model;

import info.zpss.uniwood.client.builder.FloorBuilder;
import info.zpss.uniwood.client.controller.PostController;
import info.zpss.uniwood.client.entity.Floor;
import info.zpss.uniwood.client.entity.Post;
import info.zpss.uniwood.client.util.interfaces.Model;

import javax.swing.*;
import java.util.List;
import java.util.concurrent.TimeoutException;


public class PostModel implements Model {
    private Post post;
    private static final int pageSize = 5;
    private int fromFloor;

    public PostModel() {
        this.init();
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public void setFromFloor(int fromFloor) {
        this.fromFloor = fromFloor;
    }

    public Floor getFloor(int floorID) throws InterruptedException, TimeoutException {
        for (Floor floor : post.getFloors())
            if (floor.getId() == floorID)
                return floor;
        return FloorBuilder.getInstance().get(floorID, post.getId());
    }

    public List<Floor> getNextPageFloors() throws InterruptedException, TimeoutException {
        post.getFloors().clear();
        if (fromFloor == 0)
            fromFloor = 1;
        else {
            fromFloor += pageSize;
            if (fromFloor > post.getFloorCount()) {
                fromFloor = 1;
                SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(PostController.getInstance()
                        .getView().getComponent(), "已到达最后一页！"));
            }
        }
        int to = fromFloor + pageSize - 1;
        to = to > post.getFloorCount() ? post.getFloorCount() : to;
        return getPageFloors(to);
    }

    public List<Floor> getPrevPageFloors() throws InterruptedException, TimeoutException {
        post.getFloors().clear();
        int to = fromFloor - 1;
        if (to < pageSize) {
            fromFloor = 1;
            to = fromFloor + pageSize - 1;
            SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(PostController.getInstance()
                    .getView().getComponent(), "已到达第一页！"));
        } else {
            fromFloor = to - pageSize + 1;
            fromFloor = Math.max(fromFloor, 1);
        }
        to = to > post.getFloorCount() ? post.getFloorCount() : to;
        return getPageFloors(to);
    }

    private List<Floor> getPageFloors(int toFloor) throws InterruptedException, TimeoutException {
        int size = post.getFloors().size();
        if (size != 0) {
            Floor floor = post.getFloors().get(size - 1);
            if (floor.getId() == toFloor)
                return post.getFloors();
        }
        for (int i = fromFloor; i <= toFloor; i++)
            post.getFloors().add(FloorBuilder.getInstance().get(i, post.getId()));
        return getPageFloors(toFloor);
    }

    @Override
    public void init() {
        post = new Post();
        fromFloor = 0;
    }
}
