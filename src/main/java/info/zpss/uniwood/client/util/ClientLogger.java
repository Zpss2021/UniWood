package info.zpss.uniwood.client.util;

import info.zpss.uniwood.client.Main;
import info.zpss.uniwood.common.Arguable;
import info.zpss.uniwood.common.Logger;

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
