package info.zpss.uniwood.desktop.client.util;

import info.zpss.uniwood.desktop.client.util.socket.SocketHandler;

import java.io.IOException;
import java.net.Socket;

public class ServerConnection {
    private SocketHandler handler;
    private Socket serverSocketConn;
    private final String serverHost;
    private final int serverPort;


    public ServerConnection(String host, int port) {
        this.handler = null;
        this.serverHost = host;
        this.serverPort = port;
    }

    public void connect() throws IOException {
        disconnect();
        serverSocketConn = new Socket(serverHost, serverPort);
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
