package info.zpss.uniwood.client.view.window;

import info.zpss.uniwood.client.Main;
import info.zpss.uniwood.client.controller.MainController;
import info.zpss.uniwood.client.util.Avatar;
import info.zpss.uniwood.client.util.ClientLogger;
import info.zpss.uniwood.client.util.FontMaker;
import info.zpss.uniwood.client.util.interfaces.Renderable;
import info.zpss.uniwood.client.view.MainView;
import info.zpss.uniwood.client.item.PostItem;
import info.zpss.uniwood.client.item.ZoneItem;
import info.zpss.uniwood.client.view.render.PostItemRender;
import info.zpss.uniwood.client.view.render.ZoneItemRender;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.Enumeration;
import java.util.Vector;
import java.util.concurrent.TimeoutException;

public class MainWindow extends JFrame implements MainView {
    private final JPanel outerPane, asidePane, mainPane, cfgPane, ctrlPane, cntPane;
    private final ZonePanel zonePane;
    private final UserPanel userPanel;
    private final SearchPanel searchPane;
    private final BtnPanel btnPane;
    private final PostPanel postPane;

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

        this.initGlobalFont(new FontMaker().build());
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
            this.pack();
            this.setVisible(true);
            this.validate();
            this.setLocationRelativeTo(parent);
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
    public JButton getLoginOrUserCenterButton() {
        return userPanel.loginOrUserCenterBtn;
    }

    @Override
    public JButton getRegisterOrLogoutButton() {
        return userPanel.registerOrLogoutBtn;
    }

    @Override
    public UserPanel getUserPanel() {
        return userPanel;
    }

    @Override
    public PostPanel getPostPanel() {
        return postPane;
    }

    @Override
    public BtnPanel getButtonPanel() {
        return btnPane;
    }

    @Override
    public SearchPanel getSearchPanel() {
        return searchPane;
    }

    @Override
    public ZonePanel getZonePanel() {
        return zonePane;
    }

