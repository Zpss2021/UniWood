package info.zpss.uniwood.client.item;

import info.zpss.uniwood.client.entity.Floor;
import info.zpss.uniwood.client.util.interfaces.Item;

import java.text.SimpleDateFormat;

public class FloorItem implements Item {
    public final int id;
    public final String avatar;
    public final String username;
    public final String university;
    public final String time;
    public final String content;

    public FloorItem(Floor floor) {
        this.id = floor.getId();
        this.avatar = floor.getAuthor().getAvatar();
        this.username = floor.getAuthor().getUsername();
        this.university = floor.getAuthor().getUniversity();
        this.time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(floor.getTime());
        this.content = floor.getContent();
    }
}