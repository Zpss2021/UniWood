package info.zpss.uniwood.desktop.client.view.window;

import info.zpss.uniwood.desktop.client.util.Avatar;
import info.zpss.uniwood.desktop.client.view.RegisterView;

import javax.swing.*;
import java.awt.*;

public class RegisterWindow extends JFrame implements RegisterView {
    private Component parent;
    private final JPanel contentPanel, formPanel, footerPanel, avatarPane, textPane;
    private final JLabel usernameLbl, passwordLbl, cfmPwdLbl, universityLbl, avatarLbl;
    private final JTextField usernameText, passwordText, pwdConfirmText;
    private final JComboBox<String> universityCombo;
    private final JButton setAvatarBtn, registerBtn;

    public RegisterWindow() {
        super();

        this.parent = null;
        this.contentPanel = new JPanel();
        this.formPanel = new JPanel();
        this.footerPanel = new JPanel();
        this.avatarPane = new JPanel();
        this.textPane = new JPanel();

        this.usernameLbl = new JLabel("用户名：");
        this.passwordLbl = new JLabel("密码：");
        this.cfmPwdLbl = new JLabel("确认密码：");
        this.universityLbl = new JLabel("大学：");
        this.avatarLbl = new JLabel();

        this.usernameText = new JTextField();
        this.passwordText = new JPasswordField();
        this.pwdConfirmText = new JPasswordField();

        this.universityCombo = new JComboBox<>();
        this.setAvatarBtn = new JButton("上传头像");
        this.registerBtn = new JButton("立即注册");

        avatarLbl.setIcon(new ImageIcon(new ImageIcon("src/main/resources/default_avatar.jpg")
                .getImage().getScaledInstance(96, 96, Image.SCALE_SMOOTH)));  // TODO

        // TODO：字体

        this.initWindow();

        this.setTitle("UniWood 注册");
        this.setIconImage(new ImageIcon("src/main/resources/default_avatar.jpg").getImage());
        this.setContentPane(contentPanel);
        this.setPreferredSize(new Dimension(480, 320));
        this.setAlwaysOnTop(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // TODO
    }

    private void initWindow() {
        this.contentPanel.setLayout(new BorderLayout());
        this.contentPanel.add(formPanel, BorderLayout.CENTER);
        this.contentPanel.add(footerPanel, BorderLayout.SOUTH);

        this.formPanel.setLayout(new BorderLayout());
        this.formPanel.add(avatarPane, BorderLayout.WEST);
        this.formPanel.add(textPane, BorderLayout.CENTER);

        this.avatarPane.setPreferredSize(new Dimension(150, 240));
        this.avatarPane.setLayout(null);
        this.avatarPane.add(avatarLbl);
        this.avatarPane.add(setAvatarBtn);

        avatarLbl.setBounds(30, 55, 96, 96);
        setAvatarBtn.setBounds(18, 165, 120, 30);

        this.textPane.setLayout(null);
        this.textPane.add(usernameLbl);
        this.textPane.add(passwordLbl);
        this.textPane.add(cfmPwdLbl);
        this.textPane.add(universityLbl);
        this.textPane.add(usernameText);
        this.textPane.add(passwordText);
        this.textPane.add(pwdConfirmText);
        this.textPane.add(universityCombo);

        usernameLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        passwordLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        cfmPwdLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        universityLbl.setHorizontalAlignment(SwingConstants.RIGHT);

        usernameLbl.setBounds(0, 50, 80, 30);
        passwordLbl.setBounds(0, 90, 80, 30);
        cfmPwdLbl.setBounds(0, 130, 80, 30);
        universityLbl.setBounds(0, 170, 80, 30);
        usernameText.setBounds(80, 50, 200, 30);
        passwordText.setBounds(80, 90, 200, 30);
        pwdConfirmText.setBounds(80, 130, 200, 30);
        universityCombo.setBounds(80, 170, 200, 30);

        this.footerPanel.setPreferredSize(new Dimension(480, 50));
        this.footerPanel.setLayout(null);
        this.footerPanel.add(registerBtn);

        registerBtn.setBounds(80, 0, 320, 35);
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

    public static void main(String[] args) {
        new RegisterWindow().showWindow(null);
    }

    @Override
    public void setAvatar(Avatar avatar) {
        avatarLbl.setIcon(avatar.toIcon(96));
    }

    @Override
    public JButton getRegisterBtn() {
        return registerBtn;
    }

    @Override
    public JButton getSetAvatarBtn() {
        return setAvatarBtn;
    }

    @Override
    public JTextField getUsernameText() {
        return usernameText;
    }

    @Override
    public JPasswordField getPasswordText() {
        return (JPasswordField) passwordText;
    }

    @Override
    public JPasswordField getPwdConfirmText() {
        return (JPasswordField) pwdConfirmText;
    }

    @Override
    public JComboBox<String> getUniversityCombo() {
        return universityCombo;
    }
}