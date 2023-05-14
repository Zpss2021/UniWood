package info.zpss.uniwood.desktop.client.util;

import info.zpss.uniwood.desktop.client.Main;
import info.zpss.uniwood.desktop.common.Log;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

public class SocketHandler extends Thread {
    private final Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    public SocketHandler(Socket socket) {
        this.socket = socket;
    }

    public void send(String message) {
        if (Main.debug())
            Log.add("向服务器发送消息：" + message, Thread.currentThread());
        writer.println(message);
        writer.flush();
    }

    private void handleMessage(String message) {
        if (Main.debug())
            Log.add("收到服务器消息：" + message, Thread.currentThread());
    }

    @Override
    public String toString() {
        return String.format("%s:%d", socket.getInetAddress(), socket.getPort());
    }

    @Override
    public void run() {
        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),
                    StandardCharsets.UTF_8)));
            String rec;
            while (!socket.isClosed() && ((rec = reader.readLine()) != null))
                handleMessage(rec);
        } catch (SocketException e) {
            if (socket.isClosed())
                return;
            Log.add(String.format("服务器%s套接字连接异常！", this), Log.Type.WARN, Thread.currentThread());
            Log.add(e, Thread.currentThread());
        } catch (IOException e) {
            Log.add(String.format("服务器%s读取消息流I/O异常！", this), Log.Type.WARN, Thread.currentThread());
            Log.add(e, Thread.currentThread());
        } finally {
            try {
                reader.close();
                writer.close();
            } catch (IOException ex) {
                Log.add(String.format("服务器%s关闭消息流I/O异常！", this), Log.Type.WARN, Thread.currentThread());
                Log.add(ex, Thread.currentThread());
            }
        }
    }
}
