package info.zpss.uniwood.client.view.dialog;

import info.zpss.uniwood.client.entity.Post;
import info.zpss.uniwood.client.util.ImageLoader;
import info.zpss.uniwood.client.view.UserFavorListView;
import info.zpss.uniwood.client.view.UserPostListView;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Vector;

public class UserListDialog extends JDialog implements UserFavorListView, UserPostListView {
    private final JPanel contentPanel, btnPane;
    private final JScrollPane scrollPane;
    private final JList<String> list;
    private final JButton delBtn, openBtn;

    public UserListDialog(Component owner) {
        super((Frame) owner, false);

        this.setLocation(owner.getX() + owner.getWidth() / 2 - 135,
                owner.getY() + owner.getHeight() / 2 - 180);

        this.contentPanel = new JPanel();
        this.btnPane = new JPanel();
        this.list = new JList<>();
        this.scrollPane = new JScrollPane(list);
        this.delBtn = new JButton();
        this.openBtn = new JButton();

        this.initWindow();

        this.setTitle("Uniwood-贴子管理");
        this.setIconImage(ImageLoader.load("images/icon.png").getImage());
        this.setContentPane(contentPanel);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setPreferredSize(new Dimension(270, 360));
        this.setResizable(false);
    }

    private void initWindow() {
        delBtn.setIcon(ImageLoader.load("images/垃圾桶.png", 24, 24));
        openBtn.setIcon(ImageLoader.load("images/发布.png", 24, 24));

        btnPane.add(delBtn);
        btnPane.add(openBtn);

        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(btnPane, BorderLayout.NORTH);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
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

    @Override
    public JButton getDeleteBtn() {
        return delBtn;
    }

    @Override
    public JButton getOpenBtn() {
        return openBtn;
    }

    @Override
    public JList<String> getUserPostList() {
        return list;
    }

    @Override
    public JList<String> getUserFavorList() {
        return list;
    }

    @Override
    public void setPostList(List<Post> postList) {
        setList(postList);
    }

    @Override
    public void setFavorList(List<Post> favorList) {
        setList(favorList);
    }

    private void setList(List<Post> postList) {
        Vector<String> listData = new Vector<>();
        for (Post post : postList) {
            String content = post.getFloors().get(0).getContent();
            content = (content.length() > 20) ? content.substring(0, 17) + "..." : content;
            listData.add(String.format("#%d %s", post.getId(), content));
        }
        list.setListData(listData);
    }
}
