package info.zpss.test.LogTest;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogTest {
    private PrintStream out;
    private final static LogTest INSTANCE;

    static {
        INSTANCE = new LogTest();
    }

    private LogTest() {
    }

    public static LogTest getInstance() {
        return INSTANCE;
    }

    public void setLogFileDir(String path) {
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
            this.out = new PrintStream(new FileOutputStream(String.format("%s/%s-%d.log", path, date_str, i)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void addConsole(String message) {
        System.out.println(message);
    }

    public void addFile(String message) {
        if (this.out != null)
            this.out.println(message);
    }

    public void add(String message) {
        this.addConsole(message);
        this.addFile(message);
    }

    public static void main(String[] args) {
        String logDir = "logs"; // 默认日志文件输出路径
        for (int i = 0; i < args.length; i++)
            if (args[i].equals("-l"))
                if(i + 1 < args.length)
                    logDir = args[i + 1];

        LogTest logger = LogTest.getInstance();
        logger.setLogFileDir(logDir);
        logger.add("Hello World!");
    }
}