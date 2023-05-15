package info.zpss.uniwood.desktop.client;

import info.zpss.uniwood.desktop.client.util.ServerConnection;
import info.zpss.uniwood.desktop.common.Log;

public class Main {
    private static boolean debugMode;
    private static String[] arguments;
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
        arguments = new String[args.length];
        System.arraycopy(args, 0, arguments, 0, args.length);
        if (paramInArgs("-D") || paramInArgs("--DEBUG"))
            debugMode = true;
        setLog();
        Log.add("Hello, UniWood!", Log.Type.INFO, Thread.currentThread());
        try {
            execute();
        } catch (RuntimeException e) {
            Log.add(e, Thread.currentThread());
        }
    }

    private static void execute() throws RuntimeException {
        // TODO: 从这里开始写代码
//        MainWindow mainWindow = new MainWindow();
//        mainWindow.showWindow();
        try {
            ServerConnection serverConnection = new ServerConnection("localhost", 60196);
            serverConnection.connect();
            Log.add("连接服务器成功！", Log.Type.INFO, Thread.currentThread());
            System.out.println("连接服务器成功！");
            serverConnection.send("Hello, Server!");
            serverConnection.send("Hello, Server!");
            serverConnection.send("Hello, Server!");
            serverConnection.send("Hello, Server!");
            serverConnection.send("Hello, Server!");
            serverConnection.send("Hello, Server!");
            serverConnection.send("Hello, Server!");
        } catch (Exception e) {
            Log.add("连接服务器失败！", Log.Type.ERROR, Thread.currentThread());
            Log.add(e, Thread.currentThread());
        }
    }

    private static void setLog() {
        String logDir = debugMode ? "src/main/logs/desktop/client" : "logs";
        String fromArgs = stringInArgs("-l", "--log");
        Log.setLogFileDir((fromArgs == null) ? logDir : fromArgs);
        Log.add(String.format("UniWood-%s-%s", PLATFORM, VERSION), Log.Type.INFO, Thread.currentThread());
        if (debugMode)
            Log.add("DEBUG MODE ON!!", Log.Type.DEBUG, Thread.currentThread());
    }
}