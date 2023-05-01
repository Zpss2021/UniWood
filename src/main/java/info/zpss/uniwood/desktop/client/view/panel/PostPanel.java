package info.zpss.uniwood.desktop.client.view.panel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// 贴子面板，用来显示特定分区内的贴子列表
public class PostPanel extends JScrollPane {
    private final JPanel innerPane;

    public PostPanel() {
        super();
        this.innerPane = new JPanel();
        this.innerPane.setLayout(new BoxLayout(innerPane, BoxLayout.Y_AXIS));
        this.setViewportView(innerPane);
        this.getVerticalScrollBar().setUnitIncrement(16);
        this.setBorder(BorderFactory.createTitledBorder("广场"));
    }

    public void setBorderTitle(String title) {
        this.setBorder(BorderFactory.createTitledBorder(title));
    }

    public void addPost(PostItemPanel itemPanel) {
        innerPane.add(itemPanel);
    }

    public void addPost(String title, String content) {
        this.addPost(new PostItemPanel("src/main/resources/default_avatar.jpg", title, content));
    }

    public void addPost(String avatar, String title, String content) {
        this.addPost(new PostItemPanel(avatar, title, content));
    }

    public void clear() {
        innerPane.removeAll();
    }

    public static class PostItemPanel extends JPanel {
        public PostItemPanel(String avatar, String title, String content) {
            JPanel panel = new JPanel(new BorderLayout());
            JPanel infoPane = new JPanel(new BorderLayout());
            JPanel avatarPane = new JPanel();
            JPanel favorPane = new JPanel();

            JLabel avatarLbl = new JLabel();
            JTextArea titleText = new JTextArea(title);
            JTextArea contentText = new JTextArea(content);
            JButton favorBtn = new JButton("收藏");

            int infoLen = 35;
            Icon avatarIcon = new ImageIcon(new ImageIcon(avatar).getImage().getScaledInstance(infoLen,
                    infoLen, Image.SCALE_FAST));
            avatarLbl.setIcon(avatarIcon);
            avatarLbl.setBounds(0, 0, infoLen, infoLen);
            avatarPane.setOpaque(false);
            avatarPane.setPreferredSize(new Dimension(infoLen, infoLen));
            avatarPane.add(avatarLbl);

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

            favorBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            favorBtn.setOpaque(false);
            favorBtn.setPreferredSize(new Dimension(60, 25));
            favorBtn.addActionListener(e -> System.out.println("收藏了" + titleText.getText()));
            favorPane.setOpaque(false);
            favorPane.add(favorBtn);
            favorPane.setPreferredSize(new Dimension(60, infoLen));

            infoPane.add(avatarPane, BorderLayout.WEST);
            infoPane.add(titleText, BorderLayout.CENTER);
            infoPane.add(favorPane, BorderLayout.EAST);
            infoPane.setOpaque(false);
            infoPane.setPreferredSize(new Dimension(560, 50));
            panel.setOpaque(false);
            panel.add(infoPane, BorderLayout.NORTH);
            panel.add(contentText, BorderLayout.CENTER);
            panel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));

            this.add(panel);
            this.setBackground(Color.WHITE);
        }
    }

    // 测试用入口函数
    public static void main(String[] args) {
    }

}
