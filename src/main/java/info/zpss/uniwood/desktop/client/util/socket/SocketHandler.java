package info.zpss.uniwood.desktop.client.util.socket;

import info.zpss.uniwood.desktop.client.Main;
import info.zpss.uniwood.desktop.client.controller.LoginController;
import info.zpss.uniwood.desktop.client.model.entity.User;
import info.zpss.uniwood.desktop.client.util.Log;
import info.zpss.uniwood.desktop.common.*;

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
            Main.logger().add("向服务器发送消息：" + message, Thread.currentThread());
        writer.println(message);
        writer.flush();
    }

    private void handleMessage(String message) {
        if (Main.debug())
            Main.logger().add("收到服务器消息：" + message, Thread.currentThread());
        ProtoMsg msg = ProtoMsg.parse(message);
        if (msg.cmd == null) {  // TODO
            Main.logger().add("服务器消息解析失败！", Log.Type.WARN, Thread.currentThread());
            Main.logger().add(String.format("未知信息：%s", message), Log.Type.WARN, Thread.currentThread());
            return;
        }
        switch (msg.cmd) {
            case LOGIN_SUCCESS:
                Main.logger().add("用户" + msg.args[1] + "登录成功！", Log.Type.INFO, Thread.currentThread());
                User loginUser = new User();
                loginUser.update(msg);
                LoginController.getInstance().loginSuccess(loginUser);
                break;
            case LOGIN_FAILED:
                Main.logger().add("用户登录失败！", Log.Type.INFO, Thread.currentThread());
                LoginController.getInstance().loginFailed();
                break;
            default:
                Main.logger().add("未知命令：" + msg.cmd, Log.Type.WARN, Thread.currentThread());
                break;
        }
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
            while (!Thread.currentThread().isInterrupted()
                    && !socket.isClosed()
                    && ((rec = reader.readLine()) != null))
                handleMessage(rec);
        } catch (SocketException e) {
            if (socket.isClosed())
                return;
            Main.logger().add(String.format("服务器%s套接字连接异常！", this), Log.Type.WARN, Thread.currentThread());
            Main.logger().add(e, Thread.currentThread());
            try {   // TODO：重连机制
                Main.connection().connect();
            } catch (IOException ex) {
                Main.logger().add("连接服务器失败！", Log.Type.ERROR, Thread.currentThread());
                Main.logger().add(e, Thread.currentThread());
                System.exit(1);
            }
        } catch (IOException e) {
            Main.logger().add(String.format("服务器%s读取消息流I/O异常！", this), Log.Type.WARN, Thread.currentThread());
            Main.logger().add(e, Thread.currentThread());
        } finally {
            try {
                reader.close();
                writer.close();
            } catch (IOException ex) {
                Main.logger().add(String.format("服务器%s关闭消息流I/O异常！", this), Log.Type.WARN, Thread.currentThread());
                Main.logger().add(ex, Thread.currentThread());
            }
        }
    }
}
