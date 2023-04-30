package info.zpss.test.JPostListTest;

import javax.swing.*;
import java.awt.*;

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

