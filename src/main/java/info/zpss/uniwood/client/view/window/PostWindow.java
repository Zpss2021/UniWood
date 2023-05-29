package info.zpss.uniwood.client.view.window;

import info.zpss.uniwood.client.entity.Floor;
import info.zpss.uniwood.client.view.PostView;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Vector;

public class PostWindow extends JFrame implements PostView {
    private final JPanel outerPane, asidePane, footerPane;
    private final JButton shareBtn, favorBtn, replyBtn, refreshBtn, prevBtn, nextBtn;
    private final FloorPanel floorPane;

    public PostWindow() {
        super();

        this.outerPane = new JPanel(new BorderLayout());
        this.asidePane = new JPanel();
        this.footerPane = new JPanel();
        this.floorPane = new FloorPanel();

        this.shareBtn = new JButton();
        this.favorBtn = new JButton();
        this.replyBtn = new JButton();
        this.refreshBtn = new JButton("刷新");
        this.prevBtn = new JButton("上一页");
        this.nextBtn = new JButton("下一页");

        shareBtn.setToolTipText("分享");
        favorBtn.setToolTipText("收藏");
        replyBtn.setToolTipText("回复");

        shareBtn.setFocusable(false);
        favorBtn.setFocusable(false);
        replyBtn.setFocusable(false);

        shareBtn.setPreferredSize(new Dimension(56, 48));
        favorBtn.setPreferredSize(new Dimension(56, 48));
        replyBtn.setPreferredSize(new Dimension(56, 48));
        refreshBtn.setPreferredSize(new Dimension(84, 32));
        prevBtn.setPreferredSize(new Dimension(96, 32));
        nextBtn.setPreferredSize(new Dimension(96, 32));

        shareBtn.setIcon(new ImageIcon(new ImageIcon("src/main/resources/分享.png")
                .getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
        favorBtn.setIcon(new ImageIcon(new ImageIcon("src/main/resources/收藏-空心.png")
                .getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
        replyBtn.setIcon(new ImageIcon(new ImageIcon("src/main/resources/发送.png")
                .getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
        refreshBtn.setIcon(new ImageIcon(new ImageIcon("src/main/resources/刷新.png")
                .getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH)));
        prevBtn.setIcon(new ImageIcon(new ImageIcon("src/main/resources/上一页.png")
                .getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH)));
        nextBtn.setIcon(new ImageIcon(new ImageIcon("src/main/resources/下一页.png")
                .getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH)));

        // TODO：字体
        // TODO：为按钮添加事件

        this.initWindow();

        this.setTitle("贴子标题_贴子分区_UniWood");  // TODO：从贴子对象获取标题
        this.setIconImage(new ImageIcon("src/main/resources/default_avatar.jpg").getImage());
        this.setContentPane(outerPane);
        this.setPreferredSize(new Dimension(840, 640));
        this.setMinimumSize(new Dimension(720, 560));
        this.setAlwaysOnTop(true);
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void initWindow() {
        asidePane.setLayout(new FlowLayout(FlowLayout.CENTER, 16, 16));
        asidePane.setPreferredSize(new Dimension(72, 640));
        asidePane.add(shareBtn);
        asidePane.add(favorBtn);
        asidePane.add(replyBtn);

        footerPane.setLayout(new FlowLayout(FlowLayout.CENTER, 8, 8));
        footerPane.setPreferredSize(new Dimension(840, 45));
        footerPane.add(prevBtn);
        footerPane.add(refreshBtn);
        footerPane.add(nextBtn);

        floorPane.setPreferredSize(new Dimension(640, 480));

        JPanel emptyPane = new JPanel();
        emptyPane.setPreferredSize(new Dimension(72, 640));
        outerPane.add(emptyPane, BorderLayout.WEST);
        outerPane.add(asidePane, BorderLayout.EAST);
        outerPane.add(floorPane, BorderLayout.CENTER);
        outerPane.add(footerPane, BorderLayout.SOUTH);
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
        SwingUtilities.invokeLater(() -> {
            this.setVisible(false);
            this.dispose();
        });
    }

    @Override
    public Component getComponent() {
        return this;
    }

    @Override
    public FloorPanel getFloorPanel() {
        return floorPane;
    }

    public static class FloorPanel extends JPanel {
        private Vector<FloorItem> floorList;
        public final JPanel floorListPane;
        public final JScrollPane floorListScrollPane;

        public FloorPanel() {
            this.floorList = new Vector<>();
            this.floorListPane = new JPanel();

            this.floorListPane.setLayout(new BoxLayout(floorListPane, BoxLayout.Y_AXIS));
            this.setBorder(BorderFactory.createTitledBorder("楼层"));

            this.floorListScrollPane = new JScrollPane(floorListPane);
            floorListScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            floorListScrollPane.getVerticalScrollBar().setUnitIncrement(16);

            this.setLayout(new BorderLayout());
            this.add(floorListScrollPane, BorderLayout.CENTER);
        }

        // TODO：添加楼层数据接口

        public void setListData(Vector<FloorItem> floorList) {
            this.floorList.clear();
            this.floorList = floorList;
            this.clear();
            for (FloorItem item : floorList)
                this.floorListPane.add(new FloorItemRender(item));
        }

        public void add(FloorItem item) {
            this.floorList.add(item);
            this.floorListPane.add(new FloorItemRender(item));
        }

        public void add(Vector<FloorItem> items) {
            this.floorList.addAll(items);
            for (FloorItem item : items)
                this.floorListPane.add(new FloorItemRender(item));
        }

        public void update(FloorItem item) {
            for (int i = 0; i < this.floorList.size(); i++) {
                if (this.floorList.get(i).id == item.id) {
                    this.floorList.set(i, item);
                    for (Component c : this.floorListPane.getComponents())
                        if (c instanceof FloorItemRender) {
                            FloorItemRender p = (FloorItemRender) c;
                            if (p.getItem().id == item.id) {
                                p.update(item);
                                this.floorListPane.repaint();
                                break;
                            }
                        }
                    break;
                }
            }
        }

        public void setFloorNumberTitle(String title) {
            this.setBorder(BorderFactory.createTitledBorder(title));
        }

        public void clear() {
            this.floorListPane.removeAll();
        }

        public static class FloorItem {
            private final int id;
            private final String avatar;
            private final String username;
            private final String university;
            private final String time;
            private final String content;

            public FloorItem(Floor floor) {
                this.id = floor.getId();
                this.avatar = floor.getAuthor().getAvatar();
                this.username = floor.getAuthor().getUsername();
                this.university = floor.getAuthor().getUniversity();
                this.time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(floor.getTime());
                this.content = floor.getContent();
            }
        }

        private static class FloorItemRender extends JPanel {
            private FloorItem item;
            public final JPanel floorItemPanel, centerPanel, userPanel, contentPanel, infoPanel;
            public final JLabel avatarLbl, usernameLbl, univLbl, infoLbl;
            public final JTextArea contentText;

            public FloorItemRender(FloorItem item) {
                super();
                this.item = item;
                this.floorItemPanel = new JPanel(new BorderLayout());
                this.centerPanel = new JPanel(new BorderLayout());
                this.userPanel = new JPanel();
                this.contentPanel = new JPanel(new BorderLayout());
                this.infoPanel = new JPanel();
                this.avatarLbl = new JLabel();
                this.usernameLbl = new JLabel();
                this.univLbl = new JLabel();
                this.infoLbl = new JLabel();
                this.contentText = new JTextArea();

                updateUser(item);
                updateContent(item);
                updateInfo(item);

                initUserPanel();
                initContentPanel();
                initInfoPanel();
                initFloorItemPanel();

                this.setLayout(new BorderLayout());
                this.add(floorItemPanel, BorderLayout.CENTER);
                this.setBackground(Color.WHITE);
            }

            public FloorItem getItem() {
                return this.item;
            }

            public void update(FloorItem item) {
                this.item = item;
                updateUser(item);
                updateContent(item);
                updateInfo(item);
            }

            public void updateUser(FloorItem item) {
                int avatarLen = 32;
                Icon avatarIcon = new ImageIcon(new ImageIcon(item.avatar).getImage().getScaledInstance(avatarLen,
                        avatarLen, Image.SCALE_FAST));
                avatarLbl.setIcon(avatarIcon);
                avatarLbl.setBounds(0, 0, avatarLen, avatarLen);

                usernameLbl.setText(item.username);
                univLbl.setText(item.university);
            }

            public void updateContent(FloorItem item) {
                contentText.setText(item.content);
            }

            public void updateInfo(FloorItem item) {
                infoLbl.setText(String.format("%d楼 %s", item.id, item.time));
            }

            private void initUserPanel() {
                userPanel.setOpaque(false);
                userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.Y_AXIS));
                userPanel.add(avatarLbl);
                userPanel.add(usernameLbl);
                userPanel.add(univLbl);
                // TODO：添加点击事件

            }

            private void initContentPanel() {
                contentText.setEditable(false);
                contentText.setBorder(null);
                contentText.setLineWrap(true);

                contentPanel.setOpaque(false);
                contentPanel.add(contentText, BorderLayout.CENTER);
            }

            private void initInfoPanel() {
                infoPanel.setOpaque(false);
                infoPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
                infoPanel.add(infoLbl);
            }

            private void initFloorItemPanel() {
                centerPanel.setOpaque(false);
                centerPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                centerPanel.add(contentPanel, BorderLayout.CENTER);
                infoPanel.setPreferredSize(new Dimension(560, 24));

                floorItemPanel.setOpaque(false);
                floorItemPanel.add(userPanel, BorderLayout.WEST);
                floorItemPanel.add(centerPanel, BorderLayout.CENTER);
                floorItemPanel.add(infoPanel, BorderLayout.SOUTH);
                floorItemPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
            }
        }
    }
}
