package info.zpss.uniwood.desktop.client.util;

import info.zpss.uniwood.desktop.client.Main;
import info.zpss.uniwood.desktop.common.AbstractLog;
import info.zpss.uniwood.desktop.common.Arguable;

public class Log extends AbstractLog {
    @Override
    public void init(String[] args) throws Exception {
        String logDir = Main.debug() ? "src/main/logs/desktop/client" : "logs";
        String fromArgs = Arguable.stringInArgs(args, "-l", "--log");
        this.setLogFileDir((fromArgs == null) ? logDir : fromArgs);
        this.add(String.format("UniWood-%s-%s", Main.PLATFORM, Main.VERSION), Log.Type.INFO, Thread.currentThread());
        if (Main.debug())
            this.add("DEBUG MODE ON!!", Log.Type.DEBUG, Thread.currentThread());
    }
}
