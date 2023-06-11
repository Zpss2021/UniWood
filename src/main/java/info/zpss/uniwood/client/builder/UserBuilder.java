package info.zpss.uniwood.client.builder;

import info.zpss.uniwood.client.Main;
import info.zpss.uniwood.client.entity.User;
import info.zpss.uniwood.client.util.interfaces.Builder;
import info.zpss.uniwood.common.Command;
import info.zpss.uniwood.common.MsgProto;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class UserBuilder implements Builder<User> {
    private static final UserBuilder INSTANCE;
    private static final int expireSecond = 60;
    private static final Map<Integer, User> users;

    static {
        INSTANCE = new UserBuilder();
        users = new HashMap<>();
    }

    public static UserBuilder getInstance() {
        return INSTANCE;
    }

    private UserBuilder() {
        Thread holder = new Thread(() -> {
            while (true) {
                hold();
                users.clear();
                if (Main.debug())
                    Main.logger().add("UserBuilder：缓存已清空", Thread.currentThread());
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
    public void add(User user) {
        users.put(user.getId(), user);
    }

    public User get(Integer userId) throws InterruptedException, TimeoutException {
        if (users.containsKey(userId))
            return users.get(userId);
        return build(userId, 0);
    }

    public User build(Integer userId, int count) throws InterruptedException, TimeoutException {
        if (users.containsKey(userId))
            return users.get(userId);
        if (count == 0)
            new Thread(() -> Main.connection().send(MsgProto.build(Command.USER_INFO, userId.toString()))).start();
        if (count > Main.maxWaitCycle())
            throw new TimeoutException();
        Thread.sleep(Main.waitCycleMills(count));
        return build(userId, count + 1);
    }

    public void remove(Integer userId) {
        users.remove(userId);
    }
}
