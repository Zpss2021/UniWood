package info.zpss.uniwood.server.service;

import info.zpss.uniwood.server.entity.Post;
import info.zpss.uniwood.server.service.impl.PostServiceImpl;

import java.util.List;

public interface PostService {
    static PostService getInstance() {
        return PostServiceImpl.getInstance();
    }

    Post getPost(Integer postID);

    List<Post> getPostsByUserId(Integer userID);
}
