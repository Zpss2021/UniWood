package info.zpss.uniwood.client.util.socket;

import info.zpss.uniwood.client.Main;
import info.zpss.uniwood.common.Arguable;
import info.zpss.uniwood.common.Command;
import info.zpss.uniwood.common.MsgProto;
import info.zpss.uniwood.client.util.ClientLogger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class ServerConnection implements Arguable {
    private SocketAddress serverHost;
    private SocketHandler handler;
    private Socket serverSocketConn;
    private final ScheduledExecutorService executor;
    private int timeout;
    private int retry;

    public ServerConnection() {
        executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(() -> {
            try {
                if (serverSocketConn != null && serverSocketConn.isClosed()) {
                    Main.logger().add("服务器连接已断开，正在重连...", ClientLogger.Type.WARN, Thread.currentThread());
                    connect();
                }
                send(MsgProto.build(Command.HEARTBEAT).toString());
            } catch (IOException e) {
                Main.logger().add("服务器连接异常！", ClientLogger.Type.WARN, Thread.currentThread());
            }
        }, 1, 3, java.util.concurrent.TimeUnit.SECONDS);
    }

    public void connect() throws IOException {
        disconnect();
        int i = 0;
        while (true) {
            try {
                serverSocketConn = new Socket();
                serverSocketConn.connect(serverHost, timeout);
                Main.logger().add("服务器已连接", ClientLogger.Type.INFO, Thread.currentThread());
                break;
            } catch (IOException e) {
                if (i < retry) {
                    i++;
                    Main.logger().add("第" + i + "次连接服务器超时，重试中...", ClientLogger.Type.WARN, Thread.currentThread());
                } else
                    throw e;
            }
        }
        handler = new SocketHandler(serverSocketConn);
        handler.start();
    }

    public void disconnect() throws IOException {
        if (serverSocketConn != null && !serverSocketConn.isClosed()) {
            if (handler != null && handler.isAlive()) {
                handler.interrupt();
                handler = null;
            }
            executor.shutdown();
            serverSocketConn.close();
            serverSocketConn = null;
        }
    }

    public void send(String message) {
        handler.send(message);
    }

    public void send(MsgProto message) {
        handler.send(message.toString());
    }

    @Override
    public void config(String[] args) throws RuntimeException {
        String host = Arguable.stringInArgs(args, "-H", "--host");
        String port = Arguable.stringInArgs(args, "-P", "--port");
        String timeout = Arguable.stringInArgs(args, "-T", "--timeout");
        String retry = Arguable.stringInArgs(args, "-R", "--retry");
        if (host == null) {
            host = Main.debug() ? "localhost" : "zpss.info";
            Main.logger().add(String.format("未指定服务器地址，使用默认地址%s", host),
                    ClientLogger.Type.INFO, Thread.currentThread());
        }
        if (port == null) {
            port = "60196";
            Main.logger().add(String.format("未指定服务器端口，使用默认端口%s", port),
                    ClientLogger.Type.INFO, Thread.currentThread());
        }
        if (timeout == null) {
            timeout = "1000";
            Main.logger().add(String.format("未指定连接超时时间，使用默认值%s", timeout),
                    ClientLogger.Type.INFO, Thread.currentThread());
        }
        if (retry == null) {
            retry = "3";
            Main.logger().add(String.format("未指定连接重试次数，使用默认值%s", retry),
                    ClientLogger.Type.INFO, Thread.currentThread());
        }
        this.serverHost = new InetSocketAddress(host, Integer.parseInt(port));
        this.timeout = Integer.parseInt(timeout);
        this.retry = Integer.parseInt(retry);
    }
}
