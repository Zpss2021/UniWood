package info.zpss.uniwood.desktop.server.model;

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

    public void setId(Integer id) {
        this.id = id;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Floor{" +
                "id=" + id +
                ", author=" + author +
                ", time=" + time +
                ", content='" + content + '\'' +
                '}';
    }
}