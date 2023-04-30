package info.zpss.uniwood.desktop.client.tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {

    public enum Type {
        INFO("INFO"),
        WARN("WARN"),
        DEBUG("DEBUG"),
        ERROR("ERROR");
        private final String type;

        Type(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }

        @Override
        public String toString() {
            return getType();
        }
    }

    private static PrintStream out = null;

    private Log() {
    }

    public static void setLogFileDir(String path) {
        int i;
        String date_str = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));
        File logsDir = new File(path);
        if (!logsDir.exists() && logsDir.mkdirs())
            i = 0;
        else {
            i = 1;
            while (new File(String.format("%s/%s-%d.log", path, date_str, i)).exists())
                i++;
        }
        try {
            out = new PrintStream(new FileOutputStream(String.format("%s/%s-%d.log", path, date_str, i)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void addConsole(String message) {
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date(System.currentTimeMillis()));
        System.out.printf("[%s]%s\n", time, message);
    }

    public static void addFile(String message) {
        if (out != null) {
            String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date(System.currentTimeMillis()));
            out.printf("[%s]%s\n", time, message);
        }
    }

    public static void add(String message, Thread currentThread) {
        addConsole(String.format("[%s/%s]%s", currentThread.getName(), Type.INFO, message));
        addFile(String.format("[%s/%s]%s", currentThread.getName(), Type.INFO, message));
    }

    public static void add(String message, Type messageType, Thread currentThread) {
        addConsole(String.format("[%s/%s]%s", currentThread.getName(), messageType, message));
        addFile(String.format("[%s/%s]%s", currentThread.getName(), messageType, message));
    }

    public static void add(Exception e, Thread currentThread) {
        addConsole(String.format("[%s/EXCEPTION]%s", currentThread.getName(), e));
        addFile(String.format("[%s/EXCEPTION]%s", currentThread.getName(), e));
    }
}