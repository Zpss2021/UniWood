package info.zpss.uniwood.client.view.render;

import info.zpss.uniwood.client.controller.PostController;
import info.zpss.uniwood.client.item.FloorItem;
import info.zpss.uniwood.client.util.Avatar;
import info.zpss.uniwood.client.util.FontMaker;
import info.zpss.uniwood.client.util.interfaces.Render;

import javax.swing.*;
import java.awt.*;

public class FloorItemRender extends JPanel implements Render {
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

        userPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        usernameLbl.setFont(new FontMaker().bold().build());
        univLbl.setFont(new FontMaker().bold().build());
        infoLbl.setFont(new FontMaker().build());
        contentText.setFont(new FontMaker().large().build());

        usernameLbl.setForeground(new Color(80, 80, 150));
        univLbl.setForeground(new Color(80, 80, 150));
        infoLbl.setForeground(Color.DARK_GRAY);
        contentText.setForeground(Color.BLACK);

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
        this.register();
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
        Avatar avatar = new Avatar();
        avatar.fromBase64(item.avatar);
        avatarLbl.setIcon(avatar.toIcon(avatarLen));
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
        avatarLbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        usernameLbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        univLbl.setAlignmentX(Component.CENTER_ALIGNMENT);
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

    @Override
    public void repaint() {
        super.repaint();
        if (floorItemPanel != null)
            SwingUtilities.invokeLater(() -> {
                floorItemPanel.setPreferredSize(new Dimension(0, floorItemPanel.getPreferredSize().height));
                SwingUtilities.updateComponentTreeUI(floorItemPanel);
            });
    }

    @Override
    public void register() {
        PostController.getInstance().regFloorItem(this);
    }
}