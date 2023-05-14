package info.zpss.test.SocketTest.server;

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

    public void send(String s) {
        if (writer == null)
            return;
        writer.println(s);
        writer.flush();
    }

    private String handleMessage(String message) {
        System.out.println("收到" + message);
        return "Hello, Client";    // TODO
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
            System.out.println("连接断开"); // 连接断开
        } catch (SocketException e) {
            if (socket.isClosed())
                return;
            System.err.println("读取消息流Socket异常！");
            e.printStackTrace();
        } catch (IOException e) {
            if (socket.isClosed())
                return;
            System.err.println("读取消息流I/O异常！");
            e.printStackTrace();
        } finally {
            try {
                reader.close();
                writer.close();
            } catch (IOException ex) {
                System.err.println("关闭消息流I/O异常！");
                ex.printStackTrace();
            }
            listener.removeHandler(this);   // 从监听器中移除
        }
    }
}