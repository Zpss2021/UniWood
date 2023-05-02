package info.zpss.uniwood.desktop.client.view.panel;

import javax.swing.*;
import java.awt.*;

// ctrlPane.EAST
public class BtnPanel extends JPanel {
    public final JButton newPostBtn;
    public final JButton refreshBtn;
    public final JButton prevPageBtn;
    public final JButton nextPageBtn;

    public BtnPanel() {
        super();

        this.newPostBtn = new JButton("发帖");
        this.refreshBtn = new JButton("刷新");
        this.prevPageBtn = new JButton("上一页");
        this.nextPageBtn = new JButton("下一页");

        newPostBtn.setIcon(new ImageIcon(new ImageIcon("src/main/resources/default_avatar.jpg")
                .getImage().getScaledInstance(16, 16, Image.SCALE_FAST)));  // TODO
        refreshBtn.setIcon(new ImageIcon(new ImageIcon("src/main/resources/default_avatar.jpg")
                .getImage().getScaledInstance(16, 16, Image.SCALE_FAST)));  // TODO
        prevPageBtn.setIcon(new ImageIcon(new ImageIcon("src/main/resources/default_avatar.jpg")
                .getImage().getScaledInstance(16, 16, Image.SCALE_FAST)));  // TODO
        nextPageBtn.setIcon(new ImageIcon(new ImageIcon("src/main/resources/default_avatar.jpg")
                .getImage().getScaledInstance(16, 16, Image.SCALE_FAST)));  // TODO

        // TODO：字体
        // TODO：为按钮添加图标
        // TODO：为按钮添加事件

        this.setLayout(new GridLayout(2, 2, 5, 5));

        this.add(newPostBtn);
        this.add(prevPageBtn);
        this.add(refreshBtn);
        this.add(nextPageBtn);

        this.setSize(300, 100);
        this.setBorder(BorderFactory.createTitledBorder("操作"));
    }
}
