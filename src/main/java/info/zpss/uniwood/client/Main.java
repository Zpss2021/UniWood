package info.zpss.uniwood.client;

import info.zpss.uniwood.client.controller.MainController;
import info.zpss.uniwood.client.entity.Floor;
import info.zpss.uniwood.client.entity.User;
import info.zpss.uniwood.client.util.ClientLogger;
import info.zpss.uniwood.client.util.socket.ServerConnection;
import info.zpss.uniwood.client.view.window.PostWindow;
import info.zpss.uniwood.common.Arguable;

import java.util.Date;
import java.util.Vector;

public class Main {
    private static boolean debugMode;
    private static String[] arguments;
    private static ClientLogger logger;
    private static ServerConnection connection;
    public static final String PLATFORM;
    public static final String VERSION;

    static {
        debugMode = true;   // TODO: 打包jar前改为false
        arguments = null;
        PLATFORM = "DESKTOP-CLIENT";
        VERSION = "1.0.0";
    }

    public static boolean debug() {
        return Main.debugMode;
    }

    public static String[] args() {
        return Main.arguments;
    }

    public static ClientLogger logger() {
        return Main.logger;
    }

    public static ServerConnection connection() {
        return Main.connection;
    }

    public static void main(String[] args) {
        if (debugMode)
            args = new String[]{"-D", "-H", "localhost", "-P", "60196"};

        arguments = new String[args.length];
        System.arraycopy(args, 0, arguments, 0, args.length);

        if (Arguable.paramInArgs(args, "-D", "--DEBUG"))
            debugMode = true;

        try {
            logger = new ClientLogger();
            logger.config(args);
        } catch (Exception e) {
            System.err.println("初始化日志失败！");
            e.printStackTrace();
            System.exit(1);
        }

        try {
            connection = new ServerConnection();
            connection.config(args);
            connection.connect();
        } catch (Exception e) {
            logger.add("连接服务器失败！", ClientLogger.Type.ERROR, Thread.currentThread());
            logger.add(e, Thread.currentThread());
            System.exit(1);
        }

        logger.add("Hello, UniWood!", ClientLogger.Type.INFO, Thread.currentThread());

        try {
            execute();
        } catch (RuntimeException e) {
            logger.add(e, Thread.currentThread());
        }

    }

    private static void execute() throws RuntimeException {
        // TODO: 从这里开始写代码
        MainController.getInstance().register();
        MainController.getInstance().getView().showWindow(null);
        PostWindow window = new PostWindow();
        Vector<PostWindow.FloorPanel.FloorItem> floorItems = new Vector<>();
        Floor floor = new Floor(1, new User(1, "张三", "河南大学", "12"), new Date(System.currentTimeMillis()), "内容内容");
        floorItems.add(new PostWindow.FloorPanel.FloorItem(floor));
        floorItems.add(new PostWindow.FloorPanel.FloorItem(floor));
        floorItems.add(new PostWindow.FloorPanel.FloorItem(floor));
        floorItems.add(new PostWindow.FloorPanel.FloorItem(floor));
        floorItems.add(new PostWindow.FloorPanel.FloorItem(floor));
        floorItems.add(new PostWindow.FloorPanel.FloorItem(floor));
        floorItems.add(new PostWindow.FloorPanel.FloorItem(floor));
        floorItems.add(new PostWindow.FloorPanel.FloorItem(floor));
        floorItems.add(new PostWindow.FloorPanel.FloorItem(floor));
        floorItems.add(new PostWindow.FloorPanel.FloorItem(floor));
        floorItems.add(new PostWindow.FloorPanel.FloorItem(floor));
        floorItems.add(new PostWindow.FloorPanel.FloorItem(floor));
        window.getFloorPanel().setListData(floorItems);
        window.showWindow(MainController.getInstance().getView().getComponent());
//        try {
//            connection.disconnect();
//        } catch (IOException e) {
//            ClientLogger.add(e, Thread.currentThread());
//        }
    }
}