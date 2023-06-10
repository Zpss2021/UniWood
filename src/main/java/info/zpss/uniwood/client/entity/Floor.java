package info.zpss.uniwood.client.entity;

import info.zpss.uniwood.client.builder.UserBuilder;
import info.zpss.uniwood.client.util.interfaces.Entity;
import info.zpss.uniwood.common.Command;
import info.zpss.uniwood.common.MsgProto;

import java.util.Date;

public class Floor implements Entity {
    private Integer id;
    private Integer postId;
    private User author;
    private Date time;
    private String content;

    public Floor() {
    }

    public Floor(Integer id, Integer postId, User author, Date time, String content) {
        this.id = id;
        this.postId = postId;
        this.author = author;
        this.time = time;
        this.content = content;
    }

    public Integer getId() {
        return id;
    }

    public Integer getPostId() {
        return postId;
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

    @Override
    public void update(MsgProto msg) {
        try {
            if (msg.cmd.equals(Command.FLOR_INFO)) {
                this.id = Integer.valueOf(msg.args[0]);
                this.postId = Integer.valueOf(msg.args[1]);
                this.author = UserBuilder.getInstance().get(Integer.valueOf(msg.args[2]));
                this.time = new Date(Long.parseLong(msg.args[3]));
                this.content = MsgProto.linebreakToChar(msg.args[4]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}