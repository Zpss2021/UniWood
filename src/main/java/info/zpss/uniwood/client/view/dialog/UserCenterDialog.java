package info.zpss.uniwood.client.view.dialog;

import info.zpss.uniwood.client.Main;
import info.zpss.uniwood.client.controller.UserCenterController;
import info.zpss.uniwood.client.model.UserCenterModel;
import info.zpss.uniwood.client.util.Avatar;
import info.zpss.uniwood.client.util.FontMaker;
import info.zpss.uniwood.client.util.ImageLoader;
import info.zpss.uniwood.client.view.UserCenterView;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.TimeoutException;

public class UserCenterDialog extends JDialog implements UserCenterView {
    private final JPanel contentPanel;

    private final JPanel usernamePane, userInfoPane, followPane, btnPane;

    private final JLabel avatarLbl, usernameLbl, userIdLbl, universityLbl;

    private final JLabel followingLbl, followerLbl;

    private final JButton favorBtn, postBtn;

    private final JButton followOrEditBtn;

    public UserCenterDialog(Component owner) {
        super((Frame) owner, false);

        this.setLocation(owner.getX() + owner.getWidth() / 2 - 135,
                owner.getY() + owner.getHeight() / 2 - 180);

        this.contentPanel = new JPanel();
        this.usernamePane = new JPanel();
        this.userInfoPane = new JPanel();
        this.followPane = new JPanel();
        this.btnPane = new JPanel();
        this.avatarLbl = new JLabel();
        this.usernameLbl = new JLabel("username");
        this.userIdLbl = new JLabel("ID：0000");
        this.universityLbl = new JLabel("大学：河南大学");
        this.followingLbl = new JLabel("<html><u>0 关注</u></html>");   // 关注数
        this.followerLbl = new JLabel("<html><u>0 粉丝</u></html>");    // 粉丝数
        this.favorBtn = new JButton();
        this.postBtn = new JButton();
        this.followOrEditBtn = new JButton("编辑");

        avatarLbl.setIcon(ImageLoader.load("images/default_avatar.jpg", 96, 96));
        favorBtn.setIcon(ImageLoader.load("images/收藏-实心.png", 24, 24));
        postBtn.setIcon(ImageLoader.load("images/发送.png", 24, 24));
        followOrEditBtn.setIcon(ImageLoader.load("images/自定义.png", 24, 24));

        this.initWindow();

        this.setTitle("用户中心");
        this.setIconImage(ImageLoader.load("images/icon.png").getImage());
        this.setContentPane(contentPanel);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setPreferredSize(new Dimension(270, 360));
        this.setResizable(false);
    }

    private void initWindow() {
        favorBtn.setToolTipText("收藏");
        postBtn.setToolTipText("发帖");

        favorBtn.setFocusable(false);
        postBtn.setFocusable(false);
        followOrEditBtn.setFocusable(false);

        contentPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        avatarLbl.setPreferredSize(new Dimension(96, 96));
        usernameLbl.setFont(new FontMaker().large().large().large().bold().build());
        userIdLbl.setFont(new FontMaker().small().build());
        universityLbl.setFont(new FontMaker().small().build());

        followerLbl.setFont(new FontMaker().large().build());
        followingLbl.setFont(new FontMaker().large().build());
        followerLbl.setForeground(Color.BLUE);
        followingLbl.setForeground(Color.BLUE);

        favorBtn.setPreferredSize(new Dimension(48, 36));
        postBtn.setPreferredSize(new Dimension(48, 36));

        followOrEditBtn.setPreferredSize(new Dimension(120, 48));

        usernamePane.setPreferredSize(new Dimension(270, 36));
        usernamePane.add(usernameLbl);

        userInfoPane.setPreferredSize(new Dimension(270, 28));
        userInfoPane.add(userIdLbl);
        userInfoPane.add(universityLbl);

        followPane.setLayout(new FlowLayout(FlowLayout.CENTER, 16, 0));
        followPane.setPreferredSize(new Dimension(270, 32));
        followPane.add(followingLbl);
        followPane.add(followerLbl);

        btnPane.setLayout(new FlowLayout(FlowLayout.CENTER, 16, 0));
        btnPane.setPreferredSize(new Dimension(270, 40));
        btnPane.add(favorBtn);
        btnPane.add(postBtn);

        contentPanel.add(avatarLbl);
        contentPanel.add(usernamePane);
        contentPanel.add(userInfoPane);
        contentPanel.add(followPane);
        contentPanel.add(btnPane);
        contentPanel.add(followOrEditBtn);

        followerLbl.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        followingLbl.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    @Override
    public void showWindow(Component parent) {
        SwingUtilities.invokeLater(() -> {
            UserCenterController.getInstance().register();
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
    public JLabel getFollowingLabel() {
        return followingLbl;
    }

    @Override
    public JLabel getFollowerLabel() {
        return followerLbl;
    }

    @Override
    public JButton getFavorButton() {
        return favorBtn;
    }

    @Override
    public JButton getPostButton() {
        return postBtn;
    }

    @Override
    public JButton getFollowOrEditButton() {
        return followOrEditBtn;
    }

    @Override
    public void setUser(UserCenterModel model) {
        SwingUtilities.invokeLater(() -> {
            try {
                Avatar avatar = new Avatar();
                avatar.fromBase64(model.getAvatar());
                avatarLbl.setIcon(avatar.toIcon(96));
                usernameLbl.setText(model.getUsername());
                userIdLbl.setText("ID：" + model.getId());
                universityLbl.setText("大学：" + model.getUniversity());
                followingLbl.setText("<html><u>" + model.getFollowings(0).size() + " 关注</u></html>");
                followerLbl.setText("<html><u>" + model.getFollowers(0).size() + " 粉丝</u></html>");
                setTitle(model.getUsername());
            } catch (InterruptedException | TimeoutException e) {
                Main.logger().add("用户信息获取失败", Thread.currentThread());
                Main.logger().add(e, Thread.currentThread());
            }
        });
    }
}
