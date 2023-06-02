package info.zpss.uniwood.client.model;

import info.zpss.uniwood.client.builder.FloorBuilder;
import info.zpss.uniwood.client.entity.Floor;
import info.zpss.uniwood.client.entity.Post;
import info.zpss.uniwood.client.util.interfaces.Model;

import java.util.List;


public class PostModel implements Model {
    private Post post;
    private static final int pageSize = 10;

    public PostModel() {
        this.init();
    }

    public List<Floor> getFloors() throws InterruptedException {
        int size = post.getFloors().size();
        if(post.getFloors().size() > size || size == post.getFloorCount())
            return post.getFloors();
        for (int i = size, j = 1; i < post.getFloorCount(); i++, j++) {
            Floor floor = FloorBuilder.getInstance().get(post.getId(), i);
            post.getFloors().add(floor);
            if(j == pageSize)
                break;
        }
        return getFloors();
    }

    @Override
    public void init() {
        post = new Post();
    }
}