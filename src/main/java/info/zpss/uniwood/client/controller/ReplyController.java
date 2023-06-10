package info.zpss.uniwood.client.controller;

import info.zpss.uniwood.client.Main;
import info.zpss.uniwood.client.builder.FloorBuilder;
import info.zpss.uniwood.client.model.ReplyModel;
import info.zpss.uniwood.client.util.interfaces.Controller;
import info.zpss.uniwood.client.view.ReplyView;
import info.zpss.uniwood.client.view.dialog.ReplyDialog;
import info.zpss.uniwood.common.Command;
import info.zpss.uniwood.common.MsgProto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.concurrent.TimeoutException;

public class ReplyController implements Controller<ReplyModel, ReplyView> {
    private static final ReplyController INSTANCE;
    private static final ReplyModel model;
    private static final ReplyView view;
    private static boolean registered;

    static {
        model = new ReplyModel();
        view = new ReplyDialog(PostController.getInstance().getView().getComponent());
        INSTANCE = new ReplyController();
        registered = false;
    }

    private ReplyController() {
    }

    public static ReplyController getInstance() {
        return INSTANCE;
    }

    @Override
    public ReplyModel getModel() {
        return model;
    }

    @Override
    public ReplyView getView() {
        return view;
    }

    @Override
    public void register() {
        if (!registered) {
            registered = true;
            view.getReplyButton().addActionListener(e -> reply());
            view.getReplyArea().addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    if (view.getReplyArea().getForeground().equals(Color.GRAY)) {
                        view.getReplyArea().setText("");
                        view.getReplyArea().setForeground(Color.BLACK);
                    }
                }

                @Override
                public void focusLost(FocusEvent e) {
                    if (view.getReplyArea().getText().isEmpty()) {
                        view.getReplyArea().setText("按 Enter 键换行\nCtrl + Enter 回复");
                        view.getReplyArea().setForeground(Color.GRAY);
                    }
                }
            });
            view.getReplyArea().addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_ENTER) {
                        e.consume();
                        reply();
                    } else if (e.getKeyCode() == KeyEvent.VK_TAB) {
                        e.consume();
                        view.getReplyArea().insert("    ", view.getReplyArea().getCaretPosition());
                    } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        e.consume();
                        view.getReplyArea().insert("\n", view.getReplyArea().getCaretPosition());
                    }
                }
            });
        }
        model.init();
        model.setPostId(PostController.getInstance().getModel().getPost().getId());
        try {
            String postTitle = FloorBuilder.getInstance().get(1, model.getPostId()).getContent();
            postTitle = postTitle.length() > 36 ? postTitle.substring(0, 33) + "..." : postTitle;
            view.setTitle(String.format("%s-回复", postTitle));
            view.getTitleText().setText(String.format("%s", postTitle));
        } catch (InterruptedException | TimeoutException e) {
            Main.logger().add(e, Thread.currentThread());
        }
    }

    private void reply() {
        String content = view.getReplyArea().getText();
        if (content.isEmpty() || view.getReplyArea().getForeground().equals(Color.GRAY)) {
            JOptionPane.showMessageDialog(view.getComponent(), "回贴内容不能为空",
                    "回复", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (content.contains("|")) {
            JOptionPane.showMessageDialog(view.getComponent(), "回贴内容含有非法字符！",
                    "回复", JOptionPane.WARNING_MESSAGE);
            return;
        }
        model.setContent(content);
        MsgProto msg = MsgProto.build(Command.REPLY,
                MainController.getInstance().getModel().getLoginUser().getId().toString(),
                model.getPostId().toString(),
                model.getContent()
        );
        Main.connection().send(msg);
        JOptionPane.showMessageDialog(view.getComponent(), "回贴成功！",
                "回复", JOptionPane.INFORMATION_MESSAGE);
        view.getReplyArea().setText("");
        view.hideWindow();
        PostController.getInstance().refreshPost();
    }
}
