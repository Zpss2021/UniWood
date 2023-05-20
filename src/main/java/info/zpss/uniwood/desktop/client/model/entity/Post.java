package info.zpss.uniwood.desktop.client.model.entity;

import java.util.Date;
import java.util.List;

// TODO 贴子
public class Post {
    private Integer id;
    private Zone zone;
    private User author;
    private Date time;
    private List<Floor> floors;

    public Post() {
    }

    public Post(Integer id, Zone zone, User author, Date time, List<Floor> floors) {
        this.id = id;
        this.zone = zone;
        this.author = author;
        this.time = time;
        this.floors = floors;   // TODO
    }

    public Integer getId() {
        return id;
    }

    public Zone getZone() {
        return zone;
    }

    public User getAuthor() {
        return author;
    }

    public Date getTime() {
        return time;
    }

    public List<Floor> getFloors() {
        return floors;
    }
}
