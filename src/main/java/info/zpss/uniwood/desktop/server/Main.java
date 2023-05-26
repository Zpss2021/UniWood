package info.zpss.uniwood.desktop.server;

import info.zpss.uniwood.desktop.common.Arguable;
import info.zpss.uniwood.desktop.server.service.UserService;
import info.zpss.uniwood.desktop.server.util.Database;
import info.zpss.uniwood.desktop.server.util.Log;
import info.zpss.uniwood.desktop.server.util.socket.Server;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static boolean debugMode;
    private static String[] arguments;
    private static Log logger;
    private static Database database;
    private static Server server;
    public static final String PLATFORM;
    public static final String VERSION;

    static {
        debugMode = true;   // TODO: 打包jar前改为false
        arguments = null;
        database = null;
        server = null;
        PLATFORM = "DESKTOP-SERVER";
        VERSION = "1.0.0";
    }

    public static boolean debug() {
        return debugMode;
    }

    public static String[] args() {
        return arguments;
    }

    public static Log logger() {
        return logger;
    }

    public static Database database() {
        return database;
    }

    public static Server server() {
        return server;
    }

    public static void main(String[] args) {
        if (debugMode)
            args = new String[]{"-D", "-P", "60196", "-M", "16"};

        arguments = new String[args.length];
        System.arraycopy(args, 0, arguments, 0, args.length);

        if (Arguable.paramInArgs(args, "-D", "--DEBUG"))
            debugMode = true;

        try {
            logger = new Log();
            logger().init(args);
        } catch (Exception e) {
            System.err.println("日志初始化失败");
            e.printStackTrace();
            System.exit(1);
        }

        try {
            database = new Database();
            database.init(args);
            logger.add("数据库连接成功", Log.Type.INFO, Thread.currentThread());
        } catch (SQLException e) {
            logger.add("数据库连接失败", Log.Type.ERROR, Thread.currentThread());
            logger.add(e, Thread.currentThread());
            System.exit(1);
        }

        try {
            server = new Server();
            server.init(args);
            server.start();
            logger.add("客户端连接服务启动成功", Log.Type.INFO, Thread.currentThread());
        } catch (Exception e) {
            logger.add("客户端连接服务启动失败", Log.Type.ERROR, Thread.currentThread());
            logger.add(e, Thread.currentThread());
            System.exit(1);
        }

        logger.add("Hello, UniWood!", Log.Type.INFO, Thread.currentThread());

        try {
            execute();
        } catch (RuntimeException e) {
            logger.add(e, Thread.currentThread());
        }
    }

    private static void execute() throws RuntimeException {
        // TODO：数据库连接后，服务器开始监听前运行的代码
        UserService.getInstance().offlineAll(); // 重置所有在线用户状态为离线
    }
}