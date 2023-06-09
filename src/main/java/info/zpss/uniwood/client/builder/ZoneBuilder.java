package info.zpss.uniwood.client.builder;

import info.zpss.uniwood.client.Main;
import info.zpss.uniwood.client.controller.MainController;
import info.zpss.uniwood.client.entity.Zone;
import info.zpss.uniwood.client.util.interfaces.Builder;
import info.zpss.uniwood.common.Command;
import info.zpss.uniwood.common.MsgProto;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class ZoneBuilder implements Builder<Zone> {
    private static final ZoneBuilder INSTANCE;
    private static final int expireSecond = 90;
    private static final Map<Integer, Zone> zones;
    private static boolean isWaiting;

    static {
        INSTANCE = new ZoneBuilder();
        zones = new HashMap<>();
        isWaiting = false;
    }

    public static ZoneBuilder getInstance() {
        return INSTANCE;
    }

    private ZoneBuilder() {
        Thread holder = new Thread(() -> {
            while (true) {
                hold();
                if (!isWaiting) {
                    zones.clear();
                    if (Main.debug())
                        Main.logger().add("ZoneBuilder：缓存已清空", Thread.currentThread());
                }
                try {
                    MainController.getInstance().getModel().getZones(0);
                } catch (InterruptedException | TimeoutException e) {
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

    public Zone get(Integer zoneId) throws InterruptedException, TimeoutException {
        isWaiting = true;
        if (zones.containsKey(zoneId)) {
            isWaiting = false;
            return zones.get(zoneId);
        }
        return build(zoneId, 0);
    }

    public Zone build(Integer zoneId, int count) throws InterruptedException, TimeoutException {
        isWaiting = true;
        if (zones.containsKey(zoneId)) {
            isWaiting = false;
            return zones.get(zoneId);
        }
        if (count == 0)
            new Thread(() -> Main.connection().send(MsgProto.build(Command.ZONE_INFO, zoneId.toString()))).start();
        if (count > Main.maxWaitCycle())
            throw new TimeoutException();
        Thread.sleep(Main.waitCycleMills(count));
        return build(zoneId, count + 1);
    }
}
