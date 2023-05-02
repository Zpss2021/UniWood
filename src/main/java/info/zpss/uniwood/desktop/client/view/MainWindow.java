package info.zpss.uniwood.desktop.client.view;

import info.zpss.uniwood.desktop.client.view.panel.*;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class MainWindow extends JFrame {
    // 定义控件
    public final JPanel outerPane;
    // 一级面板
    public final JPanel asidePane;
    public final JPanel mainPane;

    // 二级面板
    public final ZonePanel zonePane;
    public final JPanel cfgPane;
    public final JPanel ctrlPane;
    public final JPanel cntPane;

    // 三级面板
    public final UserPanel userPanel;
    public final SearchPanel searchPane;
    public final BtnPanel btnPane;
    public final PostPanel postPane;

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
    }

    private static Vector<ZonePanel.ZoneItem> getExampleZoneItems() {
        Vector<ZonePanel.ZoneItem> zoneItems = new Vector<>();
        zoneItems.add(new ZonePanel.ZoneItem("src/main/resources/default_avatar.jpg", "分区1"));
        zoneItems.add(new ZonePanel.ZoneItem("src/main/resources/default_avatar.jpg", "分区2"));
        zoneItems.add(new ZonePanel.ZoneItem("src/main/resources/default_avatar.jpg", "分区3"));
        zoneItems.add(new ZonePanel.ZoneItem("src/main/resources/default_avatar.jpg", "分区4"));
        zoneItems.add(new ZonePanel.ZoneItem("src/main/resources/default_avatar.jpg", "分区5"));
        zoneItems.add(new ZonePanel.ZoneItem("src/main/resources/default_avatar.jpg", "分区6"));
        zoneItems.add(new ZonePanel.ZoneItem("src/main/resources/default_avatar.jpg", "分区7"));
        zoneItems.add(new ZonePanel.ZoneItem("src/main/resources/default_avatar.jpg", "分区8"));
        zoneItems.add(new ZonePanel.ZoneItem("src/main/resources/default_avatar.jpg", "分区9"));
        zoneItems.add(new ZonePanel.ZoneItem("src/main/resources/default_avatar.jpg", "分区10"));
        zoneItems.add(new ZonePanel.ZoneItem("src/main/resources/default_avatar.jpg", "分区11"));
        zoneItems.add(new ZonePanel.ZoneItem("src/main/resources/default_avatar.jpg", "分区12"));
        zoneItems.add(new ZonePanel.ZoneItem("src/main/resources/default_avatar.jpg", "分区13"));
        zoneItems.add(new ZonePanel.ZoneItem("src/main/resources/default_avatar.jpg", "分区14"));
        zoneItems.add(new ZonePanel.ZoneItem("src/main/resources/default_avatar.jpg", "分区15"));
        zoneItems.add(new ZonePanel.ZoneItem("src/main/resources/default_avatar.jpg", "分区16"));
        return zoneItems;
    }

    private static Vector<PostPanel.PostItem> getExamplePostItems() {
        Vector<PostPanel.PostItem> postItems = new Vector<>();
        postItems.add(new PostPanel.PostItem(1001, "src/main/resources/default_avatar.jpg", "Alice 河南大学\n#12345 12小时前 12-34 12:34", "帖子1的内容帖子1的内容帖子1的内容帖子1的内容帖子1的内容帖子1的内容帖子1的内容帖子1的内容"));
        postItems.add(new PostPanel.PostItem(1002, "src/main/resources/default_avatar.jpg", "Bob 河南大学\n#12345 12小时前 12-34 12:34", "帖子2的内容帖子2的内容帖子2的内容\n帖子2的内容帖子2的内容帖子2的内容帖子2的内容帖子2的内容"));
        postItems.add(new PostPanel.PostItem(1003, "src/main/resources/default_avatar.jpg", "Carol 河南大学\n#12345 12小时前 12-34 12:34", "帖子3的内容"));
        postItems.add(new PostPanel.PostItem(1004, "src/main/resources/default_avatar.jpg", "Denny 河南大学\n#12345 12小时前 12-34 12:34", "帖子4的内容帖子4的内容帖子4的内容"));
        postItems.add(new PostPanel.PostItem(1005, "src/main/resources/default_avatar.jpg", "Elsa 河南大学\n#12345 12小时前 12-34 12:34", "帖子5的内容帖子5\n的内容帖子5的内容\n帖子5的内容\n帖子5的内容"));
        postItems.add(new PostPanel.PostItem(1006, "src/main/resources/default_avatar.jpg", "Flank 河南大学\n#12345 12小时前 12-34 12:34", "帖子6的内容\n帖子6的内容"));
        postItems.add(new PostPanel.PostItem(1007, "src/main/resources/default_avatar.jpg", "Groin 河南大学\n#12345 12小时前 12-34 12:34", "帖子7的内容帖子7的内容帖子7的内容帖子7的内容帖子7的内容帖子7的内容" + "帖子7的内容帖子7的内容帖子7的内容帖子7的内容帖子7的内容帖子7的内容帖子7的内容帖子7的内容"));
        postItems.add(new PostPanel.PostItem(1008, "src/main/resources/default_avatar.jpg", "Henny 河南大学\n#12345 12小时前 12-34 12:34", "帖子8的内容"));
        postItems.add(new PostPanel.PostItem(1009, "src/main/resources/default_avatar.jpg", "Instance 河南大学\n#12345 12小时前 12-34 12:34", "帖子9的内容"));
        postItems.add(new PostPanel.PostItem(1010, "src/main/resources/default_avatar.jpg", "Jack 河南大学\n#12345 12小时前 12-34 12:34", "帖子10的内容"));
        return postItems;
    }
}
