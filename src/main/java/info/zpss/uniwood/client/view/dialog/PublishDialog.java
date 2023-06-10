package info.zpss.uniwood.client.view.dialog;

import info.zpss.uniwood.client.util.FontMaker;
import info.zpss.uniwood.client.view.PublishView;

import javax.swing.*;
import java.awt.*;

public class PublishDialog extends JDialog implements PublishView {
    private final JPanel contentPanel, northPane, southPane;
    private final JScrollPane centerPane;
    private final JLabel zoneLbl;
    private final JTextField zoneText;
    private final JTextArea contentText;
    private final JButton publishBtn;

    public PublishDialog(Component owner) {
        super((Frame) owner, true);

        this.setLocation(owner.getX() + owner.getWidth() / 2 - 240,
                owner.getY() + owner.getHeight() / 2 - 160);

        this.contentPanel = new JPanel();
        this.northPane = new JPanel();
        this.centerPane = new JScrollPane();
        this.southPane = new JPanel();
        this.zoneLbl = new JLabel("发布到：");
        this.zoneText = new JTextField();
        this.contentText = new JTextArea();
        this.publishBtn = new JButton("发表");

        publishBtn.setIcon(new ImageIcon(new ImageIcon("src/main/resources/发布.png")
                .getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH)));
        zoneText.setFont(new FontMaker().bold().build());
        contentText.setFont(new FontMaker().large().build());

        this.initWindow();

        this.setTitle("UniWood 发表新贴子");
        this.setIconImage(new ImageIcon("src/main/resources/default_avatar.jpg").getImage());
        this.setContentPane(contentPanel);
        this.setPreferredSize(new Dimension(480, 320));
        this.setAlwaysOnTop(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void initWindow() {
        zoneText.setEditable(false);
        zoneText.setPreferredSize(new Dimension(400, 24));
        contentText.setLineWrap(true);
        contentText.setWrapStyleWord(true);

        this.contentPanel.setLayout(new BorderLayout());
        this.contentPanel.add(northPane, BorderLayout.NORTH);
        this.contentPanel.add(centerPane, BorderLayout.CENTER);
        this.contentPanel.add(southPane, BorderLayout.SOUTH);

        this.northPane.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.northPane.add(zoneLbl);
        this.northPane.add(zoneText);

        this.centerPane.setBorder(BorderFactory.createTitledBorder("内容"));
        this.centerPane.add(contentText);

        centerPane.setViewportView(contentText);
        centerPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        this.southPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        this.southPane.add(publishBtn);
    }

    @Override
    public void showWindow(Component parent) {
        SwingUtilities.invokeLater(() -> {
            this.pack();
            this.setVisible(true);
            this.validate();
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
    public JTextField getZoneField() {
        return zoneText;
    }

    @Override
    public JTextArea getContentArea() {
        return contentText;
    }

    @Override
    public JButton getPublishButton() {
        return publishBtn;
    }
}
