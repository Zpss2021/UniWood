package info.zpss.uniwood.desktop.client;

import info.zpss.uniwood.desktop.client.util.ServerConnection;
import info.zpss.uniwood.desktop.common.Arguable;
import info.zpss.uniwood.desktop.common.Log;

import java.io.IOException;

public class Main {
    private static boolean debugMode;
    private static String[] arguments;
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

    public static ServerConnection connection() {
        return Main.connection;
    }

    public static void main(String[] args) {
        if (debugMode)
            args = new String[]{"-D", "-H" , "localhost", "-P", "60196"};

        arguments = new String[args.length];
        System.arraycopy(args, 0, arguments, 0, args.length);

        if (Arguable.paramInArgs(args, "-D", "--DEBUG"))
            debugMode = true;

        setLog();

        try {
            connection = new ServerConnection();
            connection.init(args);
            connection.connect();
        } catch (Exception e) {
            Log.add("连接服务器失败！", Log.Type.ERROR, Thread.currentThread());
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
        // TODO: 从这里开始写代码
//        MainWindow mainWindow = new MainWindow();
//        mainWindow.showWindow();
        connection.send("Hello, Server!");
        connection.send("Hello, Server!");
        try {
            connection.disconnect();
        } catch (IOException e) {
            Log.add(e, Thread.currentThread());
        }
    }

    private static void setLog() {
        String logDir = debugMode ? "src/main/logs/desktop/client" : "logs";
        String fromArgs = Arguable.stringInArgs(arguments, "-l", "--log");
        Log.setLogFileDir((fromArgs == null) ? logDir : fromArgs);
        Log.add(String.format("UniWood-%s-%s", PLATFORM, VERSION), Log.Type.INFO, Thread.currentThread());
        if (debugMode)
            Log.add("DEBUG MODE ON!!", Log.Type.DEBUG, Thread.currentThread());
    }
}