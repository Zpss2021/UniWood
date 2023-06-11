package info.zpss.uniwood.client.view.dialog;

import info.zpss.uniwood.client.entity.Post;
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
        super((Frame) owner, true);

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
        this.setIconImage(new ImageIcon("src/main/resources/icon.png").getImage());
        this.setContentPane(contentPanel);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setPreferredSize(new Dimension(270, 360));
        this.setAlwaysOnTop(true);
        this.setResizable(false);
    }

    private void initWindow() {
        delBtn.setIcon(new ImageIcon(new ImageIcon("src/main/resources/垃圾桶.png")
                .getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
        openBtn.setIcon(new ImageIcon(new ImageIcon("src/main/resources/发布.png")
                .getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));

        btnPane.add(delBtn);
        btnPane.add(openBtn);

        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(btnPane, BorderLayout.NORTH);
        contentPanel.add(scrollPane, BorderLayout.CENTER);
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
        SwingUtilities.invokeLater(() -> list.setListData(listData));
    }
}
