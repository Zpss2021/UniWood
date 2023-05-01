package info.zpss.uniwood.desktop.client.view.panel;

import javax.swing.*;

// 用户面板，用来显示登录用户的信息
public class UserPanel extends JPanel {
    public final JLabel avatarLbl;
    public final JLabel usernameLbl;
    public final JButton userCenterBtn;
    public final JButton logoutBtn;

    public UserPanel() {
        super();
        avatarLbl = new JLabel();
        usernameLbl = new JLabel();
        userCenterBtn = new JButton("用户中心");
        logoutBtn = new JButton("注销");

        avatarLbl.setSize(48, 48);
        usernameLbl.setSize(75, 25);
        logoutBtn.setSize(75, 25);

        this.setLayout(null);

        avatarLbl.setLocation(15, 20);
        usernameLbl.setLocation(75, 20);
        logoutBtn.setLocation(75, 45);

        // TODO：字体
        // TODO：为按钮添加图标
        // TODO：为按钮添加事件

        this.add(avatarLbl);
        this.add(usernameLbl);
        this.add(logoutBtn);

        this.setSize(150, 115);
        this.setBorder(BorderFactory.createTitledBorder("用户"));
    }

    public void setAvatar(String filename) {
        Icon avatarIcon = new ImageIcon(new ImageIcon(filename)
                .getImage().getScaledInstance(48, 48, 1));
        avatarLbl.setIcon(avatarIcon);
    }

    // 测试用入口函数
    public static void main(String[] args) {
    }
}
