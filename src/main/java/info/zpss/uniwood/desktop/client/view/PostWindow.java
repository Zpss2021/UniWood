package info.zpss.uniwood.desktop.client.view;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

// 楼层窗口，用来显示特定贴子内的楼层列表
public class PostWindow extends JFrame {
    private Vector<FloorItem> floorList;
    public final JPanel outerPane;
    public final JPanel floorListPane;
    public final JScrollPane floorListScrollPane;

    public PostWindow(MainWindow parent) {
        super();

        this.outerPane = new JPanel();
        this.floorList = new Vector<>();    // TODO：从贴子对象获取楼层列表
        this.floorListPane = new JPanel();
        this.floorListScrollPane = new JScrollPane(floorListPane);

        this.initWindow();

        this.setTitle("贴子标题");  // TODO：从贴子对象获取标题
        this.setIconImage(new ImageIcon("src/main/resources/default_avatar.jpg").getImage());
        this.setContentPane(outerPane);
        this.setLocationRelativeTo(parent);
        this.setPreferredSize(new Dimension(640, 480));
        this.setMinimumSize(new Dimension(640, 480));
        this.setAlwaysOnTop(true);
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public void initWindow() {
        outerPane.setLayout(new BorderLayout());
        floorListPane.setLayout(new BoxLayout(floorListPane, BoxLayout.Y_AXIS));

        floorListScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        floorListScrollPane.getVerticalScrollBar().setUnitIncrement(16);

        outerPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        outerPane.add(floorListScrollPane);
    }

    public void showWindow() {
        SwingUtilities.invokeLater(() -> {
            this.pack();
            this.setVisible(true);
            this.validate();
        });
    }

    // TODO：添加楼层数据接口

    public static class FloorItem {
        public final int floor;
        public final String avatar;
        public final String title;
        public final String content;

        // TODO：更改构造函数，使其可以从楼层+回贴用户的实体类中构造
        public FloorItem(int floor, String avatar, String title, String content) {
            this.floor = floor;
            this.avatar = avatar;
            this.title = title;
            this.content = content;
        }
    }

    private static class FloorItemRender extends JPanel {
        // TODO：楼层渲染器
    }
}
