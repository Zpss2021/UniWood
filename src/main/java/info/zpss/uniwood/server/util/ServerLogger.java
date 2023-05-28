package info.zpss.uniwood.server.util;

import info.zpss.uniwood.common.Logger;
import info.zpss.uniwood.common.Arguable;
import info.zpss.uniwood.server.Main;

public class ServerLogger extends Logger {

    @Override
    public void config(String[] args) throws Exception {
        String logDir = Main.debug() ? "src/main/logs/desktop/server" : "logs";
        String fromArgs = Arguable.stringInArgs(args, "-l", "--log");
        this.setLogFileDir((fromArgs == null) ? logDir : fromArgs);
        this.add(String.format("UniWood-%s-%s", Main.PLATFORM, Main.VERSION), ServerLogger.Type.INFO, Thread.currentThread());
        if (Main.debug())
            this.add("DEBUG MODE ON!!", ServerLogger.Type.DEBUG, Thread.currentThread());
    }
}
