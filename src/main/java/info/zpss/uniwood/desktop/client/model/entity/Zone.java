package info.zpss.uniwood.desktop.client.model.entity;

// TODO 分区
public class Zone {
    private Integer id;
    private String name;
    private String icon;
    private String description;

    public Zone() {
    }

    public Zone(Integer id, String name, String icon, String description) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIcon() {
        return icon;
    }

    public String getDescription() {
        return description;
    }
}
