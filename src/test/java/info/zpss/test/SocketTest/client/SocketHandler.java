package info.zpss.test.SocketTest.client;

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
        System.out.println("向服务器发送消息：" + message);  // TODO:DEBUG
        writer.println(message);
        writer.flush();
    }

    private void handleMessage(String message) {
        System.out.println("收到服务器消息：" + message);  // TODO:DEBUG
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
            System.err.println("读取消息流Socket异常！");
            e.printStackTrace();
        } catch (IOException e) {
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
        }
    }
}
