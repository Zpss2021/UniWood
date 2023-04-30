package info.zpss.uniwood.desktop.client;

import info.zpss.uniwood.desktop.client.tools.*;

public class Main {
    private static boolean DEBUG;
    private static String[] arguments;
    public static final String PLATFORM;
    public static final String VERSION;

    static {
        DEBUG = true;   // TODO: 打包jar前改为false
        arguments = null;
        PLATFORM = "DESKTOP";
        VERSION = "1.0.0";
    }

    public static void main(String[] args) {
        arguments = new String[args.length];
        System.arraycopy(args, 0, arguments, 0, args.length);
        if (paramInArgs("-D") || paramInArgs("--DEBUG"))
            DEBUG = true;
        setLog();
        System.out.println("Hello, UniWood!");
    }

    public static boolean isDebug() {
        return Main.DEBUG;
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

    private static void setLog() {
        String logDir = DEBUG ? "src/main/logs" : "logs";
        String fromArgs = stringInArgs("-l");
        Log.setLogFileDir((((fromArgs == null) ?
                (fromArgs = stringInArgs("--len")) : fromArgs) == null) ?
                logDir : fromArgs);
        Log.add(String.format("UniWood-%s-%s", PLATFORM, VERSION), Log.Type.INFO, Thread.currentThread());
        if (DEBUG)
            Log.add("DEBUG MODE ON!!", Log.Type.DEBUG, Thread.currentThread());
    }
}