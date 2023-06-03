package info.zpss.uniwood.client.view.render;

import info.zpss.uniwood.client.util.Avatar;
import info.zpss.uniwood.client.item.PostItem;
import info.zpss.uniwood.client.util.interfaces.Render;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PostItemRender extends JPanel implements Render {
    private PostItem item;
    private final JPanel postItemPanel, headerPanel;
    private final JPanel avatarPane, favorPane;
    private final JLabel avatarLbl;
    private final JTextArea titleText, contentText;
    private final JButton favorBtn;

    public PostItemRender(PostItem item) {
        super();
        this.item = item;
        postItemPanel = new JPanel(new BorderLayout());
        headerPanel = new JPanel(new BorderLayout(10, 0));
        avatarPane = new JPanel();
        favorPane = new JPanel();

        avatarLbl = new JLabel();
        titleText = new JTextArea();
        contentText = new JTextArea();
        favorBtn = new JButton("收藏");

        initTitleText();
        initFavorBtn();
        initContentText();

        updateAvatar(item);
        updateTitleText(item);
        updateContentText(item);

        initPanel();

        this.add(postItemPanel, BorderLayout.CENTER);
        this.setBackground(Color.WHITE);
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
        titleText.setText(item.title);
    }

    public void updateContentText(PostItem item) {
        contentText.setText(item.content);
    }

    private void initTitleText() {
        titleText.setCursor(new Cursor(Cursor.HAND_CURSOR));
        titleText.setEditable(false);
        titleText.setOpaque(false);
        titleText.setBorder(null);
        titleText.setLineWrap(true);
    }

    private void initFavorBtn() {
        favorBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        favorBtn.setOpaque(false);
        favorBtn.setPreferredSize(new Dimension(72, 35));
        favorBtn.setIcon(new ImageIcon(new ImageIcon("src/main/resources/收藏-空心.png").getImage()
                .getScaledInstance(12, 12, Image.SCALE_SMOOTH)));
    }

    private void initContentText() {
        contentText.setCursor(new Cursor(Cursor.HAND_CURSOR));
        contentText.setEditable(false);
        contentText.setOpaque(false);
        contentText.setBorder(null);
        contentText.setLineWrap(true);
    }

    private void initPanel() {
        favorPane.setOpaque(false);
        favorPane.add(favorBtn);
        favorPane.setPreferredSize(new Dimension(72, 35));

        headerPanel.add(avatarPane, BorderLayout.WEST);
        headerPanel.add(titleText, BorderLayout.CENTER);
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
            SwingUtilities.invokeLater(() -> SwingUtilities.updateComponentTreeUI(postItemPanel));
    }

    @Override
    public void register() {
        titleText.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("点击了" + titleText.getText() + "标题");
            }
        });
        favorBtn.addActionListener(e -> System.out.println("收藏了" + titleText.getText()));
        contentText.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("点击了" + titleText.getText() + "内容");
            }
        });
    }
}