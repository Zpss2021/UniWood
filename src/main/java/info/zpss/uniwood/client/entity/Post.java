package info.zpss.uniwood.client.entity;

import info.zpss.uniwood.client.Main;
import info.zpss.uniwood.client.builder.FloorBuilder;
import info.zpss.uniwood.client.builder.PostBuilder;
import info.zpss.uniwood.client.builder.UserBuilder;
import info.zpss.uniwood.client.builder.ZoneBuilder;
import info.zpss.uniwood.client.controller.MainController;
import info.zpss.uniwood.client.util.interfaces.Entity;
import info.zpss.uniwood.common.Command;
import info.zpss.uniwood.common.MsgProto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class Post implements Entity {
    private Integer id;
    private Zone zone;
    private User author;
    private Date time;
    private Integer floorCount;
    private final List<Floor> floors;

    public Post() {
        this.floors = new ArrayList<>(0);
    }

    public Post(Integer id, Zone zone, User author, Date time, Integer floorCount) {
        this.id = id;
        this.zone = zone;
        this.author = author;
        this.time = time;
        this.floorCount = floorCount;
        this.floors = new ArrayList<>(0);
    }

    public Integer getId() {
        return id;
    }

    public Zone getZone() {
        return zone;
    }

    public User getAuthor() {
        return author;
    }

    public Date getTime() {
        return time;
    }

    public Integer getFloorCount() {
        return floorCount;
    }

    public List<Floor> getFloors() {
        return floors;
    }

    @Override
    public void update(MsgProto msg) {
        try {
            if (msg.cmd.equals(Command.POST_INFO)) {
                this.id = Integer.valueOf(msg.args[0]);
                this.zone = ZoneBuilder.getInstance().get(Integer.valueOf(msg.args[1]));
                this.author = UserBuilder.getInstance().get(Integer.valueOf(msg.args[2]));
                this.time = new Date(Long.parseLong(msg.args[3]));
                this.floorCount = Integer.valueOf(msg.args[4]);
                this.floors.add(FloorBuilder.getInstance().get(1, this.id));
            } else if (msg.cmd.equals(Command.ZONE_POST)) {
                ArrayList<Post> p = new ArrayList<>();
                for (String s : msg.args)
                    p.add(PostBuilder.getInstance().get(Integer.parseInt(s)));
                MainController.getInstance().getModel().setZonePosts(p);
            }
        } catch (InterruptedException | TimeoutException e) {
            Main.logger().add("贴子信息更新失败", Thread.currentThread());
            Main.logger().add(e, Thread.currentThread());
        }
    }
}