package info.zpss.uniwood.desktop.client;

import info.zpss.uniwood.desktop.client.controller.MainController;
import info.zpss.uniwood.desktop.client.util.ClientLogger;
import info.zpss.uniwood.desktop.client.util.socket.ServerConnection;
import info.zpss.uniwood.desktop.common.Arguable;

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

//        try {
//            connection.disconnect();
//        } catch (IOException e) {
//            ClientLogger.add(e, Thread.currentThread());
//        }
    }
}