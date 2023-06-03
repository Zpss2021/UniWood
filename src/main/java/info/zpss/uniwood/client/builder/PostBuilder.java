package info.zpss.uniwood.client.builder;

import info.zpss.uniwood.client.Main;
import info.zpss.uniwood.client.entity.Post;
import info.zpss.uniwood.client.util.interfaces.Builder;
import info.zpss.uniwood.common.Command;
import info.zpss.uniwood.common.MsgProto;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class PostBuilder implements Builder<Post> {
    private static final PostBuilder INSTANCE;
    private static final int expireSecond = 60;
    private static final Map<Integer, Post> posts;

    static {
        INSTANCE = new PostBuilder();
        posts = new HashMap<>();
    }

    public static PostBuilder getInstance() {
        return INSTANCE;
    }

    private PostBuilder() {
        Thread holder = new Thread(() -> {
            while (true) {
                hold();
                posts.clear();
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
            e.printStackTrace();
        }
    }

    @Override
    public void add(Post post) {
        posts.put(post.getId(), post);
    }

    public Post get(Integer postId) throws InterruptedException, TimeoutException {
        if (posts.containsKey(postId))
            return posts.get(postId);
        return build(postId, 0);
    }

    public Post build(Integer postId, int count) throws InterruptedException, TimeoutException {
        if (posts.containsKey(postId))
            return posts.get(postId);
        if (count == 0)
            new Thread(() -> Main.connection().send(MsgProto.build(Command.POST_INFO, postId.toString()))).start();
        if (count > Main.maxWaitCycle())
            throw new TimeoutException();
        Thread.sleep(Main.waitCycleMills(count));
        return build(postId, count + 1);
    }
}
