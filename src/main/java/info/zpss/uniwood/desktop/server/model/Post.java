package info.zpss.uniwood.desktop.server.model;

// 贴子
public class Post {
    private Integer id;
    private Integer zone_id;

    public Post() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getZoneId() {
        return zone_id;
    }

    public void setZoneId(Integer zoneId) {
        this.zone_id = zoneId;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", zone_id=" + zone_id +
                '}';
    }
}
