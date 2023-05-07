package info.zpss.uniwood.desktop.client.view.window;

import info.zpss.uniwood.desktop.client.Main;
import info.zpss.uniwood.desktop.client.model.Floor;
import info.zpss.uniwood.desktop.client.model.Post;
import info.zpss.uniwood.desktop.client.model.User;
import info.zpss.uniwood.desktop.client.model.Zone;
import info.zpss.uniwood.desktop.client.view.MainWindowView;
import info.zpss.uniwood.desktop.client.view.panel.*;
import info.zpss.uniwood.desktop.common.Log;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class MainWindow extends JFrame implements MainWindowView {
    // 定义控件
    private final JPanel outerPane;
    // 一级面板
    private final JPanel asidePane;
    private final JPanel mainPane;

    // 二级面板
    private final ZonePanel zonePane;
    private final JPanel cfgPane;
    private final JPanel ctrlPane;
    private final JPanel cntPane;

    // 三级面板
    private final UserPanel userPanel;
    private final SearchPanel searchPane;
    private final BtnPanel btnPane;
    private final PostPanel postPane;

    // 构造函数
    public MainWindow() {
        super();
        this.outerPane = new JPanel(new BorderLayout());
        this.asidePane = new JPanel(new BorderLayout());
        this.mainPane = new JPanel(new BorderLayout());

        this.zonePane = new ZonePanel();
        this.cfgPane = new JPanel(new BorderLayout());
        this.ctrlPane = new JPanel(new BorderLayout());
        this.cntPane = new JPanel(new BorderLayout());

        this.userPanel = new UserPanel();
        this.searchPane = new SearchPanel();
        this.btnPane = new BtnPanel();
        this.postPane = new PostPanel();

        this.initWindow();

        Dimension windowSize = new Dimension(960, 640);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setTitle("UniWood - 桌面端");
        this.setIconImage(new ImageIcon("src/main/resources/default_avatar.jpg").getImage());
        this.setContentPane(outerPane);
        this.setBounds((screenSize.width - windowSize.width) / 2, (screenSize.height - windowSize.height) / 2,
                windowSize.width, windowSize.height);
        this.setPreferredSize(windowSize);
        this.setMinimumSize(new Dimension(900, 320));
        this.setAlwaysOnTop(false);
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
        SwingUtilities.updateComponentTreeUI(this);
        Log.add("窗口初始化完成", Log.Type.INFO, Thread.currentThread());
    }

    private void initWindow() {
        outerPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        asidePane.setPreferredSize(new Dimension(180, 550));
        mainPane.setPreferredSize(new Dimension(760, 550));

        zonePane.setPreferredSize(new Dimension(180, 500));
        cfgPane.setPreferredSize(new Dimension(180, 100));

        ctrlPane.setPreferredSize(new Dimension(760, 100));
        cntPane.setPreferredSize(new Dimension(760, 550));

        searchPane.setPreferredSize(new Dimension(400, 100));
        btnPane.setPreferredSize(new Dimension(200, 100));

        outerPane.add(asidePane, BorderLayout.WEST);
        outerPane.add(mainPane, BorderLayout.CENTER);

        asidePane.add(zonePane, BorderLayout.CENTER);
        asidePane.add(cfgPane, BorderLayout.SOUTH);

        mainPane.add(ctrlPane, BorderLayout.NORTH);
        mainPane.add(cntPane, BorderLayout.CENTER);

        cfgPane.add(userPanel, BorderLayout.CENTER);

        ctrlPane.add(searchPane, BorderLayout.CENTER);
        ctrlPane.add(btnPane, BorderLayout.EAST);

        cntPane.add(postPane, BorderLayout.CENTER);
    }

    public void showWindow() {
        SwingUtilities.invokeLater(() -> {
            this.pack();
            this.setVisible(true);
            this.validate();
        });
    }

    // 测试用入口函数
    public static void main(String[] args) {
        MainWindow w = new MainWindow();

        w.zonePane.zoneList.setListData(getExampleZoneItems());
        w.zonePane.zoneList.setSelectedIndex(0);

        w.postPane.setZoneTitle(w.zonePane.zoneList.getSelectedValue().toString()); // TODO：添加到事件
        w.postPane.setListData(getExamplePostItems());

        w.showWindow();

        PostWindow postWindow = new PostWindow(w);
        postWindow.getFloorPanel().setFloorNumberTitle("共10层");
        postWindow.getFloorPanel().setListData(getExampleFloorItems());
        postWindow.showWindow();
    }

    private static Vector<ZonePanel.ZoneItem> getExampleZoneItems() {
        Vector<ZonePanel.ZoneItem> zoneItems = new Vector<>();

        zoneItems.add(new ZonePanel.ZoneItem(new Zone(1, "分区1", "src/main/resources/default_avatar.jpg", "描述分区1")));
        zoneItems.add(new ZonePanel.ZoneItem(new Zone(2, "分区2", "src/main/resources/default_avatar.jpg", "描述分区2")));
        zoneItems.add(new ZonePanel.ZoneItem(new Zone(3, "分区3", "src/main/resources/default_avatar.jpg", "描述分区3")));
        zoneItems.add(new ZonePanel.ZoneItem(new Zone(4, "分区4", "src/main/resources/default_avatar.jpg", "描述分区4")));
        zoneItems.add(new ZonePanel.ZoneItem(new Zone(5, "分区5", "src/main/resources/default_avatar.jpg", "描述分区5")));
        zoneItems.add(new ZonePanel.ZoneItem(new Zone(6, "分区6", "src/main/resources/default_avatar.jpg", "描述分区6")));
        zoneItems.add(new ZonePanel.ZoneItem(new Zone(7, "分区7", "src/main/resources/default_avatar.jpg", "描述分区7")));
        zoneItems.add(new ZonePanel.ZoneItem(new Zone(8, "分区8", "src/main/resources/default_avatar.jpg", "描述分区8")));
        zoneItems.add(new ZonePanel.ZoneItem(new Zone(9, "分区9", "src/main/resources/default_avatar.jpg", "描述分区9")));
        zoneItems.add(new ZonePanel.ZoneItem(new Zone(10, "分区10", "src/main/resources/default_avatar.jpg", "分区10")));

        return zoneItems;
    }

    private static Vector<PostPanel.PostItem> getExamplePostItems() {
        Vector<PostPanel.PostItem> postItems = new Vector<>();

        Zone zone1 = new Zone(1, "分区1", "src/main/resources/default_avatar.jpg", "描述分区1"),
                zone2 = new Zone(2, "分区2", "src/main/resources/default_avatar.jpg", "描述分区2");

        User user1 = new User(1001, "Alice", "src/main/resources/default_avatar.jpg", "河南大学"),
                user2 = new User(1002, "Bob", "src/main/resources/default_avatar.jpg", "清华大学");

        Date date1 = new Date(),
                date2 = new Date();

        List<Floor> floors1 = List.of(new Floor(1, new User(), new Date(), "111")),
                floors2 = List.of(new Floor(1, new User(), new Date(), "贴子二贴子二贴子二贴子二贴子二贴子二贴子二贴子二" +
                        "贴子二贴子二贴子二贴子二贴子二贴子二贴子二贴子二贴子二贴子二贴子二贴子二贴子二贴子二贴子二贴子二贴子二贴子二" +
                        "贴子二贴子二贴子二贴子二贴子二贴子二贴子二贴子二贴子二贴子二贴子二贴子二贴子二贴子二贴子二贴子二贴子二贴子二" +
                        "贴子二贴子二贴子二贴子二贴子二贴子二贴子二贴子二贴子二贴子二贴子二贴子二贴子二贴子二贴子二贴子二贴子二贴子二" +
                        "贴子二贴子二贴子二贴子二贴子二贴子二贴子二贴子二贴子二贴子二贴子二贴子二贴子二贴子二贴子二贴子二贴子二贴子二" +
                        "贴子二贴子二贴子二贴子二贴子二贴子二贴子二贴子二贴子二贴子二贴子二贴子二贴子二贴子二贴子二贴子二贴子二贴子二" +
                        "贴子二贴子二贴子二贴子二贴子二贴子二贴子二贴子二贴子二贴子二"));

        Post post1 = new Post(10001, zone1, user1, date1, floors1);
        Post post2 = new Post(10002, zone2, user2, date2, floors2);

        postItems.add(new PostPanel.PostItem(post1));
        postItems.add(new PostPanel.PostItem(post2));
        postItems.add(new PostPanel.PostItem(post1));
        postItems.add(new PostPanel.PostItem(post2));
        postItems.add(new PostPanel.PostItem(post1));
        postItems.add(new PostPanel.PostItem(post2));

        return postItems;
    }

    private static Vector<FloorPanel.FloorItem> getExampleFloorItems() {
        Vector<FloorPanel.FloorItem> floorItems = new Vector<>();

        User user1 = new User(1001, "Alice", "src/main/resources/default_avatar.jpg", "河南大学"),
                user2 = new User(1002, "Bob", "src/main/resources/default_avatar.jpg", "清华大学");

        Date date1 = new Date(),
                date2 = new Date();

        Floor floor1 = new Floor(1, user1, date1, "111"),
                floor2 = new Floor(2, user2, date2, "22222222222222222222222222222222222222222222222222222222222222" +
                        "222222222222222222222222222222222222222222222222222222222222222222222222222222222222222");

        floorItems.add(new FloorPanel.FloorItem(floor1));
        floorItems.add(new FloorPanel.FloorItem(floor2));
        floorItems.add(new FloorPanel.FloorItem(floor1));
        floorItems.add(new FloorPanel.FloorItem(floor2));
        floorItems.add(new FloorPanel.FloorItem(floor1));
        floorItems.add(new FloorPanel.FloorItem(floor2));
        floorItems.add(new FloorPanel.FloorItem(floor1));
        floorItems.add(new FloorPanel.FloorItem(floor2));
        floorItems.add(new FloorPanel.FloorItem(floor1));
        floorItems.add(new FloorPanel.FloorItem(floor2));


        return floorItems;
    }
}
