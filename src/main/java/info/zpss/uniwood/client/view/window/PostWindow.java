package info.zpss.uniwood.client.view.window;

import info.zpss.uniwood.client.item.FloorItem;
import info.zpss.uniwood.client.util.ImageLoader;
import info.zpss.uniwood.client.util.interfaces.Renderable;
import info.zpss.uniwood.client.view.PostView;
import info.zpss.uniwood.client.view.render.FloorItemRender;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

public class PostWindow extends JFrame implements PostView {
    private final JPanel outerPane, asidePane, footerPane, centerPane;
    private final JButton shareBtn, favorBtn, replyBtn, refreshBtn, prevBtn, nextBtn;
    private final FloorPanel floorPane;
    private boolean favor;

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

        shareBtn.setIcon(ImageLoader.load("images/分享.png", 24, 24));
        favorBtn.setIcon(ImageLoader.load("images/收藏-空心.png", 24, 24));
        replyBtn.setIcon(ImageLoader.load("images/发送.png", 24, 24));
        refreshBtn.setIcon(ImageLoader.load("images/刷新.png", 16, 16));
        prevBtn.setIcon(ImageLoader.load("images/上一页.png", 16, 16));
        nextBtn.setIcon(ImageLoader.load("images/下一页.png", 16, 16));

        this.initWindow();

        this.favor = false;
        this.setTitle("贴子标题_贴子分区_UniWood");
        this.setIconImage(ImageLoader.load("images/icon.png").getImage());
        this.setContentPane(outerPane);
        this.setPreferredSize(new Dimension(720, 560));
        this.setMinimumSize(new Dimension(720, 560));
        this.setResizable(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                favor = true;
                toggleFavor();
            }
        });
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

    @Override
    public void rollToTop() {
        SwingUtilities.invokeLater(() -> floorPane.floorListScrollPane.getVerticalScrollBar().setValue(0));
    }

    @Override
    public boolean toggleFavor() {
        if (favor) {
            favorBtn.setIcon(ImageLoader.load("images/收藏-空心.png", 24, 24));
            favor = false;
        } else {
            favorBtn.setIcon(ImageLoader.load("images/收藏-实心.png", 24, 24));
            favor = true;
        }
        return favor;
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