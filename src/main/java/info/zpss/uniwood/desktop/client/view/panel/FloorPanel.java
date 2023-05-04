package info.zpss.uniwood.desktop.client.view.panel;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class FloorPanel extends JPanel {
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
            if (this.floorList.get(i).floor == item.floor) {
                this.floorList.set(i, item);
                for (Component c : this.floorListPane.getComponents())
                    if (c instanceof FloorItemRender) {
                        FloorItemRender p = (FloorItemRender) c;
                        if (p.getItem().floor == item.floor) {
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
        public final int floor;
        public final String avatar;
        public final String username;
        public final String university;
        public final String time;
        public final String content;

        // TODO：更改构造函数，使其可以从楼层+回贴用户的实体类中构造
        public FloorItem(int floor, String avatar, String username, String university, String time, String content) {
            this.floor = floor;
            this.avatar = avatar;
            this.username = username;
            this.university = university;
            this.time = time;
            this.content = content;
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
            infoLbl.setText(String.format("%d楼 %s", item.floor, item.time));
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
