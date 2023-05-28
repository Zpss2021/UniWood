package info.zpss.uniwood.desktop.server.dao;

import info.zpss.uniwood.desktop.server.entity.Post;

import java.util.List;

public interface PostDao {
    Post getPost(Integer postID);

    List<Post> getPosts();

    List<Post> getPostsByZoneID(Integer zoneID);

    List<Post> getPostsByUserID(Integer userID);

    Integer addPost(Integer zoneID);    // 传入分区ID，返回新建贴子的ID

    void deletePost(Integer postID);
}
