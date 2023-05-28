package info.zpss.uniwood.desktop.client.view.panel;

import info.zpss.uniwood.desktop.client.entity.Post;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Vector;

// 贴子面板，用来显示特定分区内的贴子列表
public class PostPanel extends JPanel {
    private Vector<PostItem> postList;
    public final JPanel postListPane;
    public final JScrollPane postListScrollPane;

    public PostPanel() {
        super();
        this.postList = new Vector<>();
        this.postListPane = new JPanel();

        this.postListPane.setLayout(new BoxLayout(postListPane, BoxLayout.Y_AXIS));
        this.setBorder(BorderFactory.createTitledBorder("广场"));

        this.postListScrollPane = new JScrollPane(postListPane);
        postListScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        postListScrollPane.getVerticalScrollBar().setUnitIncrement(16);

        this.setLayout(new BorderLayout());
        this.add(postListScrollPane);
    }

    public void setListData(Vector<PostItem> postList) {
        this.postList.clear();
        this.postList = postList;
        this.clear();
        for (PostItem item : postList)
            this.postListPane.add(new PostItemRender(item));
    }

    public void add(PostItem item) {
        this.postList.add(item);
        this.postListPane.add(new PostItemRender(item));
    }

    public void add(Vector<PostItem> items) {
        this.postList.addAll(items);
        for (PostItem item : items)
            this.postListPane.add(new PostItemRender(item));
    }

    public void update(PostItem item) {
        for (int i = 0; i < this.postList.size(); i++) {
            if (this.postList.get(i).id == item.id) {
                this.postList.set(i, item);
                for (Component c : this.postListPane.getComponents())
                    if (c instanceof PostItemRender) {
                        PostItemRender p = (PostItemRender) c;
                        if (p.getItem().id == item.id) {
                            p.update(item);
                            this.postListPane.repaint();
                            break;
                        }
                    }
                break;
            }
        }
    }

    public void setZoneTitle(String title) {
        this.setBorder(BorderFactory.createTitledBorder(title));
    }

    public void clear() {
        postListPane.removeAll();
    }

    public static class PostItem {
        private final int id;
        private final String avatar;
        private final String title;
        private final String content;

        public PostItem(Post post) {
            String createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(post.getTime());
            String description = post.getFloors().get(0).getContent();
            this.id = post.getId();
            this.avatar = post.getAuthor().getAvatar();
            this.title = String.format("%s %s\n#%d %s",
                    post.getAuthor().getUsername(), post.getAuthor().getUniversity(), post.getId(), createTime);
            this.content = (description.length() > 100) ? (description.substring(0, 120) + "...") : description;
        }
    }

    private static class PostItemRender extends JPanel {
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
            Icon avatarIcon = new ImageIcon(new ImageIcon(item.avatar).getImage().getScaledInstance(infoLen,
                    infoLen, Image.SCALE_FAST));
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

            titleText.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    System.out.println("点击了" + titleText.getText() + "标题");
                }
            });
        }

        private void initFavorBtn() {
            favorBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            favorBtn.setOpaque(false);
            favorBtn.setPreferredSize(new Dimension(72, 35));
            favorBtn.setIcon(new ImageIcon(new ImageIcon("src/main/resources/default_avatar.jpg").getImage()
                    .getScaledInstance(12, 12, Image.SCALE_FAST)));

            favorBtn.addActionListener(e -> System.out.println("收藏了" + titleText.getText()));
        }

        private void initContentText() {
            contentText.setCursor(new Cursor(Cursor.HAND_CURSOR));
            contentText.setEditable(false);
            contentText.setOpaque(false);
            contentText.setBorder(null);
            contentText.setLineWrap(true);

            contentText.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    System.out.println("点击了" + titleText.getText() + "内容");
                }
            });
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
    }
}
