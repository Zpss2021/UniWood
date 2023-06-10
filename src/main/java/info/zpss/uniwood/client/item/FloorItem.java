package info.zpss.uniwood.client.item;

import info.zpss.uniwood.client.entity.Floor;
import info.zpss.uniwood.client.util.TimeDesc;
import info.zpss.uniwood.client.util.interfaces.Item;

import java.text.SimpleDateFormat;

public class FloorItem implements Item {
    public final int id;
    public final String avatar;
    public final String username;
    public final String university;
    public final String time;
    public final String content;
    private final Floor floor;

    public FloorItem(Floor floor) {
        this.id = floor.getId();
        this.avatar = floor.getAuthor().getAvatar();
        this.username = floor.getAuthor().getUsername();
        this.university = floor.getAuthor().getUniversity();
        this.time = String.format("%s %s", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(floor.getTime()),
                TimeDesc.getTimeDesc(floor.getTime().getTime()));
        this.content = floor.getContent();
        this.floor = floor;
    }

    public Floor getFloor() {
        return this.floor;
    }
}