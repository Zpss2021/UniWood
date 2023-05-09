package info.zpss.uniwood.desktop.server;

import info.zpss.uniwood.desktop.common.Log;

public class Main {
    private static boolean debugMode;
    private static String[] arguments;
    public static final String PLATFORM;
    public static final String VERSION;

    static {
        debugMode = true;   // TODO: 打包jar前改为false
        arguments = null;
        PLATFORM = "DESKTOP-SERVER";
        VERSION = "1.0.0";
    }

    public static boolean isDebug() {
        return Main.debugMode;
    }

    public static boolean paramInArgs(String para) {
        for (String arg : arguments)
            if (arg.equals(para))
                return true;
        return false;
    }

    public static String stringInArgs(String para) {
        for (int i = 0; i < arguments.length; i++)
            if (arguments[i].equals(para))
                if (i + 1 < arguments.length)
                    return arguments[i + 1];
        return null;
    }

    public static void main(String[] args) {
        if(debugMode)
            args = new String[] { "-D" };
        arguments = new String[args.length];
        System.arraycopy(args, 0, arguments, 0, args.length);
        if (paramInArgs("-D") || paramInArgs("--DEBUG"))
            debugMode = true;
        setLog();
        Log.add("Hello, UniWood!", Log.Type.INFO, Thread.currentThread());
        try {
            execute();
        } catch (Exception e) {
            Log.add(e, Thread.currentThread());
        }
    }

    private static void execute() throws Exception {
        // TODO: 从这里开始写代码

    }

    private static void setLog() {
        String logDir = debugMode ? "src/main/logs/desktop/server" : "logs";
        String fromArgs = stringInArgs("-l");
        Log.setLogFileDir((((fromArgs == null) ?
                (fromArgs = stringInArgs("--len")) : fromArgs) == null) ?
                logDir : fromArgs);
        Log.add(String.format("UniWood-%s-%s", PLATFORM, VERSION), Log.Type.INFO, Thread.currentThread());
        if (debugMode)
            Log.add("DEBUG MODE ON!!", Log.Type.DEBUG, Thread.currentThread());
    }

}