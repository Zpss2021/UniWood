package info.zpss.uniwood.client.util.socket;

import info.zpss.uniwood.client.Main;
import info.zpss.uniwood.client.controller.*;
import info.zpss.uniwood.client.util.ClientLogger;
import info.zpss.uniwood.common.Command;
import info.zpss.uniwood.common.MsgProto;

import javax.swing.*;
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

    public void send(MsgProto msg) {
        String snd = MsgProto.charToLinebreak(msg);
        if (Main.debug())
            Main.logger().add(String.format("向服务器发送消息：%s", ((snd.length() > 64) ?
                    (snd.substring(0, 61) + "...") : snd)), Thread.currentThread());
        writer.println(snd);
        writer.flush();
    }

    private void handleMessage(String message) {
        if (Main.debug())
            Main.logger().add(String.format("收到服务器消息：%s",
                    ((message.length() > 64) ? (message.substring(0, 61) + "...") : message)), Thread.currentThread());
        MsgProto msg = MsgProto.parse(message);
        if (msg.cmd.equals(Command.UNKNOWN)) {
            Main.logger().add("服务器消息解析失败！", ClientLogger.Type.WARN, Thread.currentThread());
            Main.logger().add(String.format("未知信息：%s", message), ClientLogger.Type.WARN, Thread.currentThread());
            return;
        }
        switch (msg.cmd) {
            case LOGIN_SUCCESS:
                new Thread(() -> HandlerMethods.loginSuccess(msg)).start();
                break;
            case LOGIN_FAILED:
                new Thread(() -> HandlerMethods.loginFailed(msg)).start();
                break;
            case REGISTER_SUCCESS:
                new Thread(() -> HandlerMethods.registerSuccess(msg)).start();
                break;
            case REGISTER_FAILED:
                new Thread(() -> HandlerMethods.registerFailed(msg)).start();
                break;
            case UNIV_LIST:
                new Thread(() -> HandlerMethods.universityList(msg)).start();
                break;
            case USER_INFO:
                new Thread(() -> HandlerMethods.userInfo(msg)).start();
                break;
            case FOLW_LIST:
            case FANS_LIST:
            case POST_LIST:
                new Thread(() -> HandlerMethods.userCenterUpdate(msg)).start();
                break;
            case ZONE_LIST:
                new Thread(() -> HandlerMethods.loginUserUpdate(msg)).start();
                break;
            case FAVOR_LIST:
                new Thread(() -> HandlerMethods.favorList(msg)).start();
                break;
            case EDIT_SUCCESS:
                new Thread(HandlerMethods::editSuccess).start();
                break;
            case EDIT_FAILED:
                new Thread(() -> HandlerMethods.editFailed(msg)).start();
                break;
            case POST_INFO:
                new Thread(() -> HandlerMethods.postInfo(msg)).start();
                break;
            case ZONE_INFO:
                new Thread(() -> HandlerMethods.zoneInfo(msg)).start();
                break;
            case FLOR_INFO:
                new Thread(() -> HandlerMethods.floorInfo(msg)).start();
                break;
            case ZONE_POST:
                new Thread(() -> HandlerMethods.zonePost(msg)).start();
                break;
            default:
                Main.logger().add(String.format("未知命令：%s", msg.cmd), ClientLogger.Type.WARN, Thread.currentThread());
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
            LoginController.getInstance().getView().hideWindow();
            PostController.getInstance().getView().hideWindow();
            RegisterController.getInstance().getView().hideWindow();
            EditController.getInstance().getView().hideWindow();
            UserCenterController.getInstance().getView().hideWindow();
            Main.logger().add(String.format("服务器%s连接异常，正在尝试重新连接...", this),
                    ClientLogger.Type.WARN, Thread.currentThread());
            Main.logger().add(e, Thread.currentThread());
            SwingUtilities.invokeLater(() ->
                    JOptionPane.showMessageDialog(MainController.getInstance().getView().getComponent(),
                            "与服务器的连接已断开，正在尝试重新连接...", "警告", JOptionPane.WARNING_MESSAGE));
            try {
                Main.connection().connect();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(MainController.getInstance().getView().getComponent(),
                        "连接服务器失败，请检查网络连接！", "错误", JOptionPane.ERROR_MESSAGE);
                Main.logger().add("连接服务器失败！", ClientLogger.Type.ERROR, Thread.currentThread());
                Main.logger().add(e, Thread.currentThread());
                System.exit(-1);
            }
            JOptionPane.showMessageDialog(MainController.getInstance().getView().getComponent(),
                    "与服务器的连接已恢复！", "提示", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            Main.logger().add(String.format("服务器%s读取消息流I/O异常！", this),
                    ClientLogger.Type.WARN, Thread.currentThread());
            Main.logger().add(e, Thread.currentThread());
        } finally {
            try {
                reader.close();
                writer.close();
            } catch (IOException ex) {
                Main.logger().add(String.format("服务器%s关闭消息流I/O异常！", this),
                        ClientLogger.Type.WARN, Thread.currentThread());
                Main.logger().add(ex, Thread.currentThread());
            }
        }
    }
}
