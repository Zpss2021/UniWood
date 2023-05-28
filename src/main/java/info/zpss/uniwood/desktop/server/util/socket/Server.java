package info.zpss.uniwood.desktop.server.util.socket;

import info.zpss.uniwood.desktop.client.Main;
import info.zpss.uniwood.desktop.common.Arguable;
import info.zpss.uniwood.desktop.server.util.ServerLogger;

public class Server implements Arguable {
    private SocketListener listener;
    private int port;
    private int maxConn;

    public Server(){
    }

    private Server(int port, int maxConn) {
        this.listener = null;
        this.port = port;
        this.maxConn = maxConn;
    }

    public void start() throws Exception {
        if (listener == null || !listener.isAlive()) {
            listener = new SocketListener(port, maxConn);
            listener.start();
        }
    }

    public void stop() throws Exception {
        if (listener != null && listener.isAlive()) {
            listener.close();
            listener.interrupt();
            listener = null;
        }
    }

    public SocketListener getListener() {
        return listener;
    }

    @Override
    public void config(String[] args) throws NumberFormatException {
        String port = Arguable.stringInArgs(args, "-P", "--port");
        String maxConn = Arguable.stringInArgs(args, "-M", "--max-conn");
        if (port == null) {
            port = "60196";
            Main.logger().add(String.format("未指定服务器端口，使用默认端口%s", port),
                    ServerLogger.Type.INFO, Thread.currentThread());
        }
        if (maxConn == null) {
            maxConn = "16";
            Main.logger().add(String.format("未指定最大客户端连接数，使用默认值%s", maxConn),
                    ServerLogger.Type.INFO, Thread.currentThread());
        }
        this.port = Integer.parseInt(port);
        this.maxConn = Integer.parseInt(maxConn);
    }
}