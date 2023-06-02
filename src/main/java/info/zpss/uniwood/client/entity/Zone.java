package info.zpss.uniwood.client.entity;

import info.zpss.uniwood.client.util.interfaces.Entity;
import info.zpss.uniwood.common.Command;
import info.zpss.uniwood.common.MsgProto;

public class Zone implements Entity {
    private Integer id;
    private String name;
    private String description;
    private String icon;

    public Zone() {
    }

    public Zone(Integer id, String name, String description, String icon) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.icon = icon;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }

    @Override
    public void update(MsgProto msg) {
        if (msg.cmd.equals(Command.ZONE_INFO)) {
            this.id = Integer.valueOf(msg.args[0]);
            this.name = msg.args[1];
            this.description = msg.args[2];
            this.icon = msg.args[3];
        }
    }
}
