package info.zpss.uniwood.desktop.client.model;

import java.util.Date;
import java.util.List;

// 贴子
public class Post {
    private Integer id;
    private Zone zone;
    private String title;
    private User author;
    private Date time;
    private List<Floor> floors;

    public Post() {
    }
}
