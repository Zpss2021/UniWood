package info.zpss.uniwood.client.view.render;

import info.zpss.uniwood.client.util.Avatar;
import info.zpss.uniwood.client.item.ZoneItem;
import info.zpss.uniwood.client.util.FontMaker;
import info.zpss.uniwood.client.util.interfaces.Render;

import javax.swing.*;
import java.awt.*;

public class ZoneItemRender extends JPanel implements ListCellRenderer<ZoneItem>, Render {
    private final JLabel iconLabel;
    private final JLabel nameLabel;

    public ZoneItemRender() {
        super();
        this.iconLabel = new JLabel();
        this.nameLabel = new JLabel();
        nameLabel.setFont(new FontMaker().bold().large().large().large().build());
        nameLabel.setPreferredSize(new Dimension(200, 50));
        this.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        this.add(iconLabel);
        this.add(nameLabel);
    }

    @Override
    public Component getListCellRendererComponent
            (JList<? extends ZoneItem> list, ZoneItem value, int index, boolean isSelected, boolean cellHasFocus) {
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

    @Override
    public void register() {
    }
}