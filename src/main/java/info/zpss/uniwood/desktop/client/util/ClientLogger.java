package info.zpss.uniwood.desktop.client.util;

import info.zpss.uniwood.desktop.client.Main;
import info.zpss.uniwood.desktop.common.Logger;
import info.zpss.uniwood.desktop.common.Arguable;

public class ClientLogger extends Logger {
    @Override
    public void config(String[] args) throws Exception {
        String logDir = Main.debug() ? "src/main/logs/desktop/client" : "logs";
        String fromArgs = Arguable.stringInArgs(args, "-l", "--log");
        this.setLogFileDir((fromArgs == null) ? logDir : fromArgs);
        this.add(String.format("UniWood-%s-%s", Main.PLATFORM, Main.VERSION), ClientLogger.Type.INFO, Thread.currentThread());
        if (Main.debug())
            this.add("DEBUG MODE ON!!", ClientLogger.Type.DEBUG, Thread.currentThread());
    }
}
