package info.zpss.uniwood.desktop.server.util;

import info.zpss.uniwood.desktop.server.util.socket.SocketListener;

public class Server {
    private SocketListener listener;
    private final int port;
    private final int maxConn;

    public Server(int port, int maxConn) {
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

}