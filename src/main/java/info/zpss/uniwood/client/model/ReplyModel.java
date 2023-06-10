package info.zpss.uniwood.client.model;

import info.zpss.uniwood.client.util.interfaces.Model;

public class ReplyModel implements Model {
    private Integer postId;
    private String content;

    public ReplyModel() {
        this.init();
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public void init() {
        postId = null;
        content = null;
    }
}
