package info.zpss.uniwood.client.util;

import info.zpss.uniwood.client.Main;
import info.zpss.uniwood.common.Arguable;
import info.zpss.uniwood.common.Logger;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class WaitTime implements Arguable {
    private int baseCycleMillis;
    private int maxWaitCycle;
    private int baseWaitCycle;
    private double averageWaitCycle;
    private final ScheduledExecutorService executor;

    public WaitTime() {
        this.baseCycleMillis = 20;
        this.maxWaitCycle = 150;
        this.baseWaitCycle = 1;
        this.averageWaitCycle = 1;
        this.executor = Executors.newSingleThreadScheduledExecutor();
    }

    public int getMaxWaitCycle() {
        return maxWaitCycle;
    }

    public int getWaitCycleMills(int waitCount) throws TimeoutException {
        int waitMills = this.baseCycleMillis + (waitCount + baseWaitCycle) * 10;
        if (waitMills > Main.connection().getTimeout())
            throw new TimeoutException();
        averageWaitCycle = averageWaitCycle * 0.8 + waitCount * 0.2;
        return waitMills;
    }

    @Override
    public void config(String[] args) throws Exception {
        String baseCycleMillis = Arguable.stringInArgs(args, "-w", "--wait");
        String maxWaitCycle = Arguable.stringInArgs(args, "-c", "--count");
        if (baseCycleMillis == null) {
            Main.logger().add(String.format("未指定请求消息周期等待时间，使用默认值%d", this.baseCycleMillis),
                    Logger.Type.INFO, Thread.currentThread());
        } else this.baseCycleMillis = Integer.parseInt(baseCycleMillis);
        if (maxWaitCycle == null) {
            Main.logger().add(String.format("未指定请求消息最大等待周期，使用默认值%d", this.maxWaitCycle),
                    Logger.Type.INFO, Thread.currentThread());
        } else this.maxWaitCycle = Integer.parseInt(maxWaitCycle);
        executor.scheduleAtFixedRate(() -> {
            if (Main.debug())
                Main.logger().add(String.format("平均等待周期：%f", averageWaitCycle),
                        Logger.Type.INFO, Thread.currentThread());
            this.baseWaitCycle = (int) averageWaitCycle;
        }, 1, 1, TimeUnit.SECONDS);
    }
}