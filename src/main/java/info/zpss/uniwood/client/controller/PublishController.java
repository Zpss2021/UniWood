package info.zpss.uniwood.client.controller;

import info.zpss.uniwood.client.Main;
import info.zpss.uniwood.client.builder.ZoneBuilder;
import info.zpss.uniwood.client.model.PublishModel;
import info.zpss.uniwood.client.util.interfaces.Controller;
import info.zpss.uniwood.client.view.PublishView;
import info.zpss.uniwood.client.view.dialog.PublishDialog;
import info.zpss.uniwood.common.Command;
import info.zpss.uniwood.common.MsgProto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.concurrent.TimeoutException;

public class PublishController implements Controller<PublishModel, PublishView> {
    private static final PublishController INSTANCE;
    private static final PublishModel model;
    private static final PublishView view;
    private static boolean registered;

    static {
        model = new PublishModel();
        view = new PublishDialog(MainController.getInstance().getView().getComponent());
        INSTANCE = new PublishController();
        registered = false;
    }

    private PublishController() {
    }

    public static PublishController getInstance() {
        return INSTANCE;
    }

    @Override
    public PublishModel getModel() {
        return model;
    }

    @Override
    public PublishView getView() {
        return view;
    }

    @Override
    public void register() {
        if (!registered) {
            registered = true;
            view.getPublishButton().addActionListener(e -> publish());
            view.getContentArea().addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    if (view.getContentArea().getForeground().equals(Color.GRAY)) {
                        view.getContentArea().setText("");
                        view.getContentArea().setForeground(Color.BLACK);
                    }
                }

                @Override
                public void focusLost(FocusEvent e) {
                    if (view.getContentArea().getText().isEmpty()) {
                        view.getContentArea().setText("按 Enter 键换行\nCtrl + Enter 发表");
                        view.getContentArea().setForeground(Color.GRAY);
                    }
                }
            });
            view.getContentArea().addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_ENTER) {
                        e.consume();
                        publish();
                    } else if (e.getKeyCode() == KeyEvent.VK_TAB) {
                        e.consume();
                        view.getContentArea().insert("    ", view.getContentArea().getCaretPosition());
                    } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        e.consume();
                        view.getContentArea().insert("\n", view.getContentArea().getCaretPosition());
                    }
                }
            });
        }
        model.init();
        model.setZoneId(MainController.getInstance().getZoneID());
        try {
            String zoneName = ZoneBuilder.getInstance().get(model.getZoneId()).getName();
            view.setTitle(String.format("%s-发表新贴子", zoneName));
            view.getZoneField().setText(zoneName);
        } catch (InterruptedException | TimeoutException e) {
            Main.logger().add(e, Thread.currentThread());
        }
    }

    private void publish() {
        String content = view.getContentArea().getText();
        if (content.isEmpty() || view.getContentArea().getForeground().equals(Color.GRAY)) {
            JOptionPane.showMessageDialog(view.getComponent(), "发贴内容不能为空",
                    "发表", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (content.contains("|")) {
            JOptionPane.showMessageDialog(view.getComponent(), "发贴内容含有非法字符！",
                    "发表", JOptionPane.WARNING_MESSAGE);
            return;
        }
        model.setContent(content);
        MsgProto msg = MsgProto.build(Command.PUBLISH,
                model.getZoneId().toString(),
                MainController.getInstance().getModel().getLoginUser().getId().toString(),
                model.getContent()
        );
        Main.connection().send(msg);
        JOptionPane.showMessageDialog(view.getComponent(), "发表成功！",
                "发表", JOptionPane.INFORMATION_MESSAGE);
        view.getContentArea().setText("");
        view.hideWindow();
        MainController.getInstance().refresh();
    }
}
