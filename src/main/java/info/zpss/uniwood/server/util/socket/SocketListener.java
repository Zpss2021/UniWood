package info.zpss.uniwood.server.util.socket;

import info.zpss.uniwood.server.Main;
import info.zpss.uniwood.server.util.ServerLogger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SocketListener extends Thread {
    private final ServerSocket server;
    private final List<SocketHandler> handlers;
    private final ExecutorService pool; // 线程池，用于处理连接
    private final Semaphore sem;    // 信号量，用于控制最大连接数

    public SocketListener(int port, int maxConnections) throws Exception {
        this.server = new ServerSocket(port);
        this.handlers = new LinkedList<>();
        this.pool = Executors.newFixedThreadPool(maxConnections);
        this.sem = new Semaphore(maxConnections);
    }

    public void close() throws IOException {
        server.close();
    }

    public void removeHandler(SocketHandler handler) {
        handlers.remove(handler);
        sem.release();
    }

    public List<SocketHandler> getHandlers() {
        return handlers;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                sem.acquire();
                Socket socket = server.accept();
                SocketHandler handler = new SocketHandler(socket, this);
                handlers.add(handler);
                pool.execute(handler);
            } catch (IOException e) {
                Main.logger().add("处理套接字连接异常！", ServerLogger.Type.WARN, Thread.currentThread());
                Main.logger().add(e, Thread.currentThread());
            } catch (InterruptedException e) {
                Main.logger().add("套接字监听线程中断！", ServerLogger.Type.WARN, Thread.currentThread());
                Main.logger().add(e, Thread.currentThread());
            }
        }
        pool.shutdown();
    }
}

