package info.zpss.uniwood.server.service.impl;

import info.zpss.uniwood.server.dao.PostDao;
import info.zpss.uniwood.server.dao.impl.PostDaoImpl;
import info.zpss.uniwood.server.entity.Post;
import info.zpss.uniwood.server.service.PostService;

import java.util.List;

public class PostServiceImpl implements PostService {
    private static final PostServiceImpl INSTANCE;
    private static final PostDao postDao;

    static {
        INSTANCE = new PostServiceImpl();
        postDao = PostDaoImpl.getInstance();
    }

    private PostServiceImpl() {
    }

    public static PostServiceImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public Post getPost(Integer postID) {
        return postDao.getPost(postID);
    }

    @Override
    public List<Post> getPostsByUserId(Integer userID) {
        return postDao.getPostsByUserID(userID);
    }

    @Override
    public Integer getFloorCount(Integer postID) {
        return postDao.getFloorCount(postID);
    }

    @Override
    public List<Post> getLimitPostsByZoneId(Integer zoneID, Integer from, Integer pageSize) {
        return postDao.getLimitPostsByZoneID(zoneID, from, pageSize);
    }

}
