package info.zpss.uniwood.client.item;

import info.zpss.uniwood.client.entity.Zone;
import info.zpss.uniwood.client.util.interfaces.Item;

public class ZoneItem implements Item {
    public final Integer id;
    public final String icon;
    public final String name;

    public ZoneItem(Zone zone) {
        this.id = zone.getId();
        this.icon = zone.getIcon();
        this.name = zone.getName();
    }

    @Override
    public String toString() {
        return this.name;
    }
}