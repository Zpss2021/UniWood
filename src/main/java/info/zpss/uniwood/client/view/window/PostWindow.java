package info.zpss.uniwood.client.view.window;

import info.zpss.uniwood.client.item.FloorItem;
import info.zpss.uniwood.client.view.PostView;
import info.zpss.uniwood.client.util.interfaces.Renderable;
import info.zpss.uniwood.client.view.render.FloorItemRender;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class PostWindow extends JFrame implements PostView {
    private final JPanel outerPane, asidePane, footerPane, centerPane;
    private final JButton shareBtn, favorBtn, replyBtn, refreshBtn, prevBtn, nextBtn;
    private final FloorPanel floorPane;

    public PostWindow() {
        super();

        this.outerPane = new JPanel(new BorderLayout());
        this.asidePane = new JPanel();
        this.footerPane = new JPanel();
        this.centerPane = new JPanel(new BorderLayout());
        this.floorPane = new FloorPanel();

        this.shareBtn = new JButton();
        this.favorBtn = new JButton();
        this.replyBtn = new JButton();
        this.refreshBtn = new JButton("刷新");
        this.prevBtn = new JButton("上一页");
        this.nextBtn = new JButton("下一页");

        shareBtn.setToolTipText("分享");
        favorBtn.setToolTipText("收藏");
        replyBtn.setToolTipText("回复");

        shareBtn.setFocusable(false);
        favorBtn.setFocusable(false);
        replyBtn.setFocusable(false);

        shareBtn.setPreferredSize(new Dimension(56, 48));
        favorBtn.setPreferredSize(new Dimension(56, 48));
        replyBtn.setPreferredSize(new Dimension(56, 48));
        refreshBtn.setPreferredSize(new Dimension(84, 32));
        prevBtn.setPreferredSize(new Dimension(96, 32));
        nextBtn.setPreferredSize(new Dimension(96, 32));

        shareBtn.setIcon(new ImageIcon(new ImageIcon("src/main/resources/分享.png")
                .getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
        favorBtn.setIcon(new ImageIcon(new ImageIcon("src/main/resources/收藏-空心.png")
                .getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
        replyBtn.setIcon(new ImageIcon(new ImageIcon("src/main/resources/发送.png")
                .getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
        refreshBtn.setIcon(new ImageIcon(new ImageIcon("src/main/resources/刷新.png")
                .getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH)));
        prevBtn.setIcon(new ImageIcon(new ImageIcon("src/main/resources/上一页.png")
                .getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH)));
        nextBtn.setIcon(new ImageIcon(new ImageIcon("src/main/resources/下一页.png")
                .getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH)));

        this.initWindow();

        this.setTitle("贴子标题_贴子分区_UniWood");
        this.setIconImage(new ImageIcon("src/main/resources/default_avatar.jpg").getImage());
        this.setContentPane(outerPane);
        this.setPreferredSize(new Dimension(840, 640));
        this.setMinimumSize(new Dimension(720, 560));
        this.setAlwaysOnTop(true);
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void initWindow() {
        asidePane.setLayout(new FlowLayout(FlowLayout.CENTER, 16, 16));
        asidePane.setPreferredSize(new Dimension(72, 640));
        asidePane.add(shareBtn);
        asidePane.add(favorBtn);
        asidePane.add(replyBtn);

        footerPane.setLayout(new FlowLayout(FlowLayout.CENTER, 8, 8));
        footerPane.setPreferredSize(new Dimension(840, 45));
        footerPane.add(prevBtn);
        footerPane.add(refreshBtn);
        footerPane.add(nextBtn);

        JPanel emptyPane = new JPanel();
        emptyPane.setPreferredSize(new Dimension(72, 640));
        outerPane.add(emptyPane, BorderLayout.WEST);
        outerPane.add(asidePane, BorderLayout.EAST);
        outerPane.add(centerPane, BorderLayout.CENTER);
        outerPane.add(footerPane, BorderLayout.SOUTH);

        JPanel blankPane = new JPanel();
        blankPane.setBackground(Color.WHITE);
        centerPane.add(blankPane, BorderLayout.CENTER);
        centerPane.add(floorPane, BorderLayout.NORTH);
        centerPane.setBorder(BorderFactory.createTitledBorder("楼层"));
    }

    @Override
    public void showWindow(Component parent) {
        SwingUtilities.invokeLater(() -> {
            this.pack();
            this.setVisible(true);
            this.validate();
            this.setLocationRelativeTo(parent);
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
    public FloorPanel getFloorPanel() {
        return floorPane;
    }

    @Override
    public JButton getShareBtn() {
        return shareBtn;
    }

    @Override
    public JButton getFavorBtn() {
        return favorBtn;
    }

    @Override
    public JButton getReplyBtn() {
        return replyBtn;
    }

    @Override
    public JButton getRefreshBtn() {
        return refreshBtn;
    }

    @Override
    public JButton getPrevBtn() {
        return prevBtn;
    }

    @Override
    public JButton getNextBtn() {
        return nextBtn;
    }

    public static class FloorPanel extends JPanel implements Renderable<FloorItem> {
        private Vector<FloorItem> listData;
        public final JPanel floorListPane;
        public final JScrollPane floorListScrollPane;

        public FloorPanel() {
            this.listData = new Vector<>();
            this.floorListPane = new JPanel();

            this.floorListPane.setLayout(new BoxLayout(floorListPane, BoxLayout.Y_AXIS));

            this.floorListScrollPane = new JScrollPane(floorListPane);
            floorListScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            floorListScrollPane.getVerticalScrollBar().setUnitIncrement(16);

            this.setBorder(BorderFactory.createLineBorder(Color.WHITE));
            this.setLayout(new BorderLayout());
            this.add(floorListScrollPane, BorderLayout.CENTER);
        }

        @Override
        public void setListData(Vector<FloorItem> floorList) {
            this.floorListPane.removeAll();
            this.listData = floorList;
            for (FloorItem item : floorList)
                this.floorListPane.add(new FloorItemRender(item));
        }

        @Override
        public void add(FloorItem item) {
            this.listData.add(item);
            this.floorListPane.add(new FloorItemRender(item));
        }

        @Override
        public void addAll(Vector<FloorItem> items) {
            this.listData.addAll(items);
            for (FloorItem item : items)
                this.floorListPane.add(new FloorItemRender(item));
        }

        @Override
        public void update(FloorItem item) {
            for (int i = 0; i < this.listData.size(); i++) {
                if (this.listData.get(i).id == item.id) {
                    this.listData.set(i, item);
                    for (Component c : this.floorListPane.getComponents())
                        if (c instanceof FloorItemRender) {
                            FloorItemRender p = (FloorItemRender) c;
                            if (p.getItem().id == item.id) {
                                p.update(item);
                                this.floorListPane.repaint();
                                break;
                            }
                        }
                    break;
                }
            }
        }

        @Override
        public void clear() {
            this.floorListPane.removeAll();
            this.floorListPane.repaint();
            this.listData.clear();
        }

        @Override
        public void repaint() {
            super.repaint();
            if (floorListScrollPane != null) {
                SwingUtilities.invokeLater(() -> {
                    int height = floorListPane.getPreferredSize().height;
                    Container parent = this.getParent();
                    int maxHeight = (parent != null) ? (parent.getHeight() - 25) : height;
                    floorListScrollPane.setPreferredSize(new Dimension(0, Math.min(height, maxHeight)));
                    updateUI();
                });
            }
        }
    }
}