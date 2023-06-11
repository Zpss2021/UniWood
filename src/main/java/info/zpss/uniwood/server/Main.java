package info.zpss.uniwood.server;

import info.zpss.uniwood.common.Arguable;
import info.zpss.uniwood.server.service.UserService;
import info.zpss.uniwood.server.util.Database;
import info.zpss.uniwood.server.util.ServerLogger;
import info.zpss.uniwood.server.util.socket.Server;

import java.sql.SQLException;

public class Main {
    private static boolean debugMode;
    private static String[] arguments;
    private static ServerLogger logger;
    private static Database database;
    private static Server server;
    public static final String PLATFORM;
    public static final String VERSION;

    static {
        debugMode = false;
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

    public static ServerLogger logger() {
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

        if (args.length == 0 || Arguable.paramInArgs(args, "-h", "--help")) {
            System.out.println("用法: java -jar UniWood_server.jar [选项]");
            System.out.println("选项:");
            System.out.println("  -h, --help\t\t\t\t显示此帮助信息");
            System.out.println("  -v, --version\t\t\t\t显示版本信息");
            System.out.println("  -D, --DEBUG\t\t\t\t开启调试模式");
            System.out.println("  -l, --log <log directory>\t\t设置存放日志的文件夹路径");
            System.out.println("  -u, --url <url>\t\t\t\t设置数据库连接地址，默认为`jdbc:mysql:///uniwood?<...args>`");
            System.out.println("  -n, --username <name>\t\t\t设置数据库用户名，默认为`zpss`");
            System.out.println("  -p, --password <password>\t\t设置数据库密码，默认为`henu`");
            System.out.println("  -P, --port <port>\t\t\t\t设置供客户端连接到的服务器端口，默认为`60196`");
            System.out.println("  -M, --max-conn <max connection>\t设置最大连接数，默认为`16`");
            System.exit(0);
        }

        arguments = new String[args.length];
        System.arraycopy(args, 0, arguments, 0, args.length);

        if (Arguable.paramInArgs(arguments, "-v", "--version")) {
            System.out.printf("UniWood %s%s%n", PLATFORM, VERSION);
            System.exit(0);
        }

        if (Arguable.paramInArgs(arguments, "-D", "--DEBUG"))
            debugMode = true;

        try {
            logger = new ServerLogger();
            logger().config(arguments);
        } catch (Exception e) {
            System.err.println("日志初始化失败");
            e.printStackTrace();
            System.exit(1);
        }

        try {
            database = new Database();
            database.config(arguments);
            logger.add("数据库连接成功", ServerLogger.Type.INFO, Thread.currentThread());
        } catch (SQLException e) {
            logger.add("数据库连接失败", ServerLogger.Type.ERROR, Thread.currentThread());
            logger.add(e, Thread.currentThread());
            System.exit(1);
        }

        try {
            server = new Server();
            server.config(arguments);
            server.start();
            logger.add("客户端连接服务启动成功", ServerLogger.Type.INFO, Thread.currentThread());
        } catch (Exception e) {
            logger.add("客户端连接服务启动失败", ServerLogger.Type.ERROR, Thread.currentThread());
            logger.add(e, Thread.currentThread());
            System.exit(1);
        }

        logger.add("Hello, UniWood!", ServerLogger.Type.INFO, Thread.currentThread());

        try {
            execute();
        } catch (RuntimeException e) {
            logger.add(e, Thread.currentThread());
        }
    }

    private static void execute() throws RuntimeException {
        // 数据库连接后，服务器开始监听前运行的代码
        UserService.getInstance().offlineAll(); // 重置所有在线用户状态为离线
    }
}