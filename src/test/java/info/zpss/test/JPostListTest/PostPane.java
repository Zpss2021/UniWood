package info.zpss.test.JPostListTest;

import javax.swing.*;

public class PostPane extends JScrollPane {
    private final JPanel innerPane;

    public PostPane() {
        super();
        this.innerPane = new JPanel();
        this.innerPane.setLayout(new BoxLayout(innerPane, BoxLayout.Y_AXIS));
        this.setViewportView(innerPane);
        this.getVerticalScrollBar().setUnitIncrement(16);
    }

    public void addPost(PostItemPanel itemPanel) {
        innerPane.add(itemPanel);
    }

    public void addPost(String title, String content) {
        this.addPost(new PostItemPanel("src/test/resources/1.png", title, content));
    }
}
