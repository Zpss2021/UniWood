package info.zpss.uniwood.desktop.server;

import info.zpss.uniwood.desktop.common.Log;
import info.zpss.uniwood.desktop.server.mapper.Impl.UserMapperImpl;
import info.zpss.uniwood.desktop.server.mapper.UserMapper;
import info.zpss.uniwood.desktop.server.util.Database;

import java.sql.SQLException;

public class Main {
    private static boolean debugMode;
    private static String[] arguments;
    private static Database DATABASE;
    public static final String PLATFORM;
    public static final String VERSION;

    static {
        debugMode = true;   // TODO: 打包jar前改为false
        arguments = null;
        DATABASE = null;
        PLATFORM = "DESKTOP-SERVER";
        VERSION = "1.0.0";
    }

    public static boolean debug() {
        return Main.debugMode;
    }

    public static Database database() {
        return DATABASE;
    }

    public static boolean paramInArgs(String para) {
        for (String arg : arguments)
            if (arg.equals(para))
                return true;
        return false;
    }

    public static String stringInArgs(String paraA, String paraB) {
        for (int i = 0; i < arguments.length; i++)
            if (arguments[i].equals(paraA) || arguments[i].equals(paraB))
                if (i + 1 < arguments.length)
                    return arguments[i + 1];
        return null;
    }

    public static void main(String[] args) {
        if (debugMode)
            args = new String[]{"-D"};
        arguments = new String[args.length];
        System.arraycopy(args, 0, arguments, 0, args.length);
        if (paramInArgs("-D") || paramInArgs("--DEBUG"))
            debugMode = true;
        setLog();
        try {
            setDatabase();
            Log.add("数据库连接成功", Log.Type.INFO, Thread.currentThread());
        } catch (SQLException e) {
            Log.add(e, Thread.currentThread());
            System.exit(1);
        }
        Log.add("Hello, UniWood!", Log.Type.INFO, Thread.currentThread());
        try {
            execute();
        } catch (Exception e) {
            Log.add(e, Thread.currentThread());
        }
    }

    private static void execute() throws Exception {
        // TODO: 从这里开始写代码
        UserMapper userMapper = new UserMapperImpl();
        System.out.println("1号用户的信息："+userMapper.getUser(1));
        System.out.println("2号用户的粉丝："+userMapper.getUserFollowers(2));
        System.out.println("2号用户的关注："+userMapper.getUserFollowings(2));
        System.out.println("3号用户的分区："+userMapper.getUserZones(3));
        System.out.println("3号用户的帖子："+userMapper.getUserPosts(3));
    }

    private static void setLog() {
        String logDir = debugMode ? "src/main/logs/desktop/server" : "logs";
        String fromArgs = stringInArgs("-l", "--log");
        Log.setLogFileDir((fromArgs == null) ? logDir : fromArgs);
        Log.add(String.format("UniWood-%s-%s", PLATFORM, VERSION), Log.Type.INFO, Thread.currentThread());
        if (debugMode)
            Log.add("DEBUG MODE ON!!", Log.Type.DEBUG, Thread.currentThread());
    }

    private static void setDatabase() throws SQLException {
        String url = stringInArgs("-u", "--url");
        String username = stringInArgs("-n", "--username");
        String password = stringInArgs("-p", "--password");
        if (url == null) {
            url = "jdbc:mysql://localhost:3306/uniwood" +
                    "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC&useServerPrepStmts=true";
            Log.add("未指定数据库URL，使用默认URL", Log.Type.INFO, Thread.currentThread());
        }
        if (username == null) {
            username = "zpss";
            Log.add("未指定数据库用户名，使用默认用户名", Log.Type.INFO, Thread.currentThread());
        }
        if (password == null) {
            password = "henu";
            Log.add("未指定数据库密码，使用默认密码", Log.Type.INFO, Thread.currentThread());
        }
        DATABASE = new Database(url, username, password);
    }

}