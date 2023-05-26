package info.zpss.uniwood.desktop.client.view.window;

import info.zpss.uniwood.desktop.client.view.LoginView;

import javax.swing.*;
import java.awt.*;

// 登录窗口
public class LoginWindow extends JFrame implements LoginView {
    private Component parent;
    private final JPanel contentPanel, bgPane, formPane, footerPane;
    private final JLabel bgLbl, usernameLbl, passwordLbl, registerLbl;
    private final JTextField usernameText, passwordText;
    private final JCheckBox autoLoginCheck, rememberMeCheck;
    private final JButton loginBtn;

    public LoginWindow() {
        super();

        this.parent = null;
        this.contentPanel = new JPanel();
        this.bgPane = new JPanel();
        this.formPane = new JPanel();
        this.footerPane = new JPanel();

        this.bgLbl = new JLabel();
        this.usernameLbl = new JLabel("用户名：");
        this.passwordLbl = new JLabel("密码：");
        this.registerLbl = new JLabel("<html><u>注册账号</u></html>");

        this.usernameText = new JTextField();
        this.passwordText = new JPasswordField();

        this.autoLoginCheck = new JCheckBox("自动登录");
        this.rememberMeCheck = new JCheckBox("记住密码");

        this.loginBtn = new JButton("登录");

        bgLbl.setIcon(new ImageIcon(new ImageIcon("src/main/resources/default_avatar.jpg")
                .getImage().getScaledInstance(480, 105, Image.SCALE_FAST)));  // TODO

        // TODO: 头图
        // TODO：字体

        this.initWindow();

        this.setTitle("UniWood 登录");
        this.setIconImage(new ImageIcon("src/main/resources/default_avatar.jpg").getImage());
        this.setContentPane(contentPanel);
        this.setPreferredSize(new Dimension(480, 320));
        this.setAlwaysOnTop(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // TODO
    }

    private void initWindow() {
        this.contentPanel.setLayout(new BorderLayout());
        this.contentPanel.add(bgPane, BorderLayout.NORTH);
        this.contentPanel.add(formPane, BorderLayout.CENTER);
        this.contentPanel.add(footerPane, BorderLayout.SOUTH);

        this.bgPane.setLayout(new FlowLayout(FlowLayout.CENTER));
        this.bgPane.add(bgLbl);

        this.formPane.setLayout(null);
        this.formPane.add(usernameLbl);
        this.formPane.add(usernameText);
        this.formPane.add(passwordLbl);
        this.formPane.add(passwordText);
        this.formPane.add(autoLoginCheck);
        this.formPane.add(rememberMeCheck);
        this.formPane.add(loginBtn);
        usernameLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        passwordLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        usernameLbl.setBounds(80,10, 60, 30);
        usernameText.setBounds(140, 10, 200, 30);
        passwordLbl.setBounds(80, 50, 60, 30);
        passwordText.setBounds(140, 50, 200, 30);
        autoLoginCheck.setBounds(140, 85, 80, 20);
        rememberMeCheck.setBounds(260, 85, 80, 20);
        loginBtn.setBounds(140, 105, 200, 30);

        registerLbl.setCursor(new Cursor(Cursor.HAND_CURSOR));
        registerLbl.setForeground(Color.BLUE);

        this.footerPane.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.footerPane.add(registerLbl);
    }

    @Override
    public void showWindow(Component parent) {
        SwingUtilities.invokeLater(() -> {
            this.parent = parent;
            this.setLocationRelativeTo(parent);
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
    public JTextField getUsernameText() {
        return usernameText;
    }

    @Override
    public JTextField getPasswordText() {
        return passwordText;
    }

    @Override
    public JButton getLoginButton() {
        return loginBtn;
    }
}
