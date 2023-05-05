package info.zpss.uniwood.desktop.client.view.panel;

import info.zpss.uniwood.desktop.client.model.Zone;

import javax.swing.*;
import java.awt.*;

// 分区面板，用来显示登录用户的分区列表
public class ZonePanel extends JPanel {
    public final JList<ZoneItem> zoneList;
    public final JScrollPane zoneListPane;

    public ZonePanel() {
        super();
        this.zoneList = new JList<>();

        // TODO：字体
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
            Icon zoneIcon = new ImageIcon(new ImageIcon(value.icon)
                    .getImage().getScaledInstance(len, len, Image.SCALE_FAST));
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
