package info.zpss.uniwood.client.item;

import info.zpss.uniwood.client.entity.Post;
import info.zpss.uniwood.client.util.interfaces.Item;

import java.text.SimpleDateFormat;

public class PostItem implements Item {
    public final int id;
    public final String avatar;
    public final String title;
    public final String content;

    public PostItem(Post post) {
        String createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(post.getTime());
        String description = post.getFloors().get(0).getContent();
        this.id = post.getId();
        this.avatar = post.getAuthor().getAvatar();
        this.title = String.format("%s %s\n#%d %s",
                post.getAuthor().getUsername(), post.getAuthor().getUniversity(), post.getId(), createTime);
        this.content = (description.length() > 100) ? (description.substring(0, 120) + "...") : description;
    }
}