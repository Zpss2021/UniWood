package info.zpss.uniwood.client.util.builders;

import info.zpss.uniwood.client.Main;
import info.zpss.uniwood.client.entity.User;
import info.zpss.uniwood.common.Command;
import info.zpss.uniwood.common.MsgProto;

import java.util.HashMap;
import java.util.Map;

public class UserBuilder {
    private static final UserBuilder INSTANCE;
    private static final int expireSecond = 180;
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
            }
        });
        holder.setDaemon(true);
        holder.start();
    }

    private void hold() {
        try {
            Thread.sleep(expireSecond * 1000);
        } catch (InterruptedException e) {
            Main.logger().add(e, Thread.currentThread());
        }
    }

    public void add(User user) {
        users.put(user.getId(), user);
    }

    public User get(Integer userId) throws InterruptedException {
        if (users.containsKey(userId))
            return users.get(userId);
        return build(userId);
    }

    public User build(Integer userId) throws InterruptedException {
        if (users.containsKey(userId))
            return users.get(userId);
        new Thread(() -> Main.connection().send(MsgProto.build(Command.USER_INFO, userId.toString()))).start();
        Thread.sleep(200);
        return build(userId);
    }
}
