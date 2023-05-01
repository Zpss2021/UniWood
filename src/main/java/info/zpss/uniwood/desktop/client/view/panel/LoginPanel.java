package info.zpss.uniwood.desktop.client.view.panel;

import javax.swing.*;
import java.awt.*;

// 登录面板，用来显示登录界面
public class LoginPanel extends JPanel {
    public final JLabel avatarLbl;
    public final JButton loginBtn;
    public final JButton registerBtn;

    public LoginPanel() {
        super();
        avatarLbl = new JLabel();
        loginBtn = new JButton("登录");
        registerBtn = new JButton("注册");

        int avatarLen = 48;
        Icon avatarIcon = new ImageIcon(new ImageIcon("src/main/resources/default_avatar.jpg")
                .getImage().getScaledInstance(avatarLen, avatarLen, Image.SCALE_FAST));
        avatarLbl.setIcon(avatarIcon);

        avatarLbl.setSize(avatarLen, avatarLen);
        loginBtn.setSize(75, 25);
        registerBtn.setSize(75, 25);

        this.setLayout(null);

        avatarLbl.setLocation(15, 20);
        loginBtn.setLocation(75, 20);
        registerBtn.setLocation(75, 45);

        // TODO：字体
        // TODO：为按钮添加图标
        // TODO：为按钮添加事件

        this.add(avatarLbl);
        this.add(loginBtn);
        this.add(registerBtn);

        this.setSize(150, 115);
        this.setBorder(BorderFactory.createTitledBorder("登录"));
    }

    // 测试用入口函数
    public static void main(String[] args) {
    }
}
