package info.zpss.uniwood.client.view.render;

import info.zpss.uniwood.client.controller.MainController;
import info.zpss.uniwood.client.item.PostItem;
import info.zpss.uniwood.client.util.Avatar;
import info.zpss.uniwood.client.util.FontMaker;
import info.zpss.uniwood.client.util.interfaces.Render;

import javax.swing.*;
import java.awt.*;

public class PostItemRender extends JPanel implements Render {
    private PostItem item;
    public final JPanel postItemPanel, headerPanel;
    public final JPanel avatarPane, favorPane, titlePane;
    public final JLabel avatarLbl;
    public final JTextArea userText, infoText, contentText;
    public final JButton favorBtn;
    private boolean favor;

    public PostItemRender(PostItem item) {
        super();
        this.item = item;
        postItemPanel = new JPanel(new BorderLayout());
        headerPanel = new JPanel(new BorderLayout(10, 0));
        avatarPane = new JPanel();
        favorPane = new JPanel();
        titlePane = new JPanel();

        avatarLbl = new JLabel();
        userText = new JTextArea();
        infoText = new JTextArea();
        contentText = new JTextArea();
        favorBtn = new JButton();

        favorBtn.setToolTipText("收藏");

        userText.setFont(new FontMaker().bold().build());
        infoText.setFont(new FontMaker().small().build());
        contentText.setFont(new FontMaker().large().build());


        userText.setForeground(new Color(80, 80, 150));
        infoText.setForeground(Color.DARK_GRAY);
        contentText.setForeground(Color.DARK_GRAY);

        initTitleText();
        initFavorBtn();
        initContentText();

        updateAvatar(item);
        updateTitleText(item);
        updateContentText(item);

        initPanel();

        this.add(postItemPanel, BorderLayout.CENTER);
        this.setBackground(Color.WHITE);
        this.register();
    }

    public PostItem getItem() {
        return item;
    }

    public void update(PostItem item) {
        this.item = item;
        updateAvatar(item);
        updateTitleText(item);
        updateContentText(item);
    }

    public void updateAvatar(PostItem item) {
        int infoLen = 35;
        Avatar avatar = new Avatar();
        avatar.fromBase64(item.avatar);
        Icon avatarIcon = avatar.toIcon(infoLen);
        avatarLbl.setIcon(avatarIcon);
        avatarLbl.setBounds(0, 0, infoLen, infoLen);
        avatarPane.setOpaque(false);
        avatarPane.setPreferredSize(new Dimension(infoLen, infoLen));
        avatarPane.add(avatarLbl);
    }

    public void updateTitleText(PostItem item) {
        userText.setText(item.user);
        infoText.setText(item.info);
    }

    public void updateContentText(PostItem item) {
        contentText.setText(item.content);
    }

    private void initTitleText() {
        userText.setCursor(new Cursor(Cursor.HAND_CURSOR));
        userText.setEditable(false);
        userText.setOpaque(false);
        userText.setBorder(null);
        userText.setLineWrap(true);
        infoText.setEditable(false);
        infoText.setOpaque(false);
        infoText.setBorder(null);
        infoText.setLineWrap(true);
    }

    private void initFavorBtn() {
        favorBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        favorBtn.setOpaque(false);
        favorBtn.setBorder(null);
        favorBtn.setContentAreaFilled(false);
        favorBtn.setFocusPainted(false);
        favorBtn.setPreferredSize(new Dimension(35, 35));
        favorBtn.setIcon(new ImageIcon(new ImageIcon("收藏-空心.png").getImage()
                .getScaledInstance(28, 28, Image.SCALE_SMOOTH)));
    }

    private void initContentText() {
        contentText.setCursor(new Cursor(Cursor.HAND_CURSOR));
        contentText.setEditable(false);
        contentText.setOpaque(false);
        contentText.setLineWrap(true);
        contentText.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
    }

    private void initPanel() {
        favorPane.setOpaque(false);
        favorPane.add(favorBtn);
        favorPane.setPreferredSize(new Dimension(72, 35));

        titlePane.setOpaque(false);
        titlePane.setLayout(new BoxLayout(titlePane, BoxLayout.Y_AXIS));
        titlePane.add(userText);
        titlePane.add(infoText);

        headerPanel.add(avatarPane, BorderLayout.WEST);
        headerPanel.add(titlePane, BorderLayout.CENTER);
        headerPanel.add(favorPane, BorderLayout.EAST);
        headerPanel.setOpaque(false);
        headerPanel.setPreferredSize(new Dimension(640, 50));

        postItemPanel.setOpaque(false);
        postItemPanel.add(headerPanel, BorderLayout.NORTH);
        postItemPanel.add(contentText, BorderLayout.CENTER);
        postItemPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
    }

    @Override
    public void repaint() {
        super.repaint();
        if (postItemPanel != null)
            SwingUtilities.invokeLater(() -> {
                postItemPanel.setPreferredSize(new Dimension(640, postItemPanel.getPreferredSize().height));
                SwingUtilities.updateComponentTreeUI(postItemPanel);
            });
    }

    @Override
    public void register() {
        MainController.getInstance().regPostItem(this);
        favor = false;
    }

    // 返回操作后收藏状态
    public boolean toggleFavor() {
        if (favor) {
            favorBtn.setIcon(new ImageIcon(new ImageIcon("src/main/resources/收藏-空心.png").getImage()
                    .getScaledInstance(28, 28, Image.SCALE_SMOOTH)));
            favor = false;
        } else {
            favorBtn.setIcon(new ImageIcon(new ImageIcon("src/main/resources/收藏-实心.png").getImage()
                    .getScaledInstance(28, 28, Image.SCALE_SMOOTH)));
            favor = true;
        }
        return favor;
    }
}