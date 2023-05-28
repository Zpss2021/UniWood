package info.zpss.uniwood.desktop.server.entity;

// 关注
public class Follow {
    private Integer followerId;     // 关注者
    private Integer followingId;    // 被关注者

    public Follow() {
    }

    public Integer getFollowerId() {
        return followerId;
    }

    public void setFollowerId(Integer followerId) {
        this.followerId = followerId;
    }

    public Integer getFollowingId() {
        return followingId;
    }

    public void setFollowingId(Integer followingId) {
        this.followingId = followingId;
    }

    @Override
    public String toString() {
        return "Follow{" +
                "followerId=" + followerId +
                ", followingId=" + followingId +
                '}';
    }
}
