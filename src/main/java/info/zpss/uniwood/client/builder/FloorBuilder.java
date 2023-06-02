package info.zpss.uniwood.client.builder;

import info.zpss.uniwood.client.Main;
import info.zpss.uniwood.client.entity.Floor;
import info.zpss.uniwood.client.util.interfaces.Builder;
import info.zpss.uniwood.common.Command;
import info.zpss.uniwood.common.MsgProto;

import java.util.HashMap;
import java.util.Map;

public class FloorBuilder implements Builder<Floor> {
    private static final FloorBuilder INSTANCE;
    private static final int expireSecond = 60;
    private static final Map<Integer, Map<Integer, Floor>> floors;

    static {
        INSTANCE = new FloorBuilder();
        floors = new HashMap<>();
    }

    private FloorBuilder() {
        Thread holder = new Thread(() -> {
            while (true) {
                hold();
                floors.clear();
            }
        });
        holder.setDaemon(true);
        holder.start();
    }

    public static FloorBuilder getInstance() {
        return INSTANCE;
    }

    @Override
    public void hold() {
        try {
            Thread.sleep(expireSecond * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void add(Floor floor) {
        if (floors.containsKey(floor.getPostId())) {
            floors.get(floor.getPostId()).put(floor.getId(), floor);
        } else {
            Map<Integer, Floor> map = new HashMap<>();
            map.put(floor.getId(), floor);
            floors.put(floor.getPostId(), map);
        }
    }

    public Floor get(Integer floorId, Integer postId) throws InterruptedException {
        if (floors.containsKey(postId) && floors.get(postId).containsKey(floorId))
            return floors.get(postId).get(floorId);
        return build(floorId, postId);
    }

    public Floor build(Integer floorId, Integer postId) throws InterruptedException {
        if (floors.containsKey(postId) && floors.get(postId).containsKey(floorId))
            return floors.get(postId).get(floorId);
        new Thread(() -> Main.connection().send(
                MsgProto.build(
                        Command.FLOR_INFO,
                        floorId.toString(),
                        postId.toString()
                )
        )).start();
        Thread.sleep(200);
        return build(floorId, postId);
    }
}
