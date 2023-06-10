package info.zpss.uniwood.server.dao;

import info.zpss.uniwood.server.entity.Post;

import java.util.List;

public interface PostDao {
    Post getPost(Integer postID);

    List<Post> getPostsByUserID(Integer userID);    // 用户发表的贴子

    List<Post> getFavorsByUserID(Integer userID);    // 用户收藏的贴子

    Integer addPost(Integer zoneID);    // 传入分区ID，返回新建贴子的ID

    void deletePost(Integer postID);

    Integer getFloorCount(Integer postID);

    List<Post> getLimitPostsByZoneID(Integer zoneID, Integer from, Integer pageSize);
}
