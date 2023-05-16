package info.zpss.uniwood.desktop.client.util;

import info.zpss.uniwood.desktop.client.util.socket.SocketHandler;
import info.zpss.uniwood.desktop.common.Log;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;

public class ServerConnection {
    private final SocketAddress serverHost;
    private SocketHandler handler;
    private Socket serverSocketConn;

    public ServerConnection(SocketAddress serverHost) {
        this.serverHost = serverHost;
        this.handler = null;
        this.serverSocketConn = null;
    }

    public void connect(int timeout) throws IOException {
        disconnect();
        int i = 0;
        serverSocketConn = new Socket();
        while (true) {
            try {
                serverSocketConn.connect(serverHost, timeout);
                break;
            } catch (IOException e) {
                if (i < 3) {
                    i++;
                    Log.add("第" + i + "次连接服务器超时，重试中...", Log.Type.WARN, Thread.currentThread());
                } else
                    throw e;
            }
        }
        handler = new SocketHandler(serverSocketConn);
        handler.start();
    }

    public void disconnect() throws IOException {
        if(serverSocketConn != null && !serverSocketConn.isClosed()) {
            if(handler != null && handler.isAlive()) {
                handler.interrupt();
                handler = null;
            }
            serverSocketConn.close();
            serverSocketConn = null;
        }
    }

    public void send(String message) {
        handler.send(message);
    }
}
