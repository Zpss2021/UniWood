package info.zpss.uniwood.client.view.dialog;

import info.zpss.uniwood.client.util.FontBuilder;
import info.zpss.uniwood.client.view.UserCenterView;

import javax.swing.*;
import java.awt.*;

public class UserCenterDialog extends JDialog implements UserCenterView {
    private final JPanel contentPanel;

    private final JPanel usernamePane, userInfoPane, followPane, btnPane;

    private final JLabel avatarLbl;

    private final JLabel usernameLbl;

    private final JLabel userIdLbl, universityLbl;

    private final JLabel followingLbl, followerLbl;

    private final JButton favorBtn, postBtn;

    private final JButton followOrEditBtn;

    public UserCenterDialog(Component owner) {
        super((Frame) owner, true);

        this.setLocation(owner.getX() + owner.getWidth() / 2 - 135,
                owner.getY() + owner.getHeight() / 2 - 180);

        this.contentPanel = new JPanel();
        this.usernamePane = new JPanel();
        this.userInfoPane = new JPanel();
        this.followPane = new JPanel();
        this.btnPane = new JPanel();
        this.avatarLbl = new JLabel();
        this.usernameLbl = new JLabel("username");  // TODO
        this.userIdLbl = new JLabel("ID：0000"); // TODO
        this.universityLbl = new JLabel("大学：河南大学"); // TODO
        this.followingLbl = new JLabel("<html><u>0 关注</u></html>");   // 关注数 TODO
        this.followerLbl = new JLabel("<html><u>0 粉丝</u></html>");    // 粉丝数 TODO
        this.favorBtn = new JButton();
        this.postBtn = new JButton();
        this.followOrEditBtn = new JButton("编辑");

        avatarLbl.setIcon(new ImageIcon(new ImageIcon("src/main/resources/default_avatar.jpg")
                .getImage().getScaledInstance(96, 96, Image.SCALE_SMOOTH)));
        favorBtn.setIcon(new ImageIcon(new ImageIcon("src/main/resources/收藏-实心.png")
                .getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
        postBtn.setIcon(new ImageIcon(new ImageIcon("src/main/resources/发送.png")
                .getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
        followOrEditBtn.setIcon(new ImageIcon(new ImageIcon("src/main/resources/自定义.png")
                .getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));


        this.initWindow();

        this.setTitle("用户中心-UniWood");  // TODO：从服务器获取用户名
        this.setIconImage(new ImageIcon("src/main/resources/default_avatar.jpg").getImage());
        this.setContentPane(contentPanel);
        this.setPreferredSize(new Dimension(270, 360));
        this.setAlwaysOnTop(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void initWindow() {
        contentPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        avatarLbl.setPreferredSize(new Dimension(96, 96));
        usernameLbl.setFont(new FontBuilder().large().large().bold().build());
        userIdLbl.setFont(new FontBuilder().build());
        universityLbl.setFont(new FontBuilder().build());

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
}
