package info.zpss.uniwood.desktop.client;

import info.zpss.uniwood.desktop.client.util.ServerConnection;
import info.zpss.uniwood.desktop.common.Arguable;
import info.zpss.uniwood.desktop.common.Log;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

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

    public static void main(String[] args) {
        if (debugMode)
            args = new String[]{"-D"};

        arguments = new String[args.length];
        System.arraycopy(args, 0, arguments, 0, args.length);

        if (Arguable.paramInArgs(args, "-D", "--DEBUG"))
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
            SocketAddress address = new InetSocketAddress("127.0.0.1", 60196);
            ServerConnection serverConnection = new ServerConnection(address);
            serverConnection.connect(10000);
            Log.add("连接服务器成功！", Log.Type.INFO, Thread.currentThread());
            System.out.println("连接服务器成功！");
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
        String fromArgs = Arguable.stringInArgs(arguments, "-l", "--log");
        Log.setLogFileDir((fromArgs == null) ? logDir : fromArgs);
        Log.add(String.format("UniWood-%s-%s", PLATFORM, VERSION), Log.Type.INFO, Thread.currentThread());
        if (debugMode)
            Log.add("DEBUG MODE ON!!", Log.Type.DEBUG, Thread.currentThread());
    }
}