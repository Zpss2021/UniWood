package info.zpss.uniwood.desktop.client.util;

import info.zpss.uniwood.desktop.client.Main;
import info.zpss.uniwood.desktop.client.util.socket.SocketHandler;
import info.zpss.uniwood.desktop.common.Arguable;
import info.zpss.uniwood.desktop.common.Log;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class ServerConnection implements Arguable {
    private SocketAddress serverHost;
    private SocketHandler handler;
    private Socket serverSocketConn;
    private int timeout;
    private int retry;

    public ServerConnection(){
    }

    public void connect() throws IOException {
        disconnect();
        int i = 0;
        while (true) {
            try {
                serverSocketConn = new Socket();
                serverSocketConn.connect(serverHost, timeout);
                break;
            } catch (IOException e) {
                if (i < retry) {
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

    @Override
    public void init(String[] args) throws RuntimeException {
        String host = Arguable.stringInArgs(args, "-H", "--host");
        String port = Arguable.stringInArgs(args, "-P", "--port");
        String timeout = Arguable.stringInArgs(args, "-T", "--timeout");
        String retry = Arguable.stringInArgs(args, "-R", "--retry");
        if (host == null) {
            host = Main.debug() ? "localhost" : "zpss.info";
            Log.add(String.format("未指定服务器地址，使用默认地址%s", host), Log.Type.INFO, Thread.currentThread());
        }
        if (port == null) {
            port = "60196";
            Log.add(String.format("未指定服务器端口，使用默认端口%s", port), Log.Type.INFO, Thread.currentThread());
        }
        if (timeout == null) {
            timeout = "1000";
            Log.add(String.format("未指定连接超时时间，使用默认值%s", timeout), Log.Type.INFO, Thread.currentThread());
        }
        if (retry == null) {
            retry = "3";
            Log.add(String.format("未指定连接重试次数，使用默认值%s", retry), Log.Type.INFO, Thread.currentThread());
        }
        this.serverHost = new InetSocketAddress(host, Integer.parseInt(port));
        this.timeout = Integer.parseInt(timeout);
        this.retry = Integer.parseInt(retry);
    }
}