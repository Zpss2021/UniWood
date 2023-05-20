package info.zpss.uniwood.desktop.client.model.entity;

import java.util.Date;

// TODO 楼层
public class Floor {
    private Integer id;
    private User author;
    private Date time;
    private String content;

    public Floor() {
    }

    public Floor(Integer id, User author, Date time, String content) {
        this.id = id;
        this.author = author;
        this.time = time;
        this.content = content;
    }

    public Integer getId() {
        return id;
    }

    public User getAuthor() {
        return author;
    }

    public Date getTime() {
        return time;
    }

    public String getContent() {
        return content;
    }
}