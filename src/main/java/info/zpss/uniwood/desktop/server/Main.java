package info.zpss.uniwood.desktop.server;

import info.zpss.uniwood.desktop.common.Arguable;
import info.zpss.uniwood.desktop.common.Log;
import info.zpss.uniwood.desktop.server.mapper.*;
import info.zpss.uniwood.desktop.server.mapper.Impl.*;
import info.zpss.uniwood.desktop.server.model.*;
import info.zpss.uniwood.desktop.server.util.Database;
import info.zpss.uniwood.desktop.server.util.Server;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static boolean debugMode;
    private static String[] arguments;
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

        setLog();

        try {
            database = new Database();
            database.init(args);
            Log.add("数据库连接成功", Log.Type.INFO, Thread.currentThread());
        } catch (SQLException e) {
            Log.add("数据库连接失败", Log.Type.ERROR, Thread.currentThread());
            Log.add(e, Thread.currentThread());
            System.exit(1);
        }

        try {
            server = new Server();
            server.init(args);
            server.start();
            Log.add("客户端连接服务启动成功", Log.Type.INFO, Thread.currentThread());
        } catch (Exception e) {
            Log.add("客户端连接服务启动失败", Log.Type.ERROR, Thread.currentThread());
            Log.add(e, Thread.currentThread());
            System.exit(1);
        }

        Log.add("Hello, UniWood!", Log.Type.INFO, Thread.currentThread());

        try {
            execute();
        } catch (RuntimeException e) {
            Log.add(e, Thread.currentThread());
        }
    }

    private static void execute() throws RuntimeException {
        // TODO：数据库连接后，服务器开始监听前运行的代码
        UserMapper userMapper = UserMapperImpl.getInstance();
        ZoneMapper zoneMapper = ZoneMapperImpl.getInstance();
        PostMapper postMapper = PostMapperImpl.getInstance();
        FloorMapper floorMapper = FloorMapperImpl.getInstance();
        User user1 = userMapper.getUser(1);
        List<Post> postByUser1 = postMapper.getPostsByUserID(user1.getId());
        List<List<Floor>> floorsInPosts = new ArrayList<>();
        for (Post post : postByUser1)
            floorsInPosts.add(floorMapper.getFloors(post.getId()));
        for (List<Floor> floors : floorsInPosts)
            for (Floor floor : floors)
                System.out.println(floor);
    }

    private static void setLog() {
        String logDir = debugMode ? "src/main/logs/desktop/server" : "logs";
        String fromArgs = Arguable.stringInArgs(arguments, "-l", "--log");
        Log.setLogFileDir((fromArgs == null) ? logDir : fromArgs);
        Log.add(String.format("UniWood-%s-%s", PLATFORM, VERSION), Log.Type.INFO, Thread.currentThread());
        if (debugMode)
            Log.add("DEBUG MODE ON!!", Log.Type.DEBUG, Thread.currentThread());
    }

}