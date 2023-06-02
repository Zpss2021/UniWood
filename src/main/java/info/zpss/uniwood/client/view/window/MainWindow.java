package info.zpss.uniwood.client.view.window;

import info.zpss.uniwood.client.Main;
import info.zpss.uniwood.client.entity.Post;
import info.zpss.uniwood.client.entity.Zone;
import info.zpss.uniwood.client.util.Avatar;
import info.zpss.uniwood.client.util.ClientLogger;
import info.zpss.uniwood.client.util.FontMaker;
import info.zpss.uniwood.client.view.MainView;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.Vector;

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
    public ZonePanel getZonePanel() {
        return zonePane;
    }

    @Override
    public PostPanel getPostPanel() {
        return postPane;
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

            // TODO：字体
            // TODO：为按钮添加事件

            this.setLayout(new GridLayout(2, 2, 5, 5));

            this.add(newPostBtn);
            this.add(prevPageBtn);
            this.add(refreshBtn);
            this.add(nextPageBtn);

            this.setSize(300, 100);
            this.setBorder(BorderFactory.createTitledBorder("操作"));
        }
    }

    public static class PostPanel extends JPanel {
        private Vector<PostItem> postList;
        public final JPanel postListPane;
        public final JScrollPane postListScrollPane;

        public PostPanel() {
            super();
            this.postList = new Vector<>();
            this.postListPane = new JPanel();

            this.postListPane.setLayout(new BoxLayout(postListPane, BoxLayout.Y_AXIS));
            this.setBorder(BorderFactory.createTitledBorder("广场"));

            this.postListScrollPane = new JScrollPane(postListPane);
            postListScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            postListScrollPane.getVerticalScrollBar().setUnitIncrement(16);

            this.setLayout(new BorderLayout());
            this.add(postListScrollPane);
        }

        public void setListData(Vector<PostItem> postList) {
            this.postList.clear();
            this.postList = postList;
            this.clear();
            for (PostItem item : postList)
                this.postListPane.add(new PostItemRender(item));
        }

        public void add(PostItem item) {
            this.postList.add(item);
            this.postListPane.add(new PostItemRender(item));
        }

        public void add(Vector<PostItem> items) {
            this.postList.addAll(items);
            for (PostItem item : items)
                this.postListPane.add(new PostItemRender(item));
        }

        public void update(PostItem item) {
            for (int i = 0; i < this.postList.size(); i++) {
                if (this.postList.get(i).id == item.id) {
                    this.postList.set(i, item);
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

        public void setZoneTitle(String title) {
            this.setBorder(BorderFactory.createTitledBorder(title));
        }

        public void clear() {
            postListPane.removeAll();
        }

        public static class PostItem {
            private final int id;
            private final String avatar;
            private final String title;
            private final String content;

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

        private static class PostItemRender extends JPanel {
            private PostItem item;
            private final JPanel postItemPanel, headerPanel;
            private final JPanel avatarPane, favorPane;
            private final JLabel avatarLbl;
            private final JTextArea titleText, contentText;
            private final JButton favorBtn;

            public PostItemRender(PostItem item) {
                super();
                this.item = item;
                postItemPanel = new JPanel(new BorderLayout());
                headerPanel = new JPanel(new BorderLayout(10, 0));
                avatarPane = new JPanel();
                favorPane = new JPanel();

                avatarLbl = new JLabel();
                titleText = new JTextArea();
                contentText = new JTextArea();
                favorBtn = new JButton("收藏");

                initTitleText();
                initFavorBtn();
                initContentText();

                updateAvatar(item);
                updateTitleText(item);
                updateContentText(item);

                initPanel();

                this.add(postItemPanel, BorderLayout.CENTER);
                this.setBackground(Color.WHITE);
            }

            public PostItem getItem() {
                return item;
            }

            public void update(PostItem item) {
                this.item = item;
                updateAvatar(item);
                updateTitleText(item);
                updateContentText(item);
            }

            public void updateAvatar(PostItem item) {
                int infoLen = 35;
                Avatar avatar = new Avatar();
                avatar.fromBase64(item.avatar);
                Icon avatarIcon = avatar.toIcon(infoLen);
                avatarLbl.setIcon(avatarIcon);
                avatarLbl.setBounds(0, 0, infoLen, infoLen);
                avatarPane.setOpaque(false);
                avatarPane.setPreferredSize(new Dimension(infoLen, infoLen));
                avatarPane.add(avatarLbl);
            }

            public void updateTitleText(PostItem item) {
                titleText.setText(item.title);
            }

            public void updateContentText(PostItem item) {
                contentText.setText(item.content);
            }

            private void initTitleText() {
                titleText.setCursor(new Cursor(Cursor.HAND_CURSOR));
                titleText.setEditable(false);
                titleText.setOpaque(false);
                titleText.setBorder(null);
                titleText.setLineWrap(true);

                titleText.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        System.out.println("点击了" + titleText.getText() + "标题");
                    }
                });
            }

            private void initFavorBtn() {
                favorBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
                favorBtn.setOpaque(false);
                favorBtn.setPreferredSize(new Dimension(72, 35));
                favorBtn.setIcon(new ImageIcon(new ImageIcon("src/main/resources/收藏-空心.png").getImage()
                        .getScaledInstance(12, 12, Image.SCALE_SMOOTH)));

                favorBtn.addActionListener(e -> System.out.println("收藏了" + titleText.getText()));
            }

            private void initContentText() {
                contentText.setCursor(new Cursor(Cursor.HAND_CURSOR));
                contentText.setEditable(false);
                contentText.setOpaque(false);
                contentText.setBorder(null);
                contentText.setLineWrap(true);

                contentText.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        System.out.println("点击了" + titleText.getText() + "内容");
                    }
                });
            }

            private void initPanel() {
                favorPane.setOpaque(false);
                favorPane.add(favorBtn);
                favorPane.setPreferredSize(new Dimension(72, 35));

                headerPanel.add(avatarPane, BorderLayout.WEST);
                headerPanel.add(titleText, BorderLayout.CENTER);
                headerPanel.add(favorPane, BorderLayout.EAST);
                headerPanel.setOpaque(false);
                headerPanel.setPreferredSize(new Dimension(640, 50));

                postItemPanel.setOpaque(false);
                postItemPanel.add(headerPanel, BorderLayout.NORTH);
                postItemPanel.add(contentText, BorderLayout.CENTER);
                postItemPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
            }
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


            // TODO：字体
            // TODO：为按钮和搜索框添加事件

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

    public static class ZonePanel extends JPanel {
        public final JList<ZoneItem> zoneList;
        public final JScrollPane zoneListPane;

        public ZonePanel() {
            super();
            this.zoneList = new JList<>();

            // TODO：为按钮添加事件

            this.zoneList.setCellRenderer(new ZoneItemRender());
            this.zoneList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            zoneListPane = new JScrollPane(this.zoneList);
            zoneListPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            this.setLayout(new BorderLayout());
            this.add(zoneListPane, BorderLayout.CENTER);
            this.setBorder(BorderFactory.createTitledBorder("分区"));
        }

        public void setZoneList(ZoneItem[] items) {
            this.zoneList.setListData(items);
        }

        public static class ZoneItem {
            private final String icon;
            private final String name;

            public ZoneItem(Zone zone) {
                this.icon = zone.getIcon();
                this.name = zone.getName();
            }

            @Override
            public String toString() {
                return this.name;
            }
        }

        private static class ZoneItemRender extends JPanel implements ListCellRenderer<ZoneItem> {
            private final JLabel iconLabel;
            private final JLabel nameLabel;

            public ZoneItemRender() {
                super();
                this.iconLabel = new JLabel();
                this.nameLabel = new JLabel();
                this.setLayout(new FlowLayout(FlowLayout.LEFT));
                this.add(iconLabel);
                this.add(nameLabel);
            }

            @Override
            public Component getListCellRendererComponent(JList<? extends ZoneItem> list, ZoneItem value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                int len = 48;
                Avatar icon = new Avatar();
                icon.fromBase64(value.icon);
                Icon zoneIcon = icon.toIcon(len);
                this.iconLabel.setIcon(zoneIcon);
                this.iconLabel.setSize(len, len);
                this.nameLabel.setText(value.name);
                if (isSelected) {
                    this.setBackground(list.getSelectionBackground());
                    this.setForeground(list.getSelectionForeground());
                } else {
                    this.setBackground(list.getBackground());
                    this.setForeground(list.getForeground());
                }
                return this;
            }
        }
    }
}
