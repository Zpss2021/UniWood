package info.zpss.uniwood.client.view.dialog;

import info.zpss.uniwood.client.util.FontMaker;
import info.zpss.uniwood.client.view.ReplyView;

import javax.swing.*;
import java.awt.*;

public class ReplyDialog extends JDialog implements ReplyView {
    private final JPanel contentPanel, southPane, northPane;
    private final JScrollPane centerPane;
    private final JLabel titleLbl;
    private final JTextField titleText;
    private final JTextArea contentText;
    private final JButton replyBtn;

    public ReplyDialog(Component owner) {
        super((Frame) owner, true);

        this.setLocation(owner.getX() + owner.getWidth() / 2 - 240,
                owner.getY() + owner.getHeight() / 2 - 160);

        this.contentPanel = new JPanel();
        this.northPane = new JPanel();
        this.centerPane = new JScrollPane();
        this.southPane = new JPanel();
        this.titleLbl = new JLabel("贴子：");
        this.titleText = new JTextField();
        this.contentText = new JTextArea();
        this.replyBtn = new JButton("回复");

        replyBtn.setIcon(new ImageIcon(new ImageIcon("src/main/resources/发送.png")
                .getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH)));
        contentText.setFont(new FontMaker().large().build());

        this.initWindow();

        this.setTitle("UniWood 回复贴子");
        this.setIconImage(new ImageIcon("src/main/resources/icon.png").getImage());
        this.setContentPane(contentPanel);
        this.setPreferredSize(new Dimension(480, 320));
        this.setAlwaysOnTop(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void initWindow() {
        titleText.setEditable(false);
        titleText.setPreferredSize(new Dimension(400, 24));
        contentText.setLineWrap(true);
        contentText.setWrapStyleWord(true);
        contentText.setForeground(Color.GRAY);
        contentText.setText("按 Enter 键换行\nCtrl + Enter 回复");

        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(northPane, BorderLayout.NORTH);
        contentPanel.add(centerPane, BorderLayout.CENTER);
        contentPanel.add(southPane, BorderLayout.SOUTH);

        centerPane.setViewportView(contentText);
        centerPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        centerPane.setBorder(BorderFactory.createTitledBorder("内容"));

        southPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        southPane.add(replyBtn);

        northPane.setLayout(new FlowLayout(FlowLayout.LEFT));
        northPane.add(titleLbl);
        northPane.add(titleText);
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
    public JTextArea getReplyArea() {
        return contentText;
    }

    @Override
    public JButton getReplyButton() {
        return replyBtn;
    }

    @Override
    public JTextField getTitleText() {
        return titleText;
    }
}