    @Override
    public void initGlobalFont(Font font) {
        FontUIResource fontRes = new FontUIResource(font);
        for (Enumeration<Object> keys = UIManager.getDefaults().keys(); keys.hasMoreElements(); ) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource)
                UIManager.put(key, fontRes);
        }
    }

    public static class BtnPanel extends JPanel {
        public final JButton newPostBtn;
        public final JButton refreshBtn;
        public final JButton prevPageBtn;
        public final JButton nextPageBtn;

        public BtnPanel() {
            super();

            this.newPostBtn = new JButton("发帖");
            this.refreshBtn = new JButton("刷新");
            this.prevPageBtn = new JButton("上一页");
            this.nextPageBtn = new JButton("下一页");

            newPostBtn.setIcon(new ImageIcon(new ImageIcon("src/main/resources/发布.png")
                    .getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH)));
            refreshBtn.setIcon(new ImageIcon(new ImageIcon("src/main/resources/刷新.png")
                    .getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH)));
            prevPageBtn.setIcon(new ImageIcon(new ImageIcon("src/main/resources/上一页.png")
                    .getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH)));
            nextPageBtn.setIcon(new ImageIcon(new ImageIcon("src/main/resources/下一页.png")
                    .getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH)));

            this.setLayout(new GridLayout(2, 2, 5, 5));

            this.add(newPostBtn);
            this.add(prevPageBtn);
            this.add(refreshBtn);
            this.add(nextPageBtn);

            this.setSize(300, 100);
            this.setBorder(BorderFactory.createTitledBorder("操作"));
        }

        public void register() {
            newPostBtn.addActionListener(e -> MainController.getInstance().newPost());
            refreshBtn.addActionListener(e -> MainController.getInstance().refresh());
            prevPageBtn.addActionListener(e -> MainController.getInstance().prevPage());
            nextPageBtn.addActionListener(e -> MainController.getInstance().nextPage());
        }
    }

    public static class SearchPanel extends JPanel {
        public final JPanel searchPanel;
        public final JPanel ctrlPanel;
        public final JTextField searchField;
        public final JButton searchBtn;
        public final JPanel radioPane;
        public final JRadioButton postRadio;
        public final JRadioButton zoneRadio;
        public final JRadioButton userRadio;

        public SearchPanel() {
            super();

            this.searchPanel = new JPanel(new GridBagLayout());
            this.ctrlPanel = new JPanel();
            this.searchField = new JTextField();
            this.searchBtn = new JButton("搜索");
            this.radioPane = new JPanel(new GridLayout(3, 1));
            this.postRadio = new JRadioButton("贴子");
            this.zoneRadio = new JRadioButton("分区");
            this.userRadio = new JRadioButton("用户");

            searchBtn.setIcon(new ImageIcon(new ImageIcon("src/main/resources/搜索.png")
                    .getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));

            radioPane.add(postRadio);
            radioPane.add(zoneRadio);
            radioPane.add(userRadio);

//            ButtonGroup group = new ButtonGroup(); TODO
            searchField.setFont(new FontMaker().bold().large().build());
            searchField.setText(" 输入关键词搜索贴子/分区/用户"); // TODO：根据不同的搜索类型，显示不同的提示
            searchField.setForeground(Color.GRAY);
            searchField.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent evt) {
                    if (searchField.getText().equals("输入关键词搜索贴子/分区/用户")) {
                        searchField.setText("");
                        searchField.setForeground(Color.BLACK);
                    }
                }

                @Override
                public void focusLost(FocusEvent evt) {
                    if (searchField.getText().equals("")) {
                        searchField.setText("输入关键词搜索贴子/分区/用户");
                        searchField.setForeground(Color.GRAY);
                    }
                }
            });

            searchField.setPreferredSize(new Dimension(250, 60));
            radioPane.setPreferredSize(new Dimension(60, 60));
            searchBtn.setPreferredSize(new Dimension(80, 60));


            searchPanel.add(searchField, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER,
                    GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
            ctrlPanel.add(radioPane);
            ctrlPanel.add(searchBtn);

            this.setLayout(new BorderLayout());
            this.add(searchPanel, BorderLayout.CENTER);
            this.add(ctrlPanel, BorderLayout.EAST);

            this.setBorder(BorderFactory.createTitledBorder("搜索"));
        }
    }

    public static class UserPanel extends JPanel {
        public final int avatarLen;
        public final JLabel avatarLbl;
        public final JButton loginOrUserCenterBtn;
        public final JButton registerOrLogoutBtn;

        public UserPanel() {
            super();
            avatarLen = 56;
            avatarLbl = new JLabel();
            loginOrUserCenterBtn = new JButton("登录");
            registerOrLogoutBtn = new JButton("注册");

            Icon avatarIcon = new ImageIcon(new ImageIcon("src/main/resources/default_avatar.jpg")
                    .getImage().getScaledInstance(avatarLen, avatarLen, Image.SCALE_SMOOTH));
            avatarLbl.setIcon(avatarIcon);
            loginOrUserCenterBtn.setIcon(new ImageIcon(new ImageIcon("src/main/resources/登录.png")
                    .getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH)));
            registerOrLogoutBtn.setIcon(new ImageIcon(new ImageIcon("src/main/resources/注册.png")
                    .getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH)));
            avatarLbl.setSize(avatarLen, avatarLen);
            loginOrUserCenterBtn.setSize(85, 30);
            registerOrLogoutBtn.setSize(85, 30);

            this.setLayout(null);

            avatarLbl.setLocation(15, 25);
            loginOrUserCenterBtn.setLocation(75, 25);
            registerOrLogoutBtn.setLocation(75, 55);

            this.add(avatarLbl);
            this.add(loginOrUserCenterBtn);
            this.add(registerOrLogoutBtn);

            this.setSize(150, 115);
            this.setBorder(BorderFactory.createTitledBorder("登录"));
        }

        public void setLogin(String avatarBase64) {
            Avatar avatar = new Avatar();
            avatar.fromBase64(avatarBase64);
            avatarLbl.setIcon(avatar.toIcon(avatarLen));
            loginOrUserCenterBtn.setText("我的");
            registerOrLogoutBtn.setText("登出");
            loginOrUserCenterBtn.setIcon(new ImageIcon(new ImageIcon("src/main/resources/个人中心.png")
                    .getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH)));
        }

        public void setLogout() {
            Icon avatarIcon = new ImageIcon(new ImageIcon("src/main/resources/default_avatar.jpg")
                    .getImage().getScaledInstance(avatarLen, avatarLen, Image.SCALE_SMOOTH));
            avatarLbl.setIcon(avatarIcon);
            loginOrUserCenterBtn.setText("登录");
            registerOrLogoutBtn.setText("注册");
            loginOrUserCenterBtn.setIcon(new ImageIcon(new ImageIcon("src/main/resources/登录.png")
                    .getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH)));
        }
    }

    public static class PostPanel extends JPanel implements Renderable<PostItem> {
        private Vector<PostItem> listData;
        public final JPanel postListPane;
        public final JScrollPane postListScrollPane;

        public PostPanel() {
            super();
            this.listData = new Vector<>();
            this.postListPane = new JPanel();

            this.postListPane.setLayout(new GridLayout(0, 1));
            this.setBorder(BorderFactory.createTitledBorder("广场"));

            this.postListScrollPane = new JScrollPane(postListPane);
            postListScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            postListScrollPane.getVerticalScrollBar().setUnitIncrement(16);

            this.setLayout(new BorderLayout());

            JPanel blankPanel = new JPanel();
            blankPanel.setBackground(Color.WHITE);
            this.add(postListScrollPane, BorderLayout.NORTH);
            this.add(blankPanel, BorderLayout.CENTER);
        }

        @Override
        public void setListData(Vector<PostItem> postList) {
            this.postListPane.removeAll();
            this.listData = postList;
            for (PostItem item : postList)
                this.postListPane.add(new PostItemRender(item));
        }

        @Override
        public void add(PostItem item) {
            this.listData.add(item);
            this.postListPane.add(new PostItemRender(item));
        }

        @Override
        public void addAll(Vector<PostItem> listData) {
            this.listData.addAll(listData);
            for (PostItem item : listData)
                this.postListPane.add(new PostItemRender(item));
        }

        @Override
        public void update(PostItem item) {
            for (int i = 0; i < this.listData.size(); i++) {
                if (this.listData.get(i).id == item.id) {
                    this.listData.set(i, item);
                    for (Component c : this.postListPane.getComponents())
                        if (c instanceof PostItemRender) {
                            PostItemRender p = (PostItemRender) c;
                            if (p.getItem().id == item.id) {
                                p.update(item);
                                this.postListPane.repaint();
                                break;
                            }
                        }
                    break;
                }
            }
        }

        @Override
        public void clear() {
            postListPane.removeAll();
            postListPane.repaint();
            listData.clear();
        }

        @Override
        public void repaint() {
            super.repaint();
            if (postListScrollPane != null)
                SwingUtilities.invokeLater(() -> {
                    int height = postListPane.getPreferredSize().height;
                    int maxHeight = getParent().getHeight() - 25;
                    postListScrollPane.setPreferredSize(new Dimension(0, Math.min(height, maxHeight)));
                    updateUI();
                });
        }

        public void setTitle(String title) {
            this.setBorder(BorderFactory.createTitledBorder(title));
        }
    }

    public static class ZonePanel extends JPanel implements Renderable<ZoneItem> {
        public final JScrollPane zoneListPane;
        public final JList<ZoneItem> zoneList;
        private Vector<ZoneItem> listData;

        public ZonePanel() {
            super();
            this.zoneList = new JList<>();
            this.listData = null;

            this.zoneList.setCellRenderer(new ZoneItemRender());
            this.zoneList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            zoneListPane = new JScrollPane(this.zoneList);
            zoneListPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            this.setLayout(new BorderLayout());
            this.add(zoneListPane, BorderLayout.CENTER);
            this.setBorder(BorderFactory.createTitledBorder("分区"));
        }

        @Override
        public void setListData(Vector<ZoneItem> listData) {
            this.listData = listData;
            this.zoneList.setListData(this.listData);
        }

        @Override
        public void add(ZoneItem item) {
            this.listData.add(item);
        }

        @Override
        public void addAll(Vector<ZoneItem> listData) {
            this.listData.addAll(listData);
        }

        @Override
        public void update(ZoneItem item) {
            int index = this.listData.indexOf(item);
            this.listData.set(index, item);
        }

        @Override
        public void clear() {
            this.zoneList.removeAll();
            this.zoneList.repaint();
            this.listData.clear();
        }

        public void register() {
            this.zoneList.addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting()) {
                    ZoneItem item = zoneList.getSelectedValue();
                    if (item != null) {
                        try {
                            MainController.getInstance().setZonePosts();
                        } catch (InterruptedException | TimeoutException ex) {
                            Main.logger().add(ex, Thread.currentThread());
                            JOptionPane.showMessageDialog(MainController.getInstance().getView().getComponent(),
                                    "加载贴子列表失败，请稍后刷新重试", "错误", JOptionPane.ERROR_MESSAGE);
                            zoneList.clearSelection();
                        }
                    }
                }
            });
        }
    }
}