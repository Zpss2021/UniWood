package info.zpss.uniwood.desktop.server.util;

import info.zpss.uniwood.desktop.common.Log;
import info.zpss.uniwood.desktop.common.ProtoMsg;
import info.zpss.uniwood.desktop.server.Main;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

public class SocketHandler extends Thread {
    private final Socket socket;
    private final ServerSocketListener listener;
    private BufferedReader reader;
    private PrintWriter writer;

    public SocketHandler(Socket socket, ServerSocketListener listener) {
        this.socket = socket;
        this.listener = listener;
    }

    public void send(String message) {
        writer.println(message);
        writer.flush();
    }

    private String handleMessage(String message) {
        if (Main.debug())
            Log.add(String.format("收到客户端%s消息：%s", this, message), Thread.currentThread());
        ProtoMsg protoMsg = ProtoMsg.parse(message);
        switch (protoMsg.cmd) {
            // TODO
        }
        return null;    // 当返回值为null时，不发送任何消息
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
            String rec, snd;
            while (!socket.isClosed() && ((rec = reader.readLine()) != null)) {
                snd = handleMessage(rec);
                if (snd != null)
                    send(snd);
            }
            Log.add(String.format("客户端%s断开连接", this), Log.Type.INFO, Thread.currentThread());
        } catch (SocketException e) {
            if (socket.isClosed())
                return;
            Log.add(String.format("客户端%s套接字连接异常！", this), Log.Type.WARN, Thread.currentThread());
            Log.add(e, Thread.currentThread());
        } catch (IOException e) {
            if (socket.isClosed())
                return;
            Log.add(String.format("客户端%s读取消息流I/O异常！", this), Log.Type.WARN, Thread.currentThread());
            Log.add(e, Thread.currentThread());
        } finally {
            try {
                reader.close();
                writer.close();
            } catch (IOException ex) {
                Log.add(String.format("客户端%s关闭消息流I/O异常！", this), Log.Type.WARN, Thread.currentThread());
                Log.add(ex, Thread.currentThread());
            }
            listener.removeHandler(this);
        }
    }
}