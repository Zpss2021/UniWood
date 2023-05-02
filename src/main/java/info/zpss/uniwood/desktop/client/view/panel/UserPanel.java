package info.zpss.uniwood.desktop.client.view.panel;

import javax.swing.*;
import java.awt.*;

// 登录面板，用来显示登录界面
public class UserPanel extends JPanel {
    public final JLabel avatarLbl;
    public final JButton loginBtn;
    public final JButton registerBtn;

    public UserPanel() {
        super();
        avatarLbl = new JLabel();
        loginBtn = new JButton("登录");
        registerBtn = new JButton("注册");

        int avatarLen = 56;
        Icon avatarIcon = new ImageIcon(new ImageIcon("src/main/resources/default_avatar.jpg")
                .getImage().getScaledInstance(avatarLen, avatarLen, Image.SCALE_FAST));
        avatarLbl.setIcon(avatarIcon);
        loginBtn.setIcon(new ImageIcon(new ImageIcon("src/main/resources/default_avatar.jpg")
                .getImage().getScaledInstance(16, 16, Image.SCALE_FAST)));  // TODO
        registerBtn.setIcon(new ImageIcon(new ImageIcon("src/main/resources/default_avatar.jpg")
                .getImage().getScaledInstance(16, 16, Image.SCALE_FAST)));  // TODO
        avatarLbl.setSize(avatarLen, avatarLen);
        loginBtn.setSize(85, 30);
        registerBtn.setSize(85, 30);

        this.setLayout(null);

        avatarLbl.setLocation(15, 25);
        loginBtn.setLocation(75, 25);
        registerBtn.setLocation(75, 55);

        // TODO：字体
        // TODO：为按钮添加图标
        // TODO：为按钮添加事件

        this.add(avatarLbl);
        this.add(loginBtn);
        this.add(registerBtn);

        this.setSize(150, 115);
        this.setBorder(BorderFactory.createTitledBorder("登录"));
    }
}
