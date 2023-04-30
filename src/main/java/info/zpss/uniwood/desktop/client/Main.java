package info.zpss.uniwood.desktop.client;

import info.zpss.uniwood.desktop.client.tools.*;

public class Main {
    private static boolean DEBUG = true;  // TODO: 打包jar前改为false
    public static final String PLATFORM = "DESKTOP";
    public static final String VERSION = "1.0.0";

    public static void main(String[] args) {
        if (paramInArgs("-D", args) || paramInArgs("--DEBUG", args))
            DEBUG = true;
        setLog(args);
        System.out.println("Hello, UniWood!");
    }

    public static boolean isDebug() {
        return Main.DEBUG;
    }

    private static boolean paramInArgs(String para, String[] args) {
        for (String arg : args)
            if (arg.equals(para))
                return true;
        return false;
    }

    private static String stringInArgs(String para, String[] args) {
        for (int i = 0; i < args.length; i++)
            if (args[i].equals(para))
                if (i + 1 < args.length)
                    return args[i + 1];
        return null;
    }

    private static void setLog(String[] args) {
        String logDir = DEBUG ? "src/main/logs" : "logs";
        String fromArgs = stringInArgs("-l", args);
        Log.setLogFileDir((((fromArgs == null) ?
                (fromArgs = stringInArgs("--len", args)) : fromArgs) == null) ?
                logDir : fromArgs);
        Log.add(String.format("UniWood-%s-%s", PLATFORM, VERSION), Log.Type.INFO, Thread.currentThread());
        if (DEBUG)
            Log.add("DEBUG MODE ON!!", Log.Type.DEBUG, Thread.currentThread());
    }
}