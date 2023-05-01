package info.zpss.uniwood.desktop.client.view;

import info.zpss.uniwood.desktop.client.view.panel.*;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    // 定义控件
    // 一级面板
    public final JPanel asidePane;  // this.WEST
    public final JPanel mainPane;   // this.CENTER

    // 二级面板
    public final ZonePanel zonePane;   // asidePane.CENTER
    public final JPanel cfgPane;    // asidePane.SOUTH
    public final JPanel ctrlPane;   // mainPane.NORTH
    public final JPanel cntPane;    // mainPane.CENTER

    // 三级面板
    public final LoginPanel loginPane;  // cfgPane
    public final UserPanel userPane;    // cfgPane
    public final SearchPanel searchPane; // ctrlPane.CENTER
    public final BtnPanel btnPane;    // ctrlPane.EAST
    public final PostPanel postPane;    // cntPane
    public final FloorPanel floorPane;  // cntPane

    // 构造函数
    public MainWindow() {
        super();
        // 实例化控件
        // 一级面板
        this.asidePane = new JPanel(new BorderLayout());
        this.mainPane = new JPanel(new BorderLayout());

        // 二级面板
        this.zonePane = new ZonePanel();
        this.cfgPane = new JPanel(new FlowLayout());
        this.ctrlPane = new JPanel(new BorderLayout());
        this.cntPane = new JPanel(new FlowLayout());

        // 三级面板
        this.loginPane = new LoginPanel();
        this.userPane = new UserPanel();
        this.searchPane = new SearchPanel();
        this.btnPane = new BtnPanel();
        this.postPane = new PostPanel();
        this.floorPane = new FloorPanel();

        this.initWindow();

        // 设置全局控件属性
        this.setTitle("UniWood - 桌面端");
        int width = 960;
        int height = 640;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setBounds((screenSize.width - width) / 2, (screenSize.height - height) / 2, width, height);
        this.setMinimumSize(new Dimension(800, 600)); // TODO: 限制最小尺寸
        this.setLayout(new BorderLayout());
        this.add(asidePane, BorderLayout.WEST);
        this.add(mainPane, BorderLayout.CENTER);
//        this.setIconImage(new ImageIcon("src/main/resources/icon.png").getImage());
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
    }

    private void initWindow() {
        // 设置布局及控件属性
        asidePane.setPreferredSize(new Dimension(180, 550));
        mainPane.setPreferredSize(new Dimension(760, 550));

        zonePane.setPreferredSize(new Dimension(180, 500));
        cfgPane.setPreferredSize(new Dimension(180, 100));

        ctrlPane.setPreferredSize(new Dimension(760, 75));
        cntPane.setPreferredSize(new Dimension(760, 550));

        loginPane.setPreferredSize(new Dimension(180, 80));
        userPane.setPreferredSize(new Dimension(180, 80));

        searchPane.setPreferredSize(new Dimension(400, 75));
        btnPane.setPreferredSize(new Dimension(200, 75));

        postPane.setPreferredSize(new Dimension(760, 505));
        floorPane.setPreferredSize(new Dimension(760, 505));

        asidePane.add(zonePane, BorderLayout.CENTER);
        asidePane.add(cfgPane, BorderLayout.SOUTH);
        mainPane.add(ctrlPane, BorderLayout.NORTH);
        mainPane.add(cntPane, BorderLayout.CENTER);
        cfgPane.add(loginPane);
        cfgPane.add(userPane);
        ctrlPane.add(searchPane, BorderLayout.CENTER);
        ctrlPane.add(btnPane, BorderLayout.EAST);
        cntPane.add(postPane);
        cntPane.add(floorPane);

        // 设置默认显示
        loginPane.setVisible(true);
        userPane.setVisible(false);

        postPane.setVisible(true);
        floorPane.setVisible(false);
    }

    public void showWindow() {
        this.setVisible(true);
        this.validate();
    }

    // 测试用入口函数
    public static void main(String[] args) {
        MainWindow w = new MainWindow();
        ZonePanel.ZoneItem[] items = {
                new ZonePanel.ZoneItem("src/main/resources/default_avatar.jpg", "分区1"),
                new ZonePanel.ZoneItem("src/main/resources/default_avatar.jpg", "分区2"),
                new ZonePanel.ZoneItem("src/main/resources/default_avatar.jpg", "分区3"),
                new ZonePanel.ZoneItem("src/main/resources/default_avatar.jpg", "分区4"),
                new ZonePanel.ZoneItem("src/main/resources/default_avatar.jpg", "分区5"),
                new ZonePanel.ZoneItem("src/main/resources/default_avatar.jpg", "分区6"),
                new ZonePanel.ZoneItem("src/main/resources/default_avatar.jpg", "分区7"),
                new ZonePanel.ZoneItem("src/main/resources/default_avatar.jpg", "分区8"),
                new ZonePanel.ZoneItem("src/main/resources/default_avatar.jpg", "分区9"),
                new ZonePanel.ZoneItem("src/main/resources/default_avatar.jpg", "分区10"),
                new ZonePanel.ZoneItem("src/main/resources/default_avatar.jpg", "分区11"),
                new ZonePanel.ZoneItem("src/main/resources/default_avatar.jpg", "分区12"),
                new ZonePanel.ZoneItem("src/main/resources/default_avatar.jpg", "分区13"),
                new ZonePanel.ZoneItem("src/main/resources/default_avatar.jpg", "分区14"),
                new ZonePanel.ZoneItem("src/main/resources/default_avatar.jpg", "分区15"),
                new ZonePanel.ZoneItem("src/main/resources/default_avatar.jpg", "分区16")
        };
        w.zonePane.zoneList.setListData(items);
        w.postPane.setBorderTitle("广场");
        w.postPane.addPost("Alice 河南大学\n#12345 12小时前 12-34 12:34", "帖子1的内容帖子1的内容帖子1的内容帖子1的内容帖子1的内容帖子1的内容帖子1的内容帖子1的内容");
        w.postPane.addPost("Bob 河南大学\n#12345 12小时前 12-34 12:34", "帖子2的内容帖子2的内容帖子2的内容\n帖子2的内容帖子2的内容帖子2的内容帖子2的内容帖子2的内容");
        w.postPane.addPost("Carol 河南大学\n#12345 12小时前 12-34 12:34", "帖子3的内容");
        w.postPane.addPost("Denny 河南大学\n#12345 12小时前 12-34 12:34", "帖子4的内容帖子4的内容帖子4的内容");
        w.postPane.addPost("Elsa 河南大学\n#12345 12小时前 12-34 12:34", "帖子5的内容帖子5\n的内容帖子5的内容\n帖子5的内容\n帖子5的内容");
        w.postPane.addPost("Flank 河南大学\n#12345 12小时前 12-34 12:34", "帖子6的内容\n帖子6的内容");
        w.postPane.addPost("Groin 河南大学\n#12345 12小时前 12-34 12:34", "帖子7的内容帖子7的内容帖子7的内容帖子7的内容帖子7的内容帖子7的内容" +
                "帖子7的内容帖子7的内容帖子7的内容帖子7的内容帖子7的内容帖子7的内容帖子7的内容帖子7的内容");
        w.postPane.addPost("Henny 河南大学\n#12345 12小时前 12-34 12:34", "帖子8的内容");
        w.postPane.addPost("Instance 河南大学\n#12345 12小时前 12-34 12:34", "帖子9的内容");
        w.postPane.addPost("Jack 河南大学\n#12345 12小时前 12-34 12:34", "帖子10的内容");
        w.showWindow();
    }
}
