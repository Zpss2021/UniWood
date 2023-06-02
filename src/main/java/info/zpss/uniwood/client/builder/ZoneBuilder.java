package info.zpss.uniwood.client.builder;

import info.zpss.uniwood.client.Main;
import info.zpss.uniwood.client.controller.MainController;
import info.zpss.uniwood.client.entity.Zone;
import info.zpss.uniwood.client.util.interfaces.Builder;
import info.zpss.uniwood.common.Command;
import info.zpss.uniwood.common.MsgProto;

import java.util.HashMap;
import java.util.Map;

public class ZoneBuilder implements Builder<Zone> {
    private static final ZoneBuilder INSTANCE;
    private static final int expireSecond = 900;
    private static final Map<Integer, Zone> zones;

    static {
        INSTANCE = new ZoneBuilder();
        zones = new HashMap<>();
    }

    public static ZoneBuilder getInstance() {
        return INSTANCE;
    }

    private ZoneBuilder() {
        Thread holder = new Thread(() -> {
            while (true) {
                hold();
                zones.clear();
                try {
                    MainController.getInstance().getModel().getZones();
                } catch (InterruptedException e) {
                    Main.logger().add(e, Thread.currentThread());
                }
            }
        });
        holder.setDaemon(true);
        holder.start();
    }

    @Override
    public void hold() {
        try {
            Thread.sleep(expireSecond * 1000);
        } catch (InterruptedException e) {
            Main.logger().add(e, Thread.currentThread());
        }
    }

    @Override
    public void add(Zone zone) {
        zones.put(zone.getId(), zone);
    }

    @Override
    public Zone get(Integer zoneId) throws InterruptedException {
        if (zones.containsKey(zoneId))
            return zones.get(zoneId);
        return build(zoneId);
    }

    @Override
    public Zone build(Integer zoneId) throws InterruptedException {
        if (zones.containsKey(zoneId))
            return zones.get(zoneId);
        new Thread(() -> Main.connection().send(MsgProto.build(Command.ZONE_INFO, zoneId.toString()))).start();
        Thread.sleep(200);
        return build(zoneId);
    }
}
