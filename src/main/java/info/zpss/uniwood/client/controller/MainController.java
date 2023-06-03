package info.zpss.uniwood.client.controller;

import info.zpss.uniwood.client.entity.Post;
import info.zpss.uniwood.client.entity.User;
import info.zpss.uniwood.client.entity.Zone;
import info.zpss.uniwood.client.model.MainModel;
import info.zpss.uniwood.client.util.interfaces.Controller;
import info.zpss.uniwood.client.view.MainView;
import info.zpss.uniwood.client.item.PostItem;
import info.zpss.uniwood.client.item.ZoneItem;
import info.zpss.uniwood.client.view.window.MainWindow;
import info.zpss.uniwood.common.Command;
import info.zpss.uniwood.client.Main;
import info.zpss.uniwood.common.MsgProto;

import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.TimeoutException;

public class MainController implements Controller<MainModel, MainView> {
    private static final MainController INSTANCE;
    private static final MainModel model;
    private static final MainView view;
    private static boolean registered;

    static {
        model = new MainModel();
        view = new MainWindow();
        INSTANCE = new MainController();
        registered = false;
    }

    private MainController() {
    }

    public static MainController getInstance() {
        return INSTANCE;
    }

    @Override
    public MainModel getModel() {
        return model;
    }

    @Override
    public MainView getView() {
        return view;
    }

    @Override
    public void register() {
        for (ActionListener l : view.getLoginOrUserCenterButton().getActionListeners())
            view.getLoginOrUserCenterButton().removeActionListener(l);
        for (ActionListener l : view.getRegisterOrLogoutButton().getActionListeners())
            view.getRegisterOrLogoutButton().removeActionListener(l);
        ActionListener loginListener = e -> userLogin(),
                registerListener = e -> userRegister(),
                userCenterListener = e -> userCenter(),
                logoutListener = e -> userLogout();
        if (model.getLoginUser() != null) {
            view.getLoginOrUserCenterButton().addActionListener(userCenterListener);
            view.getRegisterOrLogoutButton().addActionListener(logoutListener);
        } else {
            view.getRegisterOrLogoutButton().addActionListener(registerListener);
            view.getLoginOrUserCenterButton().addActionListener(loginListener);
        }
        if (!registered) {
            registered = true;
            view.getZonePanel().register();
            view.getPostPanel().register();
        }
    }

    public void userLogin() {
        LoginController.getInstance().register();
        LoginController.getInstance().getView().showWindow(view.getComponent());
    }

    private void userRegister() {
        RegisterController.getInstance().register();
        RegisterController.getInstance().getView().showWindow(view.getComponent());
    }

    private void userCenter() {
        UserCenterController.getInstance().setUser(model.getLoginUser());
        UserCenterController.getInstance().register();
        UserCenterController.getInstance().getView().showWindow(view.getComponent());
    }

    private void userLogout() {
        Main.connection().send(MsgProto.build(Command.LOGOUT));
        model.init();
        view.getUserPanel().setLogout();
        register();
    }

    private void setZones() throws InterruptedException, TimeoutException {
        List<Zone> zones = model.getZones(0);
        Vector<ZoneItem> zoneItems = new Vector<>();
        for (Zone zone : zones)
            zoneItems.add(new ZoneItem(zone));
        view.getZonePanel().setListData(zoneItems);
        view.getZonePanel().zoneList.setSelectedIndex(0);
    }

    private void setPosts() throws InterruptedException, TimeoutException {
        int zoneID = view.getZonePanel().zoneList.getSelectedValue().id;
        List<Post> posts = model.getPosts(zoneID, 0);
        Vector<PostItem> postItems = new Vector<>();
        for (Post post : posts)
            postItems.add(new PostItem(post));
        view.getPostPanel().setListData(postItems);
    }

    public void loginSuccess(User loginUser) {
        try {
            model.setLoginUser(loginUser);
            view.getUserPanel().setLogin(loginUser.getAvatar());
            setZones();
            setPosts();
            register();
        } catch (InterruptedException | TimeoutException e) {
            Main.logger().add(e, Thread.currentThread());
        }
    }

    public void updateZonePosts() {
        try {
            view.getPostPanel().setTitle(view.getZonePanel().zoneList.getSelectedValue().name);
            setPosts();
        } catch (InterruptedException | TimeoutException e) {
            Main.logger().add(e, Thread.currentThread());
        }
    }
}
