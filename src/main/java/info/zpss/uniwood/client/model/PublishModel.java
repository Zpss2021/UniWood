package info.zpss.uniwood.client.model;

import info.zpss.uniwood.client.util.interfaces.Model;

public class PublishModel implements Model {
    private Integer zoneId;
    private String content;

    public PublishModel() {
        this.init();
    }

    public Integer getZoneId() {
        return zoneId;
    }

    public void setZoneId(Integer zoneId) {
        this.zoneId = zoneId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public void init() {
        zoneId = null;
        content = null;
    }
}
