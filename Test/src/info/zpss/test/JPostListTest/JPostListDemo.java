package info.zpss.test.JPostListTest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class JPostListDemo extends JFrame {
    public JPostListDemo() {
        super("帖子列表");
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
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
        this.setLayout(new BorderLayout());
        PostPane postPane = new PostPane();
        postPane.addPost("Alice 河南大学\n#12345 12小时前 12-34 12:34", "帖子1的内容帖子1的内容帖子1的内容帖子1的内容帖子1的内容帖子1的内容帖子1的内容帖子1的内容");
        postPane.addPost("Bob 河南大学\n#12345 12小时前 12-34 12:34", "帖子2的内容帖子2的内容帖子2的内容\n帖子2的内容帖子2的内容帖子2的内容帖子2的内容帖子2的内容");
        postPane.addPost("Carol 河南大学\n#12345 12小时前 12-34 12:34", "帖子3的内容");
        postPane.addPost("Denny 河南大学\n#12345 12小时前 12-34 12:34", "帖子4的内容帖子4的内容帖子4的内容");
        postPane.addPost("Elsa 河南大学\n#12345 12小时前 12-34 12:34", "帖子5的内容帖子5\n的内容帖子5的内容\n帖子5的内容\n帖子5的内容");
        postPane.addPost("Flank 河南大学\n#12345 12小时前 12-34 12:34", "帖子6的内容\n帖子6的内容");
        postPane.addPost("Groin 河南大学\n#12345 12小时前 12-34 12:34", "帖子7的内容帖子7的内容帖子7的内容帖子7的内容帖子7的内容帖子7的内容" +
                "帖子7的内容帖子7的内容帖子7的内容帖子7的内容帖子7的内容帖子7的内容帖子7的内容帖子7的内容");
        postPane.addPost("Henny 河南大学\n#12345 12小时前 12-34 12:34", "帖子8的内容");
        postPane.addPost("Instance 河南大学\n#12345 12小时前 12-34 12:34", "帖子9的内容");
        postPane.addPost("Jack 河南大学\n#12345 12小时前 12-34 12:34", "帖子10的内容");
        this.add(postPane, BorderLayout.CENTER);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new JPostListDemo();
    }
}

class PostItemPanel extends JPanel {
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
        infoPane.setPreferredSize(new Dimension(320, 50));
        panel.setOpaque(false);
        panel.add(infoPane, BorderLayout.NORTH);
        panel.add(contentText, BorderLayout.CENTER);
        panel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));

        this.add(panel);
        this.setBackground(Color.white);
    }
}

class PostPane extends JScrollPane {
    private final JPanel innerPane;

    public PostPane() {
        super();
        this.innerPane = new JPanel();
        this.innerPane.setLayout(new BoxLayout(innerPane, BoxLayout.Y_AXIS));
        this.setViewportView(innerPane);
        this.getVerticalScrollBar().setUnitIncrement(16);
    }

    public void addPost(PostItemPanel itemPanel) {
        innerPane.add(itemPanel);
    }

    public void addPost(String title, String content) {
        this.addPost(new PostItemPanel("Test/res/1.png", title, content));
    }
}