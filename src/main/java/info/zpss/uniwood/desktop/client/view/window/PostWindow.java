package info.zpss.uniwood.desktop.client.view.window;

import info.zpss.uniwood.desktop.client.view.PostView;
import info.zpss.uniwood.desktop.client.view.panel.FloorPanel;

import javax.swing.*;
import java.awt.*;

// 楼层窗口，用来显示特定贴子内的楼层列表
public class PostWindow extends JFrame implements PostView {
    private Component parent;
    private final JPanel outerPane, asidePane, footerPane;
    private final JButton shareBtn, favorBtn, replyBtn, refreshBtn, prevBtn, nextBtn;
    private final FloorPanel floorPane;

    public PostWindow() {
        super();

        this.parent = null;
        this.outerPane = new JPanel(new BorderLayout());
        this.asidePane = new JPanel();
        this.footerPane = new JPanel();
        this.floorPane = new FloorPanel();

        this.shareBtn = new JButton("分享");
        this.favorBtn = new JButton("收藏");
        this.replyBtn = new JButton("回复");
        this.refreshBtn = new JButton("刷新");
        this.prevBtn = new JButton("上一页");
        this.nextBtn = new JButton("下一页");

        shareBtn.setIcon(new ImageIcon(new ImageIcon("src/main/resources/default_avatar.jpg")
                .getImage().getScaledInstance(16, 16, Image.SCALE_FAST)));  // TODO
        favorBtn.setIcon(new ImageIcon(new ImageIcon("src/main/resources/default_avatar.jpg")
                .getImage().getScaledInstance(16, 16, Image.SCALE_FAST)));  // TODO
        replyBtn.setIcon(new ImageIcon(new ImageIcon("src/main/resources/default_avatar.jpg")
                .getImage().getScaledInstance(16, 16, Image.SCALE_FAST)));  // TODO
        refreshBtn.setIcon(new ImageIcon(new ImageIcon("src/main/resources/default_avatar.jpg")
                .getImage().getScaledInstance(16, 16, Image.SCALE_FAST)));  // TODO
        prevBtn.setIcon(new ImageIcon(new ImageIcon("src/main/resources/default_avatar.jpg")
                .getImage().getScaledInstance(16, 16, Image.SCALE_FAST)));  // TODO
        nextBtn.setIcon(new ImageIcon(new ImageIcon("src/main/resources/default_avatar.jpg")
                .getImage().getScaledInstance(16, 16, Image.SCALE_FAST)));  // TODO

        // TODO：字体
        // TODO：为按钮添加图标
        // TODO：为按钮添加事件

        this.initWindow();

        this.setTitle("贴子标题_贴子分区_UniWood");  // TODO：从贴子对象获取标题
        this.setIconImage(new ImageIcon("src/main/resources/default_avatar.jpg").getImage());
        this.setContentPane(outerPane);
        this.setParent(parent);
        this.setPreferredSize(new Dimension(840, 640));
        this.setMinimumSize(new Dimension(720, 560));
        this.setAlwaysOnTop(true);
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public void initWindow() {
        asidePane.setLayout(new FlowLayout(FlowLayout.CENTER, 8, 8));
        asidePane.setPreferredSize(new Dimension(72, 640));
        asidePane.add(shareBtn);
        asidePane.add(favorBtn);
        asidePane.add(replyBtn);

        footerPane.setLayout(new FlowLayout(FlowLayout.CENTER, 8, 8));
        footerPane.setPreferredSize(new Dimension(840, 40));
        footerPane.add(prevBtn);
        footerPane.add(refreshBtn);
        footerPane.add(nextBtn);

        floorPane.setPreferredSize(new Dimension(640, 480));

        JPanel emptyPane = new JPanel();
        emptyPane.setPreferredSize(new Dimension(72, 640));
        outerPane.add(emptyPane, BorderLayout.WEST);
        outerPane.add(asidePane, BorderLayout.EAST);
        outerPane.add(floorPane, BorderLayout.CENTER);
        outerPane.add(footerPane, BorderLayout.SOUTH);
    }

    @Override
    public void showWindow() {
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
    public void setParent(Component parent) {
        this.parent = parent;
        setLocationRelativeTo(parent);
    }

    @Override
    public Component getComponent() {
        return this;
    }

    @Override
    public FloorPanel getFloorPanel() {
        return floorPane;
    }
}
