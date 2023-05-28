package info.zpss.uniwood.server.entity;

// 用户发布的贴子
public class UserPost {
    private Integer userId;
    private Integer postId;

    public UserPost() {
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    @Override
    public String toString() {
        return "UserPost{" +
                "userId=" + userId +
                ", postId=" + postId +
                '}';
    }
}
