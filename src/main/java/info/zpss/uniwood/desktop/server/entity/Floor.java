package info.zpss.uniwood.desktop.server.entity;

import java.util.Date;

// 楼层
public class Floor {
    private Integer id;
    private Integer author_id;
    private Date createTime;
    private String content;

    public Floor() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAuthorId() {
        return author_id;
    }

    public void setAuthorId(Integer authorId) {
        this.author_id = authorId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Floor{" +
                "id=" + id +
                ", author_id=" + author_id +
                ", createTime=" + createTime +
                ", content='" + content + '\'' +
                '}';
    }
}