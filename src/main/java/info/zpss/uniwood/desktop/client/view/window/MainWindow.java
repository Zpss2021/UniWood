package info.zpss.uniwood.desktop.client.view.window;

import info.zpss.uniwood.desktop.client.Main;
import info.zpss.uniwood.desktop.client.util.ClientLogger;
import info.zpss.uniwood.desktop.client.view.MainView;
import info.zpss.uniwood.desktop.client.view.panel.*;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame implements MainView {
    private Component parent;
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
        Main.logger().add("窗口初始化完成", ClientLogger.Type.INFO, Thread.currentThread());
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

    @Override
    public void showWindow(Component parent) {
        SwingUtilities.invokeLater(() -> {
            this.parent = parent;
            this.setLocationRelativeTo(parent);
            this.pack();
            this.setVisible(true);
            this.validate();
        });
    }

    @Override
    public void hideWindow() {
        SwingUtilities.invokeLater(() -> this.setVisible(false));
    }

    @Override
    public Component getComponent() {
        return this;
    }

    @Override
    public JButton getLoginButton() {
        return userPanel.loginBtn;
    }

    @Override
    public JButton getRegisterButton() {
        return userPanel.registerBtn;
    }

    @Override
    public UserPanel getUserPanel() {
        return userPanel;
    }
}
