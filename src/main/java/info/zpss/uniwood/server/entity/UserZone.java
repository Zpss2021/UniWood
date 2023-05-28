package info.zpss.uniwood.server.entity;

// 用户加入的分区
public class UserZone {
    private Integer userId;
    private Integer zoneId;

    public UserZone() {
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getZoneId() {
        return zoneId;
    }

    public void setZoneId(Integer zoneId) {
        this.zoneId = zoneId;
    }

    @Override
    public String toString() {
        return "UserZone{" +
                "userId=" + userId +
                ", zoneId=" + zoneId +
                '}';
    }
}
